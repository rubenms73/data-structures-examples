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

    // A nested class keeps traversal state separate from the stack itself.
    class StackIterator : public Iterator<T>
    {
    private:
        typename std::list<T>::const_iterator current;
        typename std::list<T>::const_iterator finish;

    public:
        explicit StackIterator(const std::list<T>& source)
            : current(source.cbegin()), finish(source.cend())
        {
        }

        bool hasNext() const override
        {
            return current != finish;
        }

        const T& next() override
        {
            if (!hasNext())
                throw std::out_of_range("Iterator is exhausted");
            const T& item = *current;
            ++current;
            return item;
        }
    };

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

    // Do not change or destroy the stack while this iterator is in use.
    std::unique_ptr<Iterator<T>> iterator() const override
    {
        return std::make_unique<StackIterator>(data);
    }

    // Standard C++ traversal is available on the concrete class as well.
    // const_iterator prevents changing stored values through the traversal.
    typename std::list<T>::const_iterator begin() const
    {
        return data.cbegin();
    }

    typename std::list<T>::const_iterator end() const
    {
        return data.cend();
    }

    bool isEmpty() const override
    {
        return data.empty();
    }
};

#endif
