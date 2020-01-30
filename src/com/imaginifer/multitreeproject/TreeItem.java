/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imaginifer.multitreeproject;

import java.util.*;

/**
 *
 * @author imaginifer
 */
public class TreeItem {
    public static final int MAXIMUM_STRING_SIZE = 256;
    private final int ident;
    private final int parentIdent;
    private String storedString;
    private boolean expanded;
    private SelectionState selectionState;
    private final TreeSet<Integer> childrenIdents;
    
    public TreeItem(int ident, String stringToStore, int parentIdent){
        this.ident = ident;
        this.parentIdent = parentIdent;
        this.selectionState = SelectionState.NOT_SELECTED;
        this.expanded = false;
        childrenIdents = new TreeSet<>();
        this.storedString = stringToStore.getBytes().length > MAXIMUM_STRING_SIZE ?
                stringToStore.substring(0, MAXIMUM_STRING_SIZE/2) : stringToStore;
    }
    
    public int getIdent(){
        return ident;
    }

    public String getStoredString() {
        return storedString;
    }

    public void setStoredString(String storedString) {
        this.storedString = storedString;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = isParent() ? expanded : false;
    }

    public SelectionState getSelectionState() {
        return selectionState;
    }

    public void setSelectionState(SelectionState selectionState) {
        this.selectionState = selectionState;
    }

    public TreeSet<Integer> getChildrenIdents() {
        return childrenIdents;
    }
    
    public void addChild(int childIdent){
        childrenIdents.add(childIdent);
    }
    
    public boolean isParent(){
        return !childrenIdents.isEmpty();
    }
    
    public int getParentIdent(){
        return this.parentIdent;
    }
    
    
}
