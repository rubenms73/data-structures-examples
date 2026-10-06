using System;
using System.Collections;
using System.Collections.Generic;

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
            // Native enumerators maintain independent positions and are disposable.
            using (IEnumerator<int> first = stack.GetEnumerator())
            using (IEnumerator<int> second = stack.GetEnumerator())
            {
                Check(first.MoveNext() && first.Current == 99);
                Check(first.MoveNext() && first.Current == 98);
                Check(second.MoveNext() && second.Current == 99);
                int remaining = 97;
                while (first.MoveNext())
                {
                    Check(first.Current == remaining--);
                }
                Check(remaining == -1 && !first.MoveNext() && stack.Size() == 100);
            }
            for (int value = 99; value >= 0; value--)
            {
                Check(stack.Pop() == value);
            }
            Check(stack.IsEmpty());
            stack.Push(7);
            Check(stack.Pop() == 7);
            stack.Push(2);
            stack.Push(2);
            int duplicates = 0;
            foreach (int value in stack)
            {
                Check(value == 2);
                duplicates++;
            }
            Check(duplicates == 2 && stack.Pop() == 2 && stack.Pop() == 2);
            ArrayStack<string> words = new ArrayStack<string>();
            words.Push("first");
            words.Push(null);
            Check(words.Peek() == null && words.Pop() == null);
            Check(words.Pop() == "first");
            Console.WriteLine("All C# checks passed.");
        }
    }
}
