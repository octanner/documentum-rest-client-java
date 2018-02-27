/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestPropertyValueAssist;

@XmlRootElement(name="fixed-list")
public class JaxbPropertyValueAssistFixedValue implements RestPropertyValueAssist {
    private String condition;
    private List<String> values;
    private boolean allowUserValues;

    public String getType() {
        return "fixed-list";
    }

    @XmlAttribute
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @XmlElement(name="value")
    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public boolean isAllowCaching() {
        return false;
    }

    @XmlAttribute(name="allow-user-values")
    public boolean isAllowUserValues() {
        return allowUserValues;
    }

    public void setAllowUserValues(boolean allowUserValues) {
        this.allowUserValues = allowUserValues;
    }

    public String getQueryAttribute() {
        return null;
    }

    public String getQueryExpression() {
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbPropertyValueAssistFixedValue that = (JaxbPropertyValueAssistFixedValue)obj;
        return Equals.equal(condition, that.condition) 
            && Equals.equal(values, that.values)
            && Equals.equal(allowUserValues, that.allowUserValues);
    }
}