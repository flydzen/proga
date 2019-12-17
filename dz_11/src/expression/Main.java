package expression;

public class Main {
    public static void main(String[] args) {
        Element el = new Add(
                new Subtract(
                        new Const(5),
                        new Subtract(
                                new Const(2.5),
                                new Variable("x"))
                ),
                new Const(1)
        );
        System.out.println(el);
        System.out.println(el.toMiniString());
    } //x^2 - 2x + 1
}
