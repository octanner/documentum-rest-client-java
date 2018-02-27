/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;

@XmlRootElement(name = "lifecycle")
public class JaxbTypeLifecycleBased {
    private String id;
    private List<JaxbTypeStateBased> states;
    
    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name="state")
    public List<JaxbTypeStateBased> getStates() {
        return states;
    }

    public void setStates(List<JaxbTypeStateBased> states) {
        this.states = states;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbTypeLifecycleBased that = (JaxbTypeLifecycleBased)obj;
        return Equals.equal(id, that.id) 
            && Equals.equal(states, that.states);
    }
}