package com.xingray.java.openai.webflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Permission {
    @JsonProperty("allow_search_indices")
    private Boolean allowSearchIndices;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("organization")
    private String organization;
    @JsonProperty("allow_sampling")
    private Boolean allowSampling;
    @JsonProperty("allow_create_engine")
    private Boolean allowCreateEngine;
    @JsonProperty("allow_logprobs")
    private Boolean allowLogprobs;
    @JsonProperty("is_blocking")
    private Boolean isBlocking;
    @JsonProperty("id")
    private String id;
    @JsonProperty("allow_view")
    private Boolean allowView;
    @JsonProperty("allow_fine_tuning")
    private Boolean allowFineTuning;
    @JsonProperty("object")
    private String object;
    @JsonProperty("group")
    private Object group;

    public Boolean getAllowSearchIndices() {
        return allowSearchIndices;
    }

    public void setAllowSearchIndices(Boolean allowSearchIndices) {
        this.allowSearchIndices = allowSearchIndices;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public Boolean getAllowSampling() {
        return allowSampling;
    }

    public void setAllowSampling(Boolean allowSampling) {
        this.allowSampling = allowSampling;
    }

    public Boolean getAllowCreateEngine() {
        return allowCreateEngine;
    }

    public void setAllowCreateEngine(Boolean allowCreateEngine) {
        this.allowCreateEngine = allowCreateEngine;
    }

    public Boolean getAllowLogprobs() {
        return allowLogprobs;
    }

    public void setAllowLogprobs(Boolean allowLogprobs) {
        this.allowLogprobs = allowLogprobs;
    }

    public Boolean getIsBlocking() {
        return isBlocking;
    }

    public void setIsBlocking(Boolean isBlocking) {
        this.isBlocking = isBlocking;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getAllowView() {
        return allowView;
    }

    public void setAllowView(Boolean allowView) {
        this.allowView = allowView;
    }

    public Boolean getAllowFineTuning() {
        return allowFineTuning;
    }

    public void setAllowFineTuning(Boolean allowFineTuning) {
        this.allowFineTuning = allowFineTuning;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Object getGroup() {
        return group;
    }

    public void setGroup(Object group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "allowSearchIndices=" + allowSearchIndices +
                ", created=" + created +
                ", organization='" + organization + '\'' +
                ", allowSampling=" + allowSampling +
                ", allowCreateEngine=" + allowCreateEngine +
                ", allowLogprobs=" + allowLogprobs +
                ", isBlocking=" + isBlocking +
                ", id='" + id + '\'' +
                ", allowView=" + allowView +
                ", allowFineTuning=" + allowFineTuning +
                ", object='" + object + '\'' +
                ", group=" + group +
                '}';
    }
}