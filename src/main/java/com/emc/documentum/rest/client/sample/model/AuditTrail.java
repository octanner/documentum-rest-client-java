/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * represents the audit policy
 */
public interface AuditTrail extends RestObject {
    public List<AuditData> getAuditDataList();
    
    public interface AuditData {
        public String getAttrName();
        public List<ValuePair> getValuePairs();
    }

    public interface ValuePair {
        public Object getOldValue();
        public Object getNewValue();
    }
}
