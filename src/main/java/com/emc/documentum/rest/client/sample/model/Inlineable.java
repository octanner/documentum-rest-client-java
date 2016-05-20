/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

/**
 * the object which can be inlined in the feed's entry
 */
public interface Inlineable {
    
    /**
     * @return the content src of non inlined entry content
     */
    public String getSrc();
    
    /**
     * @return the content type of non inlined entry content
     */
    public String getContentType();
    
    /**
     * @return the inline content
     */
    public Linkable getContent();
}
