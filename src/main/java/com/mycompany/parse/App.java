package com.mycompany.parse;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.common.StringUtils;
import static org.parboiled.support.ParseTreeUtils.printNodeTree;
import org.parboiled.support.ParsingResult;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws IOException {
        String input = readFile(args[0], Charset.forName("UTF-8"));
//        System.out.println(input);
        
        DDLParser parser = Parboiled.createParser(DDLParser.class);
        ParsingResult<?> result = new RecoveringParseRunner(parser.DataDefinition()).run(input);

        System.out.println(input + " = " + result.parseTreeRoot.getValue() + '\n');
        System.out.println(printNodeTree(result) + '\n');

        if (!result.matched) {
            System.out.println(StringUtils.join(result.parseErrors, "---\n"));
        }
    }

    static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
