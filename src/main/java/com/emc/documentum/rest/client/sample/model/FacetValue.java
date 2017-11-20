/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * represents search facet value provided by REST server
 */
public interface FacetValue extends Linkable {
    public String getFacetId();
    public String getId();
    public int getCount();
    public String getConstraint();
    public List<Facet> getFacets();
}
