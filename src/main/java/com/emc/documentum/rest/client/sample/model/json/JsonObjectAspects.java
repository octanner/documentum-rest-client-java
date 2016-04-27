/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonObjectAspects extends LinkableBase implements ObjectAspects {
    @JsonProperty
    private List<String> aspects;
    @JsonProperty
    private List<Link> links;
    
    public JsonObjectAspects() {
    }
    
    public JsonObjectAspects(String... aspects) {
        this.aspects = Arrays.asList(aspects);
    }

    @Override
    public List<String> getAspects() {
        return aspects;
    }

    public void setAspects(List<String> aspects) {
        this.aspects = aspects;
    }

    @Override
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public boolean equals(Object obj) {
        JsonObjectAspects that = (JsonObjectAspects)obj;
        return Equals.equal(aspects, that.aspects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aspects);
    }
}
