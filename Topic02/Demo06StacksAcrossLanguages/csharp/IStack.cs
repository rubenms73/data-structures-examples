using System.Collections.Generic;

namespace StackExample
{
    // Both foreach and the explicit enumerator traverse from top to bottom.
    public interface IStack<T> : IEnumerable<T>
    {
        void Push(T item);
        T Pop();
        T Peek();
        int Size();
        bool IsEmpty();
    }
}
