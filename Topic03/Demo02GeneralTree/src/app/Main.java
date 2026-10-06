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
        System.out.println("addChild(team) copies the subtree into Company.");
        System.out.println("Original team label before change: " + team.label());
        team.setLabel("Changed outside");
        System.out.println("Original team label after change: " + team.label());
        System.out.println("The Company copy retains the Team label:");
        System.out.println("Company hierarchy:");
        System.out.println();
        System.out.print(company.toTreeString());
        System.out.println();
        System.out.println("Preorder traversal: parent before its children.");
        for (String label : company)
        {
            System.out.println("Visit: " + label);
        }
        System.out.println("Nodes: " + company.size() + "; height: " + company.height());
        System.out.println("removeChild(1) returns: " + company.removeChild(1).label());
        System.out.println();
        System.out.println("============================================================");
        System.out.println("After removing Support: nodes = " + company.size() + "; height = " + company.height());
        System.out.println();
        System.out.print(company.toTreeString());
        System.out.println();
    }
}

