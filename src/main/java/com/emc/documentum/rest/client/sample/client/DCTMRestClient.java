/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.concurrent.NotThreadSafe;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.emc.documentum.rest.client.sample.client.annotation.ClientAsyncOption;
import com.emc.documentum.rest.client.sample.client.annotation.NotBatchable;
import com.emc.documentum.rest.client.sample.client.impl.AbstractRestTemplateClient.ClientType;
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

/**
 * The sample REST client library 
 */
@NotThreadSafe
public interface DCTMRestClient extends Cloneable {

    /**
     * @return the http headers of the previous operation 
     */
    @NotBatchable
    public HttpHeaders getHeaders();
    
    /**
     * set the if match header for the next operation
     * @param ifMatch the if-match header value
     */
    @NotBatchable @ClientAsyncOption(retainClient=true)
    public void ifMatch(String ifMatch);

    /**
     * set the if none match header for the next operation
     * @param ifNoneMatch the if-none-match header value
     */
    @NotBatchable @ClientAsyncOption(retainClient=true)
    public void ifNoneMatch(String ifNoneMatch);
    
    /**
     * @return the http status of the previous operation
     */
    @NotBatchable
    public HttpStatus getStatus();

    /**
     * @return the client type
     */
    @NotBatchable @ClientAsyncOption(false)
    public ClientType getClientType();
    
    /**
     * enable streaming for the binary content transfer.
     * especially useful when upload big files.
     * only valid for the next operations.
     * will be automatically disabled after the operation.
     * @return the DCTMRestClient itself
     */
    @NotBatchable @ClientAsyncOption(retainClient=true)
    public DCTMRestClient enableStreamingForNextRequest();
    
    /**
     * @return the cached HomeDocument object
     */
    @NotBatchable @ClientAsyncOption(false)
    public HomeDocument getHomeDocument();

    /**
     * @return the product info
     */
    @NotBatchable @ClientAsyncOption(false)
    public RestObject getProductInfo();
    
    /**
     * @return the major version of the rest services
     */
    @NotBatchable @ClientAsyncOption(false)
    public double getMajorVersion();
    
    /**
     * @return the cached Repositories feed
     */
    @NotBatchable @ClientAsyncOption(false)
    public Feed<Repository> getRepositories();
    
    /**
     * @return the cached Repository object
     */
    @NotBatchable @ClientAsyncOption(false)
    public Repository getRepository();
    
    /**
     * @return the current selected repository name
     */
    @NotBatchable @ClientAsyncOption(false)
    public String getCurrentRepositoryName();
    
    /**
     * execute a readonly dql
     * @param dql the dql to be executed
     * @param params the query parameters the query parameters
     * @return the query feed response
     */
    public Feed<RestObject> dql(String dql, String... params);
    
    /**
     * execute the simple search
     * @param search the string to be searched
     * @param params the query parameters
     * @return the search result feed
     */
    public SearchFeed<RestObject> search(String search, String... params);
    
    /**
     * execute the search
     * @param search the Search object
     * @param params the query parameters
     * @return the search result feed
     */
    public SearchFeed<RestObject> search(Search search, String... params);
    
    /**
     * get cabinets
     * @param params the query parameters
     * @return the cabinets feed based on query parameters
     */
    public Feed<RestObject> getCabinets(String... params);
    
    /**
     * get a cabinet by name
     * @param cabinet the cabinet name
     * @param params the query parameters
     * @return the cabinet by its name
     */
    @NotBatchable
    public RestObject getCabinet(String cabinet, String... params);

    /**
     * create a new cabinet
     * @param cabinetToCreate the new cabinet object
     * @return the created cabinet
     */

    RestObject createCabinet(RestObject cabinetToCreate);

    /**
     * @param parent the parent object, e.g. cabinet, folder
     * @param params the query parameters
     * @return the folders feed under the specified object
     */
    public Feed<RestObject> getFolders(Linkable parent, String... params);
    
    /**
     * @param parent the parent object, e.g. cabinet, folder
     * @param params the query parameters
     * @return the documents (dm_document or its subtype) feed under the specified object
     */
    public Feed<RestObject> getDocuments(Linkable parent, String... params);
    
