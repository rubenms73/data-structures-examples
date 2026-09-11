package ds.topic01.demos;

import ds.topic01.arrays.AbstractMyIntArray;
import ds.topic01.arrays.ArrayAlgorithms;
import ds.topic01.arrays.DynamicMyIntArray;
import ds.topic01.arrays.FixedMyIntArray;
import ds.topic01.arrays.MyIntArray;

public final class Demo02IntArrays {
    public static void main(String[] args) {
        MyIntArray fixed = new FixedMyIntArray(3);
        MyIntArray dynamic = new DynamicMyIntArray(3);
        fill(fixed);
        fill(dynamic);
        show("Fixed", fixed);
        show("Dynamic", dynamic);

        fixed.set(1, 10);
        System.out.println("After set(1, 10): " + ArrayAlgorithms.contents(fixed));
        System.out.println("size = " + fixed.size() + ", contains(10) = " + fixed.contains(10));

        try {
            fixed.add(16);
        } catch (IllegalStateException e) {
            System.out.println("Fixed add(16): " + e.getClass().getSimpleName());
        }
        System.out.println("Fixed still contains: " + ArrayAlgorithms.contents(fixed));
        dynamic.add(16);
        show("Dynamic after add(16)", dynamic);

        // Uncomment one line at a time to discuss compile-time checks:
        // MyIntArray invalid = new AbstractMyIntArray();
        // System.out.println(dynamic.data.length);
    }

    private static void fill(MyIntArray values) {
        values.add(4);
        values.add(8);
        values.add(12);
    }

    private static void show(String label, MyIntArray values) {
        System.out.println(label + ": " + ArrayAlgorithms.contents(values)
                + "; sum = " + ArrayAlgorithms.sum(values));
    }
}
