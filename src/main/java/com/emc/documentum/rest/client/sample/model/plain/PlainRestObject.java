/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.plain;

import java.util.HashMap;
import java.util.Map;

import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.emc.documentum.rest.client.sample.model.ObjectLifecycle;
import com.emc.documentum.rest.client.sample.model.RestObject;

/**
 * the plain RestObject implementation which has properties only
 * normally used when create/update the RestObject
 */
public class PlainRestObject extends PlainLinkableBase implements RestObject {
    private final String type;
    private final Map<String, Object> properties;
    private final String href;
    private ObjectLifecycle objectLifecycle;
    private ObjectAspects objectAspects;
    
    public PlainRestObject(String type, Map<String, Object> properties) {
        this.properties = properties;
        this.type = type;
        this.href = null;
    }
    
    public PlainRestObject(Map<String, Object> properties) {
        this(null, properties);
    }
    
    public PlainRestObject(String...properties) {
        if(properties == null || properties.length %2 != 0) {
            throw new IllegalArgumentException("the properties must be key value pair");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        for(int i=0;i<properties.length;i+=2) {
            map.put(properties[i], properties[i+1]);
        }
        this.type = null;
        this.href = null;
        this.properties = map;
    }
    
    public PlainRestObject(String href) {
        this.href = href;
        this.type = null;
        this.properties = null;
    }
    
    @Override
    public String getObjectId() {
        return properties==null?null:(String)properties.get("r_object_id");
    }
    
    @Override
    public String getObjectName() {
        return properties==null?null:(String)properties.get("object_name");
    }
    
    @Override
    public String getObjectType() {
        return properties==null?null:(String)properties.get("r_object_type");
    }
    
    @Override
    public String getType() {
        return type;
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    @Override
    public ObjectLifecycle getObjectLifecycle() {
        return objectLifecycle;
    }

    public void setObjectLifecycle(ObjectLifecycle objectLifecycle) {
        this.objectLifecycle = objectLifecycle;
    }

    @Override
    public ObjectAspects getObjectAspects() {
        return objectAspects;
    }

    public void setObjectAspects(ObjectAspects objectAspects) {
        this.objectAspects = objectAspects;
    }

    @Override
    public String getHref() {
        return href;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDefinition() {
        return null;
    }

    @Override
    public String getPropertiesType() {
        return null;
    }
}
