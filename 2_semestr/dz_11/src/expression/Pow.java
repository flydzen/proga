package expression;

import expression.exceptions.ComputingException;

import java.util.EnumSet;


public class Pow<T extends Number> extends Operation<T> {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public Pow(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.POW, left, right);
    }

    @Override
    public T op(T a, T b) {
        return Math.pow(a, b);
    }
}
