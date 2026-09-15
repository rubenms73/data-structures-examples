package tests;

import java.util.*;
import java.math.BigInteger;
import ds.*;

public final class ExampleChecks {
    private static int checks;
    public static void main(String[] args) {
        generics();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void generics() {
        MyArray<String> names = new ArrayListMyArray<>();
        throwsType(IllegalArgumentException.class, () -> GenericAlgorithms.first(names));
        equal(true, names.add("Ana"));
        equal("Ana", GenericAlgorithms.first(names));
        names.set(0, "Ruben");
        equal("Ruben", names.get(0));
        equal(1, names.size());
        names.add(null);
        equal(null, names.get(1));
        throwsType(IndexOutOfBoundsException.class, () -> names.get(2));
        throwsType(IndexOutOfBoundsException.class, () -> names.set(-1, "x"));
        MyArray<Integer> scores = new ArrayListMyArray<>();
        scores.add(8);
        Integer first = GenericAlgorithms.first(scores);
        equal(8, first);
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
