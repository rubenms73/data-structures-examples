package ds.topic01.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class Demo09Costs {
    public static void main(String[] args) {
        for (int size : new int[] {4, 8, 16}) {
            int[] values = new int[size];
            Arrays.fill(values, 1);
            sumAndCount(values);
        }
        List<String> array = new ArrayList<>(Arrays.asList("A", "B", "C", "D", "E"));
        List<String> linked = new LinkedList<>(array);
        System.out.println("ArrayList middle: " + middle(array));
        System.out.println("LinkedList middle: " + middle(linked));
        System.out.println("ArrayList.get: direct array access, O(1).");
        System.out.println("LinkedList.get at the middle: follows links, O(n).");
    }

    private static void sumAndCount(int[] values) {
        int total = 0;
        int additions = 0;
        for (int value : values) {
            total += value;
            additions++;
        }
        System.out.println("n = " + values.length + ": sum = " + total
                + ", additions = " + additions);
    }

    /** Requires a nonempty list. */
    public static String middle(List<String> values) {
        return values.get(values.size() / 2);
    }
}
