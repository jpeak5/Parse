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
public class ParentNode<T extends Node> extends Node{
    
    public ArrayList<T> children;
    
    public ParentNode(ArrayList<T> children){
        this.children = children;
    }
    
    public ArrayList<T> getChildren(){
        return this.children;
    }
    
    public String toString(int indentFactor){
        StringBuilder sb = new StringBuilder();
//        IndentNode indent = new IndentNode(indentFactor);
        for (Node n: this.children){
//            sb.append(indent.toString());
            sb.append(n.toString(indentFactor));
        }
        return sb.toString();
    }
}
