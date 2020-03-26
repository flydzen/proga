package expression;

import expression.exceptions.ExpressionException;

import java.util.Objects;

public class Variable<T extends Number> implements Value<T>{
    private String name;
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public T evaluate(T x) {
        return x;
    }

    @Override
    public String toString() {
        return name;
    }
    public String toMiniString(boolean state) {
        return name;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        if (name.equals("x")){
            return x;
        } else if (name.equals("y")){
            return y;
        }else if (name.equals("z")){
            return z;
        }else {
            throw new ExpressionException("no variable");
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return name.equals(variable.name);
    }
}
