package ds;

/** Selected observer operations from the main presentation. */
public interface Rational
{
    /** Returns the numerator of this representation. */
    int numerator();

    /** Returns the nonzero denominator of this representation. */
    int denominator();

    /** Returns the quotient of numerator and denominator as a double. */
    double value();
}
