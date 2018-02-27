/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import org.springframework.util.StringUtils;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.VirtualDocumentNode;

import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntry;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContent;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;

@RestServiceSample("Virtual Document Node")
@RestServiceVersion(7.3)
public class VirtualDocumentNodeSample extends Sample {
    public void vdnodes() {
        RestObject object = null;
        while(object == null) {
            String vdId = read("Please input the object id of the virtual document object (press 'return' to skip sample):", "");
            if(StringUtils.isEmpty(vdId)) {
                return;
            }
            Feed<RestObject> feed = client.dql("select r_object_id from dm_sysobject where r_object_id='" + vdId + "'");
            if(feed.getEntries() != null && feed.getEntries().size() == 1) {
                String objectUri = feed.getEntries().get(0).getHref(LinkRelation.EDIT);
                object = client.getObject(objectUri);
                if(object.getHref(LinkRelation.VIRTUAL_DOCUMENT_NODES) == null) {
                    System.out.println("The object " + vdId + " is not a virtual document.");
                    object = null;
                }
            } else {
                System.out.println("The object " + vdId + " can not be found.");
            }
        }

        printStep("get the virtual document nodes of the object " + object.getObjectId());
        Feed<VirtualDocumentNode> nodes = client.getVirtualDocumentNodes(object);
        printEntry(nodes, "id", "title", "summary");
        printNewLine();

        printStep("get the inline virtual document nodes of the object " + object.getObjectId());
        nodes = client.getVirtualDocumentNodes(object, "inline", "true");
        printEntryContent(nodes, "selectedObjectName", "selectedObjectId", "nodeId", "vdmNumber");
        printNewLine();

        printStep("get the inline virtual document nodes of the object with all depth" + object.getObjectId());
        nodes = client.getVirtualDocumentNodes(object, "inline", "true", "depth", "-1");
        printEntryContent(nodes, "selectedObjectName", "selectedObjectId", "nodeId", "vdmNumber", "availableVersions");
        printNewLine();
    }
}
