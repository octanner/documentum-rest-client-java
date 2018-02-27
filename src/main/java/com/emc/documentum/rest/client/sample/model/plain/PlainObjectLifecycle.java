/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.plain;

import java.util.List;

import com.emc.documentum.rest.client.sample.model.ObjectLifecycle;

/**
 * the plain ObjectLifecycle implementation
 * which is used to attach the lifecycle to the object only 
 */
public class PlainObjectLifecycle extends PlainLinkableBase implements ObjectLifecycle {
    private String lifecycleId;
    private String aliassetName;
    private String currentState;

    public PlainObjectLifecycle (String lifecycleId) {
        this(lifecycleId, null, null);
    }
    
    public PlainObjectLifecycle (String lifecycleId, String currentState) {
        this(lifecycleId, currentState, null);
    }
    
    public PlainObjectLifecycle(String lifecycleId, String currentState, String aliassetName) {
        this.lifecycleId = lifecycleId;
        this.aliassetName = aliassetName;
        this.currentState = currentState;
    }

    @Override
    public String getLifecycleId() {
        return lifecycleId;
    }

    @Override
    public String getLifecycleName() {
        return null;
    }

    @Override
    public String getAliassetName() {
        return aliassetName;
    }

    @Override
    public String getCurrentState() {
        return currentState;
    }

    @Override
    public String getPreviousState() {
        return null;
    }

    @Override
    public String getNextState() {
        return null;
    }

    @Override
    public String getResumeState() {
        return null;
    }

    @Override
    public boolean isAbleToDemote() {
        return false;
    }

    @Override
    public boolean isAbleToPromote() {
        return false;
    }

    @Override
    public boolean isAbleToResume() {
        return false;
    }

    @Override
    public boolean isAbleToSuspend() {
        return false;
    }

    @Override
    public List<Schedule> getSchedules() {
        return null;
    }
}
