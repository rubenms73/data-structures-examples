package ds.topic01.arrays;

/** Shared operations expressed only in terms of size() and get(). */
public abstract class AbstractMyIntArray implements MyIntArray {
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean contains(int value) {
        for (int i = 0; i < size(); i++)
            if (get(i) == value) return true;
        return false;
    }
}
