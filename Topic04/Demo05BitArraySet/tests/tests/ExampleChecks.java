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

    public static void main(String[] args)
    {
        // Random operations compared with a TreeSet, which also iterates in letter order.
        Random random = new Random(4);
        for (int round = 0; round < 2000; round++)
        {
            LetterSet set = new LetterSet();
            TreeSet<Character> ref = new TreeSet<>();
            for (int step = 0; step < 40; step++)
            {
                char c = (char) ('a' + random.nextInt(26));
                int operation = random.nextInt(4);
                if (operation <= 1)
                    equal(ref.add(c), set.add(c));
                else if (operation == 2)
                    equal(ref.remove(c), set.remove(c));
                else
                {
                    Iterator<Character> mine = set.iterator();
                    Iterator<Character> theirs = ref.iterator();
                    while (mine.hasNext())
                    {
                        char value = mine.next();
                        equal(theirs.next(), value);
                        if (value % 3 == 0)
                        {
                            mine.remove();
                            theirs.remove();
                        }
                    }
                    equal(false, theirs.hasNext());
                }
                equal(ref.size(), set.size());
                equal(ref.contains(c), set.contains(c));
                equal(ref, set);
                equal(ref.hashCode(), set.hashCode());
                equal(ref.toString(), set.toString());
            }
        }

        LetterSet set = new LetterSet();
        set.addAll(List.of('a', 'z'));
        rejects(IllegalArgumentException.class, () -> set.add('A'));
        rejects(IllegalArgumentException.class, () -> set.add('{'));
        rejects(NullPointerException.class, () -> set.add(null));
        rejects(NullPointerException.class, () -> set.contains(null));
        rejects(NullPointerException.class, () -> set.remove(null));
        equal(false, set.contains('A'));
        equal(false, set.remove('{'));
        equal(2, set.size());

        Iterator<Character> it = set.iterator();
        rejects(IllegalStateException.class, it::remove);
        equal('a', it.next());
        it.remove();
        rejects(IllegalStateException.class, it::remove);
        equal('z', it.next());
        rejects(NoSuchElementException.class, it::next);
        equal(List.of('z'), new ArrayList<>(set));

        set.clear();
        equal(0, set.size());
        equal(false, set.iterator().hasNext());
        rejects(IllegalArgumentException.class, () -> new BitArraySet<Integer>(0)
        {
            protected int index(Integer e)
            {
                return e;
            }

            protected Integer element(int index)
            {
                return index;
            }
        });
        System.out.println("All " + checks + " checks passed.");
    }
}
