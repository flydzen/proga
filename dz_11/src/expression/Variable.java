package expression;

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
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        return hashCode() == obj.hashCode();
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
}
