package com.xingray.java.openai.webflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TextCompletionResponse {

    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("choices")
    private List<Choices> choices;
    @JsonProperty("model")
    private String model;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public List<Choices> getChoices() {
        return choices;
    }

    public void setChoices(List<Choices> choices) {
        this.choices = choices;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "OpenAiResponse{" +
                "id='" + id + '\'' +
                ", object='" + object + '\'' +
                ", created=" + created +
                ", choices=" + choices +
                ", model='" + model + '\'' +
                '}';
    }

    public static class Choices {
        @JsonProperty("text")
        private String text;
        @JsonProperty("index")
        private Integer index;
        @JsonProperty("logprobs")
        private Object logprobs;
        @JsonProperty("finish_reason")
        private Object finishReason;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
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

        public Object getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(Object finishReason) {
            this.finishReason = finishReason;
        }

        @Override
        public String toString() {
            return "Choices{" +
                    "text='" + text + '\'' +
                    ", index=" + index +
                    ", logprobs=" + logprobs +
                    ", finishReason=" + finishReason +
                    '}';
        }
    }
}
