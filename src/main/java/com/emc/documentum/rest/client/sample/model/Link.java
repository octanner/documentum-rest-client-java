/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import com.emc.documentum.rest.client.sample.model.json.JsonLink;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;



/**
 * the link of the resources
 */
@JsonTypeInfo(use=Id.CLASS, defaultImpl=JsonLink.class)
public interface Link {
    /**
     * @return the link relation of the link
     */
    public String getRel();
    
    /**
     * @return the link href
     */
    public String getHref();
    
    /**
     * @return the specified title of each link, can be used for some links which have same relation
     */
    public String getTitle();
    
    public String getType();
    
    /**
     * @return the href template if the link is a template
     */
    public String getHreftemplate();
    
    /**
     * @return whether the link is a template or not
     */
    public boolean isTemplate();
}
