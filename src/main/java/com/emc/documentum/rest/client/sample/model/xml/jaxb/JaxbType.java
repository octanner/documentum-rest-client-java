/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkableBase;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name = "type", namespace = XMLNamespace.DM_NAMESPACE)
public class JaxbType extends LinkableBase implements RestType {
    private String name;
    private String label;
    private String parent;
    private String sharedParent;
    private String category;
    private List<Map<String, Object>> properties;
    private TypeProperties objectProperties;
    private List<Link> links;
    
    @Override
    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @XmlAttribute
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    @XmlAttribute
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    @XmlAttribute(name="shared-parent")
    public String getSharedParent() {
        return sharedParent;
    }

    public void setSharedParent(String sharedParent) {
        this.sharedParent = sharedParent;
    }

    @Override
    @XmlAttribute
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    @XmlTransient
    public List<Map<String, Object>> getProperties() {
        if(properties == null) {
            properties = new ArrayList<Map<String, Object>>();
            if(objectProperties != null && objectProperties.getElements() != null) {
                for(Element e : objectProperties.getElements()) {
                    Map<String, Object> map = new HashMap<>();
                    addAttribute(e, map, AttributeType.STRING, "name");
                    addAttribute(e, map, AttributeType.BOOLEAN, "repeating");
                    addAttribute(e, map, AttributeType.STRING, "type");
                    addAttribute(e, map, AttributeType.INT, "length");
                    addAttribute(e, map, AttributeType.STRING, "label");
                    addAttribute(e, map, AttributeType.BOOLEAN, "hidden");
                    addAttribute(e, map, AttributeType.BOOLEAN, "required");
                    addAttribute(e, map, AttributeType.BOOLEAN, "notnull");
                    addAttribute(e, map, AttributeType.BOOLEAN, "readonly");
                    addAttribute(e, map, AttributeType.BOOLEAN, "searchable");
                    addAttribute(e, map, AttributeType.STRING, "default-literal");
                    addAttribute(e, map, AttributeType.STRING, "default-expression");
                    NodeList nodeList = e.getChildNodes();
                    List<String> list = null;
                    for(int i=0;i<nodeList.getLength();++i) {
                        Node node = nodeList.item(i);
                        switch(node.getLocalName()) {
                        case "item":
                            if(list == null) {
                                list = new ArrayList<String>();
                            }
                            list.add(node.getTextContent());
                            break;
                        case "dependencies":
                            List<String> dependencies = new ArrayList<>();
                            NodeList childNodeList = node.getChildNodes();
                            for(int j=0;j<childNodeList.getLength();++j) {
                                Node childNode = childNodeList.item(i);
                                if(childNode.getLocalName().equals("item")) {
                                    dependencies.add(childNode.getTextContent());
                                }
                            }
                            map.put("dependencies", dependencies);
                            break;
                        case "value-assist":
                            //todo
                            break;
                        }
                    }
                    if(list != null) {
                        map.put("defaults", list);
                    }
                    properties.add(map);
                }
            }
        }
        return properties;
    }
    
    private void addAttribute(Element e, Map<String, Object> map, AttributeType type, String attribute) {
        if(e.hasAttribute(attribute)) {
            switch(type) {
                case STRING:
                    map.put(attribute, e.getAttribute(attribute));
                    break;
                case INT:
                    map.put(attribute, Integer.parseInt(e.getAttribute(attribute)));
                    break;
                case BOOLEAN:
                    map.put(attribute, Boolean.parseBoolean(e.getAttribute(attribute)));
                    break;
            }
        }
    }
    
    private static enum AttributeType {
        STRING, INT, BOOLEAN
    }

    public void setProperties(List<Map<String, Object>> properties) {
        this.properties = properties;
        // no update support
    }

    @XmlElement(name="properties")
    protected TypeProperties getObjectProperties() {
        return objectProperties;
    }

    protected void setObjectProperties(TypeProperties objectProperties) {
        this.objectProperties = objectProperties;
    }
    
    @Override
    @XmlElementWrapper(name = "links")
    @XmlElement(name = "link", type = JaxbLink.class, namespace = XMLNamespace.DM_NAMESPACE)
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @XmlRootElement(name = "properties")
    static class TypeProperties {
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
            TypeProperties that = (TypeProperties) obj;
            return Equals.equal(propertiesType, that.propertiesType)
                    && Equals.equal(elements, that.elements);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbType that = (JaxbType) obj;
        return Equals.equal(name, that.name)
                && Equals.equal(label, that.label)
                && Equals.equal(parent, that.parent)
                && Equals.equal(sharedParent, that.sharedParent)
                && Equals.equal(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, label, parent, sharedParent, category);
    }
}
