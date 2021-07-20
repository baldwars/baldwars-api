package fr.esgi.baldwarsapi.domain.analysis.plagiarism.visitors;

import fr.esgi.baldwarsapi.antlr4.CLexer;
import fr.esgi.baldwarsapi.antlr4.CParser;
import lombok.SneakyThrows;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.antlr.v4.runtime.CharStreams.fromString;

public class TransformerVisitor {

    private final VariableTransformerVisitor variableVisitor;
    private final ParserRuleContext context;

    @SneakyThrows
    public TransformerVisitor(String source) {
        try {
            var inputStream = fromString(source);
            var lexer = new CLexer(inputStream);
            var tokenStream = new CommonTokenStream(lexer);
            var parser = new CParser(tokenStream);
            context = parser.blockItem();

            var path = Paths.get(source);
            var content = Files.readAllLines(path);

            variableVisitor = new VariableTransformerVisitor(content);

        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    public String visit() {
        variableVisitor.visit(context);
        var argumentVisitor = new ArgumentTransformerVisitor(variableVisitor.getSource());
        argumentVisitor.visit(context);

        return String.join(" ", argumentVisitor.getSource());
    }
}
