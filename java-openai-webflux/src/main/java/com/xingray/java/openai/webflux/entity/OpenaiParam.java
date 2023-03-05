package com.xingray.java.openai.webflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenaiParam {

    @JsonProperty("model")
    private String model;
    @JsonProperty("stream")
    private Boolean stream;
    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("temperature")
    private Integer temperature;
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }
}
