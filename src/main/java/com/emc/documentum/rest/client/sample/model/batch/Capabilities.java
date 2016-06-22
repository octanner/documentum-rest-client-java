/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.batch;

import java.util.List;

import com.emc.documentum.rest.client.sample.model.Linkable;

public interface Capabilities extends Linkable {
    public String getTransactions();
    public String getSequence();
    public String getOnError();
    public List<String> getBatchable();
    public List<String> getNonBatchable();
}
