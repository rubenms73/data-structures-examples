package tests;

import ds.ArrayStack;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ExampleChecks
{
    private static int checks;

    private static void check(boolean condition)
    {
        if (!condition)
            throw new AssertionError("Unexpected stack behaviour");
        checks++;
    }

    private static void expect(Class<? extends RuntimeException> type, Runnable action)
    {
        try
        {
            action.run();
        }
        catch (RuntimeException exception)
        {
            check(type.isInstance(exception));
            return;
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }

    public static void main(String[] args)
    {
        expect(IllegalArgumentException.class, () -> new ArrayStack<Integer>(-1));
        ArrayStack<Integer> zero = new ArrayStack<>(0);
        check(zero.isEmpty());
        expect(IllegalStateException.class, () -> zero.push(1));
        ArrayStack<Integer> stack = new ArrayStack<>(3);
        expect(NoSuchElementException.class, stack::pop);
        expect(NoSuchElementException.class, stack::peek);
        expect(NoSuchElementException.class, () -> stack.iterator().next());
        stack.push(1);
        stack.push(null);
        stack.push(3);
        check(stack.size() == 3 && stack.peek() == 3);
        expect(IllegalStateException.class, () -> stack.push(4));
        check(stack.size() == 3 && stack.peek() == 3);
        Iterator<Integer> iterator = stack.iterator();
        check(iterator.next() == 3);
        expect(UnsupportedOperationException.class, iterator::remove);
        check(iterator.next() == null);
        check(iterator.next() == 1 && !iterator.hasNext());
        expect(NoSuchElementException.class, iterator::next);
        check(stack.pop() == 3);
        check(stack.pop() == null);
        check(stack.pop() == 1 && stack.isEmpty());
        for (int cycle = 0; cycle < 100; cycle++)
        {
            for (int value = 0; value < 3; value++)
            {
                stack.push(value);
            }
            for (int value = 2; value >= 0; value--)
            {
                check(stack.peek() == value && stack.pop() == value);
            }
            check(stack.size() == 0);
        }
        System.out.println("All " + checks + " checks passed.");
    }
}
