package expression.exceptions;

public class ArgumentException extends OverflowException {
    public ArgumentException(final String message) {
        super(message);
    }
}