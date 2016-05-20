/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client.impl.jackson;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.emc.documentum.rest.client.sample.model.ValueAssistant;
import com.emc.documentum.rest.client.sample.model.ValueAssistantRequest;
import com.emc.documentum.rest.client.sample.model.json.JsonFeeds;
import com.emc.documentum.rest.client.sample.model.json.JsonHomeDocument;
import com.emc.documentum.rest.client.sample.model.json.JsonObject;
import com.emc.documentum.rest.client.sample.model.json.JsonObjectAspects;
import com.emc.documentum.rest.client.sample.model.json.JsonRepository;
import com.emc.documentum.rest.client.sample.model.json.JsonType;
import com.emc.documentum.rest.client.sample.model.json.JsonValueAssistance;
import com.emc.documentum.rest.client.sample.model.json.JsonValueAssistantRequest;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * the DCTMRestClient implementation by Jackson json support
 */
@NotThreadSafe
public class DCTMJacksonClient extends AbstractRestTemplateClient implements DCTMRestClient {
    public DCTMJacksonClient(String contextRoot, String repositoryName,
            String username, String password, boolean useFormatExtension) {
        super(contextRoot, repositoryName, username, password, useFormatExtension);
    }
    
    @Override
    public HomeDocument getHomeDocument() {
        if(homeDocument == null) {
            homeDocument = get(getHomeDocumentUri(), Headers.ACCEPT_JSON_HOME_DOCUMENT, JsonHomeDocument.class);
        }
        return homeDocument;
    }
    
    @Override
    public RestObject getProductInfo() {
        if(productInfo == null) {
            productInfo = get(getHomeDocument().getHref(LinkRelation.ABOUT), false, JsonObject.class);
        }
        return productInfo;
    }

