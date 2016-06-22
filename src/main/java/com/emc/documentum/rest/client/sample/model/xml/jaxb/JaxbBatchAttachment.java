/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.io.InputStream;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.batch.Include;
import com.emc.documentum.rest.client.sample.model.batch.SettableAttachment;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="attachment", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbBatchAttachment implements SettableAttachment {
    private JaxbBatchInclude include;
    private String contentType;
    private InputStream contentStream;

    public JaxbBatchAttachment() {
    }
    
    public JaxbBatchAttachment(JaxbBatchInclude include) {
        this.include = include;
    }

    @Override
    @XmlElement(name="Include", namespace=XMLNamespace.XOP_NAMESPACE)
    public JaxbBatchInclude getInclude() {
        return include;
    }

    @Override
    public void setInclude(Include include) {
        this.include = (JaxbBatchInclude)include;
    }

    @Override
    @XmlTransient
    public String getContentType() {
        return contentType;
    }

    @Override
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    @XmlTransient
    public InputStream getContentStream() {
        return contentStream;
    }

    @Override
    public void setContentStream(InputStream contentStream) {
        this.contentStream = contentStream;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbBatchAttachment that = (JaxbBatchAttachment)obj;
        return Equals.equal(include, that.include);
    }

    @Override
    public int hashCode() {
        return Objects.hash(include);
    }
}
