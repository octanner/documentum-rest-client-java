/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

/**
 * the link relations used by REST services
 * the resource should be navigated by link relations except Home Document resource
 * only a few link relations are listed
 */
public enum LinkRelation {
    SELF("self"),
    EDIT("edit"),
    DELETE("delete", true),
    UP("up"),
    DOWN("down"),
    CONTENTS("contents"),
    CONTENT("content"),
    EDIT_MEDIA("edit-media"),
    VERSIONS("version-history"),
    PARENT("parent"),
    ALTERNATE("alternate"),
    PAGING_NEXT("next"),
    PAGING_PREV("previous"),
    PAGING_FIRST("first"),
    PAGING_LAST("last"),
    ENCLOSURE("enclosure"),
    AUTHOR("authour"),
    CANONICAL("canonical"),
    PREDECESSOR_VERSION("predecessor-version"),
    TYPE("type", true),
    TYPES("types", true),
    ASPECT_TYPES("aspect-types", true),
    ASSIS_VALUES("assist-values", true),
    ACL("acl", true),
    
    // Documentum specific link relations
    FOLDERS("folders", true),
    DOCUMENTS("documents", true),
    OBJECTS("objects", true),
    ANCESTOR_FOLDER("ancestor-folder", true),
    CABINET("cabinet", true),
    CONTENT_MEDIA("content-media", true),
    PRIMARY_CONTENT("primary-content", true),
    CHECKED_OUT_OBJECTS("checked-out-objects", true),
    DEFAULT_FOLDER("default-folder", true),
    RELATIONS("relations", true),
    RELATION_TYPES("relation-types", true),
    ACLS("acls", true),
    ASSOCIATIONS("associations", true),
    PERMISSIONS("permissions", true),
    PERMISSION_SET("permission-set", true),
    OBJECT_ASPECTS("object-aspects", true),
    COMMENTS("comments", true),
    REPLIES("replies", true),
    VIRTUAL_DOCUMENT_NODES("virtual-document-nodes", true),
    
    //Home Document link relations
    REPOSITORIES("repositories", true),
    ABOUT("about", false),
    
    //User/Group link relation
    USERS("users", true),
    USER("user", true),
    GROUPS("groups", true),
    CURRENT_USER("current-user", true),
    CURRENT_USER_PREFERENCES("current-user-preferences", true),
    
    //for versioning
    CHECKIN_NEXT_MAJOR("checkin-next-major", true),
    CHECKIN_NEXT_MINOR("checkin-next-minor", true),
    CHECKIN_BRANCH_VERSION("checkin-branch", true),
    CHECKOUT("checkout", true),
    CANCEL_CHECKOUT("cancel-checkout", true),
    
    //for lightweight object
    MATERIALIZE("materialize", true),
    DEMATERIALIZE("dematerialize", true),
    LIGHTWEIGHT_OBJECTS("lightweight-objects", true),
    SHARED_PARENT("shared-parent", true),
    
    //for batch
    BATCH_CAPABILITIES("batch-capabilities", true),
    BATCHES("batches", true),
    
    DQL("dql", true),
    SEARCH("search", true),
    SAVED_SEARCHES("saved-searches", true),
    SAVED_SEARCH_SAVED_RESULTS("saved-search-results", true),
    SAVED_SEARCH_EXECUTION("search-execution", true),
    SEARCH_TEMPLATES("search-templates", true),
    CABINETS("cabinets", true),
    FORMATS("formats", true),
    NETWORK_LOCATIONS("network-locations", true),
    PARENT_LINKS("parent-links", true),
    CHILD_LINKS("child-links", true);
    
    private static final String PREFIX = "http://identifiers.emc.com/linkrel/";
    private final String rel;
    
    private LinkRelation(String rel) {
        this.rel = rel;
    }
    
    private LinkRelation(String rel, boolean isDocumentum) {
        this.rel = isDocumentum ? PREFIX + rel : rel;
    }
    
    public String rel() {
        return rel;
    }
}
