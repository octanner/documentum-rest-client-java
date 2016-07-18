/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

import com.emc.documentum.rest.client.sample.client.DCTMRestErrorException;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.batch.Batch;
import com.emc.documentum.rest.client.sample.model.batch.Batch.OnError;
import com.emc.documentum.rest.client.sample.model.batch.BatchBuilder;
import com.emc.documentum.rest.client.sample.model.batch.Capabilities;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static org.springframework.http.HttpHeaders.LOCATION;

@RestServiceSample("Batch Capabilities and Batch")
@RestServiceVersion(7.2)
public class BatchSample extends Sample {
    public void batch() {
        printStep("get batch capabilities");
        Capabilities capabilities = client.getBatchCapabilities();
        System.out.println("transactions:" + capabilities.getTransactions() + ", sequence:" + capabilities.getSequence() + ", on-error:" + capabilities.getOnError());
        System.out.println("batchable resources: " + capabilities.getBatchable());
        System.out.println("non batchable resources: " + capabilities.getNonBatchable());
        printNewLine();
        
        printStep("create simple batch with get operations, and the requests are returned as well");
        BatchBuilder builder = BatchBuilder.builder(client).returnRequest(true);
        builder.operation().getUsers("items-per-page", "10");
        builder.operation().getCabinets("items-per-page", "10", "inline", "true");
        RestObject tempCabinet = client.getCabinet("Temp");
        builder.operation().getFolders(tempCabinet, "items-per-page", "10");
        builder.operation().getObjects(tempCabinet, "items-per-page", "10");
        builder.operation().get(tempCabinet);
        Batch batch = builder.build();
        Batch batchResult = client.createBatch(batch);
        print(batchResult, true, true);
        printNewLine();

        final int operationCount = 200;
        printStep("create sequential and parallel batch with " + operationCount + " get operations to see performance difference");
        builder = BatchBuilder.builder(client).description("sequential batch").transactional(false).sequential(true);
        for(int i=0;i<operationCount;++i) {
            builder.operation().getUsers("items-per-page", "10");
        }
        batch = builder.build();
        long start = System.currentTimeMillis();
        batchResult = client.createBatch(batch);
        long end = System.currentTimeMillis();
        System.out.println(batch.getDescription() + " executed " + (end - start) + " milliseconds");
        print(batchResult);

        builder = BatchBuilder.builder(client).description("parallel batch").transactional(false).sequential(false);
        for(int i=0;i<operationCount;++i) {
            builder.operation().getUsers("items-per-page", "10");
        }
        batch = builder.build();
        start = System.currentTimeMillis();
        batchResult = client.createBatch(batch);
        end = System.currentTimeMillis();
        System.out.println(batch.getDescription() + " executed " + (end - start) + " milliseconds");
        print(batchResult);
        printNewLine();

        printStep("create batch with create operations in a transaction");
        builder = BatchBuilder.builder(client).transactional(true);
        builder.operation().createDocument(tempCabinet, new PlainRestObject("object_name", "batch obj 1"));
        builder.operation().createDocument(tempCabinet, new PlainRestObject("object_name", "batch obj 2"));
        builder.operation().createDocument(tempCabinet, new PlainRestObject("object_name", "batch obj 3"));
        batch = builder.build();
        Batch createBatchResult = client.createBatch(batch);
        print(createBatchResult, true, true);
        printNewLine();
        
        printStep("create batch with delete operations in a transaction, the third operation will fail the transaction");
        builder = BatchBuilder.builder(client).transactional(true);
        builder.operation().delete(createBatchResult.getOperations().get(0).getResponse().getHeader(LOCATION));
        builder.operation().delete(createBatchResult.getOperations().get(1).getResponse().getHeader(LOCATION));
        //delete the deleted get(1) document
        builder.operation().delete(createBatchResult.getOperations().get(1).getResponse().getHeader(LOCATION));
        builder.operation().delete(createBatchResult.getOperations().get(2).getResponse().getHeader(LOCATION));
        batch = builder.build();
        try {
            client.createBatch(batch);
        } catch(DCTMRestErrorException e) {
            System.out.println(e.getStatus() + " [" + e.getError().getCode() + "] " + e.getError().getDetails()); 
        }
        print(client.getDocument(createBatchResult.getOperations().get(0).getResponse().getHeader(LOCATION)));
        print(client.getDocument(createBatchResult.getOperations().get(1).getResponse().getHeader(LOCATION)));
        print(client.getDocument(createBatchResult.getOperations().get(2).getResponse().getHeader(LOCATION)));
        printNewLine();
        
        printStep("create batch with delete operations in a non transaction and sequential and CONTINUE on error, the third operation will fail");
        builder = BatchBuilder.builder(client).transactional(false).sequential(true).onError(OnError.CONTINUE);
        builder.operation().delete(createBatchResult.getOperations().get(0).getResponse().getHeader(LOCATION));
        builder.operation().delete(createBatchResult.getOperations().get(1).getResponse().getHeader(LOCATION));
        //delete the deleted get(1) document
        builder.operation().delete(createBatchResult.getOperations().get(1).getResponse().getHeader(LOCATION));
        builder.operation().delete(createBatchResult.getOperations().get(2).getResponse().getHeader(LOCATION));
        batch = builder.build();
        batchResult = client.createBatch(batch);
        print(batchResult, false, true);
        try {
            client.getDocument(createBatchResult.getOperations().get(0).getResponse().getHeader(LOCATION));
        } catch(DCTMRestErrorException e) {
            System.out.println(e.getStatus() + " [" + e.getError().getCode() + "] " + e.getError().getDetails()); 
        }
        try {
            client.getDocument(createBatchResult.getOperations().get(1).getResponse().getHeader(LOCATION));
        } catch(DCTMRestErrorException e) {
            System.out.println(e.getStatus() + " [" + e.getError().getCode() + "] " + e.getError().getDetails()); 
        }
        try {
            client.getDocument(createBatchResult.getOperations().get(2).getResponse().getHeader(LOCATION));
        } catch(DCTMRestErrorException e) {
            System.out.println(e.getStatus() + " [" + e.getError().getCode() + "] " + e.getError().getDetails()); 
        }
        printNewLine();
        
        printStep("create batch with create operations with attached contents");
        builder = BatchBuilder.builder(client).transactional(true);
        builder.operation().createDocument(tempCabinet, new PlainRestObject("object_name", "batch obj 1"),
                new ByteArrayInputStream("I'm the content of the first object, 111".getBytes()), "text/plain");
        builder.operation().createDocument(tempCabinet, new PlainRestObject("object_name", "batch obj 2"),
                new ByteArrayInputStream("I'm the content of the second object, 222".getBytes()), "text/plain");
        builder.operation().createDocument(tempCabinet, new PlainRestObject("object_name", "batch obj 3"),
                new ByteArrayInputStream("I'm the content of the third object 333".getBytes()), "text/plain");
        batch = builder.build();
        createBatchResult = client.createBatch(batch);
        print(createBatchResult, true, true);
        printNewLine();
        
        printStep("create batch with delete operations");
        builder = BatchBuilder.builder(client);
        builder.operation().delete(createBatchResult.getOperations().get(0).getResponse().getHeader(LOCATION));
        builder.operation().delete(createBatchResult.getOperations().get(1).getResponse().getHeader(LOCATION));
        builder.operation().delete(createBatchResult.getOperations().get(2).getResponse().getHeader(LOCATION));
        batch = builder.build();
        batchResult = client.createBatch(batch);
        print(batchResult, false, true);
        printNewLine();
    }
    
