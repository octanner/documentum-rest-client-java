/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.batch;

public interface SettableHeader extends Header {
    public void setName(String name);
    public void setValue(String value);
}
