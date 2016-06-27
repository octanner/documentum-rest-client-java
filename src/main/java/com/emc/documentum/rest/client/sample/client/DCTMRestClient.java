/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.emc.documentum.rest.client.sample.client.annotation.NotBatchable;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.FolderLink;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.emc.documentum.rest.client.sample.model.Preference;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.emc.documentum.rest.client.sample.model.SearchFeed;
import com.emc.documentum.rest.client.sample.model.ValueAssistant;
import com.emc.documentum.rest.client.sample.model.ValueAssistantRequest;
import com.emc.documentum.rest.client.sample.model.batch.Batch;
import com.emc.documentum.rest.client.sample.model.batch.Capabilities;

/**
 * The sample REST client library 
 */
@NotThreadSafe
public interface DCTMRestClient {

    /**
     * @return the http headers of the previous operation 
     */
    @NotBatchable
    public HttpHeaders getHeaders();
    
    /**
     * @return the http status of the previous operation
     */
    @NotBatchable
    public HttpStatus getStatus();
    
    /**
     * enable streaming for the binary content transfer.
     * especially useful when upload big files.
     * only valid for the next operations.
     * will be automatically disabled after the operation.
     */
    @NotBatchable
    public DCTMRestClient enableStreamingForNextRequest();
    
    /**
     * @return the cached HomeDocument object
     */
    @NotBatchable
    public HomeDocument getHomeDocument();

    /**
     * @return the product info
     */
    @NotBatchable
    public RestObject getProductInfo();
    
    @NotBatchable
    /**
     * @return the major version of the rest services
     */
    public double getMajorVersion();
    
    /**
     * @return the cached Repositories feed
     */
    @NotBatchable
    public Feed<Repository> getRepositories();
    
    /**
     * @return the cached Repository object
     */
    @NotBatchable
    public Repository getRepository();
    
    /**
     * execute a readonly dql
     * @param dql the dql to be executed
     * @param params the query parameters
     * @return the query feed response
     */
    public Feed<RestObject> dql(String dql, String... params);
    
    /**
     * execute the simple search
     * @param search
     * @param params
     * @return
     */
    public SearchFeed<RestObject> search(String search, String... params);
    
    /**
     * @param params
     * @return the cabinets feed based on query parameters
     */
    public Feed<RestObject> getCabinets(String... params);
    
    /**
     * @param cabinet the cabinet name
     * @param params
     * @return the cabinet by its name
     */
    @NotBatchable
    public RestObject getCabinet(String cabinet, String... params);
    
    /**
     * @param parent the parent object, e.g. cabinet, folder
     * @param params
     * @return the folders feed under the specified object
     */
    public Feed<RestObject> getFolders(RestObject parent, String... params);
    
    /**
     * @param parent the parent object, e.g. cabinet, folder
     * @param params
     * @return the documents (dm_document or its subtype) feed under the specified object
     */
    public Feed<RestObject> getDocuments(RestObject parent, String... params);
    
    /**
     * @param parent the parent object, e.g. cabinet, folder
     * @param params
     * @return the sysobjects (dm_sysobject or its subtype) feed under the specified object
     */
    public Feed<RestObject> getObjects(RestObject parent, String... params);
    
    /**
     * @param object
     * @param params
     * @return get the object by its self link
     */
    public RestObject get(RestObject object, String... params);
    
    /**
     * @param uri
     * @param clazz
     * @param params
     * @return the single object
     */
    public <T> T get(String uri, Class<T> clazz, String... params);
    
    /**
     * create a folder under specified folder/cabinet
     * @param parent the folder/cabinet where the new folder will be created under
     * @param newFolder the new folder with its properties
     * @param params
     * @return the created folder
     */
    public RestObject createFolder(RestObject parent, RestObject newFolder, String... params);
    
    /**
     * @param folderUri
     * @param params
     * @return the folder of the specified uri
     */
    public RestObject getFolder(String folderUri, String... params);
    
    /**
     * create a sysobject (or its subtype) under specified parent's link rel
     * @param parent the parent object where the new sysobject will be created under
     * @param rel the LinkRelation used to create new object under the parent
     * @param objectToCreate the new object with its properties
     * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
     * @param contentMediaType the mediatype of the content
     * @param params
     * @return the created sysobject
     */
    public RestObject createObject(RestObject parent, LinkRelation rel, RestObject objectToCreate, Object content, String contentMediaType, String... params);
    
    /**
     * create a sysobject (or its subtype) under specified parent's link rel
     * @param parent the parent object where the new sysobject will be created under
     * @param rel the LinkRelation used to create new object under the parent
     * @param objectToCreate the new object with its properties
     * @return the created sysobject
     */
    public RestObject createObject(RestObject parent, LinkRelation rel, RestObject objectToCreate);

    /**
     * create a sysobject (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new sysobject will be created under
     * @param objectToCreate the new object with its properties
     * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
     * @param contentMediaType the mediatype of the content
     * @param params
     * @return the created sysobject
     */
    public RestObject createObject(RestObject parent, RestObject objectToCreate, Object content, String contentMediaType, String... params);
    
