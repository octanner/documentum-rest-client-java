/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

import com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbContext;
import com.emc.documentum.rest.client.sample.client.util.Collections;
import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestProperty;
import com.emc.documentum.rest.client.sample.model.RestPropertyLifecycleInfo;
import com.emc.documentum.rest.client.sample.model.RestPropertyValueAssist;
import com.emc.documentum.rest.client.sample.model.RestTypeMappingTable;
import com.emc.documentum.rest.client.sample.model.RestTypeValueConstraint;

public class JaxbProperty implements RestProperty {
    private String name;
    private String type;
    private String label;
    private int length;
    private boolean repeating;
    private boolean hidden;
    private boolean required;
    private boolean notNull;
    private boolean readOnly;
    private boolean searchable;
    private int displayHint;
    private String defaultLiteral;
    private String defaultExpression;
    private List<JaxbDefaultValue> defaultList;
    private List<String> dependencies;
    private String notNullEnforce;
    private String notNullMessage;
    private List<RestTypeMappingTable> mappingTables;
    private boolean ignoreConstraints;
    private boolean ignoreImmutable;
    private List<RestTypeValueConstraint> constraints;
    private String helpText;
    private String commentText;
    private String category;
    private List<JaxbTypeLifecycleBased> lifecycles;
    private List<Element> valueAssistElement;
    
    @XmlAttribute(name="default-literal")
    public String getDefaultLiteral() {
        return defaultLiteral;
    }

    public void setDefaultLiteral(String defaultLiteral) {
        this.defaultLiteral = defaultLiteral;
    }

    @XmlAttribute(name="default-expression")
    public String getDefaultExpression() {
        return defaultExpression;
    }

    public void setDefaultExpression(String defaultExpression) {
        this.defaultExpression = defaultExpression;
    }

    @XmlElement(name="default-value", type=JaxbDefaultValue.class)
    public List<JaxbDefaultValue> getDefaultList() {
        return defaultList;
    }

