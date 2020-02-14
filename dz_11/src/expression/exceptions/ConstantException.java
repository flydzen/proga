package expression.exceptions;

public class ConstantException extends OverflowException {
    public ConstantException(final String message) {
        super(message);
    }
}