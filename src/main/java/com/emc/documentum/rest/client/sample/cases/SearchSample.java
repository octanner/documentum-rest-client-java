/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Arrays;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.Search.FacetDefinition;
import com.emc.documentum.rest.client.sample.model.SearchFeed;
import com.emc.documentum.rest.client.sample.model.builder.SearchBuilder;

import static com.emc.documentum.rest.client.sample.client.util.Debug.debugSerialize;
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
}
