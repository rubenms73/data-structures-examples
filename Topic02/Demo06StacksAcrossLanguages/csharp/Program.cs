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
            for (int value = 1; value <= 3; value++)
            {
                stack.Push(value);
            }
            Console.WriteLine("Size: " + stack.Size());
            Console.WriteLine("Top: " + stack.Peek());
            while (!stack.IsEmpty())
            {
                Console.WriteLine("Pop: " + stack.Pop());
            }
            Console.WriteLine("Size: " + stack.Size());
        }
    }
}
