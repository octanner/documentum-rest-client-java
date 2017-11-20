/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
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

@RestServiceSample("Document(s) CRUD")
public class DocumentCRUDSample extends Sample {
    public void crudDocument() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create a document without binary content under the Temp cabinet");
        RestObject newObjectWithoutContent = new PlainRestObject("object_name", "obj_without_content");
        RestObject createdObjectWithoutContent = client.createDocument(tempCabinet, newObjectWithoutContent);
        printHttpStatus();
        print(createdObjectWithoutContent);
        printNewLine();
        
        printStep("update the document with a new name");
        RestObject updateObjectName = new PlainRestObject("object_name", "new_object_name");
        RestObject updatedObjectWithObjectName = client.update(createdObjectWithoutContent, updateObjectName);
        printHttpStatus();
        print(updatedObjectWithObjectName);
        printNewLine();
        
        printStep("delete the created document");
        client.delete(updatedObjectWithObjectName);
        printHttpStatus();
        printNewLine();

        printStep("create a document with binary content under the Temp cabinet");
        RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
        RestObject createdObjectWithContent = client.createDocument(tempCabinet, newObjectWithContent, "I'm the content of the object", "text/plain", "format", "crtext");
        printHttpStatus();
        print(createdObjectWithContent);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdObjectWithContent);
        printHttpStatus();        
        printNewLine();
    }
    
    @RestServiceVersion(7.3)
    public void importDocumentWithContents() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("import a document with binary contents under the Temp cabinet");
        printStep("and all contents are saved as primary content");
        RestObject newDocWithContent = new PlainRestObject("object_name", "doc_with_contents");
        RestObject createdDocWithContent = client.createDocument(tempCabinet, newDocWithContent,
                Arrays.asList((Object)"I'm the first content of the object", (Object)"I'm the second content of the object", (Object)"I'm the third content of the object", (Object)"I'm the fourth content of the object"),
                Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain"),
                "format", "crtext", "content-count", "4");
        printHttpStatus();
        print(createdDocWithContent);
        Feed<RestObject> contents = client.getContents(createdDocWithContent);
        printEntryContentSrc(contents);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdDocWithContent);
        printHttpStatus();        
        printNewLine();

        printStep("import a document with binary contents under the Temp cabinet");
        printStep("and the first content is saved as primary content, all other contents are saved as renditions");
        RestObject newDocWithRendition = new PlainRestObject("object_name", "doc_with_renditions");
        RestObject createdDocWithRendition = client.createDocument(tempCabinet, newDocWithRendition,
                Arrays.asList((Object)"I'm the first content of the object", (Object)"I'm the second content of the object", (Object)"I'm the third content of the object", (Object)"I'm the fourth content of the object"),
                Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain"),
                "all-primary", "false", "format", "crtext,html,pub_html,crtext", "modifier", ",,m2,m3", "content-count", "4");
        printHttpStatus();
        print(createdDocWithRendition);
        contents = client.getContents(createdDocWithRendition);
        printEntryContentSrc(contents);
        printNewLine();
        
        printStep("delete the created object");
        client.delete(createdDocWithRendition);
        printHttpStatus();        
        printNewLine();
    }
}
