package expression;

import expression.Operations.TOperation;
import expression.exceptions.ComputingException;
import java.util.EnumSet;

public class CheckedDivide<T extends Number> extends Operation<T> {
    public static EnumSet<Opers> left = EnumSet.of(Opers.ADD, Opers.SUB, Opers.LEFT, Opers.LEFT, Opers.RIGHT);
    public static EnumSet<Opers> right = EnumSet.of(Opers.ADD, Opers.SUB, Opers.DIV, Opers.MUL, Opers.LEFT, Opers.RIGHT);
    public CheckedDivide(Element arg1, Element arg2, TOperation<T> operation) {
        super(arg1, arg2, Opers.DIV, left, right, operation);
    }
    @Override
    public T op(T a, T b) {
        return operation.divide(a, b);
    }
    @Override
    public T op(T a, T b) {
        if (a == Integer.MIN_VALUE && b == -1)
            throw new ComputingException("Overflow during division");
        return a/b;
    }
}
