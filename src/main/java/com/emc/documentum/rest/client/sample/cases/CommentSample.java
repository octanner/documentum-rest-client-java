/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Comment;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainComment;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printFields;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Comments and Replies")
@RestServiceVersion(7.3)
public class CommentSample extends Sample {
    public void acls() {
        RestObject tempCabinet = client.getCabinet("Temp");
        RestObject object = new PlainRestObject("object_name", "obj_for_comment");
        RestObject createdObject = client.createObject(tempCabinet, object);

        printStep("create a comment for the object " + createdObject.getObjectId());
        Comment createdComment1 = client.createComment(createdObject, new PlainComment("this is my first comment"));
        printFields(createdComment1);
        printNewLine();
        
        printStep("create another comment for the object " + createdObject.getObjectId());
        Comment createdComment2 = client.createComment(createdObject, new PlainComment("this is my second comment"));
        printFields(createdComment2);
        printNewLine();

        printStep("get comments feed of the object " + createdObject.getObjectId());
        Feed<Comment> comments = client.getComments(createdObject);
        printEntryContentSrc(comments);
        printNewLine();
        
        printStep("create a replay for the first comment " + createdComment1.getCommentId());
        Comment createdReply1 = client.createReply(createdComment1, new PlainComment("this is my first reply"));
        printFields(createdReply1);
        printNewLine();
        
        printStep("create another replay for the first comment " + createdComment1.getCommentId());
        Comment createdReply2 = client.createReply(createdComment1, new PlainComment("this is my second reply"));
        printFields(createdReply2);
        printNewLine();

        printStep("get replies feed of the first comment " + createdComment1.getCommentId());
        Feed<Comment> replies = client.getReplies(createdComment1);
        printEntryContentSrc(replies);
        printNewLine();

        printStep("delete the comments and replies");
        client.delete(createdReply1);
        printHttpStatus();
        client.delete(createdReply2);
        printHttpStatus();
        client.delete(createdComment1);
        printHttpStatus();
        client.delete(createdComment2);
        printHttpStatus();
        
        client.delete(createdObject);
        printNewLine();
    }
}
