/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.Lifecycle;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbLifecycleState.JaxbModule;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbLifecycleState.JaxbProcedure;

@XmlRootElement(name = "lifecycle")
public class JaxbLifecycle extends JaxbDmLinkableBase implements Lifecycle {
    private String id;
    private String name;
    private String title;
    private String subject;
    private List<String> keywords;
    private String owner;
    private Date created;
    private Date modified;
    private String implementationType;
    private List<String> versionLabels;
    private List<String> aliasSets;
    private List<TypeInclusion> typeInclusions;
    private List<LifecycleState> states;
    private Module userValidationService;
    private Procedure appValidation;
    private String status;
    private String statusMessage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @XmlElement(name="implementation-type")
    public String getImplementationType() {
        return implementationType;
    }

    public void setImplementationType(String implementationType) {
        this.implementationType = implementationType;
    }

    @XmlElementWrapper(name="version-labels")
    @XmlElement(name="version-label")
    public List<String> getVersionLabels() {
        return versionLabels;
    }

    public void setVersionLabels(List<String> versionLabels) {
        this.versionLabels = versionLabels;
    }

    @XmlElementWrapper(name="alias-sets")
    @XmlElement(name="alias-set")
    public List<String> getAliasSets() {
        return aliasSets;
    }

    public void setAliasSets(List<String> aliasSets) {
        this.aliasSets = aliasSets;
    }

    @XmlElementWrapper(name="type-inclusions")
    @XmlElement(name="type-inclusion", type=JaxbTypeInclusion.class)
    public List<TypeInclusion> getTypeInclusions() {
        return typeInclusions;
    }

    public void setTypeInclusions(List<TypeInclusion> typeInclusions) {
        this.typeInclusions = typeInclusions;
    }

    @XmlElementWrapper
    @XmlElement(name="state", type=JaxbLifecycleState.class)
    public List<LifecycleState> getStates() {
        return states;
    }

    public void setStates(List<LifecycleState> states) {
        this.states = states;
    }

    @XmlElement(name="user-validation-service", type=JaxbModule.class)
    public Module getUserValidationService() {
        return userValidationService;
    }

    public void setUserValidationService(Module userValidationService) {
        this.userValidationService = userValidationService;
    }

    @XmlElement(name="app-validation", type=JaxbProcedure.class)
    public Procedure getAppValidation() {
        return appValidation;
    }

    public void setAppValidation(Procedure appValidation) {
        this.appValidation = appValidation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlElement(name="status-message")
    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbLifecycle that = (JaxbLifecycle) obj;
        return Equals.equal(id, that.id)
                && Equals.equal(name, that.name)
                && Equals.equal(title, that.title)
                && Equals.equal(subject, that.subject)
                && Equals.equal(keywords, that.keywords)
                && Equals.equal(owner, that.owner)
                && Equals.equal(created, that.created)
                && Equals.equal(modified, that.modified)
                && Equals.equal(implementationType, that.implementationType)
                && Equals.equal(versionLabels, that.versionLabels)
                && Equals.equal(aliasSets, that.aliasSets)
                && Equals.equal(typeInclusions, that.typeInclusions)
                && Equals.equal(states, that.states)
                && Equals.equal(userValidationService, that.userValidationService)
                && Equals.equal(appValidation, that.appValidation)
                && Equals.equal(status, that.status)
                && Equals.equal(statusMessage, that.statusMessage)
                && Equals.equal(links, that.links);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, states);
    }
    
    @XmlRootElement(name = "type-inclusion")
    public static class JaxbTypeInclusion implements TypeInclusion {
        private String type;
        private boolean includeSubtypes;
        
        public String getType() {
            return type;
        }
        public void setType(String type) {
            this.type = type;
        }
        @XmlElement(name="include-subtypes")
        public boolean isIncludeSubtypes() {
            return includeSubtypes;
        }
        public void setIncludeSubtypes(boolean includeSubtypes) {
            this.includeSubtypes = includeSubtypes;
        }
        @Override
        public boolean equals(Object obj) {
            JaxbTypeInclusion that = (JaxbTypeInclusion) obj;
            return Equals.equal(type, that.type)
                    && Equals.equal(includeSubtypes, that.includeSubtypes);
        }
        @Override
        public int hashCode() {
            return Objects.hash(type, includeSubtypes);
        }
    }
}