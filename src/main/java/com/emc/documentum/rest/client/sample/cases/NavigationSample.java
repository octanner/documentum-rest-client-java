/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Entry;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.HomeDocument;
import com.emc.documentum.rest.client.sample.model.Repository;
import com.emc.documentum.rest.client.sample.model.RestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printLinks;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.SELF;

@RestServiceSample("Navigation")
public class NavigationSample extends Sample {
    public void navigation() {
        printStep("get home document resource");
        HomeDocument home = client.getHomeDocument();
        printLinks(home);
        printNewLine();
        
        printStep("get repositories collection");
        Feed<Repository> repositories = client.getRepositories();
        System.out.println("There are " + repositories.getTotal() + " repositories in total.");
        printEntryContentSrc(repositories);
        printNewLine();

        printStep("get the repository");
        Repository repository = client.getRepository();
        System.out.println(repository.getName());
        for(Repository.Server s : repository.getServers()) {
            System.out.println("Name: " + s.getName());
            System.out.println("Host: " + s.getHost());
            System.out.println("Docbroker: " + s.getDocbroker());
            System.out.println("Version: " + s.getVersion());
        }
        printNewLine();
        
        printStep("get cabinets");
        Feed<RestObject> cabinets = client.getCabinets();
        printEntryContentSrc(cabinets);
        printNewLine();

        printStep("get cabinets by page with 2 cabinets per page");
        cabinets = client.getCabinets("items-per-page", "2", "include-total", "true");
        System.out.println("There are " + cabinets.getTotal() + " cabinets in total.");
        printEntryContentSrc(cabinets);
        printNewLine();

        printStep("get cabinets of the next page");
        cabinets = client.nextPage(cabinets);
        printEntryContentSrc(cabinets);
        printNewLine();
        
        printStep("get cabinets of the last page");
        cabinets = client.lastPage(cabinets);
        printEntryContentSrc(cabinets);
        printNewLine();

        printStep("get cabinets of the previous page");
        cabinets = client.previousPage(cabinets);
        printEntryContentSrc(cabinets);
        printNewLine();
        
        printStep("get the Temp cabinet");
        RestObject tempCabinet = client.getCabinet("Temp");
        print(tempCabinet);
        printNewLine();

        printStep("get folders under the Temp cabinet");
        Feed<RestObject> folders = client.getFolders(tempCabinet);
        printEntryContentSrc(folders);
        printNewLine();
        
        printStep("get documents under the Temp cabinet");
        Feed<RestObject> documents = client.getDocuments(tempCabinet);
        printEntryContentSrc(documents);
        printNewLine();
        
        printStep("get sysobjects under the Temp cabinet");
        Feed<RestObject> objects = client.getDocuments(tempCabinet);
        printEntryContentSrc(objects);
        printNewLine();
        
        printStep("get cabinets with the content embedded in the feed entry");
        cabinets = client.getCabinets("inline", "true");
        for(Entry<RestObject> e : cabinets.getEntries()) {
            RestObject o = e.getContentObject();
            print(o);
            print(o, SELF);
        }
        printNewLine();
        
        printStep("get folders under the Temp cabinet with the content embedded in the feed entry");
        folders = client.getFolders(tempCabinet, "inline", "true");
        for(Entry<RestObject> e : folders.getEntries()) {
            RestObject o = e.getContentObject();
            print(o);
            print(o, SELF);
        }
        printNewLine();
    }
}
