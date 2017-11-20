/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
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
    
    public static class SearchTemplateFeed extends JsonFeed<JsonSearchTemplate>{
    }

    public static class SavedSearchFeed extends JsonFeed<JsonSavedSearch>{
    }

    public static class PreferenceFeed extends JsonFeed<JsonPreference>{
    }
    
    public static class CommentFeed extends JsonFeed<JsonComment>{
    }

    public static class VirtualDocumentNodeFeed extends JsonFeed<JsonVirtualDocumentNode>{
    }
}
