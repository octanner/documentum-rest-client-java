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
}
