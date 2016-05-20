/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="format", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbFormat extends JaxbObject {
    public JaxbFormat() {
        super();
    }

    public JaxbFormat(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "format";
    }
}
