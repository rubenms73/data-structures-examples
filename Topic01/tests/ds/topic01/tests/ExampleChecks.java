package ds.topic01.tests;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import ds.topic01.arrays.ArrayAlgorithms;
import ds.topic01.arrays.DynamicMyIntArray;
import ds.topic01.arrays.FixedMyIntArray;
import ds.topic01.arrays.MyIntArray;
import ds.topic01.collections.ArrayBag;
import ds.topic01.comparison.LengthComparator;
import ds.topic01.comparison.Maximum;
import ds.topic01.demos.Demo09Costs;
import ds.topic01.demos.Demo12Wildcards;
import ds.topic01.generics.ArrayListMyArray;
import ds.topic01.generics.GenericAlgorithms;
import ds.topic01.generics.MyArray;
import ds.topic01.iteration.Fibonacci;
import ds.topic01.iteration.IntRange;
import ds.topic01.movies.Movie;
import ds.topic01.movies.RatingComparator;
import ds.topic01.movies.TitleComparator;
import ds.topic01.rational.Rational;
import ds.topic01.rational.RationalImp1;
import ds.topic01.rational.RationalImp2;
import ds.topic01.wildcards.Circle;
import ds.topic01.wildcards.FlexibleMaximum;
import ds.topic01.wildcards.Rectangle;
import ds.topic01.wildcards.Shape;
import ds.topic01.wildcards.ShapeAlgorithms;
import ds.topic01.wildcards.Square;

/** Behaviour checks for the teaching examples. No external test library is needed. */
public final class ExampleChecks {
    private static int checks;

    public static void main(String[] args) {
        rational();
        arrays();
        generics();
        comparison();
        ranges();
        bags();
        wildcards();
        fibonacci();
        equal("C", Demo09Costs.middle(Arrays.asList("A", "B", "C", "D", "E")));
        System.out.println("All " + checks + " checks passed.");
    }

    private static void rational() {
        for (int n : new int[] {0, 3, -7, Integer.MIN_VALUE}) {
            for (int d : new int[] {4, -2, Integer.MAX_VALUE}) {
                for (Rational r : new Rational[] {new RationalImp1(n, d), new RationalImp2(n, d)}) {
                    equal(n, r.numerator());
                    equal(d, r.denominator());
                    equal((double) n / d, r.value());
                }
            }
        }
        throwsType(IllegalArgumentException.class, () -> new RationalImp1(1, 0));
        throwsType(IllegalArgumentException.class, () -> new RationalImp2(1, 0));
    }

