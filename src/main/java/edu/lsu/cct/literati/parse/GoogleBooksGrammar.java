/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lsu.cct.literati.parse;

import edu.lsu.cct.literati.parse.ast.*;
import java.util.ArrayList;
import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.ValueStack;

/**
 *
 * @author jpeak5
 */
@BuildParseTree
public class GoogleBooksGrammar extends BaseParser<Object> {

    protected static final char CROSSED_OUT = '\uffff';
    
    private int lineCount =0;

    public Rule DataDefinition() {
        return Sequence(
                Lines(),
                EOI);
    }

    Rule Pages() {
        return Sequence(Page(), FirstOf(Pages(), EOI));
    }

    Rule Page() {
        return Sequence(Optional(Header()), OneOrMore(Line()));
    }

    Rule Header() {
        return (FirstOf(
                LHPageNumberHead(),
                RHPageNumberHead()));
    }

    Rule RHPageNumberHead() {
        return NOTHING;
    }

    Rule LHPageNumberHead() {
        return Sequence(
                OneOrMore(Digit()),
                OneOrMore(
                FirstOf(Space(), Newline())),
                OneOrMore(Word()),
                Newline());
    }

    Rule Digit() {
        return CharRange('0', '9');
    }

    Rule Word() {
            return OneOrMore(
                    Alpha(),
                    WordSpecialChars(),
                    TestNot(Space())
                    );
    }
    
    Rule LegalChars() {
        return FirstOf(
                Alpha(),
                Punctuation(),
                Space(),
                Digit(),
                JunkChars()
                );
    }
    
    Rule WordSpecialChars() {
        return AnyOf("'-");
    }
    
    Rule JunkChars(){
        return AnyOf("•—■^»«„*");
    }
    
    Rule Punctuation() {
        return AnyOf("=+-,/.;':\"()&%$#@!");
    }
    
    Rule Alpha() {
        return FirstOf(CharRange('a','z'), CharRange('A','Z'));
    }

    Rule Lines() {
        return OneOrMore(Line());
    }


    
    Rule Line() {
        return FirstOf(
                Sequence(BlankLine(), push(addBlankLine())),
                Sequence(NonBlankLine(), push(guessLineType(match()))
                ));
    }
    
    ParentNode guessLineType(String match) {

        int upper = 0;
        int lower = 0;
        int other = 0;
        int numbr = 0;
            
        for(int i=0; i<match.length(); i++){
            Character test = match.charAt(i);

            if(test.toString().matches("[A-Z]")){
                upper++;
            }else if(test.toString().matches("[a-z]")){
                lower++;
            }else if(test.toString().matches("[0-9]")){
                numbr++;
            }else{
                other++;
            }
        }
        System.out.printf("UC %d | lc %d | num %d\n", upper, lower, numbr);
        boolean isnumeric = numbr > upper + lower + other;
        boolean isHeader  = (float)upper/(lower) > .5; //arbitrary mult
        
        return isHeader ? consolidateConsequetiveHeaderNodes(match) : consolidateLines(match);
    }
    

    HeaderNode consolidateConsequetiveHeaderNodes(String match){
        ValueStack stack = getContext().getValueStack();
        ArrayList<LineNode> hlns = new ArrayList<>();
        
        if(!stack.isEmpty()) {
                if(stack.peek() instanceof BlankLineNode && stack.peek(1) instanceof HeaderNode) {
                    
                    //this is gross
                    Object o0 = stack.pop();
                    hlns.add(blankOrHeader(o0));
                    
                    Object o1 = stack.pop();
                    hlns.add(blankOrHeader(o1));
                    
                }else if(stack.peek() instanceof HeaderNode){
                    hlns.add((HeaderLineNode) stack.pop());
                }
        }
        hlns.add(new HeaderLineNode(match, lineCount));
        return new HeaderNode(hlns);
    }
    
    public LineNode blankOrHeader(Object o){
        return o instanceof HeaderLineNode ? (HeaderLineNode) o : (BlankLineNode) o;
    }
    
    BlankLineNode addBlankLine(){
        return new BlankLineNode("\n", this.lineCount++);
    }
    
    ParagraphNode consolidateLines(String match){
        LineNode incoming = new LineNode(match, this.lineCount++);
        ValueStack stack  = getContext().getValueStack();
        
        if(!stack.isEmpty() && stack.peek() instanceof ParagraphNode){
            ParagraphNode p = (ParagraphNode)stack.pop();
            p.children.add(incoming);
            return p;
        }
        ArrayList<LineNode> lns = new ArrayList<>();
        lns.add(incoming);
        return new ParagraphNode(lns);
    }

    Rule Space() {
        return AnyOf(" \t");
    }

    public Rule NotNewline() {
        return TestNot(AnyOf("\r\n"));
    }

    public Rule Newline() {
        return FirstOf('\n', Sequence('\r', Optional('\n')));
    }
    
    public Rule NonBlankLine() {
        return Sequence(
                OneOrMore(LegalChars()),
                Newline()
                );
    }
    
    public Rule BlankLine() {
        return Sequence(
                ZeroOrMore(Space()),
                Newline()
                );
    }
}
