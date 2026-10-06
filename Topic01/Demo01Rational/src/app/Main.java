package app;

import ds.Rational;
import ds.RationalImp1;
import ds.RationalImp2;

public final class Main
{
    public static void main(String[] args)
    {
        Rational a = new RationalImp1(3, 4);
        Rational b = new RationalImp2(3, 4);
        System.out.println("Same Rational interface, two representations:");
        System.out.print("RationalImp1 (two fields): ");
        show(a);
        System.out.print("RationalImp2 (array): ");
        show(b);

        System.out.println("\nReference identity versus numeric value:");
        Rational r = a; // A second reference to the same object.
        System.out.println("r and a refer to the same object: " + (r == a));
        System.out.println("a and b refer to the same object: " + (a == b));
        System.out.println("a and b return the same value: " + (a.value() == b.value()));

        System.out.println("\nConstructing 3/0 violates the denominator invariant:");
        try
        {
            new RationalImp1(3, 0);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Rejected: " + e.getMessage());
        }
    }

    // This client only uses operations declared by Rational.
    private static void show(Rational r)
    {
        System.out.println(r.numerator() + "/" + r.denominator() + " = " + r.value());
    }
}

