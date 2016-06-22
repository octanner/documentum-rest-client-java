/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.batch.SettableHeader;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="header", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbBatchHeader implements SettableHeader {
    private String name;
    private String value;
    
    public JaxbBatchHeader() {
    }
    
    public JaxbBatchHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    @XmlAttribute
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    @XmlAttribute
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbBatchHeader that = (JaxbBatchHeader)obj;
        return Equals.equal(name, that.name) &&
               Equals.equal(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
    
    @Override
    public String toString() {
        return name + "=" + value;
    }
}
