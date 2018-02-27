/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;
import java.util.Map;

public interface RestProperty {
    public String getName();
    public String getType();
    public String getLabel();
    public int getLength();
    public boolean isRepeating();
    public boolean isHidden();
    public boolean isRequired();
    public boolean isNotNull();
    public boolean isReadOnly();
    public boolean isSearchable();
    public int getDisplayHint();
    public List<DefaultValue> getDefault();
    public List<String> getDependencies();
    public Map<String, Map<String, RestPropertyLifecycleInfo>> getPropertyLifecycles();
    public String getNotNullEnforce();
    public String getNotNullMessage();
    public List<RestTypeMappingTable> getMappingTables();
    public boolean isIgnoreConstraints();
    public boolean isIgnoreImmutable();
    public String getHelpText();
    public String getCommentText();
    public String getCategory();
    public List<RestTypeValueConstraint> getConstraints();
    public List<RestPropertyValueAssist> getValueAssist();
    
    public interface DefaultValue {
        public Object getExpression();
        public boolean isExpression();
    }
}
