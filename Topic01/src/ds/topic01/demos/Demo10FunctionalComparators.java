package ds.topic01.demos;

import java.util.Arrays;
import java.util.Comparator;
import ds.topic01.comparison.LengthComparator;

public final class Demo10FunctionalComparators {
    public static void main(String[] args) {
        Comparator<String> named = new LengthComparator();

        Comparator<String> anonymous = new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.compare(a.length(), b.length());
            }
        };

        Comparator<String> lambda =
                (a, b) -> Integer.compare(a.length(), b.length());

        show("Named class", named);
        show("Anonymous class", anonymous);
        show("Lambda", lambda);
    }

    private static void show(String label, Comparator<String> order) {
        String[] words = {"pear", "banana", "fig"};
        Arrays.sort(words, order);
        System.out.println(label + ": " + Arrays.toString(words));
    }
}
