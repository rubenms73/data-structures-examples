package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        SinglyLinkedList<String> list = new SinglyLinkedList<>();
        list.add("A");
        list.add("C");
        list.insert(1, "B");
        for (String value : list)
        {
            System.out.println(value);
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext())
        {
            if (it.next().equals("C"))
                it.remove();
        }
        list.add("D");
        System.out.println("Last after removal and append: " + list.get(2));
    }
}
