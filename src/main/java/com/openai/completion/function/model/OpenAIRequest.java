package com.openai.completion.function.model;

import java.util.List;
import java.util.Map;

public class OpenAIRequest {
    private String model;
    private List<Map<String, String>> messages;
    private List<FunctionCall> functions;

    public OpenAIRequest(String model, List<Map<String, String>> messages, List<FunctionCall> functions) {
        this.model = model;
        this.messages = messages;
        this.functions = functions;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Map<String, String>> getMessages() {
        return messages;
    }

    public void setMessages(List<Map<String, String>> messages) {
        this.messages = messages;
    }

    public List<FunctionCall> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionCall> functions) {
        this.functions = functions;
    }
}
