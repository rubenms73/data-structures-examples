"""Run the same stack demonstration as Java, C++ and C#."""

from stack import ArrayStack, Stack


def main() -> None:
    stack: Stack[int] = ArrayStack[int]()
    for value in range(1, 4):
        stack.push(value)
    print("Size:", stack.size())
    print("Top:", stack.peek())
    print("Traversal:", end="")
    for value in stack:
        print("", value, end="")
    print()
    while not stack.is_empty():
        print("Pop:", stack.pop())
    print("Size:", stack.size())


if __name__ == "__main__":
    main()
