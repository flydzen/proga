package expression.exceptions;

public class ComputingException extends OverflowException {
    public ComputingException(final String message) {
        super(message);
    }
}