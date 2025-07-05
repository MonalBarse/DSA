package com.monal;
import java.util.*;

/**
 * COMPREHENSIVE QUEUE DATA STRUCTURE LEARNING RESOURCE
 *
 * This class covers all essential Queue concepts, patterns, and problem-solving techniques
 * needed for DSA interviews and Online Assessments (OAs).
 *
 * TOPICS COVERED:
 * 1. Queue Fundamentals & Implementation
 * 2. Queue using Arrays and LinkedList
 * 3. Circular Queue Implementation
 * 4. Deque (Double-ended Queue)
 * 5. Priority Queue (Heap-based)
 * 6. Common Queue Patterns & Algorithms
 * 7. BFS Traversal Patterns
 * 8. Sliding Window with Queue
 * 9. Monotonic Queue/Deque
 * 10. Interview Problems with Solutions
 */
public class Queues {

    // ==================== QUEUE FUNDAMENTALS ====================

    /**
     * CUSTOM QUEUE IMPLEMENTATION USING ARRAY
     *
     * KEY INSIGHTS:
     * - Fixed size implementation with front and rear pointers
     * - Circular approach to optimize space utilization
     * - Important for understanding queue mechanics
     *
     * TIME COMPLEXITY: O(1) for all operations
     * SPACE COMPLEXITY: O(n) where n is capacity
     */
    static class ArrayQueue<T> {
        private Object[] queue;
        private int front, rear, size, capacity;

        public ArrayQueue(int capacity) {
            this.capacity = capacity;
            this.queue = new Object[capacity];
            this.front = 0;
            this.rear = -1;
            this.size = 0;
        }

        public boolean enqueue(T item) {
            // COMMON MISTAKE: Not checking for overflow
            if (isFull()) {
                System.out.println("Queue Overflow! Cannot add: " + item);
                return false;
            }

            // TRICK: Use modulo for circular behavior
            rear = (rear + 1) % capacity;
            queue[rear] = item;
            size++;
            return true;
        }

        @SuppressWarnings("unchecked")
        public T dequeue() {
            // COMMON MISTAKE: Not checking for underflow
            if (isEmpty()) {
                System.out.println("Queue Underflow! Cannot remove from empty queue");
                return null;
            }

            T item = (T) queue[front];
            queue[front] = null; // OPTIMIZATION: Help GC by nullifying reference
            front = (front + 1) % capacity;
            size--;
            return item;
        }

        @SuppressWarnings("unchecked")
        public T peek() {
            return isEmpty() ? null : (T) queue[front];
        }

        public boolean isEmpty() { return size == 0; }
        public boolean isFull() { return size == capacity; }
        public int size() { return size; }

        // DEBUGGING HELPER: Visualize queue state
        public void display() {
            if (isEmpty()) {
                System.out.println("Queue is empty");
                return;
            }

            System.out.print("Queue: [");
            for (int i = 0; i < size; i++) {
                int index = (front + i) % capacity;
                System.out.print(queue[index]);
                if (i < size - 1) System.out.print(", ");
            }
            System.out.println("] (front=" + front + ", rear=" + rear + ")");
        }
    }

    // ==================== PRIORITY QUEUE PATTERNS ====================

    /**
     * PRIORITY QUEUE MASTERY FOR INTERVIEWS
     *
     * CRITICAL INSIGHTS:
     * - Java's PriorityQueue is a MIN-HEAP by default
     * - For MAX-HEAP: use Collections.reverseOrder() or custom comparator
     * - Perfect for problems involving "Top K", "Kth largest/smallest"
     */
    public static void priorityQueuePatterns() {
        System.out.println("\n=== PRIORITY QUEUE PATTERNS ===");

        // MIN-HEAP (default behavior)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        // MAX-HEAP using comparator
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        int[] nums = {3, 1, 4, 1, 5, 9, 2, 6};

        for (int num : nums) {
            minHeap.offer(num);
            maxHeap.offer(num);
        }

        System.out.println("Min-Heap top 3: ");
        for (int i = 0; i < 3 && !minHeap.isEmpty(); i++) {
            System.out.print(minHeap.poll() + " ");
        }

        System.out.println("\nMax-Heap top 3: ");
        for (int i = 0; i < 3 && !maxHeap.isEmpty(); i++) {
            System.out.print(maxHeap.poll() + " ");
        }
        System.out.println();
    }

