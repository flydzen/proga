package expression;

import expression.exceptions.ComputingException;
import expression.exceptions.ParsingException;

public class Log2 implements Element {
    private Element arg;

    public Log2(Element arg){
        this.arg = arg;
    }

    @Override
    public double evaluate(double x) {
        return Math.log((int)arg.evaluate(x),2);
    }

    @Override
    public int evaluate(int x) {
        return Math.log(arg.evaluate(x),2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Math.log(arg.evaluate(x, y, z), 2);
    }

    @Override
    public String toMiniString() {
        return "pow2 " + arg.toMiniString();
    }
}
