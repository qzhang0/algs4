# 8 Puzzle

## Summary

Write a program to solve the 8-puzzle problem (and its natural generalizations) using the A* search algorithm. Check the programming assignment [specification](http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html) | [local](./8puzzle.html) that describes the assignment requirements.

## Testing

[Tests](../../tests/8puzzle)

## Result

[Report](../../reports/8puzzle_output)

## Note

> __Best-first search__. Now, we describe a solution to the problem that illustrates a general artificial intelligence methodology known as the [A* search algorithm](https://en.wikipedia.org/wiki/A*_search_algorithm). We define a search node of the game to be a board, the number of moves made to reach the board, and the predecessor search node. First, insert the initial search node (the initial board, 0 moves, and a null predecessor search node) into a priority queue. Then, delete from the priority queue the search node with the minimum priority, and insert onto the priority queue all neighboring search nodes (those that can be reached in one move from the dequeued search node). Repeat this procedure until the search node dequeued corresponds to a goal board. The success of this approach hinges on the choice of _priority function_ for a search node. We consider two priority functions:
>
> - _Hamming priority function_. The number of blocks in the wrong position, plus the number of moves made so far to get to the search node. Intuitively, a search node with a small number of blocks in the wrong position is close to the goal, and we prefer a search node that have been reached using a small number of moves.
> - _Manhattan priority function_. The sum of the Manhattan distances (sum of the vertical and horizontal distance) from the blocks to their goal positions, plus the number of moves made so far to get to the search node.

- The `equals()` method will fail if it is inherited by subclasses. Use code like `y.getClass() != this.getClass()` instead of `y.getClass() != Foo.class`
- The inner class 'Solver$SearchNode' should be refactored into a static nested class.
	- A non-static nested class has full access to the members of the class within which it is nested. A static nested class does not have a reference to a nesting instance, so a static nested class cannot invoke non-static methods or access non-static fields of an instance of the class within which it is nested.
	- Each instance of a nonstatic nested class is implicitly associated with an enclosing instance of its containing class. It is possible to invoke methods on the enclosing instance.
	- A static nested class does not have access to the enclosing instance. It uses less space too.

- `equals()`. Java has some arcane rules for implementing `equals()`, discussed on p. 103 of Algorithms, 4th edition. Note that the argument to `equals()` is required to be Object. Inspect [Date.java](https://algs4.cs.princeton.edu/12oop/Date.java.html) or [Transaction.java](https://algs4.cs.princeton.edu/12oop/Transaction.java.html) for online examples.
- `twin()`. Use it to determine whether a puzzle is solvable: exactly one of a board and its twin are solvable. A twin is obtained by swapping any pair of blocks (the blank square is not a block). `Solver` will use only one twin.