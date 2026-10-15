package ds;

public class Rectangle implements Shape
{
    private final double width;
    private final double height;

    public Rectangle(double width, double height)
    {
        if (!Double.isFinite(width) || !Double.isFinite(height)
                || width < 0 || height < 0)
            throw new IllegalArgumentException("Dimensions must be finite and nonnegative");
        this.width = width;
        this.height = height;
    }

    @Override
    public double area()
    {
        return width * height;
    }
}
