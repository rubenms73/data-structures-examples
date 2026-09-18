"""A typed stack contract and a growing-array implementation.

The annotations support static analysis. Python does not enforce the generic
argument at runtime. None is a valid element when the chosen type permits it.
"""

from abc import ABC, abstractmethod
from collections.abc import Iterator
from typing import Generic, TypeVar

T = TypeVar("T")


class Stack(ABC, Generic[T]):
    """Last-in, first-out operations and top-to-bottom iteration."""

    @abstractmethod
    def push(self, item: T) -> None:
        """Insert one occurrence at the top."""
        raise NotImplementedError

    @abstractmethod
    def pop(self) -> T:
        """Remove the top, or raise IndexError if empty."""
        raise NotImplementedError

    @abstractmethod
    def peek(self) -> T:
        """Observe the top, or raise IndexError if empty."""
        raise NotImplementedError

    @abstractmethod
    def size(self) -> int:
        raise NotImplementedError

    @abstractmethod
    def is_empty(self) -> bool:
        raise NotImplementedError

    @abstractmethod
    def __iter__(self) -> Iterator[T]:
        """Create an independent traversal without removing elements."""
        raise NotImplementedError


class ArrayStack(Stack[T]):
    """Python's list supplies a growing array; its last element is the top.

    The list remains private by convention. Do not modify the stack during a
    traversal. Stored objects are shared references and are not cloned.
    """

    def __init__(self) -> None:
        self._data: list[T] = []

    def push(self, item: T) -> None:
        self._data.append(item)

    def pop(self) -> T:
        if self.is_empty():
            raise IndexError("Stack is empty")
        return self._data.pop()

    def peek(self) -> T:
        if self.is_empty():
            raise IndexError("Stack is empty")
        return self._data[-1]

    def size(self) -> int:
        return len(self._data)

    def is_empty(self) -> bool:
        return len(self._data) == 0

    def __iter__(self) -> Iterator[T]:
        # Like C# yield return, yield suspends this loop after each element.
        # Each call creates its own generator and therefore its own index.
        for index in range(len(self._data) - 1, -1, -1):
            yield self._data[index]
