/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.batch.Attachment;
import com.emc.documentum.rest.client.sample.model.batch.Header;
import com.emc.documentum.rest.client.sample.model.batch.SettableRequest;

public class JsonBatchRequest implements SettableRequest {
    private String uri;
    private String method;
    private List<JsonBatchHeader> batchHeaders;
    private String entity;
    private JsonBatchAttachment attachment;
    
    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public JsonBatchAttachment getAttachment() {
        return attachment;
    }

    @Override
    public void setAttachment(Attachment attachment) {
        this.attachment = (JsonBatchAttachment)attachment;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<Header> getHeaders() {
        return (List)batchHeaders;
    }

    public void setHeaders(List<JsonBatchHeader> headers) {
        this.batchHeaders = headers;
    }
    
    @Override
    public void addHeader(Header header) {
        if(batchHeaders == null) {
            batchHeaders = new ArrayList<JsonBatchHeader>();
        }
        batchHeaders.add((JsonBatchHeader)header);
    }

    @Override
    public void setHeader(Header header) {
        if(batchHeaders == null) {
            addHeader(header);
        } else {
            boolean added = false;
            for(JsonBatchHeader h : batchHeaders) {
                if(h.getName().equalsIgnoreCase(header.getName())) {
                    h.setValue(header.getValue());
                    added = true;
                    break;
                }
            }
            if(!added) {
                addHeader(header);
            }
        }
    }
    
    @Override
    public String getEntity() {
        return entity;
    }

    @Override
    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object obj) {
        JsonBatchRequest that = (JsonBatchRequest)obj;
        return Equals.equal(uri, that.uri) &&
               Equals.equal(method, that.method) &&
               Equals.equal(attachment, that.attachment) &&
               Equals.equal(batchHeaders, that.batchHeaders) &&
               Equals.equal(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, method, entity, batchHeaders, attachment);
    }
}
