package app;

import java.util.Comparator;
import ds.Graph;

/** Exact directed graph from Topic 6's "Example graph for DFS and BFS" slide. */
public final class ExampleGraph
{
    private ExampleGraph()
    {
    }

    /** Builds all ten vertices and nineteen arcs, with alphabetical traversal order. */
    public static Graph<String> create()
    {
        Graph<String> graph = new Graph<>(Comparator.naturalOrder());
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        graph.addVertex("G");
        graph.addVertex("H");
        graph.addVertex("I");
        graph.addVertex("J");
        graph.addEdge("A", "B");
        graph.addEdge("A", "D");
        graph.addEdge("A", "E");
        graph.addEdge("B", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "A");
        graph.addEdge("E", "B");
        graph.addEdge("E", "D");
        graph.addEdge("F", "G");
        graph.addEdge("F", "I");
        graph.addEdge("F", "J");
        graph.addEdge("G", "E");
        graph.addEdge("G", "H");
        graph.addEdge("H", "C");
        graph.addEdge("H", "D");
        graph.addEdge("I", "D");
        graph.addEdge("I", "H");
        graph.addEdge("J", "G");
        graph.addEdge("J", "I");
        return graph;
    }
}
