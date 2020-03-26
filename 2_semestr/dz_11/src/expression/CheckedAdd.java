package expression;

import java.util.EnumSet;

import expression.Operations.TOperation;
import expression.exceptions.ComputingException;

public class CheckedAdd<T extends Number> extends Operation<T> {
    public static EnumSet<Opers> left = EnumSet.of(Opers.LEFT, Opers.RIGHT);
    public static EnumSet<Opers> right = EnumSet.noneOf(Opers.class);

    public CheckedAdd(Element arg1, Element arg2, TOperation<T> operation) {
        super(arg1, arg2, Opers.ADD, left, right, operation);
    }

    @Override
    public T op(T a, T b) {
        return operation.add(a,b);
    }
}