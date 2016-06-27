/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Version Management")
public class VersionManagementSample extends Sample {
    public void versionManagement() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create a document without binary content under the Temp cabinet");
        RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
        RestObject createdObjectWithoutContent = client.createDocument(tempCabinet, newObjectWithoutContent);
        printHttpStatus();
        print(createdObjectWithoutContent, "r_object_id", "r_lock_owner");
        printNewLine();
        
        printStep("check out the created document");
        RestObject checkedOutObject = client.checkout(createdObjectWithoutContent);
        printHttpStatus();
        print(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        printStep("cancel check out the created document");
        client.cancelCheckout(checkedOutObject);
        printHttpStatus();
        printNewLine();
        
        printStep("check out the created document");
        createdObjectWithoutContent = client.get(createdObjectWithoutContent);
        checkedOutObject = client.checkout(createdObjectWithoutContent);
        printHttpStatus();
        print(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        printStep("check in the created document next minor version with new name");
        RestObject newObjectWithName = new PlainRestObject("object_name", "obj_without_content_checked_in");
        RestObject checkedInObject = client.checkinNextMinor(checkedOutObject, newObjectWithName, null, null);
        printHttpStatus();
        print(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label");
        printNewLine();
        
        printStep("check out the created document");
        checkedOutObject = client.checkout(createdObjectWithoutContent);
        printHttpStatus();
        print(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        printStep("check in the created document branch version with new binary content");
        checkedInObject = client.checkinBranch(checkedOutObject, null, "new content", null);
        printHttpStatus();
        print(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label");
        printNewLine();
        
        printStep("check out the checked in document");
        checkedOutObject = client.checkout(checkedInObject);
        printHttpStatus();
        print(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        printStep("check in the created document next major version with both new anme and new binary content");
        newObjectWithName = new PlainRestObject("object_name", "new object name");
        checkedInObject = client.checkinNextMajor(checkedOutObject, newObjectWithName, "new content", "text/plain");
        printHttpStatus();
        print(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label");
        printNewLine();
        
        printStep("delete the created document with all versions");
        client.delete(checkedInObject, "del-version", "all");
        printHttpStatus();
        printNewLine();
        
        System.out.println("finish Version Management sample");
        printNewLine();
    }
}
