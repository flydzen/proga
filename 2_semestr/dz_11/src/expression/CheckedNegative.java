package expression;

import expression.exceptions.ComputingException;

public class CheckedNegative<T extends Number> implements UnOp<T> {
    private Element<T> arg;

    public CheckedNegative(Element<T> arg){
        this.arg = arg;
    }

    @Override
    public T evaluate(T x) {
        T value = arg.evaluate(x);
        if (value == Integer.MIN_VALUE)
            throw new ComputingException("Overflow on negation");
        return -value;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        T value = arg.evaluate(x, y, z);
        if (value == Integer.MIN_VALUE)
            throw new ComputingException("Overflow on negation");
        return -value;
    }
    @Override
    public String toMiniString() {
        return "- " + arg.toMiniString();
    }

    @Override
    public String toString() {
        return "-" + arg;
    }
}
