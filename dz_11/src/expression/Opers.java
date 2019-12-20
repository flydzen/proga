package expression;

import java.util.Map;

public enum Opers {
    ADD("+"),
    SUB("-"),
    DIV("/"),
    MUL("*"),
    LEFT("<<"),
    RIGHT(">>"),
    OPEN("("),
    CLOSE(")"),
    SQRT("SQRT"),
    ABS("ABS"),
    UNO("-"),
    NONE("NONE");

    String op;
    Opers(String s) {
        op = s;
    }
    public String toString(){
        return op;
    }
}
