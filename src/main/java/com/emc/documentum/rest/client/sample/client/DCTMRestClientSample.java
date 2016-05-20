/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emc.documentum.rest.client.sample.client.util.Randoms;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.Link;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Linkable;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.emc.documentum.rest.client.sample.model.PlainRestObject;
import com.emc.documentum.rest.client.sample.model.PlainValueAssistantRequest;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.emc.documentum.rest.client.sample.model.ValueAssistant;


/**
 * sample application uses DCTMRestClient
 */
public class DCTMRestClientSample {
    private static DCTMRestClient client;
    private static final String NEWLINE = System.getProperty("line.separator");
    
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("Please input the client representation type: ");
        for(DCTMRestClientBinding b : DCTMRestClientBinding.values()) {
            sb.append(b.name() + " ");
        }
        String bindingStr = read(sb.toString(), "XML");
        DCTMRestClientBinding binding = DCTMRestClientBinding.valueOf(bindingStr.toUpperCase());
        String contextRoot = read("Please input the REST context path:", "http://localhost:8080/dctm-rest");
        String repository = read("Please input the repository name:", "REPO");
        String username = read("Please input the username:", "dmadmin");
        String password = read("Please input the password:", "password");
        String useFormatExtension = read("Please input the whether add format extension .xml or .json for URI:", "false");
        String debug = read("Please input whether print debug information:", "false");
        
        DCTMRestClientBuilder builder = new DCTMRestClientBuilder().
                bind(binding).
                contextRoot(contextRoot).
                credentials(username, password).
                repository(repository).
                useFormatExtension("true".equalsIgnoreCase(useFormatExtension)).
                debug("true".equalsIgnoreCase(debug));
        
        client = builder.build();
        
        RestObject productInfo = client.getProductInfo();
        System.out.println();
        
        sb = new StringBuilder();
        sb.append(productInfo.getProperties().get("product") + " " + productInfo.getProperties().get("product_version")).append(NEWLINE)
          .append("Please input the number of the sample operation which need be executed:").append(NEWLINE)
          .append("0 Exit").append(NEWLINE)
          .append("1 Navigation").append(NEWLINE)
          .append("2 Folder Create/Update/Delete").append(NEWLINE)
          .append("3 Sysobject Create/Update/Delete").append(NEWLINE)
          .append("4 Document Create/Update/Delete").append(NEWLINE)
          .append("5 Content Management").append(NEWLINE)
          .append("6 Version Management").append(NEWLINE)
          .append("7 DQL Query").append(NEWLINE)
          .append("8 Type(REST Services 7.x) and Value Assistance (REST Services 7.3+)").append(NEWLINE)
          .append("9 Lightweight Object Type/Create/Materialize/Dematerialize/Reparent (REST Services 7.3+)").append(NEWLINE)
          .append("10 Aspect AspectType/Attach/Detach (REST Services 7.3+)").append(NEWLINE)
          .append("11 Get Group(s)/User(s)/Default Folder (REST Services 7.x) and Create/Update/Delete User/Group Add/Remove User to/from Group (REST Services 7.3+)").append(NEWLINE)
          .append("12 Get Relation Typs(s) and Relation Create/Delete/Get").append(NEWLINE)
          .append("13 Get Format(s)").append(NEWLINE)
          .append("14 Get Network Location(s)").append(NEWLINE);
        
