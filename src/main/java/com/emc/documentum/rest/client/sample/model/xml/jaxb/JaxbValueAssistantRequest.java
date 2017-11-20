/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.w3c.dom.Element;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.ValueAssistantRequest;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="assist-value-request")
public class JaxbValueAssistantRequest implements ValueAssistantRequest {
    @XmlTransient
    protected Map<String, Object> properties;
 
    @XmlElement(name="properties")
    protected ValueAssistantRequestProperties requestProperties;
    
    public JaxbValueAssistantRequest() {
    }

    public JaxbValueAssistantRequest(ValueAssistantRequest object) {
        setProperties(object.getProperties());
    }
    
    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    @SuppressWarnings("unchecked")
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
        if(properties != null) {
            requestProperties = new ValueAssistantRequestProperties();
            List<Element> list = new ArrayList<Element>(properties.size());
            for(Map.Entry<String, Object> entry : properties.entrySet()) {
                if(entry.getValue() instanceof List) {
                    list.add(new RepeatingPropertyElement(entry.getKey(), (List<String>)entry.getValue()));
                } else {
                    list.add(new SinglePropertyElement(entry.getKey(), (String)entry.getValue()));
                }
            }
            requestProperties.setElements(list);
        }
    }

    @XmlElement(name="properties")
    protected ValueAssistantRequestProperties getObjectProperties() {
        return requestProperties;
    }

    protected void setObjectProperties(ValueAssistantRequestProperties objectProperties) {
        this.requestProperties = objectProperties;
    }
    
    @XmlRootElement(name = "properties")
    static class ValueAssistantRequestProperties {
        private List<Element> elements;
        private String propertiesType;
        
        @XmlAnyElement
        public List<Element> getElements() {
            return elements;
        }

        public void setElements(List<Element> elements) {
            this.elements = elements;
        }
        
        @XmlAttribute(name="type", namespace=XMLNamespace.SCHEMA_INSTANCE_NAMESPACE)
        public String getPropertiesType() {
            return propertiesType;
        }

        public void setPropertiesType(String propertiesType) {
            this.propertiesType = propertiesType;
        }
        
        @Override
        public boolean equals(Object obj) {
            ValueAssistantRequestProperties that = (ValueAssistantRequestProperties) obj;
            return Equals.equal(propertiesType, that.propertiesType)
                    && Equals.equal(elements, that.elements);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbValueAssistantRequest that = (JaxbValueAssistantRequest) obj;
        return Equals.equal(getProperties(), that.getProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }
}
