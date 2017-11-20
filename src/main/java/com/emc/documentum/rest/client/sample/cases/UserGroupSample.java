/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.client.util.Randoms;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;
import static com.emc.documentum.rest.client.sample.model.LinkRelation.DEFAULT_FOLDER;

@RestServiceSample("User(s)/Group(s)")
public class UserGroupSample extends Sample {
    public void getUserGroup() {
        printStep("get all users");
        Feed<RestObject> users = client.getUsers();
        printEntryContentSrc(users);
        printNewLine();

        printStep("get a single user");
        RestObject user = client.getUser(users.getEntries().get(0).getContentSrc());
        print(user, "user_name", "user_login_name", "user_privileges", "user_address");
        printNewLine();
        
        printStep("get the user " + user.getProperties().get("user_name") + "'s default folder");
        RestObject defaultFolder = client.getFolder(user.getHref(DEFAULT_FOLDER));
        print(defaultFolder);
        printNewLine();

        printStep("get all groups");
        Feed<RestObject> groups = client.getGroups();
        printEntryContentSrc(groups);
        printNewLine();

        printStep("get a single group");
        RestObject group = client.getGroup(groups.getEntries().get(0).getContentSrc());
        print(group, "group_name", "owner_name", "group_display_name");
        printNewLine();

    }
    
    @RestServiceVersion(7.3)
    public void manulateUserGroup() {
        String newUser = "user_" + Randoms.nextString(10);
        printStep("create the user " + newUser);
        RestObject createdUser = client.createUser(new PlainRestObject("user_name", newUser, "user_login_name", newUser, "user_address", newUser + "@test.com"));
        print(createdUser, "user_name", "user_login_name", "user_privileges", "user_address");
        printNewLine();

        printStep("update the user " + newUser);
        RestObject updatedUser = client.update(createdUser, new PlainRestObject("user_address", "updated " + newUser + "@test.com"));
        print(updatedUser, "user_name", "user_login_name", "user_privileges", "user_address");
        printNewLine();

        String newGroup = "group_" + Randoms.nextString(10);
        printStep("create the group " + newGroup);
        RestObject createdGroup = client.createGroup(new PlainRestObject("group_name", newGroup));
        print(createdGroup, "group_name", "owner_name", "group_display_name");
        printNewLine();

        printStep("update the group " + newGroup);
        RestObject updatedGroup = client.update(createdGroup, new PlainRestObject("group_display_name", "updated " + newGroup));
        print(updatedGroup, "group_name", "owner_name", "group_display_name");
        printNewLine();

        printStep("get the group " + newGroup + " member users");
        Feed<RestObject> groupUsers = client.getUsers(updatedGroup);
        if(groupUsers.getEntries() == null) {
            System.out.println("there are 0 users in the group " + newGroup);
        }
        printNewLine();

        printStep("add the user " + newUser + " to the group " + newGroup);
        client.addUserToGroup(updatedGroup, updatedUser);
        printHttpStatus();        
        printNewLine();
        
        printStep("get the group " + newGroup + " member users");
        groupUsers = client.getUsers(updatedGroup);
        System.out.println("there are " + groupUsers.getEntries().size() + " users in the group " + newGroup);
        printEntryContentSrc(groupUsers);
        printNewLine();
        
        printStep("remove the user " + newUser + " from the group " + newGroup);
        client.delete(groupUsers.getEntries().get(0));
        printHttpStatus();
        printNewLine();
        
        printStep("get the group " + newGroup + " member groups");
        Feed<RestObject> groupGroups = client.getGroups(updatedGroup);
        if(groupGroups.getEntries() == null) {
            System.out.println("there are 0 groups in the group " + newGroup);
        }
        printNewLine();

        String newSubGroup = "sub_group_" + Randoms.nextString(10);
        printStep("create the new group " + newSubGroup);
        RestObject createdSubGroup = client.createGroup(new PlainRestObject("group_name", newSubGroup));
        print(createdSubGroup, "group_name", "owner_name", "group_display_name");
        printNewLine();
        
        printStep("add the group " + newSubGroup + " to the group " + newGroup);
        client.addGroupToGroup(updatedGroup, createdSubGroup);
        printHttpStatus();
        printNewLine();
        
        printStep("get the group " + newGroup + " member groups");
        groupGroups = client.getGroups(updatedGroup);
        System.out.println("there are " + groupGroups.getEntries().size() + " groups in the group " + newGroup);
        printEntryContentSrc(groupGroups);
        printNewLine();
        
        printStep("remove the group " + newSubGroup + " from the group " + newGroup);
        client.delete(groupGroups.getEntries().get(0));
        printHttpStatus();
        printNewLine();
        
        printStep("delete user " + newUser);
        client.delete(updatedUser);
        printHttpStatus();        
        printNewLine();
        
        printStep("delete group " + newSubGroup);
        client.delete(createdSubGroup);
        printHttpStatus();        
        printNewLine();
        
        printStep("delete group " + newGroup);
        client.delete(updatedGroup);
        printHttpStatus();        
        printNewLine();
    }
}
