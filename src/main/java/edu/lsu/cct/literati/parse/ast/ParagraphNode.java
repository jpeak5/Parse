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
public class ParagraphNode extends Node {

    public ParagraphNode(ArrayList<LineNode> lines){
        this.children = lines;
    }
}
