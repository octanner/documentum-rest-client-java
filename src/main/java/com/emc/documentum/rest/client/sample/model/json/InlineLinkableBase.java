/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.Objects;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.InlineLinkable;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class InlineLinkableBase extends LinkableBase implements InlineLinkable {
    private String src;
    @JsonProperty("type")  
    private String contentType;
    
    public String getSrc() {
        return src;
    }
    
    public void setSrc(String src) {
        this.src = src;
    }
    
    public String getContentType() {
        return contentType;
    }
    
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    @Override
    @JsonIgnore
    public Linkable getContent() {
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        InlineLinkableBase that = (InlineLinkableBase)obj;
        return super.equals(obj)
            && Equals.equal(src, that.src) 
            && Equals.equal(contentType, that.contentType);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(src, contentType);
    }
}
