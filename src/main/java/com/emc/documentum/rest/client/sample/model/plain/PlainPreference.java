/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.plain;

import java.util.List;

import com.emc.documentum.rest.client.sample.model.Preference;

/**
 * the plain Preference implementation which has properties only
 * normally used when create/update the Preference
 */
public class PlainPreference extends PlainLinkableBase implements Preference {
    private String client;
    private String owner;
    private String title;
    private String subject;
    private List<String> keywords;
    private String creationDate;
    private String modifyDate;
    private String preference;

    @Override
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    @Override
    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