    /**
     * @param parent the parent object, e.g. cabinet, folder
     * @param params the query parameters
     * @return the sysobjects (dm_sysobject or its subtype) feed under the specified object
     */
    public Feed<RestObject> getObjects(Linkable parent, String... params);
    
    /**
     * @param t the old resource representation
     * @param params the query parameters
     * @param <T> the linkable representation
     * @return get the resource by its self link
     */
    public <T extends Linkable> T get(T t, String... params);
    
    /**
     * @param uri the uri of the resource
     * @param clazz the resource type
     * @param params the query parameters
     * @param <T> the class type
     * @return the single object
     */
    public <T> T get(String uri, Class<T> clazz, String... params);
    
    /**
     * create a folder under specified folder/cabinet
     * @param parent the folder/cabinet where the new folder will be created under
     * @param newFolder the new folder with its properties
     * @param params the query parameters
     * @return the created folder
     */
    public RestObject createFolder(Linkable parent, RestObject newFolder, String... params);
    
    /**
     * @param folderUri the uri of the folder
     * @param params the query parameters
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
     * @param params the query parameters
     * @return the created sysobject
     */
    public RestObject createObject(Linkable parent, LinkRelation rel, RestObject objectToCreate, Object content, String contentMediaType, String... params);
    
    /**
     * create a sysobject (or its subtype) under specified parent's link rel
     * @param parent the parent object where the new sysobject will be created under
     * @param rel the LinkRelation used to create new object under the parent
     * @param objectToCreate the new object with its properties
     * @param contents the binary contents, it can be list of byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
     * @param contentMediaTypes the mediatypes of the contents
     * @param params the query parameters
     * @return the created sysobject
     */
    public RestObject createObject(Linkable parent, LinkRelation rel, RestObject objectToCreate, List<Object> contents, List<String> contentMediaTypes, String... params);

    /**
     * create a sysobject (or its subtype) under specified parent's link rel
     * @param parent the parent object where the new sysobject will be created under
     * @param rel the LinkRelation used to create new object under the parent
     * @param objectToCreate the new object with its properties
     * @return the created sysobject
     */
    public RestObject createObject(Linkable parent, LinkRelation rel, RestObject objectToCreate);

    /**
     * create a sysobject (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new sysobject will be created under
     * @param objectToCreate the new object with its properties
     * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
     * @param contentMediaType the mediatype of the content
     * @param params the query parameters
     * @return the created sysobject
     */
    public RestObject createObject(Linkable parent, RestObject objectToCreate, Object content, String contentMediaType, String... params);
    
    /**
     * create a sysobject (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new sysobject will be created under
     * @param objectToCreate the new object with its properties
     * @param contents the binary content, it can be list of byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
     * @param contentMediaTypes the mediatypes of the contents
     * @param params the query parameters
     * @return the created sysobject
     */
    public RestObject createObject(Linkable parent, RestObject objectToCreate, List<Object> contents, List<String> contentMediaTypes, String... params);

    /**
     * create a sysobject (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new sysobject will be created under
     * @param objectToCreate the new object with its properties
     * @param params the query parameters
     * @return the created sysobject
     */
    public RestObject createObject(Linkable parent, RestObject objectToCreate, String... params);

    /**
     * @param objectUri the uri of the object
     * @param params the query parameters
     * @return the sysobject of the specified uri
     */
    public RestObject getObject(String objectUri, String... params);
    
    /**
     * create a document (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new document will be created under
     * @param objectToCreate the new document with its properties
     * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param contentMediaType the mediatype of the content
     * @param params the query parameters
     * @return the created document
     */
    public RestObject createDocument(Linkable parent, RestObject objectToCreate, Object content, String contentMediaType, String... params);

    /**
     * create a document (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new document will be created under
     * @param objectToCreate the new document with its properties
     * @param contents the binary contents, it can be list of byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
     * @param contentMediaTypes the mediatypes of the contents
     * @param params the query parameters
     * @return the created document
     */
    public RestObject createDocument(Linkable parent, RestObject objectToCreate, List<Object> contents, List<String> contentMediaTypes, String... params);

