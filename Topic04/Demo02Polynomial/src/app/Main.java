package app;

import ds.Polynomial;

public final class Main
{
    public static void main(String[] args)
    {
        System.out.println("Sparse notation: {exponent=coefficient}; absent exponents have coefficient zero.");
        Polynomial first = new Polynomial();
        System.out.println("Initial polynomial: " + first + "; degree = " + first.degree());
        System.out.println("addTerm(2, 3), previous coefficient: " + first.addTerm(2, 3)
                + "; first = " + first);
        System.out.println("addTerm(0, 1), previous coefficient: " + first.addTerm(0, 1)
                + "; first = " + first);
        Polynomial second = new Polynomial();
        second.addTerm(2, -3);
        second.addTerm(1, 2);
        System.out.println("First: " + first + "; degree = " + first.degree());
        System.out.println("Second: " + second + "; degree = " + second.degree());

        // plus() creates a new polynomial and accumulates equal exponents.
        Polynomial sum = first.plus(second);
        System.out.println("\nSum: " + sum + "; degree = " + sum.degree());
        System.out.println("Coefficient of x^2 after cancellation: " + sum.coefficient(2));
        System.out.println("Operands after plus(): first = " + first + "; second = " + second);
        System.out.println("At x = 2: first = " + first.evaluate(2)
                + "; second = " + second.evaluate(2) + "; sum = " + sum.evaluate(2));
        System.out.println("sum.addTerm(1, -2), previous coefficient: " + sum.addTerm(1, -2)
                + "; sum = " + sum);
        System.out.println("sum.addTerm(0, -1), previous coefficient: " + sum.addTerm(0, -1)
                + "; sum = " + sum + "; degree = " + sum.degree());
        System.out.println("Zero polynomial at x = 2: " + sum.evaluate(2));
    }
}
