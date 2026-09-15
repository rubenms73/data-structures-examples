package ds;

import java.util.Comparator;

/** Compares strings by length. Arguments must be non-null. */
public final class LengthComparator implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        return Integer.compare(a.length(), b.length());
    }
}
