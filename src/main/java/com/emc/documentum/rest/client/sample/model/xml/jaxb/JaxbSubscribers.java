/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Subscribers;

@XmlRootElement(name = "subscribers")
public class JaxbSubscribers implements Subscribers {
    private List<String> subscribers;
    
    public JaxbSubscribers(String... subscribers) {
        this.subscribers = Arrays.asList(subscribers);
    }
    
    public JaxbSubscribers() {
    }

    @Override
    @XmlElementWrapper(name="subscribers")
    @XmlElement(name="subscriber")
    public List<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<String> subscribers) {
        this.subscribers = subscribers;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbSubscribers that = (JaxbSubscribers) obj;
        return Equals.equal(subscribers, that.subscribers);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(subscribers);
    }
}