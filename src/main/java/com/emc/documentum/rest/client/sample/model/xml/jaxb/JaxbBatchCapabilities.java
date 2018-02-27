/*
 * Copyright (c) 2018. Open Text Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.batch.Capabilities;

@XmlRootElement(name="batch-capabilities")
public class JaxbBatchCapabilities extends JaxbDmLinkableBase implements Capabilities {
    private String transactions;
    private String sequence;
    private String onError;
    private List<String> batchable;
    private List<String> nonBatchable;

    @Override
    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }

    @Override
    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    @XmlElement(name="on-error")
    public String getOnError() {
        return onError;
    }

    public void setOnError(String onError) {
        this.onError = onError;
    }

    @Override
    @XmlElementWrapper(name="batchable-resources")
    @XmlElement(name="batchable-resource")
    public List<String> getBatchable() {
        return this.batchable;
    }

    public void setBatchable(List<String> batchable) {
        this.batchable = batchable;
    }

    @Override
    @XmlElementWrapper(name="non-batchable-resources")
    @XmlElement(name="non-batchable-resource")
    public List<String> getNonBatchable() {
        return nonBatchable;
    }

    public void setNonBatchable(List<String> nonBatchable) {
        this.nonBatchable = nonBatchable;
    }

    @Override
    public boolean equals(Object obj) {
        JaxbBatchCapabilities that = (JaxbBatchCapabilities)obj;
        return Equals.equal(transactions, that.transactions) &&
               Equals.equal(sequence, that.sequence) &&
               Equals.equal(onError, that.onError) &&
               Equals.equal(batchable, that.batchable) &&
               Equals.equal(nonBatchable, that.nonBatchable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactions, sequence, onError, batchable, nonBatchable);
    }
}
