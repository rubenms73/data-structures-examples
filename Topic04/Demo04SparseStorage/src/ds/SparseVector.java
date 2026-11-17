package ds;

import java.util.HashMap;
import java.util.Map;

/** Fixed logical length; only nonzero entries occupy map positions. */
public class SparseVector
{
    // Logical vector length, including positions whose value is zero.
    private final int length;
    // Index-to-value mapping containing only nonzero entries.
    private final Map<Integer, Double> values = new HashMap<>();

    public SparseVector(int length)
    {
        if (length < 0)
            throw new IllegalArgumentException("Length must not be negative");
        this.length = length;
    }

    public int length()
    {
        return length;
    }

    public int storedEntries()
    {
        return values.size();
    }

    private void checkIndex(int index)
    {
        if (index < 0 || index >= length)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
    }

    public double get(int index)
    {
        checkIndex(index);
        Double value = values.get(index);
        if (value == null)
            return 0;
        return value;
    }

    public void set(int index, double value)
    {
        checkIndex(index);
        if (value == 0)
            values.remove(index);
        else
            values.put(index, value);
    }
}