    // ==================== BFS PATTERN MASTERY ====================

    /**
     * BFS TRAVERSAL - THE FOUNDATION OF MANY INTERVIEW PROBLEMS
     *
     * ALGORITHMIC INSIGHTS:
     * - Queue naturally maintains level order
     * - Perfect for shortest path in unweighted graphs
     * - Level-by-level processing pattern is crucial
     *
     * COMMON APPLICATIONS:
     * - Tree level order traversal
     * - Shortest path in maze/grid
     * - Word ladder problems
     * - Social network connections (degrees of separation)
     */
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public static List<List<Integer>> levelOrderTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size(); // CRUCIAL: Capture size before processing
            List<Integer> currentLevel = new ArrayList<>();

            // PATTERN: Process entire level at once
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

    // ==================== SLIDING WINDOW WITH DEQUE ====================

    /**
     * SLIDING WINDOW MAXIMUM - ADVANCED DEQUE PATTERN
     *
     * PROBLEM: Given array and window size k, find maximum in each sliding window
     *
     * ALGORITHMIC INSIGHTS:
     * - Deque maintains elements in decreasing order
     * - Front always contains index of maximum element
     * - Remove elements outside current window
     * - Remove smaller elements from rear (they'll never be maximum)
     *
     * TIME COMPLEXITY: O(n) - each element added/removed at most once
     * SPACE COMPLEXITY: O(k) - deque size limited by window
     *
     * INTERVIEW TIP: This pattern appears in many "sliding window extrema" problems
     */
    public static int[] slidingWindowMaximum(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n - k + 1];

        // Deque stores indices, not values
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            // STEP 1: Remove indices outside current window
            while (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // STEP 2: Remove indices of smaller elements from rear
            // INSIGHT: If nums[i] >= nums[deque.peekLast()], then nums[deque.peekLast()]
            // will never be maximum in any future window containing nums[i]
            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }

            // STEP 3: Add current index
            deque.offerLast(i);

