package expression;

public interface BinOp<T extends Number> extends Element<T>{
    T op(T a, T b);
    public Opers getType();
}
