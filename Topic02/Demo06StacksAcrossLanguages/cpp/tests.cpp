#include "ListStack.h"
#include <iostream>
#include <string>

void check(bool condition)
{
    if (!condition)
        throw std::logic_error("Unexpected stack behaviour");
}

int main()
{
    ListStack<int> stack;
    check(stack.isEmpty() && stack.size() == 0);
    bool rejected = false;
    try
    {
        stack.pop();
    }
    catch (const std::underflow_error&)
    {
        rejected = true;
    }
    check(rejected);
    rejected = false;
    try
    {
        stack.peek();
    }
    catch (const std::underflow_error&)
    {
        rejected = true;
    }
    check(rejected);
    for (int value = 0; value < 100; value++)
    {
        stack.push(value);
    }
    ListStack<int> copy = stack;
    check(copy.pop() == 99 && stack.size() == 100);
    const Stack<int>& view = stack;
    check(view.peek() == 99);
    for (int value = 99; value >= 0; value--)
    {
        check(stack.peek() == value && stack.pop() == value);
    }
    check(stack.isEmpty());
    stack.push(7);
    check(stack.pop() == 7);
    ListStack<std::string> words;
    words.push("first");
    words.push("second");
    check(words.pop() == "second" && words.pop() == "first");
    std::cout << "All C++ checks passed.\n";
}
