package ds;

import java.util.Map;
import java.util.TreeMap;

/** Sparse polynomial: only nonzero finite coefficients are stored. */
public class Polynomial
{
    private final TreeMap<Integer, Double> terms = new TreeMap<>();

    public Polynomial()
    {
    }

    public Polynomial(Polynomial source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        terms.putAll(source.terms);
    }

    /** Accumulate a term and return the previous coefficient, or zero. */
    public double addTerm(int exponent, double coefficient)
    {
        if (exponent < 0)
            throw new IllegalArgumentException("Exponent must not be negative");
        if (coefficient != coefficient || coefficient == Double.POSITIVE_INFINITY
            || coefficient == Double.NEGATIVE_INFINITY)
            throw new IllegalArgumentException("Coefficient must be finite");
        double previous = coefficient(exponent);
        double result = previous + coefficient;
        if (result == Double.POSITIVE_INFINITY || result == Double.NEGATIVE_INFINITY)
            throw new ArithmeticException("Coefficient overflow");
        if (result == 0)
            terms.remove(exponent);
        else
            terms.put(exponent, result);
        return previous;
    }

    public double coefficient(int exponent)
    {
        if (exponent < 0)
            throw new IllegalArgumentException("Exponent must not be negative");
        Double value = terms.get(exponent);
        if (value == null)
            return 0;
        return value;
    }

    /** The zero polynomial has degree -1 by this example's convention. */
    public int degree()
    {
        if (terms.isEmpty())
            return -1;
        return terms.lastKey();
    }

    public double evaluate(double x)
    {
        if (x != x || x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY)
            throw new IllegalArgumentException("Argument must be finite");
        double result = 0;
        for (Map.Entry<Integer, Double> term : terms.entrySet())
        {
            result += term.getValue() * Math.pow(x, term.getKey());
        }
        return result;
    }

    /** Produces a new polynomial; neither operand is changed. */
    public Polynomial plus(Polynomial other)
    {
        if (other == null)
            throw new NullPointerException("Operand must not be null");
        Polynomial result = new Polynomial(this);
        for (Map.Entry<Integer, Double> term : other.terms.entrySet())
        {
            result.addTerm(term.getKey(), term.getValue());
        }
        return result;
    }

    @Override
    public String toString()
    {
        // Exponent-to-coefficient notation exposes the sparse representation.
        return terms.toString();
    }
}
