/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Network Location(s)")
public class NetworkLocationSample extends Sample {
    public void networkLocation() {
        printStep("get all network locations");
        Feed<RestObject> networkLocations = client.getNetworkLocations();
        printEntryContentSrc(networkLocations);
        printNewLine();

        if(networkLocations.getEntries().size() > 0) {
            printStep("get a single network location");
            RestObject networkLocation = client.getNetworkLocation(networkLocations.getEntries().get(0).getContentSrc());
            print(networkLocation, "object_name", "r_object_type", "netloc_ident", "netloc_display_name", "begin_near_ip_address", "end_near_ip_address");
            printNewLine();
        }
    }
}
