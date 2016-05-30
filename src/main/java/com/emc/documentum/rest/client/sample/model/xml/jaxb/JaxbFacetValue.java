/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Facet;
import com.emc.documentum.rest.client.sample.model.FacetValue;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name = "facet-value", namespace = XMLNamespace.DM_NAMESPACE)
public class JaxbFacetValue extends JaxbDmLinkableBase implements FacetValue {
    private String facetId;
    private String id;
    private int count;
    private String constraint;
    private List<Facet> facets;

    @Override
    @XmlElement(name="facet-id")
    public String getFacetId() {
        return facetId;
    }

    public void setFacetId(String facetId) {
        this.facetId = facetId;
    }

    @Override
    @XmlElement(name="facet-value-id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    @XmlElement(name="facet-value-count")
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    @XmlElement(name="facet-value-constraint")
    public String getConstraint() {
        return constraint;
    }

    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    @Override
    @XmlElement(name="facet", type=JaxbFacet.class, namespace=XMLNamespace.DM_NAMESPACE)
    public List<Facet> getFacets() {
        return facets;
    }

    public void setFacets(List<Facet> facets) {
        this.facets = facets;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbFacetValue that = (JaxbFacetValue) obj;
        return Equals.equal(facetId, that.facetId)
                && Equals.equal(id, that.id)
                && Equals.equal(count, that.count)
                && Equals.equal(constraint, that.constraint);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(facetId, id, count, constraint);
    }
}