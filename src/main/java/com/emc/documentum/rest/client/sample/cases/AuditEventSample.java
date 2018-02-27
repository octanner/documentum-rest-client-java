/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Collections;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.AvailableAuditEvents;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.EDIT;

@RestServiceSample("Audit Event(s)")
@RestServiceVersion(16.4)
public class AuditEventSample extends Sample {
    public void availableAuditEvents() {
        printStep("Please Note: only the user who has CONFIG privilege can manipulate available audit events");
        printStep("get available audit events");
        AvailableAuditEvents events = client.getAvailableAuditEvents();
        if(events != null) {
            System.out.println(events.getEvents());
        }
        printNewLine();
    }
    
    public void registeredAuditEvent() {
        printStep("Please Note: only the user who has CONFIG privilege can manipulate registered audit events");

        printStep("get registered audit events");
        Feed<RestObject> events = client.getRegisteredAuditEvents();
        if(events.getEntries() != null) {
            for(Entry<?> e : events.getEntries()) {
                System.out.println(e.getTitle() + " -> " + e.getHref(EDIT));
            }
        }
        printNewLine();
        
        printStep("register an audit event to the type dm_document");
        RestObject event = client.registerAuditEvent(new PlainRestObject(Collections.singletonMap("event", (Object)"dm_destroy")), "audit-scope", "type", "object-type", "dm_document");
        printHttpStatus();
        print(event, "event", "registered_id", "audit_subtypes", "user_name");
        printNewLine();
        
        printStep("get the single registered audit event");
        event = client.getRegisteredAuditEvent(event.self());
        print(event, "event", "registered_id", "audit_subtypes", "user_name");
        printNewLine();
        
        printStep("unregister the audit event");
        client.unregisterAuditEvent(event);
        printHttpStatus();
        printNewLine();
    }
}
