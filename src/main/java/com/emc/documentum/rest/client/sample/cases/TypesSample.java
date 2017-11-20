/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Map;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestType;

import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printLinks;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Retrieve Type(s)")
public class TypesSample extends Sample {
    public void type() {
        printStep("get types");
        Feed<RestType> types = client.getTypes();
        printEntryContentSrc(types);
        printNewLine();
        
        printStep("get inline types");
        types = client.getTypes("inline", "true", "items-per-page", "1");
        for(Entry<RestType> e : types.getEntries()) {
            System.out.println(e.getContentObject().getName() + " " + e.getContentObject().getCategory());
        }
        printNewLine();
        
        printStep("get type resource");
        RestType type = client.getType("dm_document");
        printLinks(type);
        System.out.println(type.getName() + " " + type.getCategory() + " " + type.getParent()
                + (type.getSharedParent() == null ? "" : type.getSharedParent()));
        for(Map<String, Object> map : type.getProperties()) {
            System.out.println(map.get("name") + ": " + map);
        }
        printNewLine();
    }
}
