/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.AvailableAuditEvents;

@XmlRootElement(name="audit-event-list")
public class JaxbAvailableAuditEvents extends JaxbDmLinkableBase implements AvailableAuditEvents {
    private List<String> events;

    @XmlElementWrapper(name = "events")
    @XmlElement(name = "event")
    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbAvailableAuditEvents that = (JaxbAvailableAuditEvents) obj;
        return Equals.equal(events, that.events)
                && super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(events);
    }
}
