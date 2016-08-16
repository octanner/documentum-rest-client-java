/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Arrays;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.SavedSearch;
import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.Search.FacetDefinition;
import com.emc.documentum.rest.client.sample.model.Search.PropertyExpression;
import com.emc.documentum.rest.client.sample.model.Search.PropertyListExpression;
import com.emc.documentum.rest.client.sample.model.SearchFeed;
import com.emc.documentum.rest.client.sample.model.SearchTemplate;
import com.emc.documentum.rest.client.sample.model.SearchTemplate.FullTextVariable;
import com.emc.documentum.rest.client.sample.model.builder.SearchBuilder;
import com.emc.documentum.rest.client.sample.model.plain.PlainSavedSearch;
import com.emc.documentum.rest.client.sample.model.plain.PlainSearchTemplate;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printSearchFeed;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Search (Advanced)")
@RestServiceVersion(7.3)
public class SearchSample extends Sample {
    public void search() {
        printStep("search with aql");
        SearchBuilder builder = SearchBuilder.builder(client);
        Search search = builder.allVersions(true)
                               .columns("r_object_id", "r_object_type", "object_name")
                               .fullTextExpression("test", true)
                               .sort("r_object_id", false, null, null)
                               .build();
        print(client, search);
        printNewLine();
        SearchFeed<RestObject> result = client.search(search, "items-per-page", "5");
        printSearchFeed(result);
        printNewLine();

        printStep("search with aql and facet");
        search = builder.reset()
                        .fullTextExpression("test", true)
                        .facetDefinition("f1", Arrays.asList("r_object_type"), FacetDefinition.GROUP_BY_ALPHARANGE, FacetDefinition.SORT_FREQUENCY, 8, "range", "a:e, f:z")
                        .facetDefinition("f2", Arrays.asList("r_modify_date"), FacetDefinition.GROUP_BY_DATE_RELATIVE_DATE, FacetDefinition.SORT_VALUE_ASCENDING, 9)
                        .build();
        print(client, search);
        printNewLine();
        result = client.search(search, "items-per-page", "5");
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
        SearchTemplate createdSearchTemplateWithoutVariables = client.createSearchTemplate(new PlainSearchTemplate(search, "object_name", "my-search-template-without-variables"));
        print(client, createdSearchTemplateWithoutVariables);
        printNewLine();
        
        printStep("get the single search template " + createdSearchTemplateWithoutVariables.getObjectName());
        SearchTemplate searchTemplateWithoutVariables = client.getSearchTemplate(createdSearchTemplateWithoutVariables.self());
        print(client, searchTemplateWithoutVariables);
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
        SearchTemplate createdSearchTemplateWithVariables = client.createSearchTemplate(new PlainSearchTemplate(search, "object_name", "my-search-template-with-variables"));
        print(client, createdSearchTemplateWithVariables);
        printNewLine();
        
        printStep("get a single search template " + createdSearchTemplateWithVariables.getObjectName());
        SearchTemplate searchTemplateWithVariables = client.getSearchTemplate(createdSearchTemplateWithVariables.self());
        print(client, searchTemplateWithVariables);
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
        
        printStep("create a search template with external variables to execute");
        search = builder.reset()
                        .allVersions(true)
                        .facetDefinition("f1", Arrays.asList("r_object_type"), FacetDefinition.GROUP_BY_ALPHARANGE, FacetDefinition.SORT_FREQUENCY, 8, "range", "a:e, f:z")
                        .facetDefinition("f2", Arrays.asList("r_modify_date"), FacetDefinition.GROUP_BY_DATE_RELATIVE_DATE, FacetDefinition.SORT_VALUE_ASCENDING, 9)
                        .columns("r_object_id", "r_object_type", "object_name")
                        .fullTextExpression("to be replaced", true).asTemplate()
                        .sort("r_object_id", false, null, null)
                        .build();
        SearchTemplate createdSearchTemplateToExecute = client.createSearchTemplate(new PlainSearchTemplate(search, "object_name", "my-search-template-to-execute"));
        print(client, createdSearchTemplateToExecute);
        FullTextVariable fullTextVariable = (FullTextVariable)createdSearchTemplateToExecute.getExternalVariables().get(0);
        fullTextVariable.setVariableValue("test");
        SearchFeed<RestObject> result = client.executeSearchTemplate(createdSearchTemplateToExecute, "items-per-page", "5");
        printSearchFeed(result);
        printNewLine();
        
        printStep("delete the created search templates");
        client.delete(createdSearchTemplateToExecute);
        printHttpStatus();
    }

