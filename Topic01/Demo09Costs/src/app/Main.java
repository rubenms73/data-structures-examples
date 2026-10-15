package app;

import java.util.Arrays;
import ds.CostAlgorithms;

public final class Main
{
    public static void main(String[] args)
    {
        System.out.println("Sum arrays filled with ones; count additions rather than elapsed time:");
        for (int size : new int[] {4, 8, 16})
        {
            int[] values = new int[size];
            Arrays.fill(values, 1);
            CostAlgorithms.sumAndCount(values);
        }
        System.out.println("Doubling n doubles the additions: linear work, O(n).");
        String[] array = {"A", "B", "C", "D", "E"};
        System.out.println("Input for direct access: " + Arrays.toString(array));
        System.out.println("Middle index: " + array.length / 2);
        System.out.println("Array middle: " + CostAlgorithms.middle(array));
        System.out.println("Array middle: direct access, O(1).");
    }

}

