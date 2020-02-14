package expression.parser;

import expression.exceptions.ExpressionException;

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
        return data.charAt(--pos);
    }

    @Override
    public ExpressionException error(final String message) {
        return new ExpressionException(pos + ": " + message);
    }

}
