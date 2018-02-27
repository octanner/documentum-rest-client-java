/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.Date;
import java.util.List;

/**
 * represents the Lifecycle
 */
public interface Lifecycle extends Linkable {
    public String getId();
    public String getName();
    public String getTitle();
    public String getSubject();
    public List<String> getAliasSets();
    public List<TypeInclusion> getTypeInclusions();
    public List<String> getKeywords();
    public String getOwner();
    public Date getCreated();
    public Date getModified();
    public String getImplementationType();
    public List<String> getVersionLabels();
    public List<LifecycleState> getStates();
    public Module getUserValidationService();
    public Procedure getAppValidation();
    public String getStatus();
    public String getStatusMessage();
        
    public static interface TypeInclusion {
        public String getType();
        public boolean isIncludeSubtypes();
    }
    
    public static interface EntryCriteria {
        public String getId();
        public String getExpression();
    }
    
    public static interface Procedure {
        public String getId();
        public String getName();
        public String getVersion();
    }
    
    public static interface Module {
        public String getId();
        public String getName();
        public String getPrimaryClass();
    }

    public static interface LifecycleState {
        public String getName();
        public String getType();
        public int getNo();
        public int getIndex();
        public String getDescription();
        public boolean isExceptional();
        public String getExceptionState();
        public boolean isAllowAttach();
        public boolean isAllowSchedule();
        public boolean isAllowReturnToBase();
        public boolean isAllowDemote();
        public List<String> getReturnConditions();
        public EntryCriteria getEntryCriteria();
        public Procedure getUserCriteria();
        public Procedure getAction();
        public Procedure getUserAction();
        public Procedure getUserPostAction();
        public String getTypeOverrideId();
        public Module getUserCriteriaService();
        public Module getUserActionService();
        public Module getUserPostService();
        public Module getSystemAction();
    }
}
