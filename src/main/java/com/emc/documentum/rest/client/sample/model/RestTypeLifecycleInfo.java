/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

public interface RestTypeLifecycleInfo {
    public String getLabel();
    public String getDefaultLifecycle();
    public String getDefaultLifecycleVersion();
    public boolean getIgnoreConstraints();
    public List<RestTypeValueConstraint> getConstraints();
}
