package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        PeekingIterator<String> it = new PeekingIterator<>(Arrays.asList("A", null, "B"));
        while (it.hasNext())
        {
            System.out.println("Peek: " + it.peek() + "; next: " + it.next());
        }
    }
}
