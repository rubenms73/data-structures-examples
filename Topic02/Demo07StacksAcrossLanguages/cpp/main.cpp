#include "ListStack.h"
#include <iostream>

int main()
{
    ListStack<int> storage;
    Stack<int>& stack = storage;
    std::cout << "Initial stack: size = " << stack.size() << "; empty = " << std::boolalpha << stack.isEmpty() << '\n';
    // Automatic lifetime avoids explicit new/delete in the client program.
    for (int value = 1; value <= 3; value++)
    {
        stack.push(value);
        std::cout << "push(" << value << "): size = " << stack.size() << "; top = " << stack.peek() << '\n';
    }
    std::cout << "Traversal from top to bottom:";
    // Range-based for uses begin/end, dereferencing and incrementing STL iterators.
    // The concrete storage supplies traversal; Stack<int>& supplies ADT operations.
    for (const auto& value : storage)
    {
        std::cout << " " << value;
    }
    std::cout << '\n';
    std::cout << "After traversal: size = " << stack.size() << "; top = " << stack.peek() << '\n';
    while (!stack.isEmpty())
    {
        std::cout << "pop(): " << stack.pop() << "; size = " << stack.size() << "; top = ";
        if (stack.isEmpty())
        {
            std::cout << "(empty)";
        }
        else
        {
            std::cout << stack.peek();
        }
        std::cout << '\n';
    }
    stack.push(9);
    std::cout << "Reuse with push(9): size = " << stack.size() << "; top = " << stack.peek() << '\n';
    std::cout << "pop(): " << stack.pop() << "; size = " << stack.size() << "; top = (empty)\n";
}
