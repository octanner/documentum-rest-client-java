/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.List;
import java.util.Map;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestProperty;
import com.emc.documentum.rest.client.sample.model.RestPropertyLifecycleInfo;
import com.emc.documentum.rest.client.sample.model.RestPropertyValueAssist;
import com.emc.documentum.rest.client.sample.model.RestType;

import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printLinks;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Type(s)")
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
        RestType type = client.getType("dm_document", "include-all", "true");
        printLinks(type);
        System.out.println(type.getName() + " " + type.getCategory() + " " + type.getParent()
                + (type.getSharedParent() == null ? "" : type.getSharedParent()));
        for(RestProperty p : type.getProperties()) {
            System.out.println(p.getName() + "(" + p.getType() + ")");
            printVa(p.getValueAssist());
            if(p.getPropertyLifecycles() != null) {
                for(java.util.Map.Entry<String, Map<String, RestPropertyLifecycleInfo>> le : p.getPropertyLifecycles().entrySet()) {
                    for(java.util.Map.Entry<String, RestPropertyLifecycleInfo> se : le.getValue().entrySet()) {
                        System.out.println("\t" + le.getKey() + "->" + se.getKey());
                        printVa(se.getValue().getValueAssist());
                    }
                }
            }
        }
        printNewLine();
        
        printStep("get properties by the display config for the webtop#Info");
        type = client.getType("dm_document", "scope-config", "webtop", "display-config", "Info");
        for(RestProperty p : type.getProperties()) {
            System.out.println(p.getName() + "(" + p.getType() + ")" + " diplay hint: " + p.getDisplayHint());
        }
        printNewLine();
    }
    
    private static void printVa(List<RestPropertyValueAssist> vas) {
        if(vas != null) {
            System.out.println("value assist definition");
            for(RestPropertyValueAssist va : vas) {
                if(va.getType().equals("query")) {
                    System.out.println(va.getType() + "(" + va.getCondition() + "): " + va.getQueryAttribute() + "[" + va.getQueryExpression() + "]");
                } else {
                    System.out.println(va.getType() + "(" + va.getCondition() + "): " + va.getValues());
                }
            }
        }
    }
}
