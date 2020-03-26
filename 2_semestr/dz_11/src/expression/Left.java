package expression;

import java.util.EnumSet;


public class Left<T extends Number> extends Operation<T> {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public Left(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.LEFT, left, right);
    }

    @Override
    public T op(T a, T b) {
        return a << b;
    }
}
