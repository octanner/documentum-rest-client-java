/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.io.File;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.client.util.Randoms;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;

@RestServiceSample("Archived Contents")
@RestServiceVersion(16.4)
public class ArchivedContentsSample extends Sample {
    public void getArchivedContentsOfAnObject() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create a document with binary content under the Temp cabinet");
        RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
        RestObject createdObjectWithContent = client.createDocument(tempCabinet, newObjectWithContent, (Object)"I'm the primary content of the object", "text/plain", "format", "crtext");
        printHttpStatus();
        print(createdObjectWithContent);
        printNewLine();
        
        printStep("create a new rendition to the document");
        client.enableStreamingForNextRequest().createContent(createdObjectWithContent, "I'm the rendition content", "text/html", "primary", "false");
        printHttpStatus();
        printNewLine();
        
        String folder = read("Please input the file path to download the archived file:", System.getProperty("java.io.tmpdir") + Randoms.nextString(10));
        File file = client.getArchivedContents(folder, createdObjectWithContent.getObjectId());
        System.out.println("the file " + file.getAbsolutePath() + "(" + file.length() + " bytes) is created");
        if(file != null && read("Do you want to delete the file", true)) {
            if(!file.delete()) {
                file.deleteOnExit();
            }
        }
        
        printStep("delete the created object");
        client.delete(createdObjectWithContent);
        printHttpStatus();        
        printNewLine();
    }
}