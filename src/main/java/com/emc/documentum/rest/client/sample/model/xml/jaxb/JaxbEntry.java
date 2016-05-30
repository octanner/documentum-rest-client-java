/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.w3c.dom.Element;

import com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbContext;
import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Author;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Inlineable;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

@XmlRootElement(name = "entry", namespace = XMLNamespace.ATOM_NAMESPACE)
public class JaxbEntry<T extends Linkable> extends JaxbAtomLinkableBase implements Entry<T> {
    private String id;
    private String title;
    private String updated;
    private String summary;
    private List<Author> authors;
    private EntryContent<T> content;
    private String published;
    
    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public EntryContent<T> getContent() {
        return content;
    }

    public void setContent(EntryContent<T> content) {
        this.content= content;
    }

    @Override
    @XmlElement(name="author", type=JaxbAuthor.class)
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String getContentSrc() {
        return content==null?null:content.getSrc();
    }

    @Override
    public String getContentType() {
        return content==null?null:content.getContentType();
    }

    @Override
    public String getPublished() {
        return this.published;
    }
    public void setPublished(String published) {
        this.published = published;
    }

    @Override
    public T getContentObject() {
        return content==null?null:content.getContent();
    }
    
    @XmlRootElement(name="content")
    public static class EntryContent<T extends Linkable> implements Inlineable {
        private String src;
        private String type;
        private Element element;
        
        @XmlAnyElement
        public Element getElement() {
            return element;
        }
        public void setElement(Element element) {
            this.element = element;
        }
        @XmlAttribute
        public String getSrc() {
            return src;
        }
        public void setSrc(String src) {
            this.src = src;
        }
        @XmlAttribute(name="type")
        public String getContentType() {
            return type;
        }
        public void setContentType(String type) {
            this.type = type;
        }
        
        @Override
        public boolean equals(Object obj) {
            EntryContent<?> that = (EntryContent<?>)obj;
            return Equals.equal(src, that.src) 
                && Equals.equal(type, that.type);
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public T getContent() {
            return element==null?null:(T)DCTMJaxbContext.unmarshal(element);
        }
    }

    @Override
    public boolean equals(Object obj) {
        JaxbEntry<?> that = (JaxbEntry<?>)obj;
        return Equals.equal(id, that.id) 
            && Equals.equal(title, that.title)
            && Equals.equal(updated, that.updated)
            && Equals.equal(summary, that.summary)
            && Equals.equal(content, that.content)
            && Equals.equal(links, that.links)
            && Equals.equal(authors, that.authors)
            && Equals.equal(published, that.published);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, updated, summary, content);
    }
}