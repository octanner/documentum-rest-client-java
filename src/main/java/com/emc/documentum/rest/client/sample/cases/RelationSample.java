/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.OBJECTS;

@RestServiceSample("Relation Type(s) and Relation")
public class RelationSample extends Sample {
    public void relation() {
        printStep("get all relation types");
        Feed<RestObject> relationTypes = client.getRelationTypes();
        printEntryContentSrc(relationTypes);
        printNewLine();

        printStep("get a single relation type");
        RestObject relationType = client.getRelationType(relationTypes.getEntries().get(0).getContentSrc());
        print(relationType, "relation_name", "parent_type", "child_type");
        printNewLine();

        printStep("get all relations");
        Feed<RestObject> relations = client.getRelations();
        printEntryContentSrc(relations);
        printNewLine();
        
        printStep("create a relation");
        RestObject tempCabinet = client.getCabinet("Temp");
        RestObject parent = client.createObject(tempCabinet, OBJECTS, new PlainRestObject("object_name", "parent_object", "r_object_type", "dm_sysobject"));
        RestObject child = client.createObject(tempCabinet, OBJECTS, new PlainRestObject("object_name", "child_object", "r_object_type", "dm_sysobject"));
        RestObject createdRelation = client.createRelation(new PlainRestObject("relation_name", "peer", "parent_id", parent.getObjectId(), "child_id", child.getObjectId()));
        print(createdRelation, "relation_name", "parent_id", "child_id", "r_object_id");
        printNewLine();
        
        printStep("delete a relation");
        client.delete(createdRelation);
        printHttpStatus();
        printNewLine();
        
        client.delete(parent);
        client.delete(child);
    }
}
