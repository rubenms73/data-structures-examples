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
    while (!stack.isEmpty())
    {
        std::cout << "Pop: " << stack.pop() << '\n';
    }
    std::cout << "Size: " << stack.size() << '\n';
}
