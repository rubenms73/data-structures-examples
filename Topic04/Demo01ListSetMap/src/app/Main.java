package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        ListSet<String> set = new ListSet<>();
        set.add("A");
        set.add("A");
        set.add("B");
        System.out.println("Set: " + set);
        ListMap<String, Integer> map = new ListMap<>();
        map.put("A", 1);
        System.out.println("Previous: " + map.put("A", 2));
        map.put("B", 3);
        map.keySet().remove("B");
        System.out.println("Map after view removal: " + map);
    }
}
