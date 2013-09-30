/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lsu.cct.literati.parse.ast;

/**
 *
 * @author jpeak5
 */
public class TextNode {
    public String text;
    
    public TextNode(String text){
        this.text = text;
    }
    
    public String getText(){
        return this.text;
    }
    
}
