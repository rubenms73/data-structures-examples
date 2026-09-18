package ds;

import java.util.Iterator;

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
     * ASCII branches show parent-child relationships. This general tree
     * has no binary left/right links. The method only uses the Tree interface.
     * Recursion follows tree height and preserves child order; children() may
     * allocate additional snapshots according to the concrete representation.
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
        Iterator<? extends Tree<E>> children = node.children().iterator();
        while (children.hasNext())
        {
            Tree<E> child = children.next();
            appendTree(child, childPrefix, !children.hasNext(), false, result);
        }
    }
}
