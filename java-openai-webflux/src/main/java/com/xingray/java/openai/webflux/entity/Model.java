package com.xingray.java.openai.webflux.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Model {
    @JsonProperty("parent")
    private Object parent;
    @JsonProperty("created")
    private Integer created;
    @JsonProperty("root")
    private String root;
    @JsonProperty("owned_by")
    private String ownedBy;
    @JsonProperty("permission")
    private List<Permission> permission;
    @JsonProperty("id")
    private String id;
    @JsonProperty("object")
    private String object;

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public Integer getCreated() {
        return created;
    }

    public void setCreated(Integer created) {
        this.created = created;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public List<Permission> getPermission() {
        return permission;
    }

    public void setPermission(List<Permission> permission) {
        this.permission = permission;
    }

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

    @Override
    public String toString() {
        return "Model{" +
                "parent=" + parent +
                ", created=" + created +
                ", root='" + root + '\'' +
                ", ownedBy='" + ownedBy + '\'' +
                ", permission=" + permission +
                ", id='" + id + '\'' +
                ", object='" + object + '\'' +
                '}';
    }
}