    /**
     * create a document (or its subtype) under specified folder/cabinet
     * @param parent the folder/cabinet where the new document will be created under
     * @param objectToCreate the new document with its properties
     * @return the created document
     */
    public RestObject createDocument(Linkable parent, RestObject objectToCreate);

    /**
     * @param documentUri the uri of the document
     * @param params the query parameters
     * @return the document of the specified uri
     */
    public RestObject getDocument(String documentUri, String... params);
    
    /**
     * update the RestObject with new properties
     * @param oldObject the previously fetched RestObject
     * @param newObject the new RestObject with new properties to be updated
     * @param params the query parameters
     * @return the updated RestObject
     */
    public RestObject update(RestObject oldObject, RestObject newObject, String... params);
    
    /**
     * update the RestObject with new properties
     * @param oldObject the old object to be updated
     * @param rel the link relation
     * @param newObject the new object to be updated
     * @param method the http method
     * @param params the query parameters
     * @return the updated RestObject
     */
    public RestObject update(RestObject oldObject, LinkRelation rel, RestObject newObject, HttpMethod method, String... params);
    
    /**
     * delete the resource
     * @param linkable the linkable resource
     * @param params the query parameters
     */
    public void delete(Linkable linkable, String... params);
    
    /**
     * delete the resource by the uri 
     * @param uri the resource uri to be deleted
     * @param params the query parameters
     */
    public void delete(String uri, String... params);
    
    /**
     * create a content (primary content or rendition) to a RestObject
     * @param object the previously fetched RestObject
     * @param content the binary content, it can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param mediaType the mediaType of the content
     * @param params the query parameters
     * @return the created content RestObject
     */
    public RestObject createContent(RestObject object, Object content, String mediaType, String... params);
    
    /**
     * upload distributed content to the ACS or BOCS server
     * @param url the distributed content upload url
     * @param content the binary content
     */
    public void uploadDistributedContent(String url, InputStream content);
    
    /**
     * @param object the object where to get content from
     * @param params the query parameters
     * @return the metadata of primary content of the specified RestObject
     */
    public RestObject getPrimaryContent(RestObject object, String... params);
    
    /**
     * @param contentUri the uri of the content
     * @param params the query parameters
     * @return the metadata of the content specified by the uri
     */
    public RestObject getContent(String contentUri, String... params);
    
    /**
     * @param object the object to get contents
     * @param params the query parameters
     * @return the content metadata collection
     */
    public Feed<RestObject> getContents(RestObject object, String... params);
    
    /**
     * @param uri the uri of the content media
     * @return the content in bytes
     */
    @ClientAsyncOption(false)
    public byte[] getContentBytes(String uri);
    
    /**
     * @param uri the uri of the content media
     * @return the content file
     */
    @ClientAsyncOption(false)
    public File getContentFile(String uri, String destFilePath);
    
    /**
     * @param destFolder the destination folder
     * @param objectId the object id
     * @param params the query parameters
     * @return the archived contents file of the object
     * @throws IOException
     */
    @ClientAsyncOption(false)
    public File getArchivedContents(String destFolder, String objectId, String... params);
    
    /**
     * get current user
     * @param params the query parameters
     * @return the user RestObject
     */
    public RestObject getCurrentUser(String... params);
    
    /**
     * get user default folder
     * @param params the query parameters
     * @return the user default folder
     */
    public RestObject getDefaultFolder(String... params);
    
    /**
     * get users of the repository
     * @param params the query parameters
     * @return the users feed collection
     */
    public Feed<RestObject> getUsers(String... params);
    
    /**
     * get users of a parent
     * @param parent the parent resource
     * @param params the query parameters
     * @return the users collection
     */
    public Feed<RestObject> getUsers(Linkable parent, String... params);

    /**
     * get groups of the repository
     * @param params the query parameters
     * @return the groups collection
     */
    public Feed<RestObject> getGroups(String... params);
    
    /**
     * get groups of a parent
     * @param parent the parent resource
     * @param params the query parameters
     * @return the groups collection
     */
    public Feed<RestObject> getGroups(Linkable parent, String... params);
    
