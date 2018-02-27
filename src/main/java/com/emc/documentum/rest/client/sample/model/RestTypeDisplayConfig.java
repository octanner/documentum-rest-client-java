/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

public interface RestTypeDisplayConfig {
    public String getId();
    public String getName();
    public String getAttributeSource();
    public boolean isFixedDisplay();
    public List<RestPropertyAttributeHint> getAttributeHints();
    
    public interface RestPropertyAttributeHint {
        String getAttribute();
        int getDisplayHint();
    }
}
