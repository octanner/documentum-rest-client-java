/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Collections;
import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.RestProperty;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.emc.documentum.rest.client.sample.model.RestTypeLifecycleInfo;
import com.emc.documentum.rest.client.sample.model.RestTypeMappingTable;
import com.emc.documentum.rest.client.sample.model.RestTypeScopeConfig;
import com.emc.documentum.rest.client.sample.model.RestTypeValueConstraint;

@XmlRootElement(name = "type")
public class JaxbType extends JaxbDmLinkableBase implements RestType {
    private String name;
    private String label;
    private String parent;
    private String sharedParent;
    private String category;
    private String helpText;
    private String commentText;
    private List<RestProperty> properties;
    private String defaultLifecycle;
    private String defaultLifecycleVersion;
    private List<String> auditableSystemEvents;
    private List<String> auditableAppEvents;
    private List<RestTypeMappingTable> mappingTables;
    private List<RestTypeScopeConfig> scopeConfigs;
    private List<RestTypeValueConstraint> constraints;
    private boolean ignoreConstraints;
    private List<JaxbTypeLifecycleBased> lifecycles;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @XmlAttribute
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @XmlAttribute(name="shared-parent")
    public String getSharedParent() {
        return sharedParent;
    }

    public void setSharedParent(String sharedParent) {
        this.sharedParent = sharedParent;
    }

