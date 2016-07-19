/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="acl", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbAcl extends JaxbObject {
    public JaxbAcl() {
        super();
    }

    public JaxbAcl(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "acl";
    }
}
