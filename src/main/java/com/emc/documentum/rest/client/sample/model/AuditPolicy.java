/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.Map;

/**
 * represents the audit policy
 */
public interface AuditPolicy extends Linkable {
    public String getObjectId();
    public String getName();
    public String getAccessorName();
    public boolean isGroup();
    public Map<String, String> getAttributeRules();
}
