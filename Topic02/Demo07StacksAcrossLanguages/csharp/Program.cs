using System;

namespace StackExample
{
    public class Program
    {
        public static void Main(string[] args)
        {
            if (args.Length == 1 && args[0] == "test")
            {
                ExampleChecks.Run();
                return;
            }
            IStack<int> stack = new ArrayStack<int>(3);
            Console.WriteLine("Initial stack: size = " + stack.Size() + "; empty = " + stack.IsEmpty().ToString().ToLowerInvariant());
            for (int value = 1; value <= 3; value++)
            {
                stack.Push(value);
                Console.WriteLine("push(" + value + "): size = " + stack.Size() + "; top = " + stack.Peek());
            }
            Console.Write("Traversal from top to bottom:");
            foreach (int value in stack)
            {
                Console.Write(" " + value);
            }
            Console.WriteLine();
            Console.WriteLine("After traversal: size = " + stack.Size() + "; top = " + stack.Peek());
            while (!stack.IsEmpty())
            {
                int value = stack.Pop();
                string top = stack.IsEmpty() ? "(empty)" : stack.Peek().ToString();
                Console.WriteLine("pop(): " + value + "; size = " + stack.Size() + "; top = " + top);
            }
            stack.Push(9);
            Console.WriteLine("Reuse with push(9): size = " + stack.Size() + "; top = " + stack.Peek());
            Console.WriteLine("pop(): " + stack.Pop() + "; size = " + stack.Size() + "; top = (empty)");
        }
    }
}
