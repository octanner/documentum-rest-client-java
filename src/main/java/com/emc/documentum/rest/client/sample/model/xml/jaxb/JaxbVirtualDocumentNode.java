/*
 * Copyright (c) 2018. OPEN TEXT Corporation. All Rights Reserved.
 */
package com.emc.documentum.rest.client.sample.model.xml.jaxb;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.emc.documentum.rest.client.sample.client.util.Equals;
import com.emc.documentum.rest.client.sample.model.VirtualDocumentNode;

@XmlRootElement(name="virtual-document-node")
public class JaxbVirtualDocumentNode extends JaxbDmLinkableBase implements VirtualDocumentNode {
    private boolean areChildrenCompound;
    private List<String> availableVersions;
    private String binding;
    private boolean canBeRemoved;
    private boolean canBeRestructured;
    private int childCount;
    private String chronicleId;
    private int copyBehavior;
    private boolean followAssembly;
    private String nodeId;
    private boolean isBindingBroken;
    private boolean isCompound;
    private boolean isFromAssembly;
    private boolean isStructurallyModified;
    private boolean isVirtualDocument;
    private String lateBindingValue;
    private boolean overrideLateBinding;
    private String vdmNumber;
    private String selectedObjectName;
    private String selectedObjectId;
    
    @Override
    @XmlElementWrapper(name="available-versions")
    @XmlElement(name = "item")
    public List<String> getAvailableVersions() {
        return availableVersions;
    }

    public void setAvailableVersions(List<String> availableVersions) {
        this.availableVersions = availableVersions;
    }

    @Override
    @XmlElement(name="binding")
    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    @Override
    @XmlElement(name="child-count")
    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }

    @Override
    @XmlElement(name="chronicle-id")
    public String getChronicleId() {
        return chronicleId;
    }

    public void setChronicleId(String chronicleId) {
        this.chronicleId = chronicleId;
    }

    @Override
    @XmlElement(name="copy-behavior")
    public int getCopyBehavior() {
        return copyBehavior;
    }

    public void setCopyBehavior(int copyBehavior) {
        this.copyBehavior = copyBehavior;
    }

    @Override
    @XmlElement(name="follow-assembly")
    public boolean isFollowAssembly() {
        return followAssembly;
    }

    public void setFollowAssembly(boolean followAssembly) {
        this.followAssembly = followAssembly;
    }

    @Override
    @XmlElement(name="node-id")
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String id) {
        this.nodeId = id;
    }

    @Override
    @XmlElement(name="late-binding-value")
    public String getLateBindingValue() {
        return lateBindingValue;
    }

    public void setLateBindingValue(String lateBindingValue) {
        this.lateBindingValue = lateBindingValue;
    }

    @Override
    @XmlElement(name="override-late-binding")
    public boolean isOverrideLateBinding() {
        return overrideLateBinding;
    }

    public void setOverrideLateBinding(boolean overrideLateBinding) {
        this.overrideLateBinding = overrideLateBinding;
    }

    @Override
    @XmlElement(name="vdm-number")
    public String getVdmNumber() {
        return vdmNumber;
    }

    public void setVdmNumber(String vdmNumber) {
        this.vdmNumber = vdmNumber;
    }

    @Override
    @XmlElement(name="are-children-compound")
    public boolean isAreChildrenCompound() {
        return areChildrenCompound;
    }

    public void setAreChildrenCompound(boolean areChildrenCompound) {
        this.areChildrenCompound = areChildrenCompound;
    }

    @Override
    @XmlElement(name="can-be-removed")
    public boolean isCanBeRemoved() {
        return canBeRemoved;
    }

    public void setCanBeRemoved(boolean canBeRemoved) {
        this.canBeRemoved = canBeRemoved;
    }

    @Override
    @XmlElement(name="can-be-restructured")
    public boolean isCanBeRestructured() {
        return canBeRestructured;
    }

    public void setCanBeRestructured(boolean canBeRestructured) {
        this.canBeRestructured = canBeRestructured;
    }

    @Override
    @XmlElement(name="is-binding-broken")
    public boolean isBindingBroken() {
        return isBindingBroken;
    }

    public void setBindingBroken(boolean isBindingBroken) {
        this.isBindingBroken = isBindingBroken;
    }

    @Override
    @XmlElement(name="is-compound")
    public boolean isCompound() {
        return isCompound;
    }

    public void setCompound(boolean isCompound) {
        this.isCompound = isCompound;
    }

    @Override
    @XmlElement(name="is-from-assembly")
    public boolean isFromAssembly() {
        return isFromAssembly;
    }

    public void setFromAssembly(boolean isFromAssembly) {
        this.isFromAssembly = isFromAssembly;
    }

    @Override
    @XmlElement(name="is-structurally-modified")
    public boolean isStructurallyModified() {
        return isStructurallyModified;
    }

    public void setStructurallyModified(boolean isStructurallyModified) {
        this.isStructurallyModified = isStructurallyModified;
    }

    @Override
    @XmlElement(name="is-virtual-document")
    public boolean isVirtualDocument() {
        return isVirtualDocument;
    }

    public void setVirtualDocument(boolean isVirtualDocument) {
        this.isVirtualDocument = isVirtualDocument;
    }

    @Override
    @XmlElement(name="selected-object-name")
    public String getSelectedObjectName() {
        return selectedObjectName;
    }

    public void setSelectedObjectName(String selectedObjectName) {
        this.selectedObjectName = selectedObjectName;
    }

    @Override
    @XmlElement(name="selected-object-id")
    public String getSelectedObjectId() {
        return selectedObjectId;
    }

    public void setSelectedObjectId(String selectedObjectId) {
        this.selectedObjectId = selectedObjectId;
    }
    
    @Override
    public boolean equals(Object obj) {
        JaxbVirtualDocumentNode that = (JaxbVirtualDocumentNode) obj;
        return Equals.equal(areChildrenCompound, that.areChildrenCompound)
                && Equals.equal(availableVersions, that.availableVersions)
                && Equals.equal(binding, that.binding)
                && Equals.equal(canBeRemoved, that.canBeRemoved)
                && Equals.equal(canBeRestructured, that.canBeRestructured)
                && Equals.equal(childCount, that.childCount)
                && Equals.equal(chronicleId, that.chronicleId)
                && Equals.equal(copyBehavior, that.copyBehavior)
                && Equals.equal(followAssembly, that.followAssembly)
                && Equals.equal(nodeId, that.nodeId)
                && Equals.equal(isBindingBroken, that.isBindingBroken)
                && Equals.equal(isCompound, that.isCompound)
                && Equals.equal(isFromAssembly, that.isFromAssembly)
                && Equals.equal(isStructurallyModified, that.isStructurallyModified)
                && Equals.equal(isVirtualDocument, that.isVirtualDocument)
                && Equals.equal(lateBindingValue, that.lateBindingValue)
                && Equals.equal(overrideLateBinding, that.overrideLateBinding)
                && Equals.equal(vdmNumber, that.vdmNumber)
                && Equals.equal(selectedObjectName, that.selectedObjectName)
                && Equals.equal(selectedObjectId, that.selectedObjectId)
                && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeId, vdmNumber, selectedObjectId);
    }
}
