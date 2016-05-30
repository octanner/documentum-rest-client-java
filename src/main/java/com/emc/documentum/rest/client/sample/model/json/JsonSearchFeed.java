/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.ArrayList;
import java.util.List;

import com.emc.documentum.rest.client.sample.model.Facet;
import com.emc.documentum.rest.client.sample.model.InlineLinkable;
import com.emc.documentum.rest.client.sample.model.SearchEntry;
import com.emc.documentum.rest.client.sample.model.SearchFeed;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonSearchFeed<T extends InlineLinkable> extends JsonFeedBase<T, SearchEntry<T>> implements SearchFeed<T> {
    @JsonProperty
    private List<JsonFacet> facets;
    
    @JsonProperty
    private List<JsonSearchEntry<T>> entries;

    @Override
    public List<SearchEntry<T>> getEntries() {
        return entries==null?null:new ArrayList<SearchEntry<T>>(entries);
    }

    public void setEntries(List<JsonSearchEntry<T>> entries) {
        this.entries = entries;
    }
    
    @Override
    public List<Facet> getFacets() {
        return facets==null?null:new ArrayList<Facet>(facets);
    }

    public void setFacets(List<JsonFacet> facets) {
        this.facets = facets;
    }
}
