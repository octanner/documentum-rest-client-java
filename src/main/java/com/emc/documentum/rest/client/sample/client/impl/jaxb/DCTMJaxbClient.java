/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
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
import com.emc.documentum.rest.client.sample.client.util.Headers;
import com.emc.documentum.rest.client.sample.client.util.SupportedMediaTypes;
import com.emc.documentum.rest.client.sample.client.util.UriHelper;
import com.emc.documentum.rest.client.sample.model.Comment;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.FeedBase;
import com.emc.documentum.rest.client.sample.model.FolderLink;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
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
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbNetworkLocation;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbObjectAspects;
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
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSysObject;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbType;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbUser;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbValueAssistance;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbValueAssistantRequest;

import static com.emc.documentum.rest.client.sample.client.util.Headers.ACCEPT_ATOM_HEADERS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ABOUT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ACLS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ASPECT_TYPES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ASSIS_VALUES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ASSOCIATIONS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.BATCH_CAPABILITIES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CABINETS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CANCEL_CHECKOUT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CHECKIN_BRANCH_VERSION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CHECKIN_NEXT_MAJOR;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CHECKIN_NEXT_MINOR;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CHECKOUT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.COMMENTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CONTENTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CURRENT_USER_PREFERENCES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.DELETE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.DEMATERIALIZE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.DOCUMENTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.EDIT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.FOLDERS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.FORMATS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.GROUPS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.MATERIALIZE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.NETWORK_LOCATIONS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.OBJECTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.OBJECT_ASPECTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.PAGING_FIRST;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.PAGING_LAST;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.PAGING_NEXT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.PAGING_PREV;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.PRIMARY_CONTENT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.RELATIONS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.RELATION_TYPES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.REPLIES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.REPOSITORIES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SAVED_SEARCHES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SAVED_SEARCH_SAVED_RESULTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SEARCH;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SEARCH_EXECUTION;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SEARCH_TEMPLATES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SELF;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SHARED_PARENT;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.TYPES;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.USERS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.VERSIONS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.VIRTUAL_DOCUMENT_NODES;
import static org.springframework.http.HttpMethod.PUT;

/**
 * the DCTMRestClient implementation by JAXB xml support
 */
