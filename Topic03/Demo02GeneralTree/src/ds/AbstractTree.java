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

    /**
     * Returns the ordered hierarchy with the root first, one node per line.
     * Four spaces per depth show parent-child relationships. This general tree
     * has no binary left/right links. The method only uses the Tree interface.
     * Recursion follows tree height and preserves child order; children() may
     * allocate additional snapshots according to the concrete representation.
     */
    public String toTreeString()
    {
        StringBuilder result = new StringBuilder();
        appendTree(this, 0, result);
        return result.toString();
    }

    private void appendTree(Tree<E> node, int depth, StringBuilder result)
    {
        for (int i = 0; i < depth; i++)
        {
            result.append("    ");
        }
        if (depth == 0)
            result.append("ROOT: ");
        else
            result.append("- ");
        result.append(String.valueOf(node.label()).replace("\r", "\\r")
                .replace("\n", "\\n").replace("\t", "\\t")).append('\n');
        for (Tree<E> child : node.children())
        {
            appendTree(child, depth + 1, result);
        }
    }
}
