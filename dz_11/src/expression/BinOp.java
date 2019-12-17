package expression;

public interface BinOp extends Element{
    double op(double a, double b);
    int op(int a, int b);
    public Opers getType();
}
