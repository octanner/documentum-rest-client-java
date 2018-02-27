/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.ObjectLifecycle;

@XmlRootElement(name = "object-lifecycle")
public class JaxbObjectLifecycle extends JaxbDmLinkableBase implements ObjectLifecycle {
    private String lifecycleId;
    private String lifecycleName;
    private String aliassetName;
    private String currentState;
    private String previousState;
    private String nextState;
    private String resumeState;
    private boolean ableToDemote;
    private boolean ableToPromote;
    private boolean ableToResume;
    private boolean ableToSuspend;
    private List<Schedule> schedules;
    
    public JaxbObjectLifecycle(ObjectLifecycle objectLifecycle) {
        lifecycleId = objectLifecycle.getLifecycleId();
        lifecycleName = objectLifecycle.getLifecycleName();
        aliassetName = objectLifecycle.getAliassetName();
        currentState = objectLifecycle.getCurrentState();
        previousState = objectLifecycle.getPreviousState();
        nextState = objectLifecycle.getNextState();
        resumeState = objectLifecycle.getResumeState();
        ableToDemote = objectLifecycle.isAbleToDemote();
        ableToPromote = objectLifecycle.isAbleToPromote();
        ableToResume = objectLifecycle.isAbleToResume();
        ableToSuspend = objectLifecycle.isAbleToSuspend();
        this.schedules = objectLifecycle.getSchedules();
    }
    
    public JaxbObjectLifecycle() {
    }

    @Override
    @XmlElement(name="lifecycle-id")
    public String getLifecycleId() {
        return lifecycleId;
    }

    public void setLifecycleId(String lifecycleId) {
        this.lifecycleId = lifecycleId;
    }

    @Override
    @XmlElement(name="lifecycle-name")
    public String getLifecycleName() {
        return lifecycleName;
    }

    public void setLifecycleName(String lifecycleName) {
        this.lifecycleName = lifecycleName;
    }

    @Override
    @XmlElement(name="aliasset-name")
    public String getAliassetName() {
        return aliassetName;
    }

    public void setAliassetName(String aliassetName) {
        this.aliassetName = aliassetName;
    }

    @Override
    @XmlElement(name="current-state")
    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    @Override
    @XmlElement(name="previous-state")
    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    @Override
    @XmlElement(name="next-state")
    public String getNextState() {
        return nextState;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }

    @Override
    @XmlElement(name="resume-state")
    public String getResumeState() {
        return resumeState;
    }

    public void setResumeState(String resumeState) {
        this.resumeState = resumeState;
    }

    @Override
    @XmlElement(name="able-to-demote")
    public boolean isAbleToDemote() {
        return ableToDemote;
    }

    public void setAbleToDemote(boolean ableToDemote) {
        this.ableToDemote = ableToDemote;
    }

    @Override
    @XmlElement(name="able-to-promote")
    public boolean isAbleToPromote() {
        return ableToPromote;
    }

    public void setAbleToPromote(boolean ableToPromote) {
        this.ableToPromote = ableToPromote;
    }

    @Override
    @XmlElement(name="able-to-resume")
    public boolean isAbleToResume() {
        return ableToResume;
    }

    public void setAbleToResume(boolean ableToResume) {
        this.ableToResume = ableToResume;
    }

    @Override
    @XmlElement(name="able-to-suspend")
    public boolean isAbleToSuspend() {
        return ableToSuspend;
    }

    public void setAbleToSuspend(boolean ableToSuspend) {
        this.ableToSuspend = ableToSuspend;
    }

    @Override
    @XmlElementWrapper(name="schedules")
    @XmlElement(name="schedule", type=JaxbSchedule.class)
    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbObjectLifecycle that = (JaxbObjectLifecycle) obj;
        return Equals.equal(lifecycleId, that.lifecycleId)
                && Equals.equal(lifecycleName, that.lifecycleName)
                && Equals.equal(aliassetName, that.aliassetName)
                && Equals.equal(currentState, that.currentState)
                && Equals.equal(previousState, that.previousState)
                && Equals.equal(nextState, that.nextState)
                && Equals.equal(resumeState, that.resumeState)
                && Equals.equal(ableToDemote, that.ableToDemote)
                && Equals.equal(ableToPromote, that.ableToPromote)
                && Equals.equal(ableToResume, that.ableToResume)
                && Equals.equal(ableToSuspend, that.ableToSuspend)
                && Equals.equal(schedules, that.schedules)
                && super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(lifecycleId, lifecycleName, currentState);
    }
    
    @XmlRootElement(name = "type-inclusion")
    public static class JaxbSchedule implements Schedule {
        private String action;
        private String schedule;
        private String from;
        private String to;
        @Override
        public String getAction() {
            return action;
        }
        public void setAction(String action) {
            this.action = action;
        }
        @Override
        public String getSchedule() {
            return schedule;
        }
        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }
        @Override
        public String getFrom() {
            return from;
        }
        public void setFrom(String from) {
            this.from = from;
        }
        @Override
        public String getTo() {
            return to;
        }
        public void setTo(String to) {
            this.to = to;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbSchedule that = (JaxbSchedule) obj;
            return Equals.equal(action, that.action)
                    && Equals.equal(schedule, that.schedule)
                    && Equals.equal(from, that.from)
                    && Equals.equal(to, that.to);
        }
    }
}