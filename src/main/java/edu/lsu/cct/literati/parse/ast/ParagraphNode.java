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
public class ParagraphNode extends ParentNode<LineNode> {

    public ParagraphNode(ArrayList<LineNode> children){
        super(children);
    }
    
    @Override
    public String toString(int indentFactor) {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>\n");
        indentFactor++;
        sb.append(super.toString(indentFactor));
        sb.append("</p>\n");
        return sb.toString();
    }
}
