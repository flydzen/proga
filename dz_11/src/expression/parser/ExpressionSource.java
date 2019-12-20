package expression.parser;

public interface ExpressionSource {
    boolean hasNext();
    char next();
    char getChar();
    char prev();
    ExpressionException error(final String message);
}