    @Override
    public Feed<Repository> getRepositories() {
        if(repositories == null) {
            String repositoriesUri = getHomeDocument().getHref(LinkRelation.REPOSITORIES);
            Feed<? extends Repository> feed = get(repositoriesUri, true, JsonFeeds.RepositoryFeed.class);
            repositories = (Feed<Repository>)feed;
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
                    repository = get(e.getContentSrc(), false, JsonRepository.class);
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
        Feed<? extends RestObject> feed = get(getRepository().getHref(LinkRelation.SELF), true, JsonFeeds.ObjectFeed.class, UriHelper.append(params, "dql", dql));
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public Feed<RestObject> getCabinets(String... params) {
        Repository repository = getRepository();
        String cabinetsUri = repository.getHref(LinkRelation.CABINETS);
        Feed<? extends RestObject> feed = get(cabinetsUri, true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject getCabinet(String cabinet, String... params) {
        RestObject obj = null; 
        Feed<RestObject> feed = getCabinets("filter", "starts-with(object_name,'" + cabinet + "')");
        List<Entry<RestObject>> entries = feed.getEntries();
        if(entries != null) {
            for(Entry<RestObject> e : (List<Entry<RestObject>>)entries) {
                if(cabinet.equals(e.getTitle())) {
                    obj = get(e.getContentSrc(), false, JsonObject.class);
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
        Feed<? extends RestObject> feed = get(parent.getHref(LinkRelation.FOLDERS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public Feed<RestObject> getObjects(RestObject parent, String... params) {
        Feed<? extends RestObject> feed = get(parent.getHref(LinkRelation.OBJECTS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public Feed<RestObject> getDocuments(RestObject parent, String... params) {
        Feed<? extends RestObject> feed = get(parent.getHref(LinkRelation.DOCUMENTS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject createFolder(RestObject parent, RestObject newFolder, String... params) {
        return post(parent.getHref(LinkRelation.FOLDERS), new JsonObject(newFolder), JsonObject.class, params);
    }
    
    @Override
    public RestObject getFolder(String folderUri, String... params) {
        return get(folderUri, false, JsonObject.class, params);
    }
    
    @Override
    public RestObject createObject(RestObject parent, LinkRelation rel, RestObject objectToCreate, Object content, String... params) {
        return post(parent.getHref(rel), new JsonObject(objectToCreate), content, JsonObject.class, params);
    }

    @Override
    public RestObject getObject(String objectUri, String... params) {
        return get(objectUri, false, JsonObject.class, params);
    }
    
    @Override
    public RestObject createDocument(RestObject parent, RestObject objectToCreate, Object content, String... params) {
        return post(parent.getHref(LinkRelation.DOCUMENTS), new JsonObject(objectToCreate), content, JsonObject.class, params);
    }
    
    @Override
    public RestObject getDocument(String documentUri, String... params) {
        return get(documentUri, false, JsonObject.class, params);
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
        return post(object.getHref(LinkRelation.CONTENTS), content, mediaType, JsonObject.class, params);
    }
    
    @Override
    public RestObject getPrimaryContent(RestObject object, String... params) {
        return getContent(object.getHref(LinkRelation.PRIMARY_CONTENT), params);
    }
    
    @Override
    public RestObject getContent(String contentUri, String... params) {
        return get(contentUri, false, JsonObject.class, params);
    }
    
    @Override
    public Feed<RestObject> getContents(RestObject object, String... params) {
        Feed<? extends RestObject> feed = get(object.getHref(LinkRelation.CONTENTS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject checkout(RestObject object, String... params) {
        return put(object.getHref(LinkRelation.CHECKOUT), JsonObject.class, params);
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
        Feed<? extends RestObject> feed = get(object.getHref(LinkRelation.VERSIONS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject materialize(RestObject oldObject) {
        return put(oldObject.getHref(LinkRelation.MATERIALIZE), JsonObject.class);
    }

    @Override
    public void dematerialize(RestObject oldObject) {
        delete(oldObject.getHref(LinkRelation.DEMATERIALIZE));
    }

    @Override
    public RestObject reparent(RestObject oldObject, RestObject newParent) {
        JsonObject parent = new JsonObject();
        parent.setHref(newParent.getHref(LinkRelation.SELF));
        return post(oldObject.getHref(LinkRelation.SHARED_PARENT), parent, JsonObject.class);
    }
    
    @Override
    public RestType getType(String name, String... params) {
        return get(getRepository().getHref(LinkRelation.TYPES)+"/"+name, false, JsonType.class, params);
    }
    
    @Override
    public Feed<RestType> getTypes(String... params) {
        Repository repository = getRepository();
        String typesUri = repository.getHref(LinkRelation.TYPES);
        Feed<? extends RestType> feed = get(typesUri, true, JsonFeeds.TypeFeed.class, params);
        return (Feed<RestType>)feed;
    }
    
    @Override
    public Feed<RestObject> getAspectTypes(String... params) {
        Repository repository = getRepository();
        String aspectTypesUri = repository.getHref(LinkRelation.ASPECT_TYPES);
        Feed<? extends RestObject> feed = get(aspectTypesUri, true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }

    @Override
    public RestObject getAspectType(String aspectType, String... params) {
        return get(getRepository().getHref(LinkRelation.ASPECT_TYPES)+"/"+aspectType, false, JsonObject.class, params);
    }

    @Override
    public ValueAssistant getValueAssistant(RestType type, ValueAssistantRequest request, String... params) {
        return post(type.getHref(LinkRelation.ASSIS_VALUES), new JsonValueAssistantRequest(request), JsonValueAssistance.class, params);
    }
    
    @Override
    public ObjectAspects attach(RestObject object, String... aspects) {
        return post(object.getHref(LinkRelation.OBJECT_ASPECTS), new JsonObjectAspects(aspects), JsonObjectAspects.class);        
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
        Feed<? extends RestObject> feed = get(parent.getHref(LinkRelation.USERS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }

    @Override
    public Feed<RestObject> getGroups(String... params) {
        Feed<? extends RestObject> feed = get(getRepository().getHref(LinkRelation.GROUPS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject getUser(String userUri, String... params) {
        return get(userUri, false, JsonObject.class, params);
    }
    
    @Override
    public RestObject getGroup(String groupUri, String... params) {
        return get(groupUri, false, JsonObject.class, params);
    }

    @Override
    public RestObject createUser(RestObject userToCreate) {
        return post(getRepository().getHref(LinkRelation.USERS), new JsonObject(userToCreate), JsonObject.class);
    }
    
    @Override
    public RestObject createGroup(RestObject groupToCreate) {
        return post(getRepository().getHref(LinkRelation.GROUPS), new JsonObject(groupToCreate), JsonObject.class);
    }

    @Override
    public void addUserToGroup(RestObject group, RestObject user) {
        JsonObject groupUser = new JsonObject();
        groupUser.setHref(user.getHref(LinkRelation.SELF));
        post(group.getHref(LinkRelation.USERS), groupUser, null);
    }
     
    @Override
    public Feed<RestObject> getRelationTypes(String... params) {
        Feed<? extends RestObject> feed = get(getRepository().getHref(LinkRelation.RELATION_TYPES), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject getRelationType(String uri, String... params) {
        return get(uri, false, JsonObject.class, params);
    }
    
    @Override
    public Feed<RestObject> getRelations(String... params) {
        Feed<? extends RestObject> feed = get(getRepository().getHref(LinkRelation.RELATIONS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject getRelation(String uri, String... params) {
        return get(uri, false, JsonObject.class, params);
    }
    
    @Override
    public RestObject createRelation(RestObject object) {
        return post(getRepository().getHref(LinkRelation.RELATIONS), new JsonObject(object), JsonObject.class);
    }
    
    @Override
    public Feed<RestObject> getFormats(String... params) {
        Feed<? extends RestObject> feed = get(getRepository().getHref(LinkRelation.FORMATS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject getFormat(String uri, String... params) {
        return get(uri, false, JsonObject.class, params);
    }

    @Override
    public Feed<RestObject> getNetworkLocations(String... params) {
        Feed<? extends RestObject> feed = get(getRepository().getHref(LinkRelation.NETWORK_LOCATIONS), true, JsonFeeds.ObjectFeed.class, params);
        return (Feed<RestObject>)feed;
    }
    
    @Override
    public RestObject getNetworkLocation(String uri, String... params) {
        return get(uri, false, JsonObject.class, params);
    }

    @Override
    public <T extends Linkable> Feed<T> nextPage(Feed<T> feed) {
        return page(feed.getHref(LinkRelation.PAGING_NEXT), feed.getClass());
    }
    
    @Override
    public <T extends Linkable> Feed<T> previousPage(Feed<T> feed) {
        return page(feed.getHref(LinkRelation.PAGING_PREV), feed.getClass());
    }

    @Override
    public <T extends Linkable> Feed<T> firstPage(Feed<T> feed) {
        return page(feed.getHref(LinkRelation.PAGING_FIRST), feed.getClass());
    }

    @Override
    public <T extends Linkable> Feed<T> lastPage(Feed<T> feed) {
        return page(feed.getHref(LinkRelation.PAGING_LAST), feed.getClass());
    }
    
    private <T extends Linkable> Feed<T> page(String uri, Class<? extends Feed> clazz) {
        Feed<T> feed = null;
        if(uri != null) {
            feed = get(uri, true, clazz);
        }
        return feed;
    }
    
    private RestObject checkin(RestObject oldObject, LinkRelation rel, RestObject newObject, Object content, String... params) {
        RestObject resp = null;
        if(newObject != null && content != null) {
            resp = post(oldObject.getHref(rel), new JsonObject(newObject), content, JsonObject.class, params);
        } else if(newObject == null) {
            resp = post(oldObject.getHref(rel), content, MediaType.APPLICATION_OCTET_STREAM_VALUE, oldObject.getClass(), params);
        } else if(content == null) {
            resp = post(oldObject.getHref(rel), new JsonObject(newObject), JsonObject.class, params);
        }
        return resp;
    }
    
    @Override
    protected void initRestTemplate(RestTemplate restTemplate) {
        restTemplate.setErrorHandler(new DCTMJacksonErrorHandler(restTemplate.getMessageConverters()));
        for(HttpMessageConverter<?> c : restTemplate.getMessageConverters()) {
            if(c instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter)c).getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            } else if(c instanceof FormHttpMessageConverter) {
                try {
                    Field pcField = FormHttpMessageConverter.class.getDeclaredField("partConverters");
                    pcField.setAccessible(true);
                    List<HttpMessageConverter<?>> partConverters = (List<HttpMessageConverter<?>>)pcField.get(c);
                    for(HttpMessageConverter<?> pc : partConverters) {
                        if(pc instanceof MappingJackson2HttpMessageConverter) {
                            ((MappingJackson2HttpMessageConverter)pc).getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
                            break;
                        }
                    }
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    @Override
    protected ClientType getClientType() {
        return ClientType.JSON;
    }
}
