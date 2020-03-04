package expression;

import java.util.Objects;

public class Const implements Value{
    private final Number value;
    public Const(Number value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
    @Override
    public String toMiniString() {
        return toString();
    }
    @Override
    public double evaluate(double x) {
        return this.value.doubleValue();
    }
    @Override
    public int evaluate(int x, int y, int z){
        return value.intValue();
    }

    @Override
    public int evaluate(int x){
        return this.value.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Const aConst = (Const) o;
        return Objects.equals(value, aConst.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
