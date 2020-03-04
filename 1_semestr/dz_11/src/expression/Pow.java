package expression;

import expression.exceptions.ComputingException;

import java.util.EnumSet;


public class Pow extends Operation {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public Pow(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.POW, left, right);
    }

    @Override
    public int op(int a, int b) {
        return Math.pow(a, b);
    }

    @Override
    public double op(double a, double b) {
        return Math.pow((int)a, (int)b);
    }
}
