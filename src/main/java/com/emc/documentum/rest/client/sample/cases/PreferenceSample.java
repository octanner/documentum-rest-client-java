/*
 * Copyright (c) 2016. EMC Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.cases;

import java.util.Arrays;

import com.emc.documentum.rest.client.sample.client.annotation.RestServiceSample;
import com.emc.documentum.rest.client.sample.client.annotation.RestServiceVersion;
import com.emc.documentum.rest.client.sample.model.Feed;
import com.emc.documentum.rest.client.sample.model.Preference;
import com.emc.documentum.rest.client.sample.model.plain.PlainPreference;

import static com.emc.documentum.rest.client.sample.client.util.Debug.print;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printEntryContentSrc;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printNewLine;
import static com.emc.documentum.rest.client.sample.client.util.Debug.printStep;

@RestServiceSample("User Preference(s)")
@RestServiceVersion(7.3)
public class PreferenceSample extends Sample {
    public void preference() {
        printStep("create an user preference");
        PlainPreference plainPreference = new PlainPreference();
        plainPreference.setClient("client code");
        plainPreference.setKeywords(Arrays.asList("key1", "key2"));
        plainPreference.setSubject("preference subject");
        plainPreference.setTitle("preference title");
        plainPreference.setPreference("any preference string can be set here");
        Preference createdPreference = client.createPreference(plainPreference);
        print(createdPreference);
        printNewLine();
        
        printStep("get all preferences");
        Feed<Preference> prefenreces = client.getPreferences();
        printEntryContentSrc(prefenreces);
        printNewLine();

        printStep("get a single prefenrece");
        Preference preference = client.getPreference(prefenreces.getEntries().get(0).getContentSrc());
        print(preference);
        printNewLine();

        printStep("udpate prefenrece");
        plainPreference = new PlainPreference();
        plainPreference.setPreference("the new updated user prefenrece string");
        Preference updatedPreference = client.updatePreference(createdPreference, plainPreference);
        print(updatedPreference);
        printNewLine();
        
        printStep("delete a prefenrece");
        client.delete(updatedPreference);
        printHttpStatus();
        printNewLine();
    }
}
