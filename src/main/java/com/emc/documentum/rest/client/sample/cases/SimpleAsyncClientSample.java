/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.client.DCTMRestClientBuilder;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.FutureModel;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.RestType;
import com.emc.documentum.rest.client.sample.model.SearchFeed;
import com.emc.documentum.rest.client.sample.model.batch.Capabilities;

import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Simple async client")
public class SimpleAsyncClientSample extends Sample {
    @SuppressWarnings("unused")
    public void asyncCall() {
        printStep("build up async client based on the sync client");
        DCTMRestClient asyncClinet = DCTMRestClientBuilder.buildAsyncClient(client);

        printStep("execute operations of DCTMRestClient like a normal sync clinet");
        Feed<RestObject> groups = asyncClinet.getGroups();
        
        printStep("continue executing other operations without blocking");
        Feed<RestObject> cabinets = asyncClinet.getCabinets();
        Capabilities capabilities = asyncClinet.getBatchCapabilities();
        RestObject currentUser = asyncClinet.getCurrentUser();
        Feed<RestObject> users = asyncClinet.getUsers();
        
        printStep("use the result as the normal DCTMRestClient response, but the operations on the result will be blocked until the async execution finished");
        System.out.println("there are " + groups.getEntries().size() + " gruops");
        
        printStep("cast the response to the FutureModel, if wants to know more information");
        if(users instanceof FutureModel) {
            FutureModel futureUsers = (FutureModel)users;
            
            printStep("check whether the execution finished");
            while(true) {
                boolean ready = futureUsers.isModelReady();
                System.out.println("isModelReady: " + ready);
                if(ready) {
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }

            printStep("or get the async call status and headers");
            System.out.println(futureUsers.getStatus());
            System.out.println(futureUsers.getHeaders());
        }
        
        printStep("get the status and headers from the async client, will only return the latest operation's info, here is getUsers");
        System.out.println(asyncClinet.getStatus());
        System.out.println(asyncClinet.getHeaders());
    }

    public void compareWithSync() {
        printStep("warm up");
        Feed<RestObject> cabinets = client.getCabinets();
        Feed<RestObject> users = client.getUsers();
        Feed<RestType> types = client.getTypes();
        Feed<RestObject> formats = client.getFormats();
        SearchFeed<RestObject> searches = client.search("test");
        
        printStep("execute some requests syncly");
        long start = System.currentTimeMillis();
        cabinets = client.getCabinets();
        users = client.getUsers();
        types = client.getTypes();
        formats = client.getFormats();
        searches = client.search("test");
        long end = System.currentTimeMillis();
        System.out.println("sync execution costs " + (end - start) + " milliseconds");
        System.out.println("cabinets: " + cabinets.getEntries().size() +
                           ", users: " + users.getEntries().size() +
                           ", types: " + types.getEntries().size() +
                           ", formats: " + formats.getEntries().size() +
                           ", searches: " + searches.getEntries().size());
       
        printStep("execute some requests asyncly");
        DCTMRestClient asyncClinet = DCTMRestClientBuilder.buildAsyncClient(client);
        start = System.currentTimeMillis();
        cabinets = asyncClinet.getCabinets();
        users = asyncClinet.getUsers();
        types = asyncClinet.getTypes();
        formats = asyncClinet.getFormats();
        searches = asyncClinet.search("test");
        end = System.currentTimeMillis();
        System.out.println("async execution costs " + (end - start) + " milliseconds");
        System.out.println("cabinets: " + cabinets.getEntries().size() +
                ", users: " + users.getEntries().size() +
                ", types: " + types.getEntries().size() +
                ", formats: " + formats.getEntries().size() +
                ", searches: " + searches.getEntries().size());
    }
}
