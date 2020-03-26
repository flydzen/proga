package expression;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface ToMiniString<T extends Number> {
    default String toMiniString() {
        return toString();
    }
}
