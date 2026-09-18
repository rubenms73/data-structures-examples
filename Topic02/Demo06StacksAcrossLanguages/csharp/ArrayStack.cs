using System;
using System.Collections;
using System.Collections.Generic;

namespace StackExample
{
    /// <summary>A growing array stack. Null elements are allowed.</summary>
    public class ArrayStack<T> : IStack<T>
    {
        private T[] data;
        private int size;

        public ArrayStack() : this(10)
        {
        }

        public ArrayStack(int capacity)
        {
            if (capacity < 0)
                throw new ArgumentOutOfRangeException(nameof(capacity));
            data = new T[capacity];
        }

        public void Push(T item)
        {
            if (size == data.Length)
            {
                // Growing from zero must produce a positive capacity.
                // Guard the multiplication against integer overflow.
                if (data.Length > int.MaxValue / 2)
                    throw new InvalidOperationException("Capacity limit reached");
                int capacity = data.Length == 0 ? 1 : data.Length * 2;
                T[] larger = new T[capacity];
                Array.Copy(data, larger, size);
                data = larger;
            }
            data[size++] = item;
        }

        public T Pop()
        {
            if (IsEmpty())
                throw new InvalidOperationException("Stack is empty");
            T item = data[--size];
            // Clear unused cells, including references to removed objects.
            data[size] = default(T);
            return item;
        }

        public T Peek()
        {
            if (IsEmpty())
                throw new InvalidOperationException("Stack is empty");
            return data[size - 1];
        }

        public int Size()
        {
            return size;
        }

        public bool IsEmpty()
        {
            return size == 0;
        }

        // The compiler creates the enumerator state machine from yield return.
        // Do not modify the stack while traversing it.
        public IEnumerator<T> GetEnumerator()
        {
            for (int index = size - 1; index >= 0; index--)
            {
                yield return data[index];
            }
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }
    }
}
