# Heap Data Structure - Patterns & Use Cases

## 🧠 What is a Heap?

A **heap** is a special complete binary tree (usually implemented using arrays) where each node follows a specific ordering property:

* **Min Heap**: Parent node is smaller than or equal to its children.
* **Max Heap**: Parent node is greater than or equal to its children.

Used heavily in:

* Priority Queues
* Scheduling Algorithms
* Top-K problems
* Efficient retrieval of min/max element

---

## 🧱 Heap Basics

* **Binary Heap** (Min/Max)
* **Priority Queue** (built on heaps)
* **Custom Comparators** (for complex objects)
* **Heapify / Build Heap** (heap from array in O(N))
* **Insertion / Deletion** (O(log N))

---

## 📚 Common Heap Patterns with Boilerplate Code

### 1. **Top-K Elements Pattern**

**Use Case:** Kth largest/smallest element, Top K frequent elements, Closest points

```java
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for (int num : nums) {
    minHeap.add(num);
    if (minHeap.size() > k) {
        minHeap.poll(); // Maintain size K
    }
}
return minHeap.peek();
```

---

### 2. **Sliding Window with Heap**

**Use Case:** Maximum in sliding window, median in data stream

```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
// balance heaps so that maxHeap has equal or one more element than minHeap
```

*Note:* Need extra structure (e.g., HashMap) for lazy deletion

---

### 3. **Priority Simulation**

**Use Case:** Task scheduling, meeting rooms

```java
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
for (int[] task : tasks) {
    // Pop tasks that can be completed
    while (!pq.isEmpty() && pq.peek()[1] <= currentTime) {
        pq.poll();
    }
    pq.offer(task);
}
```

---

### 4. **Merge K Sorted Lists / Arrays**

**Use Case:** External sorting, merge k sorted streams

```java
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
for (int i = 0; i < lists.length; i++) {
    if (lists[i].length > 0) pq.offer(new int[]{lists[i][0], i, 0});
}
while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    result.add(curr[0]);
    if (curr[2] + 1 < lists[curr[1]].length) {
        pq.offer(new int[]{lists[curr[1]][curr[2] + 1], curr[1], curr[2] + 1});
    }
}
```

---

### 5. **Greedy Choice using Heap**

**Use Case:** Connect ropes, minimum cost, Dijkstra

```java
PriorityQueue<Integer> pq = new PriorityQueue<>();
for (int rope : ropes) pq.add(rope);

int cost = 0;
while (pq.size() > 1) {
    int first = pq.poll();
    int second = pq.poll();
    cost += first + second;
    pq.add(first + second);
}
return cost;
```

---

### 6. **Median of Data Stream (Dual Heap)**

**Use Case:** Stream median

```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // lower half
PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // upper half

public void addNum(int num) {
    maxHeap.offer(num);
    minHeap.offer(maxHeap.poll());
    if (maxHeap.size() < minHeap.size()) {
        maxHeap.offer(minHeap.poll());
    }
}

public double findMedian() {
    if (maxHeap.size() == minHeap.size()) {
        return (maxHeap.peek() + minHeap.peek()) / 2.0;
    } else {
        return maxHeap.peek();
    }
}
```

---

## ⚙️ Real-World Use Cases

* Operating System CPU Scheduling
* Event Simulation Systems
* Online Leaderboards (Top-K)
* A\* Algorithm (uses priority queue)

---

## 🧩 Advanced Concepts (Optional)

* Fibonacci Heap / Binomial Heap (for theoretical optimization)
* Pairing Heaps (simpler alternative)
* Indexed Heaps (support decrease key)
* Lazy Deletion with heaps

---

## ✅ What's Next?

Once you’re confident with the basics:

* Implement custom heaps
* Practice Leetcode/Codeforces problems by pattern
* Use `PriorityQueue` in Java with custom comparators
* Explore problems mixing heaps with maps, sliding windows, or DP

---

Let me know when you're ready to dive into implementation, problem sets, or cheatsheets!
