/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.Search.Column;
import com.emc.documentum.rest.client.sample.model.Search.Expression;
import com.emc.documentum.rest.client.sample.model.Search.FacetDefinition;
import com.emc.documentum.rest.client.sample.model.Search.Location;
import com.emc.documentum.rest.client.sample.model.Search.Sort;
import com.emc.documentum.rest.client.sample.model.builder.SearchBuilder;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbColumn;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbExpressionSet;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbFacetDefinition;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbFullTextExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbIdLocation;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbPathLocation;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbPropertyExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbPropertyListExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbPropertyRangeExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbRelativeDateExpression;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearch.JaxbSort;

public class JaxbSearchBuilder extends SearchBuilder {
    private JaxbSearch search = new JaxbSearch();

    @Override
    public Search build() {
        return search;
    }
    
    @Override
    public SearchBuilder reset() {
        search = new JaxbSearch();
        return this;
    }

    @Override
    public SearchBuilder types(String... types) {
        search.setTypes(Arrays.asList(types));
        return this;
    }
    
    @Override
    public SearchBuilder columns(String... columns) {
        List<Column> list = new ArrayList<>();
        for (String c : columns) {
            list.add(new JaxbColumn(c));
        }
        return this;
    }

    @Override
    public SearchBuilder sort(String property, Boolean ascending, String lang, Boolean ascii) {
        return sort(new JaxbSort(property, ascending, lang, ascii));
    }
    
    @Override
    public SearchBuilder sort(Sort sort) {
        if (search.getSorts() == null) {
            search.setSorts(new ArrayList<Sort>());
        }
        search.getSorts().add(sort);
        return this;
    }
    
    @Override
    public SearchBuilder idLocation(String id, boolean descendent) {
        return location(new JaxbIdLocation(id, descendent));
    }

    @Override
    public SearchBuilder pathLocation(String path, boolean descendent) {
        return location(new JaxbPathLocation(path, descendent));
    }

    @Override
    public SearchBuilder location(Location location) {
        if (search.getLocations() == null) {
            search.setLocations(new ArrayList<Location>());
        }
        search.getLocations().add(location);
        return this;
    }
    
    @Override
    public SearchBuilder collections(String... collections) {
        search.setCollections(Arrays.asList(collections));
        return this;
    }
    
    @Override
    public SearchBuilder allVersions(boolean allVersions) {
        search.setAllVersions(allVersions);
        return this;
    }

    @Override
    public SearchBuilder includeHiddenObjects(boolean includeHiddenObjects) {
        search.setIncludeHiddenObjects(includeHiddenObjects);
        return this;
    }

    @Override
    public SearchBuilder maxResultsForFacets(int maxResultsForFacets) {
        search.setMaxResultsForFacets(maxResultsForFacets);
        return this;
    }

    @Override
    public SearchBuilder facetDefinition(String id, List<String> attributes, String groupby, String sort, int maxValues,
            String... properties) {
        JaxbFacetDefinition definition = new JaxbFacetDefinition(id, attributes, groupby, sort, maxValues);
        if(properties != null && properties.length > 0) {
            Map<String, String> map = new HashMap<>();
            for(int i=0;i<properties.length-1;i+=2) {
                map.put(properties[i], properties[i+1]);
            }
            definition.setProperties(map);
        }
        return facetDefinition(definition);
    }

    @Override
    public SearchBuilder facetDefinition(FacetDefinition facetDefinition) {
        if (search.getFacetDefinitions() == null) {
            search.setFacetDefinitions(new ArrayList<FacetDefinition>());
        }
        search.getFacetDefinitions().add(facetDefinition);
        return this;
    }

    @Override
    public SearchBuilder expressionSet(List<Expression> expressions) {
        search.setExpressionSet(new JaxbExpressionSet(expressions));
        return this;
    }

    @Override
    public SearchBuilder propertyExpression(String name, String value, String operator,
            boolean exactMatch, boolean repeated, boolean caseSensitive, boolean fuzzy) {
        return expression(new JaxbPropertyExpression(name, value, operator, exactMatch, repeated, caseSensitive, fuzzy));
    }

    @Override
    public SearchBuilder fullTextExpression(String value, boolean fuzzy) {
        return expression(new JaxbFullTextExpression(value, fuzzy));
    }

    @Override
    public SearchBuilder relativeDateExpression(String name, int value, String timeUnit, String operator) {
        return expression(new JaxbRelativeDateExpression(name, value, timeUnit, operator));
    }

    @Override
    public SearchBuilder propertyListExpression(String name, List<String> values, String operator, boolean repeated) {
        return expression(new JaxbPropertyListExpression(name, values, operator, repeated));
    }

    @Override
    public SearchBuilder propertyRangeExpression(String name, String from, String to) {
        return expression(new JaxbPropertyRangeExpression(name, from, to));
    }

    @Override
    public SearchBuilder expression(Expression expression) {
        if(search.getExpressionSet() == null) {
            JaxbExpressionSet expressionSet = new JaxbExpressionSet();
            expressionSet.setExpressions(new ArrayList<Expression>());
            search.setExpressionSet(expressionSet);
        }
        search.getExpressionSet().getExpressions().add(expression);
        return this;
    }

    @Override
    public SearchBuilder asTemplate() {
        if(search.getExpressionSet() != null && search.getExpressionSet().getExpressions().size() > 0) {
            ((JaxbExpression)search.getExpressionSet().getExpressions().get(search.getExpressionSet().getExpressions().size() - 1)).setTemplate(true);
        }
        return this;
    }
}
