#include "ListStack.h"
#include <iostream>

int main()
{
    ListStack<int> storage;
    Stack<int>& stack = storage;
    // Automatic lifetime avoids explicit new/delete in the client program.
    for (int value = 1; value <= 3; value++)
    {
        stack.push(value);
    }
    std::cout << "Size: " << stack.size() << '\n';
    std::cout << "Top: " << stack.peek() << '\n';
    std::cout << "Traversal:";
    // Range-based for uses begin/end, dereferencing and incrementing STL iterators.
    // The concrete storage supplies traversal; Stack<int>& supplies ADT operations.
    for (const auto& value : storage)
    {
        std::cout << " " << value;
    }
    std::cout << '\n';
    while (!stack.isEmpty())
    {
        std::cout << "Pop: " << stack.pop() << '\n';
    }
    std::cout << "Size: " << stack.size() << '\n';
}
