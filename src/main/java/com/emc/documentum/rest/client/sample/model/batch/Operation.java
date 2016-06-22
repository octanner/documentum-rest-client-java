/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.batch;

public interface Operation {
    public String getId();
    public String getDescription();
    public Batch.Status getState();
    public String getStarted();
    public String getFinished();
    public Request getRequest();
    public Response getResponse();
}
