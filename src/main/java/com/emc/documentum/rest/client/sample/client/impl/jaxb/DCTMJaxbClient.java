/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl.jaxb;

import java.io.OutputStream;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.impl.AbstractRestTemplateClient;
import com.emc.documentum.rest.client.sample.client.util.Collections;
import com.emc.documentum.rest.client.sample.client.util.Headers;
import com.emc.documentum.rest.client.sample.client.util.SupportedMediaTypes;
import com.emc.documentum.rest.client.sample.client.util.UriHelper;
import com.emc.documentum.rest.client.sample.model.AuditPolicy;
import com.emc.documentum.rest.client.sample.model.AuditTrail;
import com.emc.documentum.rest.client.sample.model.AvailableAuditEvents;
import com.emc.documentum.rest.client.sample.model.Comment;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.FolderLink;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.Lifecycle;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.emc.documentum.rest.client.sample.model.ObjectLifecycle;
import com.emc.documentum.rest.client.sample.model.Permission;
import com.emc.documentum.rest.client.sample.model.PermissionSet;
import com.emc.documentum.rest.client.sample.model.Preference;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.emc.documentum.rest.client.sample.model.SavedSearch;
import com.emc.documentum.rest.client.sample.model.Search;
import com.emc.documentum.rest.client.sample.model.SearchFeed;
import com.emc.documentum.rest.client.sample.model.SearchTemplate;
import com.emc.documentum.rest.client.sample.model.ValueAssistant;
import com.emc.documentum.rest.client.sample.model.ValueAssistantRequest;
import com.emc.documentum.rest.client.sample.model.VirtualDocumentNode;
import com.emc.documentum.rest.client.sample.model.batch.Batch;
import com.emc.documentum.rest.client.sample.model.batch.Capabilities;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbAcl;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbAspectType;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbAuditEvent;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbAuditPolicy;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbAuditTrail;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbAvailableAuditEvents;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbBatch;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbBatchCapabilities;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbCabinet;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbComment;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbContent;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbDocument;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbFeed;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbFolder;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbFolderLink;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbFormat;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbGroup;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbHomeDocument;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbLifecycle;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbNetworkLocation;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbObjectAspects;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbObjectLifecycle;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbPermission;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbPermissionSet;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbPreference;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbProductInfo;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbRelation;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbRelationType;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbRepository;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSavedSearch;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearchFeed;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSearchTemplate;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSubscribers;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSysObject;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbType;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbUser;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbValueAssistance;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbValueAssistantRequest;

import static com.emc.documentum.rest.client.sample.client.util.Headers.ACCEPT_ATOM_HEADERS;
import static com.emc.documentum.rest.client.sample.client.util.SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ABOUT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ACLS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ASPECT_TYPES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ASSIS_VALUES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ASSOCIATIONS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.AUDIT_POLICIES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.AVAILABLE_AUDIT_EVENTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.BATCH_CAPABILITIES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CABINETS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CANCEL_CHECKOUT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CANCEL_DEMOTION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CANCEL_PROMOTION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CANCEL_RESUMPTION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CANCEL_SUSPENSION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CHECKIN_BRANCH_VERSION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CHECKIN_NEXT_MAJOR;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CHECKIN_NEXT_MINOR;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CHECKOUT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.COMMENTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CONTENTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CURRENT_USER_PREFERENCES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.DELETE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.DEMATERIALIZE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.DEMOTION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.DOCUMENTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.EDIT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.FOLDERS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.FORMATS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.GROUPS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.LIFECYCLES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.MATERIALIZE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.NETWORK_LOCATIONS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.OBJECTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.OBJECT_ASPECTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.OBJECT_LIFECYCLE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.PRIMARY_CONTENT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.PROMOTION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.RECENT_TRAILS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.REGISTERED_AUDIT_EVENTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.RELATIONS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.RELATION_TYPES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.REPLIES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.REPOSITORIES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.RESUMPTION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SAVED_SEARCHES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SAVED_SEARCH_SAVED_RESULTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SEARCH;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SEARCH_EXECUTION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SEARCH_TEMPLATES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SELF;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SHARED_PARENT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SUBSCRIBE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SUBSCRIPTIONS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SUSPENSION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.TYPES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.UNSUBSCRIBE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.USERS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.VERSIONS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.VIRTUAL_DOCUMENT_NODES;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.MediaType.APPLICATION_ATOM_XML_VALUE;

