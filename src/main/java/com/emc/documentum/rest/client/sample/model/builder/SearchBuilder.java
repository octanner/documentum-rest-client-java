/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.builder;

import java.util.List;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.impl.AbstractRestTemplateClient.ClientType;
import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.Search.Expression;
import com.emc.documentum.rest.client.sample.model.Search.FacetDefinition;
import com.emc.documentum.rest.client.sample.model.Search.Location;
import com.emc.documentum.rest.client.sample.model.Search.Sort;
import com.emc.documentum.rest.client.sample.model.json.JsonSearchBuilder;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearchBuilder;

public abstract class SearchBuilder {
    public static SearchBuilder builder(DCTMRestClient client) {
        return client.getClientType()==ClientType.XML?new JaxbSearchBuilder() : new JsonSearchBuilder(); 
    }

    public abstract Search build();
    public abstract SearchBuilder reset();
    public abstract SearchBuilder types(String... types);
    public abstract SearchBuilder columns(String... columns);
    public abstract SearchBuilder sort(String property, Boolean ascending, String lang, Boolean ascii);
    public abstract SearchBuilder sort(Sort sort);
    public abstract SearchBuilder idLocation(String id, boolean descendent);
    public abstract SearchBuilder pathLocation(String path, boolean descendent);
    public abstract SearchBuilder location(Location location);
    public abstract SearchBuilder collections(String... collections);
    public abstract SearchBuilder allVersions(boolean allVersions);
    public abstract SearchBuilder includeHiddenObjects(boolean includeHiddenObjects);
    public abstract SearchBuilder maxResultsForFacets(int maxResultsForFacets);
    public abstract SearchBuilder facetDefinition(String id, List<String> attributes, String groupby, String sort, int maxValues, String... properties);
    public abstract SearchBuilder facetDefinition(FacetDefinition facetDefinition);
    public abstract SearchBuilder expressionSet(List<Expression> expressions);
    public abstract SearchBuilder propertyExpression(String name, String value, String operator, boolean exactMatch,
                                                     boolean repeated, boolean caseSensitive, boolean fuzzy);
    public abstract SearchBuilder fullTextExpression(String value, boolean fuzzy);
    public abstract SearchBuilder relativeDateExpression(String name, int value, String timeUnit, String operator);
    public abstract SearchBuilder propertyListExpression(String name, List<String> values, String operator, boolean repeated);
    public abstract SearchBuilder propertyRangeExpression(String name, String from, String to);
    public abstract SearchBuilder expression(Expression expression);
    public abstract SearchBuilder asTemplate();
}
