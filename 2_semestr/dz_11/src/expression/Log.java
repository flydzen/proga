package expression;

import expression.exceptions.ComputingException;
import expression.exceptions.ParsingException;

import java.util.EnumSet;


public class Log<T extends Number> extends Operation<T> {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public Log(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.LOG, left, right);
    }

    @Override
    public T op(T a, T b) {
        return Math.log(a, b);
    }
}
