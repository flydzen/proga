package expression;

import expression.parser.Expression;

public class Main {
    public static void main(String[] args) {
        Element ex = new Expression().parse("z // -z");
        System.out.println(ex.toString());
        System.out.println(ex.evaluate(-510870105, 1949412994, 441981722));
    }
}


