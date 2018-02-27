/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Element;

import com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbContext;
import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestPropertyLifecycleInfo;
import com.emc.documentum.rest.client.sample.model.RestPropertyValueAssist;
import com.emc.documentum.rest.client.sample.model.RestTypeValueConstraint;

@XmlRootElement(name="property-of-lifecycle")
public class JaxbPropertyLifecycleInfo implements RestPropertyLifecycleInfo {
    private String label;
    private boolean hidden;
    private boolean required;
    private boolean notNull;
    private boolean readOnly;
    private boolean searchable;
    private List<String> dependencies;
//    private List<Map<String, Object>> vaDefinitions;
    private boolean ignoreConstraints;
    private boolean ignoreImmutable;
    private List<RestTypeValueConstraint> constraints;
    private String notNullEnforce;
    private String notNullMessage;
    private List<Element> valueAssistElement;

//    @XmlTransient
//    public List<Map<String, Object>> getVaDefinitions() {
//        return vaDefinitions;
//    }
//
//    public void setVaDefinitions(List<Map<String, Object>> vaDefinitions) {
//        this.vaDefinitions = vaDefinitions;
//    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public void setIgnoreConstraints(boolean ignoreConstraints) {
        this.ignoreConstraints = ignoreConstraints;
    }

    public void setIgnoreImmutable(boolean ignoreImmutable) {
        this.ignoreImmutable = ignoreImmutable;
    }

    public void setConstraints(List<RestTypeValueConstraint> constraints) {
        this.constraints = constraints;
    }

    public void setNotNullEnforce(String notNullEnforce) {
        this.notNullEnforce = notNullEnforce;
    }

    public void setNotNullMessage(String notNullMessage) {
        this.notNullMessage = notNullMessage;
    }

    @Override
    @XmlAttribute
    public String getLabel() {
        return label;
    }

    @Override
    @XmlAttribute
    public boolean isHidden() {
        return hidden;
    }

    @Override
    @XmlAttribute
    public boolean isRequired() {
        return required;
    }

    @Override
    @XmlAttribute(name="notnull")
    public boolean isNotNull() {
        return notNull;
    }

    @Override
    @XmlAttribute(name="readonly")
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    @XmlAttribute
    public boolean isSearchable() {
        return searchable;
    }

    @Override
    @XmlElementWrapper(name="dependencies")
    @XmlElement(name="item")
    public List<String> getDependencies() {
        return dependencies;
    }

//    @Override
//    @XmlElementWrapper(name="value-assist")
//    @XmlElement(type)
//    @XmlTransient
//    public List<Map<String, Object>> getVADefinitions() {
//        return vaDefinitions;
//    }
    
    @Override
    @XmlAttribute(name="ignore-parent-constraints")
    public boolean isIgnoreConstraints() {
        return ignoreConstraints;
    }

    @Override
    @XmlElementWrapper(name="constraints")
    @XmlElement(name="constraint", type=JaxbTypeValueConstraint.class)
    public List<RestTypeValueConstraint> getConstraints() {
        return constraints;
    }

    @Override
    @XmlAttribute(name="ignore-immutable")
    public boolean isIgnoreImmutable() {
        return ignoreImmutable;
    }

    @Override
    @XmlAttribute(name="notnull-enforce")
    public String getNotNullEnforce() {
        return notNullEnforce;
    }

    @Override
    @XmlAttribute(name="notnull-message")
    public String getNotNullMessage() {
        return notNullMessage;
    }

    @XmlElementWrapper(name="value-assist")
    @XmlAnyElement
    public List<Element> getValueAssistElement() {
        return valueAssistElement;
    }

    public void setValueAssistElement(List<Element> valueAssistElement) {
        this.valueAssistElement = valueAssistElement;
    }

    @Override
//    @XmlTransient
    public List<RestPropertyValueAssist> getValueAssist() {
        if(valueAssistElement == null) {
            return null;
        }
        List<RestPropertyValueAssist> valueAssist = new ArrayList<>(valueAssistElement.size());
        for(Element e : valueAssistElement) {
            valueAssist.add((RestPropertyValueAssist)DCTMJaxbContext.unmarshal(e));
        }
        return valueAssist;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbPropertyLifecycleInfo that = (JaxbPropertyLifecycleInfo)obj;
        return Equals.equal(hidden, that.hidden) 
            && Equals.equal(label, that.label)
            && Equals.equal(required, that.required)
            && Equals.equal(notNull, that.notNull)
            && Equals.equal(readOnly, that.readOnly)
            && Equals.equal(searchable, that.searchable)
            && Equals.equal(dependencies, that.dependencies)
            && Equals.equal(ignoreConstraints, that.ignoreConstraints)
            && Equals.equal(ignoreImmutable, that.ignoreImmutable)
            && Equals.equal(constraints, that.constraints)
            && Equals.equal(notNullEnforce, that.notNullEnforce)
            && Equals.equal(notNullMessage, that.notNullMessage);
    }
}