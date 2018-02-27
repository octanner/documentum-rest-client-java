/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.FolderLink;

@XmlRootElement(name="folder-link")
public class JaxbFolderLink extends JaxbDmLinkableBase implements FolderLink {
    private String href;
    private String parentId;
    private String childId;
    
    public JaxbFolderLink() {
    }

    public JaxbFolderLink(FolderLink folderLink) {
        this.href = folderLink.getHref();
        this.parentId = folderLink.getParentId();
        this.childId = folderLink.getChildId();
    }

    @Override
    @XmlAttribute
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    @XmlAttribute(name="parent-id")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    @XmlAttribute(name="child-id")
    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbFolderLink that = (JaxbFolderLink)obj;
        return Equals.equal(href, that.href) &&
               Equals.equal(parentId, that.parentId) &&
               Equals.equal(childId, that.childId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(href, parentId, childId);
    }
}
