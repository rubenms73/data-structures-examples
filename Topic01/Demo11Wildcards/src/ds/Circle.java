package ds;

public final class Circle implements Shape
{
    private final double radius;

    public Circle(double radius)
    {
        if (!Double.isFinite(radius) || radius < 0)
            throw new IllegalArgumentException("Radius must be finite and nonnegative");
        this.radius = radius;
    }

    @Override
    public double area()
    {
        return Math.PI * radius * radius;
    }
}
