/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client;

import java.util.List;

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
     * @return the DCTMRestClient itself
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
     * @param params the query parameters
     * @return the cabinets feed based on query parameters
     */
    public Feed<RestObject> getCabinets(String... params);
    
    /**
     * @param cabinet the cabinet name
     * @param params the query parameters
     * @return the cabinet by its name
     */
    @NotBatchable
    public RestObject getCabinet(String cabinet, String... params);
    
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
     * @param object the old object
     * @param params the query parameters
     * @return get the object by its self link
     */
    public RestObject get(RestObject object, String... params);
    
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
     * @return the created sysobject
     */
    public RestObject createObject(Linkable parent, RestObject objectToCreate);

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
    public byte[] getContentBytes(String uri);
    
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
}
