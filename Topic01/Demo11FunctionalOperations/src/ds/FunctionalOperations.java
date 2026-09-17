package ds;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/** Uses operations supplied by the caller, without collections or streams. */
public final class FunctionalOperations
{
    private FunctionalOperations()
    {
    }

    /** Tests each value, transforms accepted values and performs an action. */
    public static void process(Integer[] values, Predicate<Integer> condition,
            Function<Integer, String> transform, Consumer<String> action)
    {
        for (Integer value : values)
        {
            if (condition.test(value))
                action.accept(transform.apply(value));
        }
    }
}
