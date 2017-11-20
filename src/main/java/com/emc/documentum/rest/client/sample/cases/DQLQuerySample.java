/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.PAGING_NEXT;

@RestServiceSample("DQL Query")
public class DQLQuerySample extends Sample {
    public void dqlQuery() {
        printStep("execute dql 'select * from dm_cabinet' with 5 items per page");
        Feed<RestObject> queryResult = client.dql("select * from dm_user", "items-per-page", "5");
        for(Entry<RestObject> e : queryResult.getEntries()) {
            RestObject o = e.getContentObject();
            print(o, "r_object_id", "user_name");
        }
        System.out.println(queryResult.getHref(PAGING_NEXT));
        printNewLine();
        
        printStep("get the next page of dql 'select * from dm_cabinet' with 5 items per page");
        queryResult = client.nextPage(queryResult);
        for(Entry<RestObject> e : queryResult.getEntries()) {
            RestObject o = e.getContentObject();
            print(o, "r_object_id", "user_name");
        }
        printNewLine();
        
        printStep("execute dql 'select * from dm_format' with 5 items per page");
        queryResult = client.dql("select name,description from dm_format", "items-per-page", "5");
        for(Entry<RestObject> e : queryResult.getEntries()) {
            RestObject o = e.getContentObject();
            print(o, "name", "description");
        }
        printNewLine();

        printStep("get the next page of dql 'select * from dm_format' with 5 items per page");
        queryResult = client.nextPage(queryResult);
        for(Entry<RestObject> e : queryResult.getEntries()) {
            RestObject o = e.getContentObject();
            print(o, "name", "description");
        }
        printNewLine();
    }
}
