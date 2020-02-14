package expression;

import java.util.EnumSet;


public class Add extends Operation {
    public static EnumSet<Opers> left = EnumSet.of(Opers.LEFT, Opers.RIGHT);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public Add(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.ADD, left, right);
    }

    @Override
    public int op(int a, int b) {
        return a+b;
    }

    @Override
    public double op(double a, double b) {
        return a + b;
    }
}
