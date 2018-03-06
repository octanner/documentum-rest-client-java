/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

@RestServiceSample("Sysobject(s) CRUD")
public class ObjectCRUDSample extends Sample {
    public void crudSysobject() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create an object without binary content under the Temp cabinet");
        RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
        RestObject createdObjectWithoutContent = client.createObject(tempCabinet, newObjectWithoutContent);
        printHttpStatus();
        print(createdObjectWithoutContent);
        printNewLine();
        
        printStep("update the object with a new name");
        RestObject updateObjectName = new PlainRestObject("object_name", "new_object_name");
        RestObject updatedObjectWithObjectName = client.update(createdObjectWithoutContent, updateObjectName);
        printHttpStatus();
        print(updatedObjectWithObjectName);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(updatedObjectWithObjectName);
        printHttpStatus();
        printNewLine();
        
        printStep("create an object with repeating properties and specify dm_docment type under the Temp cabinet");
        Map<String, Object> newPropertiesMap = new HashMap<String, Object>();
        newPropertiesMap.put("r_object_type", "dm_document");
        newPropertiesMap.put("object_name", "obj_with_repeating_properties");
        newPropertiesMap.put("keywords", Arrays.asList("objects", "repeating", "properties"));
        RestObject newObjectWithRepeatingProperties = new PlainRestObject(newPropertiesMap);
        RestObject createdObjectWithRepeatingProperties = client.createObject(tempCabinet, newObjectWithRepeatingProperties);
        printHttpStatus();
        print(createdObjectWithRepeatingProperties, "r_object_id", "object_name", "r_object_type", "keywords");
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdObjectWithRepeatingProperties);
        printHttpStatus();
        printNewLine();

        printStep("create an object with specify object type in another place to override the r_object_type");
        RestObject newObjectWithOverwriteType = new PlainRestObject("dm_sysobject", newPropertiesMap);
        RestObject createdObjectWithOverwriteType = client.createObject(tempCabinet, newObjectWithOverwriteType);
        printHttpStatus();
        print(createdObjectWithOverwriteType);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdObjectWithOverwriteType);
        printHttpStatus();
        printNewLine();

        printStep("create an object with binary content under the Temp cabinet");
        RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
        RestObject createdObjectWithContent = client.createObject(tempCabinet, newObjectWithContent, (Object)"I'm the content of the object", "text/plain", "format", "crtext");
        printHttpStatus();
        print(createdObjectWithContent);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdObjectWithContent);
        printHttpStatus();        
        printNewLine();
    }
    
    @RestServiceVersion(7.3)
    public void importObjectWithContents() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("import an object with binary contents under the Temp cabinet");
        printStep("and all contents are saved as primary content");
        RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_contents");
        RestObject createdObjectWithContent = client.createObject(tempCabinet, newObjectWithContent,
                Arrays.asList((Object)"I'm the first content of the object", (Object)"I'm the second content of the object", (Object)"I'm the third content of the object", (Object)"I'm the fourth content of the object"),
                Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain"),
                "format", "crtext", "content-count", "4");
        printHttpStatus();
        print(createdObjectWithContent);
        Feed<RestObject> contents = client.getContents(createdObjectWithContent);
        printEntryContentSrc(contents);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdObjectWithContent);
        printHttpStatus();        
        printNewLine();

        printStep("import an object with binary contents under the Temp cabinet");
        printStep("and the first content is saved as primary content, all other contents are saved as renditions");
        RestObject newObjectWithRendition = new PlainRestObject("object_name", "obj_with_renditions");
        RestObject createdObjectWithRendition = client.createObject(tempCabinet, newObjectWithRendition,
                Arrays.asList((Object)"I'm the first content of the object", (Object)"I'm the second content of the object", (Object)"I'm the third content of the object", (Object)"I'm the fourth content of the object"),
                Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain"),
                "all-primary", "false", "format", "crtext,html,pub_html,crtext", "modifier", ",,m2,m3", "content-count", "4");
        printHttpStatus();
        print(createdObjectWithRendition);
        contents = client.getContents(createdObjectWithRendition);
        printEntryContentSrc(contents);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdObjectWithRendition);
        printHttpStatus();        
        printNewLine();
    }
    
    @RestServiceVersion(16.4)
    public void createWithLifecycleAndAspects() {
        printStep("create an object with the aspects and the lifecycle the Temp cabinet");

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
            RestObject object = client.createObject(tempCabinet, objectToBeCreated);
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
        printStep("Operate the object with etag");

        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create an object under the Temp cabinet");
        RestObject newObject = new PlainRestObject("object_name", "new doc");
        RestObject createdObject = client.createObject(tempCabinet, newObject);
        printHttpStatus();
        printNewLine();
        
        printStep("get the object");
        RestObject object = client.get(createdObject);
        printHttpStatus();
        String etag = client.getHeaders().getETag();
        System.out.println("the etag is " + etag);
        printNewLine();
        
        printStep("get the object with if-none-match header");
        client.ifNoneMatch(etag);
        client.get(object);
        printHttpStatus();
        printNewLine();
        
        printStep("update the object with if-match header");
        RestObject updateObjectName = new PlainRestObject("object_name", "new_object_name");
        client.ifMatch(etag);
        RestObject updatedObjectWithObjectName = client.update(createdObject, updateObjectName);
        printHttpStatus();
        String newetag = client.getHeaders().getETag();
        System.out.println("the new etag is " + newetag);
        printNewLine();
        
        printStep("update the object with the old header");
        updateObjectName = new PlainRestObject("object_name", "new_object_name");
        client.ifMatch(etag);
        try {
            client.update(createdObject, updateObjectName);
        } catch(DCTMRestErrorException e) {
            printHttpStatus();
        }
        printNewLine();
        
        printStep("delete the created object");
        client.delete(updatedObjectWithObjectName);
        printHttpStatus();
        printNewLine();
    }
}
