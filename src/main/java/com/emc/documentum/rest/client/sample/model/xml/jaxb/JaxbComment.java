/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Comment;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="comment", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbComment extends JaxbDmLinkableBase implements Comment {
    private String objectId;
    private String commentId;
    private String ownerName;
    private String creationDate;
    private String modifyDate;
    private String contentValue;
    private String parentId;
    private String title;
    private boolean canDelete;
    private boolean canReply;

    public JaxbComment() {
    }
    
    public JaxbComment(Comment comment) {
        this.objectId = comment.getObjectId();
        this.commentId = comment.getCommentId();
        this.ownerName = comment.getOwnerName();
        this.creationDate = comment.getCreationDate();
        this.modifyDate = comment.getModifyDate();
        this.contentValue = comment.getContentValue();
        this.parentId = comment.getParentId();
        this.title = comment.getTitle();
        this.canDelete = comment.isCanDelete();
        this.canReply = comment.isCanReply();
    }
    
    @Override
    @XmlElement(name="object-id")
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    @XmlElement(name="comment-id")
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @Override
    @XmlElement(name="owner-name")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @Override
    @XmlElement(name="creation-date")
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    @XmlElement(name="modify-date")
    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    @XmlElement(name="content-value")
    public String getContentValue() {
        return contentValue;
    }

    public void setContentValue(String contentValue) {
        this.contentValue = contentValue;
    }

    @Override
    @XmlElement(name="parent-id")
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    @XmlElement(name="can-delete")
    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    @XmlElement(name="can-reply")
    public boolean isCanReply() {
        return canReply;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbComment that = (JaxbComment) obj;
        return Equals.equal(objectId, that.objectId)
                && Equals.equal(commentId, that.commentId)
                && Equals.equal(ownerName, that.ownerName)
                && Equals.equal(creationDate, that.creationDate)
                && Equals.equal(modifyDate, that.modifyDate)
                && Equals.equal(creationDate, that.creationDate)
                && Equals.equal(contentValue, that.contentValue)
                && Equals.equal(parentId, that.parentId)
                && Equals.equal(title, that.title)
                && Equals.equal(canDelete, that.canDelete)
                && Equals.equal(canReply, that.canReply)
                && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objectId, commentId, ownerName, contentValue, parentId, title);
    }
}
