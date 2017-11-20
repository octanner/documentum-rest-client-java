/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Map;

import org.springframework.util.StringUtils;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.ObjectAspects;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;

@RestServiceSample("AspectType and Aspect Attach/Detach")
@RestServiceVersion(7.3)
public class AspectSample extends Sample {
    public void aspect() {
        printStep("get all aspect types");
        Feed<RestObject> aspectTypes = client.getAspectTypes();
        printEntryContentSrc(aspectTypes);
        printNewLine();
        
        printStep("attach an aspect to an object");
        String aspectType = read("Please input the aspect type to be attached (return to skip):", "");
        if(!StringUtils.isEmpty(aspectType)) {
            RestObject aspect = client.getAspectType(aspectType);
            for(Map.Entry<String, Object> entry : aspect.getProperties().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            
            RestObject tempCabinet = client.getCabinet("Temp");
            RestObject object = client.createObject(tempCabinet, new PlainRestObject("object_name", "obj_to_be_attached"));
            System.out.println(object.getObjectId() + " is created to attach " + aspectType);
            
            ObjectAspects objectAspects = client.attach(object, aspectType);
            System.out.println("attached aspects: " + objectAspects.getAspects());
            printNewLine();
    
            printStep("detach an aspect from an object");
            client.detach(objectAspects, aspectType);
            printHttpStatus();
    
            printStep("delete the created object " + object.getObjectId());
            client.delete(object);
            printHttpStatus();
        }
    }
}
