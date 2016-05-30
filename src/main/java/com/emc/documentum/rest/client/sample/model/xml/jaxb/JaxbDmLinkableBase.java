package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkableBase;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

public abstract class JaxbDmLinkableBase extends LinkableBase {
    protected List<Link> links;

    @Override
    @XmlElementWrapper(name = "links")
    @XmlElement(name = "link", type = JaxbLink.class, namespace = XMLNamespace.DM_NAMESPACE)
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }
}
