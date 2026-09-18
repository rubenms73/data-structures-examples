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
};

#endif
