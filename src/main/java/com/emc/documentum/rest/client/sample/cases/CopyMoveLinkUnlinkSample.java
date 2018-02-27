/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.FolderLink;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainFolderLink;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.CHILD_LINKS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.OBJECTS;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.PARENT_LINKS;

@RestServiceSample("Copy, Move, Link and Unlink")
public class CopyMoveLinkUnlinkSample extends Sample {
    public void copyMoveLinkUnlink() {
        RestObject tempCabinet = client.getCabinet("Temp");
        RestObject objectToBeChanged = client.createObject(tempCabinet, OBJECTS, new PlainRestObject("object_name", "object_to_be_copied", "r_object_type", "dm_sysobject"));
        RestObject destFolder = client.createFolder(tempCabinet, new PlainRestObject("object_name", "my_dest_folder"));
        printStep("copy the object " + objectToBeChanged.getObjectName() + "(" + objectToBeChanged.getObjectId() + ")" + " from " + tempCabinet.getObjectName() + "(" + tempCabinet.getObjectId() + ")"
                  + " to " + destFolder.getObjectName() + "(" + destFolder.getObjectId() + ")");
        RestObject copiedObject = client.createObject(destFolder, OBJECTS, new PlainRestObject(objectToBeChanged.self()));
        printHttpStatus();
        print(copiedObject, "r_object_id", "object_name", "i_folder_id");
        printNewLine();
        
        printStep("get the parent folder links of the object " + objectToBeChanged.getObjectName() + "(" + objectToBeChanged.getObjectId() + ")");
        Feed<FolderLink> parentFolderLinks = client.getFolderLinks(objectToBeChanged, PARENT_LINKS);
        printEntryContentSrc(parentFolderLinks);
        printNewLine();

        printStep("move the object " + objectToBeChanged.getObjectName() + "(" + objectToBeChanged.getObjectId() + ")" + " from " + tempCabinet.getObjectName() + "(" + tempCabinet.getObjectId() + ")"
                + " to " + destFolder.getObjectName() + "(" + destFolder.getObjectId() + ")");
        FolderLink parentLinkToBeMove = client.getFolderLink(parentFolderLinks.getEntries().get(0).getContentSrc());
        if(parentLinkToBeMove.getChildId().equals(objectToBeChanged.getObjectId()) && parentLinkToBeMove.getParentId().equals(tempCabinet.getObjectId())) {
            FolderLink movedLink = client.move(parentLinkToBeMove, new PlainFolderLink(destFolder.self()));
            System.out.println("href:" + movedLink.getHref() + ", parent id:" + movedLink.getParentId() + ", child id:" + movedLink.getChildId());
        } else {
            System.out.println("nothing to be moved");
        }
        printNewLine();
        
        printStep("link the object " + objectToBeChanged.getObjectName() + "(" + objectToBeChanged.getObjectId() + ")" + " to " + tempCabinet.getObjectName() + "(" + tempCabinet.getObjectId() + ")" + " by parent links");
        FolderLink linkedByTheParentLinks = client.link(objectToBeChanged, PARENT_LINKS, new PlainFolderLink(tempCabinet.self()));
        System.out.println("href:" + linkedByTheParentLinks.getHref() + ", parent id:" + linkedByTheParentLinks.getParentId() + ", child id:" + linkedByTheParentLinks.getChildId());
        printNewLine();
        
        printStep("unlink the object " + objectToBeChanged.getObjectName() + "(" + objectToBeChanged.getObjectId() + ")" + " from " + destFolder.getObjectName() + "(" + destFolder.getObjectId() + ")" + " by parent link");
        parentFolderLinks = client.getFolderLinks(objectToBeChanged, PARENT_LINKS, "inline", "true");
        for(Entry<FolderLink> e : parentFolderLinks.getEntries()) {
            if(e.getContentObject().getChildId().equals(objectToBeChanged.getObjectId()) && e.getContentObject().getParentId().equals(destFolder.getObjectId())) {
                client.delete(e);
                printHttpStatus();
            }
        }
        printNewLine();
        
        printStep("link the object " + objectToBeChanged.getObjectName() + "(" + objectToBeChanged.getObjectId() + ")" + " to " + destFolder.getObjectName() + "(" + destFolder.getObjectId() + ")" + " by child links");
        FolderLink linkedByTheChildLinks = client.link(destFolder, CHILD_LINKS, new PlainFolderLink(objectToBeChanged.self()));
        System.out.println("href:" + linkedByTheChildLinks.getHref() + ", parent id:" + linkedByTheChildLinks.getParentId() + ", child id:" + linkedByTheChildLinks.getChildId());
        printNewLine();
        
        printStep("unlink the object " + objectToBeChanged.getObjectName() + "(" + objectToBeChanged.getObjectId() + ")" + " from " + destFolder.getObjectName() + "(" + destFolder.getObjectId() + ")" + " by child link");
        Feed<FolderLink> childFolderLinks = client.getFolderLinks(destFolder, CHILD_LINKS, "inline", "true");
        for(Entry<FolderLink> e : childFolderLinks.getEntries()) {
            if(e.getContentObject().getChildId().equals(objectToBeChanged.getObjectId()) && e.getContentObject().getParentId().equals(destFolder.getObjectId())) {
                client.delete(e);
                printHttpStatus();
            }
        }
        printNewLine();

        client.delete(copiedObject);
        client.delete(objectToBeChanged);
        client.delete(destFolder);
    }
}
