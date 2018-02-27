/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import org.springframework.util.StringUtils;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.DEMATERIALIZE;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.LIGHTWEIGHT_OBJECTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.MATERIALIZE;

@RestServiceSample("Lightweight Object Create/Materialize/Dematerialize/Reparent")
@RestServiceVersion(7.3)
public class LightweightObjectSample extends Sample {
    public void cmdrLightweightObject() {
        printStep("get all shareable types");
        Feed<RestType> shareableTypes = client.getTypes("filter", "type_category=2");
        printEntryContentSrc(shareableTypes);
        printNewLine();
        
        printStep("get all lightweight types");
        Feed<RestType> lightweightTypes = client.getTypes("filter", "type_category=4");
        printEntryContentSrc(lightweightTypes);
        printNewLine();
        
        String shareableType = read("Please input the shareable type (return to skip):", "");
        String lightweightType = read("Please input the lightweight type (return to skip):", "");
        if(!StringUtils.isEmpty(shareableType) && !StringUtils.isEmpty(lightweightType)) {
            RestObject tempCabinet = client.getCabinet("Temp");
            
            printStep("create a shreable object under the Temp cabinet");
            RestObject newShareableObject1 = new PlainRestObject("object_name", "shareable_object_1", "r_object_type", shareableType, "title", "shared title 1");
            RestObject createdShareableObject1 = client.createObject(tempCabinet, newShareableObject1);
            printHttpStatus();
            print(createdShareableObject1);
            printNewLine();
            
            printStep("create a lightweight object sharing the " + createdShareableObject1.getObjectName());
            RestObject newLightweightObject1 = new PlainRestObject("object_name", "lightweight_object_1", "r_object_type", lightweightType);
            RestObject createdLightweightObject1 = client.createObject(createdShareableObject1, LIGHTWEIGHT_OBJECTS, newLightweightObject1);
            printHttpStatus();
            print(createdLightweightObject1);
            printNewLine();
    
            printStep("materialize the lightweight object " + createdLightweightObject1.getObjectName());
            RestObject materializedObject = client.materialize(createdLightweightObject1);
            printHttpStatus();
            if(materializedObject.getHref(DEMATERIALIZE) != null) {
                System.out.println(createdLightweightObject1.getObjectName() + " materialized");
            }
            printNewLine();
            
            printStep("dematerialize the lightweight object " + createdLightweightObject1.getObjectName());
            client.dematerialize(materializedObject);
            RestObject dematerializedObject = client.get(createdLightweightObject1);
            printHttpStatus();
            if(materializedObject.getHref(MATERIALIZE) != null) {
                System.out.println(createdLightweightObject1.getObjectName() + " dematerialized");
            }
            printNewLine();
            
            printStep("create another shreable object under the Temp cabinet");
            RestObject newShareableObject2 = new PlainRestObject("object_name", "shareable_object_2", "r_object_type", shareableType, "title", "shared title 2");
            RestObject createdShareableObject2 = client.createObject(tempCabinet, newShareableObject2);
            printHttpStatus();
            print(createdShareableObject2);
            printNewLine();
            
            printStep("reparent the lightweight to the new shareable object " + createdShareableObject2.getObjectName());
            RestObject reparentedObject = client.reparent(dematerializedObject, createdShareableObject2);
            printHttpStatus();
            print(reparentedObject, "title");
            printNewLine();
            
            printStep("delete the lightweight object " + reparentedObject.getObjectName());
            client.delete(reparentedObject);
            printHttpStatus();
            
            printStep("delete the shareable object " + createdShareableObject2.getObjectName());
            client.delete(createdShareableObject2);
            printHttpStatus();
            
            printStep("delete the shareable object " + createdShareableObject1.getObjectName());
            client.delete(createdShareableObject1);
            printHttpStatus();
        }
    }
}
