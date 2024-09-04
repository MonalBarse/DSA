# DSA (JAVA)

## Structure

- `src/`: Contains all the Java source files.
- `bin/`: Contains the compiled Java class files.

```bash

    git clone https://github.com/MonalBarse/DSA
```

## Bash Scripts

### compile.sh

A script to compile all Java files in the `src/` directory and place the compiled `.class` files in the `bin/` directory.

### run.sh

A script to compile the Java files using `compile.sh` and then run a specified Java class.

## Add a ./compile.sh file to each directory

- for eg. in Recusion directory, add a compile.sh file

```bash

    #!/bin/bash

    # Directory paths
    SRC_DIR="src"
    BIN_DIR="bin"

    # Create the bin directory if it doesn't exist
    mkdir -p $BIN_DIR

    # Compile all .java files in src/ and place .class files into bin/
    javac -d $BIN_DIR $SRC_DIR/com/monal/*.java

    # Check if compilation was successful
    if [ $? -eq 0 ]; then
    echo "Compilation successful."
    else
    echo "Compilation failed."
    fi


```

- Create a run.sh file in the same directory

```bash


#!/bin/bash

# Compile Java files using compile.sh
./compile.sh

# Check if compilation was successful
if [ $? -eq 0 ]; then
    # Navigate to the bin/ directory
    cd ../bin

    # Run the specified Java class
    java com.monal.$1

    # Navigate back to the original directory
    cd -
else
    echo "Compilation failed. Please fix errors before running."
fi

```

- Make sure both compile.sh and run.sh have executable permissions (chmod +x compile.sh run.sh).

## How to Use

1. **Compile Java Files**:

   ```sh
   ./Topic/
       ./compile.sh
   ```

2. **Run Class Files**:
   ```sh
   ./Topic/
       ./run.sh Filename
   ```

# CheckList

## Introduction to Java

- [x] Introduction
- [x] How it works
- [x] Setup Installation
- [x] Input and Output in Java

## Conditionals & Loops in Java

- [x] if else
- [x] loops
- [x] Switch statements

## Data types

- [x] Coding best practices

## Functions

- [x] Introduction
- [x] Scoping in Java
- [x] Shadowing
- [x] Variable Length Arguments
- [x] Overloading

## Arrays

- [x] Introduction
- [x] Memory management
- [x] Input and Output
- [x] ArrayList Introduction
- [x] Sorting
  - [x] Insertion Sort
  - [x] Selection Sort
  - [x] Bubble Sort
  - [ ] Cyclic Sort (Merge sort etc after recursion)

## Searching

- [x] Linear Search
- [x] Binary Search
- [x] Modified Binary Search
- [x] Binary Search Interview questions
- [ ] Binary Search on 2D Arrays
- [x] Pattern questions

## Strings

- [x] Introduction
- [x] How Strings work
- [x] Comparison of methods
- [x] Operations in Strings
- [x] StringBuilder in java

## Maths for DSA

- [x] Introduction
- [x] Complete Bitwise Operators
- [ ] Prime numbers
- [ ] HCF / LCM
- [ ] Sieve of Eratosthenes
- [ ] Newton's Square Root Method
- [ ] Number Theory
- [ ] Euclidean algorithm

## Space and Time Complexity Analysis

- [x] Introduction
- [x] Comparison of various cases
- [x] Solving Linear Recurrence Relations
- [x] Solving Divide and Conquer Recurrence Relations
- [x] Big-O, Big-Omega, Big-Theta Notations
- [ ] Get equation of any relation easily - Akra Bazzi formula
- [x] Complexity discussion of all the problems we do
- [ ] Space Complexity
- [ ] Memory Allocation of various languages
- [ ] NP Completeness and Hardness

## Recursion

- [x] Introduction
- [x] Why recursion?
- [x] Flow of recursive programs - stacks
- [x] Convert recursion to iteration
- [x] Tree building of function calls
- [ ] Tail recursion
- [x] Sorting
  - [x] Merge Sort
  - [x] Quick Sort

## Backtracking

- [x] Sudoku Solver
- [x] N-Queens
- [x] N-Knights
- [x] Maze problems
- [x] Recursion String Problems
- [x] Recursion Array Problems
- [x] Recursion Pattern Problems
- [x] Subset Questions
- [ ] Recursion - Permutations, Dice Throws etc Questions

## Object Oriented Programming

- [x] Introduction
- [x] Classes & its instances
- [x] this keyword in Java
- [x] Properties
- [ ] Inheritance
- [ ] Abstraction
- [ ] Polymorphism
- [ ] Encapsulation
- [ ] Overloading & Overriding
- [x] Static & Non-Static
- [ ] Access Control
- [ ] Interfaces
- [ ] Abstract Classes
- [ ] Singleton Class
- [ ] final, finalize, finally
- [ ] Exception Handling

## Linked List

- [ ] Introduction
- [ ] Singly and Doubly Linked List
- [ ] Circular Linked List
- [ ] Fast and slow pointer
- [ ] Cycle Detection
- [ ] Reversing of LinkedList
- [ ] Linked List Interview questions

## Stacks & Queues

- [ ] Introduction
- [ ] Interview problems
- [ ] Push efficient
- [ ] Pop efficient
- [ ] Queue using Stack and Vice versa
- [ ] Circular Queue

## Dynamic Programming

- [ ] Introduction
- [ ] Recursion + Recursion DP + Iteration + Iteration Space Optimized
- [ ] Complexity Analysis
- [ ] 0/1 Knapsack
- [ ] Subset Questions
- [ ] Unbounded Knapsack
- [ ] Subseq questions
- [ ] String DP

## Trees

- [ ] Introduction
- [ ] Binary Trees
- [ ] Binary Search Trees
- [ ] DFS
- [ ] BFS
- [ ] AVL Trees
- [ ] Segment Tree
- [ ] Fenwick Tree / Binary Indexed Tree

## Heaps

- [ ] Introduction
- [ ] Theory
- [ ] Priority Queue
- [ ] Two Heaps Method
- [ ] k-way merge
- [ ] top k elements
- [ ] interval problems

## Hashmaps

- [ ] Introduction
- [ ] Theory - how it works
- [ ] Comparisons of various forms
- [ ] Limitations and how to solve
- [ ] Map using LinkedList
- [ ] Map using Hash
- [ ] Chaining
- [ ] Probing
- [ ] Huffman-Encoder

## Tries

- [ ] Introduction

## Graphs

- [ ] Introduction
- [ ] BFS
- [ ] DFS
- [ ] Working with graph components
- [ ] Minimum Spanning Trees
- [ ] Kruskal Algorithm
- [ ] Prims Algorithm
- [ ] Dijkstra’s shortest path algorithm
- [ ] Topological Sort
- [ ] Bellman ford
- [ ] A\* pathfinding Algorithm

## Greedy Algorithms

- [ ] Introduction

## Advanced concepts apart from interviews

- [ ] Fast IO
- [ ] File handling
- [ ] Bitwise + DP
- [ ] Extended Euclidean algorithm
- [ ] Modulo Multiplicative Inverse
- [ ] Linear Diophantine Equations
- [ ] Matrix Exponentiation
- [ ] Mathematical Expectation
- [ ] Catalan Numbers
- [ ] Fermat’s Theorem
- [ ] Wilson's Theorem
- [ ] Euler's Theorem
- [ ] Lucas Theorem
- [ ] Chinese Remainder Theorem
- [ ] Euler Totient
- [ ] NP-Completeness
- [ ] Multithreading
- [ ] Fenwick Tree / Binary Indexed Tree
- [ ] Square Root Decomposition
