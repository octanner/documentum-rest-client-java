/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;

@XmlRootElement(name="relation-type")
public class JaxbRelationType extends JaxbObject {
    public JaxbRelationType() {
        super();
    }

    public JaxbRelationType(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "relation-type";
    }
}
