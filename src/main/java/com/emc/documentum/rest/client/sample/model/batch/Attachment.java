/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.batch;

import java.io.InputStream;

public interface Attachment {
    public Include getInclude();
    public String getContentType();
    public InputStream getContentStream();
}
