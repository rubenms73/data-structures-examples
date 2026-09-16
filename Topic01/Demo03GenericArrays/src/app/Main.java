package app;

import ds.ArrayListMyArray;
import ds.GenericAlgorithms;
import ds.MyArray;

public final class Main
{
    public static void main(String[] args)
    {
        MyArray<String> names = new ArrayListMyArray<>();
        names.add("Ana");
        names.add("Ruben");
        MyArray<Integer> scores = new ArrayListMyArray<>();
        scores.add(8);
        scores.add(10);

        String name = GenericAlgorithms.first(names);
        Integer score = GenericAlgorithms.first(scores);
        System.out.println("First name: " + name);
        System.out.println("First score: " + score);

        // Uncomment one line at a time:
        // names.add(8);
        // MyArray<Object> objects = names;

        Object[] mixed = {"Ana", 8};
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
