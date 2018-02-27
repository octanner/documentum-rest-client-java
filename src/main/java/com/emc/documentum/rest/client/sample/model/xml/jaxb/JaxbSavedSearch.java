/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.emc.documentum.rest.client.sample.model.SavedSearch;
import com.emc.documentum.rest.client.sample.model.Search;

import static com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbContext.marshal;
import static com.emc.documentum.rest.client.sample.client.impl.jaxb.DCTMJaxbContext.unmarshal;

@XmlRootElement(name = "saved-search")
public class JaxbSavedSearch extends JaxbObject implements SavedSearch {
    private String queryDocument;

    public JaxbSavedSearch() {
    }
    
    public JaxbSavedSearch(SavedSearch savedSearch) {
        super(savedSearch);
        setSearch(savedSearch.getSearch());
    }

    @Override
    public String getName() {
        return "saved-search";
    }

    @Override
    @XmlElement(name = "query-document")
    public String getQueryDocument() {
        return queryDocument;
    }
    
    public void setQueryDocument(String queryDocument) {
        this.queryDocument = queryDocument;
    }
    
    @Override
    @XmlTransient
    public Search getSearch() {
        return queryDocument==null?null:(Search)unmarshal(queryDocument);
    }

    public void setSearch(Search search) {
        queryDocument = search == null?null:marshal(search);
    }
}
