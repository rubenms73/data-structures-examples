package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        WeightedGraph<String> graph = new WeightedGraph<>();
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 1);
        graph.addEdge("C", "B", 2);
        graph.addVertex("D");
        System.out.println("Distances: " + graph.distancesFrom("A"));
        System.out.println("Path to B: " + graph.shortestPath("A", "B"));
        System.out.println("Path to D: " + graph.shortestPath("A", "D"));
        WeightedGraph<String> copy = new WeightedGraph<>(graph);
        copy.removeVertex("C");
        System.out.println("Original/copy edges: " + graph.edgeCount() + "/" + copy.edgeCount());
    }
}
