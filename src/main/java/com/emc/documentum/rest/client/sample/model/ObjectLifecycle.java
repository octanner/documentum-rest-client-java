/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * represents the Object Lifecycle
 */
public interface ObjectLifecycle extends Linkable {
    public String getLifecycleId();
    public String getLifecycleName();
    public String getAliassetName();
    public String getCurrentState();
    public String getPreviousState();
    public String getNextState();
    public String getResumeState();
    public boolean isAbleToDemote();
    public boolean isAbleToPromote();
    public boolean isAbleToResume();
    public boolean isAbleToSuspend();
    public List<Schedule> getSchedules();

    public static interface Schedule {
        public String getAction();
        public String getSchedule();
        public String getFrom();
        public String getTo();
    }
}