    /**
     * get single user
     * @param userUri the uri of the user
     * @param params the query parameters
     * @return the user RestObject
     */
    public RestObject getUser(String userUri, String... params);
    
    /**
     * get single group
     * @param groupUri the uri of the group
     * @param params the query parameters
     * @return the group RestObject
     */
    public RestObject getGroup(String groupUri, String... params);
    
    /**
     * create an user
     * @param userToCreate the user to be created
     * @return created user RestObject
     */
    public RestObject createUser(RestObject userToCreate);
    
    /**
     * create a group
     * @param groupToCreate the group to be created
     * @return created group RestObject
     */
    public RestObject createGroup(RestObject groupToCreate);
    
    /**
     * add the user to a group
     * @param group the group to be added into
     * @param user the user to be added
     */
    public void addUserToGroup(RestObject group, RestObject user);
    
    /**
     * add the group to a parent group
     * @param group the parent group to be added into
     * @param subGroup the sub group to be added into parent group
     */
    public void addGroupToGroup(RestObject group, RestObject subGroup);
    
    /**
     * @param feed the current page
     * @param <T> the Linkable type
     * @return the next page of the feed collection if it has
     */
    public <T extends Linkable> Feed<T> nextPage(Feed<T> feed);
    
    /**
     * @param feed the current page
     * @param <T> the Linkable type
     * @return the previous page of the feed collection if it has
     */
    public <T extends Linkable> Feed<T> previousPage(Feed<T> feed);

    /**
     * @param feed the current page
     * @param <T> the Linkable type
     * @return the first page of the feed collection if it has
     */
    public <T extends Linkable> Feed<T> firstPage(Feed<T> feed);

    /**
     * @param feed the current page
     * @param <T> the Linkable type
     * @return the last page of the feed collection if it has
     */
    public <T extends Linkable> Feed<T> lastPage(Feed<T> feed);
    
    /**
     * @param object the object to get the version collection
     * @param params the query parameters
     * @return the version histories of the given object
     */
    public Feed<RestObject> getVersions(RestObject object, String... params);
    
    /**
     * check out the given RestObject (sysobject, document and their subtype)
     * @param object the object to be checked out
     * @param params the query parameters
     * @return checked out RestObject
     */
    public RestObject checkout(RestObject object, String... params);
    
    /**
     * cancel a checked out object
     * @param object the object to be cancelled check out
     */
    public void cancelCheckout(RestObject object);
    
