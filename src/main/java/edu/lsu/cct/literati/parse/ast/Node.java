/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lsu.cct.literati.parse.ast;

import java.util.ArrayList;

/**
 *
 * @author jpeak5
 */
public class Node<T> {
    public ArrayList<T> children;
    
    public <T> Node() {
        
    }
    
    public ArrayList<T> getChildren(){
        return this.children;
    }
}
