package app;

import ds.LetterSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class Main
{
    // Shows the 26 bits of the set, from 'a' to 'z'.
    private static String bits(Set<Character> set)
    {
        StringBuilder result = new StringBuilder();
        for (char c = 'a'; c <= 'z'; c++)
        {
            result.append(set.contains(c) ? '1' : '0');
        }
        return result.toString();
    }

    public static void main(String[] args)
    {
        Set<Character> set = new LetterSet();
        System.out.println("1. Each letter is one position of the bit array");
        System.out.println("Initial: " + set + "; size = " + set.size());
        for (char c : new char[] {'c', 'f', 'c'})
        {
            System.out.println("add(" + c + "): " + set.add(c) + "; set = " + set
                    + "; size = " + set.size());
        }
        System.out.println("            abcdefghijklmnopqrstuvwxyz");
        System.out.println("bits:       " + bits(set));

        System.out.println("\n2. Membership and removal read or write one position");
        System.out.println("contains(c): " + set.contains('c') + "; contains(z): " + set.contains('z'));
        System.out.println("remove(c): " + set.remove('c') + "; set = " + set);
        System.out.println("remove(c) again: " + set.remove('c') + "; set = " + set);

        System.out.println("\n3. A traversal follows the indices, not the insertion order");
        set.addAll(List.of('z', 'a', 'm'));
        System.out.println("After adding z, a and m: " + set);
        Iterator<Character> it = set.iterator();
        it.next();
        it.remove();
        System.out.println("Iterator removes the first element: " + set + "; size = " + set.size());

        System.out.println("\n4. Only the 26 lowercase letters belong to the universe");
        System.out.println("contains(A): " + set.contains('A'));
        try
        {
            set.add('A');
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("add(A): " + e.getMessage() + "; set = " + set);
        }

        System.out.println("\n5. Inherited bulk operations combine sets");
        Set<Character> other = new LetterSet();
        other.addAll(List.of('b', 'f', 'z'));
        Set<Character> union = new LetterSet();
        union.addAll(set);
        union.addAll(other);
        Set<Character> both = new LetterSet();
        both.addAll(set);
        both.retainAll(other);
        System.out.println("set = " + set + "; other = " + other);
        System.out.println("Union: " + union + "; intersection: " + both);
        set.clear();
        System.out.println("clear(): set = " + set + "; size = " + set.size());
    }
}
