#ifndef STACK_ITERATOR_H
#define STACK_ITERATOR_H

// Course-style traversal interface: the same protocol as the Java example.
// This interface is intentionally separate from the STL iterator protocol.
template <class T>
class Iterator
{
public:
    virtual ~Iterator() = default;
    virtual bool hasNext() const = 0;
    virtual const T& next() = 0;
};

#endif
