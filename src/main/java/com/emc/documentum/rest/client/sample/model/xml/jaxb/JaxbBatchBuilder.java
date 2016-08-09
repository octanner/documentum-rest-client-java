package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import com.emc.documentum.rest.client.sample.client.DCTMRestClient;
import com.emc.documentum.rest.client.sample.model.batch.SettableAttachment;
import com.emc.documentum.rest.client.sample.model.batch.SettableBatch;
import com.emc.documentum.rest.client.sample.model.batch.SettableHeader;
import com.emc.documentum.rest.client.sample.model.batch.SettableInclude;
import com.emc.documentum.rest.client.sample.model.batch.SettableOperation;
import com.emc.documentum.rest.client.sample.model.batch.SettableRequest;
import com.emc.documentum.rest.client.sample.model.builder.BatchBuilder;

public class JaxbBatchBuilder extends BatchBuilder {
    public JaxbBatchBuilder(DCTMRestClient client) {
        super(client);
    }

    @Override
    protected SettableBatch createBatch() {
        return new JaxbBatch();
    }

    @Override
    protected SettableOperation createOperation() {
        return new JaxbBatchOperation();
    }

    @Override
    protected SettableRequest createRequest() {
        return new JaxbBatchRequest();
    }

    @Override
    protected SettableHeader createHeader() {
        return new JaxbBatchHeader();
    }

    @Override
    protected SettableInclude createInclude() {
        return new JaxbBatchInclude();
    }

    @Override
    protected SettableAttachment createAttachment() {
        return new JaxbBatchAttachment();
    }
}
