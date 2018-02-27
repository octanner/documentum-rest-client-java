/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.AuditPolicy;

@XmlRootElement(name = "audit-policy")
public class JaxbAuditPolicy extends JaxbDmLinkableBase implements AuditPolicy {
    private String objectId;
    private String name;
    private String accessorName;
    private boolean isGroup;
    private Element attributeRulesElement;
    
    public JaxbAuditPolicy(AuditPolicy auditPolicy) {
        objectId = auditPolicy.getObjectId();
        name = auditPolicy.getName();
        accessorName = auditPolicy.getAccessorName();
        isGroup = auditPolicy.isGroup();
        setAttributeRules(auditPolicy.getAttributeRules());
    }
    
    public JaxbAuditPolicy() {
    }

    @Override
    @XmlElement(name="object-id")
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @XmlElement(name="accessor-name")
    public String getAccessorName() {
        return accessorName;
    }

    public void setAccessorName(String accessorName) {
        this.accessorName = accessorName;
    }

    @Override
    @XmlElement(name="is-group")
    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean isGroup) {
        this.isGroup = isGroup;
    }

    @Override
    @XmlTransient
    public Map<String, String> getAttributeRules() {
        if(attributeRulesElement == null) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        NodeList nodes = attributeRulesElement.getChildNodes();
        for(int i=0;i<nodes.getLength();i++) {
            Node node = nodes.item(i);
            map.put(node.getLocalName(), node.getTextContent());
        }
        return map;
    }

    public void setAttributeRules(Map<String, String> attributeRulesMap) {
        this.attributeRulesElement = new MapKeyValueElement("attribute-rules", attributeRulesMap);
    }
    
    @XmlAnyElement
    public Element getAttributeRulesElement() {
        return attributeRulesElement;
    }
    
    public void setAttributeRulesElement(Element attributeRules) {
        this.attributeRulesElement = attributeRules;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbAuditPolicy that = (JaxbAuditPolicy) obj;
        return Equals.equal(objectId, that.objectId)
                && Equals.equal(name, that.name)
                && Equals.equal(accessorName, that.accessorName)
                && Equals.equal(isGroup, that.isGroup)
                && Equals.equal(getAttributeRules(), that.getAttributeRules())
                && super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(objectId, name, accessorName);
    }
}