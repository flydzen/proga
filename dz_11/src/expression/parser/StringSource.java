package expression.parser;

import expression.exceptions.ExpressionException;
import expression.exceptions.ParsingException;

public class StringSource implements ExpressionSource {
    private final String data;
    protected int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }
    public char getChar() {return data.charAt(pos);}
    @Override
    public char next() {
        return data.charAt(pos++);
    }
    @Override
    public char prev() {
        if (pos == 0) {
            throw new ParsingException("Parsing exception");
        }
        return data.charAt(pos-1);
    }

    @Override
    public ExpressionException error(final String message) {
        return new ExpressionException(pos + ": " + message);
    }

}
