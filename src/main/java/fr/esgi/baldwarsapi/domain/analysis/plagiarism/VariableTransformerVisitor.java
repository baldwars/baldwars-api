package fr.esgi.baldwarsapi.domain.analysis.plagiarism;

import fr.esgi.baldwarsapi.antlr4.CBaseVisitor;
import fr.esgi.baldwarsapi.antlr4.CParser;

import java.util.ArrayList;
import java.util.List;

public class VariableTransformerVisitor extends CBaseVisitor<Object> {

    private final List<String> source;
    private final List<LocalizedElement> variables;

    public VariableTransformerVisitor(List<String> source) {
        this.source = source;
        this.variables = new ArrayList<>();
    }

    @Override public Object visitInitDeclarator(CParser.InitDeclaratorContext ctx) {
        var initializer = ctx.initializer().getText();
        var token = tokenize(initializer);
        var variable = new LocalizedElement(token, ctx.start.getLine(), ctx.getText());

        variables.add(variable);
        transform();

        return visitChildren(ctx);
    }

    private String tokenize(String input) {
        var word = "[a-zA-Z0-9_]+";
        var binaryOperator = "([-+/*])";

        String WITH_DELIMITER = "((?<=%1$s)|(?=%1$s))";
        var elements = input.split(String.format(WITH_DELIMITER, binaryOperator));

        StringBuilder s = new StringBuilder();
        for (var element : elements) {
            try {
                Integer.parseInt(element);
            } catch (NumberFormatException e) {
                if (element.matches(word)) {
                    element = element.replace(element, "$");
                }
            }
            s.append(String.join("", element));
        }

        return "$=" + s;
    }

    private void transform() {
        for (var variable : variables) {
            var row = source.get(variable.row - 1).replace(" ", "");
            source.remove(variable.row - 1);
            source.add(variable.row - 1, row.replace(variable.parent, variable.value));
        }
    }

    public List<String> getSource() {
        return source;
    }
}
