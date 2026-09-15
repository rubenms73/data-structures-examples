package ds;

public final class ArrayAlgorithms {
    private ArrayAlgorithms() { }

    /** Returns the sum of the stored elements using Java int arithmetic. */
    public static int sum(MyIntArray values) {
        int total = 0;
        for (int i = 0; i < values.size(); i++)
            total += values.get(i);
        return total;
    }

    /** Formats the sequence using only the operations of its interface. */
    public static String contents(MyIntArray values) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) result.append(", ");
            result.append(values.get(i));
        }
        return result.append("]").toString();
    }
}
