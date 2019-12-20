package expression;

import java.util.EnumSet;


public class Left extends Operation {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public Left(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.LEFT, left, right);
    }

    @Override
    public int op(int a, int b) {
        return a << b;
    }

    @Override
    public double op(double a, double b) {
        return (int)a << (int)b;
    }
}
