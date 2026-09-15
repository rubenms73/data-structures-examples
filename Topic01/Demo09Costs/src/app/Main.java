package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import ds.CostAlgorithms;

public final class Main {
    public static void main(String[] args) {
        for (int size : new int[] {4, 8, 16}) {
            int[] values = new int[size];
            Arrays.fill(values, 1);
            CostAlgorithms.sumAndCount(values);
        }
        List<String> array = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
        List<String> linked = new LinkedList<>(array);
        System.out.println("ArrayList middle: " + CostAlgorithms.middle(array));
        System.out.println("LinkedList middle: " + CostAlgorithms.middle(linked));
        System.out.println("ArrayList.get: direct array access, O(1).");
        System.out.println("LinkedList.get at the middle: follows links, O(n).");
    }

}
