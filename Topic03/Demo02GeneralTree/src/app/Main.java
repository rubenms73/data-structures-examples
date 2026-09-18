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
        System.out.println("Company hierarchy:");
        System.out.println();
        System.out.print(company.toTreeString());
        System.out.println();
        for (String label : company)
        {
            System.out.println(label);
        }
        System.out.println("Nodes: " + company.size() + "; height: " + company.height());
        company.removeChild(1);
        System.out.println();
        System.out.println("============================================================");
        System.out.println("After removing Support:");
        System.out.println();
        System.out.print(company.toTreeString());
        System.out.println();
    }
}
