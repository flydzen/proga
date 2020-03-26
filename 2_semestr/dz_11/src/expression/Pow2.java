package expression;

import expression.exceptions.ComputingException;

public class Pow2<T extends Number> implements UnOp<T> {
    private Element<T> arg;

    public Pow2(Element arg){
        this.arg = arg;
    }

    @Override
    public T evaluate(T x) {
        return Math. pow(2, arg.evaluate(x));
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return Math.pow(2, arg.evaluate(x, y, z));
    }

    @Override
    public String toMiniString() {
        return "pow2 " + arg.toMiniString();
    }
}
