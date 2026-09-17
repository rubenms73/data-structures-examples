package ds;

public final class ShapeAlgorithms
{
    private ShapeAlgorithms()
    {
    }

    /** Sums the areas of non-null shapes in a non-null generic array. */
    public static double totalArea(MyArray<? extends Shape> shapes)
    {
        double total = 0;
        for (int i = 0; i < shapes.size(); i++)
        {
            total += shapes.get(i).area();
        }
        return total;
    }
}
