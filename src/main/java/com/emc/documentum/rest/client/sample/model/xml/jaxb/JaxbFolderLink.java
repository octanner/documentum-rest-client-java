/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.FolderLink;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkableBase;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="folder-link", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbFolderLink extends LinkableBase implements FolderLink {
    private String href;
    private String parentId;
    private String childId;
    private List<Link> links;
    
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

    @Override
    @XmlElementWrapper(name = "links")
    @XmlElement(name = "link", type = JaxbLink.class, namespace = XMLNamespace.DM_NAMESPACE)
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
