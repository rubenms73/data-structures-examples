package app;

import ds.SparseMatrix;
import ds.SparseVector;

public final class Main
{
    public static void main(String[] args)
    {
        SparseVector vector = new SparseVector(1000000);
        System.out.println("1. Logical length and stored entries are different");
        System.out.println("Initial vector: length = " + vector.length()
                + "; stored entries = " + vector.storedEntries());
        System.out.println("Unset get(123): " + vector.get(123));
        vector.set(999999, 7);
        show(vector, "set(999999, 7)");
        vector.set(3, -2);
        show(vector, "set(3, -2)");
        vector.set(999999, 9);
        show(vector, "set(999999, 9): replacing a value");
        vector.set(999999, 0);
        show(vector, "set(999999, 0): remove its stored entry");
        System.out.println("get(999999) after removal: " + vector.get(999999));

        SparseMatrix matrix = new SparseMatrix(1000, 1000);
        System.out.println("\n2. A 1000 x 1000 matrix stores only nonempty rows");
        System.out.println("Initial stored rows: " + matrix.storedRows());
        System.out.println("Unset get(5, 9): " + matrix.get(5, 9));
        matrix.set(5, 9, 12);
        System.out.println("set(5, 9, 12): value = " + matrix.get(5, 9)
                + "; stored rows = " + matrix.storedRows());
        matrix.set(5, 10, 4);
        System.out.println("set(5, 10, 4): stored rows = " + matrix.storedRows());
        matrix.set(8, 2, -3);
        System.out.println("set(8, 2, -3): stored rows = " + matrix.storedRows());
        matrix.set(5, 9, 0);
        System.out.println("Clear (5, 9): stored rows = " + matrix.storedRows()
                + "; row 5 still contains (5, 10) = " + matrix.get(5, 10));
        matrix.set(5, 10, 0);
        System.out.println("Clear last entry of row 5: stored rows = " + matrix.storedRows());
        matrix.set(8, 2, 0);
        System.out.println("Clear last entry of row 8: stored rows = " + matrix.storedRows());
    }

    private static void show(SparseVector vector, String operation)
    {
        System.out.println(operation + ": stored entries = " + vector.storedEntries()
                + "; logical length = " + vector.length());
    }
}