    /**
     * create a sysobject (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new sysobject will be created under
     * @param objectToCreate the new object with its properties
     * @return the created sysobject
     */
    public RestObject createObject(RestObject parent, RestObject objectToCreate);

    /**
     * @param objectUri
     * @param params
     * @return the sysobject of the specified uri
     */
    public RestObject getObject(String objectUri, String... params);
    
    /**
     * create a document (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new document will be created under
     * @param objectToCreate the new document with its properties
     * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param contentMediaType the mediatype of the content
     * @param params
     * @return the created document
     */
    public RestObject createDocument(RestObject parent, RestObject objectToCreate, Object content, String contentMediaType, String... params);

    /**
     * create a document (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new document will be created under
     * @param objectToCreate the new document with its properties
     * @return the created document
     */
    public RestObject createDocument(RestObject parent, RestObject objectToCreate);

    /**
     * @param documentUri
     * @param params
     * @return the document of the specified uri
     */
    public RestObject getDocument(String documentUri, String... params);
    
    /**
     * update the RestObject with new properties
     * @param oldObject the previously fetched RestObject
     * @param newObject the new RestObject with new properties to be updated
     * @param params
     * @return the updated RestObject
     */
    public RestObject update(RestObject oldObject, RestObject newObject, String... params);
    
    /**
     * update the RestObject with new properties
     * @param oldObject
     * @param rel
     * @param newObject
     * @param method
     * @param params
     * @return
     */
    public RestObject update(RestObject oldObject, LinkRelation rel, RestObject newObject, HttpMethod method, String... params);
    
    /**
     * delete the resource
     * @param linkable
     * @param params
     */
    public void delete(Linkable linkable, String... params);
    
    /**
     * delete thr uri
     * @param uri
     * @param params
     */
    public void delete(String uri, String... params);
    
    /**
     * create a content (primary content or rendition) to a RestObject
     * @param object the previously fetched RestObject
     * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param mediaType
     * @param params
     * @return
     */
    public RestObject createContent(RestObject object, Object content, String mediaType, String... params);
    
    /**
     * @param object
     * @param params
     * @return the metadata of primary content of the specified RestObject
     */
    public RestObject getPrimaryContent(RestObject object, String... params);
    
    /**
     * @param contentUri
     * @param params
     * @return the metadata of the content specified by the uri
     */
    public RestObject getContent(String contentUri, String... params);
    
    /**
     * @param object
     * @param params
     * @return the content metadata collection
     */
    public Feed<RestObject> getContents(RestObject object, String... params);
    
    /**
     * get users of the repository
     * @param params
     * @return
     */
    public Feed<RestObject> getUsers(String... params);
    
    /**
     * get users of a parent
     * @param parent
     * @param rel
     * @param params
     * @return
     */
    public Feed<RestObject> getUsers(Linkable parent, String... params);

    /**
     * get groups of the repository
     * @param params
     * @return
     */
    public Feed<RestObject> getGroups(String... params);
    
    /**
     * get single user
     * @param userUri
     * @param params
     * @return
     */
    public RestObject getUser(String userUri, String... params);
    
    /**
     * get single group
     * @param groupUri
     * @param params
     * @return
     */
    public RestObject getGroup(String groupUri, String... params);
    
    /**
     * create an user
     * @param userToCreate
     * @return
     */
    public RestObject createUser(RestObject userToCreate);
    
    /**
     * create a group
     * @param groupToCreate
     * @return
     */
    public RestObject createGroup(RestObject groupToCreate);
    
    /**
     * add the user to a group
     * @param group
     * @param user
     */
    public void addUserToGroup(RestObject group, RestObject user);
    
    /**
     * @param feed the current page
     * @return the next page of the feed collection if it has
     */
    public <T extends Linkable> Feed<T> nextPage(Feed<T> feed);
    
    /**
     * @param feed the current page
     * @return the previous page of the feed collection if it has
     */
    public <T extends Linkable> Feed<T> previousPage(Feed<T> feed);

    /**
     * @param feed the current page
     * @return the first page of the feed collection if it has
     */
    public <T extends Linkable> Feed<T> firstPage(Feed<T> feed);

    /**
     * @param feed the current page
     * @return the last page of the feed collection if it has
     */
    public <T extends Linkable> Feed<T> lastPage(Feed<T> feed);
    
    /**
     * @param object
     * @param params
     * @return the version histories of the given object
     */
    public Feed<RestObject> getVersions(RestObject object, String... params);
    
    /**
     * check out the given RestObject (sysobject, document and their subtype)
     * @param object
     * @param params
     * @return checked out RestObject
     */
    public RestObject checkout(RestObject object, String... params);
    
    /**
     * cancel a checked out object
     * @param object
     */
    public void cancelCheckout(RestObject object);
    
