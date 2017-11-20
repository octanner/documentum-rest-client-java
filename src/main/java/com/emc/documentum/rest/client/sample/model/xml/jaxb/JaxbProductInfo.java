/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.model.RestObject;

@XmlRootElement(name="product-info")
public class JaxbProductInfo extends JaxbObject {
    public JaxbProductInfo() {
        super();
    }

    public JaxbProductInfo(RestObject object) {
        super(object);
    }

    @Override
    public String getName() {
        return "product-info";
    }
}
