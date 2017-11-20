/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;

@XmlRootElement(name="relation")
public class JaxbRelation extends JaxbObject {
    public JaxbRelation() {
        super();
    }

    public JaxbRelation(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "relation";
    }
}
