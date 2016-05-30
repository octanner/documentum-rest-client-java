package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.SearchEntry;
import com.emc.documentum.rest.client.sample.model.xml.XMLNamespace;

public class JaxbSearchEntry<T extends Linkable> extends JaxbEntry<T> implements SearchEntry<T>{
    private String score;
    private List<String> terms;

    @Override
    @XmlElement(namespace=XMLNamespace.RELEVANCE_NAMESPACE)
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @XmlElementWrapper(name = "terms")
    @XmlElement(name = "term", namespace=XMLNamespace.DM_NAMESPACE)
    @Override
    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbSearchEntry<?> that = (JaxbSearchEntry<?>)obj;
        return super.equals(that) 
            && Equals.equal(terms, that.terms)
            && Equals.equal(score, that.score);
    }
}
