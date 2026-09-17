package app;

import java.util.Arrays;
import ds.CostAlgorithms;

public final class Main
{
    public static void main(String[] args)
    {
        for (int size : new int[] {4, 8, 16})
        {
            int[] values = new int[size];
            Arrays.fill(values, 1);
            CostAlgorithms.sumAndCount(values);
        }
        String[] array = {"A", "B", "C", "D", "E"};
        System.out.println("Array middle: " + CostAlgorithms.middle(array));
        System.out.println("Array middle: direct access, O(1).");
    }

}
