/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.batch.Attachment;
import com.emc.documentum.rest.client.sample.model.batch.Header;
import com.emc.documentum.rest.client.sample.model.batch.SettableRequest;

@XmlRootElement(name="request")
public class JaxbBatchRequest implements SettableRequest {
    private String uri;
    private String method;
    private List<JaxbBatchHeader> headers;
    private String entity;
    private JaxbBatchAttachment attachment;
    private List<JaxbBatchAttachment> attachments;
        
    @Override
    @XmlAttribute
    public String getUri() {
        return uri;
    }

    @Override
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    @XmlAttribute
    public String getMethod() {
        return method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    @XmlElement
    public JaxbBatchAttachment getAttachment() {
        return attachment;
    }

    @Override
    public void setAttachment(Attachment attachment) {
        this.attachment = (JaxbBatchAttachment)attachment;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @XmlElementWrapper
    @XmlElement(name = "attachment", type = JaxbBatchAttachment.class)
    public List<Attachment> getAttachments() {
        return (List)attachments;
    }

    public void setAttachments(List<JaxbBatchAttachment> attachments) {
        this.attachments = attachments;
    }
    
    @Override
    public void addAttachment(Attachment attachment) {
        if(attachments == null) {
            attachments = new ArrayList<>();
        }
        attachments.add((JaxbBatchAttachment)attachment);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    @XmlTransient
    public List<Header> getHeaders() {
        return (List)headers;
    }
    
    @Override
    public void addHeader(Header header) {
        if(headers == null) {
            headers = new ArrayList<JaxbBatchHeader>();
        }
        headers.add((JaxbBatchHeader)header);
    }
    
    @Override
    public void setHeader(Header header) {
        if(headers == null) {
            addHeader(header);
        } else {
            boolean added = false;
            for(JaxbBatchHeader h : headers) {
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

    @Override
    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbBatchRequest that = (JaxbBatchRequest)obj;
        return Equals.equal(uri, that.uri) &&
               Equals.equal(method, that.method) &&
               Equals.equal(attachment, that.attachment) &&
               Equals.equal(headers, that.headers) &&
               Equals.equal(entity, that.entity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri, method, entity, headers, attachment);
    }
}
