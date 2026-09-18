package tests;

import ds.*;
import java.util.*;

public final class ExampleChecks
{
    private static int checks;

    private static void equal(Object expected, Object actual)
    {
        checks++;
        if (!Objects.equals(expected, actual))
            throw new AssertionError("Expected " + expected + ", got " + actual);
    }

    private static void rejects(Class<? extends Exception> type, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Exception e)
        {
            if (type.isInstance(e))
                return;
            throw new AssertionError(e);
        }
        throw new AssertionError("Expected " + type);
    }

    private static <E> List<E> collect(Iterable<E> source)
    {
        List<E> result = new ArrayList<>();
        for (E value : source)
        {
            result.add(value);
        }
        return result;
    }

    public static void main(String[] args)
    {
        rejects(IllegalArgumentException.class, () -> new SparseVector(-1));
        rejects(IllegalArgumentException.class, () -> new SparseMatrix(2, -1));
        SparseVector empty = new SparseVector(0);
        rejects(IndexOutOfBoundsException.class, () -> empty.get(0));
        SparseMatrix matrix = new SparseMatrix(7, 9);
        int[][] dense = new int[7][9];
        Random random = new Random(17);
        for (int i = 0; i < 300; i++)
        {
            int row = random.nextInt(7);
            int column = random.nextInt(9);
            int value = random.nextInt(3);
            dense[row][column] = value;
            matrix.set(row, column, value);
            for (int r = 0; r < 7; r++)
            {
                for (int c = 0; c < 9; c++)
                {
                    equal(dense[r][c], matrix.get(r, c));
                }
            }
        }
        rejects(IndexOutOfBoundsException.class, () -> matrix.set(7, 0, 1));
        SparseMatrix one = new SparseMatrix(1, 1);
        one.set(0, 0, 4);
        one.set(0, 0, 0);
        equal(0, one.storedRows());
        System.out.println("All " + checks + " checks passed.");
    }
}
