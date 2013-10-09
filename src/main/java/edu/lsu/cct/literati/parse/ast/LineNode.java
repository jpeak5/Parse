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
public class LineNode extends TextNode{

    public int lineNumber;
//    public String text;
    
    public LineNode(String s){
        super(s);
    }
    
    public LineNode(String s, int lineNum) {
        super(s);
        this.lineNumber = lineNum;
    }

    @Override
    public String toString(int indentFactor) {
        IndentNode ind = new IndentNode(indentFactor);
        return ind.toString() + this.getLineBreak(this.lineNumber) + this.text;
    }

    public String getLineBreak(int lineNum) {
        return String.format("<lb n=%d/>", this.lineNumber);
    }
}
