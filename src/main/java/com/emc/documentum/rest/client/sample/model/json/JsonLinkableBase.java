package com.emc.documentum.rest.client.sample.model.json;

import java.util.ArrayList;
import java.util.List;

import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkableBase;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class JsonLinkableBase extends LinkableBase {
    @JsonProperty
    protected List<JsonLink> links;
    
    @Override
    public List<Link> getLinks() {
        return links==null?null:new ArrayList<Link>(links);
    }

    public void setLinks(List<JsonLink> links) {
        this.links = links;
    }
}
