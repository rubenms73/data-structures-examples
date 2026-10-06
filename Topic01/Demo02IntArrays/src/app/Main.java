package app;

import ds.AbstractMyIntArray;
import ds.ArrayAlgorithms;
import ds.DynamicMyIntArray;
import ds.FixedMyIntArray;
import ds.MyIntArray;

public final class Main
{
    public static void main(String[] args)
    {
        MyIntArray fixed = new FixedMyIntArray(3);
        MyIntArray dynamic = new DynamicMyIntArray(3);
        System.out.println("Both arrays start with capacity 3 and size 0.");
        System.out.println("Fill the fixed array:");
        fill(fixed);
        System.out.println("Fill the dynamic array:");
        fill(dynamic);
        show("Fixed", fixed);
        show("Dynamic", dynamic);

        fixed.set(1, 10);
        System.out.println("After set(1, 10): " + ArrayAlgorithms.contents(fixed));
        System.out.println("size = " + fixed.size() + ", contains(10) = " + fixed.contains(10));

        try
        {
            fixed.add(16);
        }
        catch (IllegalStateException e)
        {
            System.out.println("Fixed add(16): " + e.getClass().getSimpleName());
        }
        System.out.println("Fixed still contains: " + ArrayAlgorithms.contents(fixed));
        dynamic.add(16);
        show("Dynamic after add(16)", dynamic);

        // Uncomment one line at a time to discuss compile-time checks:
        // MyIntArray invalid = new AbstractMyIntArray();
        // System.out.println(dynamic.data.length);
    }

    private static void fill(MyIntArray values)
    {
        for (int value : new int[] {4, 8, 12})
        {
            values.add(value);
            System.out.println("  add(" + value + "): " + ArrayAlgorithms.contents(values)
                    + "; size = " + values.size());
        }
    }

    private static void show(String label, MyIntArray values)
    {
        System.out.println(label + ": " + ArrayAlgorithms.contents(values)
                + "; size = " + values.size() + "; sum = " + ArrayAlgorithms.sum(values));
    }
}