    public void savedSearch() {
        printStep("create a saved search");
        SearchBuilder builder = SearchBuilder.builder(client);
        Search search = builder.allVersions(false)
                               .includeHiddenObjects(true)
                               .facetDefinition("facet", Arrays.asList("r_modify_date"), FacetDefinition.GROUP_BY_DATE_RELATIVE_DATE, FacetDefinition.SORT_VALUE_ASCENDING, 9)
                               .types("dm_sysobject")
                               .columns("r_object_id", "r_object_type", "object_name")
                               .propertyExpression("object_name", "test", PropertyExpression.OPERATOR_CONTAINS, false, false, false, true)
                               .propertyListExpression("object_name", Arrays.asList("test", "rest", "hello", "world"), PropertyListExpression.OPERATOR_NOT_IN, false)
                               .sort("r_modify_date", true, null, null)
                               .build();
        SavedSearch createdSavedSearch = client.createSavedSearch(new PlainSavedSearch(search, "object_name", "my-saved-search"));
        print(client, createdSavedSearch);
        printNewLine();
        
        printStep("get the single saved search " + createdSavedSearch.getObjectName());
        SavedSearch savedSearch = client.getSavedSearch(createdSavedSearch.self());
        print(client, savedSearch);
        printNewLine();
        
        printStep("update the saved search " + createdSavedSearch.getObjectName());
        search = builder.reset()
                        .facetDefinition("facet", Arrays.asList("r_modify_date"), FacetDefinition.GROUP_BY_DATE_RELATIVE_DATE, FacetDefinition.SORT_VALUE_ASCENDING, 9)
                        .types("dm_sysobject")
                        .columns("r_object_id", "r_object_type", "object_name")
                        .fullTextExpression("test", true)
                        .sort("r_modify_date", true, null, null)
                        .build();
        SavedSearch updatedSavedSearch = client.updateSavedSearch(createdSavedSearch, new PlainSavedSearch(search, "object_name", "updated-saved-search"));
        print(client, updatedSavedSearch);
        printNewLine();
        
        printStep("execute the saved search " + updatedSavedSearch.getObjectName());
        SearchFeed<RestObject> result = client.executeSavedSearch(updatedSavedSearch, "items-per-page", "5");
        printSearchFeed(result);
        printNewLine();
        
        printStep("enable and refresh the saved search result of " + updatedSavedSearch.getObjectName());
        result = client.enableSavedSearchResult(updatedSavedSearch);
        printSearchFeed(result);
        printNewLine();
        
        printStep("get the saved search result of " + updatedSavedSearch.getObjectName());
        result = client.getSavedSearchResult(updatedSavedSearch, "items-per-page", "5");
        printSearchFeed(result);
        printNewLine();
        
        printStep("disable the saved search result of " + updatedSavedSearch.getObjectName());
        client.disableSavedSearchResult(updatedSavedSearch);
        printHttpStatus();
        printNewLine();
        
        printStep("convert the saved search to a search template");
        SearchTemplate convertedSearchTemplate = client.createSearchTemplate(new PlainSearchTemplate(updatedSavedSearch.self()));
        print(client, convertedSearchTemplate);
        printNewLine();

        printStep("get all saved searches");
        Feed<SavedSearch> savedSearches = client.getSavedSearches();
        printEntryContentSrc(savedSearches);
        printNewLine();

        printStep("delete the saved search and the search template");
        client.delete(convertedSearchTemplate);
        printHttpStatus();
        client.delete(updatedSavedSearch);
        printHttpStatus();
        printNewLine();
    }
}
