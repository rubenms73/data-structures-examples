package app;

import ds.ArrayStack;
import ds.Stack;

public class Main
{
    public static void main(String[] args)
    {
        Stack<Integer> stack = new ArrayStack<>(3);
        for (int value = 1; value <= 3; value++)
        {
            stack.push(value);
        }
        System.out.println("Size: " + stack.size());
        System.out.println("Top: " + stack.peek());
        while (!stack.isEmpty())
        {
            System.out.println("Pop: " + stack.pop());
        }
        System.out.println("Size: " + stack.size());
    }
}
