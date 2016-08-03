/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample;

import java.util.List;

import com.emc.documentum.rest.client.sample.cases.Sample;
import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.model.RestObject;

import static com.emc.documentum.rest.client.sample.cases.Sample.loadSamples;
import static com.emc.documentum.rest.client.sample.client.DCTMRestClientBuilder.buildWithPrompt;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Reader.read;
import static com.emc.documentum.rest.client.sample.client.util.Reader.readEnterToContinue;

/**
 * sample application uses DCTMRestClient
 */
public class DCTMRestClientSample {
    private static DCTMRestClient client;
    private static final String NEWLINE = System.getProperty("line.separator");
    
    public static void main(String[] args) throws Exception {
        client = buildWithPrompt();
        
        double version = client.getMajorVersion();
        printNewLine();
        List<Sample> samples = loadSamples(version, client);
        RestObject productInfo = client.getProductInfo();
        StringBuilder sb = new StringBuilder();
        sb.append(productInfo.getProperties().get("product") + " " + productInfo.getProperties().get("product_version")).append(NEWLINE)
          .append(" 0 Exit").append(NEWLINE);
        for(int i=0;i<samples.size();++i) {
            sb.append(" " + (i+1) + " " + samples.get(i).name() + " samples").append(NEWLINE);
        }
        sb.append("Please input the number of the sample operation which need be executed:");
        while(true) {
            String sampleOperation = read(sb.toString());
            int op = 0;
            try {
                op = Integer.parseInt(sampleOperation);
                if(op == 0) {
                    System.out.println("Thanks, bye.");
                    System.exit(0);
                } else if(op <= samples.size()) {
                    samples.get(op-1).run();
                } else {
                    System.out.println("Unsupported " + op);
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            readEnterToContinue();
        }
    }
}
