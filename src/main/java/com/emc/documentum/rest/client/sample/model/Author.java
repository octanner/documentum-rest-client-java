/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import com.emc.documentum.rest.client.sample.model.json.JsonAuthor;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;


/**
 * the class represents the Author of Feed and Entry
 */
@JsonTypeInfo(use=Id.CLASS, defaultImpl=JsonAuthor.class)
public interface Author {
    
    /**
     * @return author name
     */
    public String getName();
    
    /**
     * @return the uri of the author
     */
    public String getUri();
    
    /**
     * @return the email of the author
     */
    public String getEmail();
}
