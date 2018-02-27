/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestPropertyValueAssist;

@XmlRootElement(name = "query")
public class JaxbPropertyValueAssistQuery implements RestPropertyValueAssist {
    private String condition;
    private boolean allowCaching;
    private boolean allowUserValues;
    private String queryAttribute;
    private String queryExpression;

    public String getType() {
        return "query";
    }

    @XmlAttribute
    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public List<String> getValues() {
        return null;
    }

    @XmlAttribute(name="allow-caching")
    public boolean isAllowCaching() {
        return allowCaching;
    }

    public void setAllowCaching(boolean allowCaching) {
        this.allowCaching = allowCaching;
    }

    @XmlAttribute(name="allow-user-values")
    public boolean isAllowUserValues() {
        return allowUserValues;
    }

    public void setAllowUserValues(boolean allowUserValues) {
        this.allowUserValues = allowUserValues;
    }

    @XmlAttribute(name="query-attribute")
    public String getQueryAttribute() {
        return queryAttribute;
    }

    public void setQueryAttribute(String queryAttribute) {
        this.queryAttribute = queryAttribute;
    }

    @XmlValue
    public String getQueryExpression() {
        return queryExpression;
    }

    public void setQueryExpression(String queryExpression) {
        this.queryExpression = queryExpression;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbPropertyValueAssistQuery that = (JaxbPropertyValueAssistQuery)obj;
        return Equals.equal(condition, that.condition)
            && Equals.equal(allowCaching, that.allowCaching)
            && Equals.equal(allowUserValues, that.allowUserValues)
            && Equals.equal(queryAttribute, that.queryAttribute)
            && Equals.equal(queryExpression, that.queryExpression);
    }
}