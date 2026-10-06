package app;

import java.util.Arrays;
import java.util.Comparator;
import ds.LengthComparator;

public final class Main
{
    public static void main(String[] args)
    {
        String a = "pear", b = "banana";
        // boolean before = a < b; // Uncomment: objects cannot be ordered with <.
        System.out.println("pear.compareTo(banana): " + a.compareTo(b));
        System.out.println("A positive comparison places pear after banana alphabetically.");

        String[] words = {"pear", "banana", "fig"};
        System.out.println("Original: " + Arrays.toString(words));
        Arrays.sort(words);
        System.out.println("Natural order: " + Arrays.toString(words));

        Comparator<String> byLength = new LengthComparator();
        System.out.println("LengthComparator.compare(pear, banana): " + byLength.compare(a, b));
        System.out.println("A negative comparison places the shorter word first.");
        Arrays.sort(words, byLength);
        System.out.println("Length order: " + Arrays.toString(words));
    }
}

