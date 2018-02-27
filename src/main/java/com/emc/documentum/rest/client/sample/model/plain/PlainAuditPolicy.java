/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.plain;

import java.util.Map;

import com.emc.documentum.rest.client.sample.model.AuditPolicy;

/**
 * the plain AuditPolicy implementation
 * which is used for AuditPolicy operations 
 */
public class PlainAuditPolicy extends PlainLinkableBase implements AuditPolicy {
    private String objectId;
    private String name;
    private String accessorName;
    private boolean isGroup;
    private Map<String, String> attributeRules;

    @Override
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAccessorName() {
        return accessorName;
    }

    public void setAccessorName(String accessorName) {
        this.accessorName = accessorName;
    }

    @Override
    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    @Override
    public Map<String, String> getAttributeRules() {
        return attributeRules;
    }

    public void setAttributeRules(Map<String, String> attributeRules) {
        this.attributeRules = attributeRules;
    }
}
