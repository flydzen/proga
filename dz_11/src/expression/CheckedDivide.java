package expression;

import expression.exceptions.ComputingException;
import java.util.EnumSet;

public class CheckedDivide extends Operation {
    public static EnumSet<Opers> left = EnumSet.of(Opers.ADD, Opers.SUB, Opers.LEFT, Opers.LEFT, Opers.RIGHT);
    public static EnumSet<Opers> right = EnumSet.of(Opers.ADD, Opers.SUB, Opers.DIV, Opers.MUL, Opers.LEFT, Opers.RIGHT);
    public CheckedDivide(Element arg1, Element arg2) {
        super(arg1, arg2, Opers.DIV, left, right);
    }
    @Override
    public double op(double a, double b) {
        return a/b;
    }
    @Override
    public int op(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1)
            throw new ComputingException("Overflow during division");
        return a/b;
    }
}
