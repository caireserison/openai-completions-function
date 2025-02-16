package com.openai.completion.function.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class FunctionCall {
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("parameters")
    private Map<String, Object> parameters;
    @JsonProperty("arguments")
    private String arguments;

    public FunctionCall(String name, String description, Map<String, Object> parameters, String arguments) {
        this.name = name;
        this.description = description;
        this.parameters = parameters;
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
}
