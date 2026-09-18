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
        Polynomial p = new Polynomial();
        equal(-1, p.degree());
        equal(0.0, p.addTerm(2, 3));
        equal(3.0, p.addTerm(2, -3));
        equal(-1, p.degree());
        rejects(IllegalArgumentException.class, () -> p.addTerm(-1, 1));
        rejects(IllegalArgumentException.class, () -> p.addTerm(1, Double.NaN));
        rejects(IllegalArgumentException.class, () -> p.addTerm(1, Double.POSITIVE_INFINITY));
        p.addTerm(0, 1);
        p.addTerm(2, 3);
        Polynomial copy = new Polynomial(p);
        copy.addTerm(2, -3);
        equal(3.0, p.coefficient(2));
        equal(13.0, p.evaluate(2));
        Polynomial sum = p.plus(copy);
        equal(14.0, sum.evaluate(2));
        equal(2.0, sum.coefficient(0));
        equal(3.0, sum.coefficient(2));
        rejects(NullPointerException.class, () -> p.plus(null));
        System.out.println("All " + checks + " checks passed.");
    }
}
