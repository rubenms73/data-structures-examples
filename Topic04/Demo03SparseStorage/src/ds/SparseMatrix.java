package ds;

import java.util.HashMap;
import java.util.Map;

/** Sparse rows are created on demand and removed when their last entry vanishes. */
public class SparseMatrix
{
    // Logical row count, including rows with no stored entries.
    private final int rows;
    // Logical length of every row.
    private final int columns;
    // Row index to sparse row; rows containing only zeros are omitted.
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
        // Dropping an empty row keeps storage proportional to nonzero data.
        if (vector.storedEntries() == 0)
            data.remove(row);
    }

    public int storedRows()
    {
        return data.size();
    }
}
