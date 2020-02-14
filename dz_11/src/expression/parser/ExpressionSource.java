package expression.parser;

import expression.exceptions.ExpressionException;

public interface ExpressionSource {
    boolean hasNext();
    char next();
    char getChar();
    char prev();
    ExpressionException error(final String message);
}
