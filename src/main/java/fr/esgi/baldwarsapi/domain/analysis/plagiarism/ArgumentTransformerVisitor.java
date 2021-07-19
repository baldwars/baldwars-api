package fr.esgi.baldwarsapi.domain.analysis.plagiarism;

import fr.esgi.baldwarsapi.antlr4.CBaseVisitor;
import fr.esgi.baldwarsapi.antlr4.CParser;

import java.util.ArrayList;
import java.util.List;

public class ArgumentTransformerVisitor extends CBaseVisitor<Object> {

    private final List<String> source;
    private final List<LocalizedElement> arguments;

    public ArgumentTransformerVisitor(List<String> source) {
        this.source = source;
        this.arguments = new ArrayList<>();
    }

    @Override public Object visitArgumentExpressionList(CParser.ArgumentExpressionListContext ctx) {
        var token = ctx.start;
        var value = ctx.parent.getChild(2).getText();
        var argument = new LocalizedElement(value, token.getLine(), ctx.parent.getText());

        arguments.add(argument);
        transform();

        return visitChildren(ctx);
    }

    private void transform() {
        for (var argument : arguments) {
            var row = source.get(argument.row - 1).replace(" ", "");
            source.remove(argument.row - 1);
            source.add(argument.row - 1, row.replace(argument.value, "$"));
        }
    }

    public List<String> getSource() {
        return source;
    }
}
