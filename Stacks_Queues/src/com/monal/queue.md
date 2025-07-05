# Queue Patterns

## 🎯 Queue Pattern Recognition Framework

### Pattern 1: BFS Traversal Pattern
**Recognition Signs:**
- "Level by level" processing
- "Shortest path" in unweighted graph
- "Minimum steps" problems
- Tree level order traversal

**Boilerplate:**
```java
// BFS Traversal Template
public class BFSPattern {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // Critical: capture size before loop
            List<Integer> currentLevel = new ArrayList<>();

            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);

                // Add children for next level
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(currentLevel);
        }

        return result;
    }
}
```

---

### Pattern 2: Sliding Window with Deque
**Recognition Signs:**
- "Maximum/minimum in sliding window"
- "First/last element in subarray"
- Need to maintain order while adding/removing from both ends

**Boilerplate:**
```java
// Sliding Window Deque Template
import java.util.*;

public class SlidingWindowDeque {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];
        int resultIndex = 0;

        // Deque stores indices, maintains decreasing order of values
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // Remove indices outside current window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // Remove smaller elements from rear (maintain decreasing order)
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            deque.offerLast(i); // Add current index

            // Window is complete, record maximum
            if (i >= k - 1) {
                result[resultIndex++] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
```

---

### Pattern 3: Priority Queue (Top K Pattern)
**Recognition Signs:**
- "K largest/smallest elements"
- "K closest points"
- "Merge K sorted arrays"
- Task scheduling with priorities

**Boilerplate:**
```java
// Priority Queue Template
import java.util.*;

public class PriorityQueuePattern {

    // Top K Smallest Elements Template
    public int[] getKSmallest(int[] nums, int k) {
        // Max heap for k smallest (counter-intuitive but correct)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

        for (int num : nums) {
            maxHeap.offer(num);

            // Keep only k elements
            if (maxHeap.size() > k) {
                maxHeap.poll(); // Remove largest
            }
        }

        // Convert to array
        int[] result = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            result[i] = maxHeap.poll();
        }

        return result;
    }

    // Custom Object Priority Queue
    public int[][] kClosestPoints(int[][] points, int k) {
        // Max heap based on distance
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) ->
            (b[0] * b[0] + b[1] * b[1]) - (a[0] * a[0] + a[1] * a[1])
        );

        for (int[] point : points) {
            maxHeap.offer(point);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }

        return maxHeap.toArray(new int[k][]);
    }
}
```

---

### Pattern 4: Multi-Source BFS
**Recognition Signs:**
- "Minimum time to reach all cells"
- "Rotting oranges" type problems
- Multiple starting points simultaneously

**Boilerplate:**
```java
// Multi-Source BFS Template
public class MultiSourceBFS {
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0) return -1;

        int rows = grid.length, cols = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int freshCount = 0;

        // Initialize: add all rotten oranges to queue
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                } else if (grid[i][j] == 1) {
                    freshCount++;
                }
            }
        }

        if (freshCount == 0) return 0; // No fresh oranges

        int minutes = 0;

        // BFS level by level
        while (!queue.isEmpty() && freshCount > 0) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0], col = current[1];

                // Check all 4 directions
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];

                    // Bounds check and fresh orange check
                    if (newRow >= 0 && newRow < rows &&
                        newCol >= 0 && newCol < cols &&
                        grid[newRow][newCol] == 1) {

                        grid[newRow][newCol] = 2; // Make it rotten
                        freshCount--;
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }

            minutes++; // One minute passed
        }

        return freshCount == 0 ? minutes : -1;
    }
}
```

---

## 🔥 Essential Practice Problems by Pattern

### Level 1: Basic Queue Operations
1. **Implement Queue using Stacks** (LC 232)
2. **Design Circular Queue** (LC 622)
3. **Number of Recent Calls** (LC 933)

### Level 2: BFS Fundamentals
4. **Binary Tree Level Order Traversal** (LC 102)
5. **Binary Tree Zigzag Level Order** (LC 103)
6. **Minimum Depth of Binary Tree** (LC 111)
7. **Rotting Oranges** (LC 994)

### Level 3: Sliding Window Deque
8. **Sliding Window Maximum** (LC 239) ⭐
9. **Shortest Subarray with Sum at Least K** (LC 862)
10. **Constrained Subsequence Sum** (LC 1425)

### Level 4: Priority Queue Mastery
11. **Kth Largest Element in Array** (LC 215)
12. **Top K Frequent Elements** (LC 347)
13. **K Closest Points to Origin** (LC 973)
14. **Meeting Rooms II** (LC 253)
15. **Task Scheduler** (LC 621)

### Level 5: Advanced Patterns
16. **Word Ladder** (LC 127) - BFS on words
17. **Open the Lock** (LC 752) - BFS with states
18. **Shortest Path in Binary Matrix** (LC 1091)
19. **Jump Game VI** (LC 1696) - Deque optimization
20. **Find Median from Data Stream** (LC 295) - Dual heaps

