package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Expression<T extends Number> extends ToMiniString<T> {
    T evaluate(T x);
}
