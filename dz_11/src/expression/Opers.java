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
    LOG2("log2"),
    POW2("pow2"),
    LOG("//"),
    POW("**"),
    NONE("NONE");

    String op;
    Opers(String s) {
        op = s;
    }
    public String toString(){
        return op;
    }
}
