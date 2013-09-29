/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lsu.cct.literati.parse;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

/**
 *
 * @author jpeak5
 */
@BuildParseTree
public class GoogleBooksGrammar extends BaseParser<Object> {

    protected static final char CROSSED_OUT = '\uffff';

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
        return AnyOf("•—■");
    }
    
    Rule Punctuation() {
        return AnyOf("=+-,.;':\"()&%$#@!");
    }
    
    Rule Alpha() {
        return FirstOf(CharRange('a','z'), CharRange('A','Z'));
    }

    Rule Lines() {
        return OneOrMore(Line(), push(match()));
    }

    Rule Line() {
        return FirstOf(BlankLine(),NonBlankLine());
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
