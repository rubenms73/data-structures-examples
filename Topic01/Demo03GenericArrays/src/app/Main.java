package app;

import ds.FixedMyArray;
import ds.GenericAlgorithms;
import ds.MyArray;

public final class Main
{
    public static void main(String[] args)
    {
        MyArray<String> names = new FixedMyArray<>(4);
        names.add("Ana");
        names.add("Ruben");
        MyArray<Integer> scores = new FixedMyArray<>(4);
        scores.add(8);
        scores.add(10);

        System.out.println("MyArray<String>: " + names.get(0) + ", " + names.get(1)
                + "; size = " + names.size());
        System.out.println("MyArray<Integer>: " + scores.get(0) + ", " + scores.get(1)
                + "; size = " + scores.size());
        System.out.println("The same generic first() method preserves each element type:");
        String name = GenericAlgorithms.first(names);
        Integer score = GenericAlgorithms.first(scores);
        System.out.println("First name: " + name);
        System.out.println("First score: " + score);

        // Uncomment one line at a time:
        // names.add(8);
        // MyArray<Object> objects = names;

        Object[] mixed = {"Ana", 8};
        System.out.println("Object[] contents: " + java.util.Arrays.toString(mixed));
        System.out.println("Attempt to cast the Integer at index 1 to String:");
        try
        {
            String second = (String) mixed[1];
            System.out.println(second);
        }
        catch (ClassCastException e)
        {
            System.out.println("Object[] accepts mixed values; the wrong cast fails at run time.");
        }
    }
}

