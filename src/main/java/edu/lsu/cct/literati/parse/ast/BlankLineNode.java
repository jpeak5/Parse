/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lsu.cct.literati.parse.ast;


/**
 *
 * @author jpeak5
 */
public class BlankLineNode extends LineNode{
    
    public int count;
    
    public BlankLineNode (String s, int lineNum){
        super(s, lineNum);
    }
    
//    public BlankLineNode(int count,int lineNum){
//        this.count = count;
//        this.lineNumber = lineNum;
//    }
    

}
