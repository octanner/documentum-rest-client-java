/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

/**
 * represents Folder Link provided by REST server
 */
public interface FolderLink extends Linkable {
    public String getHref();
    public String getParentId();
    public String getChildId();
}
