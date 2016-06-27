/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Facet;
import com.emc.documentum.rest.client.sample.model.FacetValue;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.SearchEntry;
import com.emc.documentum.rest.client.sample.model.SearchFeed;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;

@RestServiceSample("Search")
@RestServiceVersion(7.2)
public class SimpleSearchSample extends Sample {
    public void search() {
        printStep("simple search");
        String searchValue = read("Please input the value you want to search:");
        SearchFeed<RestObject> result = client.search(searchValue);
        for(SearchEntry<RestObject> e : result.getEntries()) {
            System.out.println(e.getTitle() + " -> " + e.getContentSrc());
            System.out.println("score:" + e.getScore() + ", terms:" + e.getTerms());
        }
        printNewLine();

        printStep("simple search with facet");
        result = client.search(searchValue, "facet", "r_modify_date");
        for(SearchEntry<RestObject> e : result.getEntries()) {
            System.out.println(e.getTitle() + " -> " + e.getContentSrc());
            System.out.println("score:" + e.getScore() + ", terms:" + e.getTerms());
        }
        for(Facet f : result.getFacets()) {
            System.out.println("facet id:" + f.getId() + ", facet label:" + f.getLabel());
            for(FacetValue fv : f.getValues()) {
                System.out.println("facet-id:" + fv.getFacetId() + ", facet-value-id:" + fv.getId()
                    + ", facet-value-count:" + fv.getCount() + "facet-value-constraint:" + fv.getConstraint());
                print(fv);
            }
        }
        printNewLine();
        
        printStep("simple search with inline result");
        result = client.search(searchValue, "inline", "true");
        for(SearchEntry<RestObject> e : result.getEntries()) {
            System.out.println("score:" + e.getScore() + ", terms:" + e.getTerms());
            print(e.getContentObject());
        }
        printNewLine();
    }
}
