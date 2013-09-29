/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parse;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

/**
 *
 * @author jpeak5
 */
@BuildParseTree
public class DDLParser extends BaseParser<Object> {

    protected static final char CROSSED_OUT = '\uffff';

    public Rule DataDefinition() {
        return OneOrMore(Statements());
    }

    Rule Statements() {
        return Sequence(
                Statement(),
                ';',
                EOI);
    }

    Rule Statement() {
        return Sequence(
                CreateOrDrop(), Space(), Entity(), Space(), Identifier());
    }

    Rule Entity() {
        return FirstOf(
                String("DATABASE"),
                String("TABLE"),
                String("VIEW")
                );
    }
    
    Rule CreateOrDrop() {
        return FirstOf("CREATE", "DROP");
    }

    //g
    Rule Space() {
        return OneOrMore(
                AnyOf(" \t"));
    }

    Rule Identifier() {
        return Sequence(IdentifierChars(), ZeroOrMore(IdentifierChars()));
    }

    Rule IdentifierChars() {
        return FirstOf(
                Number(),
                LC_Char(),
                UC_Char(),
                Underscore());
    }

    Rule Number() {
        return OneOrMore(
                Digit());
    }

    Rule Digit() {
        return CharRange('0', '9');
    }

    Rule UC_Char() {
        return OneOrMore(
                CharRange('A', 'Z'));
    }

    Rule LC_Char() {
        return OneOrMore(
                CharRange('a', 'z'));
    }

    Rule Underscore() {
        return OneOrMore(
                Ch('_'));
    }
}
