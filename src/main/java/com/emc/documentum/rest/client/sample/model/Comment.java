/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

public interface Comment extends Linkable {
    public String getObjectId();
    public String getCommentId();;
    public String getOwnerName();
    public String getCreationDate();
    public String getModifyDate();
    public String getContentValue();
    public String getParentId();
    public String getTitle();
    public boolean isCanDelete();
    public boolean isCanReply();
}
