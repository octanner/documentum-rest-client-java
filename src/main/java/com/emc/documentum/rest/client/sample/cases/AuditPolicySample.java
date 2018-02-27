/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Collections;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.AuditPolicy;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.plain.PlainAuditPolicy;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Audit Policy(s)")
@RestServiceVersion(16.4)
public class AuditPolicySample extends Sample {
    public void auditPolicy() {
        printStep("Please Note: only a docbase install owner can perform CREATE/UPDATE operation on audit policy");
        printStep("create an audit policy");
        PlainAuditPolicy auditPolicy = new PlainAuditPolicy();
        auditPolicy.setName("my_audit_policy");
        auditPolicy.setAccessorName("accessor_x");
        auditPolicy.setGroup(true);
        auditPolicy.setAttributeRules(Collections.singletonMap("object-type", "dm_document"));
        AuditPolicy created = client.createAuditPolicy(auditPolicy);
        print(created);
        
        printStep("get the audit policies");
        Feed<AuditPolicy> auditPolicies = client.getAuditPolicies();
        printEntryContentSrc(auditPolicies);
        printNewLine();

        printStep("update the audit policy");
        auditPolicy = new PlainAuditPolicy();
        auditPolicy.setAccessorName("accessor_xxx");
        AuditPolicy updated = client.updateAuditPolicy(created, auditPolicy);
        print(updated);
        printNewLine();
        
        printStep("get the inline audit policies");
        auditPolicies = client.getAuditPolicies("inline", "true", "filter", "name='my_audit_policy'");
        if(!auditPolicies.getEntries().isEmpty()) {
            updated = auditPolicies.getEntries().get(0).getContentObject();
            print(updated);
        }
        printNewLine();
        
        printStep("delete the created audit policy");
        client.delete(updated);
        printHttpStatus();
        printNewLine();
    }
}
