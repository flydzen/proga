package expression;

import expression.exceptions.ComputingException;
import expression.exceptions.ParsingException;

import java.util.EnumSet;


public class Log extends Operation {
    public static EnumSet<Opers> left = EnumSet.noneOf(Opers.class);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);
    public Log(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.LOG, left, right);
    }

    @Override
    public int op(int a, int b) {
        return Math.log(a, b);
    }

    @Override
    public double op(double a, double b) {
        return Math.log((int)a, (int)b);
    }
}
