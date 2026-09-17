package app;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import ds.FunctionalOperations;

public final class Main
{
    public static void main(String[] args)
    {
        // Predicate: receives a value and returns a boolean.
        Predicate<Integer> even = x -> x % 2 == 0;
        System.out.println("Predicate.test(4): " + even.test(4));
        System.out.println("Predicate.test(5): " + even.test(5));

        // Supplier: receives no arguments and returns a value.
        Supplier<Integer> example = () -> 4;
        Integer value = example.get();
        System.out.println("Supplier.get(): " + value);

        // Function: receives a value and returns a transformed value.
        Function<Integer, String> label = x -> "Number " + x;
        System.out.println("Function.apply(4): " + label.apply(value));

        // Consumer: receives a value, performs an action and returns no value.
        Consumer<String> print = text -> System.out.println("Consumer.accept: " + text);
        print.accept(label.apply(value));

        Integer[] numbers = {1, 2, 3, 4, 5, 6};
        System.out.println("Even values from an array:");
        FunctionalOperations.process(numbers, even, label, print);

        // Reuse the same traversal with different operations.
        System.out.println("Values greater than 4, squared:");
        FunctionalOperations.process(numbers, x -> x > 4,
                x -> "Square " + x * x, print);
    }
}
