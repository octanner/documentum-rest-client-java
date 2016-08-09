/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;

@XmlRootElement(name="folder")
public class JaxbFolder extends JaxbObject {
    public JaxbFolder() {
        super();
    }

    public JaxbFolder(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "folder";
    }
}
