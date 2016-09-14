/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;

@XmlRootElement(name="group")
public class JaxbGroup extends JaxbObject {
    public JaxbGroup() {
        super();
    }

    public JaxbGroup(RestObject object) {
        super(object);
    }

    public JaxbGroup(String href) {
        super(href);
    }

    @Override
    public String getName() {
        return "group";
    }
}
