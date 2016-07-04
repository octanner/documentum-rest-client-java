/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.ENCLOSURE;

@RestServiceSample("Content Management")
public class ContentManagementSample extends Sample {
    public void contentManagement() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create a document with binary content under the Temp cabinet");
        RestObject newObjectWithContent = new PlainRestObject("object_name", "obj_with_content");
        RestObject createdObjectWithContent = client.createDocument(tempCabinet, newObjectWithContent, "I'm the content of the object", "text/plain", "format", "crtext");
        printHttpStatus();
        print(createdObjectWithContent);
        printNewLine();
        
        printStep("get primary content of the document");
        RestObject primaryContent = client.getPrimaryContent(createdObjectWithContent, "media-url-policy", "all");
        System.out.println("the ACS link: " + primaryContent.getHref(ENCLOSURE, "ACS"));
        System.out.println("the local link: " + primaryContent.getHref(ENCLOSURE, "LOCAL"));
        printNewLine();

        printStep("create a new rendition to the document");
        client.enableStreamingForNextRequest();
        client.createContent(createdObjectWithContent, "I'm the rendition content", "text/html", "primary", "false");
        printHttpStatus();
        printNewLine();
        
        printStep("get primary content and rendition collection of the document");
        Feed<RestObject> renditionList = client.getContents(createdObjectWithContent);
        for(Entry<RestObject> renditionEntry : renditionList.getEntries()) {
            System.out.println(renditionEntry.getTitle());
            RestObject rendition = client.getContent(renditionEntry.getContentSrc());
            System.out.println("the content link: " + rendition.getHref(ENCLOSURE));
        }
        printNewLine();
        
        printStep("get the content bytes of the document from the local rest server");
        renditionList = client.getContents(createdObjectWithContent, "media-url-policy", "local", "inline", "true");
        for(Entry<RestObject> renditionEntry : renditionList.getEntries()) {
            System.out.println(renditionEntry.getTitle());
            System.out.println("the content media link: " + renditionEntry.getHref(ENCLOSURE));
            byte[] bytes = client.getContentBytes(renditionEntry.getHref(ENCLOSURE));
            System.out.println(new String(bytes));
        }
        printNewLine();
        
        printStep("delete the created document");
        client.delete(createdObjectWithContent);
        printHttpStatus();        
        printNewLine();
    }
}
