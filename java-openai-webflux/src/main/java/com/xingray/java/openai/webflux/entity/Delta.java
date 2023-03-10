package com.xingray.java.openai.webflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Delta {
    @JsonProperty("role")
    private String role;

    @JsonProperty("content")
    private String content;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Delta{" +
                "role='" + role + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
