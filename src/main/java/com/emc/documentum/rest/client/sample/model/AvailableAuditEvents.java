/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

/**
 * available audit events
 */
public interface AvailableAuditEvents extends Linkable {
    public List<String> getEvents();
}
