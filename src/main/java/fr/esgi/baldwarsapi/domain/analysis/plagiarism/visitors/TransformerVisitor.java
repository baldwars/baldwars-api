package fr.esgi.baldwarsapi.domain.analysis.plagiarism.visitors;

import fr.esgi.baldwarsapi.antlr4.CLexer;
import fr.esgi.baldwarsapi.antlr4.CParser;
import lombok.SneakyThrows;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.antlr.v4.runtime.CharStreams.fromString;

public class TransformerVisitor {

    private final VariableTransformerVisitor variableVisitor;
    private final ParserRuleContext context;

    @SneakyThrows
    public TransformerVisitor(String source) {
        var bloc = "{" + source + "}";
        var inputStream = fromString(bloc);
        var lexer = new CLexer(inputStream);
        var tokenStream = new CommonTokenStream(lexer);
        var parser = new CParser(tokenStream);
        context = parser.blockItem();

        var content = new ArrayList<>(Arrays.asList(source.split("\n")));
        variableVisitor = new VariableTransformerVisitor(content);
    }

    public String visit() {
        variableVisitor.visit(context);
        var argumentVisitor = new ArgumentTransformerVisitor(variableVisitor.getSource());
        argumentVisitor.visit(context);

        return String.join(" ", argumentVisitor.getSource());
    }
}
