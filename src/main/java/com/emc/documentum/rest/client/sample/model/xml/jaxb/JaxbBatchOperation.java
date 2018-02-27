/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.batch.Batch;
import com.emc.documentum.rest.client.sample.model.batch.Request;
import com.emc.documentum.rest.client.sample.model.batch.Response;
import com.emc.documentum.rest.client.sample.model.batch.SettableOperation;

@XmlRootElement(name="operation")
public class JaxbBatchOperation implements SettableOperation {
    private String id;
    private String description;
    private Batch.Status state;
    private String started;
    private String finished;
    private JaxbBatchRequest batchRequest;
    private JaxbBatchResponse batchResponse;

    @Override
    @XmlAttribute
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
    public Batch.Status getState() {
        return state;
    }

    @Override
    public void setState(Batch.Status state) {
        this.state = state;
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
    @XmlTransient
    public JaxbBatchRequest getRequest() {
        return batchRequest;
    }

    @Override
    public void setRequest(Request request) {
        this.batchRequest = (JaxbBatchRequest)request;
    }
    
    @XmlElement(name="request")
    public JaxbBatchRequest getBatchRequest() {
        return batchRequest;
    }

    public void setBatchRequest(JaxbBatchRequest request) {
        this.batchRequest = request;
    }

    @Override
    @XmlTransient
    public JaxbBatchResponse getResponse() {
        return batchResponse;
    }

    @Override
    public void setResponse(Response response) {
        this.batchResponse = (JaxbBatchResponse)response;
    }

    @XmlElement(name="response")
    public JaxbBatchResponse getBatchResponse() {
        return batchResponse;
    }

    public void setBatchResponse(JaxbBatchResponse response) {
        this.batchResponse = response;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbBatchOperation that = (JaxbBatchOperation)obj;
        return Equals.equal(id, that.id) &&
               Equals.equal(description, that.description) &&
               Equals.equal(state, that.state) &&
               Equals.equal(started, that.started) &&
               Equals.equal(finished, that.finished) &&
               Equals.equal(batchRequest, that.batchRequest) &&
               Equals.equal(batchResponse, that.batchResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, state, batchRequest, batchResponse);
    }
}
