/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;
import java.util.Map;

/**
 * the class represents the type of the REST services
 */
public interface RestType extends Linkable {
    /**
     * @return type name
     */
    String getName();
    
    /**
     * @return type label
     */
    String getLabel();
    
    /**
     * @return type parent type name
     */
    String getParent();

    /**
     * @return lightweight type shared parent type name
     */
    String getSharedParent();
    
    /**
     * @return type category
     */
    String getCategory();
    
    /**
     * @return all attributes of item
     */
    List<Map<String, Object>> getProperties();
}
