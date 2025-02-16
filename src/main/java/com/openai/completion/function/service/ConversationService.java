package com.openai.completion.function.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openai.completion.function.model.ChatResponse;
import com.openai.completion.function.service.rest.OpenAIService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConversationService {
    private final OpenAIService openAIService;
    private final List<Map<String, String>> conversationHistory = new ArrayList<>();

    public ConversationService(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    public ChatResponse getCityConversation(String userMessage) throws JsonProcessingException {
        String responseMessage;

        if (conversationHistory.isEmpty()) {
            responseMessage = "Olá! Eu sou um especialista em cidades e posso fornecer informações sobre cultura, história, economia e clima de qualquer cidade do mundo. Pergunte-me o que quiser!";
        } else {
            responseMessage = openAIService.chatWithOpenAI(userMessage, conversationHistory);
        }

        saveLocalHistory("user", userMessage);
        saveLocalHistory("assistant", responseMessage);

        return new ChatResponse(responseMessage);
    }

    private void saveLocalHistory(String role, String message) {
        Map<String, String> input = new HashMap<>();
        input.put("role", role);
        input.put("content", message);
        conversationHistory.add(input);
    }
}