---

## 🎯 OA Success Tips

### Queue Pattern Recognition Checklist:
- [ ] **"Level by level"** → Use regular Queue + BFS
- [ ] **"Sliding window max/min"** → Use Deque
- [ ] **"Top K anything"** → Use PriorityQueue
- [ ] **"Multiple sources"** → Use Multi-source BFS
- [ ] **"Shortest path unweighted"** → Use BFS

### Common Mistakes to Avoid:
1. **Not capturing queue size** before level processing
2. **Using ArrayList instead of ArrayDeque** for deque operations
3. **Wrong heap type** (max vs min) for Top K problems
4. **Forgetting bounds checking** in grid BFS
5. **Not handling empty inputs** properly

### Time Complexity Quick Reference:
- **Queue operations**: O(1) offer/poll
- **BFS**: O(V + E) where V = vertices, E = edges
- **Priority Queue**: O(log n) insert/remove, O(1) peek
- **Deque**: O(1) for all operations at ends

---

# Queue & Stack Patterns for DSA Interviews

## Core Queue Patterns

### 1. **Basic Queue Operations** (Easiest - 90% OA probability)
- **Pattern**: Simple enqueue/dequeue, peek, size operations
- **Time**: O(1) for all operations
- **Examples**: Implement Queue using Arrays/LinkedList
- **Boilerplate**: Basic Queue interface implementation

### 2. **Queue using Stacks** (Very Common - 80% OA probability)
- **Pattern**: Implement queue using two stacks (push-heavy or pop-heavy)
- **Key Insight**: One stack for input, one for output
- **Time**: Amortized O(1)
- **Examples**: LeetCode 232

### 3. **Sliding Window with Deque** (Medium-Hard - 60% OA probability)
- **Pattern**: Maintain min/max in sliding window using deque
- **Key Insight**: Remove elements that can't be optimal
- **Time**: O(n)
- **Examples**: Sliding Window Maximum, Min Stack

### 4. **Level Order Traversal** (Very Common - 85% OA probability)
- **Pattern**: BFS using queue for tree/graph traversal
- **Key Insight**: Process level by level
- **Time**: O(n)
- **Examples**: Binary Tree Level Order, Rotting Oranges

### 5. **Monotonic Deque** (Advanced - 40% OA probability)
- **Pattern**: Maintain increasing/decreasing order in deque
- **Key Insight**: Remove elements that violate monotonic property
- **Time**: O(n)
- **Examples**: Largest Rectangle in Histogram

### 6. **Priority Queue (Heap)** (Common - 70% OA probability)
- **Pattern**: Always access min/max element efficiently
- **Key Insight**: Use heap for dynamic min/max queries
- **Time**: O(log n) insert/delete, O(1) peek
- **Examples**: Kth Largest Element, Merge K Sorted Lists

### 7. **Circular Queue** (Medium - 50% OA probability)
- **Pattern**: Fixed-size queue with wraparound
- **Key Insight**: Use modulo arithmetic for wraparound
- **Time**: O(1) all operations
- **Examples**: Design Circular Queue

## Queue Types Priority for OAs

1. **ArrayDeque (Java)** - Most versatile, use as default
2. **LinkedList** - When you need Queue interface
3. **PriorityQueue** - For heap-based problems
4. **Custom Implementation** - For specific constraints

## Common Mistakes to Avoid

1. **Off-by-one errors** in circular queues
2. **Not checking empty queue** before dequeue
3. **Confusing FIFO vs LIFO** when using deque as stack
4. **Memory leaks** in circular buffer implementations
5. **Not handling capacity limits** in bounded queues

## Interview Success Strategy

### Easy Problems (Master First - 90% OA coverage)
- Implement Queue using Stacks
- Binary Tree Level Order Traversal
- Rotting Oranges
- Design Circular Queue

### Medium Problems (70% OA coverage)
- Sliding Window Maximum
- Number of Islands (BFS)
- Open the Lock
- Perfect Squares

### Hard Problems (30% OA coverage - Skip if time constrained)
- Shortest Path in Binary Matrix
- Minimum Window Substring with Deque
- Constrained Subsequence Sum

## Time Allocation for OA Prep
- **Week 1**: Basic Queue + Queue using Stacks + Level Order
- **Week 2**: Sliding Window + BFS Problems
- **Week 3**: Priority Queue + Advanced Patterns
- **Week 4**: Mock OAs + Pattern Recognition

## Boilerplate Templates Available
1. Basic Queue Operations
2. Queue using Two Stacks
3. Circular Queue
4. BFS Template
5. Sliding Window with Deque
6. Priority Queue Operations
7. Monotonic Deque Template