    public void setDefaultList(List<JaxbDefaultValue> defaultList) {
        this.defaultList = defaultList;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<DefaultValue> getDefault() {
        if(Collections.isNotEmpty(defaultList)) {
            return (List)defaultList;
        } else if(StringUtils.isEmpty(defaultExpression) && StringUtils.isEmpty(defaultLiteral)) {
            return null;
        } else {
            return Arrays.asList((DefaultValue)new JaxbDefaultValue(defaultLiteral, defaultExpression));
        }
    }

    public void setDefault(List<JaxbDefaultValue> defaultValue) {
        if(Collections.isNotEmpty(defaultValue)) {
            if(defaultValue.size() == 1) {
                if(defaultValue.get(0).isExpression()) {
                    defaultExpression  = defaultValue.get(0).getExpression();
                } else {
                    defaultLiteral = defaultValue.get(0).getExpression();
                }
            } else {
                defaultList = defaultValue;
            }
        } else {
            defaultList = null;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public void setDisplayHint(int displayHint) {
        this.displayHint = displayHint;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public void setNotNullEnforce(String notNullEnforce) {
        this.notNullEnforce = notNullEnforce;
    }

    public void setNotNullMessage(String notNullMessage) {
        this.notNullMessage = notNullMessage;
    }

    public void setIgnoreConstraints(boolean ignoreConstraints) {
        this.ignoreConstraints = ignoreConstraints;
    }

    public void setIgnoreImmutable(boolean ignoreImmutable) {
        this.ignoreImmutable = ignoreImmutable;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    @XmlAttribute
    public String getName() {
        return name;
    }

    @Override
    @XmlAttribute
    public String getType() {
        return type;
    }

    @Override
    @XmlAttribute
    public String getLabel() {
        return label;
    }

    @Override
    @XmlAttribute
    public int getLength() {
        return length;
    }

    @Override
    @XmlAttribute
    public boolean isRepeating() {
        return repeating;
    }

    @Override
    @XmlAttribute
    public boolean isHidden() {
        return hidden;
    }

    @Override
    @XmlAttribute
    public boolean isRequired() {
        return required;
    }

    @Override
    @XmlAttribute(name="notnull")
    public boolean isNotNull() {
        return notNull;
    }

    @Override
    @XmlAttribute(name="readonly")
    public boolean isReadOnly() {
        return readOnly;
    }

    @Override
    @XmlAttribute
    public boolean isSearchable() {
        return searchable;
    }

    @Override
    @XmlAttribute(name="display-hint")
    public int getDisplayHint() {
        return displayHint;
    }

    @Override
    @XmlElementWrapper(name="dependencies")
    @XmlElement(name="item")
    public List<String> getDependencies() {
        return dependencies;
    }

    @Override
    public Map<String, Map<String, RestPropertyLifecycleInfo>> getPropertyLifecycles() {
        if(Collections.isEmpty(lifecycles)) {
            return null;
        }
        Map<String, Map<String, RestPropertyLifecycleInfo>> map = new HashMap<>();
        for(JaxbTypeLifecycleBased lbased : lifecycles) {
            Map<String, RestPropertyLifecycleInfo> stateMap = map.get(lbased.getId());
            if(stateMap == null) {
                stateMap = new HashMap<>();
                map.put(lbased.getId(), stateMap);
            }
            for(JaxbTypeStateBased sbased : lbased.getStates()) {
                stateMap.put(sbased.getName(), sbased.getPropertyLifecycle());
            }
        }
        return map;
    }

    @Override
    @XmlElementWrapper(name="mapping-tables")
    @XmlElement(name="mapping-table", type=JaxbTypeMappingTable.class)
    public List<RestTypeMappingTable> getMappingTables() {
        return mappingTables;
    }

    @Override
    @XmlAttribute(name="ignore-parent-constraints")
    public boolean isIgnoreConstraints() {
        return ignoreConstraints;
    }

    @Override
    @XmlElementWrapper(name="constraints")
    @XmlElement(name="constraint", type=JaxbTypeValueConstraint.class)
    public List<RestTypeValueConstraint> getConstraints() {
        return constraints;
    }

    @Override
    @XmlElement(name="help-text")
    public String getHelpText() {
        return helpText;
    }

    @Override
    @XmlElement(name="comment-text")
    public String getCommentText() {
        return commentText;
    }

    @Override
    @XmlAttribute
    public String getCategory() {
        return category;
    }

    @Override
    @XmlAttribute(name="ignore-immutable")
    public boolean isIgnoreImmutable() {
        return ignoreImmutable;
    }

    @Override
    @XmlAttribute(name="notnull-enforce")
    public String getNotNullEnforce() {
        return notNullEnforce;
    }

    @Override
    @XmlAttribute(name="notnull-message")
    public String getNotNullMessage() {
        return notNullMessage;
    }
    
    @XmlElementWrapper(name="lifecycles")
    @XmlElement(name="lifecycle", type=JaxbTypeLifecycleBased.class)
    public List<JaxbTypeLifecycleBased> getLifecycles() {
        return lifecycles;
    }

    public void setLifecycles(List<JaxbTypeLifecycleBased> lifecycles) {
        this.lifecycles = lifecycles;
    }

    public void setMappingTables(List<RestTypeMappingTable> mappingTables) {
        this.mappingTables = mappingTables;
    }

    public void setConstraints(List<RestTypeValueConstraint> constraints) {
        this.constraints = constraints;
    }

    @XmlElementWrapper(name="value-assist")
    @XmlAnyElement
    public List<Element> getValueAssistElement() {
        return valueAssistElement;
    }

    public void setValueAssistElement(List<Element> valueAssistElement) {
        this.valueAssistElement = valueAssistElement;
    }

    @Override
    public List<RestPropertyValueAssist> getValueAssist() {
        if(valueAssistElement == null) {
            return null;
        }
        List<RestPropertyValueAssist> valueAssist = new ArrayList<>(valueAssistElement.size());
        for(Element e : valueAssistElement) {
            valueAssist.add((RestPropertyValueAssist)DCTMJaxbContext.unmarshal(e));
        }
        return valueAssist;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbProperty that = (JaxbProperty)obj;
        return Equals.equal(name, that.name) 
            && Equals.equal(type, that.type)
            && Equals.equal(label, that.label)
            && Equals.equal(length, that.length)
            && Equals.equal(repeating, that.repeating)
            && Equals.equal(hidden, that.hidden)
            && Equals.equal(required, that.required)
            && Equals.equal(notNull, that.notNull)
            && Equals.equal(readOnly, that.readOnly)
            && Equals.equal(searchable, that.searchable)
            && Equals.equal(displayHint, that.displayHint)
            && Equals.equal(dependencies, that.dependencies)
            && Equals.equal(defaultExpression, that.defaultExpression)
            && Equals.equal(defaultLiteral, that.defaultLiteral)
            && Equals.equal(defaultList, that.defaultList)
            && Equals.equal(lifecycles, that.lifecycles)
            && Equals.equal(notNullEnforce, that.notNullEnforce)
            && Equals.equal(notNullMessage, that.notNullMessage)
            && Equals.equal(mappingTables, that.mappingTables)
            && Equals.equal(ignoreConstraints, that.ignoreConstraints)
            && Equals.equal(ignoreImmutable, that.ignoreImmutable)
            && Equals.equal(helpText, that.helpText)
            && Equals.equal(commentText, that.commentText)
            && Equals.equal(category, that.category)
            && Equals.equal(constraints, that.constraints);
    }

    @XmlRootElement(name="default-value")
    public static class JaxbDefaultValue implements DefaultValue {
        private String defaultExpression;
        private String defaultLiteral;

        public JaxbDefaultValue() {
        }
        
        public JaxbDefaultValue(String defaultLiteral, String defaultExpression) {
            this.defaultLiteral = defaultLiteral;
            this.defaultExpression = defaultExpression;
        }
        
        @XmlAttribute(name="default-expression")
        public String getDefaultExpression() {
            return defaultExpression;
        }

        public void setDefaultExpression(String defaultExpression) {
            this.defaultExpression = defaultExpression;
        }

        @XmlAttribute(name="default-literal")
        public String getDefaultLiteral() {
            return defaultLiteral;
        }

        public void setDefaultLiteral(String defaultLiteral) {
            this.defaultLiteral = defaultLiteral;
        }

        public String getExpression() {
            return defaultLiteral==null?defaultExpression:defaultLiteral;
        }

        public boolean isExpression() {
            return !StringUtils.isEmpty(defaultExpression);
        }
        
        @Override
        public boolean equals(Object obj) {
            JaxbDefaultValue that = (JaxbDefaultValue)obj;
            return Equals.equal(defaultExpression, that.defaultExpression)
                && Equals.equal(defaultLiteral, that.defaultLiteral);
        }
    }
}
