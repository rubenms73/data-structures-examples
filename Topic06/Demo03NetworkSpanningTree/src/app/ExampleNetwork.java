package app;

import ds.Network;

/** Six campus buildings and nine possible cable connections. */
public final class ExampleNetwork
{
    private ExampleNetwork()
    {
    }

    /** Builds a fresh network. Costs are fictional, in hundreds of euros. */
    public static Network create()
    {
        Network network = new Network(new String[]
        {
            "Server", "Library", "Lab", "Office", "Classroom", "Workshop"
        });
        network.addLink(0, 1, 4);
        network.addLink(0, 2, 3);
        network.addLink(1, 2, 1);
        network.addLink(1, 3, 2);
        network.addLink(2, 3, 4);
        network.addLink(2, 4, 5);
        network.addLink(3, 4, 2);
        network.addLink(3, 5, 6);
        network.addLink(4, 5, 3);
        return network;
    }
}
