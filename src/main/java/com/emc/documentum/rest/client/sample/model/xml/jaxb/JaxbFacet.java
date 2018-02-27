/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Facet;
import com.emc.documentum.rest.client.sample.model.FacetValue;

@XmlRootElement(name = "facet")
public class JaxbFacet extends JaxbDmLinkableBase implements Facet {
    private String id;
    private String label;
    private List<FacetValue> values;

    @Override
    @XmlElement(name="facet-id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    @XmlElement(name="facet-label")
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    @XmlElement(name="facet-value", type=JaxbFacetValue.class)
    public List<FacetValue> getValues() {
        return values;
    }

    public void setValues(List<FacetValue> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbFacet that = (JaxbFacet) obj;
        return Equals.equal(id, that.id) && Equals.equal(label, that.label)
                && Equals.equal(values, that.values);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, label, values);
    }
}