package expression;

import expression.exceptions.ComputingException;

import java.math.BigInteger;
import java.util.EnumSet;

public class CheckedMultiply extends Operation {
    public static EnumSet<Opers> left = EnumSet.of(Opers.ADD, Opers.SUB, Opers.LEFT, Opers.RIGHT);
    public static EnumSet<Opers> right = EnumSet.of(Opers.ADD, Opers.SUB, Opers.DIV, Opers.LEFT, Opers.RIGHT);
    public CheckedMultiply(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.MUL, left, right);
    }

    @Override
    public double op(double a, double b) {
        return a*b;
    }
    @Override
    public int op(int a, int b) {
        return Math.multiply(a, b);
    }
}
