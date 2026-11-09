package app;

import ds.MinHeap;
import ds.MinPriorityQueue;
import java.util.Comparator;
import java.util.List;

public final class Main
{
    public static void main(String[] args)
    {
        System.out.println("1. Inserting sifts the new value up");
        MinPriorityQueue<Integer> heap = new MinHeap<>(Comparator.naturalOrder());
        for (int value : new int[] {20, 30, 25, 25, 10})
        {
            heap.add(value);
            System.out.println("add(" + value + "): array = " + heap);
        }
        System.out.println("element(): " + heap.element() + "; size = " + heap.size());

        System.out.println("\n2. Removing the minimum sifts the last element down");
        while (!heap.isEmpty())
        {
            int min = heap.remove();
            System.out.println("remove(): " + min + "; array = " + heap);
        }

        System.out.println("\n3. Building a heap bottom-up from an array");
        MinHeap<Integer> built = new MinHeap<>(List.of(9, 4, 7, 1, 3, 6, 2),
                Comparator.naturalOrder());
        System.out.println("From [9, 4, 7, 1, 3, 6, 2]: array = " + built);

        System.out.println("\n4. A reversed comparator gives a max-heap");
        MinHeap<String> words = new MinHeap<>(Comparator.reverseOrder());
        for (String word : new String[] {"pear", "apple", "plum", "fig"})
        {
            words.add(word);
        }
        StringBuilder order = new StringBuilder();
        while (!words.isEmpty())
        {
            order.append(words.remove()).append(' ');
        }
        System.out.println("Removed in order: " + order.toString().trim());
    }
}
