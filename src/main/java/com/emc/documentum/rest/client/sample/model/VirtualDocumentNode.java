/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model;

import java.util.List;

public interface VirtualDocumentNode extends Linkable {
    public List<String> getAvailableVersions();
    public String getBinding();
    public int getChildCount();
    public String getChronicleId();
    public int getCopyBehavior();
    public boolean isFollowAssembly();
    public String getNodeId();
    public String getLateBindingValue();
    public boolean isOverrideLateBinding();
    public String getVdmNumber();
    public boolean isAreChildrenCompound();
    public boolean isCanBeRemoved();
    public boolean isCanBeRestructured();
    public boolean isBindingBroken();
    public boolean isCompound();
    public boolean isFromAssembly();
    public boolean isStructurallyModified();
    public boolean isVirtualDocument();
    public String getSelectedObjectName();
    public String getSelectedObjectId();
}
