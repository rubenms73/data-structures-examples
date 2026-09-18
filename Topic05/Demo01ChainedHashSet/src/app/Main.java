package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        ChainedHashSet<Integer> set = new ChainedHashSet<>(3, 0.75);
        set.add(-1);
        set.add(Integer.MIN_VALUE);
        set.add(7);
        System.out.println("Contains minimum integer: " + set.contains(Integer.MIN_VALUE));
        Iterator<Integer> it = set.iterator();
        while (it.hasNext())
        {
            it.next();
            it.remove();
        }
        System.out.println("Size after iterator removal: " + set.size());
    }
}
