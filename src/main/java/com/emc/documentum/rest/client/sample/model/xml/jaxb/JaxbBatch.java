/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.batch.Operation;
import com.emc.documentum.rest.client.sample.model.batch.SettableBatch;

@XmlRootElement(name="batch")
public class JaxbBatch implements SettableBatch {
    private String id;
    private String description;
    private String started;
    private String finished;
    private String submitted;
    private String owner;
    private Status state;
    private Status substate;
    private Boolean transactional;
    private Boolean sequential;
    private Boolean returnRequest;
    private OnError onError;
    private List<Operation> operations;
    
    @Override
    @XmlElement
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    @XmlElement
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    @XmlElement
    public String getStarted() {
        return started;
    }

    @Override
    public void setStarted(String started) {
        this.started = started;
    }

    @Override
    @XmlElement
    public String getFinished() {
        return finished;
    }

    @Override
    public void setFinished(String finished) {
        this.finished = finished;
    }

    @Override
    @XmlElement
    public String getSubmitted() {
        return submitted;
    }

    @Override
    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    @Override
    @XmlElement
    public String getOwner() {
        return owner;
    }

    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    @XmlElement
    public Status getState() {
        return state;
    }

    @Override
    public void setState(Status state) {
        this.state = state;
    }

    @Override
    @XmlElement
    public Status getSubstate() {
        return substate;
    }

    @Override
    public void setSubstate(Status substate) {
        this.substate = substate;
    }

    @Override
    @XmlElement
    public Boolean getTransactional() {
        return transactional;
    }
    
    @Override
    public void setTransactional(Boolean transactional) {
        this.transactional = transactional;
    }

    @Override
    @XmlElement
    public Boolean getSequential() {
        return sequential;
    }

    @Override
    public void setSequential(Boolean sequential) {
        this.sequential = sequential;
    }

    @Override
    @XmlElement(name="return-request")
    public Boolean getReturnRequest() {
        return returnRequest;
    }

    @Override
    public void setReturnRequest(Boolean returnRequest) {
        this.returnRequest = returnRequest;
    }

    @Override
    @XmlElement(name="on-error")
    public OnError getOnError() {
        return onError;
    }

    @Override
    public void setOnError(OnError onError) {
        this.onError = onError;
    }

    @Override
    @XmlElementWrapper
    @XmlElement(name="operation", type = JaxbBatchOperation.class) 
    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    @Override
    public void addOperation(Operation op) {
        if(operations == null) {
            operations = new ArrayList<>();
        }
        operations.add(op);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public boolean hasAttachment() {
        for(Operation o : operations) {
            if(o.getRequest().getAttachment() != null) {
                return true;
            }
            if(o.getRequest().getAttachments() != null && !o.getRequest().getAttachments().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbBatch that = (JaxbBatch)obj;
        return Equals.equal(id, that.id) &&
               Equals.equal(description, that.description) &&
               Equals.equal(started, that.started) &&
               Equals.equal(finished, that.finished) &&
               Equals.equal(submitted, that.submitted) &&
               Equals.equal(owner, that.owner) &&
               Equals.equal(state, that.state) &&
               Equals.equal(substate, that.substate) &&
               Equals.equal(transactional, that.transactional) &&
               Equals.equal(sequential, that.sequential) &&
               Equals.equal(returnRequest, that.returnRequest) &&
               Equals.equal(operations, that.operations) &&
               Equals.equal(onError, that.onError);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, state, substate, transactional, sequential, onError, operations);
    }
}
