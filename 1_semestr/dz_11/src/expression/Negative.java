package expression;

public class Negative implements UnOp {
    private Element arg;

    public Negative(Element arg){
        this.arg = arg;
    }

    @Override
    public double evaluate(double x) {
        return -arg.evaluate(x);
    }

    @Override
    public int evaluate(int x) {
        return -arg.evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -arg.evaluate(x, y, z);
    }

    @Override
    public String toMiniString() {
        return "-" + arg.toMiniString();
    }
}