    /**
     * check in the object metadata only with next major version
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param params the query parameters
     * @return checked in object
     */
    public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, String... params);
    
    /**
     * check in the object with next major version
     * can check in both new object metadata and binary content , or new metadata or binary content only
     * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param content the binary content
     * @param contentMediaType the mediatype of the content
     * @param params the query parameters
     * @return checked in object
     */
    public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, Object content, String contentMediaType, String... params);

    /**
     * check in the object with next major version
     * can check in both new object metadata and binary content , or new metadata or binary content only
     * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param contents the binary contents, it can be list of byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
     * @param contentMediaTypes the mediatypes of the contents
     * @param params the query parameters
     * @return checked in object
     */
    public RestObject checkinNextMajor(RestObject oldObject, RestObject newObject, List<Object> contents, List<String> contentMediaTypes, String... params);

    /**
     * check in the object with next minor version
     * can check in both new object metadata and binary content , or new metadata or binary content only
     * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param content the binary content
     * @param contentMediaType the mediatype of the content
     * @param params the query parameters
     * @return checked in object
     */
    public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, Object content, String contentMediaType, String... params);
    
    /**
     * check in the object metadata only with next minor version
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param params the query parameters
     * @return checked in object
     */
    public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, String... params);

    /**
     * check in the object with next minor version
     * can check in both new object metadata and binary content , or new metadata or binary content only
     * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param contents the binary contents, it can be list of byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
     * @param contentMediaTypes the mediatypes of the contents
     * @param params the query parameters
     * @return checked in object
     */
    public RestObject checkinNextMinor(RestObject oldObject, RestObject newObject, List<Object> contents, List<String> contentMediaTypes, String... params);

    /**
     * check in the object with branch version
     * can check in both new object metadata and binary content , or new metadata or binary content only
     * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param content the binary content
     * @param contentMediaType the mediatype of the content
     * @param params the query parameters
     * @return checked in object
     */
    public RestObject checkinBranch(RestObject oldObject, RestObject newObject, Object content, String contentMediaType, String... params);
    
    /**
     * check in the object metadata only with branch version
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param params the query parameters
     * @return checked in object
     */
    public RestObject checkinBranch(RestObject oldObject, RestObject newObject, String... params);

    /**
     * check in the object with branch version
     * can check in both new object metadata and binary content , or new metadata or binary content only
     * the content can be byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object
     * @param oldObject the checked out RestObject
     * @param newObject the new metadata to be checked in
     * @param contents the binary contents, it can be list of byte array, String, javax.xml.transform.Source, org.springframework.core.io.Resource, JAXB object, and Jackson json object  
     * @param contentMediaTypes the mediatypes of the contents
     * @param params the query parameters
     * @return checked in object
     */
    public RestObject checkinBranch(RestObject oldObject, RestObject newObject, List<Object> contents, List<String> contentMediaTypes, String... params);

    /**
     * materialize the lightweight object
     * @param oldObject the object to be materialized
     * @return materialized lightweight object
     */
    public RestObject materialize(RestObject oldObject);

    /**
     * dematerialize the lightweight object
     * @param oldObject the object to be dematerialized
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
     * @param params the query parameters
     * @return the RestType
     */
    public RestType getType(String name, String... params);

    /**
     * @param params the query parameters
     * @return the types info in the repository
     */
    public Feed<RestType> getTypes(String... params);
    
    /**
     * get value assistance of a type
     * @param type the type resource
     * @param request the va request
     * @param params the query parameters
     * @return the value assistance
     */
    public ValueAssistant getValueAssistant(RestType type, ValueAssistantRequest request, String... params);
    
    /**
     * @param params the query parameters
     * @return the aspect types info in the repository
     */
    public Feed<RestObject> getAspectTypes(String... params);
    
    /**
     * get the aspect type
     * @param aspectType the aspect type name
     * @param params the query parameters
     * @return the aspect type RestObject
     */
    public RestObject getAspectType(String aspectType, String... params);
    
    /**
     * attach aspects to the object
     * @param object the object to be attached to
     * @param aspects the aspects
     * @return the attached aspects
     */
    public ObjectAspects attach(RestObject object, String... aspects);
    
    /**
     * detach an aspect from an object
     * @param objectAspects the object aspects to be detached from
     * @param aspect the aspect name
     */
    public void detach(ObjectAspects objectAspects, String aspect);
    
    /**
     * get object aspects
     * @param object the object
     * @param params the query parameters
     * @return the attached aspects
     */
    public ObjectAspects getObjectAspects(RestObject object, String... params);

    /**
     * get relation types of the repository
     * @param params the query parameters
     * @return the relation type collection feed
     */
    public Feed<RestObject> getRelationTypes(String... params);
    
    /**
     * get single relation type
     * @param uri the uri of the relation type
     * @param params the query parameters
     * @return the relation type RestObject
     */
    public RestObject getRelationType(String uri, String... params);

    /**
     * get relations of the repository
     * @param params the query parameters
     * @return the relations feed
     */
    public Feed<RestObject> getRelations(String... params);
    
    /**
     * get single relation
     * @param uri the uri of the relation
     * @param params the query parameters
     * @return the relation RestObject
     */
    public RestObject getRelation(String uri, String... params);
    
    /**
     * create a relation
     * @param object the relation to be created
     * @return the created relation
     */
    public RestObject createRelation(RestObject object);
    
    /**
     * get formats of the repository
     * @param params the query parameters
     * @return the format collection
     */
    public Feed<RestObject> getFormats(String... params);
    
    /**
     * get single format
     * @param uri the uri of the format
     * @param params the query parameters
     * @return the format RestObject
     */
    public RestObject getFormat(String uri, String... params);

    /**
     * get network locations of the repository
     * @param params the query parameters
     * @return the network location collection
     */
    public Feed<RestObject> getNetworkLocations(String... params);
    
    /**
     * get single network location
     * @param uri the uri of the network location
     * @param params the query parameters
     * @return the network location RestObject
     */
    public RestObject getNetworkLocation(String uri, String... params);
    
    /**
     * get folder links of the object
     * @param object the object to be get folder link
     * @param rel the link relation
     * @param params the query parameters
     * @return folder link collection
     */
    public Feed<FolderLink> getFolderLinks(Linkable object, LinkRelation rel, String... params);
    
    /**
     * get single folder link
     * @param uri the folder link uri
     * @param params the query parameters
     * @return the folder link object
     */
    public FolderLink getFolderLink(String uri, String... params);
    
    /**
     * move the object by the folder link
     * @param oldLink the folder link to be moved
     * @param newLink the new destination
     * @param params the query parameters
     * @return moved folder link
     */
    public FolderLink move(FolderLink oldLink, FolderLink newLink, String... params);
    
    /**
     * link an object to another place
     * @param object the object to be linked
     * @param rel the link relation
     * @param link the folder link
     * @return linked folder link result
     */
    public FolderLink link(Linkable object, LinkRelation rel, FolderLink link);
    
    /**
     * get batch capabilities
     * @return capabilities of the batch
     */
    public Capabilities getBatchCapabilities();
    
    /**
     * create and execute a batch
     * @param batch the batch to be executed
     * @return the batch result
     */
    @NotBatchable
    public Batch createBatch(Batch batch);
    
    /**
     * get user preferences
     * @param params the query parameters
     * @return the preferences collection
     */
    public Feed<Preference> getPreferences(String... params);
    
    /**
     * get single preference
     * @param uri the uri of the preference
     * @param params the query parameters
     * @return the preference object
     */
    public Preference getPreference(String uri, String... params);
    
    /**
     * create a preference
     * @param preference the preference to be created
     * @return the created preference
     */
    public Preference createPreference(Preference preference);
    
    /**
     * update a preference
     * @param oldPreference the preference to be updated
     * @param newPreference the new preference
     * @return updated preference
     */
    public Preference updatePreference(Preference oldPreference, Preference newPreference);

    /**
     * get acl collection of the repository
     * @param params the query parameters
     * @return the acl collection feed
     */
    public Feed<RestObject> getAcls(String... params);
    
    /**
     * get acl associations
     * @param params the query parameters
     * @return the acl associations feed
     */
    public Feed<RestObject> getAclAssociations(Linkable acl, String... params);
    
    /**
     * get single acl
     * @param uri the uri of the acl
     * @param params the query parameters
     * @return the acl RestObject
     */
    public RestObject getAcl(String uri, String... params);
    
    /**
     * create an acl
     * @param object the acl to be created
     * @return the created acl
     */
    public RestObject createAcl(RestObject object);
    
    /**
     * get the permission
     * @param linkable the object
     * @param params the query parameters
     * @return the permission object
     */
    public Permission getPermission(Linkable linkable, String... params);

    /**
     * get the permission-set
     * @param linkable the object or the user
     * @param params the query parameters
     * @return the permission set object
     */
    public PermissionSet getPermissionSet(Linkable linkable, String... params);

    /**
     * @param parent the parent object
     * @param params the query parameters
     * @return the comments feed of the specified object
     */
    public Feed<Comment> getComments(Linkable parent, String... params);
    
    /**
     * create a Comment of the specified object
     * @param parent the object to create comment to
     * @param comment the comment to be created
     * @return the created comment
     */
    public Comment createComment(Linkable parent, Comment comment);
    
    /**
     * @param commentUri the uri of the comment
     * @param params the query parameters
     * @return the comment of the specified uri
     */
    public Comment getComment(String commentUri, String... params);
    
    /**
     * @param parent the parent comment
     * @param params the query parameters
     * @return the comment replies feed of the specified comment
     */
    public Feed<Comment> getReplies(Linkable parent, String... params);
    
    /**
     * create a reply of the specified comment
     * @param parent the comment to create reply to
     * @param comment the reply to be created
     * @return the created reply
     */
    public Comment createReply(Linkable parent, Comment comment);
    
    /**
     * @param linkable the virtual document object
     * @param params the query parameters
     * @return the virtual document nodes feed of the specified object
     */
    public Feed<VirtualDocumentNode> getVirtualDocumentNodes(Linkable linkable, String... params);
    
    /**
     * get search templates of the repository
     * @param params the query parameters
     * @return the search template feed
     */
    public Feed<SearchTemplate> getSearchTemplates(String... params);
    
    /**
     * get single search template
     * @param uri the uri of the search template
     * @param params the query parameters
     * @return the search template
     */
    public SearchTemplate getSearchTemplate(String uri, String... params);
    
    /**
     * create a search template
     * @param template the search template to be created
     * @return the created search template
     */
    public SearchTemplate createSearchTemplate(SearchTemplate template);

    /**
     * execute a search template
     * @param toBeExecuted the search template to be execute
     * @param params the query parameters
     * @return the search result
     */
    public SearchFeed<RestObject> executeSearchTemplate(SearchTemplate toBeExecuted, String... params);

    /**
     * get saved searches of the repository
     * @param params the query parameters
     * @return the saved search feed
     */
    public Feed<SavedSearch> getSavedSearches(String... params);
    
    /**
     * get single saved search
     * @param uri the uri of the saved search
     * @param params the query parameters
     * @return the saved search
     */
    public SavedSearch getSavedSearch(String uri, String... params);
    
    /**
     * create a saved search
     * @param savedSearch the saved search to be created
     * @return the created saved search
     */
    public SavedSearch createSavedSearch(SavedSearch savedSearch);

    /**
     * update a saved search
     * @param oldSavedSearch the saved search to be updated
     * @param newSavedSearch the new saved search
     * @return updated saved search
     */
    public SavedSearch updateSavedSearch(SavedSearch oldSavedSearch, SavedSearch newSavedSearch);
    
    /**
     * execute a saved search
     * @param toBeExecuted the saved search to be execute
     * @param params the query parameters
     * @return the search result
     */
    public SearchFeed<RestObject> executeSavedSearch(SavedSearch toBeExecuted, String... params);
    
    /**
     * enable and refresh the saved search result
     * @param toBeExecuted the saved search to be enable
     * @param params the query parameters
     * @return the search result
     */
    public SearchFeed<RestObject> enableSavedSearchResult(SavedSearch toBeExecuted, String... params);

    /**
     * disable the saved search result
     * @param toBeExecuted the saved search to be execute
     */
    public void disableSavedSearchResult(SavedSearch toBeExecuted);

    /**
     * get the saved search result
     * @param toBeExecuted the saved search to be execute
     * @param params the query parameters
     * @return the search result
     */
    public SearchFeed<RestObject> getSavedSearchResult(SavedSearch toBeExecuted, String... params);
    
    /**
     * send put method to the server
     * @param uri the request uri
     * @param responseBodyClass the response
     * @param params the query parameters
     * @return the put result
     */
    public <T> T put(String uri, Class<? extends T> responseBodyClass, String... params);

    /**
     * get lifecycles of the repository
     * @param params the query parameters
     * @return the lifecycle collection
     */
    public Feed<Lifecycle> getLifecycles(String... params);
    
    /**
     * get single Lifecycle
     * @param uri the uri of the lifecycle
     * @param params the query parameters
     * @return the lifecycle object
     */
    public Lifecycle getLifecycle(String uri, String... params);
    
    /**
     * attach a lifecycle to the object
     * @param object the RestObject
     * @param objectLifecycle the lifecycle info to be attached
     * @return the object lifecycle state
     */
    public ObjectLifecycle attach(RestObject object, ObjectLifecycle objectLifecycle);
    
    /**
     * detach the lifecycle from the object
     * @param objectLifecycle the object lifecycle state
     */
    public void detach(ObjectLifecycle objectLifecycle);
    
    /**
     * get the current object lifecycle state
     * @param object the RestObject
     * @param params the query parameters
     * @return the object lifecycle state
     */
    public ObjectLifecycle getObjectLifecycle(RestObject object, String... params);

    /**
     * promote the object lifecycle
     * @param objectLifecycle the object lifecycle state
     * @param params query params
     * @return the object lifecycle state 
     */
    public ObjectLifecycle promote(ObjectLifecycle objectLifecycle, String... params);
    
    /**
     * demote the object lifecycle
     * @param objectLifecycle the object lifecycle state
     * @param params query params
     * @return the object lifecycle state 
     */
    public ObjectLifecycle demote(ObjectLifecycle objectLifecycle, String... params);

    /**
     * suspend the object lifecycle
     * @param objectLifecycle the object lifecycle state
     * @param params query params
     * @return the object lifecycle state 
     */
    public ObjectLifecycle suspend(ObjectLifecycle objectLifecycle, String... params);

    /**
     * resume the object lifecycle
     * @param objectLifecycle the object lifecycle state
     * @param params query params
     * @return the object lifecycle state 
     */
    public ObjectLifecycle resume(ObjectLifecycle objectLifecycle, String... params);
    
    /**
     * cancel all scheduled operation
     * @param objectLifecycle the object lifecycle state
     */
    public void cancel(ObjectLifecycle objectLifecycle);
    
    /**
     * subscribe the object for subscribes
     * @param object the object to be subscribed
     * @param subscribers the subscribers, if no subscribers provided, then subscriber for current user
     * @return the subscribed object
     */
    public RestObject subscribe(RestObject object, String... subscribers);
    
    /**
     * unsubscribe the object for the current user
     * @param object the object to be unsubscribed
     */
    public void unsubscribe(RestObject object);

    /**
     * get user subscriptions
     * @param params the query parameters
     * @return the subscribed objects
     */
    public Feed<RestObject> getSubscriptions(String... params);
    
    /**
     * get audit policies
     * @param params the query parameters
     * @return the audit policies
     */
    public Feed<AuditPolicy> getAuditPolicies(String... params);
    
    /**
     * create an audit policy
     * @param auditPolicy the audit policy to be created
     * @return the created audit policy
     */
    public AuditPolicy createAuditPolicy(AuditPolicy auditPolicy);
    
    /**
     * get audit policy
     * @param uri the audit policy uri
     * @param params the query parameters
     * @return the audit policy
     */
    public AuditPolicy getAuditPolicy(String uri, String... params);
    
    /**
     * update the audit policy
     * @param oldPolicy the old audit policy
     * @param newPolicy the new audit policy
     * @return the updated audit policy
     */
    public AuditPolicy updateAuditPolicy(AuditPolicy oldPolicy, AuditPolicy newPolicy);
    
    /**
     * delete the audit policy
     * @param auditPolicy to be deleted
     */
    public void deleteAuditPolicy(AuditPolicy auditPolicy);

    /**
     * get the current user's recent audit trails
     * @param params the query parameters
     * @return the audit trails collection
     */
    public Feed<RestObject> getRecentAuditTrails(String... params);

    /**
     * get single audit trail
     * @param auditTrailUri the uri of the audit trail
     * @param params the query parameters
     * @return the audit trail object
     */
    public AuditTrail getAuditTrail(String auditTrailUri, String... params);
    
    /**
     * get available audit events
     * @param params the query parameters
     * @return all available audit events
     */
    public AvailableAuditEvents getAvailableAuditEvents(String... params);
    
    /**
     * get all the registered audit events
     * @param params the query parameters
     * @return the registered audit events
     */
    public Feed<RestObject> getRegisteredAuditEvents(String... params);
    
    /**
     * register an audit event
     * @param auditEvent the audit event to be registered
     * @param params the query parameters
     * @return the registered audit event
     */
    public RestObject registerAuditEvent(RestObject auditEvent, String... params);
    
    /**
     * get the single registered audit event
     * @param uri the uri
     * @param params the query parameters
     * @return the registered audit event
     */
    public RestObject getRegisteredAuditEvent(String uri, String... params);
    
    /**
     * unregister the audit event
     * @param auditEvent the audit event to be unregistered
     */
    public void unregisterAuditEvent(RestObject auditEvent);
}