/**
 * the DCTMRestClient implementation by JAXB xml support
 */
@NotThreadSafe
public class DCTMJaxbClient extends AbstractRestTemplateClient implements DCTMRestClient, Cloneable {
    public DCTMJaxbClient(String contextRoot, String repositoryName,
            String username, String password, boolean useFormatExtension) {
        super(contextRoot, repositoryName, username, password, useFormatExtension);
    }

    public DCTMJaxbClient(String contextRoot, String repositoryName,
            String username, String password, boolean useFormatExtension, boolean ignoreSslWarning) {
        super(contextRoot, repositoryName, username, password, useFormatExtension, ignoreSslWarning);
    }
    
    @Override
    public DCTMJaxbClient clone() {
        return clone(new DCTMJaxbClient(contextRoot, repositoryName, username, password, useFormatExtension, ignoreSslWarning));
    }
    
    @Override
    public HomeDocument getHomeDocument() {
        if(homeDocument == null) {
            homeDocument = get(getHomeDocumentUri(), Headers.ACCEPT_XML_HOME_DOCUMENT, JaxbHomeDocument.class);
        }
        return homeDocument;
    }
    
    @Override
    public RestObject getProductInfo() {
        if(productInfo == null) {
            productInfo = get(getHomeDocument().getHref(ABOUT), false, JaxbProductInfo.class);
        }
        return productInfo;
    }

    @Override
    public Feed<Repository> getRepositories() {
        if(repositories == null) {
            repositories = feed(getHomeDocument(), REPOSITORIES);
        }
        return repositories;
    }
    
    @Override
    public Repository getRepository() {
        return getRepository(JaxbRepository.class);
    }
    
    @Override
    public Feed<RestObject> dql(String dql, String... params) {
        return feed(SELF, UriHelper.append(params, "dql", dql));
    }
    
    @Override
    public SearchFeed<RestObject> search(String search, String... params) {
        return get(getRepository().getHref(SEARCH), true, JaxbSearchFeed.class, UriHelper.append(params, "q", search));
    }
    
    @Override
    public SearchFeed<RestObject> search(Search search, String... params) {
        return post(getRepository().getHref(SEARCH), search, new Headers().accept(MediaType.APPLICATION_ATOM_XML_VALUE).contentType(SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE).toHttpHeaders(), JaxbSearchFeed.class, params);
    }

    @Override
    public RestObject createCabinet(RestObject cabinetToCreate) {
        return post(getRepository().getHref(CABINETS), new JaxbCabinet(cabinetToCreate), JaxbCabinet.class);
    }

    @Override
    public Feed<RestObject> getCabinets(String... params) {
        return feed(CABINETS, params);
    }
    
    @Override
    public RestObject getCabinet(String cabinet, String... params) {
        return getCabinet(cabinet, JaxbCabinet.class, params);
    }
    
    @Override
    public Feed<RestObject> getFolders(Linkable parent, String... params) {
        return feed(parent, FOLDERS, params);
    }
    
    @Override
    public Feed<RestObject> getObjects(Linkable parent, String... params) {
        return feed(parent, OBJECTS, params);
    }
    
    @Override
    public Feed<RestObject> getDocuments(Linkable parent, String... params) {
        return feed(parent, DOCUMENTS, params);
    }
    
    @Override
    public RestObject createFolder(Linkable parent, RestObject newFolder, String... params) {
        return post(parent.getHref(FOLDERS), new JaxbFolder(newFolder), JaxbFolder.class, params);
    }
    
    @Override
    public RestObject getFolder(String folderUri, String... params) {
        return get(folderUri, false, JaxbFolder.class, params);
    }
    
