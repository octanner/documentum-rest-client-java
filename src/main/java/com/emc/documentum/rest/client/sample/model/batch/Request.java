/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.batch;

import java.util.List;


public interface Request {
    public String getMethod();
    public String getUri();
    public List<Header> getHeaders();
    public String getEntity();
    public Attachment getAttachment();
}
