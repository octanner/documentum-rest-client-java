/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.AuditTrail;
import com.emc.documentum.rest.client.sample.model.AuditTrail.AuditData;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;

import static com.emc.documentum.rest.client.sample.client.util.Collections.isNotEmpty;
import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Audit Trail(s)")
@RestServiceVersion(16.4)
public class AuditTrailSample extends Sample {
    public void auditTrail() {
        printStep("get the audit trails");
        Feed<RestObject> auditTrails = client.getRecentAuditTrails("items-per-page", "10");
        printEntryContentSrc(auditTrails);
        printNewLine();

        if(isNotEmpty(auditTrails. getEntries())) {
            printStep("get the single audit trail");
            AuditTrail auditTrail = client.getAuditTrail(auditTrails.getEntries().get(0).getContentSrc());
            print(auditTrail, "user_name", "event_name", "audited_obj_id", "time_stamp", "object_name", "object_type", "event_description");
            if(isNotEmpty(auditTrail.getAuditDataList())) {
                for(AuditData data : auditTrail.getAuditDataList()) {
                    System.out.println(data.getAttrName() + " " + data.getValuePairs().get(0).getOldValue() + " to " + data.getValuePairs().get(0).getNewValue());
                }
            }
            printNewLine();
        }
    }
}
