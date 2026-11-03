package app;

import java.util.*;
import ds.UniqueLinkedCollection;

public class App
{
    public static void main(String[] args)
    {
        UniqueLinkedCollection<String> values = new UniqueLinkedCollection<>();
        check("Initially empty", 0, values.size());
        check("Add A", true, values.add("A"));
        check("Add B", true, values.add("B"));
        check("Duplicate A", false, values.add(new String("A")));
        check("After insertion", "[A, B]", values.toString());
        check("Size", 2, values.size());

        List<String> source = new ArrayList<>(Arrays.asList("A", "B", "A", "C", "B"));
        UniqueLinkedCollection<String> copy = new UniqueLinkedCollection<>(source);
        check("Conversion", "[A, B, C]", copy.toString());
        source.clear();
        source.add("Z");
        check("After changing source", "[A, B, C]", copy.toString());

        values = new UniqueLinkedCollection<>(Arrays.asList("A", "B", "C", "D"));
        Iterator<String> it = values.iterator();
        exception("Remove before next", IllegalStateException.class, it::remove);
        check("Next", "A", it.next());
        it.remove();
        check("Remove first", "[B, C, D]", values.toString());
        exception("Remove twice", IllegalStateException.class, it::remove);
        check("Next after removal", "B", it.next());
        check("Next", "C", it.next());
        it.remove();
        check("Remove middle", "[B, D]", values.toString());
        check("Next after removal", "D", it.next());
        check("End reached", false, it.hasNext());
        exception("Next at end", NoSuchElementException.class, it::next);
        it.remove();
        check("Remove last after failed next", "[B]", values.toString());

        it = values.iterator();
        while (it.hasNext())
        {
            it.next();
            it.remove();
        }
        check("Remove all", 0, values.size());
        check("Add after removing all", true, values.add("E"));
        check("Reused collection", "[E]", values.toString());
        final UniqueLinkedCollection<String> reused = values;
        exception("Insert null", NullPointerException.class, () -> reused.add(null));
        check("After rejected null", "[E]", values.toString());
        exception("Null source", NullPointerException.class,
                () -> new UniqueLinkedCollection<String>(null));
        exception("Null in source", NullPointerException.class,
                () -> new UniqueLinkedCollection<>(Arrays.asList("A", null)));
    }

    private static void check(String label, Object expected, Object actual)
    {
        System.out.println(label + ": expected " + expected + ", actual " + actual);
        if (expected == null ? actual != null : !expected.equals(actual))
            throw new AssertionError(label);
    }

    private static void exception(String label, Class<? extends Throwable> expected,
            Runnable action)
    {
        Throwable caught = null;

        try
        {
            action.run();
        }
        catch (Throwable error)
        {
            caught = error;
        }

        String actual = caught == null ? "no exception" : caught.getClass().getSimpleName();
        System.out.println(label + ": expected " + expected.getSimpleName() + ", actual " + actual);

        if (!expected.isInstance(caught))
            throw new AssertionError(label, caught);
    }
}
