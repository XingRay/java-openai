package com.xingray.java.openai.webflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingray.java.openai.webflux.entity.Message;

import java.util.List;

public class ChatCompletionParam {

    @JsonProperty("model")
    private String model;
    @JsonProperty("messages")
    private List<Message> messages;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "ChatCompletionParam{" +
                "model='" + model + '\'' +
                ", messages=" + messages +
                '}';
    }
}
