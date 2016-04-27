/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="object-aspects", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbObjectAspects extends LinkableBase implements ObjectAspects {

    private List<String> aspects;
    private List<Link> links;
    
    public JaxbObjectAspects() {
    }

    public JaxbObjectAspects(String... aspects) {
        this.aspects = Arrays.asList(aspects);
    }
    
    @Override
    @XmlElement(name = "aspect")
    public List<String> getAspects() {
        return aspects;
    }

    public void setAspects(List<String> aspects) {
        this.aspects = aspects;
    }

    @Override
    @XmlElementWrapper(name = "links")
    @XmlElement(name = "link", type = JaxbLink.class, namespace=XMLNamespace.DM_NAMESPACE)
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbObjectAspects that = (JaxbObjectAspects) obj;
        return Equals.equal(aspects, that.aspects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aspects);
    }
}
