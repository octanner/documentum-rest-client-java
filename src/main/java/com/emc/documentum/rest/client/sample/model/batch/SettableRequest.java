/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.batch;

public interface SettableRequest extends Request {
    public void setMethod(String method);
    public void setUri(String uri);
    public void addHeader(Header headers);
    public void setHeader(Header headers);
    public void setEntity(String entity);
    /**
     * @deprecated since 7.3
     */
    @Deprecated
    public void setAttachment(Attachment attachment);
    
    /*
     * since 7.3
     */
    public void addAttachment(Attachment attachment);
}
