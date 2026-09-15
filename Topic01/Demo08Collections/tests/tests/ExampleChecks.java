package tests;

import java.util.*;
import java.math.BigInteger;
import ds.*;

public final class ExampleChecks {
    private static int checks;
    public static void main(String[] args) {
        bags();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void bags() {
        List<String> source = new ArrayList<>(Arrays.asList("pear", null, "pear"));
        ArrayBag<String> bag = new ArrayBag<>(source);
        source.clear();
        equal(Arrays.asList("pear", null, "pear"), collect(bag));
        equal(3, bag.size());
        equal(true, bag.contains(null));
        equal(true, bag.contains("pear"));
        equal(false, bag.contains("fig"));
        equal(false, bag.isEmpty());
        equal(Arrays.asList("pear", null, "pear"), Arrays.asList(bag.toArray(new String[0])));
        throwsType(UnsupportedOperationException.class, () -> bag.add("fig"));
        throwsType(UnsupportedOperationException.class, () -> bag.remove("pear"));
        Iterator<String> a = bag.iterator();
        Iterator<String> b = bag.iterator();
        a.next(); a.next(); a.next();
        equal("pear", b.next());
        throwsType(NoSuchElementException.class, a::next);
        ArrayBag<String> empty = new ArrayBag<>(new ArrayList<>());
        equal(true, empty.isEmpty());
        throwsType(NoSuchElementException.class, empty.iterator()::next);
        StringBuilder element = new StringBuilder("a");
        ArrayBag<StringBuilder> references = new ArrayBag<>(Arrays.asList(element));
        element.append("b");
        equal("ab", references.iterator().next().toString());
    }

    private static <T> List<T> collect(Iterable<T> values) {
        List<T> result = new ArrayList<>();
        for (T value : values) result.add(value);
        return result;
    }

    private static void equal(Object expected, Object actual) {
        check(Objects.equals(expected, actual), "Expected " + expected + ", got " + actual);
    }

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }

    private static void throwsType(Class<? extends Throwable> expected, Runnable action) {
        checks++;
        try {
            action.run();
        } catch (Throwable actual) {
            if (expected.isInstance(actual)) return;
            throw new AssertionError("Expected " + expected.getSimpleName() + ", got " + actual, actual);
        }
        throw new AssertionError("Expected " + expected.getSimpleName());
    }
}
