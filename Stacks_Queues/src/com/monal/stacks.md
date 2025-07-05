# Stack & Queue

## Overview of Data Structures

### Stack (LIFO - Last In, First Out)

A stack is a linear data structure where elements are added and removed from the same end (top). Think of it like a stack of plates - you can only add or remove from the top.

**Key Operations:**

- `push(x)` - Add element to top (O(1))
- `add(x)` - Add element to top (O(1))
- `pop()` - Remove and return top element (O(1))
- `peek()/top()` - Return top element without removing (O(1))
- `isEmpty()` - Check if stack is empty (O(1))

### Queue (FIFO - First In, First Out)

A queue is a linear data structure where elements are added at one end (rear) and removed from the other end (front). Think of it like a line at a store - first person in line is first to be served.

**Key Operations:**

- `offer(x)` - Add element to rear (O(1))
- `enqueue(x)` - Add element to rear (O(1))
- `poll()` - Remove and return front element (O(1))
- `dequeue()` - Remove and return front element (O(1))
- `peek()` - Return front element without removing (O(1))
- `front()` - Return front element without removing (O(1))
- `isEmpty()` - Check if queue is empty (O(1))

### Deque (Double-Ended Queue)

A deque allows insertion and deletion from both ends. It can be used as both a stack and
a queue, providing flexibility for various operations.
**Key Operations:**

- `addFirst(x)` - Add element to front (O(1))
- `addLast(x)` - Add element to rear (O(1))
- `removeFirst()` - Remove and return front element (O(1))
- `removeLast()` - Remove and return rear element (O(1))
- `peekFirst()` - Return front element without removing (O(1))
- `peekLast()` - Return rear element without removing (O(1))

### Priority Queue

A priority queue is a special type of queue where each element has a priority. Elements with higher
priority are served before elements with lower priority. It is often implemented using a heap.
**Key Operations:**

- Defination =>
  PriorityQueue<T> pq = new PriorityQueue<>(); - by default, it is a min-heap
  PriorityQueue<T> pq = new PriorityQueue<>(Collections.reverseOrder()); - max-heap
  PriorityQueue<T> pq = new PriorityQueue<>(a,b -> a.x - b.x); - custom comparator this one is for min-heap
  PriorityQueue<T> pq = new PriorityQueue<>(a,b -> b.x - a.x); - custom comparator this one is for max-heap
- `offer(x)` - Add element with priority (O(log n))
- `enqueue(x)` - Add element with priority (O(log n))
- `poll()` - Remove and return highest priority element (O(log n))
- `dequeue()` - Remove and return highest priority element (O(log n))
- `peek()` - Return highest priority element without removing (O(1))

## Common Patterns

### Stack Patterns

1. **Parentheses/Bracket Matching** - Valid parentheses, balanced brackets
2. **Expression Evaluation** - Infix to postfix, calculator problems
3. **Monotonic Stack** - Next greater/smaller element, daily temperatures
4. **Function Call Stack** - Recursive problems, backtracking simulation
5. **Undo Operations** - Browser history, text editor operations
6. **Stack-based DFS** - Tree/graph traversal without recursion

### Queue Patterns

1. **Level Order Traversal** - BFS in trees and graphs
2. **Sliding Window Maximum** - Using deque for optimization
3. **Task Scheduling** - Process scheduling, time-based problems
4. **Multi-level BFS** - Shortest path, minimum steps problems
5. **Circular Queue** - Buffer implementation, streaming data
6. **Priority Queue** - Heap-based problems, top K elements

## Difficulty Progression

**Easy Level (Master These First):**

- Valid Parentheses
- Implement Stack using Queues
- Min Stack
- Baseball Game

**Medium Level:**

- Daily Temperatures
- Next Greater Element
- Evaluate Reverse Polish Notation
- Decode String

**Hard Level:**

- Largest Rectangle in Histogram
- Maximal Rectangle
- Basic Calculator III

---

## Stack Pattern Deep Dive

### Pattern 1: Parentheses/Bracket Matching

**When to Use:** Problems involving nested structures, validation of sequences
**Key Insight:** Use stack to track opening brackets, pop when closing bracket matches

### Pattern 2: Monotonic Stack

**When to Use:** Finding next/previous greater/smaller elements
**Key Insight:** Maintain stack in monotonic order, elements that break order are answers

### Pattern 3: Expression Evaluation

**When to Use:** Calculator problems, mathematical expression parsing
**Key Insight:** Use stack for operands and operators separately

### Pattern 4: Stack Simulation

**When to Use:** Problems that mention "undo", "backtrack", or "most recent"
**Key Insight:** Stack naturally handles "most recent" operations

---

## Stacks

### OA-Specific Focus Areas
##### Most Frequent in OAs (Priority 1):

- Valid Parentheses variations - 90% of companies test this
- Daily Temperatures - Extremely common, especially in tech companies
- Decode String - Popular in string manipulation rounds
- Min Stack - Design question favorite
- Next Greater Element - Foundational monotonic stack

##### Moderate Frequency (Priority 2):

Evaluate RPN - Backend/systems roles
Remove Adjacent Duplicates - String processing roles
Baseball Game - Simulation problems
Largest Rectangle - Algorithm-heavy roles

Amazon: Min Stack, Next Greater Element, Expression Evaluation
Microsoft: Calculator problems, String manipulation

##### Key Success Metrics

 Solve bracket matching in under 5 minutes
 Implement Min Stack without looking at solution
 Understand when to use monotonic stack

 Solve Daily Temperatures in under 10 minutes
 Master expression evaluation pattern
 Handle nested structures confidently

 Solve histogram problems independently
 Handle complex calculator expressions
 Recognize stack patterns in new problems

##### Interview Preparation Tips

  Practice drawing: Visualize stack operations on paper
  Time yourself: Most OA problems should be solved in 15-20 minutes
  Edge cases: Empty stack, single element, all same elements
  Space optimization: Know when you can avoid extra space

##### During the Interview:

  Identify the pattern: Ask yourself "What was the last thing I saw?"
  Draw examples: Sketch the stack state for small inputs
  Think out loud: Explain why you're using a stack
  Consider alternatives: Sometimes recursion or two pointers work better

##### Common Mistakes to Avoid:

  Forgetting to check isEmpty() before pop()
  Wrong order when popping two elements for operators
  Not handling edge cases like empty input
  Overcomplicating simple bracket matching

Now you'll be ready for:

  Queue patterns (BFS, sliding window)
  Deque applications (sliding window maximum)
  Priority queues (heap problems)
  Advanced recursion (using stack concepts)

TIP: Stack problems are about recognizing when you need to "remember" the most recent element or when you need to "undo" operations. Master the patterns, and you'll ace the OAs!