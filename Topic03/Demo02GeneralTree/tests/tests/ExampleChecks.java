package tests;

import ds.*;
import java.util.*;

public final class ExampleChecks
{
    private static int checks;

    private static void equal(Object expected, Object actual)
    {
        checks++;
        if (!Objects.equals(expected, actual))
            throw new AssertionError("Expected " + expected + ", got " + actual);
    }

    private static void rejects(Class<? extends Exception> type, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Exception e)
        {
            if (type.isInstance(e))
                return;
            throw new AssertionError(e);
        }
        throw new AssertionError("Expected " + type);
    }

    private static <E> List<E> collect(Iterable<E> source)
    {
        List<E> result = new ArrayList<>();
        for (E value : source)
        {
            result.add(value);
        }
        return result;
    }

    public static void main(String[] args)
    {
        ListTree<String> child = new ListTree<>("child");
        equal(1, child.height());
        child.addChild(new ListTree<String>("leaf"));
        ListTree<String> root = new ListTree<>("root");
        root.addChild(child);
        child.setLabel("changed");
        equal(Arrays.asList("root", "child", "leaf"), collect(root));
        equal(3, root.size());
        equal(3, root.height());
        ListTree<String> copy = new ListTree<>((Tree<String>) root);
        copy.removeChild(0);
        equal(1, copy.size());
        equal(1, copy.height());
        equal(3, root.size());
        rejects(IndexOutOfBoundsException.class, () -> root.removeChild(1));
        rejects(NullPointerException.class, () -> root.addChild(null));
        rejects(NullPointerException.class, () -> root.setLabel(null));
        Iterator<String> it = copy.iterator();
        equal("root", it.next());
        rejects(NoSuchElementException.class, it::next);
        rejects(UnsupportedOperationException.class, it::remove);
        System.out.println("All " + checks + " checks passed.");
    }
}
