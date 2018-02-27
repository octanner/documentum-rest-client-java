/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.plain;

import com.emc.documentum.rest.client.sample.model.SavedSearch;
import com.emc.documentum.rest.client.sample.model.Search;

/**
 * the plain PlainSavedSearch implementation which has properties only
 * normally used when create/update the SavedSearch
 */
public class PlainSavedSearch extends PlainRestObject implements SavedSearch {
    private Search search;
    public PlainSavedSearch(Search search, String...properties) {
        super(properties);
        this.search = search;
    }
    
    @Override
    public String getQueryDocument() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Search getSearch() {
        return search;
    }
}
