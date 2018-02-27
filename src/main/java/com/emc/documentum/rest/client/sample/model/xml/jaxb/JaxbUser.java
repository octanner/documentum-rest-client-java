/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;

@XmlRootElement(name="user")
public class JaxbUser extends JaxbObject {
    public JaxbUser() {
        super();
    }

    public JaxbUser(RestObject object) {
        super(object);
    }

    public JaxbUser(String href) {
        super(href);
    }

    @Override
    public String getName() {
        return "user";
    }
}
