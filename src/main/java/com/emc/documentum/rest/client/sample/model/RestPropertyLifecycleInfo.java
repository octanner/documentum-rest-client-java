/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

public interface RestPropertyLifecycleInfo {
    public String getLabel();
    public boolean isHidden();
    public boolean isRequired();
    public boolean isNotNull();
    public String getNotNullEnforce();
    public String getNotNullMessage();
    public boolean isReadOnly();
    public boolean isSearchable();
    public boolean isIgnoreConstraints();
    public boolean isIgnoreImmutable();
    public List<String> getDependencies();
    public List<RestTypeValueConstraint> getConstraints();
    public List<RestPropertyValueAssist> getValueAssist();
}
