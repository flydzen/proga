package expression;

import java.util.EnumSet;

public class Subtract extends Operation {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.of(Opers.ADD, Opers.SUB);

    public Subtract(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.SUB, left, right);
    }

    @Override
    public double op(double a, double b) {
        return a - b;
    }

    @Override
    public int op(int a, int b) {
        return a - b;
    }
}
