package com.bank.recommendationservice.model.engine;

public class FamilyProfile {

    private boolean hasSpouse;
    private boolean hasChildren;
    private boolean hasDependentParents;

    public boolean isHasSpouse() { return hasSpouse; }
    public void setHasSpouse(boolean hasSpouse) { this.hasSpouse = hasSpouse; }

    public boolean isHasChildren() { return hasChildren; }
    public void setHasChildren(boolean hasChildren) { this.hasChildren = hasChildren; }

    public boolean isHasDependentParents() { return hasDependentParents; }
    public void setHasDependentParents(boolean hasDependentParents) {
        this.hasDependentParents = hasDependentParents;
    }
}
