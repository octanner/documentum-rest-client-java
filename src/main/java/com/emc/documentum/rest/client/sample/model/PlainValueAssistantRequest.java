/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.HashMap;
import java.util.Map;

/**
 * the plain ValueAssistantRequest implementation which has properties
 */
public class PlainValueAssistantRequest implements ValueAssistantRequest {
    private final Map<String, Object> properties;

    public PlainValueAssistantRequest(Map<String, Object> properties) {
        this.properties = properties;
    }
    
    public PlainValueAssistantRequest(String...properties) {
        if(properties == null || properties.length %2 != 0) {
            throw new IllegalArgumentException("the properties must be key value pair");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for(int i=0;i<properties.length;i+=2) {
            map.put(properties[i], properties[i+1]);
        }
        this.properties = map;
    }
    
    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }
}
