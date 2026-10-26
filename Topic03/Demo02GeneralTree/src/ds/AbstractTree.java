package ds;

/** Recursive algorithms depend only on the interface, not the representation. */
public abstract class AbstractTree<E> implements Tree<E>
{
    /** Number of nodes in this tree. */
    public int size()
    {
        return size(this);
    }

    private static <E> int size(Tree<E> t)
    {
        int result = 1;
        ChildrenIterator<Tree<E>> itr = t.childrenIterator();
        while (itr.hasNext())
        {
            result += size(itr.next());
        }
        return result;
    }

    /** Height counts nodes on a longest root-to-leaf path: a leaf has height one. */
    public int height()
    {
        return height(this);
    }

    private static <E> int height(Tree<E> t)
    {
        int result = 1;
        ChildrenIterator<Tree<E>> itr = t.childrenIterator();
        while (itr.hasNext())
        {
            int candidate = 1 + height(itr.next());
            if (candidate > result)
                result = candidate;
        }
        return result;
    }

    /**
     * Returns the ordered hierarchy with the root first, one node per line.
     * ASCII branches show parent-child relationships. This general tree
     * has no binary left/right links. The method only uses the Tree interface.
     * Recursion follows tree height and preserves child order.
     */
    public String toTreeString()
    {
        StringBuilder result = new StringBuilder();
        appendTree(this, "", true, true, result);
        return result.toString();
    }

    private void appendTree(Tree<E> node, String prefix, boolean last, boolean root,
            StringBuilder result)
    {
        result.append(prefix);
        if (root)
            result.append("+-- ");
        else if (last)
            result.append("\\-- ");
        else
            result.append("+-- ");
        result.append(String.valueOf(node.label()).replace("\r", "\\r")
                .replace("\n", "\\n").replace("\t", "\\t"));
        if (root)
            result.append(" [ROOT]");
        result.append('\n');
        String childPrefix = prefix;
        if (!root)
            childPrefix += last ? "    " : "|   ";
        ChildrenIterator<Tree<E>> itr = node.childrenIterator();
        while (itr.hasNext())
        {
            Tree<E> child = itr.next();
            appendTree(child, childPrefix, !itr.hasNext(), false, result);
        }
    }
}
