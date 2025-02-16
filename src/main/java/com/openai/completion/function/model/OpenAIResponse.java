package com.openai.completion.function.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OpenAIResponse {
    @JsonProperty("choices")
    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public static class Choice {
        @JsonProperty("message")
        private Message message;

        public Message getMessage() {
            return message;
        }
    }

    public static class Message {
        @JsonProperty("content")
        private String content;

        @JsonProperty("function_call")
        private FunctionCall functionCall;

        public String getContent() {
            return content;
        }

        public FunctionCall getFunctionCall() {
            return functionCall;
        }
    }
}
