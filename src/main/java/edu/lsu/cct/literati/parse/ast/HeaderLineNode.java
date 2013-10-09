/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lsu.cct.literati.parse.ast;

/**
 *
 * @author jpeak5
 */
public class HeaderLineNode extends LineNode {
    public HeaderLineNode(String s){
        super(s);
    }
    
    public HeaderLineNode(String s, int lineNum) {
        super(s);
        this.lineNumber = lineNum;
    }
    
    @Override
    public String toString(int indentFactor){
        this.text = "<head>" + this.text + "</head>\n";
        return super.toString(indentFactor);
    }
    
}
