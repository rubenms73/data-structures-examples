#include "ListStack.h"
#include <algorithm>
#include <iostream>
#include <iterator>
#include <numeric>
#include <type_traits>
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
        const ListStack<int>& view = stack;
        auto first = view.begin();
        auto second = view.cbegin();
        check(*first == 99 && *second == 99);
        ++first;
        for (int value = 98; value >= 0; value--)
        {
            check(first != view.end() && *first == value);
            ++first;
        }
        check(first == view.end() && *second == 99);
        ++second;
        check(*second == 98 && stack.size() == 100);
        // Standard algorithms accept the very same iterator pair.
        check(std::distance(view.begin(), view.end()) == 100);
        check(std::accumulate(view.cbegin(), view.cend(), 0) == 4950);
        check(std::find(view.begin(), view.end(), 42) != view.end());
        // Dereferencing end is invalid in STL: exhaustion is tested by comparison.
        static_assert(std::is_same<decltype(*view.begin()), const int&>::value,
            "Stack traversal must expose read-only references");
        int expected = 99;
        for (const auto& value : view)
        {
            check(value == expected--);
        }
        check(expected == -1 && stack.size() == 100);
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
    check(stack.begin() == stack.end());
    check(std::distance(stack.cbegin(), stack.cend()) == 0);
    stack.push(7);
    check(stack.pop() == 7);
    stack.push(2);
    stack.push(2);
    check(std::count(stack.begin(), stack.end(), 2) == 2);
    check(stack.pop() == 2 && stack.pop() == 2);
    ListStack<std::string> words;
    words.push("first");
    words.push("second");
    check(words.pop() == "second" && words.pop() == "first");
    std::cout << "All C++ checks passed.\n";
}
