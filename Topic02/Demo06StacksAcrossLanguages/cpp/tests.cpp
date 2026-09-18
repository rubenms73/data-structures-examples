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
    {
        // Iteration also works through the abstract interface.
        const Stack<int>& view = stack;
        std::unique_ptr<Iterator<int>> first = view.iterator();
        std::unique_ptr<Iterator<int>> second = view.iterator();
        check(first->next() == 99 && second->next() == 99);
        for (int value = 98; value >= 0; value--)
        {
            check(first->hasNext() && first->next() == value);
        }
        check(!first->hasNext() && second->next() == 98);
        bool exhausted = false;
        try
        {
            first->next();
        }
        catch (const std::out_of_range&)
        {
            exhausted = true;
        }
        check(exhausted && stack.size() == 100);
        int expected = 99;
        for (int value : stack)
        {
            check(value == expected--);
        }
        check(expected == -1);
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
    {
        auto empty = stack.iterator();
        check(!empty->hasNext());
        bool exhausted = false;
        try
        {
            empty->next();
        }
        catch (const std::out_of_range&)
        {
            exhausted = true;
        }
        check(exhausted);
    }
    stack.push(7);
    check(stack.pop() == 7);
    ListStack<std::string> words;
    words.push("first");
    words.push("second");
    check(words.pop() == "second" && words.pop() == "first");
    std::cout << "All C++ checks passed.\n";
}
