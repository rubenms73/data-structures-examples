package ds.topic01.demos;

import java.util.Arrays;
import java.util.Comparator;

public final class Demo04StringSorting {
    public static void main(String[] args) {
        String a = "pear", b = "banana";
        // boolean before = a < b; // Uncomment: objects cannot be ordered with <.
        System.out.println("pear.compareTo(banana): " + a.compareTo(b));

        String[] words = {"pear", "banana", "fig"};
        System.out.println("Original: " + Arrays.toString(words));
        Arrays.sort(words);
        System.out.println("Natural order: " + Arrays.toString(words));

        Comparator<String> byLength =
                (left, right) -> Integer.compare(left.length(), right.length());
        Arrays.sort(words, byLength);
        System.out.println("Length order: " + Arrays.toString(words));
    }
}
