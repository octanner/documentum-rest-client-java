/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestTypeDisplayConfig;

@XmlRootElement(name = "display-config")
public class JaxbTypeDisplayConfig implements RestTypeDisplayConfig {
    private String id;
    private String name;
    private String attributeSource;
    private boolean isFixedDisplay;
    private List<RestPropertyAttributeHint> attributeHints;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributeSource(String attributeSource) {
        this.attributeSource = attributeSource;
    }

    public void setFixedDisplay(boolean isFixedDisplay) {
        this.isFixedDisplay = isFixedDisplay;
    }

    public void setAttributeHints(List<RestPropertyAttributeHint> attributeHints) {
        this.attributeHints = attributeHints;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    @XmlElement(name="attribute-source")
    public String getAttributeSource() {
        return this.attributeSource;
    }

    @Override
    @XmlElement(name="fixed-display")
    public boolean isFixedDisplay() {
        return this.isFixedDisplay;
    }

    @Override
    @XmlElementWrapper(name="attribute-hints")
    @XmlElement(name="attribute-hint", type=JaxbPropertyAttributeHint.class)
    public List<RestPropertyAttributeHint> getAttributeHints() {
        return attributeHints;
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbTypeDisplayConfig that = (JaxbTypeDisplayConfig)obj;
        return Equals.equal(name, that.name) 
            && Equals.equal(id, that.id)
            && Equals.equal(attributeSource, that.attributeSource)
            && Equals.equal(isFixedDisplay, that.isFixedDisplay)
            && Equals.equal(attributeHints, that.attributeHints);
    }
}