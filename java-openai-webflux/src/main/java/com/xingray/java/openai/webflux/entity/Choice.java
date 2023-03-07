package com.xingray.java.openai.webflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Choice {
    @JsonProperty("text")
    private String text;
    @JsonProperty("message")
    private Message message;
    @JsonProperty("index")
    private Integer index;
    @JsonProperty("logprobs")
    private Object logprobs;
    @JsonProperty("finish_reason")
    private String finishReason;
    @JsonProperty("delta")
    private Delta delta;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Object getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(Object logprobs) {
        this.logprobs = logprobs;
    }

    public String getFinishReason() {
        return finishReason;
    }

    public void setFinishReason(String finishReason) {
        this.finishReason = finishReason;
    }

    public Delta getDelta() {
        return delta;
    }

    public void setDelta(Delta delta) {
        this.delta = delta;
    }

    @Override
    public String toString() {
        return "Choices{" +
                "text='" + text + '\'' +
                ", message=" + message +
                ", index=" + index +
                ", logprobs=" + logprobs +
                ", finishReason='" + finishReason + '\'' +
                ", delta=" + delta +
                '}';
    }
}
