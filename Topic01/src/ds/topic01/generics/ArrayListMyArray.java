package ds.topic01.generics;

import java.util.ArrayList;
import java.util.List;

/** Supplies storage for the generic client examples by composition. */
public final class ArrayListMyArray<E> implements MyArray<E> {
    private final List<E> data = new ArrayList<>();

    @Override public int size() { return data.size(); }
    @Override public E get(int index) { return data.get(index); }
    @Override public void set(int index, E value) { data.set(index, value); }
    @Override public boolean add(E value) { return data.add(value); }
}
