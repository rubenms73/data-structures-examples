package ds.topic01.rational;

/** A rational number represented by an array of two integers. */
public final class RationalImp2 implements Rational {
    private final int[] r;

    /** @throws IllegalArgumentException if denominator is zero */
    public RationalImp2(int numerator, int denominator) {
        if (denominator == 0)
            throw new IllegalArgumentException("Zero denominator");
        r = new int[] {numerator, denominator};
    }

    @Override public int numerator() { return r[0]; }
    @Override public int denominator() { return r[1]; }
    @Override public double value() { return (double) r[0] / r[1]; }
}
