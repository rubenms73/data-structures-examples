package ds;

import java.util.HashMap;
import java.util.Map;

/** Sparse rows are created on demand and removed when their last entry vanishes. */
public class SparseMatrix
{
    private final int rows;
    private final int columns;
    private final Map<Integer, SparseVector> data = new HashMap<>();

    public SparseMatrix(int rows, int columns)
    {
        if (rows < 0 || columns < 0)
            throw new IllegalArgumentException("Dimensions must not be negative");
        this.rows = rows;
        this.columns = columns;
    }

    private void checkPosition(int row, int column)
    {
        if (row < 0 || row >= rows || column < 0 || column >= columns)
            throw new IndexOutOfBoundsException("Invalid matrix position");
    }

    public int get(int row, int column)
    {
        checkPosition(row, column);
        SparseVector vector = data.get(row);
        if (vector == null)
            return 0;
        return vector.get(column);
    }

    public void set(int row, int column, int value)
    {
        checkPosition(row, column);
        SparseVector vector = data.get(row);
        if (vector == null)
        {
            if (value == 0)
                return;
            vector = new SparseVector(columns);
            data.put(row, vector);
        }
        vector.set(column, value);
        if (vector.storedEntries() == 0)
            data.remove(row);
    }

    public int storedRows()
    {
        return data.size();
    }
}
