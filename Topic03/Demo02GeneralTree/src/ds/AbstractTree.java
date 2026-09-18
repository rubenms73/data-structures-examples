package ds;

/** Recursive algorithms depend only on the interface, not the representation. */
public abstract class AbstractTree<E> implements Tree<E>
{
    @Override
    public int size()
    {
        int result = 1;
        for (Tree<E> child : children())
        {
            result += child.size();
        }
        return result;
    }

    /** Height counts nodes on a longest root-to-leaf path: a leaf has height one. */
    @Override
    public int height()
    {
        int result = 1;
        for (Tree<E> child : children())
        {
            int candidate = 1 + child.height();
            if (candidate > result)
                result = candidate;
        }
        return result;
    }
}