    /**
     * check in the object with next major version
     * can check in both new object metadata and binary content , or new metadata or binary content only
     * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param content the binary content
     * @param contentMediaType the mediatype of the content
     * @param params
     * @return checked in object
     */
    public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, Object content, String contentMediaType, String... params);
    
    /**
     * check in the object with next minor version
     * can check in both new object metadata and binary content , or new metadata or binary content only
     * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param content the binary content
     * @param contentMediaType the mediatype of the content
     * @param params
     * @return checked in object
     */
    public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, Object content, String contentMediaType, String... params);
    
    /**
     * check in the object with branch version
     * can check in both new object metadata and binary content , or new metadata or binary content only
     * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param content the binary content
     * @param contentMediaType the mediatype of the content
     * @param params
     * @return checked in object
     */
    public RestObject checkinBranch(RestObject oldObject, RestObject newObject, Object content, String contentMediaType, String... params);
    
    /**
     * materialize the lightweight object
     * @param oldObject the object to be materialized
     * @return materialized lightweight object
     */
    public RestObject materialize(RestObject oldObject);

    /**
     * dematerialize the lightweight object
     * @param oldObject the object to be dematerialized
     * @return dematerialized lightweight object
     */
    public void dematerialize(RestObject oldObject);

    /**
     * reparent the lightweight object to a new shared parent
     * @param oldObject the object to be reparented
     * @param newParent the new shared parent
     * @return reparented lightweight object
     */
    public RestObject reparent(RestObject oldObject, RestObject newParent);
    
    /**
     * get the type info
     * @param name the type name
     * @param params
     * @return the RestType
     */
    public RestType getType(String name, String... params);

    /**
     * @param params
     * @return the types info in the repository
     */
    public Feed<RestType> getTypes(String... params);
    
    /**
     * get value assistance of a type
     * @param type
     * @param request
     * @param params
     * @return
     */
    public ValueAssistant getValueAssistant(RestType type, ValueAssistantRequest request, String... params);
    
    /**
     * @param params
     * @return the aspect types info in the repository
     */
    public Feed<RestObject> getAspectTypes(String... params);
    
    /**
     * get the aspect type
     * @param aspectType
     * @param param
     * @return
     */
    public RestObject getAspectType(String aspectType, String... params);
    
    /**
     * attach aspects to the object
     * @param object
     * @param aspects
     * @return
     */
    public ObjectAspects attach(RestObject object, String... aspects);
    
    /**
     * detach an aspect from an object
     * @param objectAspects
     * @param aspect
     */
    public void detach(ObjectAspects objectAspects, String aspect);

    /**
     * get relation types of the repository
     * @param params
     * @return
     */
    public Feed<RestObject> getRelationTypes(String... params);
    
    /**
     * get single relation type
     * @param uri
     * @param params
     * @return
     */
    public RestObject getRelationType(String uri, String... params);

    /**
     * get relations of the repository
     * @param params
     * @return
     */
    public Feed<RestObject> getRelations(String... params);
    
    /**
     * get single relation
     * @param uri
     * @param params
     * @return
     */
    public RestObject getRelation(String uri, String... params);
    
    /**
     * create a relation
     * @param object
     * @return
     */
    public RestObject createRelation(RestObject object);
    
    /**
     * get formats of the repository
     * @param params
     * @return
     */
    public Feed<RestObject> getFormats(String... params);
    
    /**
     * get single format
     * @param uri
     * @param params
     * @return
     */
    public RestObject getFormat(String uri, String... params);

    /**
     * get network locations of the repository
     * @param params
     * @return
     */
    public Feed<RestObject> getNetworkLocations(String... params);
    
    /**
     * get single network location
     * @param uri
     * @param params
     * @return
     */
    public RestObject getNetworkLocation(String uri, String... params);
    
    /**
     * get folder links of the object
     * @param params
     * @return
     */
    public Feed<FolderLink> getFolderLinks(RestObject object, LinkRelation rel, String... params);
    
    /**
     * get single folder link
     * @param uri
     * @param params
     * @return
     */
    public FolderLink getFolderLink(String uri, String... params);
    
    /**
     * move the object by the folder link
     * @param oldLink
     * @param newLink
     * @param params
     * @return
     */
    public FolderLink move(FolderLink oldLink, FolderLink newLink, String... params);
    
    /**
     * link an object to another place
     * @param object
     * @param rel
     * @param link
     * @return
     */
    public FolderLink link(RestObject object, LinkRelation rel, FolderLink link);
    
    /**
     * get batch capabilities
     * @return
     */
    public Capabilities getBatchCapabilities();
    
    /**
     * create and execute a batch
     * @param batch
     * @return
     */
    @NotBatchable
    public Batch createBatch(Batch batch);
    
    /**
     * get user preferences
     * @param params
     * @return
     */
    public Feed<Preference> getPreferences(String... params);
    
    /**
     * get single preference
     * @param uri
     * @param params
     * @return
     */
    public Preference getPreference(String uri, String... params);
    
    /**
     * create a preference
     * @param preference
     * @return
     */
    public Preference createPreference(Preference preference);
    
    /**
     * update a preference
     * @param oldPreference
     * @param newPreference
     * @return
     */
    public Preference updatePreference(Preference oldPreference, Preference newPreference);
}
