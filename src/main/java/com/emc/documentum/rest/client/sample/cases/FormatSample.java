/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.RestObject;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("Format(s)")
public class FormatSample extends Sample {
    public void format() {
        printStep("get all formats");
        Feed<RestObject> formats = client.getFormats();
        printEntryContentSrc(formats);
        printNewLine();

        printStep("get a single format");
        RestObject format = client.getFormat(formats.getEntries().get(0).getContentSrc());
        print(format, "name", "description", "dos_extension");
        printNewLine();
    }
}