    @Override
    public RestObject createObject(Linkable parent, LinkRelation rel, RestObject objectToCreate, Object content, String contentMediaType, String... params) {
        return post(parent.getHref(rel), new JaxbSysObject(objectToCreate), content, contentMediaType, JaxbSysObject.class, params);
    }
    
    @Override
    public RestObject createObject(Linkable parent, LinkRelation rel, RestObject objectToCreate, List<Object> contents, List<String> contentMediaTypes, String... params) {
        return post(parent.getHref(rel), new JaxbSysObject(objectToCreate), contents, contentMediaTypes, JaxbSysObject.class, params);
    }
    
    @Override
    public RestObject getObject(String objectUri, String... params) {
        return get(objectUri, false, JaxbSysObject.class, params);
    }
    
    @Override
    public RestObject createDocument(Linkable parent, RestObject objectToCreate, Object content, String contentMediaType, String... params) {
        return post(parent.getHref(DOCUMENTS), new JaxbDocument(objectToCreate), content, contentMediaType, JaxbDocument.class, params);
    }
    
    @Override
    public RestObject createDocument(Linkable parent, RestObject objectToCreate, List<Object> contents, List<String> contentMediaTypes, String... params) {
        return post(parent.getHref(DOCUMENTS), new JaxbDocument(objectToCreate), contents, contentMediaTypes, JaxbDocument.class, params);
    }

    @Override
    public RestObject getDocument(String documentUri, String... params) {
        return get(documentUri, false, JaxbDocument.class, params);
    }
    
    @Override
    public RestObject update(RestObject oldObject, RestObject newObject, String... params) {
        return update(oldObject, EDIT, newObject, HttpMethod.POST, params);
    }
    
    @Override
    public RestObject createContent(RestObject object, Object content, String mediaType, String... params) {
        return post(object.getHref(CONTENTS), content, mediaType, JaxbContent.class, params);
    }
    
    @Override
    public RestObject getPrimaryContent(RestObject object, String... params) {
        return getContent(object.getHref(PRIMARY_CONTENT), params);
    }
    
    @Override
    public RestObject getContent(String contentUri, String... params) {
        return get(contentUri, false, JaxbContent.class, params);
    }
    
    @Override
    public Feed<RestObject> getContents(RestObject object, String... params) {
        return feed(object, CONTENTS, params);
    }
    
    @Override
    public RestObject checkout(RestObject object, String... params) {
        return put(object.getHref(CHECKOUT), JaxbSysObject.class, params);
    }
    
    @Override
    public void cancelCheckout(RestObject object) {
        delete(object.getHref(CANCEL_CHECKOUT));
    }
    
