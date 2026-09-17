package app;

import java.util.Comparator;
import ds.FixedMyArray;
import ds.MyArray;
import ds.Circle;
import ds.FlexibleMaximum;
import ds.Rectangle;
import ds.Shape;
import ds.ShapeAlgorithms;
import ds.Square;

public final class Main
{
    public static void main(String[] args)
    {
        MyArray<Rectangle> rectangles = new FixedMyArray<>(new Rectangle[2]);
        rectangles.add(new Rectangle(2, 3));
        rectangles.add(new Rectangle(1, 10));
        printCount(rectangles); // MyArray<?> accepts any element type.
        MyArray<String> words = new FixedMyArray<>(new String[2]);
        words.add("pear");
        words.add("fig");
        printCount(words);
        MyArray<? extends Shape> view = rectangles;
        Shape firstShape = view.get(0);
        System.out.println("Read through ? extends Shape: " + firstShape.area());
        // view.add(new Rectangle(2, 3)); // The actual element type is unknown.
        System.out.println("Total rectangle area: " + ShapeAlgorithms.totalArea(rectangles));
        MyArray<Square> squares = new FixedMyArray<>(new Square[2]);
        squares.add(new Square(2));
        squares.add(new Square(3));
        System.out.println("Total square area: " + ShapeAlgorithms.totalArea(squares));

        Comparator<Shape> byArea = (a, b) -> Double.compare(a.area(), b.area());
        Comparator<? super Rectangle> comparator = byArea;
        System.out.println("Rectangle comparison: " + comparator.compare(rectangles.get(0), rectangles.get(1)));
        Rectangle largest = FlexibleMaximum.max(new Rectangle[] {rectangles.get(0), rectangles.get(1)}, byArea);
        System.out.println("Largest rectangle area: " + largest.area());
        Shape largestShape = FlexibleMaximum.max(new Shape[] {new Circle(1), largest}, byArea);
        System.out.println("Largest shape area: " + largestShape.area());

        // This lambda is also valid: its target gives a and b type Rectangle.
        Comparator<? super Rectangle> direct = (a, b) -> Double.compare(a.area(), b.area());
        System.out.println("Direct wildcard lambda: " + direct.compare(rectangles.get(0), rectangles.get(1)));

        MyArray<Shape> shapes = new FixedMyArray<>(new Shape[2]);
        addRectangle(shapes);
        MyArray<Object> objects = new FixedMyArray<>(new Object[3]);
        objects.add("Existing text");
        addRectangle(objects);
        System.out.println("Destination sizes: " + shapes.size() + ", " + objects.size());
        System.out.println("First Object destination element: " + objects.get(0));

        // Uncomment one line at a time:
        // MyArray<Shape> invalid = rectangles;
        // Comparator<Rectangle> tooNarrow = byArea;
        // comparator.compare(new Circle(1), new Circle(2));
    }

    private static void printCount(MyArray<?> values)
    {
        System.out.println("Count: " + values.size());
        // values.add("extra"); // The element type is unknown.
    }

    public static void addRectangle(MyArray<? super Rectangle> destination)
    {
        destination.add(new Rectangle(2, 3));
        destination.add(new Square(2));
        Object first = destination.get(0);
        // Rectangle r = destination.get(0); // May retrieve another supertype value.
        // destination.add(new Circle(1)); // A circle is not a rectangle.
    }
}
