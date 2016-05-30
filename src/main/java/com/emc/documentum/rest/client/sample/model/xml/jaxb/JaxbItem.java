/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="item", namespace=XMLNamespace.DM_NAMESPACE)
public class JaxbItem extends JaxbObject {
    public JaxbItem() {
        super();
    }

    public JaxbItem(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "item";
    }
}
