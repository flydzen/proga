package expression;

import expression.exceptions.ComputingException;

public class CheckedNegative implements UnOp {
    private Element arg;

    public CheckedNegative(Element arg){

        this.arg = arg;
    }

    @Override
    public double evaluate(double x) {
        return -arg.evaluate(x);
    }

    @Override
    public int evaluate(int x) {
        int value = arg.evaluate(x);
        if (value == Integer.MIN_VALUE)
            throw new ComputingException("Overflow on negation");
        return -value;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int value = arg.evaluate(x, y, z);
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
