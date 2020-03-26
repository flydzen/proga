package expression.Operations;

public interface TOperation<T> {

    T add(final T x, final T y);

    T subtract(final T x, final T y);

    T multiply(final T x, final T y);

    T divide(final T x, final T y);

    T negate(final T x);

    T parse(final String x);

    T min(final T x, final T y);

    T max(final T x, final T y);

    T count(final T x);
}