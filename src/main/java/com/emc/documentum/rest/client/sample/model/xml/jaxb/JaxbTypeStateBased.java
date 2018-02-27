/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;

@XmlRootElement(name = "state")
public class JaxbTypeStateBased {
    private String name;
    private JaxbTypeLifecycleInfo typeLifecycle;
    private JaxbPropertyLifecycleInfo propertyLifecycle;
    
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="type-of-lifecycle")
    public JaxbTypeLifecycleInfo getTypeLifecycle() {
        return typeLifecycle;
    }

    public void setTypeLifecycle(JaxbTypeLifecycleInfo typeLifecycle) {
        this.typeLifecycle = typeLifecycle;
    }

    @XmlElement(name="property-of-lifecycle")
    public JaxbPropertyLifecycleInfo getPropertyLifecycle() {
        return propertyLifecycle;
    }

    public void setPropertyLifecycle(JaxbPropertyLifecycleInfo propertyLifecycle) {
        this.propertyLifecycle = propertyLifecycle;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbTypeStateBased that = (JaxbTypeStateBased)obj;
        return Equals.equal(name, that.name) 
            && Equals.equal(typeLifecycle, that.typeLifecycle)
            && Equals.equal(propertyLifecycle, that.propertyLifecycle);
    }
}