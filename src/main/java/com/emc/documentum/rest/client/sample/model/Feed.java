/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;


/**
 * the class represents a feed collection
 */
public interface Feed<T extends Linkable> extends FeedBase<T, Entry<T>> {

}
