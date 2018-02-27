/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.emc.documentum.rest.client.sample.client.DCTMRestErrorException;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.Lifecycle;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.emc.documentum.rest.client.sample.model.ObjectLifecycle;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainObjectAspect;
import com.emc.documentum.rest.client.sample.model.plain.PlainObjectLifecycle;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Collections.isNotEmpty;
import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;

@RestServiceSample("Document(s) CRUD")
public class DocumentCRUDSample extends Sample {
    public void crudDocument() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create a document without binary content under the Temp cabinet");
        RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
        RestObject createdObjectWithoutContent = client.createDocument(tempCabinet, newObjectWithoutContent);
        printHttpStatus();
        print(createdObjectWithoutContent);
        printNewLine();
        
        printStep("update the document with a new name");
        RestObject updateObjectName = new PlainRestObject("object_name", "new_object_name");
        RestObject updatedObjectWithObjectName = client.update(createdObjectWithoutContent, updateObjectName);
        printHttpStatus();
        print(updatedObjectWithObjectName);
        printNewLine();
        
        printStep("delete the created document");
        client.delete(updatedObjectWithObjectName);
        printHttpStatus();
        printNewLine();

        printStep("create a document with binary content under the Temp cabinet");
        RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
        RestObject createdObjectWithContent = client.createDocument(tempCabinet, newObjectWithContent, "I'm the content of the object", "text/plain", "format", "crtext");
        printHttpStatus();
        print(createdObjectWithContent);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdObjectWithContent);
        printHttpStatus();        
        printNewLine();
    }
    
    @RestServiceVersion(7.3)
    public void importDocumentWithContents() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("import a document with binary contents under the Temp cabinet");
        printStep("and all contents are saved as primary content");
        RestObject newDocWithContent = new PlainRestObject("object_name", "doc_with_contents");
        RestObject createdDocWithContent = client.createDocument(tempCabinet, newDocWithContent,
                Arrays.asList((Object)"I'm the first content of the object", (Object)"I'm the second content of the object", (Object)"I'm the third content of the object", (Object)"I'm the fourth content of the object"),
                Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain"),
                "format", "crtext", "content-count", "4");
        printHttpStatus();
        print(createdDocWithContent);
        Feed<RestObject> contents = client.getContents(createdDocWithContent);
        printEntryContentSrc(contents);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdDocWithContent);
        printHttpStatus();        
        printNewLine();

        printStep("import a document with binary contents under the Temp cabinet");
        printStep("and the first content is saved as primary content, all other contents are saved as renditions");
        RestObject newDocWithRendition = new PlainRestObject("object_name", "doc_with_renditions");
        RestObject createdDocWithRendition = client.createDocument(tempCabinet, newDocWithRendition,
                Arrays.asList((Object)"I'm the first content of the object", (Object)"I'm the second content of the object", (Object)"I'm the third content of the object", (Object)"I'm the fourth content of the object"),
                Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain"),
                "all-primary", "false", "format", "crtext,html,pub_html,crtext", "modifier", ",,m2,m3", "content-count", "4");
        printHttpStatus();
        print(createdDocWithRendition);
        contents = client.getContents(createdDocWithRendition);
        printEntryContentSrc(contents);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdDocWithRendition);
        printHttpStatus();        
        printNewLine();
    }
    
    @RestServiceVersion(16.4)
    public void createWithLifecycleAndAspects() {
        printStep("create a document with the aspects and the lifecycle the Temp cabinet");

        ObjectAspects objectAspects = null;
        String aspectType = read("Please input the aspect type to be attached (return to skip):", "");
        if(!StringUtils.isEmpty(aspectType)) {
            objectAspects = new PlainObjectAspect(aspectType);
        }
        ObjectLifecycle objectLifecycle = null;
        String lifecycleName = read("Please input the lifecycle name to be attached (return to skip):", "");
        if(!StringUtils.isEmpty(lifecycleName)) {
            Feed<Lifecycle> lifecycles = client.getLifecycles("inline", "true", "filter", "object_name='" + lifecycleName + "'");
            if(isNotEmpty(lifecycles.getEntries())) {
                objectLifecycle = new PlainObjectLifecycle(lifecycles.getEntries().get(0).getContentObject().getId());
            }
        }

        if(objectLifecycle != null || objectAspects != null) {
            RestObject tempCabinet = client.getCabinet("Temp");
            PlainRestObject objectToBeCreated = new PlainRestObject("object_name", "obj_with_aspects_lifecycle");
            objectToBeCreated.setObjectAspects(objectAspects);
            objectToBeCreated.setObjectLifecycle(objectLifecycle);
            RestObject object = client.createDocument(tempCabinet, objectToBeCreated);
            printHttpStatus();
            if(objectAspects != null) {
                ObjectAspects attachedAspects = client.getObjectAspects(object);
                System.out.println("attached aspects: " + attachedAspects.getAspects());
            }
            if(objectLifecycle != null) {
                ObjectLifecycle attachedLifecycle = client.getObjectLifecycle(object);
                print(attachedLifecycle);
            }
            printNewLine();
            printStep("delete the created object");
            client.delete(object);
            printHttpStatus();        
            printNewLine();
        }
    }
    
    @RestServiceVersion(16.4)
    public void etag() {
        printStep("Operate the document with etag");

        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create a document under the Temp cabinet");
        RestObject newObject = new PlainRestObject("object_name", "new doc");
        RestObject createdObject = client.createDocument(tempCabinet, newObject);
        printHttpStatus();
        printNewLine();
        
        printStep("get the document");
        RestObject object = client.get(createdObject);
        printHttpStatus();
        String etag = client.getHeaders().getETag();
        System.out.println("the etag is " + etag);
        printNewLine();
        
        printStep("get the document with if-none-match header");
        client.ifNoneMatch(etag);
        client.get(object);
        printHttpStatus();
        printNewLine();
        
        printStep("update the document with if-match header");
        RestObject updateObjectName = new PlainRestObject("object_name", "new_object_name");
        client.ifMatch(etag);
        RestObject updatedObjectWithObjectName = client.update(createdObject, updateObjectName);
        printHttpStatus();
        String newetag = client.getHeaders().getETag();
        System.out.println("the new etag is " + newetag);
        printNewLine();
        
        printStep("update the document with the old header");
        updateObjectName = new PlainRestObject("object_name", "new_object_name");
        client.ifMatch(etag);
        try {
            client.update(createdObject, updateObjectName);
        } catch(DCTMRestErrorException e) {
            printHttpStatus();
        }
        printNewLine();
        
        printStep("delete the created document");
        client.delete(updatedObjectWithObjectName);
        printHttpStatus();
        printNewLine();
    }
}
