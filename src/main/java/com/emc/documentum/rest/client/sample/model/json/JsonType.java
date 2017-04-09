/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonType extends JsonInlineLinkableBase implements RestType {
    private String name;
    private String label;
    private String parent;
    private String sharedParent;
    private String category;
    private List<Map<String, Object>> properties;
    private JsonType legacyType;

    public JsonType getLegacyType() {
        return legacyType;
    }

    @JsonProperty("type")
    public void setLegacyType(JsonType legacyType) {
        this.legacyType = legacyType;
    }

    private boolean legacy() {
        return legacyType != null;
    }

    @Override
    public String getName() {
        return legacy() ? legacyType.name : name;
    }

    public void setName(String name) {
        if (legacy()) {
            this.legacyType.name = name;
        } else {
            this.name = name;
        }
    }

    @Override
    public String getLabel() {
        return legacy() ? legacyType.label : label;
    }

    public void setLabel(String label) {
        if (legacy()) {
            this.legacyType.label = label;
        } else {
            this.label = label;
        }
    }

    @Override
    public String getParent() {
        return legacy() ? legacyType.parent : parent;
    }

    public void setParent(String parent) {
        if (legacy()) {
            this.legacyType.parent = parent;
        } else {
            this.parent = parent;
        }
    }

    @Override
    public String getSharedParent() {
        return legacy() ? legacyType.sharedParent : sharedParent;
    }

    public void setSharedParent(String sharedParent) {
        if (legacy()) {
            this.legacyType.sharedParent = sharedParent;
        } else {
            this.sharedParent = sharedParent;
        }
    }

    @Override
    public String getCategory() {
        return legacy() ? legacyType.category : category;
    }

    public void setCategory(String category) {
        if (legacy()) {
            this.legacyType.category = category;
        } else {
            this.category = category;
        }
    }

    @Override
    public List<Map<String, Object>> getProperties() {
        return legacy() ? legacyType.properties : properties;
    }

    public void setProperties(List<Map<String, Object>> properties) {
        if (legacy()) {
            this.legacyType.properties = properties;
        } else {
            this.properties = properties;
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Link> getLinks() {
        return legacy() ? (List) legacyType.links : links;
    }

    @Override
    public void setLinks(List<JsonLink> links) {
        if (legacy()) {
            this.legacyType.links = links;
        } else {
            this.links = links;
        }
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
            && Equals.equal(legacyType, that.legacyType)
            && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, label, parent, sharedParent, category, legacyType, properties, getSrc(), getContentType());
    }
}
