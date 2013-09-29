/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.lsu.cct.literati.parse;

import java.io.FileWriter;
import java.text.Normalizer;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.common.StringUtils;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;
import org.parboiled.support.ParsingResult;
import org.parboiled.support.ValueStack;

/**
 *
 * @author jpeak5
 */
public class App {

    public static void main(String[] args) throws IOException {
        String input = normalizeInputLineEndings(
                readFile(args[0], Charset.forName("UTF-8")));

        GoogleBooksGrammar parser = Parboiled.createParser(GoogleBooksGrammar.class);
        ParsingResult<String> result = new RecoveringParseRunner(parser.DataDefinition()).run(input);
        assert result.parseTreeRoot != null;
//        System.out.println(input + " = " + result.parseTreeRoot.getValue() + '\n');
        System.out.println(printNodeTree(result) + '\n');

        System.out.printf("\n\nGot %d Lines...value stack type is %s\n", result.valueStack.size(), result.valueStack.pop().getClass());

        TeiBuilder tei = new TeiBuilder(result.valueStack);
        tei.WriteFile("tmp.txt", tei.BuildOutput());

        if (!result.matched) {
            System.out.println(StringUtils.join(result.parseErrors, "---\n"));
        }
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }

    static String normalizeInputLineEndings(String input) {
        String normal;
        normal = input.replaceAll("\\r\\n", "\n");
        normal = normal.replaceAll("\\n\\r", "\n");
        normal = normal.replaceAll("\\r", "\n");
        return normal;
    }
}

class TeiBuilder {

    public ArrayList<String> lines;
    public ValueStack<String> input;

    public TeiBuilder(ValueStack<String> input) {
        this.input = input;
        this.lines = new ArrayList<String>();;
    }

    private void reverseStackIntoLines() {
        while (this.input.iterator().hasNext()) {
            assert lines != null;

            String s = this.input.pop().toString();
            assert s != null;

            this.lines.add(0, s);
        }
    }

    public StringBuilder BuildOutput() {
        StringBuilder sb = new StringBuilder("");
        this.reverseStackIntoLines();
        
        for (String s : this.lines) {
            sb.append(s);
        }

        return sb;
    }

    public void WriteFile(String filename, StringBuilder output) throws IOException {
        FileWriter fw = new FileWriter(filename);
        fw.write(output.toString());
        fw.flush();
        fw.close();
    }
}
