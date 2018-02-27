/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name="feed", namespace=XMLNamespace.ATOM_NAMESPACE)
public class JaxbFeed<T extends Linkable> extends JaxbFeedBase<T, Entry<T>> implements Feed<T> {
    private List<Entry<T>> entries;

    @Override
    @XmlElement(name="entry", type=JaxbEntry.class)
    public List<Entry<T>> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry<T>> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbFeed<?> that = (JaxbFeed<?>)obj;
        return super.equals(obj) 
            && Equals.equal(entries, that.entries);
    }
}
