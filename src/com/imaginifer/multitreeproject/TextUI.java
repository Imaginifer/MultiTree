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
public class TextUI {
    
    private final TreeHandling treeHandling;
    
    public TextUI(){
        treeHandling = new TreeHandling();
    }
    
    public void runProgram(){
        treeHandling.buildExampleTree();
        boolean shouldRun = true;
        do {            
           shouldRun = displayTree(); 
        } while (shouldRun);
    }
    
    private boolean displayTree(){
        Scanner sc = new Scanner(System.in);
        List<String> items = treeHandling.displayTree();
        items.forEach(item -> System.out.print("\n"+item));
        System.out.print("\nTo expand or select an item, type 'expand' or 'select' "
                + "and its ident number, e.g. expand9\nSelecting or deselecting "
                + "unexpanded items recursively applies this to all nested items.\n"
                + "To quit, type anything else.\n");
        String response = sc.nextLine();
        return processResponse(response) || confirmExit();
    }
    
    private boolean processResponse(String response){
        if(response.length()>6 && (response.startsWith("expand") || response.startsWith("select"))){
            int ident = 0;
            try {
                ident = Integer.parseInt(response.substring(6));
            } catch (NumberFormatException e) {
                return false;
            }
            if(!treeHandling.validIdent(ident)){
                return false;
            }
            if(response.startsWith("expand")){
                treeHandling.expandOrCollapseItem(ident);
            } else {
                treeHandling.selectOrDeselectItem(ident);
            }
            return true;
        }
        return false;
    }
    
    private boolean confirmExit(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Are you sure you want to exit? y/n");
        String s = sc.nextLine();
        return s.equalsIgnoreCase("n");
    }
    
    
    
}
