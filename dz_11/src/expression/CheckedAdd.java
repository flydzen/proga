package expression;

import java.util.EnumSet;

import expression.exceptions.ComputingException;

public class CheckedAdd extends Operation {
    public static EnumSet<Opers> left = EnumSet.of(Opers.LEFT, Opers.RIGHT);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public CheckedAdd(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.ADD, left, right);
    }

    @Override
    public int op(int a, int b) {
        int res = a+b;
        if (a < 0 && b < 0 && res >= 0 || a > 0 && b > 0 && res <= 0){
            throw new ComputingException("Overflow during summation");
        }
        return a+b;
    }

    @Override
    public double op(double a, double b) {
        return a + b;
    }
}
