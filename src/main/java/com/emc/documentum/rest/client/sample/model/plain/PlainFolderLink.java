/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.plain;

import java.util.List;

import com.emc.documentum.rest.client.sample.model.FolderLink;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkRelation;


/**
 * the plain FolderLink implementation
 */
public class PlainFolderLink implements FolderLink {
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

    @Override
    public List<Link> getLinks() {
        return null;
    }

    @Override
    public String getHref(LinkRelation rel) {
        return null;
    }

    @Override
    public String getHref(LinkRelation rel, String title) {
        return null;
    }

    @Override
    public String self() {
        return null;
    }
}
