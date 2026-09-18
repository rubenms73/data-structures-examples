"""Behaviour checks using the standard library only."""

import unittest
from stack import ArrayStack, Stack


class StackChecks(unittest.TestCase):
    def test_empty_operations(self) -> None:
        stack = ArrayStack[int]()
        self.assertTrue(stack.is_empty())
        self.assertEqual(0, stack.size())
        with self.assertRaises(IndexError):
            stack.pop()
        with self.assertRaises(IndexError):
            stack.peek()
        with self.assertRaises(StopIteration):
            next(iter(stack))

    def test_growth_lifo_and_reuse(self) -> None:
        stack: Stack[int] = ArrayStack[int]()
        for cycle in range(3):
            for value in range(100):
                stack.push(value)
            self.assertEqual(100, stack.size())
            for value in range(99, -1, -1):
                self.assertEqual(value, stack.peek())
                self.assertEqual(value, stack.pop())
            self.assertTrue(stack.is_empty())

    def test_independent_iterators(self) -> None:
        stack = ArrayStack[int]()
        for value in (1, 2, 2):
            stack.push(value)
        first = iter(stack)
        second = iter(stack)
        self.assertEqual(2, next(first))
        self.assertEqual(2, next(first))
        self.assertEqual(2, next(second))
        self.assertEqual(1, next(first))
        with self.assertRaises(StopIteration):
            next(first)
        self.assertEqual([2, 2, 1], list(stack))
        self.assertEqual(3, stack.size())

    def test_none_and_false_values_are_elements(self) -> None:
        stack = ArrayStack[int | None]()
        stack.push(0)
        stack.push(None)
        self.assertEqual([None, 0], list(stack))
        self.assertIsNone(stack.peek())
        self.assertIsNone(stack.pop())
        self.assertEqual(0, stack.pop())

    def test_elements_are_shared_references(self) -> None:
        stack = ArrayStack[list[int]]()
        item = [1]
        stack.push(item)
        item.append(2)
        self.assertIs(item, stack.peek())
        self.assertEqual([1, 2], stack.pop())


if __name__ == "__main__":
    unittest.main()
