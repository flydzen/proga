package expression;

import java.util.Map;

public enum Opers {
    ADD("+"),
    SUB("-"),
    DIV("/"),
    MUL("*");

    String op;
    Opers(String s) {
        op = s;
    }
    public String toString(){
        return op;
    }
}
