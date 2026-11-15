# Binary Search Tree with AVL Balancing

(2022)

A generic Binary Search Tree implementation in Java with AVL-style rebalancing through single and double rotations. The tree can be exported to DOT format and visualized with Graphviz.

## Features

- **Generic implementation**: works with any type that implements `Comparable<E>`
- **Core operations**: insertion, search (`contains`), removal, `size`, `isEmpty`, `clear`
- **AVL balancing**:
  - Balance factor calculated from subtree heights
  - Single rotations (left and right)
  - Double rotations (left-right and right-left)
  - `applyBalancing()` rebalances the whole tree on demand
  - `isBalancedTree()` checks whether every node satisfies the AVL property
- **Traversals**: in-order and breadth-first (level order)
- **Visualization**: `toDOT()` generates a Graphviz DOT representation of the tree
- **Custom exception**: `EmptyTreeException` for operations on an empty tree

## Technologies

- Java (no external dependencies)
- Graphviz / DOT language for tree visualization

## Project Structure

```
├── App.java                 # Usage example: builds, prints and balances a tree
├── BinarySearchTree.java    # Tree implementation and balancing logic
└── EmptyTreeException.java  # Exception thrown on empty-tree access
```

## How to Run

Requires a JDK (Java 8 or later).

```bash
git clone <repo-url>
cd BinaryTreeAVL
javac *.java
java App
```

The program prints the tree in DOT format before and after balancing, then shows whether the tree is balanced.

## Visualizing the Tree

Copy the DOT output and paste it into an online Graphviz viewer, such as:

- https://dreampuf.github.io/GraphvizOnline
- http://viz-js.com/

## Example

```java
BinarySearchTree<Integer> tree = new BinarySearchTree<>();
tree.add(10);
tree.add(6);
tree.add(12);

tree.applyBalancing();
System.out.println(tree.isBalancedTree()); // true
System.out.println(tree.inOrder());        // [6, 10, 12]
System.out.println(tree.toDOT());
```