@NotThreadSafe
public class DCTMJaxbClient extends AbstractRestTemplateClient implements DCTMRestClient {
    public DCTMJaxbClient(String contextRoot, String repositoryName,
            String username, String password, boolean useFormatExtension) {
        super(contextRoot, repositoryName, username, password, useFormatExtension);
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
            String repositoriesUri = getHomeDocument().getHref(REPOSITORIES);
            repositories = get(repositoriesUri, true, JaxbFeed.class);
        }
        return repositories;
    }
    
    @Override
    public Repository getRepository() {
        if(repository == null) {
            boolean resetEnableStreaming = enableStreaming;
            Feed<Repository> repositories = getRepositories();
            for(Entry<Repository> e : repositories.getEntries()) {
                if(repositoryName.equals(e.getTitle())) {
                    repository = get(e.getContentSrc(), false, JaxbRepository.class);
                }
            }
            if(resetEnableStreaming) {
                enableStreaming = resetEnableStreaming;
            }
        }
        return repository;
    }
    
    @Override
    public Feed<RestObject> dql(String dql, String... params) {
        return get(getRepository().getHref(SELF), true, JaxbFeed.class, UriHelper.append(params, "dql", dql));
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
    public Feed<RestObject> getCabinets(String... params) {
        Repository repository = getRepository();
        String cabinetsUri = repository.getHref(CABINETS);
        return get(cabinetsUri, true, JaxbFeed.class, params);
    }
    
    @Override
    public RestObject getCabinet(String cabinet, String... params) {
        RestObject obj = null; 
        Feed feed = getCabinets("filter", "starts-with(object_name,'" + cabinet + "')");
        List<Entry> entries = feed.getEntries();
        if(entries != null) {
            for(Entry e : entries) {
                if(cabinet.equals(e.getTitle())) {
                    obj = get(e.getContentSrc(), false, JaxbCabinet.class);
                    break;
                }
            }
        }
        return obj;
    }
    
    @Override
    public <T extends Linkable> T get(T t, String... params) {
        return (T)get(t.self(), t instanceof FeedBase, t.getClass(), params);
    }
    
    @Override
    public Feed<RestObject> getFolders(Linkable parent, String... params) {
        return get(parent.getHref(FOLDERS), true, JaxbFeed.class, params);
    }
    
    @Override
    public Feed<RestObject> getObjects(Linkable parent, String... params) {
        return get(parent.getHref(OBJECTS), true, JaxbFeed.class, params);
    }
    
    @Override
    public Feed<RestObject> getDocuments(Linkable parent, String... params) {
        return get(parent.getHref(DOCUMENTS), true, JaxbFeed.class, params);
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
        return get(object.getHref(CONTENTS), true, JaxbFeed.class, params);
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
        return get(object.getHref(VERSIONS), true, JaxbFeed.class, params);
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
            throw new IllegalArgumentException(oldObject.getClass().getName());
        }
    }
    
    @Override
    public RestType getType(String name, String... params) {
        return get(getRepository().getHref(TYPES)+"/"+name, false, JaxbType.class, params);
    }
    
    @Override
    public Feed<RestType> getTypes(String... params) {
        Repository repository = getRepository();
        String typesUri = repository.getHref(TYPES);
        return get(typesUri, true, JaxbFeed.class, params);
    }
    
    @Override
    public Feed<RestObject> getAspectTypes(String... params) {
        Repository repository = getRepository();
        String aspectTypesUri = repository.getHref(ASPECT_TYPES);
        return get(aspectTypesUri, true, JaxbFeed.class, params);
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
    public Feed<RestObject> getUsers(String... params) {
        return getUsers(getRepository(), params);
    }
    
    @Override
    public Feed<RestObject> getUsers(Linkable parent, String... params) {
        return get(parent.getHref(USERS), true, JaxbFeed.class, params);
    }

    @Override
    public Feed<RestObject> getGroups(String... params) {
        return getGroups(getRepository(), params);
    }
    
    @Override
    public Feed<RestObject> getGroups(Linkable parent, String... params) {
        return get(parent.getHref(GROUPS), true, JaxbFeed.class, params);
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
        Feed<? extends RestObject> feed = get(getRepository().getHref(RELATION_TYPES), true, JaxbFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject getRelationType(String uri, String... params) {
        return get(uri, false, JaxbRelationType.class, params);
    }

    @Override
    public Feed<RestObject> getRelations(String... params) {
        Feed<? extends RestObject> feed = get(getRepository().getHref(RELATION_TYPES), true, JaxbFeed.class, params);
        return (Feed<RestObject>)feed;
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
        Feed<? extends RestObject> feed = get(getRepository().getHref(FORMATS), true, JaxbFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject getFormat(String uri, String... params) {
        return get(uri, false, JaxbFormat.class, params);
    }
    
    @Override
    public Feed<RestObject> getNetworkLocations(String... params) {
        Feed<? extends RestObject> feed = get(getRepository().getHref(NETWORK_LOCATIONS), true, JaxbFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject getNetworkLocation(String uri, String... params) {
        return get(uri, false, JaxbNetworkLocation.class, params);
    }
    
    @Override
    public Feed<FolderLink> getFolderLinks(Linkable object, LinkRelation rel, String... params) {
        Feed<? extends FolderLink> feed = get(object.getHref(rel), true, JaxbFeed.class, params);
        return (Feed<FolderLink>)feed;
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
        Feed<? extends RestObject> feed = get(getRepository().getHref(ACLS), true, JaxbFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public Feed<RestObject> getAclAssociations(Linkable acl, String... params) {
        Feed<? extends RestObject> feed = get(acl.getHref(ASSOCIATIONS), true, JaxbFeed.class, params);
        return (Feed<RestObject>)feed;
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
        Feed<? extends Preference> feed = get(getRepository().getHref(CURRENT_USER_PREFERENCES), true, JaxbFeed.class, params);
        return (Feed<Preference>)feed;
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
        Feed<? extends Comment> feed = get(parent.getHref(COMMENTS), true, JaxbFeed.class, params);
        return (Feed<Comment>)feed;
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
        Feed<? extends Comment> feed = get(parent.getHref(REPLIES), true, JaxbFeed.class, params);
        return (Feed<Comment>)feed;
    }

    @Override
    public Comment createReply(Linkable parent, Comment comment) {
        return post(parent.getHref(REPLIES), new JaxbComment(comment), JaxbComment.class);
    }

    @Override
    public Feed<VirtualDocumentNode> getVirtualDocumentNodes(Linkable linkable, String... params) {
        Feed<? extends VirtualDocumentNode> feed = get(linkable.getHref(VIRTUAL_DOCUMENT_NODES), true, JaxbFeed.class, params);
        return (Feed<VirtualDocumentNode>)feed;
    }

    @Override
    public Feed<SearchTemplate> getSearchTemplates(String... params) {
        Feed<JaxbSearchTemplate> feed = get(getRepository().getHref(SEARCH_TEMPLATES), true, JaxbFeed.class, params);
        return (Feed)feed;
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
        Feed<SavedSearch> feed = get(getRepository().getHref(SAVED_SEARCHES), true, JaxbFeed.class, params);
        return (Feed)feed;
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
    public <T extends Linkable> Feed<T> nextPage(Feed<T> feed) {
        return page(feed.getHref(PAGING_NEXT));
    }
    
    @Override
    public <T extends Linkable> Feed<T> previousPage(Feed<T> feed) {
        return page(feed.getHref(PAGING_PREV));
    }

    @Override
    public <T extends Linkable> Feed<T> firstPage(Feed<T> feed) {
        return page(feed.getHref(PAGING_FIRST));
    }

    @Override
    public <T extends Linkable> Feed<T> lastPage(Feed<T> feed) {
        return page(feed.getHref(PAGING_LAST));
    }
    
    private Feed page(String uri) {
        Feed feed = null;
        if(uri != null) {
            feed = get(uri, true, JaxbFeed.class);
        }
        return feed;
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
    
    @Override
    public void serialize(Object object, OutputStream os) {
        try {
            DCTMJaxbContext.marshal(os, object);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public ClientType getClientType() {
        return ClientType.XML;
    }
}
