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
import com.emc.documentum.rest.client.sample.model.RestTypeScopeConfig;

@XmlRootElement(name = "scope-config")
public class JaxbTypeScopeConfig implements RestTypeScopeConfig {
    private String id;
    private List<String> scopes;
    private List<String> categories;
    private List<RestTypeDisplayConfig> displayConfigs;

    public void setId(String id) {
        this.id = id;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setDisplayConfigs(List<RestTypeDisplayConfig> displayConfigs) {
        this.displayConfigs = displayConfigs;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    @XmlElementWrapper(name="scope")
    @XmlElement(name="item")
    public List<String> getScopes() {
        return this.scopes;
    }

    @Override
    @XmlElementWrapper(name="category")
    @XmlElement(name="item")
    public List<String> getCategories() {
        return this.categories;
    }

    @Override
    @XmlElementWrapper(name="display-configs")
    @XmlElement(name="display-config", type=JaxbTypeDisplayConfig.class)
    public List<RestTypeDisplayConfig> getDisplayConfigs() {
        return this.displayConfigs;
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbTypeScopeConfig that = (JaxbTypeScopeConfig)obj;
        return Equals.equal(id, that.id) 
            && Equals.equal(scopes, that.scopes)
            && Equals.equal(categories, that.categories)
            && Equals.equal(displayConfigs, that.displayConfigs);
    }
}