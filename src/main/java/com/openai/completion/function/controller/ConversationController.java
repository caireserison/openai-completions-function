package com.openai.completion.function.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.openai.completion.function.model.ChatRequest;
import com.openai.completion.function.model.ChatResponse;
import com.openai.completion.function.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("/cities")
    public ResponseEntity<ChatResponse> getCityInfo(@RequestBody ChatRequest request) throws JsonProcessingException {
        String userMessage = request.getMessage();
        ChatResponse response = conversationService.getCityConversation(userMessage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
