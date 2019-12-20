package expression;

import java.util.Objects;

public class Variable implements Value{
    private String name;
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public double evaluate(double x) {
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
    public int evaluate(int x, int y, int z) {
        if (name.equals("x")){
            return x;
        } else if (name.equals("y")){
            return y;
        }else{
            return z;
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
