package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        SparseVector vector = new SparseVector(1000000);
        vector.set(999999, 7);
        System.out.println("Length: " + vector.length() + "; stored: " + vector.storedEntries());
        SparseMatrix matrix = new SparseMatrix(1000, 1000);
        matrix.set(5, 9, 12);
        System.out.println("Entry: " + matrix.get(5, 9));
        matrix.set(5, 9, 0);
        System.out.println("Stored rows after clearing entry: " + matrix.storedRows());
    }
}
