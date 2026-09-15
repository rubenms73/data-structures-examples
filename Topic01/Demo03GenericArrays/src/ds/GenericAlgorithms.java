package ds;

public final class GenericAlgorithms {
    private GenericAlgorithms() { }

    /** @throws IllegalArgumentException if values is empty */
    public static <T> T first(MyArray<T> values) {
        if (values.size() == 0)
            throw new IllegalArgumentException("Empty array");
        return values.get(0);
    }
}
