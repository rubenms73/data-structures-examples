package ds;

import java.util.Collection;

public final class ShapeAlgorithms
{
    private ShapeAlgorithms()
    {
    }

    /** Sums the areas of non-null shapes in a non-null collection. */
    public static double totalArea(Collection<? extends Shape> shapes)
    {
        double total = 0;
        for (Shape s : shapes)
        {
            total += s.area();
        }
        return total;
    }
}
