package expression;

import java.util.Objects;

public class Const<T extends Number> implements Value<T>{
    private final T value;
    public Const(T value){
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
    public T evaluate(T x, T y, T z){
        return value;
    }

    @Override
    public T evaluate(T x){
        return this.value;
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
