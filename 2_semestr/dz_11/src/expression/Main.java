package expression;

import expression.parser.Expression;

public class Main {
    public static void main(String[] args) {
        Mode mode;
        if (args[1].equals("i")){
            mode = Mode.I;
        } else if (args[1].equals("d")){
            mode = Mode.D;
        } else {
            mode = Mode.BI;
        }
    }
}


