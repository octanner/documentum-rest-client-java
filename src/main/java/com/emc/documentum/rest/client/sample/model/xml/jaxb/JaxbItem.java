/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;

@XmlRootElement(name="item")
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
