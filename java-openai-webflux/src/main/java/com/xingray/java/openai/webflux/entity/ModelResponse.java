package com.xingray.java.openai.webflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ModelResponse {

    @JsonProperty("data")
    private List<Model> data;
    @JsonProperty("object")
    private String object;

    public List<Model> getData() {
        return data;
    }

    public void setData(List<Model> data) {
        this.data = data;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "ModelResponse{" +
                "data=" + data +
                ", object='" + object + '\'' +
                '}';
    }
}
