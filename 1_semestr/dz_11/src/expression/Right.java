package expression;

import java.util.EnumSet;


public class Right extends Operation {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public Right(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.RIGHT, left, right);
    }

    @Override
    public int op(int a, int b) {
        return a >> b;
    }

    @Override
    public double op(double a, double b) {
        return (int)a >> (int)b;
    }
}