    private static void arrays() {
        for (MyIntArray a : new MyIntArray[] {new FixedMyIntArray(3), new DynamicMyIntArray(0)}) {
            equal(true, a.isEmpty());
            equal(false, a.contains(0));
            throwsType(IndexOutOfBoundsException.class, () -> a.get(0));
            throwsType(IndexOutOfBoundsException.class, () -> a.set(0, 1));
            a.add(4); a.add(8); a.add(12);
            equal(3, a.size());
            equal(24, ArrayAlgorithms.sum(a));
            equal(true, a.contains(8));
            a.set(1, 10);
            equal(3, a.size());
            equal(10, a.get(1));
            equal(false, a.contains(8));
            throwsType(IndexOutOfBoundsException.class, () -> a.get(-1));
            throwsType(IndexOutOfBoundsException.class, () -> a.get(a.size()));
            throwsType(IndexOutOfBoundsException.class, () -> a.set(a.size(), 9));
        }
        FixedMyIntArray fixed = new FixedMyIntArray(1);
        fixed.add(7);
        throwsType(IllegalStateException.class, () -> fixed.add(9));
        equal(1, fixed.size());
        equal(7, fixed.get(0));
        FixedMyIntArray zero = new FixedMyIntArray(0);
        throwsType(IllegalStateException.class, () -> zero.add(1));
        equal(0, zero.size());
        throwsType(IllegalArgumentException.class, () -> new FixedMyIntArray(-1));
        throwsType(IllegalArgumentException.class, () -> new DynamicMyIntArray(-1));

        // Compare mixed updates with an independent library sequence across many resizes.
        Random random = new Random(2026);
        DynamicMyIntArray dynamic = new DynamicMyIntArray(0);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int value = random.nextInt(200) - 100;
            dynamic.add(value);
            expected.add(value);
            if (i % 3 == 0) {
                int index = random.nextInt(expected.size());
                dynamic.set(index, -value);
                expected.set(index, -value);
            }
            equal(expected.size(), dynamic.size());
            equal(expected.contains(value), dynamic.contains(value));
        }
        for (int i = 0; i < expected.size(); i++) equal(expected.get(i), dynamic.get(i));
    }

    private static void generics() {
        MyArray<String> names = new ArrayListMyArray<>();
        throwsType(IllegalArgumentException.class, () -> GenericAlgorithms.first(names));
        equal(true, names.add("Ana"));
        equal("Ana", GenericAlgorithms.first(names));
        names.set(0, "Ruben");
        equal("Ruben", names.get(0));
        equal(1, names.size());
        names.add(null);
        equal(null, names.get(1));
        throwsType(IndexOutOfBoundsException.class, () -> names.get(2));
        throwsType(IndexOutOfBoundsException.class, () -> names.set(-1, "x"));
        MyArray<Integer> scores = new ArrayListMyArray<>();
        scores.add(8);
        Integer first = GenericAlgorithms.first(scores);
        equal(8, first);
    }

    private static void comparison() {
        Comparator<String> length = new LengthComparator();
        String[] words = {"pear", "banana", "fig"};
        equal("banana", Maximum.max(words, length));
        equal("pear", Maximum.max(words, Comparator.naturalOrder()));
        equal("pear", words[0]);
        equal("pear", Maximum.max(new String[] {"pear", "plum"}, length));
        throwsType(IllegalArgumentException.class, () -> Maximum.max(new String[0], length));
        String[] expected = {"fig", "pear", "banana"};
        Arrays.sort(words, length);
        equal(Arrays.asList(expected), Arrays.asList(words));

        Movie early = new Movie("First", Integer.MIN_VALUE, 5);
        Movie late = new Movie("Last", Integer.MAX_VALUE, 5);
        check(early.compareTo(late) < 0, "year comparison must not overflow");
        List<Movie> movies = Arrays.asList(early, late, new Movie("A", 2018, 8),
                new Movie("B", 2018, 8), new Movie("A", 2018, 9), new Movie("A", 2018, 8));
        for (Movie a : movies) for (Movie b : movies) {
            equal(a.equals(b), a.compareTo(b) == 0);
            if (a.equals(b)) equal(a.hashCode(), b.hashCode());
        }
        for (Comparator<Movie> order : Arrays.asList(Comparator.<Movie>naturalOrder(),
                new RatingComparator(), new TitleComparator())) {
            for (Movie a : movies) for (Movie b : movies) {
                equal(Integer.signum(order.compare(a, b)), -Integer.signum(order.compare(b, a)));
                for (Movie c : movies) {
                    if (order.compare(a, b) <= 0 && order.compare(b, c) <= 0)
                        check(order.compare(a, c) <= 0, "transitive comparison");
                }
            }
        }
        throwsType(IllegalArgumentException.class, () -> new Movie("Bad", 2020, Double.NaN));
    }

    private static void ranges() {
        IntRange range = new IntRange(2, 5);
        equal(Arrays.asList(2, 3, 4), collect(range));
        Iterator<Integer> a = range.iterator();
        Iterator<Integer> b = range.iterator();
        equal(true, a.hasNext());
        equal(true, a.hasNext());
        equal(2, a.next()); equal(3, a.next()); equal(2, b.next()); equal(4, a.next());
        equal(false, a.hasNext());
        throwsType(NoSuchElementException.class, a::next);
        throwsType(NoSuchElementException.class, a::next);
        throwsType(UnsupportedOperationException.class, b::remove);
        equal(Arrays.asList(), collect(new IntRange(4, 4)));
        throwsType(NoSuchElementException.class, new IntRange(4, 4).iterator()::next);
        throwsType(IllegalArgumentException.class, () -> new IntRange(5, 4));
        equal(Arrays.asList(Integer.MAX_VALUE - 1), collect(new IntRange(Integer.MAX_VALUE - 1, Integer.MAX_VALUE)));
        equal(Arrays.asList(-2, -1, 0), collect(new IntRange(-2, 1)));
    }

    private static void bags() {
        List<String> source = new ArrayList<>(Arrays.asList("pear", null, "pear"));
        ArrayBag<String> bag = new ArrayBag<>(source);
        source.clear();
        equal(Arrays.asList("pear", null, "pear"), collect(bag));
        equal(3, bag.size());
        equal(true, bag.contains(null));
        equal(true, bag.contains("pear"));
        equal(false, bag.contains("fig"));
        equal(false, bag.isEmpty());
        equal(Arrays.asList("pear", null, "pear"), Arrays.asList(bag.toArray(new String[0])));
        throwsType(UnsupportedOperationException.class, () -> bag.add("fig"));
        throwsType(UnsupportedOperationException.class, () -> bag.remove("pear"));
        Iterator<String> a = bag.iterator();
        Iterator<String> b = bag.iterator();
        a.next(); a.next(); a.next();
        equal("pear", b.next());
        throwsType(NoSuchElementException.class, a::next);
        ArrayBag<String> empty = new ArrayBag<>(new ArrayList<>());
        equal(true, empty.isEmpty());
        throwsType(NoSuchElementException.class, empty.iterator()::next);
        StringBuilder element = new StringBuilder("a");
        ArrayBag<StringBuilder> references = new ArrayBag<>(Arrays.asList(element));
        element.append("b");
        equal("ab", references.iterator().next().toString());
    }

    private static void wildcards() {
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
        Demo12Wildcards.addRectangle(destination);
        equal(3, destination.size());
        equal("Already present", destination.get(0));
        check(destination.get(1) instanceof Rectangle, "Rectangle added to Object destination");
    }

    private static void fibonacci() {
        for (int n : new int[] {0, 1, 2, 3, 10, 92, 93}) {
            List<Long> terms = collect(new Fibonacci(n));
            equal(n, terms.size());
            BigInteger a = BigInteger.ZERO, b = BigInteger.ONE;
            for (long term : terms) {
                equal(a.longValueExact(), term);
                BigInteger next = a.add(b); a = b; b = next;
            }
        }
        throwsType(IllegalArgumentException.class, () -> new Fibonacci(-1));
        throwsType(IllegalArgumentException.class, () -> new Fibonacci(94));
        Iterator<Long> a = new Fibonacci(1).iterator();
        equal(0L, a.next());
        throwsType(NoSuchElementException.class, a::next);
        Fibonacci f = new Fibonacci();
        equal(10, collect(f).size());
        Iterator<Long> first = f.iterator(), second = f.iterator();
        first.next(); first.next();
        equal(0L, second.next());
    }

    private static <T> List<T> collect(Iterable<T> values) {
        List<T> result = new ArrayList<>();
        for (T value : values) result.add(value);
        return result;
    }

    private static void equal(Object expected, Object actual) {
        check(Objects.equals(expected, actual), "Expected " + expected + ", got " + actual);
    }

    private static void check(boolean condition, String message) {
        checks++;
        if (!condition) throw new AssertionError(message);
    }

    private static void throwsType(Class<? extends Throwable> expected, Runnable action) {
        checks++;
        try {
            action.run();
        } catch (Throwable actual) {
            if (expected.isInstance(actual)) return;
            throw new AssertionError("Expected " + expected.getSimpleName() + ", got " + actual, actual);
        }
        throw new AssertionError("Expected " + expected.getSimpleName());
    }
}
