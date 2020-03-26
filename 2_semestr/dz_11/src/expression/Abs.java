package expression;

public class Abs<T extends Number> implements Element<T> {
    private Element<T> arg;

    public Abs(Element arg){
        this.arg = arg;
    }

    @Override
    public T evaluate(T x, Mode mode) {
        return Math.abs(arg.evaluate(x), mode);
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return Math.abs(arg.evaluate(x, y, z));
    }

    @Override
    public String toMiniString() {
        return "abs " + arg.toMiniString();
    }
}
