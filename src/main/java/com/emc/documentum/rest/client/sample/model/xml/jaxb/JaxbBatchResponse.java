/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.batch.Header;
import com.emc.documentum.rest.client.sample.model.batch.Response;

@XmlRootElement(name="response")
public class JaxbBatchResponse implements Response {
    private int status;
    private List<JaxbBatchHeader> headers;
    private String entity;

    @Override
    @XmlAttribute
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    @XmlTransient
    public List<Header> getHeaders() {
        return (List)headers;
    }

    @Override
//    @XmlTransient
    public String getHeader(String header) {
        if(headers != null) {
            for(Header h : headers) {
                if(h.getName().equalsIgnoreCase(header)) {
                    return h.getValue();
                }
            }
        }
        return null;
    }
    
    @XmlElement(name="header")
    public List<JaxbBatchHeader> getBatchHeaders() {
        return headers;
    }

    public void setBatchHeaders(List<JaxbBatchHeader> headers) {
        this.headers = headers;
    }

    @Override
    @XmlElement
    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbBatchResponse that = (JaxbBatchResponse)obj;
        return Equals.equal(status, that.status) &&
               Equals.equal(headers, that.headers) &&
               Equals.equal(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity, status, headers);
    }
}
