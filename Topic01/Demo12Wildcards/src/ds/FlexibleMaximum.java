package ds;

import java.util.Comparator;

/** Appendix alternative to comparison.Maximum, kept in a separate class. */
public final class FlexibleMaximum
{
    private FlexibleMaximum()
    {
    }

    /**
     * Returns the first maximal element; the comparator may accept a supertype.
     * The array and comparator must be non-null; order must accept its elements.
     * @throws IllegalArgumentException if values is empty
     */
    public static <T> T max(T[] values, Comparator<? super T> order)
    {
        if (values.length == 0)
            throw new IllegalArgumentException("Empty array");
        T result = values[0];
        for (T value : values)
        {
            if (order.compare(result, value) < 0)
                result = value;
        }
        return result;
    }
}
