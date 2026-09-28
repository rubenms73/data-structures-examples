package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        Collection<String> values = new SinglyLinkedList<>();
        values.addAll(List.of("A", "B", "C"));
        for (String value : values)
        {
            System.out.println(value);
        }
        Iterator<String> it = values.iterator();
        while (it.hasNext())
        {
            if (it.next().equals("C"))
                it.remove();
        }
        values.add("D");
        System.out.println("After iterator removal and append: " + values);
        System.out.println("Contains B: " + values.contains("B"));
        values.remove("B");
        System.out.println("After inherited remove: " + values);
        values.clear();
        System.out.println("Empty after inherited clear: " + values.isEmpty());
    }
}
