package tests;

import java.util.*;
import java.math.BigInteger;
import ds.*;

public final class ExampleChecks
{
    private static int checks;
    public static void main(String[] args)
    {
        wildcards();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void wildcards()
    {
        Rectangle a = new Rectangle(2, 3), b = new Rectangle(1, 10);
        Comparator<Shape> byArea = (x, y) -> Double.compare(x.area(), y.area());
        Comparator<? super Rectangle> usable = byArea;
        check(usable.compare(a, b) < 0, "Shape comparator accepts Rectangle arguments");
        Comparator<? super Rectangle> direct = (x, y) -> Double.compare(x.area(), y.area());
        check(direct.compare(a, b) < 0, "wildcard lambda has Rectangle parameters");
        Rectangle largest = FlexibleMaximum.max(new Rectangle[] {a, b}, byArea);
        check(largest == b, "the return type remains Rectangle");
        equal(16.0, ShapeAlgorithms.totalArea(Arrays.asList(a, b)));
        equal(13.0, ShapeAlgorithms.totalArea(Arrays.asList(new Square(2), new Square(3))));
        equal(Math.PI, ShapeAlgorithms.totalArea(Arrays.asList(new Circle(1))));
        equal(0.0, ShapeAlgorithms.totalArea(new ArrayList<Shape>()));
        throwsType(IllegalArgumentException.class, () -> FlexibleMaximum.max(new Rectangle[0], byArea));
        List<Object> destination = new ArrayList<>();
        destination.add("Already present");
        app.Main.addRectangle(destination);
        equal(3, destination.size());
        equal("Already present", destination.get(0));
        check(destination.get(1) instanceof Rectangle, "Rectangle added to Object destination");
    }

    private static <T> List<T> collect(Iterable<T> values)
    {
        List<T> result = new ArrayList<>();
        for (T value : values)
        {
            result.add(value);
        }
        return result;
    }

    private static void equal(Object expected, Object actual)
    {
        check(Objects.equals(expected, actual), "Expected " + expected + ", got " + actual);
    }

    private static void check(boolean condition, String message)
    {
        checks++;
        if (!condition)
            throw new AssertionError(message);
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
            throw new AssertionError("Expected " + expected.getSimpleName() + ", got " + actual, actual);
        }
        throw new AssertionError("Expected " + expected.getSimpleName());
    }
}
