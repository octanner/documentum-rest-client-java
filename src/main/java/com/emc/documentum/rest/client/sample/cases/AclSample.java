/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.client.util.Randoms;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.Permission;
import com.emc.documentum.rest.client.sample.model.PermissionSet;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Acl(s), Acl Associations, Permissions and Permission-Set")
@RestServiceVersion(7.3)
public class AclSample extends Sample {
    public void acls() {
        printStep("get all acls");
        Feed<RestObject> acls = client.getAcls();
        printEntryContentSrc(acls);
        printNewLine();

        printStep("get a single acl");
        RestObject acl = client.getAcl(acls.getEntries().get(0).getContentSrc());
        print(acl, "object_name", "owner_name", "acl_class", "description");
        printNewLine();

        printStep("create an acl");
        RestObject createdAcl = client.createAcl(new PlainRestObject("object_name", "test_acl" + Randoms.nextString(8), "description", "sample acl"));
        print(createdAcl, "object_name", "owner_name", "acl_class", "description");
        printNewLine();
        
        printStep("create objects with created acl " + createdAcl.getObjectName());
        RestObject tempCabinet = client.getCabinet("Temp");
        RestObject createdFolder = client.createFolder(tempCabinet, new PlainRestObject("object_name", "folder_with_acl", "acl_name", createdAcl.getObjectName(), "acl_domain", (String)createdAcl.getProperties().get("owner_name")));
        printHttpStatus();
        print(createdFolder);
        RestObject createdObject1 = client.createObject(tempCabinet, new PlainRestObject("object_name", "obj_with_acl_1", "acl_name", createdAcl.getObjectName(), "acl_domain", (String)createdAcl.getProperties().get("owner_name")));
        printHttpStatus();
        print(createdObject1);
        RestObject createdObject2 = client.createObject(tempCabinet, new PlainRestObject("object_name", "obj_with_acl_2", "acl_name", createdAcl.getObjectName(), "acl_domain", (String)createdAcl.getProperties().get("owner_name")));
        printHttpStatus();
        print(createdObject2);
        RestObject createdObject3 = client.createObject(tempCabinet, new PlainRestObject("object_name", "obj_with_acl_3", "acl_name", createdAcl.getObjectName(), "acl_domain", (String)createdAcl.getProperties().get("owner_name")));
        printHttpStatus();
        print(createdObject3);
        printNewLine();

        printStep("get acl " + createdAcl.getObjectName() + " associations");
        Feed<RestObject> associations = client.getAclAssociations(createdAcl);
        printEntryContentSrc(associations);
        printNewLine();
        
        printStep("delete the object and acl");
        client.delete(createdFolder);
        printHttpStatus();
        client.delete(createdObject1);
        printHttpStatus();
        client.delete(createdObject2);
        printHttpStatus();
        client.delete(createdObject3);
        printHttpStatus();
        client.delete(createdAcl);
        printHttpStatus();
        printNewLine();
    }
    
    public void permissions() {
        printStep("get current user permissions of the current user default folder");
        Permission permission = client.getPermission(client.getDefaultFolder());
        print(permission);
        printNewLine();
        
        printStep("get docu user permissions of the current user default folder");
        permission = client.getPermission(client.getDefaultFolder(), "accessor", "docu");
        print(permission);
        printNewLine();
        
        printStep("get current user permission set");
        PermissionSet permissionSet = client.getPermissionSet(client.getCurrentUser());
        print(permissionSet);
        printNewLine();

        printStep("get Temp cabinet permission set");
        permissionSet = client.getPermissionSet(client.getCabinet("Temp"));
        print(permissionSet);
        printNewLine();
    }
}
