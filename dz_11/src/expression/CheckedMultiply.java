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
        BigInteger bigInteger = BigInteger.valueOf(a).multiply(BigInteger.valueOf(b));
        if (bigInteger.compareTo(BigInteger.valueOf(Integer.MAX_VALUE))>0)
            throw new ComputingException("Overflow on multiplication");
        if (bigInteger.compareTo(BigInteger.valueOf(Integer.MIN_VALUE))<0)
            throw new ComputingException("Overflow on multiplication");
        return a*b;
    }
}
