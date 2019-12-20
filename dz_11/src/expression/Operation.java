package expression;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public abstract class Operation implements BinOp {
    private Element arg1;
    private Element arg2;
    private Opers type;
    private EnumSet left;
    private EnumSet right;

    Operation(Element arg1, Element arg2, Opers type, EnumSet left, EnumSet right) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public Opers getType(){
        return type;
    }
    public double evaluate(double x) {
        return op(arg1.evaluate(x), arg2.evaluate(x));
    }

    public int evaluate(int x, int y, int z){
        return op(arg1.evaluate(x,y,z), arg2.evaluate(x,y,z));
    }

    public int evaluate(int x) {
        return op(arg1.evaluate(x), arg2.evaluate(x));
    }

    @Override
    public String toString() {
        String sb = "(" +
                arg1.toString() +
                " " + type.toString() + " " +
                arg2.toString() +
                ")";
        return sb;
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        if (arg1 instanceof BinOp && left.contains(((BinOp)arg1).getType())){
            sb.append("(").append(arg1.toMiniString()).append(")");
        }else{
            sb.append(arg1.toMiniString());
        }
        sb.append(" ").append(type.toString()).append(" ");
        if (arg2 instanceof BinOp && right.contains(((BinOp)arg2).getType())){
            sb.append("(").append(arg2.toMiniString()).append(")");
        }else{
            sb.append(arg2.toMiniString());
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg1, arg2, type, left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return arg1.equals(operation.arg1) &&
                arg2.equals(operation.arg2) &&
                type == operation.type;
    }
}
