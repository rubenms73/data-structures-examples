package ds;

import java.util.Comparator;

public final class Maximum
{
    private Maximum()
    {
    }

    /**
     * Returns the first maximal element under order.
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
