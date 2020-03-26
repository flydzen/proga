package expression;

import expression.exceptions.ComputingException;
import expression.exceptions.ParsingException;

public class Log2<T extends Number> implements UnOp<T> {
    private Element<T> arg;

    public Log2(Element arg){
        this.arg = arg;
    }

    @Override
    public T evaluate(T x) {
        return Math.log(arg.evaluate(x),2);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return Math.log(arg.evaluate(x, y, z), 2);
    }

    @Override
    public String toMiniString() {
        return "pow2 " + arg.toMiniString();
    }
}
