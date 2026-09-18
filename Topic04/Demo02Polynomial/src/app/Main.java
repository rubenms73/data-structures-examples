package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        Polynomial first = new Polynomial();
        first.addTerm(2, 3);
        first.addTerm(0, 1);
        Polynomial second = new Polynomial();
        second.addTerm(2, -3);
        second.addTerm(1, 2);
        Polynomial sum = first.plus(second);
        System.out.println("First: " + first);
        System.out.println("Sum: " + sum);
        System.out.println("Sum at 2: " + sum.evaluate(2));
    }
}
