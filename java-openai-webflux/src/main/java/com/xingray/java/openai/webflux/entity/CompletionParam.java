package com.xingray.java.openai.webflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CompletionParam {

    @JsonProperty("model")
    private String model;
    @JsonProperty("prompt")
    private String prompt;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    @JsonProperty("temperature")
    private Integer temperature;
    @JsonProperty("top_p")
    private Integer topP;
    @JsonProperty("n")
    private Integer n;
    @JsonProperty("stream")
    private Boolean stream;
    @JsonProperty("logprobs")
    private Object logprobs;
    @JsonProperty("stop")
    private String stop;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getTopP() {
        return topP;
    }

    public void setTopP(Integer topP) {
        this.topP = topP;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public Object getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(Object logprobs) {
        this.logprobs = logprobs;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    @Override
    public String toString() {
        return "CompletionParam{" +
                "model='" + model + '\'' +
                ", prompt='" + prompt + '\'' +
                ", maxTokens=" + maxTokens +
                ", temperature=" + temperature +
                ", topP=" + topP +
                ", n=" + n +
                ", stream=" + stream +
                ", logprobs=" + logprobs +
                ", stop='" + stop + '\'' +
                '}';
    }
}
