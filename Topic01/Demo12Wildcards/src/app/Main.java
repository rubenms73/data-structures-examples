package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import ds.Circle;
import ds.FlexibleMaximum;
import ds.Rectangle;
import ds.Shape;
import ds.ShapeAlgorithms;
import ds.Square;

public final class Main {
    public static void main(String[] args) {
        List<Rectangle> rectangles = new ArrayList<>(Arrays.asList(
                new Rectangle(2, 3), new Rectangle(1, 10)));
        printCount(rectangles); // Collection<?> accepts any element type.
        printCount(Arrays.asList("pear", "fig"));
        List<? extends Shape> view = rectangles;
        Shape firstShape = view.get(0);
        System.out.println("Read through ? extends Shape: " + firstShape.area());
        // view.add(new Rectangle(2, 3)); // The actual element type is unknown.
        System.out.println("Total rectangle area: " + ShapeAlgorithms.totalArea(rectangles));
        System.out.println("Total square area: "
                + ShapeAlgorithms.totalArea(Arrays.asList(new Square(2), new Square(3))));

        Comparator<Shape> byArea = (a, b) -> Double.compare(a.area(), b.area());
        Comparator<? super Rectangle> comparator = byArea;
        System.out.println("Rectangle comparison: " + comparator.compare(rectangles.get(0), rectangles.get(1)));
        Rectangle largest = FlexibleMaximum.max(rectangles.toArray(new Rectangle[0]), byArea);
        System.out.println("Largest rectangle area: " + largest.area());
        Shape largestShape = FlexibleMaximum.max(new Shape[] {new Circle(1), largest}, byArea);
        System.out.println("Largest shape area: " + largestShape.area());

        // This lambda is also valid: its target gives a and b type Rectangle.
        Comparator<? super Rectangle> direct = (a, b) -> Double.compare(a.area(), b.area());
        System.out.println("Direct wildcard lambda: " + direct.compare(rectangles.get(0), rectangles.get(1)));

        List<Shape> shapes = new ArrayList<>();
        addRectangle(shapes);
        List<Object> objects = new ArrayList<>();
        objects.add("Existing text");
        addRectangle(objects);
        System.out.println("Destination sizes: " + shapes.size() + ", " + objects.size());
        System.out.println("First Object destination element: " + objects.get(0));

        // Uncomment one line at a time:
        // List<Shape> invalid = rectangles;
        // Comparator<Rectangle> tooNarrow = byArea;
        // comparator.compare(new Circle(1), new Circle(2));
    }

    private static void printCount(Collection<?> values) {
        System.out.println("Count: " + values.size());
        // values.add("extra"); // The element type is unknown.
    }

    public static void addRectangle(Collection<? super Rectangle> destination) {
        destination.add(new Rectangle(2, 3));
        destination.add(new Square(2));
        Object first = destination.iterator().next();
        // Rectangle r = destination.iterator().next(); // May retrieve another supertype value.
        // destination.add(new Circle(1)); // A circle is not a rectangle.
    }
}
