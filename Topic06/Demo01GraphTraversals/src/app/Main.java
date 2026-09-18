package app;

import ds.Graph;
import ds.Traversals;

/** Runs BFS and DFS on the exact directed graph used in the theory presentation. */
public final class Main
{
    private Main()
    {
    }

    public static void main(String[] args)
    {
        Graph<String> graph = ExampleGraph.create();
        System.out.println("Directed graph (outgoing neighbours):");
        for (String vertex : graph.vertices())
        {
            System.out.println(vertex + " -> " + graph.neighbours(vertex));
        }
        show("BFS from A", Traversals.breadthFirst(graph, "A"));
        show("DFS from A", Traversals.depthFirst(graph, "A"));
        show("BFS forest", Traversals.breadthFirstForest(graph));
        show("DFS forest", Traversals.depthFirstForest(graph));
        System.out.println("Parent entries mean child=parent; roots have no entry.");
        System.out.println("BFS minimises the number of arcs from one source, not weighted cost.");
    }

    private static void show(String title, Traversals.Result<String> result)
    {
        System.out.println();
        System.out.println(title + ": " + result.order());
        System.out.println("Parents: " + result.parent());
    }
}