            // STEP 4: Record result when window is complete
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }

    // ==================== MONOTONIC QUEUE PATTERN ====================

    /**
     * MONOTONIC QUEUE - ADVANCED PATTERN FOR OPTIMIZATION PROBLEMS
     *
     * CONCEPT: Maintain queue where elements are in monotonic order
     *
     * APPLICATIONS:
     * - Sliding window extrema
     * - Stock span problems
     * - Largest rectangle in histogram
     * - Trapping rainwater variations
     */
    public static class MonotonicQueue {
        private Deque<Integer> deque = new ArrayDeque<>();

        // Maintain decreasing order (for maximum queries)
        public void push(int val) {
            // Remove smaller elements from back
            while (!deque.isEmpty() && deque.peekLast() < val) {
                deque.pollLast();
            }
            deque.offerLast(val);
        }

        public void pop(int val) {
            // Only remove if it's the front element
            if (!deque.isEmpty() && deque.peekFirst() == val) {
                deque.pollFirst();
            }
        }

        public int max() {
            return deque.isEmpty() ? Integer.MIN_VALUE : deque.peekFirst();
        }
    }

    // ==================== INTERVIEW PROBLEM: FIRST NON-REPEATING CHARACTER ====================

    /**
     * FIRST NON-REPEATING CHARACTER IN STREAM
     *
     * PROBLEM: Design data structure to find first non-repeating character in stream
     *
     * APPROACH INSIGHTS:
     * - Queue maintains order of characters
     * - HashMap tracks frequency
     * - Clean up queue front when characters become repeating
     *
     * TIME COMPLEXITY: O(1) amortized for each operation
     * SPACE COMPLEXITY: O(k) where k is number of unique characters
     */
    static class FirstNonRepeating {
        private Queue<Character> queue = new LinkedList<>();
        private Map<Character, Integer> freq = new HashMap<>();

        public void addCharacter(char ch) {
            // Update frequency
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);

            // Add to queue if first occurrence
            if (freq.get(ch) == 1) {
                queue.offer(ch);
            }

            // Clean up queue front
            while (!queue.isEmpty() && freq.get(queue.peek()) > 1) {
                queue.poll();
            }
        }

        public char getFirstNonRepeating() {
            return queue.isEmpty() ? '#' : queue.peek();
        }
    }

    // ==================== INTERVIEW PROBLEM: CIRCULAR TOUR ====================

    /**
     * GAS STATION CIRCULAR TOUR PROBLEM
     *
     * PROBLEM: Given gas stations in circle, find starting station to complete tour
     *
     * KEY INSIGHTS:
     * - If total gas >= total cost, solution exists and is unique
     * - Greedy approach: when we can't proceed, start from next station
     * - Queue/tracking approach helps visualize the solution
     *
     * ALGORITHMIC STRATEGY:
     * - Track cumulative balance
     * - Reset starting point when balance goes negative
     */
    public static int gasStationTour(int[] gas, int[] cost) {
        int totalGas = 0, totalCost = 0, currentGas = 0, start = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            currentGas += gas[i] - cost[i];

            // If current gas becomes negative, we can't reach next station
            // So start from next station
            if (currentGas < 0) {
                start = i + 1;
                currentGas = 0;
            }
        }

        // CRITICAL CHECK: Solution exists only if total gas >= total cost
        return totalGas >= totalCost ? start : -1;
    }

    // ==================== INTERVIEW PROBLEM: IMPLEMENT STACK USING QUEUES ====================

    /**
     * IMPLEMENT STACK USING QUEUES
     *
     * DESIGN INSIGHTS:
     * - Two approaches: using 1 queue or 2 queues
     * - 1 queue approach: rotate queue after each push
     * - 2 queue approach: use one as main, other as helper
     *
     * INTERVIEW TIP: Discuss trade-offs between approaches
     */
    static class StackUsingQueue {
        private Queue<Integer> q1 = new LinkedList<>();
        private Queue<Integer> q2 = new LinkedList<>();
        private int top;

        // APPROACH 1: Two queues, expensive pop
        public void push(int x) {
            q1.offer(x);
            top = x;
        }

        public int pop() {
            // Move all elements except last to q2
            while (q1.size() > 1) {
                top = q1.poll(); // Keep track of new top
                q2.offer(top);
            }

            int result = q1.poll();

            // Swap queues
            Queue<Integer> temp = q1;
            q1 = q2;
            q2 = temp;

            return result;
        }

        public int top() { return top; }
        public boolean empty() { return q1.isEmpty(); }

        // APPROACH 2: Single queue, expensive push (alternative implementation)
        static class StackUsingSingleQueue {
            private Queue<Integer> queue = new LinkedList<>();

            public void push(int x) {
                queue.offer(x);
                // Rotate queue to make new element front
                int size = queue.size();
                for (int i = 0; i < size - 1; i++) {
                    queue.offer(queue.poll());
                }
            }

            public int pop() { return queue.poll(); }
            public int top() { return queue.peek(); }
            public boolean empty() { return queue.isEmpty(); }
        }
    }

    // ==================== TESTING AND DEMONSTRATION ====================

    public static void main(String[] args) {
        System.out.println("=== COMPREHENSIVE QUEUE LEARNING RESOURCE ===\n");

        // Test Custom Array Queue
        System.out.println("1. CUSTOM ARRAY QUEUE DEMO:");
        ArrayQueue<String> arrayQueue = new ArrayQueue<>(5);

        // Demonstrate operations
        arrayQueue.enqueue("First");
        arrayQueue.enqueue("Second");
        arrayQueue.enqueue("Third");
        arrayQueue.display();

        System.out.println("Dequeued: " + arrayQueue.dequeue());
        arrayQueue.enqueue("Fourth");
        arrayQueue.display();

        // Test Priority Queue Patterns
        priorityQueuePatterns();

        // Test BFS Pattern
        System.out.println("\n3. BFS LEVEL ORDER TRAVERSAL:");
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        List<List<Integer>> levels = levelOrderTraversal(root);
        System.out.println("Level order: " + levels);

        // Test Sliding Window Maximum
        System.out.println("\n4. SLIDING WINDOW MAXIMUM:");
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        int[] maxWindows = slidingWindowMaximum(nums, k);
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Window size: " + k);
        System.out.println("Sliding window maximums: " + Arrays.toString(maxWindows));

        // Test First Non-Repeating Character
        System.out.println("\n5. FIRST NON-REPEATING CHARACTER STREAM:");
        FirstNonRepeating fnr = new FirstNonRepeating();
        String stream = "aabc";
        for (char ch : stream.toCharArray()) {
            fnr.addCharacter(ch);
            System.out.println("Added '" + ch + "', First non-repeating: '" +
                             fnr.getFirstNonRepeating() + "'");
        }

        // Test Gas Station Problem
        System.out.println("\n6. GAS STATION CIRCULAR TOUR:");
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {3, 4, 5, 1, 2};
        int startStation = gasStationTour(gas, cost);
        System.out.println("Gas: " + Arrays.toString(gas));
        System.out.println("Cost: " + Arrays.toString(cost));
        System.out.println("Starting station: " + (startStation == -1 ? "No solution" : startStation));

        // Test Stack Using Queue
        System.out.println("\n7. STACK USING QUEUE:");
        StackUsingQueue stack = new StackUsingQueue();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Stack top: " + stack.top());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Pop: " + stack.pop());
        System.out.println("Stack empty: " + stack.empty());

        System.out.println("\n=== KEY TAKEAWAYS FOR INTERVIEWS ===");
        System.out.println("1. Master BFS pattern - it's fundamental to many problems");
        System.out.println("2. Understand when to use Deque vs Queue vs PriorityQueue");
        System.out.println("3. Monotonic Queue/Deque is powerful for optimization problems");
        System.out.println("4. Always consider edge cases: empty queue, single element, etc.");
        System.out.println("5. Practice implementing data structures from scratch");
        System.out.println("6. Understand time/space complexities of all operations");
    }
}

