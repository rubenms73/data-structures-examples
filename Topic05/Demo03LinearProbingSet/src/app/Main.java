package app;

import ds.ProbingSet;

public final class Main
{
    public static void main(String[] args)
    {
        // Integer keys hash to themselves, so key k starts at slot k mod 7.
        ProbingSet<Integer> set = new ProbingSet<>(7);
        System.out.println("1. Collisions move on to the next slot");
        System.out.println("Initial: " + set);
        for (int key : new int[] {7, 1, 8, 15})
        {
            System.out.println("add(" + key + "), start " + key % 7 + ": "
                    + set.add(key) + "; table = " + set);
        }

        System.out.println("\n2. A removed key leaves a DELETED marker");
        System.out.println("remove(8): " + set.remove(8) + "; table = " + set);
        System.out.println("contains(15): " + set.contains(15)
                + " (the search goes past DEL)");
        System.out.println("contains(8): " + set.contains(8));

        System.out.println("\n3. Insertion reuses the first DELETED slot");
        System.out.println("add(15) again: " + set.add(15) + "; table = " + set);
        System.out.println("add(22), start 1: " + set.add(22) + "; table = " + set);
        System.out.println("size = " + set.size());

        System.out.println("\n4. A full table rejects a new key");
        ProbingSet<Integer> small = new ProbingSet<>(2);
        small.add(1);
        small.add(2);
        try
        {
            small.add(3);
        }
        catch (IllegalStateException e)
        {
            System.out.println("add(3) to " + small + ": " + e.getMessage());
        }
    }
}
