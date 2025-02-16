package com.openai.completion.function.service.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class HGWeatherService {
    private final RestClient restClient;

    @Value("${hgweather.api.url}")
    private String hgweatherUrl;
    @Value("${hgweather.api.apiKey}")
    private String hgwwatherApiKey;

    public HGWeatherService(RestClient restClient) {
        this.restClient = restClient;
    }

    public String getTemperature(String city) throws JsonProcessingException {
        String url = hgweatherUrl.concat("?key=").concat(hgwwatherApiKey).concat("&city_name=").concat(city);

        String responseBody = restClient.get()
                .uri(url)
                .header("Authorization", "Bearer " + hgwwatherApiKey)
                .header("Content-Type", "application/json")
                .retrieve()
                .body(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);

        if (rootNode != null && rootNode.get("results") != null) {
            return "A temperatura atual em " + city + " é " + rootNode.get("results").get("temp").asText() + "°C.";
        }

        return "Não consegui obter a temperatura de " + city + ". Tente novamente mais tarde.";
    }
}
