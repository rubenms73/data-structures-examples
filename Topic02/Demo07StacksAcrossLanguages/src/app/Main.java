package app;

import ds.ArrayStack;
import ds.Stack;

public class Main
{
    public static void main(String[] args)
    {
        Stack<Integer> stack = new ArrayStack<>(3);
        System.out.println("Initial stack: size = " + stack.size() + "; empty = " + stack.isEmpty());
        for (int value = 1; value <= 3; value++)
        {
            stack.push(value);
            System.out.println("push(" + value + "): size = " + stack.size() + "; top = " + stack.peek());
        }
        System.out.print("Traversal from top to bottom:");
        for (int value : stack)
        {
            System.out.print(" " + value);
        }
        System.out.println();
        System.out.println("After traversal: size = " + stack.size() + "; top = " + stack.peek());
        while (!stack.isEmpty())
        {
            System.out.println("pop(): " + stack.pop() + "; size = " + stack.size()
                    + "; top = " + (stack.isEmpty() ? "(empty)" : stack.peek()));
        }
        stack.push(9);
        System.out.println("Reuse with push(9): size = " + stack.size() + "; top = " + stack.peek());
        System.out.println("pop(): " + stack.pop() + "; size = " + stack.size() + "; top = (empty)");
    }
}
