/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lsu.cct.literati.parse.ast;

/**
 *
 * @author jpeak5
 */
public class IndentNode extends Node {

    private String text;

    public IndentNode(int multiplier) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < multiplier; i++) {
            sb.append("   ");
        }
        this.text = sb.toString();
    }
    
    @Override
    public String toString(){
        return this.text;
    }
}
