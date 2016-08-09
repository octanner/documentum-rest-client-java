/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Permission;

@XmlRootElement(name = "permission")
public class JaxbPermission extends JaxbDmLinkableBase implements Permission {
    private String accessor;
    private String basicPermission;
    private String extendPermissions;
    private String applicationPermission;

    @Override
    @XmlAttribute
    public String getAccessor() {
        return accessor;
    }

    public void setAccessor(String accessor) {
        this.accessor = accessor;
    }

    @Override
    @XmlAttribute(name="basic-permission")
    public String getBasicPermission() {
        return basicPermission;
    }

    public void setBasicPermission(String basicPermission) {
        this.basicPermission = basicPermission;
    }

    @Override
    @XmlAttribute(name="extend-permissions")
    public String getExtendPermissions() {
        return extendPermissions;
    }

    public void setExtendPermissions(String extendPermissions) {
        this.extendPermissions = extendPermissions;
    }

    @Override
    @XmlAttribute(name="application-permission")
    public String getApplicationPermission() {
        return applicationPermission;
    }

    public void setApplicationPermission(String applicationPermission) {
        this.applicationPermission = applicationPermission;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbPermission that = (JaxbPermission) obj;
        return Equals.equal(accessor, that.accessor)
                && Equals.equal(applicationPermission, that.applicationPermission)
                && Equals.equal(basicPermission, that.basicPermission)
                && Equals.equal(extendPermissions, that.extendPermissions)
                && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessor, applicationPermission, basicPermission, extendPermissions);
    }
}