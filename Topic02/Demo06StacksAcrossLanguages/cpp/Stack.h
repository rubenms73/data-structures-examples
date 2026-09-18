#ifndef STACK_H
#define STACK_H

#include <cstddef>
#include <memory>
#include "Iterator.h"

// A template describes a family of stack interfaces, one for each element type.
template <class T>
class Stack
{
public:
    virtual ~Stack() = default;
    virtual void push(const T& item) = 0;
    virtual T pop() = 0;
    virtual const T& peek() const = 0;
    virtual std::size_t size() const = 0;
    virtual bool isEmpty() const = 0;
    // The caller owns the traversal object; the stack retains its elements.
    virtual std::unique_ptr<Iterator<T>> iterator() const = 0;
};

#endif
