package expression;

public class Abs implements Element {
    private Element arg;

    public Abs(Element arg){
        this.arg = arg;
    }

    @Override
    public double evaluate(double x) {
        return Math.abs(arg.evaluate(x));
    }

    @Override
    public int evaluate(int x) {
        return Math.abs(arg.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return Math.abs(arg.evaluate(x, y, z));
    }

    @Override
    public String toMiniString() {
        return "abs " + arg.toMiniString();
    }
}
