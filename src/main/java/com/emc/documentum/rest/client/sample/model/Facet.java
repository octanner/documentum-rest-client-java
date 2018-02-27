/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * represents search facet provided by REST server
 */
public interface Facet extends Linkable {
    public String getId();
    public String getLabel();
    public List<FacetValue> getValues();
}
