#ifndef LIST_STACK_H
#define LIST_STACK_H

#include "Stack.h"
#include <list>
#include <stdexcept>

// The front of the list is the top of the stack.
// std::list manages its nodes and supplies independent container copies.
template <class T>
class ListStack : public Stack<T>
{
private:
    std::list<T> data;

public:
    // Reuse the STL iterator type. typename marks a type dependent on T.
    // A const iterator permits reading elements without modifying their values.
    using const_iterator = typename std::list<T>::const_iterator;

    void push(const T& item) override
    {
        data.push_front(item);
    }

    T pop() override
    {
        if (isEmpty())
            throw std::underflow_error("Stack is empty");
        // Copy before removing the node: its value would otherwise be destroyed.
        T item = data.front();
        data.pop_front();
        return item;
    }

    // This reference is invalid after its element is removed or the stack dies.
    const T& peek() const override
    {
        if (isEmpty())
            throw std::underflow_error("Stack is empty");
        return data.front();
    }

    std::size_t size() const override
    {
        return data.size();
    }

    bool isEmpty() const override
    {
        return data.empty();
    }

    // The standard pair supports range-based for and STL algorithms.
    // begin denotes the top; end denotes the position past the bottom.
    // Iterators do not own storage: keep the stack alive and unchanged in a traversal.
    const_iterator begin() const noexcept
    {
        return data.cbegin();
    }

    const_iterator end() const noexcept
    {
        return data.cend();
    }

    const_iterator cbegin() const noexcept
    {
        return begin();
    }

    const_iterator cend() const noexcept
    {
        return end();
    }
};

#endif
