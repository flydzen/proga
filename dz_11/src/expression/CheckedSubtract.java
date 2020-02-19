package expression;

import expression.exceptions.ComputingException;

import java.util.EnumSet;

public class CheckedSubtract extends Operation {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.of(Opers.ADD, Opers.SUB);

    public CheckedSubtract(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.SUB, left, right);
    }

    @Override
    public double op(double a, double b) {
        return a - b;
    }

    @Override
    public int op(int a, int b) {
        if (a > 0 && b < 0 && Integer.MAX_VALUE + b < a ||
                a < 0 && b > 0 && Integer.MIN_VALUE + b > a ||
                a == 0 && b == Integer.MIN_VALUE) {
            throw new ComputingException("Overflow during summation");
        }

        return a - b;
    }
}
