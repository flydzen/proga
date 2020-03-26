package expression;

import java.util.EnumSet;


public class Right<T extends Number> extends Operation<T> {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public Right(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.RIGHT, left, right);
    }

    public T op(T a, T b) {
        return a >> b;
    }
}
