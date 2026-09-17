package tests;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import ds.FixedMyArray;
import ds.MyArray;

public final class ExampleChecks
{
    private static int checks;

    public static void main(String[] args)
    {
        MyArray<String> names = new FixedMyArray<>(new String[4]);
        Iterator<String> empty = names.iterator();
        equal(false, empty.hasNext());
        throwsType(NoSuchElementException.class, empty::next);
        names.add("Ana");
        names.add("Ruben");
        Iterator<String> first = names.iterator();
        Iterator<String> second = names.iterator();
        equal(true, first.hasNext());
        equal(true, first.hasNext());
        equal("Ana", first.next());
        equal("Ruben", first.next());
        equal("Ana", second.next());
        equal(false, first.hasNext());
        throwsType(NoSuchElementException.class, first::next);
        throwsType(NoSuchElementException.class, first::next);
        equal("Ruben", second.next());
        equal(false, second.hasNext());
        throwsType(UnsupportedOperationException.class, second::remove);
        StringBuilder output = new StringBuilder();
        for (String name : names)
        {
            output.append(name).append(";");
        }
        equal("Ana;Ruben;", output.toString());
        MyArray<Integer> scores = new FixedMyArray<>(new Integer[3]);
        scores.add(8);
        scores.add(10);
        int sum = 0;
        for (Integer score : scores)
        {
            sum += score;
        }
        equal(18, sum);
        scores.add(null);
        Iterator<Integer> withNull = scores.iterator();
        equal(8, withNull.next());
        equal(10, withNull.next());
        equal(null, withNull.next());
        equal(false, withNull.hasNext());
        equal(3, scores.size());
        System.out.println("All " + checks + " checks passed.");
    }

    private static void equal(Object expected, Object actual)
    {
        checks++;
        if (!Objects.equals(expected, actual))
            throw new AssertionError("Expected " + expected + ", got " + actual);
    }

    private static void throwsType(Class<? extends Throwable> expected, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Throwable actual)
        {
            if (expected.isInstance(actual))
                return;
            throw new AssertionError("Unexpected exception", actual);
        }
        throw new AssertionError("Expected " + expected.getSimpleName());
    }
}
