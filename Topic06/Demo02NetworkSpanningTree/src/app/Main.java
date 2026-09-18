package app;

import ds.Network;
import ds.Prim;

/** Selects links for a small campus network, then simulates one unavailable link. */
public final class Main
{
    private Main()
    {
    }

    public static void main(String[] args)
    {
        Network network = ExampleNetwork.create();
        System.out.println("Campus network: fictional installation costs in hundreds of euros.");
        System.out.println("Available undirected links (fictional installation cost units):");
        long allCosts = 0;
        for (Network.Link link : network.links())
        {
            showLink(network, link);
            allCosts += link.cost();
        }
        System.out.println("Installing every link: " + allCosts);
        showTree("Minimum spanning tree", network);
        System.out.println("\nIncident: Library -- Lab is unavailable.");
        network.removeLink(1, 2);
        showTree("Recomputed minimum spanning tree", network);
        network.addLink(1, 2, 1);
        showTree("Link restored", network);
        System.out.println("\nA tree has no redundant route: one selected link failure disconnects it.");
        System.out.println("Recomputation assumes the other candidate links are available.");
        System.out.println("Minimum total installation cost is not minimum latency from the server.");
    }

    private static void showTree(String title, Network network)
    {
        Prim.Result result = Prim.minimumSpanningTree(network);
        System.out.println("\n" + title + ":");
        for (Network.Link link : result.links())
        {
            showLink(network, link);
        }
        System.out.println("Selected links: " + result.links().size()
                + " for " + network.size() + " nodes");
        System.out.println("Total cost: " + result.totalCost());
    }

    private static void showLink(Network network, Network.Link link)
    {
        System.out.println("  " + network.name(link.from()) + " -- "
                + network.name(link.to()) + " : " + link.cost());
    }
}
