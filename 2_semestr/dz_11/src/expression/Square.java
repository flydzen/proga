package expression;

public class Square<T extends Number> implements Element<T> {
    private Element<T> arg;

    public Square(Element arg){
        this.arg = arg;
    }

    @Override
    public T evaluate(T x) {
        return Math.pow(arg.evaluate(x),2);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return Math.pow(arg.evaluate(x,y,z),2);
    }

    @Override
    public String toString() {
        return "square " + arg.toString();
    }

    @Override
    public String toMiniString() {
        return "square " + arg.toMiniString();
    }
}
