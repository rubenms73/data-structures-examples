"""Run the same stack demonstration as Java, C++ and C#."""

from stack import ArrayStack, Stack


def main() -> None:
    stack: Stack[int] = ArrayStack[int]()
    print(f"Initial stack: size = {stack.size()}; empty = {str(stack.is_empty()).lower()}")
    for value in range(1, 4):
        stack.push(value)
        print(f"push({value}): size = {stack.size()}; top = {stack.peek()}")
    print("Traversal from top to bottom:", end="")
    for value in stack:
        print("", value, end="")
    print()
    print(f"After traversal: size = {stack.size()}; top = {stack.peek()}")
    while not stack.is_empty():
        value = stack.pop()
        top = "(empty)" if stack.is_empty() else stack.peek()
        print(f"pop(): {value}; size = {stack.size()}; top = {top}")
    stack.push(9)
    print(f"Reuse with push(9): size = {stack.size()}; top = {stack.peek()}")
    print(f"pop(): {stack.pop()}; size = {stack.size()}; top = (empty)")


if __name__ == "__main__":
    main()
