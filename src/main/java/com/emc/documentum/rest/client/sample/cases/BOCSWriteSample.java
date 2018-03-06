/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.io.ByteArrayInputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.LinkRelation;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.Repository.Server;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;

@RestServiceSample("BOCS/ACS write")
@RestServiceVersion(16.4)
public class BOCSWriteSample extends Sample {
    public void createDocumentWithACSWrite() {
        distributeWrite(false, null);
        printNewLine();
        distributeWrite(true, null);
    }

    public void createDocumentWithBOCSWrite() {
        String networkLocation = read("Please input the network location used by the bocs:", "");
        if(!StringUtils.isEmpty(networkLocation)) {
            distributeWrite(false, networkLocation);
            printNewLine();
            distributeWrite(true, networkLocation);
        }
    }
    
    private void distributeWrite(boolean checkOut, String networkLocation) {
        if(!supported()) {
            return;
        }
        
        RestObject object = checkOut?checkoutObject(networkLocation):createObject(networkLocation);
        
        printStep("upload the content to the server");
        client.uploadDistributedContent(object.getHref(LinkRelation.DISTRIBUTED_UPLOAD), new ByteArrayInputStream("the distributed content".getBytes()));
        printHttpStatus();
        System.out.println("the object location: " + client.getHeaders().getFirst(HttpHeaders.LOCATION));
        printNewLine();
        
        printStep("delete the created document");
        client.delete(object, "del-version", "ALL");
        printHttpStatus();
        printNewLine();
    }
    
    private RestObject createObject(String networkLocation) {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create a document and require BOCS/ACS write link under the Temp cabinet");
        RestObject newObjectWithoutContent = new PlainRestObject("object_name", "distribute_upload_obj");
        RestObject created = networkLocation==null?
                    client.createDocument(tempCabinet, (RestObject)newObjectWithoutContent, (Object)null, null,
                        "require-dc-write", "true", "format", "crtext", "content-length", "23"):
                    client.createDocument(tempCabinet, (RestObject)newObjectWithoutContent, (Object)null, null,
                        "require-dc-write", "true", "format", "crtext", "content-length", "23", "network-location", networkLocation);
        printHttpStatus();
        print(created);
        System.out.println("the distributed-upload link is: " + created.getHref(LinkRelation.DISTRIBUTED_UPLOAD));
        printNewLine();
        return created;
    }
    
    private RestObject checkoutObject(String networkLocation) {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create a document");
        RestObject newObjectWithoutContent = new PlainRestObject("object_name", "distribute_upload_obj");
        RestObject created = client.createDocument(tempCabinet, (RestObject)newObjectWithoutContent);
        printHttpStatus();
        printNewLine();

        printStep("check out the document and require BOCS/ACS write link");
        RestObject checkedOut = networkLocation==null?
                    client.checkout(created, "require-dc-write", "true", "format", "crtext", "content-length", "23"):
                    client.checkout(created, "require-dc-write", "true", "format", "crtext", "content-length", "23", "network-location", networkLocation);
        printHttpStatus();
        print(checkedOut);
        System.out.println("the distributed-upload link is: " + checkedOut.getHref(LinkRelation.DISTRIBUTED_UPLOAD));
        printNewLine();

        return checkedOut;
    }
        
    private boolean supported() {
        Repository repo = client.getRepository();
        Server server = repo.getServers().get(0);
        String[] version = server.getVersion().split("\\.");
        if(Integer.parseInt(version[0]) < 16) {
            System.out.println("The BOCS Write is not supported by the repository " + server.getVersion());
            return false;
        } else {
            return true;
        }
    }
}
