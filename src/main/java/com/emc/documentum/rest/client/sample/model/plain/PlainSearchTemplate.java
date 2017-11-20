/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.plain;

import java.util.List;

import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.SearchTemplate;

/**
 * the plain PlainSearchTemplate implementation which has properties only
 * normally used when create/update the SearchTemplate
 */
public class PlainSearchTemplate extends PlainRestObject implements SearchTemplate {
    private Search search;
    private String searchReference;
    
    public PlainSearchTemplate(String searchReference) {
        this.searchReference = searchReference;
    }
    
    public PlainSearchTemplate(Search search, String...properties) {
        super(properties);
        this.search = search;
    }
    
    @Override
    public String getQueryDocumentTemplate() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Search getSearch() {
        return search;
    }
    
    @Override
    public List<ExternalVariable<?>> getExternalVariables() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public String getSearchReference() {
        return searchReference;
    }
}
