/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import com.emc.documentum.rest.client.sample.model.Search.Searchable;

/**
 * the class represents the saved search of the REST services
 */
public interface SavedSearch extends RestObject, Searchable {
    public String getQueryDocument();
}
