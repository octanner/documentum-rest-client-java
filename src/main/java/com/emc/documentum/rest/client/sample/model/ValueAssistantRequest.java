/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.Map;

/**
 * the class represents the value assistance request of the REST services
 */
public interface ValueAssistantRequest {
    /**
     * @return value assistance request
     */
    Map<String, Object> getProperties();
}
