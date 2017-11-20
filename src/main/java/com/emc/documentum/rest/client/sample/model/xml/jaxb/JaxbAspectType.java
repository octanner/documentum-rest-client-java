/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;

@XmlRootElement(name="aspect-type")
public class JaxbAspectType extends JaxbObject {
    public JaxbAspectType() {
        super();
    }

    public JaxbAspectType(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "aspect-type";
    }
}
