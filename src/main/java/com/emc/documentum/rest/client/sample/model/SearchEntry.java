/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * the class represents the search entry of a feed
 */
public interface SearchEntry<T extends Linkable> extends Entry<T> {
    public String getScore();
    public List<String> getTerms();
}
