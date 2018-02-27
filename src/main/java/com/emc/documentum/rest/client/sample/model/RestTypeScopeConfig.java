/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

public interface RestTypeScopeConfig {
    public String getId();
    public List<String> getScopes();
    public List<String> getCategories();
    public List<RestTypeDisplayConfig> getDisplayConfigs();
}
