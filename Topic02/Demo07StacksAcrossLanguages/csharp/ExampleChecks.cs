using System;
using System.Collections;

namespace StackExample
{
    public static class ExampleChecks
    {
        private static void Check(bool condition)
        {
            if (!condition)
                throw new Exception("Unexpected stack behaviour");
        }

        private static void Expect<T>(Action action) where T : Exception
        {
            try
            {
                action();
            }
            catch (T)
            {
                return;
            }
            throw new Exception("Expected " + typeof(T).Name);
        }

        public static void Run()
        {
            Expect<ArgumentOutOfRangeException>(() => new ArrayStack<int>(-1));
            ArrayStack<int> stack = new ArrayStack<int>(0);
            Check(stack.IsEmpty());
            Expect<InvalidOperationException>(() => stack.Pop());
            Expect<InvalidOperationException>(() => stack.Peek());
            Check(!stack.GetEnumerator().MoveNext());
            for (int value = 0; value < 100; value++)
            {
                stack.Push(value);
            }
            Check(stack.Size() == 100 && stack.Peek() == 99);
            int expected = 99;
            foreach (int value in stack)
            {
                Check(value == expected--);
            }
            Check(expected == -1 && stack.Size() == 100);
            expected = 99;
            foreach (int value in (IEnumerable)stack)
            {
                Check(value == expected--);
            }
            Check(expected == -1);
            for (int value = 99; value >= 0; value--)
            {
                Check(stack.Pop() == value);
            }
            Check(stack.IsEmpty());
            stack.Push(7);
            Check(stack.Pop() == 7);
            ArrayStack<string> words = new ArrayStack<string>();
            words.Push("first");
            words.Push(null);
            Check(words.Peek() == null && words.Pop() == null);
            Check(words.Pop() == "first");
            Console.WriteLine("All C# checks passed.");
        }
    }
}
