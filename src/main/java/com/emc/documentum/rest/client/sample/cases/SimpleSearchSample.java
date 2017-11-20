/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.SearchFeed;

import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printSearchFeed;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;

@RestServiceSample("Search (Simple)")
@RestServiceVersion(7.2)
public class SimpleSearchSample extends Sample {
    public void search() {
        printStep("simple search");
        String searchValue = read("Please input the value you want to search:");
        SearchFeed<RestObject> result = client.search(searchValue);
        printSearchFeed(result);
        printNewLine();

        printStep("simple search with facet");
        result = client.search(searchValue, "facet", "r_modify_date");
        printSearchFeed(result);
        printNewLine();
        
        printStep("simple search with inline result");
        result = client.search(searchValue, "inline", "true");
        printSearchFeed(result);
        
        printNewLine();
    }
}
