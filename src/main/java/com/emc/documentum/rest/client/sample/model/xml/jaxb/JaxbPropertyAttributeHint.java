/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestTypeDisplayConfig.RestPropertyAttributeHint;

@XmlRootElement(name = "attribute-hint")
public class JaxbPropertyAttributeHint implements RestPropertyAttributeHint {
    private String attribute;
    private int displayHint;

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setDisplayHint(int displayHint) {
        this.displayHint = displayHint;
    }

    @Override
    public String getAttribute() {
        return attribute;
    }

    @Override
    @XmlElement(name="display-hint")
    public int getDisplayHint() {
        return displayHint;
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbPropertyAttributeHint that = (JaxbPropertyAttributeHint)obj;
        return Equals.equal(attribute, that.attribute) 
            && Equals.equal(displayHint, that.displayHint);
    }
}