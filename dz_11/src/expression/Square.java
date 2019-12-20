package expression;

public class Square implements Element {
    private Element arg;

    public Square(Element arg){
        this.arg = arg;
    }

    @Override
    public double evaluate(double x) {
        return Math.pow(arg.evaluate(x),2);
    }

    @Override
    public int evaluate(int x) {
        return (int)Math.pow(arg.evaluate(x),2);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int)Math.pow(arg.evaluate(x,y,z),2);
    }

    @Override
    public String toMiniString() {
        return "square " + arg.toMiniString();
    }
}