/*
 * COMPREHENSIVE STUDY GUIDE FOR QUEUE-BASED PROBLEMS
 *
 * COMMON INTERVIEW PATTERNS:
 *
 * 1. BFS TRAVERSAL PATTERN:
 *    - Tree/Graph level order traversal
 *    - Shortest path in unweighted graphs
 *    - Word ladder, minimum steps problems
 *
 * 2. SLIDING WINDOW WITH DEQUE:
 *    - Sliding window maximum/minimum
 *    - Constrained optimization problems
 *
 * 3. QUEUE SIMULATION:
 *    - Process scheduling
 *    - Buffer implementation
 *    - Rate limiting
 *
 * 4. MONOTONIC QUEUE:
 *    - Stock span problems
 *    - Largest rectangle variations
 *    - Dynamic programming optimizations
 *
 * 5. DESIGN PROBLEMS:
 *    - Implement stack using queue
 *    - Implement queue using stack
 *    - LRU cache (deque + hashmap)
 *
 * OPTIMIZATION TIPS:
 * - Use ArrayDeque instead of LinkedList for better performance
 * - Consider space complexity when choosing between approaches
 * - Precompute when possible to avoid repeated calculations
 * - Use appropriate data structures for the problem constraints
 *
 * COMMON PITFALLS TO AVOID:
 * - Not handling empty queue conditions
 * - Off-by-one errors in circular queue implementation
 * - Forgetting to maintain invariants in monotonic structures
 * - Inefficient approaches when better solutions exist
 * - Not considering edge cases in problem solutions
 */