    @Override
    public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, String... params) {
        return post(oldObject.getHref(CHECKIN_NEXT_MAJOR), new JaxbSysObject(newObject), JaxbSysObject.class, params);
    }
    
    @Override
    public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, Object content, String contentMediaType, String... params) {
        return post(oldObject.getHref(CHECKIN_NEXT_MAJOR), newObject==null?null:new JaxbSysObject(newObject), content, contentMediaType, JaxbSysObject.class, params);
    }

    @Override
    public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, List<Object> contents,
            List<String> contentMediaTypes, String... params) {
        return post(oldObject.getHref(CHECKIN_NEXT_MAJOR), newObject==null?null:new JaxbSysObject(newObject), contents, contentMediaTypes, JaxbSysObject.class, params);
    }

    @Override
    public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, String... params) {
        return post(oldObject.getHref(CHECKIN_NEXT_MINOR), new JaxbSysObject(newObject), JaxbSysObject.class, params);
    }

    @Override
    public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, Object content, String contentMediaType, String... params) {
        return post(oldObject.getHref(CHECKIN_NEXT_MINOR), newObject==null?null:new JaxbSysObject(newObject), content, contentMediaType, JaxbSysObject.class, params);
    }
    
    @Override
    public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, List<Object> contents,
            List<String> contentMediaTypes, String... params) {
        return post(oldObject.getHref(CHECKIN_NEXT_MINOR), newObject==null?null:new JaxbSysObject(newObject), contents, contentMediaTypes, JaxbSysObject.class, params);
    }

    @Override
    public RestObject checkinBranch(RestObject oldObject, RestObject newObject, String... params) {
        return post(oldObject.getHref(CHECKIN_BRANCH_VERSION), new JaxbSysObject(newObject), JaxbSysObject.class, params);
    }
    
    @Override
    public RestObject checkinBranch(RestObject oldObject, RestObject newObject, Object content, String contentMediaType, String... params) {
        return post(oldObject.getHref(CHECKIN_BRANCH_VERSION), newObject==null?null:new JaxbSysObject(newObject), content, contentMediaType, JaxbSysObject.class, params);
    }

    @Override
    public RestObject checkinBranch(RestObject oldObject, RestObject newObject, List<Object> contents, List<String> contentMediaTypes, String... params) {
        return post(oldObject.getHref(CHECKIN_BRANCH_VERSION), newObject==null?null:new JaxbSysObject(newObject), contents, contentMediaTypes, JaxbSysObject.class, params);
    }
    
    @Override
    public Feed<RestObject> getVersions(RestObject object, String... params) {
        return feed(object, VERSIONS, params);
    }
    
    @Override
    public RestObject materialize(RestObject oldObject) {
        return put(oldObject.getHref(MATERIALIZE), JaxbSysObject.class);
    }

    @Override
    public void dematerialize(RestObject oldObject) {
        delete(oldObject.getHref(DEMATERIALIZE));
    }
    
    @Override
    public RestObject reparent(RestObject oldObject, RestObject newParent) {
        try {
            RestObject newRestObject = newRestObject(oldObject, new PlainRestObject(newParent.getHref(SELF)));
            return post(oldObject.getHref(SHARED_PARENT), newRestObject, newRestObject.getClass());
        } catch (Exception e) {
            throw new IllegalArgumentException(getModelClass(oldObject).getName());
        }
    }
    
    @Override
    public RestType getType(String name, String... params) {
        return get(getRepository().getHref(TYPES)+"/"+name, false, JaxbType.class, params);
    }
    
    @Override
    public Feed<RestType> getTypes(String... params) {
        return feed(TYPES, params);
    }
    
    @Override
    public Feed<RestObject> getAspectTypes(String... params) {
        return feed(ASPECT_TYPES, params);
    }
    
    @Override
    public RestObject getAspectType(String aspectType, String... params) {
        return get(getRepository().getHref(ASPECT_TYPES)+"/"+aspectType, false, JaxbAspectType.class, params);
    }
    
    @Override
    public ValueAssistant getValueAssistant(RestType type, ValueAssistantRequest request, String... params) {
        return post(type.getHref(ASSIS_VALUES), new JaxbValueAssistantRequest(request), JaxbValueAssistance.class, params);
    }

    @Override
    public ObjectAspects attach(RestObject object, String... aspects) {
        return post(object.getHref(OBJECT_ASPECTS), new JaxbObjectAspects(aspects), JaxbObjectAspects.class);        
    }

    @Override
    public void detach(ObjectAspects objectAspects, String aspect) {
        delete(objectAspects.getHref(DELETE, aspect));
    }
    
    @Override
    public ObjectAspects getObjectAspects(RestObject object, String... params) {
        return get(object.getHref(OBJECT_ASPECTS), JaxbObjectAspects.class, params);        
    }
    
    @Override
    public Feed<RestObject> getUsers(String... params) {
        return getUsers(getRepository(), params);
    }
    
    @Override
    public Feed<RestObject> getUsers(Linkable parent, String... params) {
        return feed(parent, USERS, params);
    }

    @Override
    public Feed<RestObject> getGroups(String... params) {
        return getGroups(getRepository(), params);
    }
    
    @Override
    public Feed<RestObject> getGroups(Linkable parent, String... params) {
        return feed(parent, GROUPS, params);
    }

    @Override
    public RestObject getCurrentUser(String... params) {
        return get(getRepository().getHref(LinkRelation.CURRENT_USER), false, JaxbUser.class, params);
    }

    @Override
    public RestObject getDefaultFolder(String... params) {
        return get(getCurrentUser().getHref(LinkRelation.DEFAULT_FOLDER), false, JaxbFolder.class, params);
    }

    @Override
    public RestObject getUser(String userUri, String... params) {
        return get(userUri, false, JaxbUser.class, params);
    }
    
    @Override
    public RestObject getGroup(String groupUri, String... params) {
        return get(groupUri, false, JaxbGroup.class, params);
    }
    
    @Override
    public RestObject createUser(RestObject userToCreate) {
        return post(getRepository().getHref(USERS), new JaxbUser(userToCreate), JaxbUser.class);
    }
    
    @Override
    public RestObject createGroup(RestObject groupToCreate) {
        return post(getRepository().getHref(GROUPS), new JaxbGroup(groupToCreate), JaxbGroup.class);
    }
    
    @Override
    public void addUserToGroup(RestObject group, RestObject user) {
        post(group.getHref(USERS), new JaxbUser(user.getHref(SELF)), null);
    }
    
    @Override
    public void addGroupToGroup(RestObject group, RestObject subGroup) {
        post(group.getHref(GROUPS), new JaxbGroup(subGroup.getHref(SELF)), null);
    }

    @Override
    public Feed<RestObject> getRelationTypes(String... params) {
        return feed(RELATION_TYPES, params);
    }
    
    @Override
    public RestObject getRelationType(String uri, String... params) {
        return get(uri, false, JaxbRelationType.class, params);
    }

    @Override
    public Feed<RestObject> getRelations(String... params) {
        return feed(RELATION_TYPES, params);
    }
    
    @Override
    public RestObject getRelation(String uri, String... params) {
        return get(uri, false, JaxbRelation.class, params);
    }
    
    @Override
    public RestObject createRelation(RestObject object) {
        return post(getRepository().getHref(RELATIONS), new JaxbRelation(object), JaxbRelation.class);
    }
    
    @Override
    public Feed<RestObject> getFormats(String... params) {
        return feed(FORMATS, params);
    }
    
    @Override
    public RestObject getFormat(String uri, String... params) {
        return get(uri, false, JaxbFormat.class, params);
    }
    
    @Override
    public Feed<RestObject> getNetworkLocations(String... params) {
        return feed(NETWORK_LOCATIONS, params);
    }
    
    @Override
    public RestObject getNetworkLocation(String uri, String... params) {
        return get(uri, false, JaxbNetworkLocation.class, params);
    }
    
    @Override
    public Feed<FolderLink> getFolderLinks(Linkable object, LinkRelation rel, String... params) {
        return feed(object, rel, params);
    }
    
    @Override
    public FolderLink getFolderLink(String uri, String... params) {
        return get(uri, false, JaxbFolderLink.class, params);
    }
    
    @Override
    public FolderLink move(FolderLink oldLink, FolderLink newLink, String... params) {
        return put(oldLink.getHref(SELF), new JaxbFolderLink(newLink), JaxbFolderLink.class, params);
    }
    
    @Override
    public FolderLink link(Linkable object, LinkRelation rel, FolderLink link) {
        return post(object.getHref(rel), new JaxbFolderLink(link), JaxbFolderLink.class);
    }
    
    @Override
    public Feed<RestObject> getAcls(String... params) {
        return feed(ACLS, params);
    }
    
    @Override
    public Feed<RestObject> getAclAssociations(Linkable acl, String... params) {
        return feed(acl, ASSOCIATIONS, params);
    }
    
    @Override
    public RestObject getAcl(String uri, String... params) {
        return get(uri, false, JaxbAcl.class, params);
    }
    
    @Override
    public RestObject createAcl(RestObject object) {
        return post(getRepository().getHref(ACLS), new JaxbAcl(object), JaxbAcl.class);
    }
    
    @Override
    public Capabilities getBatchCapabilities() {
        return get(getRepository().getHref(BATCH_CAPABILITIES), false, JaxbBatchCapabilities.class);
    }
    
    @Override
    public Batch createBatch(Batch batch) {
        return post(batch, JaxbBatch.class);
    }
    
    @Override
    public Feed<Preference> getPreferences(String... params) {
        return feed(CURRENT_USER_PREFERENCES, params);
    }

    @Override
    public Preference getPreference(String uri, String... params) {
        return get(uri, false, JaxbPreference.class, params);
    }

    @Override
    public Preference createPreference(Preference preference) {
        return post(getRepository().getHref(CURRENT_USER_PREFERENCES), new JaxbPreference(preference), JaxbPreference.class);
    }

    @Override
    public Preference updatePreference(Preference oldPreference, Preference newPreference) {
        return post(oldPreference.self(), new JaxbPreference(newPreference), JaxbPreference.class);
    }

    @Override
    public Permission getPermission(Linkable linkable, String... params) {
        return get(linkable.getHref(LinkRelation.PERMISSIONS), false, JaxbPermission.class, params);
    }
    
    @Override
    public PermissionSet getPermissionSet(Linkable linkable, String... params) {
        return get(linkable.getHref(LinkRelation.PERMISSION_SET), false, JaxbPermissionSet.class, params);
    }
    
    @Override
    public Feed<Comment> getComments(Linkable parent, String... params) {
        return feed(parent, COMMENTS, params);
    }

    @Override
    public Comment createComment(Linkable parent, Comment comment) {
        return post(parent.getHref(COMMENTS), new JaxbComment(comment), JaxbComment.class);
    }

    @Override
    public Comment getComment(String commentUri, String... params) {
        return get(commentUri, false, JaxbComment.class, params);
    }

    @Override
    public Feed<Comment> getReplies(Linkable parent, String... params) {
        return feed(parent, REPLIES, params);
    }

    @Override
    public Comment createReply(Linkable parent, Comment comment) {
        return post(parent.getHref(REPLIES), new JaxbComment(comment), JaxbComment.class);
    }

    @Override
    public Feed<VirtualDocumentNode> getVirtualDocumentNodes(Linkable linkable, String... params) {
        return feed(linkable, VIRTUAL_DOCUMENT_NODES, params);
    }

    @Override
    public Feed<SearchTemplate> getSearchTemplates(String... params) {
        return feed(SEARCH_TEMPLATES, params);
    }

    @Override
    public SearchTemplate getSearchTemplate(String uri, String... params) {
        return get(uri, false, JaxbSearchTemplate.class, params);
    }

    @Override
    public SearchTemplate createSearchTemplate(SearchTemplate template) {
        return post(getRepository().getHref(SEARCH_TEMPLATES), new JaxbSearchTemplate(template), JaxbSearchTemplate.class);
    }
    
    @Override
    public SearchFeed<RestObject> executeSearchTemplate(SearchTemplate toBeExecuted, String... params) {
        return post(toBeExecuted.getHref(SEARCH_EXECUTION), toBeExecuted, new Headers().accept(MediaType.APPLICATION_ATOM_XML_VALUE).contentType(SupportedMediaTypes.APPLICATION_VND_DCTM_XML_VALUE).toHttpHeaders(), JaxbSearchFeed.class, params);
    }
    
    @Override
    public SearchFeed<RestObject> executeSavedSearch(SavedSearch toBeExecuted, String... params) {
        return get(toBeExecuted.getHref(SEARCH_EXECUTION), true, JaxbSearchFeed.class, params);
    }

    @Override
    public Feed<SavedSearch> getSavedSearches(String... params) {
        return feed(SAVED_SEARCHES, params);
    }

    @Override
    public SavedSearch getSavedSearch(String uri, String... params) {
        return get(uri, false, JaxbSavedSearch.class, params);
    }

    @Override
    public SavedSearch createSavedSearch(SavedSearch savedSearch) {
        return post(getRepository().getHref(SAVED_SEARCHES), new JaxbSavedSearch(savedSearch), JaxbSavedSearch.class);
    }
    
    @Override
    public SavedSearch updateSavedSearch(SavedSearch oldSavedSearch, SavedSearch newSavedSearch) {
        return post(oldSavedSearch.self(), new JaxbSavedSearch(newSavedSearch), JaxbSavedSearch.class);
    }

    @Override
    public SearchFeed<RestObject> enableSavedSearchResult(SavedSearch toBeExecuted, String... params) {
        return sendRequest(toBeExecuted.getHref(SAVED_SEARCH_SAVED_RESULTS), PUT, ACCEPT_ATOM_HEADERS, null, JaxbSearchFeed.class, params);
    }

    @Override
    public void disableSavedSearchResult(SavedSearch toBeExecuted) {
        delete(toBeExecuted.getHref(SAVED_SEARCH_SAVED_RESULTS));
    }

    @Override
    public SearchFeed<RestObject> getSavedSearchResult(SavedSearch toBeExecuted, String... params) {
        return get(toBeExecuted.getHref(SAVED_SEARCH_SAVED_RESULTS), true, JaxbSearchFeed.class, params);
    }
    
    @Override
    protected void initRestTemplate(RestTemplate restTemplate) {
        super.initRestTemplate(restTemplate);
        restTemplate.setErrorHandler(new DCTMJaxbErrorHandler(restTemplate.getMessageConverters()));
        for(HttpMessageConverter<?> c : restTemplate.getMessageConverters()) {
            if(c instanceof FormHttpMessageConverter) {
                ((FormHttpMessageConverter)c).addPartConverter(new Jaxb2RootElementHttpMessageConverter());
                break;
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private Feed feed(Linkable parent, LinkRelation rel, String... params) {
        return feed(parent, rel, JaxbFeed.class, params);
    }
    
    @SuppressWarnings("rawtypes")
    private Feed feed(LinkRelation rel, String... params) {
        return feed(rel, JaxbFeed.class, params);
    }

    @Override
    public void serialize(Object object, OutputStream os) {
        try {
            DCTMJaxbContext.marshal(os, object);
        } catch (RuntimeException re) {
            throw (RuntimeException)re;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public ClientType getClientType() {
        return ClientType.XML;
    }

    @Override
    public Feed<Lifecycle> getLifecycles(String... params) {
        return feed(LIFECYCLES, params);
    }
    
    @Override
    public Lifecycle getLifecycle(String uri, String... params) {
        return get(uri, false, JaxbLifecycle.class, params);
    }

    @Override
    public ObjectLifecycle attach(RestObject object, ObjectLifecycle objectLifecycle) {
        return put(object.getHref(OBJECT_LIFECYCLE), objectLifecycle==null?null:new JaxbObjectLifecycle(objectLifecycle), JaxbObjectLifecycle.class);
    }

    @Override
    public void detach(ObjectLifecycle objectLifecycle) {
        delete(objectLifecycle.self());
    }
    
    @Override
    public ObjectLifecycle getObjectLifecycle(RestObject object, String... params) {
        return get(object.getHref(OBJECT_LIFECYCLE), JaxbObjectLifecycle.class, params);
    }

    @Override
    public ObjectLifecycle promote(ObjectLifecycle objectLifecycle, String... params) {
        return put(objectLifecycle.getHref(PROMOTION), JaxbObjectLifecycle.class, params);
    }

    @Override
    public ObjectLifecycle demote(ObjectLifecycle objectLifecycle, String... params) {
        return put(objectLifecycle.getHref(DEMOTION), JaxbObjectLifecycle.class, params);
    }

    @Override
    public ObjectLifecycle suspend(ObjectLifecycle objectLifecycle, String... params) {
        return put(objectLifecycle.getHref(SUSPENSION), JaxbObjectLifecycle.class, params);
    }

    @Override
    public ObjectLifecycle resume(ObjectLifecycle objectLifecycle, String... params) {
        return put(objectLifecycle.getHref(RESUMPTION), JaxbObjectLifecycle.class, params);
    }

    @Override
    public void cancel(ObjectLifecycle objectLifecycle) {
        if(objectLifecycle.hasHref(CANCEL_PROMOTION)) {
            delete(objectLifecycle.getHref(CANCEL_PROMOTION));
        }
        if(objectLifecycle.hasHref(CANCEL_DEMOTION)) {
            delete(objectLifecycle.getHref(CANCEL_DEMOTION));
        }
        if(objectLifecycle.hasHref(CANCEL_SUSPENSION)) {
            delete(objectLifecycle.getHref(CANCEL_SUSPENSION));
        }
        if(objectLifecycle.hasHref(CANCEL_RESUMPTION)) {
            delete(objectLifecycle.getHref(CANCEL_RESUMPTION));
        }
    }

    @Override
    public RestObject subscribe(RestObject object, String... subscribers) {
        if(!object.hasHref(SUBSCRIBE)) {
            object = get(object, "check-subscription", "true");
        }
        if(object.hasHref(SUBSCRIBE)) {
            return Collections.isEmpty(subscribers) ?
                    put(object.getHref(SUBSCRIBE), object.getClass()):
                    put(object.getHref(SUBSCRIBE),  new JaxbSubscribers(subscribers), object.getClass());
        } else if(object.hasHref(UNSUBSCRIBE)) {
            throw new IllegalArgumentException("the object is already subscribed");
        } else {
            throw new IllegalArgumentException("the object is not subscribable");
        }
    }

    @Override
    public void unsubscribe(RestObject object) {
        if(!object.hasHref(UNSUBSCRIBE)) {
            object = get(object, "check-subscription", "true");
        }
        if(object.hasHref(UNSUBSCRIBE)) {
            delete(object.getHref(UNSUBSCRIBE));
        } else if(object.hasHref(SUBSCRIBE)) {
            throw new IllegalArgumentException("the object is not subscribed");
        } else {
            throw new IllegalArgumentException("the object is not unsubscribable");
        }
    }

    @Override
    public Feed<RestObject> getSubscriptions(String... params) {
        return feed(SUBSCRIPTIONS, params);
    }

    @Override
    public Feed<AuditPolicy> getAuditPolicies(String... params) {
        return feed(AUDIT_POLICIES, params);
    }

    @Override
    public AuditPolicy createAuditPolicy(AuditPolicy auditPolicy) {
        return post(getRepository().getHref(AUDIT_POLICIES), new JaxbAuditPolicy(auditPolicy), JaxbAuditPolicy.class);
    }

    @Override
    public AuditPolicy getAuditPolicy(String uri, String... params) {
        return get(uri, JaxbAuditPolicy.class, params);
    }

    @Override
    public AuditPolicy updateAuditPolicy(AuditPolicy oldPolicy, AuditPolicy newPolicy) {
        return post(oldPolicy.getHref(EDIT), new JaxbAuditPolicy(newPolicy), JaxbAuditPolicy.class);
    }

    @Override
    public void deleteAuditPolicy(AuditPolicy auditPolicy) {
        delete(auditPolicy);
    }

    @Override
    public Feed<RestObject> getRecentAuditTrails(String... params) {
        return feed(getCurrentUser(), RECENT_TRAILS, params);
    }

    @Override
    public AuditTrail getAuditTrail(String auditTrailUri, String... params) {
        return get(auditTrailUri, JaxbAuditTrail.class, params);
    }

    @Override
    public AvailableAuditEvents getAvailableAuditEvents(String... params) {
        return getRepository().hasHref(AVAILABLE_AUDIT_EVENTS)?
            get(getRepository().getHref(AVAILABLE_AUDIT_EVENTS), new Headers().accept(APPLICATION_VND_DCTM_XML_VALUE+","+APPLICATION_ATOM_XML_VALUE).toHttpHeaders(), JaxbAvailableAuditEvents.class, params):null;
    }

    @Override
    public Feed<RestObject> getRegisteredAuditEvents(String... params) {
        return feed(getRepository(), REGISTERED_AUDIT_EVENTS, params);
    }

    @Override
    public RestObject registerAuditEvent(RestObject auditEvent, String... params) {
        return post(getRepository().getHref(REGISTERED_AUDIT_EVENTS), new JaxbAuditEvent(auditEvent), JaxbAuditEvent.class, params);
    }

    @Override
    public RestObject getRegisteredAuditEvent(String uri, String... params) {
        return get(uri, JaxbAuditEvent.class, params);
    }

    @Override
    public void unregisterAuditEvent(RestObject auditEvent) {
        delete(auditEvent);
    }
}
