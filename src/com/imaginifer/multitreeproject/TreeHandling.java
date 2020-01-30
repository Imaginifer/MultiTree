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
public class TreeHandling {
    
    private final TreeMap<Integer, TreeItem> treeItems;
    private Set<Integer> finished;
    
    public TreeHandling() {
        treeItems = new TreeMap<>();
        finished = new HashSet<>();
    }
    
    private void addItem(int parentIdent, String stringToStore){
        int ident = treeItems.isEmpty() ? 1 : treeItems.lastKey()+1;
        treeItems.put(ident, new TreeItem(ident, stringToStore, parentIdent));
        if(parentIdent > 0){
           treeItems.get(parentIdent).addChild(ident);
        }
    }
    
    public void buildExampleTree(){
        addItem(0, "Qwertzuiop");
        addItem(0, "Asdfghjkl");
        addItem(0, "Yxcvbnm");
        addItem(1, "A nested item");
        addItem(2, "Yet another nested item");
        addItem(2, "And another");
        addItem(4, "A doubly nested item");
        addItem(4, "Another");
        addItem(8, "Triple nesting");
        addItem(8, "");
        addItem(8, "Another of this");
        addItem(0, "1234567890");
    }
    
    public boolean validIdent(int ident){
        return treeItems.containsKey(ident);
    }
    
    public void expandOrCollapseItem(int ident){
        treeItems.get(ident).setExpanded(!treeItems.get(ident).isExpanded());
    }
    
    public void selectOrDeselectItem(int ident){
        SelectionState state = treeItems.get(ident).getSelectionState();
        if(state == SelectionState.NOT_SELECTED){
            state = treeItems.get(ident).isExpanded() ? SelectionState.SELECTED :
                    SelectionState.RECURSIVELY_SELECTED;
        } else {
            state = SelectionState.NOT_SELECTED;
        }
        treeItems.get(ident).setSelectionState(state);
        if(!treeItems.get(ident).isExpanded() && treeItems.get(ident).isParent()){
            recursiveSelection(ident, state);
        }
    }
    
    private void recursiveSelection(int ident, SelectionState state){
        TreeSet<Integer> children = treeItems.get(ident).getChildrenIdents();
        for (Integer i : children) {
            treeItems.get(i).setSelectionState(state);
            if (treeItems.get(i).isParent()){
                recursiveSelection(i, state);
            }
        }
    }
    
    public List<String> displayTree(){
        finished.clear();
        Set<Integer> items = treeItems.navigableKeySet();
        List<String> results = new ArrayList<>();
        for (Integer i : items) {
            if(!finished.contains(i) && treeItems.get(i).getParentIdent() == 0){
                results.add(displayItem(i, 0));
                finished.add(i);
                if(hasSubtree(i)){
                    results.addAll(recursiveDisplay(i, 0));
                }
            }
        }
        return results;
    }
    
    private List<String> recursiveDisplay(int ident, int parentDepth){
        List<String> res = new ArrayList<>();
        int depth = parentDepth+1;
        TreeSet<Integer> children = treeItems.get(ident).getChildrenIdents();
            for (Integer i : children) {
                res.add(displayItem(i, depth));
                finished.add(i);
                if(hasSubtree(i)){
                    res.addAll(recursiveDisplay(i, depth));
                }
            }
        return res;
    }
    
    private boolean hasSubtree(int ident){
        return treeItems.get(ident).isExpanded() && treeItems.get(ident).isParent();
    }
    
    private String displayItem(int ident, int depth){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(" ");
        }
        sb.append(ident);
        sb.append(". ");
        sb.append(treeItems.get(ident).getStoredString());
        sb.append(!treeItems.get(ident).isExpanded() && treeItems.get(ident).isParent() ?
                " ("+treeItems.get(ident).getChildrenIdents().size()+")" : "");
        sb.append(treeItems.get(ident).getSelectionState() == SelectionState.NOT_SELECTED ? "" : " selected");
        return sb.toString();
    }
    
    
    
    
}
