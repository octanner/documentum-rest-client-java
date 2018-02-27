/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Permission;
import com.emc.documentum.rest.client.sample.model.PermissionSet;

@XmlRootElement(name = "permission-set")
public class JaxbPermissionSet extends JaxbDmLinkableBase implements PermissionSet {
    private List<Permission> permitted;
    private List<Permission> restricted;
    private List<String> requiredGroup;
    private List<String> requiredGroupSet;
    
    @Override
    @XmlElementWrapper
    @XmlElement(name = "permission", type = JaxbPermission.class)
    public List<Permission> getPermitted() {
        return permitted;
    }

    public void setPermitted(List<Permission> permitted) {
        this.permitted = permitted;
    }

    @Override
    @XmlElementWrapper
    @XmlElement(name = "permission", type = JaxbPermission.class)
    public List<Permission> getRestricted() {
        return restricted;
    }

    public void setRestricted(List<Permission> restricted) {
        this.restricted = restricted;
    }

    @Override
    @XmlElementWrapper(name = "required-group")
    @XmlElement(name = "group")
    public List<String> getRequiredGroup() {
        return requiredGroup;
    }

    public void setRequiredGroup(List<String> requiredGroup) {
        this.requiredGroup = requiredGroup;
    }

    @Override
    @XmlElementWrapper(name = "required-group-set")
    @XmlElement(name = "group")
    public List<String> getRequiredGroupSet() {
        return requiredGroupSet;
    }

    public void setRequiredGroupSet(List<String> requiredGroupSet) {
        this.requiredGroupSet = requiredGroupSet;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbPermissionSet that = (JaxbPermissionSet) obj;
        return Equals.equal(permitted, that.permitted)
                && Equals.equal(restricted, that.restricted)
                && Equals.equal(requiredGroup, that.requiredGroup)
                && Equals.equal(requiredGroupSet, that.requiredGroupSet)
                && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permitted, restricted, requiredGroup, requiredGroupSet);
    }
}