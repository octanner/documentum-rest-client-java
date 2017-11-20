/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.RestObject;
import com.emc.documentum.rest.client.sample.model.plain.PlainRestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Folder(s) CRUD")
public class FolderCRUDSample extends Sample {
    public void crudFolder() {
        RestObject tempCabinet = client.getCabinet("Temp");
        
        printStep("create a folder under the Temp cabinet");
        RestObject newFolder = new PlainRestObject("object_name", "my_new_folder");
        RestObject createdFolder = client.createFolder(tempCabinet, newFolder);
        printHttpStatus();
        print(createdFolder);
        printNewLine();

        printStep("update the folder name");
        RestObject updateFolder = new PlainRestObject("object_name", "my_new_folder_updated");
        RestObject updatedFolder = client.update(createdFolder, updateFolder);
        printHttpStatus();
        print(updatedFolder);
        printNewLine();
        
        printStep("delete the created folder");
        client.delete(createdFolder);
        printHttpStatus();
        printNewLine();
        
        printStep("create a folder under the Temp cabinet");
        RestObject newFolder1 = new PlainRestObject("object_name", "my_new_folder1");
        RestObject createdFolder1 = client.createFolder(tempCabinet, newFolder1);
        printHttpStatus();
        print(createdFolder1);
        printNewLine();
        
        printStep("create a folder under the folder just created");
        RestObject newFolder2 = new PlainRestObject("object_name", "my_new_folder2");
        RestObject createdFolder2 = client.createFolder(createdFolder1, newFolder2);
        printHttpStatus();
        print(createdFolder2);
        printNewLine();
        
        printStep("delete the created folder with a folder in it");
        client.delete(createdFolder1, "del-non-empty", "true");
        printHttpStatus();
        printNewLine();
    }
}
