package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        ListTree<String> team = new ListTree<>("Team");
        team.addChild(new ListTree<String>("Developer"));
        ListTree<String> company = new ListTree<>("Company");
        company.addChild(team);
        company.addChild(new ListTree<String>("Support"));
        team.setLabel("Changed outside");
        for (String label : company)
        {
            System.out.println(label);
        }
        System.out.println("Nodes: " + company.size() + "; height: " + company.height());
    }
}
