/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.json;


public class JsonFeeds {
    public static class ObjectFeed extends JsonFeed<JsonObject>{
    }
    
    public static class RepositoryFeed extends JsonFeed<JsonRepository>{
    }

    public static class TypeFeed extends JsonFeed<JsonType>{
    }
    
    public static class FolderLinkFeed extends JsonFeed<JsonFolderLink>{
    }
    
    public static class SearchFeed extends JsonSearchFeed<JsonObject>{
    }
    
    public static class PreferenceFeed extends JsonFeed<JsonPreference>{
    }
}