        while(true) {
            String sample = read(sb.toString());
            int op = 0;
            try {
                op = Integer.parseInt(sample);
                switch(op) {
                    case 0:
                        System.out.println("Thanks, bye.");
                        System.exit(0);
                        break;
                    case 1:
                        navigation();
                        break;
                    case 2:
                        crudFolder();
                        break;
                    case 3:
                        crudSysobject();
                        break;
                    case 4:
                        crudDocument();
                        break;
                    case 5:
                        contentManagement();
                        break;
                    case 6:
                        versionManagement();
                        break;
                    case 7:
                        dqlQuery();
                        break;
                    case 8:
                        type();
                        break;
                    case 9:
                        cmdrLightweightObject();
                        break;
                    case 10:
                        aspect();
                        break;
                    case 11:
                        userGroup();
                        break;
                    case 12:
                        relation();
                        break;
                    case 13:
                        format();
                        break;
                    case 14:
                        networkLocation();
                        break;
                    default:
                        System.out.println("Unsupported " + op);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private static String read(String prompt) {
        byte[] bytes = new byte[100];
        String value = null;
        while(value == null || value.length() == 0) {
            try {
                System.out.println(prompt);
                int readed;
                    readed = System.in.read(bytes);
                while(readed <= 0) {
                    System.out.println(prompt);
                    readed = System.in.read(bytes);
                }
                value = new String(bytes, 0, readed).trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return value;
    }
    
    private static String read(String prompt, String defaultValue) {
        System.out.println(prompt + " [default " + defaultValue + "]"); 
        String value = defaultValue;
        byte[] bytes = new byte[100];
        int readed;
        try {
            readed = System.in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
            return defaultValue;
        }
        if(readed > 0) {
            String tmp = new String(bytes, 0, readed).trim();
            if(tmp.length() > 0) {
                value = tmp;
            }
        }
        return value;
    }

    /**
     * samples to navigate the Repository
     * including get HomeDocument, Repositories Feed, Repository, Cabinets Feed, Cabinet, Folder, Document, Sysobject...
     * also has samples about Feed paging and embedded content into the Feed 
     */
    private static void navigation() {
        System.out.println("start Navigation sample");
        
        System.out.println("-------------get home document resource");
        HomeDocument home = client.getHomeDocument();
        List<Link> list = home.getLinks();
        for(Link l : list) {
            System.out.println(l.getRel() + " -> " + l.getHref());
        }
        printNewLine();
        
        System.out.println("-------------get repositories collection");
        Feed<Repository> repositories = client.getRepositories();
        System.out.println("There are " + repositories.getTotal() + " repositories in total.");
        printEntryContentSrc(repositories);
        printNewLine();

        System.out.println("-------------get the repository");
        Repository repository = client.getRepository();
        System.out.println(repository.getName());
        for(Repository.Server s : repository.getServers()) {
            System.out.println("Name: " + s.getName());
            System.out.println("Host: " + s.getHost());
            System.out.println("Docbroker: " + s.getDocbroker());
            System.out.println("Version: " + s.getVersion());
        }
        printNewLine();
        
        System.out.println("-------------get cabinets");
        Feed<RestObject> cabinets = client.getCabinets();
        printEntryContentSrc(cabinets);
        printNewLine();

        System.out.println("-------------get cabinets by page with 2 cabinets per page");
        cabinets = client.getCabinets("items-per-page", "2", "include-total", "true");
        System.out.println("There are " + cabinets.getTotal() + " cabinets in total.");
        printEntryContentSrc(cabinets);
        printNewLine();

        System.out.println("-------------get cabinets of the next page");
        cabinets = client.nextPage(cabinets);
        printEntryContentSrc(cabinets);
        printNewLine();
        
        System.out.println("-------------get cabinets of the last page");
        cabinets = client.lastPage(cabinets);
        printEntryContentSrc(cabinets);
        printNewLine();

        System.out.println("-------------get cabinets of the previous page");
        cabinets = client.previousPage(cabinets);
        printEntryContentSrc(cabinets);
        printNewLine();
        
        System.out.println("-------------get the Temp cabinet");
        RestObject tempCabinet = client.getCabinet("Temp");
        printRestObject(tempCabinet);
        printNewLine();

        System.out.println("-------------get folders under the Temp cabinet");
        Feed<RestObject> folders = client.getFolders(tempCabinet);
        printEntryContentSrc(folders);
        printNewLine();
        
        System.out.println("-------------get documents under the Temp cabinet");
        Feed<RestObject> documents = client.getDocuments(tempCabinet);
        printEntryContentSrc(documents);
        printNewLine();
        
        System.out.println("-------------get sysobjects under the Temp cabinet");
        Feed<RestObject> objects = client.getDocuments(tempCabinet);
        printEntryContentSrc(objects);
        printNewLine();
        
        System.out.println("-------------get cabinets with the content embedded in the feed entry");
        cabinets = client.getCabinets("inline", "true");
        for(Entry<RestObject> e : cabinets.getEntries()) {
            RestObject o = e.getContentObject();
            printRestObject(o);
            printLinkRel(o, LinkRelation.SELF);
        }
        printNewLine();
        
        System.out.println("-------------get folders under the Temp cabinet with the content embedded in the feed entry");
        folders = client.getFolders(tempCabinet, "inline", "true");
        for(Entry<RestObject> e : folders.getEntries()) {
            RestObject o = e.getContentObject();
            printRestObject(o);
            printLinkRel(o, LinkRelation.SELF);
        }
        printNewLine();
        
        System.out.println("finish Navigation sample");
        printNewLine();
    }
    
    /**
     * samples to create/delete/update folder
     */
    private static void crudFolder() {
        System.out.println("start Folder Create/Update/Delete sample");

        RestObject tempCabinet = client.getCabinet("Temp");
        
        System.out.println("-------------create a folder under the Temp cabinet");
        RestObject newFolder = new PlainRestObject("object_name", "my_new_folder");
        RestObject createdFolder = client.createFolder(tempCabinet, newFolder);
        printHttpStatus();
        printRestObject(createdFolder);
        printNewLine();

        System.out.println("-------------update the folder name");
        RestObject updateFolder = new PlainRestObject("object_name", "my_new_folder_updated");
        RestObject updatedFolder = client.update(createdFolder, updateFolder);
        printHttpStatus();
        printRestObject(updatedFolder);
        printNewLine();
        
        System.out.println("-------------delete the created folder");
        client.delete(createdFolder);
        printHttpStatus();
        printNewLine();
        
        System.out.println("-------------create a folder under the Temp cabinet");
        RestObject newFolder1 = new PlainRestObject("object_name", "my_new_folder1");
        RestObject createdFolder1 = client.createFolder(tempCabinet, newFolder1);
        printHttpStatus();
        printRestObject(createdFolder1);
        printNewLine();
        
        System.out.println("-------------create a folder under the folder just created");
        RestObject newFolder2 = new PlainRestObject("object_name", "my_new_folder2");
        RestObject createdFolder2 = client.createFolder(createdFolder1, newFolder2);
        printHttpStatus();
        printRestObject(createdFolder2);
        printNewLine();
        
        System.out.println("-------------delete the created folder with a folder in it");
        client.delete(createdFolder1, "del-non-empty", "true");
        printHttpStatus();
        printNewLine();
        
        System.out.println("finish Folder Create/Update/Delete sample");
        printNewLine();
    }
    
    /**
     * samples to create/delete/update Sysobject, including create object with binary content
     */
    private static void crudSysobject() {
        System.out.println("start Sysobject Create/Update/Delete sample");
        
        RestObject tempCabinet = client.getCabinet("Temp");
        
        System.out.println("-------------create an object without binary content under the Temp cabinet");
        RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
        RestObject createdObjectWithoutContent = client.createObject(tempCabinet, newObjectWithoutContent, null);
        printHttpStatus();
        printRestObject(createdObjectWithoutContent);
        printNewLine();
        
        System.out.println("-------------update the object with a new name");
        RestObject updateObjectName = new PlainRestObject("object_name", "new_object_name");
        RestObject updatedObjectWithObjectName = client.update(createdObjectWithoutContent, updateObjectName);
        printHttpStatus();
        printRestObject(updatedObjectWithObjectName);
        printNewLine();
        
        System.out.println("-------------delete the created object");
        client.delete(updatedObjectWithObjectName);
        printHttpStatus();
        printNewLine();
        
        System.out.println("-------------create an object with repeating properties and specify dm_docment type under the Temp cabinet");
        Map<String, Object> newPropertiesMap = new HashMap<String, Object>();
        newPropertiesMap.put("r_object_type", "dm_document");
        newPropertiesMap.put("object_name", "obj_with_repeating_properties");
        newPropertiesMap.put("keywords", Arrays.asList("objects", "repeating", "properties"));
        RestObject newObjectWithRepeatingProperties = new PlainRestObject(newPropertiesMap);
        RestObject createdObjectWithRepeatingProperties = client.createObject(tempCabinet, newObjectWithRepeatingProperties, null);
        printHttpStatus();
        printRestObject(createdObjectWithRepeatingProperties, "r_object_id", "object_name", "r_object_type", "keywords");
        printNewLine();
        
        System.out.println("-------------delete the created object");
        client.delete(createdObjectWithRepeatingProperties);
        printHttpStatus();
        printNewLine();

        System.out.println("-------------create an object with specify object type in another place to override the r_object_type");
        RestObject newObjectWithOverwriteType = new PlainRestObject("dm_sysobject", newPropertiesMap);
        RestObject createdObjectWithOverwriteType = client.createObject(tempCabinet, newObjectWithOverwriteType, null);
        printHttpStatus();
        printRestObject(createdObjectWithOverwriteType);
        printNewLine();
        
        System.out.println("-------------delete the created object");
        client.delete(createdObjectWithOverwriteType);
        printHttpStatus();
        printNewLine();

        System.out.println("-------------create an object with binary content under the Temp cabinet");
        RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
        RestObject createdObjectWithContent = client.createObject(tempCabinet, newObjectWithContent, "I'm the content of the object", "format", "crtext");
        printHttpStatus();
        printRestObject(createdObjectWithContent);
        printNewLine();
        
        System.out.println("-------------delete the created object");
        client.delete(createdObjectWithContent);
        printHttpStatus();        
        printNewLine();
        
        System.out.println("finish Sysobject Create/Update/Delete sample");
        printNewLine();
    }
    
    /**
     * samples to create/delete/update document, including create document with binary content
     */
    private static void crudDocument() {
        System.out.println("start Document Create/Update/Delete sample");
        
        RestObject tempCabinet = client.getCabinet("Temp");
        
        System.out.println("-------------create a document without binary content under the Temp cabinet");
        RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
        RestObject createdObjectWithoutContent = client.createDocument(tempCabinet, newObjectWithoutContent, null);
        printHttpStatus();
        printRestObject(createdObjectWithoutContent);
        printNewLine();
        
        System.out.println("-------------update the document with a new name");
        RestObject updateObjectName = new PlainRestObject("object_name", "new_object_name");
        RestObject updatedObjectWithObjectName = client.update(createdObjectWithoutContent, updateObjectName);
        printHttpStatus();
        printRestObject(updatedObjectWithObjectName);
        printNewLine();
        
        System.out.println("-------------delete the created document");
        client.delete(updatedObjectWithObjectName);
        printHttpStatus();
        printNewLine();

        System.out.println("-------------create a document with binary content under the Temp cabinet");
        RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
        RestObject createdObjectWithContent = client.createDocument(tempCabinet, newObjectWithContent, "I'm the content of the object", "format", "crtext");
        printHttpStatus();
        printRestObject(createdObjectWithContent);
        printNewLine();
        
        System.out.println("-------------delete the created object");
        client.delete(createdObjectWithContent);
        printHttpStatus();        
        printNewLine();
        
        System.out.println("finish Document Create/Update/Delete sample");
        printNewLine();
    }
    
    /**
     * samples to manage the content
     * including create object with content, create a rendition, get primary content, and get all contents' feed 
     */
    private static void contentManagement() {
        System.out.println("start Content Management sample");
        
        RestObject tempCabinet = client.getCabinet("Temp");
        
        System.out.println("-------------create a document with binary content under the Temp cabinet");
        RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
        RestObject createdObjectWithContent = client.createDocument(tempCabinet, newObjectWithContent, "I'm the content of the object", "format", "crtext");
        printHttpStatus();
        printRestObject(createdObjectWithContent);
        printNewLine();
        
        System.out.println("-------------get primary content of the document");
        RestObject primaryContent = client.getPrimaryContent(createdObjectWithContent, "media-url-policy", "all");
        System.out.println("the ACS link: " + primaryContent.getHref(LinkRelation.ENCLOSURE, "ACS"));
        System.out.println("the local link: " + primaryContent.getHref(LinkRelation.ENCLOSURE, "LOCAL"));
        printNewLine();

        System.out.println("-------------create a new rendition to the document");
        client.enableStreamingForNextRequest();
        client.createContent(createdObjectWithContent, "I'm the rendition content", "text/html", "primary", "false");
        printHttpStatus();
        printNewLine();
        
        System.out.println("-------------get primary content and rendition collection of the document");
        Feed<RestObject> renditionList = client.getContents(createdObjectWithContent);
        for(Entry<RestObject> renditionEntry : renditionList.getEntries()) {
            System.out.println(renditionEntry.getTitle());
            RestObject rendition = client.getContent(renditionEntry.getContentSrc());
            System.out.println("the content link: " + rendition.getHref(LinkRelation.ENCLOSURE));
        }
        printNewLine();
        
        System.out.println("-------------delete the created document");
        client.delete(createdObjectWithContent);
        printHttpStatus();        
        printNewLine();
        
        System.out.println("finish Content Management sample");
        printNewLine();
    }
    
    /**
     * samples to manage versioning
     * including check out, cancel check out, and check in with next major/minor or branch version
     */
    private static void versionManagement() {
        System.out.println("start Version Management sample");
        
        RestObject tempCabinet = client.getCabinet("Temp");
        
        System.out.println("-------------create a document without binary content under the Temp cabinet");
        RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
        RestObject createdObjectWithoutContent = client.createDocument(tempCabinet, newObjectWithoutContent, null);
        printHttpStatus();
        printRestObject(createdObjectWithoutContent, "r_object_id", "r_lock_owner");
        printNewLine();
        
        System.out.println("-------------check out the created document");
        RestObject checkedOutObject = client.checkout(createdObjectWithoutContent);
        printHttpStatus();
        printRestObject(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        System.out.println("-------------cancel check out the created document");
        client.cancelCheckout(checkedOutObject);
        printHttpStatus();
        printNewLine();
        
        System.out.println("-------------check out the created document");
        createdObjectWithoutContent = client.get(createdObjectWithoutContent);
        checkedOutObject = client.checkout(createdObjectWithoutContent);
        printHttpStatus();
        printRestObject(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        System.out.println("-------------check in the created document next minor version with new name");
        RestObject newObjectWithName = new PlainRestObject("object_name", "obj_without_content_checked_in");
        RestObject checkedInObject = client.checkinNextMinor(checkedOutObject, newObjectWithName, null);
        printHttpStatus();
        printRestObject(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label");
        printNewLine();
        
        System.out.println("-------------check out the created document");
        checkedOutObject = client.checkout(createdObjectWithoutContent);
        printHttpStatus();
        printRestObject(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        System.out.println("-------------check in the created document branch version with new binary content");
        checkedInObject = client.checkinBranch(checkedOutObject, null, "new content");
        printHttpStatus();
        printRestObject(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label");
        printNewLine();
        
        System.out.println("-------------check out the checked in document");
        checkedOutObject = client.checkout(checkedInObject);
        printHttpStatus();
        printRestObject(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        System.out.println("-------------check in the created document next major version with both new anme and new binary content");
        newObjectWithName = new PlainRestObject("object_name", "new object name");
        checkedInObject = client.checkinNextMajor(checkedOutObject, newObjectWithName, "new content");
        printHttpStatus();
        printRestObject(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label");
        printNewLine();
        
        System.out.println("-------------delete the created document with all versions");
        client.delete(checkedInObject, "del-version", "all");
        printHttpStatus();
        printNewLine();
        
        System.out.println("finish Version Management sample");
        printNewLine();
    }

    /**
     * samples to execute dql query against the repository
     */
    private static void dqlQuery() {
        System.out.println("start DQL Query sample");
        
        System.out.println("-------------execute dql 'select * from dm_cabinet' with 5 items per page");
        Feed<RestObject> queryResult = client.dql("select * from dm_user", "items-per-page", "5");
        for(Entry<RestObject> e : queryResult.getEntries()) {
            RestObject o = e.getContentObject();
            printRestObject(o, "r_object_id", "user_name");
        }
        System.out.println(queryResult.getHref(LinkRelation.PAGING_NEXT));
        printNewLine();
        
        System.out.println("-------------get the next page of dql 'select * from dm_cabinet' with 5 items per page");
        queryResult = client.nextPage(queryResult);
        for(Entry<RestObject> e : queryResult.getEntries()) {
            RestObject o = e.getContentObject();
            printRestObject(o, "r_object_id", "user_name");
        }
        printNewLine();
        
        System.out.println("-------------execute dql 'select * from dm_format' with 5 items per page");
        queryResult = client.dql("select name,description from dm_format", "items-per-page", "5");
        for(Entry<RestObject> e : queryResult.getEntries()) {
            RestObject o = e.getContentObject();
            printRestObject(o, "name", "description");
        }
        printNewLine();

        System.out.println("-------------get the next page of dql 'select * from dm_format' with 5 items per page");
        queryResult = client.nextPage(queryResult);
        for(Entry<RestObject> e : queryResult.getEntries()) {
            RestObject o = e.getContentObject();
            printRestObject(o, "name", "description");
        }
        printNewLine();

        System.out.println("finish DQL Query sample");
        printNewLine();
    }

   /**
     * samples to get type and value assistance information 
     */
    private static void type() {
        System.out.println("get Type and Value Assistance sample");
        
        System.out.println("-------------get types");
        Feed<RestType> types = client.getTypes();
        printEntryContentSrc(types);
        printNewLine();
        
        System.out.println("-------------get inline types");
        types = client.getTypes("inline", "true", "items-per-page", "1");
        for(Entry<RestType> e : types.getEntries()) {
            System.out.println(e.getContentObject().getName() + " " + e.getContentObject().getCategory());
        }
        printNewLine();
        
        System.out.println("-------------get type resource");
        RestType type = client.getType("dm_document");
        List<Link> list = type.getLinks();
        for(Link l : list) {
            System.out.println(l.getRel() + " -> " + l.getHref());
        }
        System.out.println(type.getName() + " " + type.getCategory() + " " + type.getParent()
                + (type.getSharedParent() == null ? "" : type.getSharedParent()));
        for(Map<String, Object> map : type.getProperties()) {
            System.out.println(map.get("name") + ": " + map);
        }
        printNewLine();
        
        System.out.println("-------------get type value assistance of the fixed list");
        String typeWithFixedVAList = read("Please input the type name with the fixed value assistance list(no such type press 'return' directly to skip):", "");
        if(!typeWithFixedVAList.isEmpty()) {
            String attrWithFixedVAList = read("Please input the attribute name of " + typeWithFixedVAList + " with the fixed assistance list:");
            RestType fixedVAListType = client.getType(typeWithFixedVAList);
            ValueAssistant va = client.getValueAssistant(fixedVAListType, new PlainValueAssistantRequest(), "included-properties", attrWithFixedVAList);
            for(ValueAssistant.Attribute a : va.getProperties()) {
                System.out.println(a.name() + " " + (a.allowUserValues() ? "allows user values" : "disallows user values"));
                for(ValueAssistant.Value v : a.values()) {
                    System.out.println(v.value() + ", label=" + v.label());
                }
            }
        }
        printNewLine();

        System.out.println("-------------get type value assistance depencencies");
        String typeWithDenpendency = read("Please input the type name with value assistance dependencies(no such type press 'return' directly to skip):", "");
        if(!typeWithDenpendency.isEmpty()) {
            RestType dependenciesVAType = client.getType(typeWithDenpendency);
            System.out.println(dependenciesVAType.getName() + " " + dependenciesVAType.getCategory());
            for(Map<String, Object> map : dependenciesVAType.getProperties()) {
                System.out.println(map.get("name") + ": " + map.get("dependencies"));
            }
            
            System.out.println("-------------get type value assistance query values with depencencies");
            String attrWithDependencies = read("Please input the attribute name of " + typeWithDenpendency + " with the query dependencies:");
            String dependency = read("Please input the dependency name of " + typeWithDenpendency + "." + attrWithDependencies + ":");
            String dependencyValue = read("Please input the dependency value of " + dependency + ":");
            ValueAssistant va = client.getValueAssistant(dependenciesVAType, new PlainValueAssistantRequest(dependency, dependencyValue), "included-properties", attrWithDependencies);
            for(ValueAssistant.Attribute a : va.getProperties()) {
                System.out.println(a.name() + " " + (a.allowUserValues() ? "allows user values" : "disallows user values"));
                for(ValueAssistant.Value v : a.values()) {
                    System.out.println(v.value() + ", label=" + v.label());
                }
            }
        }
        printNewLine();

        System.out.println("finish Type and Value Assistance sample");
        printNewLine();
    }
    
    /**
     * samples to create/materialize/dematerialize/reparent lightweight object
     */
    private static void cmdrLightweightObject() {
        System.out.println("start Lightweight Object Create/Materialize/Dematerialize/Reparent sample");
        
        System.out.println("-------------get all shareable types");
        Feed<RestType> shareableTypes = client.getTypes("filter", "type_category=2");
        printEntryContentSrc(shareableTypes);
        printNewLine();
        
        System.out.println("-------------get all lightweight types");
        Feed<RestType> lightweightTypes = client.getTypes("filter", "type_category=4");
        printEntryContentSrc(lightweightTypes);
        printNewLine();
        
        String shareableType = read("Please input the shareable type:");
        String lightweightType = read("Please input the lightweight type:");
        
        RestObject tempCabinet = client.getCabinet("Temp");
        
        System.out.println("-------------create a shreable object under the Temp cabinet");
        RestObject newShareableObject1 = new PlainRestObject("object_name", "shareable_object_1", "r_object_type", shareableType, "title", "shared title 1");
        RestObject createdShareableObject1 = client.createObject(tempCabinet, newShareableObject1, null);
        printHttpStatus();
        printRestObject(createdShareableObject1);
        printNewLine();
        
        System.out.println("-------------create a lightweight object sharing the " + createdShareableObject1.getObjectName());
        RestObject newLightweightObject1 = new PlainRestObject("object_name", "lightweight_object_1", "r_object_type", lightweightType);
        RestObject createdLightweightObject1 = client.createObject(createdShareableObject1, LinkRelation.LIGHTWEIGHT_OBJECTS, newLightweightObject1, null);
        printHttpStatus();
        printRestObject(createdLightweightObject1);
        printNewLine();

        System.out.println("-------------materialize the lightweight object " + createdLightweightObject1.getObjectName());
        RestObject materializedObject = client.materialize(createdLightweightObject1);
        printHttpStatus();
        if(materializedObject.getHref(LinkRelation.DEMATERIALIZE) != null) {
            System.out.println(createdLightweightObject1.getObjectName() + " materialized");
        }
        printNewLine();
        
        System.out.println("-------------dematerialize the lightweight object " + createdLightweightObject1.getObjectName());
        client.dematerialize(materializedObject);
        RestObject dematerializedObject = client.get(createdLightweightObject1);
        printHttpStatus();
        if(materializedObject.getHref(LinkRelation.MATERIALIZE) != null) {
            System.out.println(createdLightweightObject1.getObjectName() + " dematerialized");
        }
        printNewLine();
        
        System.out.println("-------------create another shreable object under the Temp cabinet");
        RestObject newShareableObject2 = new PlainRestObject("object_name", "shareable_object_2", "r_object_type", shareableType, "title", "shared title 2");
        RestObject createdShareableObject2 = client.createObject(tempCabinet, newShareableObject2, null);
        printHttpStatus();
        printRestObject(createdShareableObject2);
        printNewLine();
        
        System.out.println("-------------reparent the lightweight to the new shareable object " + createdShareableObject2.getObjectName());
        RestObject reparentedObject = client.reparent(dematerializedObject, createdShareableObject2);
        printHttpStatus();
        printRestObject(reparentedObject, "title");
        printNewLine();
        
        System.out.println("-------------delete the lightweight object " + reparentedObject.getObjectName());
        client.delete(reparentedObject);
        printHttpStatus();
        
        System.out.println("-------------delete the shareable object " + createdShareableObject2.getObjectName());
        client.delete(createdShareableObject2);
        printHttpStatus();
        
        System.out.println("-------------delete the shareable object " + createdShareableObject1.getObjectName());
        client.delete(createdShareableObject1);
        printHttpStatus();
        
        System.out.println("finish Lightweight Object Create/Materialize/Dematerialize/Reparent sample");
        printNewLine();
    }
    
    /**
     * samples to get aspect types, attach and detach aspect
     */
    private static void aspect() {
        System.out.println("start AspectType/Attach/Detach sample");
        
        System.out.println("-------------get all aspect types");
        Feed<RestObject> aspectTypes = client.getAspectTypes();
        printEntryContentSrc(aspectTypes);
        printNewLine();
        
        System.out.println("-------------attach an aspect to an object");
        String aspectType = read("Please input the aspect type to be attached:");
        RestObject aspect = client.getAspectType(aspectType);
        for(Map.Entry<String, Object> entry : aspect.getProperties().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        RestObject tempCabinet = client.getCabinet("Temp");
        RestObject object = client.createObject(tempCabinet, new PlainRestObject("object_name", "obj_to_be_attached"), null);
        System.out.println(object.getObjectId() + " is created to attach " + aspectType);
        
        ObjectAspects objectAspects = client.attach(object, aspectType);
        System.out.println("attached aspects: " + objectAspects.getAspects());
        printNewLine();

        System.out.println("-------------detach an aspect from an object");
        client.detach(objectAspects, aspectType);
        printHttpStatus();

        System.out.println("-------------delete the created object " + object.getObjectId());
        client.delete(object);
        printHttpStatus();
        
        System.out.println("finish AspectType/Attach/Detach sample");
        printNewLine();
    }

    /**
     * samples to manipulate the user and group
     */
    private static void userGroup() {
        System.out.println("start User(s)/Group(s) samples");
        
        System.out.println("-------------get all users");
        Feed<RestObject> users = client.getUsers();
        printEntryContentSrc(users);
        printNewLine();

        System.out.println("-------------get a single user");
        RestObject user = client.getUser(users.getEntries().get(0).getContentSrc());
        printRestObject(user, "user_name", "user_login_name", "user_privileges", "user_address");
        printNewLine();
        
        System.out.println("-------------get all groups");
        Feed<RestObject> groups = client.getGroups();
        printEntryContentSrc(groups);
        printNewLine();

        System.out.println("-------------get a single group");
        RestObject group = client.getGroup(groups.getEntries().get(0).getContentSrc());
        printRestObject(group, "group_name", "owner_name", "group_display_name");
        printNewLine();

        String newUser = "user_" + Randoms.nextString(10);
        System.out.println("-------------create the user " + newUser);
        RestObject createdUser = client.createUser(new PlainRestObject("user_name", newUser, "user_login_name", newUser, "user_address", newUser + "@test.com"));
        printRestObject(createdUser, "user_name", "user_login_name", "user_privileges", "user_address");
        printNewLine();

        System.out.println("-------------update the user " + newUser);
        RestObject updatedUser = client.update(createdUser, new PlainRestObject("user_address", "updated " + newUser + "@test.com"));
        printRestObject(updatedUser, "user_name", "user_login_name", "user_privileges", "user_address");
        printNewLine();

        String newGroup = "group_" + Randoms.nextString(10);
        System.out.println("-------------create the group " + newGroup);
        RestObject createdGroup = client.createGroup(new PlainRestObject("group_name", newGroup));
        printRestObject(createdGroup, "group_name", "owner_name", "group_display_name");
        printNewLine();

        System.out.println("-------------update the group " + newGroup);
        RestObject updatedGroup = client.update(createdGroup, new PlainRestObject("group_display_name", "updated " + newGroup));
        printRestObject(updatedGroup, "group_name", "owner_name", "group_display_name");
        printNewLine();

        System.out.println("-------------get the group " + newGroup + " member users");
        Feed<RestObject> groupUsers = client.getUsers(updatedGroup);
        if(groupUsers.getEntries() == null) {
            System.out.println("there are 0 users in the group " + newGroup);
        }
        printNewLine();

        System.out.println("-------------add the user " + newUser + " to the group " + newGroup);
        client.addUserToGroup(updatedGroup, updatedUser);
        printHttpStatus();        
        printNewLine();
        
        System.out.println("-------------get the group " + newGroup + " member users");
        groupUsers = client.getUsers(updatedGroup);
        System.out.println("there are " + groupUsers.getEntries().size() + " users in the group " + newGroup);
        printEntryContentSrc(groupUsers);
        printNewLine();
        
        System.out.println("-------------get the user " + newUser + "'s default folder");
        RestObject defaultFolder = client.getFolder(updatedUser.getHref(LinkRelation.DEFAULT_FOLDER));
        printRestObject(defaultFolder);
        printNewLine();

        System.out.println("-------------remove the user " + newUser + " from the group " + newGroup);
        client.delete(groupUsers.getEntries().get(0));
        printHttpStatus();
        printNewLine();
        
        System.out.println("-------------delete user " + newUser);
        client.delete(updatedUser);
        printHttpStatus();        
        printNewLine();
        
        System.out.println("-------------delete group " + newGroup);
        client.delete(updatedGroup);
        printHttpStatus();        
        printNewLine();
        
        System.out.println("finish User(s)/Group(s) samples");
        printNewLine();
    }
    
    /**
     * samples to manipulate the relation
     */
    private static void relation() {
        System.out.println("start Relation samples");
        
        System.out.println("-------------get all relation types");
        Feed<RestObject> relationTypes = client.getRelationTypes();
        printEntryContentSrc(relationTypes);
        printNewLine();

        System.out.println("-------------get a single relation type");
        RestObject relationType = client.getRelationType(relationTypes.getEntries().get(0).getContentSrc());
        printRestObject(relationType, "relation_name", "parent_type", "child_type");
        printNewLine();

        System.out.println("-------------get all relations");
        Feed<RestObject> relations = client.getRelations();
        printEntryContentSrc(relations);
        printNewLine();
        
        System.out.println("-------------create a relation");
        RestObject tempCabinet = client.getCabinet("Temp");
        RestObject parent = client.createObject(tempCabinet, LinkRelation.OBJECTS, new PlainRestObject("object_name", "parent_object", "r_object_type", "dm_sysobject"), null);
        RestObject child = client.createObject(tempCabinet, LinkRelation.OBJECTS, new PlainRestObject("object_name", "child_object", "r_object_type", "dm_sysobject"), null);
        RestObject createdRelation = client.createRelation(new PlainRestObject("relation_name", "peer", "parent_id", parent.getObjectId(), "child_id", child.getObjectId()));
        printRestObject(createdRelation, "relation_name", "parent_id", "child_id", "r_object_id");
        printNewLine();
        
        System.out.println("-------------delete a relation");
        client.delete(createdRelation.getHref(LinkRelation.SELF));
        printHttpStatus();
        printNewLine();
        
        client.delete(parent);
        client.delete(child);
        
        System.out.println("finish Relation samples");
        printNewLine();
    }
    
    /**
     * samples to get the format
     */
    private static void format() {
        System.out.println("start Format samples");
        
        System.out.println("-------------get all formats");
        Feed<RestObject> formats = client.getFormats();
        printEntryContentSrc(formats);
        printNewLine();

        System.out.println("-------------get a single format");
        RestObject format = client.getFormat(formats.getEntries().get(0).getContentSrc());
        printRestObject(format, "name", "description", "dos_extension");
        printNewLine();
        
        System.out.println("finish Format samples");
        printNewLine();
    }
    
    /**
     * samples to get the network location
     */
    private static void networkLocation() {
        System.out.println("start Network Location samples");
        
        System.out.println("-------------get all network locations");
        Feed<RestObject> networkLocations = client.getNetworkLocations();
        printEntryContentSrc(networkLocations);
        printNewLine();

        if(networkLocations.getEntries().size() > 0) {
            System.out.println("-------------get a single network location");
            RestObject networkLocation = client.getNetworkLocation(networkLocations.getEntries().get(0).getContentSrc());
            printRestObject(networkLocation, "object_name", "r_object_type", "netloc_ident", "netloc_display_name", "begin_near_ip_address", "end_near_ip_address");
            printNewLine();
        }
        
        System.out.println("finish Network Location samples");
        printNewLine();
    }
    
    private static void printEntryContentSrc(Feed<?> feed) {
        for(Entry<?> e : feed.getEntries()) {
            System.out.println(e.getTitle() + " -> " + e.getContentSrc());
        }
    }
    
    private static void printRestObject(RestObject object) {
        printRestObject(object, "r_object_id", "object_name", "r_object_type");
    }
    
    private static void printRestObject(RestObject object, String... properties) {
        StringBuilder sb = new StringBuilder();
        for(String p : properties) {
            if(sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(p.replaceFirst("^r_", "").replaceAll("_", " ")).append(':').append(object.getProperties().get(p));
        }
        System.out.println(sb);
    }
    
    private static void printHttpStatus() {
        System.out.println("http status: " + client.getStatus());
    }
    
    private static void printNewLine() {
        System.out.println(NEWLINE);
    }
    
    private static void printLinkRel(Linkable linkable, LinkRelation... rels) {
        for(LinkRelation rel : rels) {
            System.out.println(rel.rel() + " -> " + linkable.getHref(LinkRelation.SELF));
        }
    }
}
