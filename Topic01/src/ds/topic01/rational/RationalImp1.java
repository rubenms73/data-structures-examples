package ds.topic01.rational;

/** A rational number represented by two integer fields. */
public final class RationalImp1 implements Rational {
    private final int num;
    private final int den;

    /** @throws IllegalArgumentException if denominator is zero */
    public RationalImp1(int numerator, int denominator) {
        if (denominator == 0)
            throw new IllegalArgumentException("Zero denominator");
        num = numerator;
        den = denominator;
    }

    @Override public int numerator() { return num; }
    @Override public int denominator() { return den; }
    @Override public double value() { return (double) num / den; }
}
