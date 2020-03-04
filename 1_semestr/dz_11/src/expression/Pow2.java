package expression;

import expression.exceptions.ComputingException;

public class Pow2 implements Element {
    private Element arg;

    public Pow2(Element arg){
        this.arg = arg;
    }

    @Override
    public double evaluate(double x) {
        return Math.pow(2, (int)arg.evaluate(x));
    }

    @Override
    public int evaluate(int x) {
        return Math. pow(2, arg.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Math.pow(2, arg.evaluate(x, y, z));
    }

    @Override
    public String toMiniString() {
        return "pow2 " + arg.toMiniString();
    }
}
