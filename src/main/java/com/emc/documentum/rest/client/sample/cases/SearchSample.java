/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Arrays;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.Search.FacetDefinition;
import com.emc.documentum.rest.client.sample.model.Search.PropertyExpression;
import com.emc.documentum.rest.client.sample.model.SearchFeed;
import com.emc.documentum.rest.client.sample.model.SearchTemplate;
import com.emc.documentum.rest.client.sample.model.builder.SearchBuilder;
import com.emc.documentum.rest.client.sample.model.plain.PlainSearchTemplate;

import static com.emc.documentum.rest.client.sample.client.util.Debug.debugSerialize;
import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printFields;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printSearchFeed;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Search")
@RestServiceVersion(7.3)
public class SearchSample extends Sample {
    public void search() {
        printStep("search with aql 1");
        SearchBuilder builder = SearchBuilder.builder(client);
        Search search = builder.allVersions(true)
                               .columns("r_object_id", "r_object_type", "object_name")
                               .fullTextExpression("test", true)
                               .sort("r_object_id", false, null, null)
                               .build();
        System.out.println("The aql is: ");
        debugSerialize(client, search);
        printNewLine();
        SearchFeed<RestObject> result = client.search(search);
        printSearchFeed(result);
        printNewLine();

        printStep("search with aql 2");
        search = builder.reset()
                        .fullTextExpression("test", true)
                        .facetDefinition("f1", Arrays.asList("r_object_type"), FacetDefinition.GROUP_BY_ALPHARANGE, FacetDefinition.SORT_FREQUENCY, 8, "range", "a:e, f:z")
                        .facetDefinition("f2", Arrays.asList("r_modify_date"), FacetDefinition.GROUP_BY_DATE_RELATIVE_DATE, FacetDefinition.SORT_VALUE_ASCENDING, 9)
                        .build();
        System.out.println("The aql is: ");
        debugSerialize(client, search);
        printNewLine();
        result = client.search(search);
        printSearchFeed(result);
        printNewLine();
    }
    
    public void searchTemplate() {
        printStep("create a standard search template without external variable");
        SearchBuilder builder = SearchBuilder.builder(client);
        Search search = builder.allVersions(true)
                               .facetDefinition("f1", Arrays.asList("r_object_type"), FacetDefinition.GROUP_BY_ALPHARANGE, FacetDefinition.SORT_FREQUENCY, 8, "range", "a:e, f:z")
                               .facetDefinition("f2", Arrays.asList("r_modify_date"), FacetDefinition.GROUP_BY_DATE_RELATIVE_DATE, FacetDefinition.SORT_VALUE_ASCENDING, 9)
                               .pathLocation("/Temp", false)
                               .types("dm_sysobject")
                               .columns("r_object_id", "r_object_type", "object_name")
                               .fullTextExpression("test", true)
                               .propertyExpression("object_name", "test", PropertyExpression.OPERATOR_CONTAINS, false, false, false, true)
                               .sort("r_object_id", false, null, null)
                               .build();
        SearchTemplate createdSearchTemplateWithoutVariables = client.createSearchTemmplate(new PlainSearchTemplate(search, "object_name", "my-search-template-without-variables"));
        print(createdSearchTemplateWithoutVariables, "r_object_id", "object_name", "r_object_type", "r_is_public");
        printNewLine();
        
        printStep("get the single search template " + createdSearchTemplateWithoutVariables.getObjectName());
        SearchTemplate searchTemplateWithoutVariables = client.getSearchTemplate(createdSearchTemplateWithoutVariables.self());
        print(searchTemplateWithoutVariables, "r_object_id", "object_name", "r_object_type", "r_is_public");
        printFields(searchTemplateWithoutVariables.getExternalVariables(), "variableType", "id", "expressionType", "dataType", "propertyName", "operator", "variableValue");
        System.out.println("The aql is: ");
        debugSerialize(client, searchTemplateWithoutVariables.getSearch());
        printNewLine();
        
        printStep("create a search template with external variables");
        search = builder.reset()
                        .allVersions(true)
                        .facetDefinition("f1", Arrays.asList("r_object_type"), FacetDefinition.GROUP_BY_ALPHARANGE, FacetDefinition.SORT_FREQUENCY, 8, "range", "a:e, f:z")
                        .facetDefinition("f2", Arrays.asList("r_modify_date"), FacetDefinition.GROUP_BY_DATE_RELATIVE_DATE, FacetDefinition.SORT_VALUE_ASCENDING, 9)
                        .pathLocation("/Temp", false)
                        .types("dm_sysobject")
                        .columns("r_object_id", "r_object_type", "object_name")
                        .fullTextExpression("test", true).asTemplate()
                        .propertyExpression("object_name", "test", PropertyExpression.OPERATOR_CONTAINS, false, false, false, true).asTemplate()
                        .sort("r_object_id", false, null, null)
                        .build();
        SearchTemplate createdSearchTemplateWithVariables = client.createSearchTemmplate(new PlainSearchTemplate(search, "object_name", "my-search-template-with-variables"));
        print(createdSearchTemplateWithVariables, "r_object_id", "object_name", "r_object_type", "r_is_public");
        printNewLine();
        
        printStep("get a single search template " + createdSearchTemplateWithVariables.getObjectName());
        SearchTemplate searchTemplateWithVariables = client.getSearchTemplate(createdSearchTemplateWithVariables.self());
        print(searchTemplateWithVariables, "r_object_id", "object_name", "r_object_type", "r_is_public");
        printFields(searchTemplateWithVariables.getExternalVariables(), "variableType", "id", "expressionType", "dataType", "propertyName", "operator", "variableValue");
        System.out.println("The aql is: ");
        debugSerialize(client, searchTemplateWithVariables.getSearch());
        printNewLine();

        printStep("get all search templates");
        Feed<SearchTemplate> searchTemplates = client.getSearchTemplates();
        printEntryContentSrc(searchTemplates);
        printNewLine();

        printStep("delete the created search templates");
        client.delete(searchTemplateWithoutVariables);
        printHttpStatus();
        client.delete(searchTemplateWithVariables);
        printHttpStatus();
        printNewLine();
    }
}
