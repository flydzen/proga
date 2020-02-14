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
        int res = a-b;
        if (a < 0 && b > 0 && res >= 0 || a >= 0 && b < 0 && res <= 0){
            throw new ComputingException("Overflow during subtracting");
        }
        return a - b;
    }
}
