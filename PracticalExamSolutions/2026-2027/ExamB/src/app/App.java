package app;

import java.util.*;
import ds.ReadOnlySortedCollection;

public class App
{
    private static final class Job
    {
        final int priority;
        final String name;
        Job(int priority, String name)
        {
            this.priority = priority;
            this.name = name;
        }

        @Override
        public String toString()
        {
            return name + "(" + priority + ")";
        }
    }

    public static void main(String[] args)
    {
        check("Empty, natural order", 0, new ReadOnlySortedCollection<Integer>().size());
        check("Empty, reverse order", 0,
                new ReadOnlySortedCollection<Integer>(Comparator.reverseOrder()).size());
        List<Integer> source = new ArrayList<>(Arrays.asList(5, 2, 5, 3, 2));
        ReadOnlySortedCollection<Integer> natural = new ReadOnlySortedCollection<>(source);
        ReadOnlySortedCollection<Integer> reverse =
                new ReadOnlySortedCollection<>(source, Comparator.reverseOrder());
        check("Natural order and duplicates", "[2, 2, 3, 5, 5]", natural.toString());
        check("Reverse order", "[5, 5, 3, 2, 2]", reverse.toString());
        check("Size", 5, natural.size());
        check("Conversion from read-only source", "[2, 2, 3, 5, 5]",
                new ReadOnlySortedCollection<>(natural).toString());
        check("Conversion changes reverse order to natural", "[2, 2, 3, 5, 5]",
                new ReadOnlySortedCollection<>(reverse).toString());
        check("Source unchanged", "[5, 2, 5, 3, 2]", source.toString());
        source.clear();
        source.add(9);
        check("After changing source", "[2, 2, 3, 5, 5]", natural.toString());

        List<Job> jobs = Arrays.asList(new Job(2, "A"), new Job(1, "B"),
                new Job(2, "C"), new Job(1, "D"));
        ReadOnlySortedCollection<Job> ordered = new ReadOnlySortedCollection<>(jobs,
                (a, b) -> Integer.compare(a.priority, b.priority));
        check("Stable priority order", "[B(1), D(1), A(2), C(2)]", ordered.toString());

        Iterator<Integer> it = natural.iterator();
        List<Integer> visited = new ArrayList<>();
        while (it.hasNext())
            visited.add(it.next());
        check("Iteration", Arrays.asList(2, 2, 3, 5, 5), visited);
        exception("Next at end", NoSuchElementException.class, it::next);
        exception("Iterator removal", UnsupportedOperationException.class, it::remove);
        exception("Insert", UnsupportedOperationException.class, () -> natural.add(8));
        exception("Remove existing element", UnsupportedOperationException.class,
                () -> natural.remove(2));
        exception("Clear", UnsupportedOperationException.class, natural::clear);
        check("After rejected changes", "[2, 2, 3, 5, 5]", natural.toString());
        exception("Null source", NullPointerException.class,
                () -> new ReadOnlySortedCollection<Integer>((Collection<Integer>) null));
        exception("Null element", NullPointerException.class,
                () -> new ReadOnlySortedCollection<>(Arrays.asList(1, null)));
        exception("Non-comparable first element", ClassCastException.class,
                () -> new ReadOnlySortedCollection<>(Arrays.asList(new Object())));
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
