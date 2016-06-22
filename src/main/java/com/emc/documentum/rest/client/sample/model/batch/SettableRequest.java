/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.batch;

public interface SettableRequest extends Request {
    public void setMethod(String method);
    public void setUri(String uri);
    public void addHeader(Header headers);
    public void setHeader(Header headers);
    public void setEntity(String entity);
    public void setAttachment(Attachment attachment);
}
