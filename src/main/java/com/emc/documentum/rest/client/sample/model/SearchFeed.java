/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;


/**
 * the class represents a search feed collection
 */
public interface SearchFeed<T extends Linkable> extends FeedBase<T, SearchEntry<T>> {
    public List<Facet> getFacets();
}
