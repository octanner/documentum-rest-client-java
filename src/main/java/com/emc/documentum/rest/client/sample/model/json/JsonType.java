/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestType;

public class JsonType extends JsonInlineLinkableBase implements RestType {
    private String name;
    private String label;
    private String parent;
    private String sharedParent;
    private String category;
    private List<Map<String, Object>> properties;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String getSharedParent() {
        return sharedParent;
    }

    public void setSharedParent(String sharedParent) {
        this.sharedParent = sharedParent;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public List<Map<String, Object>> getProperties() {
        return properties;
    }

    public void setProperties(List<Map<String, Object>> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object obj) {
        JsonType that = (JsonType)obj;
        return Equals.equal(name, that.name) 
            && Equals.equal(label, that.label)
            && Equals.equal(parent, that.parent)
            && Equals.equal(sharedParent, that.sharedParent)
            && Equals.equal(category, that.category)
            && Equals.equal(properties, that.properties)
            && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, label, parent, sharedParent, category, properties, getSrc(), getContentType());
    }
}
