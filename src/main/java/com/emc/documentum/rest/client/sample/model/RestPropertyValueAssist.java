/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

public interface RestPropertyValueAssist {
    public String getType();
    public String getCondition();
    public List<String> getValues();
    public boolean isAllowCaching();
    public boolean isAllowUserValues();
    public String getQueryAttribute();
    public String getQueryExpression();
}
