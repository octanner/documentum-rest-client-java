/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */

package com.emc.documentum.rest.client.sample.model;

import java.util.List;

public interface Preference extends Linkable {
    public String getClient();
    public String getOwner();
    public String getTitle();
    public String getSubject();
    public List<String> getKeywords();
    public String getCreationDate();
    public String getModifyDate();
    public String getPreference();
}
