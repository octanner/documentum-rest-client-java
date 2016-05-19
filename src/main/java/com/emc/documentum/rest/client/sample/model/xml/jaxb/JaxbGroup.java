/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="group", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbGroup extends JaxbObject {
    public JaxbGroup() {
        super();
    }

    public JaxbGroup(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "group";
    }
}
