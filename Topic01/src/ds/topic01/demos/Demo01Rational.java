package ds.topic01.demos;

import ds.topic01.rational.Rational;
import ds.topic01.rational.RationalImp1;
import ds.topic01.rational.RationalImp2;

public final class Demo01Rational {
    public static void main(String[] args) {
        Rational a = new RationalImp1(3, 4);
        Rational b = new RationalImp2(3, 4);
        show(a);
        show(b);

        Rational r = a; // A second reference to the same object.
        System.out.println("r and a refer to the same object: " + (r == a));
        System.out.println("a and b refer to the same object: " + (a == b));
        System.out.println("a and b return the same value: " + (a.value() == b.value()));

        try {
            new RationalImp1(3, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }

    // This client only uses operations declared by Rational.
    private static void show(Rational r) {
        System.out.println(r.numerator() + "/" + r.denominator() + " = " + r.value());
    }
}
