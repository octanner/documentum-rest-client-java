/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.plain;

import java.util.Arrays;
import java.util.List;

import com.emc.documentum.rest.client.sample.model.ObjectAspects;

/**
 * the plain ObjectAspects implementation
 * which is used to attach the aspects to the object only 
 */
public class PlainObjectAspect extends PlainLinkableBase implements ObjectAspects {
    private List<String> aspects;
    
    public PlainObjectAspect() {
    }
    
    public PlainObjectAspect(String... aspects) {
        this.aspects = Arrays.asList(aspects);
    }

    public List<String> getAspects() {
        return aspects;
    }

    public void setAspects(List<String> aspects) {
        this.aspects = aspects;
    }
}
