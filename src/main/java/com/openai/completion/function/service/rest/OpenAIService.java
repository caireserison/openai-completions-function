package com.openai.completion.function.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.completion.function.model.FunctionCall;
import com.openai.completion.function.model.OpenAIRequest;
import com.openai.completion.function.model.OpenAIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {
    private static final Logger log = LoggerFactory.getLogger(OpenAIService.class);
    private final RestClient restClient;
    private final HGWeatherService hgWeatherService;

    @Value("${openai.completions.api.url}")
    private String openAiUrl;
    @Value("${openai.completions.api.apiKey}")
    private String openAiApiKey;
    @Value("${openai.completions.api.model}")
    private String openAiModel;

    public OpenAIService(RestClient restClient, HGWeatherService hgWeatherService) {
        this.restClient = restClient;
        this.hgWeatherService = hgWeatherService;
    }

    public String chatWithOpenAI(String userMessage, List<Map<String, String>> conversationHistory) throws JsonProcessingException {
        String functionWeatherName = "get_weather";

        List<FunctionCall> functions = new ArrayList<>();
        functions.add(new FunctionCall(functionWeatherName, "Obtém a temperatura atual de uma cidade", getWeatherFunctionParameters(), null));

        List<Map<String, String>> completeMessages = new ArrayList<>();
        completeMessages.add(Map.of("role", "system", "content", "Você é um especialista em cidades. Você deve responder apenas com informações úteis sobre cultura, história, economia e clima de cidades."));
        completeMessages.addAll(conversationHistory);
        completeMessages.add(Map.of("role", "user", "content", userMessage));

        OpenAIRequest requestBody = new OpenAIRequest(openAiModel, completeMessages, functions);
        OpenAIResponse responseBody;
        try {
            responseBody = restClient.post()
                    .uri(openAiUrl)
                    .header("Authorization", "Bearer " + openAiApiKey)
                    .header("Content-Type", "application/json")
                    .body(requestBody)
                    .retrieve()
                    .body(OpenAIResponse.class);
        } catch (Exception e) {
            log.error("Erro ao fazer requisição para OpenAI: {}", e.getMessage());
            return "Desculpe, não consegui processar sua solicitação.";
        }

        if (responseBody != null && !responseBody.getChoices().isEmpty()) {
            var messageResponse = responseBody.getChoices().getFirst().getMessage();
            if (messageResponse.getFunctionCall() != null) {
                if (messageResponse.getFunctionCall().getName().equals(functionWeatherName)) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(messageResponse.getFunctionCall().getArguments());
                    return hgWeatherService.getTemperature(rootNode.get("city").asText());
                }
            } else if (!messageResponse.getContent().isEmpty()) {
                return messageResponse.getContent();
            }
        }

        return "Desculpe, não consegui processar sua solicitação.";
    }

    private static Map<String, Object> getWeatherFunctionParameters() {
        return Map.of(
                        "type", "object",
                        "properties", Map.of(
                                "city", Map.of("type", "string",
                                                    "description", "Nome da cidade para buscar o clima")
                        ),
                        "required", List.of("city")
                );
    }
}