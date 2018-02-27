/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestTypeLifecycleInfo;
import com.emc.documentum.rest.client.sample.model.RestTypeValueConstraint;

@XmlRootElement(name="type-of-lifecycle")
public class JaxbTypeLifecycleInfo implements RestTypeLifecycleInfo {
    private String label;
    private String defaultLifecycle;
    private String defaultLifecycleVersion;
    private boolean ignoreConstraints;
    private List<RestTypeValueConstraint> constraints = null;
    
    @Override
    @XmlAttribute
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    @XmlAttribute(name="default-lifecycle")
    public String getDefaultLifecycle() {
        return defaultLifecycle;
    }

    public void setDefaultLifecycle(String defaultLifecycle) {
        this.defaultLifecycle = defaultLifecycle;
    }

    @Override
    @XmlAttribute(name="default-lifecycle-version")
    public String getDefaultLifecycleVersion() {
        return defaultLifecycleVersion;
    }

    public void setDefaultLifecycleVersion(String defaultLifecycleVersion) {
        this.defaultLifecycleVersion = defaultLifecycleVersion;
    }

    @Override
    @XmlAttribute(name="ignore-parent-constraints")
    public boolean getIgnoreConstraints() {
        return ignoreConstraints;
    }

    @Override
    @XmlElementWrapper(name="constraints")
    @XmlElement(name="constraint", type=JaxbTypeValueConstraint.class)
    public List<RestTypeValueConstraint> getConstraints() {
        return constraints;
    }
    
    public void setIgnoreConstraints(boolean ignoreConstraints) {
        this.ignoreConstraints = ignoreConstraints;
    }

    public void setConstraints(List<RestTypeValueConstraint> constraints) {
        this.constraints = constraints;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbTypeLifecycleInfo that = (JaxbTypeLifecycleInfo)obj;
        return Equals.equal(defaultLifecycle, that.defaultLifecycle) 
            && Equals.equal(defaultLifecycleVersion, that.defaultLifecycleVersion)
            && Equals.equal(label, that.label)
            && Equals.equal(ignoreConstraints, that.ignoreConstraints)
            && Equals.equal(constraints, that.constraints);
    }
}