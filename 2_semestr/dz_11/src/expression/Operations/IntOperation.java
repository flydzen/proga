package expression.Operations;

import expression.exceptions.ComputingException;

public class IntOperation implements TOperation<Integer> {
    @Override
    public Integer add(Integer a, Integer b) {
        if (a > 0 && b > 0 && Integer.MAX_VALUE - a < b ||
                a <= 0 && b < 0 && Integer.MIN_VALUE - a > b) {
            throw new ComputingException("Overflow during summation");
        }
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        return null;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        return null;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        if (a == Integer.MIN_VALUE && b == -1)
            throw new ComputingException("Overflow during division");
        return a/b;
    }

    @Override
    public Integer negate(Integer x) {
        return null;
    }

    @Override
    public Integer parse(String x) {
        return null;
    }

    @Override
    public Integer min(Integer x, Integer y) {
        return null;
    }

    @Override
    public Integer max(Integer x, Integer y) {
        return null;
    }

    @Override
    public Integer count(Integer x) {
        return null;
    }
}
