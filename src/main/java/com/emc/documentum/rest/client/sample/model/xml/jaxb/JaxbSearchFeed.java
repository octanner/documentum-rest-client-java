/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Facet;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.SearchEntry;
import com.emc.documentum.rest.client.sample.model.SearchFeed;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="feed", namespace=XMLNamespace.ATOM_NAMESPACE)
public class JaxbSearchFeed<T extends Linkable> extends JaxbFeedBase<T, SearchEntry<T>> implements SearchFeed<T> {
    private List<SearchEntry<T>> entries;
    private List<Facet> facets;

    @Override
    @XmlElement(name="facet", type=JaxbFacet.class)
    public List<Facet> getFacets() {
        return facets;
    }

    public void setFacets(List<Facet> facets) {
        this.facets = facets;
    }
    
    @Override
    @XmlElement(name="entry", type=JaxbSearchEntry.class)
    public List<SearchEntry<T>> getEntries() {
        return entries;
    }

    public void setEntries(List<SearchEntry<T>> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbSearchFeed<?> that = (JaxbSearchFeed<?>)obj;
        return super.equals(obj) 
            && Equals.equal(entries, that.entries);
    }
}
