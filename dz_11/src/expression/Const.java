package expression;

public class Const implements Value{
    private final double value;
    private boolean isDouble;
    Const(double value){
        this.value = value;
        this.isDouble = value%1!=0;
    }

    @Override
    public String toString() {
        if (isDouble){
            return Double.toString(value);
        }else{
            return Integer.toString((int)value);
        }
    }
    @Override
    public String toMiniString() {
        return toString();
    }
    @Override
    public double evaluate(double x) {
        return this.value;
    }
    @Override
    public int evaluate(int x, int y, int z){
        return (int)this.value;
    }

    @Override
    public int hashCode() {
        return Double.toString(value).hashCode()+(int)value;
    }

    @Override
    public int evaluate(int x){
        return (int)this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return hashCode() == obj.hashCode();
    }
}
