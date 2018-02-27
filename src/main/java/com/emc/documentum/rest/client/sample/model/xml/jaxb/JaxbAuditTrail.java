/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.AuditTrail;
import com.emc.documentum.rest.client.sample.model.RestObject;

@XmlRootElement(name="audit-trail")
public class JaxbAuditTrail extends JaxbObject implements AuditTrail {
    private List<AuditData> auditDataList;
    
    public JaxbAuditTrail() {
        super();
    }

    public JaxbAuditTrail(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "audit-trail";
    }
    
    @Override
    @XmlElementWrapper(name = "audit-data-list")
    @XmlElement(name = "audit-data", type = JaxbAuditData.class)
    public List<AuditData> getAuditDataList() {
        return auditDataList;
    }

    public void setAuditDataList(List<AuditData> auditDataList) {
        this.auditDataList = auditDataList;
    }

    @XmlRootElement(name="audit-data")
    public static class JaxbAuditData implements AuditData {
        private String attrName;
        private List<ValuePair> valuePairs;
        @Override
        @XmlElement(name = "attr-name")
        public String getAttrName() {
            return attrName;
        }
        public void setAttrName(String attrName) {
            this.attrName = attrName;
        }
        @Override
        @XmlElementWrapper(name = "value-pairs")
        @XmlElement(name = "value-pair", type = JaxbValuePair.class)
        public List<ValuePair> getValuePairs() {
            return valuePairs;
        }
        public void setValuePairs(List<ValuePair> valuePairs) {
            this.valuePairs = valuePairs;
        }
    }
    
    @XmlRootElement(name="value-pair")
    public static class JaxbValuePair implements ValuePair {
        private String oldValue;
        private String newValue;
        @Override
        @XmlElement(name = "old-value")
        public String getOldValue() {
            return oldValue;
        }
        public void setOldValue(String oldValue) {
            this.oldValue = oldValue;
        }
        @Override
        @XmlElement(name = "new-value")
        public String getNewValue() {
            return newValue;
        }
        public void setNewValue(String newValue) {
            this.newValue = newValue;
        }
    }
}
