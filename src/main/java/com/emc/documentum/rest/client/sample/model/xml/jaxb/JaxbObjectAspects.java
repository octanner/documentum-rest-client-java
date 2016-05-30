/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="object-aspects", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbObjectAspects extends JaxbDmLinkableBase implements ObjectAspects {

    private List<String> aspects;
    
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
    public boolean equals(Object obj) {
        JaxbObjectAspects that = (JaxbObjectAspects) obj;
        return Equals.equal(aspects, that.aspects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aspects);
    }
}
