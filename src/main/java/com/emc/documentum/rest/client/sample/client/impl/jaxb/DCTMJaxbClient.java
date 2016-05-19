/*
 * Copyright (c) 2014. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl.jaxb;

import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.impl.AbstractRestTemplateClient;
import com.emc.documentum.rest.client.sample.client.util.Headers;
import com.emc.documentum.rest.client.sample.client.util.UriHelper;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.emc.documentum.rest.client.sample.model.PlainRestObject;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.emc.documentum.rest.client.sample.model.ValueAssistant;
import com.emc.documentum.rest.client.sample.model.ValueAssistantRequest;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbAspectType;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbCabinet;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbContent;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbDocument;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbFeed;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbFolder;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbGroup;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbHomeDocument;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbObject;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbObjectAspects;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbProductInfo;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbRepository;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbSysObject;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbType;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbUser;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbValueAssistance;
import com.emc.documentum.rest.client.sample.model.xml.jaxb.JaxbValueAssistantRequest;

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
            productInfo = get(getHomeDocument().getHref(LinkRelation.ABOUT), false, JaxbProductInfo.class);
        }
        return productInfo;
    }

    @Override
    public Feed<Repository> getRepositories() {
        if(repositories == null) {
            String repositoriesUri = getHomeDocument().getHref(LinkRelation.REPOSITORIES);
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
        return get(getRepository().getHref(LinkRelation.SELF), true, JaxbFeed.class, UriHelper.append(params, "dql", dql));
    }
    
    @Override
    public Feed<RestObject> getCabinets(String... params) {
        Repository repository = getRepository();
        String cabinetsUri = repository.getHref(LinkRelation.CABINETS);
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
    public RestObject get(RestObject object, String... params) {
        return get(object.getHref(LinkRelation.SELF), false, object.getClass(), params);
    }
    
    @Override
    public Feed<RestObject> getFolders(RestObject parent, String... params) {
        return get(parent.getHref(LinkRelation.FOLDERS), true, JaxbFeed.class, params);
    }
    
    @Override
    public Feed<RestObject> getObjects(RestObject parent, String... params) {
        return get(parent.getHref(LinkRelation.OBJECTS), true, JaxbFeed.class, params);
    }
    
    @Override
    public Feed<RestObject> getDocuments(RestObject parent, String... params) {
        return get(parent.getHref(LinkRelation.DOCUMENTS), true, JaxbFeed.class, params);
    }
    
    @Override
    public RestObject createFolder(RestObject parent, RestObject newFolder, String... params) {
        return post(parent.getHref(LinkRelation.FOLDERS), new JaxbFolder(newFolder), JaxbFolder.class, params);
    }
    
    @Override
    public RestObject getFolder(String folderUri, String... params) {
        return get(folderUri, false, JaxbFolder.class, params);
    }
    
    @Override
    public RestObject createObject(RestObject parent, LinkRelation rel, RestObject objectToCreate, Object content, String... params) {
        return post(parent.getHref(rel), new JaxbSysObject(objectToCreate), content, JaxbSysObject.class, params);
    }

    @Override
    public RestObject getObject(String objectUri, String... params) {
        return get(objectUri, false, JaxbObject.class, params);
    }
    
    @Override
    public RestObject createDocument(RestObject parent, RestObject objectToCreate, Object content, String... params) {
        return post(parent.getHref(LinkRelation.DOCUMENTS), new JaxbDocument(objectToCreate), content, JaxbDocument.class, params);
    }
    
    @Override
    public RestObject getDocument(String documentUri, String... params) {
        return get(documentUri, false, JaxbDocument.class, params);
    }
    
    @Override
    public RestObject update(RestObject oldObject, RestObject newObject, String... params) {
        try {
            RestObject newRestObject = newRestObject(oldObject, newObject);
            return post(oldObject.getHref(LinkRelation.EDIT), newRestObject, newRestObject.getClass(), params);
        } catch (Exception e) {
            throw new IllegalArgumentException(oldObject.getClass().getName());
        }
    }
    
    @Override
    public void delete(Linkable linkable, String... params) {
        delete(linkable.getHref(LinkRelation.DELETE), params);
    }
    
    @Override
    public RestObject createContent(RestObject object, Object content, String mediaType, String... params) {
        return post(object.getHref(LinkRelation.CONTENTS), content, mediaType, JaxbContent.class, params);
    }
    
    @Override
    public RestObject getPrimaryContent(RestObject object, String... params) {
        return getContent(object.getHref(LinkRelation.PRIMARY_CONTENT), params);
    }
    
    @Override
    public RestObject getContent(String contentUri, String... params) {
        return get(contentUri, false, JaxbContent.class, params);
    }
    
    @Override
    public Feed<RestObject> getContents(RestObject object, String... params) {
        return get(object.getHref(LinkRelation.CONTENTS), true, JaxbFeed.class, params);
    }
    
    @Override
    public RestObject checkout(RestObject object, String... params) {
        return put(object.getHref(LinkRelation.CHECKOUT), JaxbSysObject.class, params);
    }
    
    @Override
    public void cancelCheckout(RestObject object) {
        delete(object.getHref(LinkRelation.CANCEL_CHECKOUT));
    }
    
    @Override
    public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, Object content, String... params) {
        return checkin(oldObject, LinkRelation.CHECKIN_NEXT_MAJOR, newObject, content, params);
    }
    
    @Override
    public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, Object content, String... params) {
        return checkin(oldObject, LinkRelation.CHECKIN_NEXT_MINOR, newObject, content, params);
    }
    
    @Override
    public RestObject checkinBranch(RestObject oldObject, RestObject newObject, Object content, String... params) {
        return checkin(oldObject, LinkRelation.CHECKIN_BRANCH_VERSION, newObject, content, params);
    }
    
    @Override
    public Feed<RestObject> getVersions(RestObject object, String... params) {
        return get(object.getHref(LinkRelation.VERSIONS), true, JaxbFeed.class, params);
    }
    
    @Override
    public RestObject materialize(RestObject oldObject) {
        return put(oldObject.getHref(LinkRelation.MATERIALIZE), JaxbSysObject.class);
    }

    @Override
    public void dematerialize(RestObject oldObject) {
        delete(oldObject.getHref(LinkRelation.DEMATERIALIZE));
    }
    
    @Override
    public RestObject reparent(RestObject oldObject, RestObject newParent) {
        try {
            RestObject newRestObject = newRestObject(oldObject, new PlainRestObject(newParent.getHref(LinkRelation.SELF)));
            return post(oldObject.getHref(LinkRelation.SHARED_PARENT), newRestObject, newRestObject.getClass());
        } catch (Exception e) {
            throw new IllegalArgumentException(oldObject.getClass().getName());
        }
    }
    
    @Override
    public RestType getType(String name, String... params) {
        return get(getRepository().getHref(LinkRelation.TYPES)+"/"+name, false, JaxbType.class, params);
    }
    
    @Override
    public Feed<RestType> getTypes(String... params) {
        Repository repository = getRepository();
        String typesUri = repository.getHref(LinkRelation.TYPES);
        return get(typesUri, true, JaxbFeed.class, params);
    }
    
    @Override
    public Feed<RestObject> getAspectTypes(String... params) {
        Repository repository = getRepository();
        String aspectTypesUri = repository.getHref(LinkRelation.ASPECT_TYPES);
        return get(aspectTypesUri, true, JaxbFeed.class, params);
    }
    
    @Override
    public RestObject getAspectType(String aspectType, String... params) {
        return get(getRepository().getHref(LinkRelation.ASPECT_TYPES)+"/"+aspectType, false, JaxbAspectType.class, params);
    }
    
    @Override
    public ValueAssistant getValueAssistant(RestType type, ValueAssistantRequest request, String... params) {
        return post(type.getHref(LinkRelation.ASSIS_VALUES), new JaxbValueAssistantRequest(request), JaxbValueAssistance.class, params);
    }

    @Override
    public ObjectAspects attach(RestObject object, String... aspects) {
        return post(object.getHref(LinkRelation.OBJECT_ASPECTS), new JaxbObjectAspects(aspects), JaxbObjectAspects.class);        
    }

    @Override
    public void detach(ObjectAspects objectAspects, String aspect) {
        delete(objectAspects.getHref(LinkRelation.DELETE, aspect));
    }
    
    @Override
    public Feed<RestObject> getUsers(String... params) {
        return getUsers(getRepository(), params);
    }
    
    @Override
    public Feed<RestObject> getUsers(Linkable parent, String... params) {
        return get(parent.getHref(LinkRelation.USERS), true, JaxbFeed.class, params);
    }

    @Override
    public Feed<RestObject> getGroups(String... params) {
        return get(getRepository().getHref(LinkRelation.GROUPS), true, JaxbFeed.class, params);
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
        return post(getRepository().getHref(LinkRelation.USERS), new JaxbUser(userToCreate), JaxbUser.class);
    }
    
    @Override
    public RestObject createGroup(RestObject groupToCreate) {
        return post(getRepository().getHref(LinkRelation.GROUPS), new JaxbGroup(groupToCreate), JaxbGroup.class);
    }
    
    @Override
    public void addUserToGroup(RestObject group, RestObject user) {
        JaxbUser groupUser = new JaxbUser();
        groupUser.setHref(user.getHref(LinkRelation.SELF));
        post(group.getHref(LinkRelation.USERS), groupUser, null);
    }

    @Override
    public <T extends Linkable> Feed<T> nextPage(Feed<T> feed) {
        return page(feed.getHref(LinkRelation.PAGING_NEXT));
    }
    
    @Override
    public <T extends Linkable> Feed<T> previousPage(Feed<T> feed) {
        return page(feed.getHref(LinkRelation.PAGING_PREV));
    }

    @Override
    public <T extends Linkable> Feed<T> firstPage(Feed<T> feed) {
        return page(feed.getHref(LinkRelation.PAGING_FIRST));
    }

    @Override
    public <T extends Linkable> Feed<T> lastPage(Feed<T> feed) {
        return page(feed.getHref(LinkRelation.PAGING_LAST));
    }
    
    private Feed page(String uri) {
        Feed feed = null;
        if(uri != null) {
            feed = get(uri, true, JaxbFeed.class);
        }
        return feed;
    }
    
    private RestObject checkin(RestObject oldObject, LinkRelation rel, RestObject newObject, Object content, String... params) {
        RestObject resp = null;
        if(newObject != null && content != null) {
            resp = post(oldObject.getHref(rel), new JaxbSysObject(newObject), content, JaxbSysObject.class, params);
        } else if(newObject == null) {
            resp = post(oldObject.getHref(rel), content, MediaType.APPLICATION_OCTET_STREAM_VALUE, oldObject.getClass(), params);
        } else if(content == null) {
            resp = post(oldObject.getHref(rel), new JaxbSysObject(newObject), JaxbSysObject.class, params);
        }
        return resp;
    }
    
    @Override
    protected void initRestTemplate(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new DCTMJaxbErrorHandler(restTemplate.getMessageConverters()));
        for(HttpMessageConverter<?> c : restTemplate.getMessageConverters()) {
            if(c instanceof FormHttpMessageConverter) {
                ((FormHttpMessageConverter)c).addPartConverter(new Jaxb2RootElementHttpMessageConverter());
                break;
            }
        }
    }

    @Override
    protected ClientType getClientType() {
        return ClientType.XML;
    }
}