    @XmlAttribute
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @XmlElement(name="help-text")
    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    @XmlElement(name="comment-text")
    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    @XmlElementWrapper(name="properties")
    @XmlElement(name="property", type=JaxbProperty.class)
    public List<RestProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<RestProperty> properties) {
        this.properties = properties;
    }

    @XmlAttribute(name="default-lifecycle")
    public String getDefaultLifecycle() {
        return defaultLifecycle;
    }

    public void setDefaultLifecycle(String defaultLifecycle) {
        this.defaultLifecycle = defaultLifecycle;
    }

    @XmlAttribute(name="default-lifecycle-version")
    public String getDefaultLifecycleVersion() {
        return defaultLifecycleVersion;
    }

    public void setDefaultLifecycleVersion(String defaultLifecycleVersion) {
        this.defaultLifecycleVersion = defaultLifecycleVersion;
    }

    public Map<String, Map<String, RestTypeLifecycleInfo>> getTypeLifecycles() {
        if(Collections.isEmpty(lifecycles)) {
            return null;
        }
        Map<String, Map<String, RestTypeLifecycleInfo>> map = new HashMap<>();
        for(JaxbTypeLifecycleBased lbased : lifecycles) {
            Map<String, RestTypeLifecycleInfo> stateMap = map.get(lbased.getId());
            if(stateMap == null) {
                stateMap = new HashMap<>();
                map.put(lbased.getId(), stateMap);
            }
            for(JaxbTypeStateBased sbased : lbased.getStates()) {
                stateMap.put(sbased.getName(), sbased.getTypeLifecycle());
            }
        }
        return map;
    }

    @XmlElementWrapper(name="lifecycles")
    @XmlElement(name="lifecycle", type=JaxbTypeLifecycleBased.class)
    public List<JaxbTypeLifecycleBased> getLifecycles() {
        return lifecycles;
    }

    public void setLifecycles(List<JaxbTypeLifecycleBased> lifecycles) {
        this.lifecycles = lifecycles;
    }

    @XmlElementWrapper(name="auditable-system-events")
    @XmlElement(name="event")
    public List<String> getAuditableSystemEvents() {
        return auditableSystemEvents;
    }

    public void setAuditableSystemEvents(List<String> auditableSystemEvents) {
        this.auditableSystemEvents = auditableSystemEvents;
    }

    @XmlElementWrapper(name="auditable-app-events")
    @XmlElement(name="event")
    public List<String> getAuditableAppEvents() {
        return auditableAppEvents;
    }

    public void setAuditableAppEvents(List<String> auditableAppEvents) {
        this.auditableAppEvents = auditableAppEvents;
    }

    @XmlElementWrapper(name="mapping-tables")
    @XmlElement(name="mapping-table", type=JaxbTypeMappingTable.class)
    public List<RestTypeMappingTable> getMappingTables() {
        return mappingTables;
    }

    public void setMappingTables(List<RestTypeMappingTable> mappingTables) {
        this.mappingTables = mappingTables;
    }

    @XmlElementWrapper(name="scope-configs")
    @XmlElement(name="scope-config", type=JaxbTypeScopeConfig.class)
    public List<RestTypeScopeConfig> getScopeConfigs() {
        return scopeConfigs;
    }

    public void setScopeConfigs(List<RestTypeScopeConfig> scopeConfigs) {
        this.scopeConfigs = scopeConfigs;
    }

    @XmlElementWrapper(name="constraints")
    @XmlElement(name="constraint", type=JaxbTypeValueConstraint.class)
    public List<RestTypeValueConstraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<RestTypeValueConstraint> constraints) {
        this.constraints = constraints;
    }

    @XmlAttribute(name="ignore-parent-constraints")
    public boolean isIgnoreConstraints() {
        return ignoreConstraints;
    }

    public void setIgnoreConstraints(boolean ignoreConstraints) {
        this.ignoreConstraints = ignoreConstraints;
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbType that = (JaxbType)obj;
        return Equals.equal(name, that.name) 
            && Equals.equal(label, that.label)
            && Equals.equal(parent, that.parent)
            && Equals.equal(sharedParent, that.sharedParent)
            && Equals.equal(category, that.category)
            && Equals.equal(properties, that.properties)
            && Equals.equal(helpText, that.helpText)
            && Equals.equal(commentText, that.commentText)
            && Equals.equal(defaultLifecycle, that.defaultLifecycle)
            && Equals.equal(defaultLifecycleVersion, that.defaultLifecycleVersion)
            && Equals.equal(lifecycles, that.lifecycles)
            && Equals.equal(auditableSystemEvents, that.auditableSystemEvents)
            && Equals.equal(auditableAppEvents, that.auditableAppEvents)
            && Equals.equal(mappingTables, that.mappingTables)
            && Equals.equal(scopeConfigs, that.scopeConfigs)
            && Equals.equal(constraints, that.constraints)
            && Equals.equal(ignoreConstraints, that.ignoreConstraints)
            && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    
    
    
    
    
    
    
    
    
//    private String name;
//    private String label;
//    private String parent;
//    private String sharedParent;
//    private String category;
//    private List<Map<String, Object>> properties;
//    private TypeProperties objectProperties;
//    
//    @Override
//    @XmlAttribute
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @Override
//    @XmlAttribute
//    public String getLabel() {
//        return label;
//    }
//
//    public void setLabel(String label) {
//        this.label = label;
//    }
//
//    @Override
//    @XmlAttribute
//    public String getParent() {
//        return parent;
//    }
//
//    public void setParent(String parent) {
//        this.parent = parent;
//    }
//
//    @Override
//    @XmlAttribute(name="shared-parent")
//    public String getSharedParent() {
//        return sharedParent;
//    }
//
//    public void setSharedParent(String sharedParent) {
//        this.sharedParent = sharedParent;
//    }
//
//    @Override
//    @XmlAttribute
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    @Override
//    @XmlTransient
//    public List<Map<String, Object>> getProperties() {
//        if(properties == null) {
//            properties = new ArrayList<Map<String, Object>>();
//            if(objectProperties != null && objectProperties.getElements() != null) {
//                for(Element e : objectProperties.getElements()) {
//                    Map<String, Object> map = new HashMap<>();
//                    addAttribute(e, map, AttributeType.STRING, "name");
//                    addAttribute(e, map, AttributeType.BOOLEAN, "repeating");
//                    addAttribute(e, map, AttributeType.STRING, "type");
//                    addAttribute(e, map, AttributeType.INT, "length");
//                    addAttribute(e, map, AttributeType.STRING, "label");
//                    addAttribute(e, map, AttributeType.BOOLEAN, "hidden");
//                    addAttribute(e, map, AttributeType.BOOLEAN, "required");
//                    addAttribute(e, map, AttributeType.BOOLEAN, "notnull");
//                    addAttribute(e, map, AttributeType.BOOLEAN, "readonly");
//                    addAttribute(e, map, AttributeType.BOOLEAN, "searchable");
//                    addAttribute(e, map, AttributeType.STRING, "default-literal");
//                    addAttribute(e, map, AttributeType.STRING, "default-expression");
//                    NodeList nodeList = e.getChildNodes();
//                    List<String> list = null;
//                    for(int i=0;i<nodeList.getLength();++i) {
//                        Node node = nodeList.item(i);
//                        switch(node.getLocalName()) {
//                        case "item":
//                            if(list == null) {
//                                list = new ArrayList<String>();
//                            }
//                            list.add(node.getTextContent());
//                            break;
//                        case "dependencies":
//                            List<String> dependencies = new ArrayList<>();
//                            NodeList childNodeList = node.getChildNodes();
//                            for(int j=0;j<childNodeList.getLength();++j) {
//                                Node childNode = childNodeList.item(i);
//                                if(childNode.getLocalName().equals("item")) {
//                                    dependencies.add(childNode.getTextContent());
//                                }
//                            }
//                            map.put("dependencies", dependencies);
//                            break;
//                        case "value-assist":
//                            //todo
//                            break;
//                        }
//                    }
//                    if(list != null) {
//                        map.put("defaults", list);
//                    }
//                    properties.add(map);
//                }
//            }
//        }
//        return properties;
//    }
//    
//    private void addAttribute(Element e, Map<String, Object> map, AttributeType type, String attribute) {
//        if(e.hasAttribute(attribute)) {
//            switch(type) {
//                case STRING:
//                    map.put(attribute, e.getAttribute(attribute));
//                    break;
//                case INT:
//                    map.put(attribute, Integer.parseInt(e.getAttribute(attribute)));
//                    break;
//                case BOOLEAN:
//                    map.put(attribute, Boolean.parseBoolean(e.getAttribute(attribute)));
//                    break;
//            }
//        }
//    }
//    
//    private static enum AttributeType {
//        STRING, INT, BOOLEAN
//    }
//
//    public void setProperties(List<Map<String, Object>> properties) {
//        this.properties = properties;
//        // no update support
//    }
//
//    @XmlElement(name="properties")
//    protected TypeProperties getObjectProperties() {
//        return objectProperties;
//    }
//
//    protected void setObjectProperties(TypeProperties objectProperties) {
//        this.objectProperties = objectProperties;
//    }
//
//    @XmlRootElement(name = "properties")
//    static class TypeProperties {
//        private List<Element> elements;
//        private String propertiesType;
//        
//        @XmlAnyElement
//        public List<Element> getElements() {
//            return elements;
//        }
//
//        public void setElements(List<Element> elements) {
//            this.elements = elements;
//        }
//        
//        @XmlAttribute(name="type", namespace=XMLNamespace.SCHEMA_INSTANCE_NAMESPACE)
//        public String getPropertiesType() {
//            return propertiesType;
//        }
//
//        public void setPropertiesType(String propertiesType) {
//            this.propertiesType = propertiesType;
//        }
//        
//        @Override
//        public boolean equals(Object obj) {
//            TypeProperties that = (TypeProperties) obj;
//            return Equals.equal(propertiesType, that.propertiesType)
//                    && Equals.equal(elements, that.elements);
//        }
//    }
//    
//    @Override
//    public boolean equals(Object obj) {
//        JaxbType that = (JaxbType) obj;
//        return Equals.equal(name, that.name)
//                && Equals.equal(label, that.label)
//                && Equals.equal(parent, that.parent)
//                && Equals.equal(sharedParent, that.sharedParent)
//                && Equals.equal(category, that.category);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, label, parent, sharedParent, category);
//    }
//    
//    public static class JaxbDefaultValue implements RestTypeDefaultValue {
//        private final boolean isExpression;
//        private final Object expression;
//
//        public JaxbDefaultValue(Object expression, boolean isExpression) {
//            this.isExpression = isExpression;
//            this.expression = expression;
//        }
//
//        public Object getExpression() {
//            return expression;
//        }
//
//        public boolean isExpression() {
//            return isExpression;
//        }
//    }
//
//    public static class JaxbRestProperty implements RestTypeProperty {
//        private final String name;
//        private final String type;
//        private final String label;
//        private final int length;
//        private final boolean repeating;
//        private final boolean hidden;
//        private final boolean required;
//        private final boolean notNull;
//        private final boolean readOnly;
//        private final boolean searchable;
//        private final Integer displayHint;
//        private final List<DmlDefaultValue> defaultValue;
//        private final List<String> dependencies;
//        private final List<Map<String, Object>> vaDefinitions;
//        private final Map<String, Map<String, DmlPropertyLifecycleInfo>> propertyLifecycles;
//        private final String notNullEnforce;
//        private final String notNullMessage;
//        private final List<DmlTypeMappingTable> mappingTables;
//        private final boolean ignoreConstraints;
//        private final boolean ignoreImmutable;
//        private final List<DmlTypeValueConstraint> constraints;
//        private final String helpText;
//        private final String commentText;
//        private final String category;
//        
//        public XmlDmlProperty(Node node) {
//            name = getAttribute(node, "name");
//            type = getAttribute(node, "type");
//            label = getAttribute(node, "label");
//            length = getIntAttribute(node, "length");
//            repeating = getBooleanAttribute(node, "repeating");
//            hidden = getBooleanAttribute(node, "hidden");
//            required = getBooleanAttribute(node, "required");
//            notNull = getBooleanAttribute(node, "notnull");
//            readOnly = getBooleanAttribute(node, "readonly");
//            searchable = getBooleanAttribute(node, "searchable");
//            displayHint = getIntegerAttribute(node, "display-hint");
//            
//            category = getAttribute(node, "category");
//            notNullEnforce = getAttribute(node, "notnull-enforce");
//            notNullMessage = getAttribute(node, "notnull-message");
//            ignoreConstraints = getBooleanAttribute(node, "ignore-parent-constraints");
//            ignoreImmutable = getBooleanAttribute(node, "ignore-immutable");
//            
//            NamedNodeMap attrs = node.getAttributes();
//            if (attrs.getNamedItem("default-expression") != null) {
//                defaultValue = Arrays.asList(new DmlDefaultValue(attrs.getNamedItem("default-expression").getTextContent(), true));
//            } else if (attrs.getNamedItem("default-literal") != null) {
//                defaultValue = Arrays.asList(new DmlDefaultValue(attrs.getNamedItem("default-literal").getTextContent(), false));
//            } else {
//                List<DmlDefaultValue> dmlDefaultValue = null;
//                List<Node> defaultNodes = toDmElementList(node, "default-value");
//                if(defaultNodes != null) {
//                    dmlDefaultValue = new ArrayList<DmlDefaultValue>(defaultNodes.size());
//                    for(Node defaultNode : defaultNodes) {
//                        String defaultExpression = getAttribute(defaultNode, "default-expression");
//                        if(defaultExpression != null) {
//                            dmlDefaultValue.add(new DmlDefaultValue(defaultExpression, true));
//                        } else {
//                            String defaultLiteral = getAttribute(defaultNode, "default-literal");
//                            if(defaultLiteral != null) {
//                                dmlDefaultValue.add(new DmlDefaultValue(defaultLiteral, false));
//                            }
//                        }
//                    }
//                }
//                defaultValue = dmlDefaultValue;
//            }
//            
//            Map<String, Node> map = toDmElementMap(node, "!default-value");
//
//            helpText = getTextContent(map, "help-text");
//            commentText = getTextContent(map, "comment-text");
//            dependencies = toListString(map.get("dependencies"));
//            mappingTables = getSubelements(map, XmlDmlTypeMappingTable.class, "mapping-tables", "mapping-table");
//            constraints = getSubelements(map, XmlDmlTypeValueConstraint.class, "constraints", "constraint");
//
//            List<Node> lfNodeList = toDmElementList(map.get("lifecycles"), "lifecycle");
//            if(lfNodeList.isEmpty()) {
//                propertyLifecycles = null;
//            } else {
//                propertyLifecycles = new HashMap<>();
//                for(Node lfNode : lfNodeList) {
//                    Map<String, DmlPropertyLifecycleInfo> stmap = new HashMap<>();
//                    propertyLifecycles.put(getAttribute(lfNode, "id"), stmap);
//                    List<Node> stNodeList = toDmElementList(lfNode, "state");
//                    for(Node stNode : stNodeList) {
//                        List<Node> plNodeList = toDmElementList(stNode, "property-of-lifecycle");
//                        for(Node plNode : plNodeList) {
//                            stmap.put(getAttribute(stNode, "name"), new XmlPropertyLifecycleInfo(plNode));
//                        }
//                    }
//                }
//            }
//            
//            Node vaDefinitionsNode = map.get("value-assist");
//            if (vaDefinitionsNode != null) {
//                vaDefinitions = new ArrayList<Map<String, Object>>();
//                for (int i = 0; i < vaDefinitionsNode.getChildNodes().getLength(); i++) {
//                    if ("query".equals(vaDefinitionsNode.getChildNodes().item(i).getLocalName())) {
//                        Map<String, Object> queryMap = new HashMap<String, Object>();
//                        Node queryNode = vaDefinitionsNode.getChildNodes().item(i);
//                        NamedNodeMap queryNodeAttributes = queryNode.getAttributes();
//                        queryMap.put("allow-caching", queryNodeAttributes.getNamedItem("allow-caching").getTextContent());
//                        queryMap.put("allow-user-values", queryNodeAttributes.getNamedItem("allow-user-values").getTextContent());
//                        queryMap.put("query-attribute", queryNodeAttributes.getNamedItem("query-attribute").getTextContent());
//                        if (queryNodeAttributes.getNamedItem("condition") != null) {
//                            queryMap.put("condition", queryNodeAttributes.getNamedItem("condition").getTextContent());
//                        }
//                        queryMap.put("query-expression", queryNode.getTextContent());
//                        vaDefinitions.add(queryMap);
//                    } else if ("fixed-list".equals(vaDefinitionsNode.getChildNodes().item(i).getLocalName())) {
//                        Map<String, Object> listMap = new HashMap<String, Object>();
//                        Node listNode = vaDefinitionsNode.getChildNodes().item(i);
//                        NamedNodeMap listNodeAttributes = listNode.getAttributes();
//                        listMap.put("allow-user-values", listNodeAttributes.getNamedItem("allow-user-values").getTextContent());
//                        if (listNodeAttributes.getNamedItem("condition") != null) {
//                            listMap.put("condition", listNodeAttributes.getNamedItem("condition").getTextContent());
//                        }
//                        List<String> listValues = new ArrayList<>();
//                        for (int j = 0; j < listNode.getChildNodes().getLength(); j++) {
//                            if ("value".equals(listNode.getChildNodes().item(j).getLocalName())) {
//                                listValues.add(listNode.getChildNodes().item(j).getTextContent());
//                            }
//                        }
//                        listMap.put("values", listValues);
//                        vaDefinitions.add(listMap);
//                    }
//                }
//            } else {
//                vaDefinitions = null;
//            }
//        }
//
//        @Override
//        public String getName() {
//            return name;
//        }
//
//        @Override
//        public String getType() {
//            return type;
//        }
//
//        @Override
//        public String getLabel() {
//            return label;
//        }
//
//        @Override
//        public int getLength() {
//            return length;
//        }
//
//        @Override
//        public boolean isRepeating() {
//            return repeating;
//        }
//
//        @Override
//        public boolean isHidden() {
//            return hidden;
//        }
//
//        @Override
//        public boolean isRequired() {
//            return required;
//        }
//
//        @Override
//        public boolean isNotNull() {
//            return notNull;
//        }
//
//        @Override
//        public boolean isReadOnly() {
//            return readOnly;
//        }
//
//        @Override
//        public boolean isSearchable() {
//            return searchable;
//        }
//
//        @Override
//        public Integer getDisplayHint() {
//            return displayHint;
//        }
//        
//        @Override
//        public List<DmlDefaultValue> getDefault() {
//            return defaultValue;
//        }
//
//        @Override
//        public List<String> getDependencies() {
//            return dependencies;
//        }
//
//        @Override
//        public List<Map<String, Object>> getVADefinitions() {
//            return vaDefinitions;
//        }
//
//        @Override
//        public Map<String, Map<String, DmlPropertyLifecycleInfo>> getPropertyLifecycles() {
//            return propertyLifecycles;
//        }
//
//        @Override
//        public List<DmlTypeMappingTable> getMappingTables() {
//            return mappingTables;
//        }
//
//        @Override
//        public boolean isIgnoreConstraints() {
//            return ignoreConstraints;
//        }
//
//        @Override
//        public List<DmlTypeValueConstraint> getConstraints() {
//            return constraints;
//        }
//
//        @Override
//        public String getHelpText() {
//            return helpText;
//        }
//
//        @Override
//        public String getCommentText() {
//            return commentText;
//        }
//
//        @Override
//        public String getCategory() {
//            return category;
//        }
//
//        @Override
//        public boolean isIgnoreImmutable() {
//            return ignoreImmutable;
//        }
//
//        @Override
//        public String getNotNullEnforce() {
//            return notNullEnforce;
//        }
//
//        @Override
//        public String getNotNullMessage() {
//            return notNullMessage;
//        }
//    }
//
//    public static class JaxbPropertyLifecycleInfo implements RestPropertyLifecycleInfo {
//        private final String label;
//        private final boolean hidden;
//        private final boolean required;
//        private final boolean notNull;
//        private final boolean readOnly;
//        private final boolean searchable;
//        private final List<String> dependencies;
//        private final List<Map<String, Object>> vaDefinitions;
//        private final String notNullEnforce;
//        private final String notNullMessage;
//        private final boolean ignoreConstraints;
//        private final boolean ignoreImmutable;
//        private final List<DmlTypeValueConstraint> constraints;
//
//        public XmlPropertyLifecycleInfo(Node node) {
//            label = getAttribute(node, "label");
//            hidden = getBooleanAttribute(node, "hidden");
//            required = getBooleanAttribute(node, "required");
//            notNull = getBooleanAttribute(node, "notnull");
//            readOnly = getBooleanAttribute(node, "readonly");
//            searchable = getBooleanAttribute(node, "searchable");
//            notNullEnforce = getAttribute(node, "notnull-enforce");
//            notNullMessage = getAttribute(node, "notnull-message");
//            ignoreConstraints = getBooleanAttribute(node, "ignore-parent-constraints");
//            ignoreImmutable = getBooleanAttribute(node, "ignore-immutable");
//            
//            Map<String, Node> map = toDmElementMap(node);
//
//            dependencies = toListString(map.get("dependencies"));
//            
//            constraints = getSubelements(map, XmlDmlTypeValueConstraint.class, "constraints", "constraint");
//
//            Node vaDefinitionsNode = map.get("value-assist");
//            if (vaDefinitionsNode != null) {
//                vaDefinitions = new ArrayList<Map<String, Object>>();
//                for (int i = 0; i < vaDefinitionsNode.getChildNodes().getLength(); i++) {
//                    if ("query".equals(vaDefinitionsNode.getChildNodes().item(i).getLocalName())) {
//                        Map<String, Object> queryMap = new HashMap<String, Object>();
//                        Node queryNode = vaDefinitionsNode.getChildNodes().item(i);
//                        NamedNodeMap queryNodeAttributes = queryNode.getAttributes();
//                        queryMap.put("allow-caching", queryNodeAttributes.getNamedItem("allow-caching").getTextContent());
//                        queryMap.put("allow-user-values", queryNodeAttributes.getNamedItem("allow-user-values").getTextContent());
//                        queryMap.put("query-attribute", queryNodeAttributes.getNamedItem("query-attribute").getTextContent());
//                        if (queryNodeAttributes.getNamedItem("condition") != null) {
//                            queryMap.put("condition", queryNodeAttributes.getNamedItem("condition").getTextContent());
//                        }
//                        queryMap.put("query-expression", queryNode.getTextContent());
//                        vaDefinitions.add(queryMap);
//                    } else if ("fixed-list".equals(vaDefinitionsNode.getChildNodes().item(i).getLocalName())) {
//                        Map<String, Object> listMap = new HashMap<String, Object>();
//                        Node listNode = vaDefinitionsNode.getChildNodes().item(i);
//                        NamedNodeMap listNodeAttributes = listNode.getAttributes();
//                        listMap.put("allow-user-values", listNodeAttributes.getNamedItem("allow-user-values").getTextContent());
//                        if (listNodeAttributes.getNamedItem("condition") != null) {
//                            listMap.put("condition", listNodeAttributes.getNamedItem("condition").getTextContent());
//                        }
//                        List<String> listValues = new ArrayList<>();
//                        for (int j = 0; j < listNode.getChildNodes().getLength(); j++) {
//                            if ("value".equals(listNode.getChildNodes().item(j).getLocalName())) {
//                                listValues.add(listNode.getChildNodes().item(j).getTextContent());
//                            }
//                        }
//                        listMap.put("values", listValues);
//                        vaDefinitions.add(listMap);
//                    }
//                }
//            } else {
//                vaDefinitions = null;
//            }
//        }
//
//
//        @Override
//        public String getLabel() {
//            return label;
//        }
//
//        @Override
//        public boolean isHidden() {
//            return hidden;
//        }
//
//        @Override
//        public boolean isRequired() {
//            return required;
//        }
//
//        @Override
//        public boolean isNotNull() {
//            return notNull;
//        }
//
//        @Override
//        public boolean isReadOnly() {
//            return readOnly;
//        }
//
//        @Override
//        public boolean isSearchable() {
//            return searchable;
//        }
//
//        @Override
//        public List<String> getDependencies() {
//            return dependencies;
//        }
//
//        @Override
//        public List<Map<String, Object>> getVADefinitions() {
//            return vaDefinitions;
//        }
//        @Override
//        public boolean isIgnoreConstraints() {
//            return ignoreConstraints;
//        }
//
//        @Override
//        public List<DmlTypeValueConstraint> getConstraints() {
//            return constraints;
//        }
//
//        @Override
//        public boolean isIgnoreImmutable() {
//            return ignoreImmutable;
//        }
//
//        @Override
//        public String getNotNullEnforce() {
//            return notNullEnforce;
//        }
//
//        @Override
//        public String getNotNullMessage() {
//            return notNullMessage;
//        }
//    }
//    
//    public static class JaxbTypeAttributeHint implements RestTypeAttributeHint {
//        private String attribute;
//        private Integer displayHint;
//
//        public XmlDmlTypeAttributeHint(Node attrHintNode) {
//            NodeList list = attrHintNode.getChildNodes();
//            for (int i = 0; i < list.getLength(); ++i) {
//                Node node = attrHintNode.getChildNodes().item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE && "attribute".equals(node.getLocalName())) {
//                    attribute = node.getTextContent();
//                } else if (node.getNodeType() == Node.ELEMENT_NODE && "display-hint".equals(node.getLocalName())) {
//                    displayHint = Integer.valueOf(node.getTextContent());
//                }
//            }
//        }
//
//        @Override
//        public String getAttribute() {
//            return attribute;
//        }
//
//        @Override
//        public Integer getDisplayHint() {
//            return displayHint;
//        }
//    }
//    
//    public static class JaxbDmlTypeDisplayConfig implements RestTypeDisplayConfig {
//        private String id;
//        private String name;
//        private String attributeSource;
//        private boolean isFixedDisplay;
//        private List<DmlTypeAttributeHint> attributeHints;
//
//        public XmlDmlTypeDisplayConfig(Node displayConfigNode) {
//            NodeList list = displayConfigNode.getChildNodes();
//            for (int i = 0; i < list.getLength(); ++i) {
//                Node node = displayConfigNode.getChildNodes().item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE && "id".equals(node.getLocalName())) {
//                    id = node.getTextContent();
//                } else if (node.getNodeType() == Node.ELEMENT_NODE && "name".equals(node.getLocalName())) {
//                    name = node.getTextContent();
//                } else if (node.getNodeType() == Node.ELEMENT_NODE && "attribute-source".equals(node.getLocalName())) {
//                    attributeSource = node.getTextContent();
//                } else if (node.getNodeType() == Node.ELEMENT_NODE && "fixed-display".equals(node.getLocalName())) {
//                    isFixedDisplay = Boolean.valueOf(node.getTextContent());
//                } else if (node.getNodeType() == Node.ELEMENT_NODE && "attribute-hints".equals(node.getLocalName())) {
//                    attributeHints = new ArrayList<>();
//                    NodeList hintList = node.getChildNodes();
//                    for (int k = 0; k < hintList.getLength(); ++k) {
//                        attributeHints.add(new XmlDmlTypeAttributeHint(node.getChildNodes().item(k)));
//                    }
//                }
//            }
//        }
//
//        @Override
//        public String getId() {
//            return id;
//        }
//
//        @Override
//        public String getName() {
//            return name;
//        }
//
//        @Override
//        public String getAttributeSource() {
//            return attributeSource;
//        }
//
//        @Override
//        public boolean isFixedDisplay() {
//            return isFixedDisplay;
//        }
//
//        @Override
//        public List<DmlTypeAttributeHint> getAttributeHints() {
//            return attributeHints;
//        }
//    }
//    
//    public static class JaxbTypeLifecycleInfo implements RestTypeLifecycleInfo {
//        private String label;
//        
//        private String defaultLifecycle;
//        
//        private String defaultLifecycleVersion;
//        
//        private boolean ignoreConstraints;
//        
//        private List<DmlTypeValueConstraint> constraints = null;
//        
//        public XmlTypeLifecycleInfo(Node node) {
//            label = getAttribute(node, "label");
//            defaultLifecycle = getAttribute(node, "default-lifecycle");
//            defaultLifecycleVersion = getAttribute(node, "default-lifecycle-version");
//            ignoreConstraints = getBooleanAttribute(node, "ignore-parent-constraints");
//            
//            Map<String, Node> typeMap = toDmElementMap(node);
//            constraints = getSubelements(typeMap, XmlDmlTypeValueConstraint.class, "constraints", "constraint");
//        }
//
//        @Override
//        public String getLabel() {
//            return label;
//        }
//
//        public void setLabel(String label) {
//            this.label = label;
//        }
//
//        @Override
//        public String getDefaultLifecycle() {
//            return defaultLifecycle;
//        }
//
//        public void setDefaultLifecycle(String defaultLifecycle) {
//            this.defaultLifecycle = defaultLifecycle;
//        }
//
//        @Override
//        public String getDefaultLifecycleVersion() {
//            return defaultLifecycleVersion;
//        }
//
//        public void setDefaultLifecycleVersion(String defaultLifecycleVersion) {
//            this.defaultLifecycleVersion = defaultLifecycleVersion;
//        }
//
//        @Override
//        public boolean getIgnoreConstraints() {
//            return ignoreConstraints;
//        }
//
//        @Override
//        public List<DmlTypeValueConstraint> getConstraints() {
//            return constraints;
//        }
//    }
//    
//    public static class JaxbDmlTypeMappingTable implements RestTypeMappingTable {
//        private final String value;
//        private final String display;
//        private final String description;
//
//        public XmlDmlTypeMappingTable(Node mappingTableNode) {
//            Map<String, String> mt = toDmTextMap(mappingTableNode.getChildNodes());
//            value = mt.get("value");
//            display = mt.get("display");
//            description = mt.get("description");
//        }
//
//        @Override
//        public String getValue() {
//            return value;
//        }
//
//        @Override
//        public String getDisplay() {
//            return display;
//        }
//
//        @Override
//        public String getDescription() {
//            return description;
//        }
//    }
//
//    public static class JaxbTypeScopeConfig implements RestTypeScopeConfig {
//        private String id;
//        private List<String> scopes;
//        private List<String> categories;
//        private List<DmlTypeDisplayConfig> displayConfigs;
//
//        public XmlDmlTypeScopeConfig(Node scopeConfigNode) {
//            NodeList list = scopeConfigNode.getChildNodes();
//            for (int i = 0; i < list.getLength(); ++i) {
//                Node node = scopeConfigNode.getChildNodes().item(i);
//                if (node.getNodeType() == Node.ELEMENT_NODE && "id".equals(node.getLocalName())) {
//                    this.id = node.getTextContent();
//                } else if (node.getNodeType() == Node.ELEMENT_NODE && "scope".equals(node.getLocalName())) {
//                    this.scopes = XmlUtil.toListString(node, "item");
//                } else if (node.getNodeType() == Node.ELEMENT_NODE && "category".equals(node.getLocalName())) {
//                    this.categories = XmlUtil.toListString(node, "item");
//                } else if (node.getNodeType() == Node.ELEMENT_NODE && "display-configs".equals(node.getLocalName())) {
//                    displayConfigs = new ArrayList<>();
//                    NodeList displayConfigNodeList = node.getChildNodes();
//                    for (int k = 0; k < displayConfigNodeList.getLength(); ++k) {
//                        Node displayConfigNode = node.getChildNodes().item(k);
//                        displayConfigs.add(new XmlDmlTypeDisplayConfig(displayConfigNode));
//                    }
//                }
//            }
//        }
//
//        @Override
//        public String getId() {
//            return id;
//        }
//
//        @Override
//        public List<String> getScopes() {
//            return scopes;
//        }
//
//        @Override
//        public List<String> getCategories() {
//            return categories;
//        }
//
//        @Override
//        public List<DmlTypeDisplayConfig> getDisplayConfigs() {
//            return displayConfigs;
//        }
//    }
//
//    public static class JaxbTypeValueConstraint implements RestTypeValueConstraint {
//        private String expression;
//        private String enforce;
//        private String dependency;
//        private String message;
//
//        public XmlDmlTypeValueConstraint(Node contraintNode) {
//            Map<String, String> constrintMap = XmlUtil.toDmTextMap(contraintNode.getChildNodes());
//            expression = (String)constrintMap.get("expression");
//            enforce = (String)constrintMap.get("enforce");
//            dependency = (String)constrintMap.get("dependency");
//            message = (String)constrintMap.get("message");
//        }
//
//        @Override
//        public String getExpression() {
//            return expression;
//        }
//
//        @Override
//        public String getEnforce() {
//            return enforce;
//        }
//
//        @Override
//        public String getDependency() {
//            return dependency;
//        }
//
//        @Override
//        public String getMessage() {
//            return message;
//        }
//    }
}
