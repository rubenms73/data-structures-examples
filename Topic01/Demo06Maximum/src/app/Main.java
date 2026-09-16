package app;

import java.util.Arrays;
import java.util.Comparator;
import ds.Maximum;

public final class Main
{
    public static void main(String[] args)
    {
        String[] words = {"pear", "banana", "fig"};
        Comparator<String> alphabetical = (a, b) -> a.compareTo(b);
        Comparator<String> byLength =
                (a, b) -> Integer.compare(a.length(), b.length());
        System.out.println("Alphabetical maximum: " + Maximum.max(words, alphabetical));
        System.out.println("Longest word: " + Maximum.max(words, byLength));
        System.out.println("Original array: " + Arrays.toString(words));
        try
        {
            Maximum.max(new String[0], byLength);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
