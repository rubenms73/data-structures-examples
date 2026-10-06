package app;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;
import ds.ImmutableBag;
import ds.MutableBag;
import ds.SortedMutableBag;

public final class Main
{
    private static Collection<String> getWords(Path file) throws IOException
    {
        MutableBag<String> words = new MutableBag<>();
        try (Scanner scanner = new Scanner(Files.newBufferedReader(file, StandardCharsets.UTF_8)))
        {
            // A word is a maximal sequence of Unicode letters. Hyphens split words.
            scanner.useDelimiter("[^\\p{L}]+");
            while (scanner.hasNext())
            {
                String word = scanner.next();
                if (!word.isEmpty())
                    words.add(word.toLowerCase(Locale.ROOT));
            }
        }
        return words;
    }

    public static void main(String[] args)
    {
        ImmutableBag<Integer> original = new ImmutableBag<>(2, -3, 2, 18);
        ImmutableBag<Integer> extended = original.withAdded(7);
        System.out.println("Immutable original: " + original);
        System.out.println("New immutable bag: " + extended);
        System.out.println("Without one 2: " + original.withoutOne(2));
        System.out.println("Original after both immutable operations: " + original
                + "; occurrences(2) = " + original.occurrences(2));

        MutableBag<Integer> mutable = new MutableBag<>(original);
        System.out.println("\nMutable copy before operations: " + mutable);
        System.out.println("add(7): " + mutable.add(7) + "; contents = " + mutable);
        System.out.println("remove(2): " + mutable.remove(2) + "; contents = " + mutable);
        System.out.println("Mutable after add and remove: " + mutable);

        ImmutableBag<Collection<Integer>> bags = new ImmutableBag<>(
                new ImmutableBag<>(100, 2),
                new ImmutableBag<>(2, -3, 2, 18),
                new ImmutableBag<>(18, 2, 2, -3),
                new ImmutableBag<>(-1, 0, 10),
                new ImmutableBag<>(18, 2, -3));
        System.out.println("\nCandidate bags: " + bags);
        System.out.println("Equal bags (same multiplicities): " + bags.occurrences(original));

        SortedMutableBag<Integer> sorted = new SortedMutableBag<>(original);
        System.out.println("Sorted: " + sorted);
        System.out.println("Same bag despite order: " + original.equals(sorted));

        System.out.println("Sorting changes iteration order, but preserves multiplicities.");
        Path input = Path.of(args.length == 0 ? "text.txt" : args[0]);
        try
        {
            SortedMutableBag<String> words = new SortedMutableBag<>(getWords(input));
            System.out.println("\nRead words from: " + input);
            System.out.println("Normalisation: lowercase Unicode words; punctuation separates words.");
            System.out.print("First 12 words in sorted iteration:");
            int shown = 0;
            for (String word : words)
            {
                if (shown == 12)
                    break;
                System.out.print(" " + word);
                shown++;
            }
            System.out.println();
            System.out.println("Words: " + words.size());
            System.out.println("Occurrences of the: " + words.occurrences("the"));
            System.out.println("Occurrences of for: " + words.occurrences("for"));
        }
        catch (IOException e)
        {
            System.err.println("Cannot read " + input + ": " + e.getMessage());
            System.exit(1);
        }
    }
}

