/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.plain;

import com.emc.documentum.rest.client.sample.model.FolderLink;


/**
 * the plain FolderLink implementation
 */
public class PlainFolderLink extends PlainLinkableBase implements FolderLink {
    private String href;
    private String parentId;
    private String childId;
    
    public PlainFolderLink() {
    }
    
    public PlainFolderLink(String href) {
        this.href = href;
    }
    
    public PlainFolderLink(String parentId, String childId) {
        this.parentId = parentId;
        this.childId = childId;
    }

    @Override
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }
}
