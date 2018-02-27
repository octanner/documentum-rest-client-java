/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntry;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Subscription(s)")
@RestServiceVersion(16.4)
public class SubscriptionSample extends Sample {
    public void subscription() {
        RestObject tempCabinet = client.getCabinet("Temp");

        printStep("create an object under the Temp cabinet");
        RestObject newObject = new PlainRestObject("object_name", "new doc");
        RestObject createdObject = client.createObject(tempCabinet, newObject);
        printHttpStatus();
        printNewLine();
        
        printStep("subscribe the object for the current user");
        RestObject object = client.subscribe(createdObject);
        printHttpStatus();
        printNewLine();
        
        printStep("get the subscribed objects");
        Feed<RestObject> subscribedObjects = client.getSubscriptions();
        printEntry(subscribedObjects, "id", "title", "summary");
        printNewLine();
        
        printStep("unsubscribe the object");
        client.unsubscribe(object);
        printHttpStatus();
        printNewLine();
        
        printStep("delete the created object");
        client.delete(object);
        printHttpStatus();
        printNewLine();
    }
}
