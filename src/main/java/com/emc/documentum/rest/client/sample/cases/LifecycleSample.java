/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import org.springframework.util.StringUtils;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.Lifecycle;
import com.emc.documentum.rest.client.sample.model.Lifecycle.LifecycleState;
import com.emc.documentum.rest.client.sample.model.ObjectLifecycle;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainObjectLifecycle;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Collections.isNotEmpty;
import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printFields;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;

@RestServiceSample("Lifecycle(s)")
@RestServiceVersion(16.4)
public class LifecycleSample extends Sample {
    public void lifecycle() {
        printStep("get all lifecycles");
        Feed<Lifecycle> lifecycles = client.getLifecycles();
        printEntryContentSrc(lifecycles);
        printNewLine();

        if(isNotEmpty(lifecycles.getEntries())) {
            printStep("get a single lifecycle");
            Lifecycle lifecycle = client.getLifecycle(lifecycles.getEntries().get(0).getContentSrc());
            printFields(lifecycle, "id", "name", "title", "subject", "keywords", "owner", "created", "modified", "implementationType", "versionLabels",
                    "aliasSets", "status", "statusMessage");
            printFields(lifecycle.getTypeInclusions(), "type", "includeSubtypes");
            if(lifecycle.getStates() != null) {
                for(LifecycleState state : lifecycle.getStates()) {
                    printFields(state, "name", "type", "description", "exceptional", "exceptionState", "allowAttach", "allowSchedule", "allowReturnToBase",
                            "allowDemote", "no", "index", "returnConditions", "typeOverrideId");
                    printFields(state.getEntryCriteria(), "id", "expression");
                    printFields(state.getUserCriteria(), "id", "name", "version");
                    printFields(state.getAction(), "id", "name", "version");
                    printFields(state.getUserAction(), "id", "name", "version");
                    printFields(state.getUserPostAction(), "id", "name", "version");
                    printFields(state.getUserCriteriaService(), "id", "name", "primaryClass");
                    printFields(state.getUserActionService(), "id", "name", "primaryClass");
                    printFields(state.getUserPostService(), "id", "name", "primaryClass");
                    printFields(state.getSystemAction(), "id", "name", "primaryClass");
                }
            }
            printNewLine();
            
            printStep("create a document under the Temp cabinet");
            RestObject tempCabinet = client.getCabinet("Temp");
            RestObject newDoc = new PlainRestObject("object_name", "new object");
            RestObject createdDoc = client.createDocument(tempCabinet, newDoc);
            printHttpStatus();
            printNewLine();
            
            String lifecycleName = read("Please input the lifecycle name to be attached to the document (return to skip):", "");
            if(!StringUtils.isEmpty(lifecycleName)) {
                lifecycles = client.getLifecycles("inline", "true", "filter", "object_name='" + lifecycleName + "'");
                if(isNotEmpty(lifecycles.getEntries())) {
                    printStep("attach " + lifecycleName + " to the object " + createdDoc.getObjectId());
                    ObjectLifecycle lifecycleToBeAttached = new PlainObjectLifecycle(lifecycles.getEntries().get(0).getContentObject().getId());
                    ObjectLifecycle objectLifecycle = client.attach(createdDoc, lifecycleToBeAttached);
                    print(objectLifecycle);
                    printNewLine();
                    
                    if(objectLifecycle.isAbleToPromote()) {
                        printStep("test whether the lifecycle can be promoted");
                        try {
                            objectLifecycle = client.promote(objectLifecycle, "trial", "true");
                        } catch(Exception e) {
                        }
                        print(objectLifecycle);
                        printNewLine();
                        
                        printStep("promote the lifecycle to the next state");
                        objectLifecycle = client.promote(objectLifecycle, "force", "true");
                        print(objectLifecycle);
                        printNewLine();
                        
                        printStep("demote the lifecycle to the previous state");
                        objectLifecycle = client.demote(objectLifecycle);
                        print(objectLifecycle);
                        printNewLine();
                    }
                    
                    while(objectLifecycle.isAbleToPromote()) {
                        printStep("promote the lifecycle");
                        objectLifecycle = client.promote(objectLifecycle, "force", "true");
                        print(objectLifecycle);
                        printNewLine();
                        
                        if(objectLifecycle.isAbleToSuspend()) {
                            printStep("suspend the lifecycle");
                            objectLifecycle = client.suspend(objectLifecycle, "force", "true");
                            print(objectLifecycle);
                            printNewLine();
                            
                            printStep("resume the lifecycle");
                            objectLifecycle = client.resume(objectLifecycle, "force", "true");
                            print(objectLifecycle);
                            printNewLine();
                        }
                    }
                } else {
                    System.out.println("the lifecycle named " + lifecycleName + " does not exist");
                }
            }
            printStep("delete the created document");
            client.delete(createdDoc);
            printHttpStatus();
            printNewLine();
        } else {
            System.out.println("create more Lifecycles to continue the following samples");
        }
    }
}