    @RestServiceVersion(7.3)
    public void batchWithMultipleAttachment() {
        printStep("create batch with create operations with attached contents");
        RestObject tempCabinet = client.getCabinet("Temp");
        BatchBuilder builder = BatchBuilder.builder(client).returnRequest(true);
        builder = BatchBuilder.builder(client).transactional(true);
        builder.operation().createDocument(tempCabinet, new PlainRestObject("object_name", "batch obj 1"),
                new ByteArrayInputStream("I'm the content of the first object, 111".getBytes()), "text/plain");
        builder.operation().createDocument(tempCabinet, new PlainRestObject("object_name", "batch obj 2"),
                Arrays.asList((Object)new ByteArrayInputStream("I'm the number 1 content of the second object, 222-1".getBytes()),
                              (Object)new ByteArrayInputStream("I'm the number 2 content of the second object, 222-2".getBytes()),
                              (Object)new ByteArrayInputStream("I'm the number 3 content of the second object, 222-3".getBytes()),
                              (Object)new ByteArrayInputStream("I'm the number 4 content of the second object, 222-4".getBytes()),
                              (Object)new ByteArrayInputStream("I'm the number 5 content of the second object, 222-5".getBytes()),
                              (Object)new ByteArrayInputStream("I'm the number 6 content of the second object, 222-6".getBytes())),
                Arrays.asList("text/plain", "text/plain", "text/plain", "text/plain", "text/plain", "text/plain"),
                "content-count", "6", "format", "crtext");
        builder.operation().createDocument(tempCabinet, new PlainRestObject("object_name", "batch obj 3"),
                Arrays.asList((Object)new ByteArrayInputStream("I'm the number 1 content of the third object, 333-1".getBytes()),
                              (Object)new ByteArrayInputStream("I'm the number 2 content of the third object, 333-2".getBytes()),
                              (Object)new ByteArrayInputStream("I'm the number 3 content of the third object, 333-3".getBytes())),
                Arrays.asList("text/plain", "text/plain", "text/plain"),
                "content-count", "3", "format", "crtext,crtext,crtext", "modifier", ",mod1,mod2", "all-primary", "false");
        Batch batch = builder.build();
        Batch createBatchResult = client.createBatch(batch);
        print(createBatchResult, true, true);
        RestObject o1 = client.getDocument(createBatchResult.getOperations().get(0).getResponse().getHeader(LOCATION));
        printEntryContentSrc(client.getContents(o1));
        RestObject o2 = client.getDocument(createBatchResult.getOperations().get(1).getResponse().getHeader(LOCATION));
        printEntryContentSrc(client.getContents(o2));
        RestObject o3 = client.getDocument(createBatchResult.getOperations().get(2).getResponse().getHeader(LOCATION));
        printEntryContentSrc(client.getContents(o3));
        printNewLine();
        
        printStep("create batch with delete operations");
        builder = BatchBuilder.builder(client);
        builder.operation().delete(o1);
        builder.operation().delete(o2);
        builder.operation().delete(o3);
        batch = builder.build();
        Batch batchResult = client.createBatch(batch);
        print(batchResult, false, true);
        printNewLine();
    }
}
