/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.batch.SettableInclude;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="Include", namespace=XMLNamespace.XOP_NAMESPACE)
public class JaxbBatchInclude implements SettableInclude {
    private String href;

    public JaxbBatchInclude() {
    }
    
    public JaxbBatchInclude(String href) {
       setHref(href);
    }

    @Override
    @XmlAttribute
    public String getHref() {
        return href;
    }

    @Override
    public void setHref(String href) {
        if(href.startsWith("cid:")) {
            this.href = href;
        } else {
            this.href = "cid:" + href;   
        }
    }

    @Override
    @XmlTransient
    public String getRawHref() {
        return href == null ? null : href.substring("cid:".length());
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbBatchInclude that = (JaxbBatchInclude)obj;
        return Equals.equal(href, that.href);
    }

    @Override
    public int hashCode() {
        return Objects.hash(href);
    }
}
