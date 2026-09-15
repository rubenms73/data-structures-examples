package app;

import java.util.Arrays;
import java.util.Comparator;
import ds.LengthComparator;

public final class Main {
    public static void main(String[] args) {
        String a = "pear", b = "banana";
        // boolean before = a < b; // Uncomment: objects cannot be ordered with <.
        System.out.println("pear.compareTo(banana): " + a.compareTo(b));

        String[] words = {"pear", "banana", "fig"};
        System.out.println("Original: " + Arrays.toString(words));
        Arrays.sort(words);
        System.out.println("Natural order: " + Arrays.toString(words));

        Comparator<String> byLength = new LengthComparator();
        Arrays.sort(words, byLength);
        System.out.println("Length order: " + Arrays.toString(words));
    }
}
