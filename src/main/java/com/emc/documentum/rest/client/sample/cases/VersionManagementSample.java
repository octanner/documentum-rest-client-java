/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Arrays;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
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
        RestObject checkedInObject = client.checkinNextMinor(checkedOutObject, newObjectWithName);
        printHttpStatus();
        print(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label");
        printNewLine();
        
        printStep("check out the created document");
        checkedOutObject = client.checkout(createdObjectWithoutContent);
        printHttpStatus();
        print(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        printStep("check in the created document branch version with new binary content");
        checkedInObject = client.checkinBranch(checkedOutObject, null, (Object)"new content", null);
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
        checkedInObject = client.checkinNextMajor(checkedOutObject, newObjectWithName, (Object)"new content", "text/plain");
        printHttpStatus();
        print(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label");
        printNewLine();
        
        printStep("delete the created document with all versions");
        client.delete(checkedInObject, "del-version", "all");
        printHttpStatus();
        printNewLine();
    }
    
    @RestServiceVersion(7.3)
    public void checkInWithContents() {
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
        
        printStep("check in the document next minor version with new name and contents");
        RestObject newObjectWithName = new PlainRestObject("object_name", "obj_without_content_checked_in");
        RestObject checkedInObject = client.checkinNextMinor(checkedOutObject, newObjectWithName,
            Arrays.asList((Object)"I'm the first content of the object", (Object)"I'm the second content of the object", (Object)"I'm the third content of the object", (Object)"I'm the fourth content of the object"),
            Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain"),
            "format", "crtext", "content-count", "4");
        printHttpStatus();
        print(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label", "r_page_cnt");
        Feed<RestObject> contents = client.getContents(checkedInObject);
        printEntryContentSrc(contents);
        printNewLine();
        
        printStep("check out the created document");
        checkedOutObject = client.checkout(checkedInObject);
        printHttpStatus();
        print(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        printStep("check in the document next major version with new contents at page 4");
        checkedInObject = client.checkinNextMajor(checkedOutObject, null,
            Arrays.asList((Object)"I'm the first content of the object", (Object)"I'm the second content of the object", (Object)"I'm the third content of the object", (Object)"I'm the fourth content of the object"),
            Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain"),
            "content-count", "4", "page", "4");
        printHttpStatus();
        print(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label", "r_page_cnt");
        contents = client.getContents(checkedInObject);
        printEntryContentSrc(contents);
        printNewLine();
        
        printStep("check out the checked in document");
        checkedOutObject = client.checkout(checkedInObject);
        printHttpStatus();
        print(checkedOutObject, "r_object_id", "r_lock_owner");
        printNewLine();
        
        printStep("check in the document next minor version with new contents at page 1 with renditions");
        checkedInObject = client.checkinNextMinor(checkedOutObject, null,
            Arrays.asList((Object)"I'm the first NEW content of the object", (Object)"I'm the second NEW content of the object", (Object)"I'm the third NEW content of the object", (Object)"I'm the fourth NEW content of the object"),
            Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain"),
            "page", "1", "content-count", "4", "all-primary", "false", "format", "crtext,html,crtext,html", "modifier", ",,modifer3,modifier4");
        printHttpStatus();
        print(checkedInObject, "r_object_id", "r_lock_owner", "r_version_label", "r_page_cnt");
        contents = client.getContents(checkedInObject);
        printEntryContentSrc(contents);
        printNewLine();
        
        printStep("delete the created document with all versions");
        client.delete(checkedInObject, "del-version", "all");
        printHttpStatus();
        printNewLine();
    }
}
