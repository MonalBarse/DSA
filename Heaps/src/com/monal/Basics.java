package com.monal;

import java.util.*;

/**
 * COMPREHENSIVE HEAPS
 * ====================================
 *
 * WHAT IS A HEAP?
 * - A heap is a complete binary tree that satisfies the heap property
 * - Max Heap: Parent >= Children (root is maximum)
 * - Min Heap: Parent <= Children (root is minimum)
 *
 * KEY PROPERTIES:
 * 1. Complete Binary Tree: All levels filled except possibly the last, filled
 * left to right
 * 2. Heap Property: Parent-child relationship maintained throughout
 * 3. Array Representation: For index i, left child = 2*i+1, right child =
 * 2*i+2, parent = (i-1)/2
 *
 * TIME COMPLEXITIES:
 * - Insert: O(log n)
 * - Delete/Extract: O(log n)
 * - Peek: O(1)
 * - Build Heap: O(n) - surprising but true!
 *
 * SPACE COMPLEXITY: O(n)
 */
public class Basics {

    /**
     * CUSTOM MIN HEAP IMPLEMENTATION
     * =============================
     *
     * Why implement our own?
     * - Better understanding of internal mechanics
     * - Some interviews expect you to implement from scratch
     * - Helps with debugging heap-related problems
     */
    static class MinHeap {
        private List<Integer> heap;

        public MinHeap() {
            this.heap = new ArrayList<>();
        }

        /**
         * INSERTION - The Foundation Operation
         *
         * Strategy: Add at end, then "bubble up" to maintain heap property
         * Time: O(log n) - worst case travels height of tree
         *
         * Common Mistake: Forgetting to check bounds in parent calculation
         */
        public void insert(int val) {
            heap.add(val);
            bubbleUp(heap.size() - 1);
        }

        // bubble up means movign the newly added element to the correct position
        private void bubbleUp(int idx) {
            // Base case: reached root
            if (idx == 0)
                return;

            int parentIndex = (idx - 1) / 2;

            // If heap property satisfied (the current element is greater than or equal to
            // its parent), stop
            if (heap.get(parentIndex) <= heap.get(idx))
                return;

            // else, swap with parent and continue bubbling up
            swap(idx, parentIndex);
            bubbleUp(parentIndex);
        }

        /**
         * EXTRACTION - The Trickiest Operation
         *
         * Strategy: Replace root with last element, then "bubble down"
         * Time: O(log n)
         *
         * Key Insight: Always replace with LAST element, not any element
         * This maintains the complete binary tree property
         */
        public int extractMin() {
            if (heap.isEmpty())
                throw new RuntimeException("Heap is empty");

            int min = heap.get(0);

            // Replace root with last element
            int lastElement = heap.remove(heap.size() - 1);

            // If heap not empty after removal, restore heap property
            if (!heap.isEmpty()) {
                heap.set(0, lastElement);
                bubbleDown(0);
            }
            return min;
        }

        private void bubbleDown(int index) {
            int leftChild = 2 * index + 1;
            int rightChild = 2 * index + 2;
            int smallest = index;

            // Find smallest among parent and children
            if (leftChild < heap.size() && heap.get(leftChild) < heap.get(smallest)) {
                smallest = leftChild;
            }

            if (rightChild < heap.size() && heap.get(rightChild) < heap.get(smallest)) {
                smallest = rightChild;
            }

            // If heap property violated, swap and continue
            if (smallest != index) {
                swap(index, smallest);
                bubbleDown(smallest);
            }
        }

        public int peek() {
            if (heap.isEmpty())
                throw new RuntimeException("Heap is empty");
            return heap.get(0);
        }

        public int size() {
            return heap.size();
        }

        public boolean isEmpty() {
            return heap.isEmpty();
        }

        private void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }

        @Override
        public String toString() {
            return heap.toString();
        }
    }

    /**
     * PROBLEM 1: K LARGEST ELEMENTS
     * ============================
     *
     * Given an array, find K largest elements.
     *
     * APPROACH COMPARISON:
     * 1. Sort entire array: O(n log n) - overkill
     * 2. Use max heap: O(n log n) - still overkill
     * 3. Use min heap of size K: O(n log k) - optimal!
     *
     * KEY INSIGHT: For K largest, use MIN heap of size K
     * For K smallest, use MAX heap of size K
     *
     * Why? The root of min heap gives us the SMALLEST of the K largest elements
     * If we find something larger, we can replace it
     */
    public static List<Integer> findKLargest(int[] nums, int k) {
        // Edge case handling - always check!
        if (k <= 0 || nums.length == 0)
            return new ArrayList<>();

        // Use min heap to keep track of K largest elements
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        for (int num : nums) {
            minHeap.offer(num);
            // If heap size exceeds K, remove the smallest
            if (minHeap.size() > k)
                minHeap.poll();
        }
        // Convert heap to list
        List<Integer> result = new ArrayList<>(minHeap);
        // Sort to return in descending order
        result.sort(Collections.reverseOrder());
        return result;

    }

    /**
     * PROBLEM 2: MERGE K SORTED LISTS
     * ===============================
     *
     * Classic heap problem - appears in many variations
     *
     * Strategy: Use min heap to always get the smallest unprocessed element
     * Time: O(n log k) where n = total elements, k = number of lists
     *
     * Common Mistake: Not handling null lists or empty lists properly
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode mergeKSortedLists(ListNode[] lists) {
        if (lists == null || lists.length == 0)
            return null;

        // Min heap to store nodes based on their values
        PriorityQueue<ListNode> minHeap = new PriorityQueue<>((a, b) -> a.val - b.val);

        // Add first node of each list to heap
        for (ListNode list : lists) {
            if (list != null) { // Important: null check!
                minHeap.offer(list);
            }
        }

        // commont trick if need to return a linked list
        // Create a dummy node assign it to a value
        // then when returning, return dummy.next
        // This avoids handling edge cases for head node separately
        // if this was max heap the value of dummy would be Integer.MAX_VALUE
        // if this was min heap the value of dummy would be Integer.MIN_VALUE
        ListNode dummy = new ListNode(Integer.MIN_VALUE);
        ListNode current = dummy;

        while (!minHeap.isEmpty()) {
            // Get the node with minimum value
            ListNode minNode = minHeap.poll();
            current.next = minNode;
            current = current.next;

            // Add next node from the same list to heap
            if (minNode.next != null) {
                minHeap.offer(minNode.next);
            }
        }

        return dummy.next;
    }

    /**
     * PROBLEM 3: TOP K FREQUENT ELEMENTS
     * ==================================
     *
     * Given array, return K most frequent elements
     *
     * Strategy:
     * 1. Count frequencies using HashMap
     * 2. Use min heap of size K based on frequency
     *
     * Time: O(n log k) - better than sorting approach O(n log n)
     *
     * Alternative: Use max heap and extract K elements - O(n log n)
     * Choose based on K's size relative to n
     */

    public static List<Integer> topKFrequent(int[] nums, int k) {
        // Count frequencies
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums)
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        // Min heap based on frequency
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> freqMap.get(a) - freqMap.get(b));

        for (int num : freqMap.keySet()) {
            minHeap.offer(num);
            if (minHeap.size() > k)
                minHeap.poll();
        }

        return new ArrayList<>(minHeap);
    }

    /*
     * PROBLEM 3.2: Replace elements by its rank in the array
     * =================================
     * Problem Statement: Given an array of N integers, the task is to
     * replace each element of the array by its rank in the array.
     *
     * Example 1:
     * Input: 20 15 26 2 98 6
     * Output: 4 3 5 1 6 2
     * Explanation: When sorted,the array is 2,6,15,20,26,98. So the rank of 2 is
     * 1,rank of 6 is 2,rank of 15 is 3 and so…
     *
     * Approach:
     * 1. Use a HashMap to store the frequency of each element.
     * 2. Use a PriorityQueue to sort the elements based on their values.
     * 3. Iterate through the sorted elements and assign ranks based on their
     * position in the sorted order.
     * 4. Replace the original elements in the array with their ranks.
     */
    public List<Integer> replaceWithRank(int[] arr) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : arr)
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        // Min heap to sort elements based on their values
        PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);
        for (int num : freqMap.keySet())
            minHeap.offer(num);

        List<Integer> result = new ArrayList<>();
        int rank = 1;
        while (!minHeap.isEmpty()) {
            int current = minHeap.poll();
            // Assign rank to all occurrences of the current element
            for (int i = 0; i < freqMap.get(current); i++) {
                result.add(rank);
            }

            // Increment rank for the next unique element
            rank++;
        }

        // Replace original elements in the array with their ranks
        for (int i = 0; i < arr.length; i++) {
            arr[i] = result.get(i);
        }
        return result;
    }

    /**
     * PROBLEM 4: SLIDING WINDOW MAXIMUM
     * =================================
     *
     * Given array and window size K, find maximum in each window
     *
     * Multiple approaches:
     * 1. Brute force: O(nk) - too slow
     * 2. Max heap: O(n log k) - good
     * 3. Deque: O(n) - optimal but complex
     *
     * Heap approach shown here - good balance of simplicity and efficiency
     */
    public static int[] slidingWindowMaximum(int[] arr, int k) {
        if (arr.length == 0 || k == 0)
            return new int[0];
        int[] result = new int[arr.length - k + 1];

        // Max heap storing [value, index]
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> b[0] - a[0]);

        for (int i = 0; i < arr.length; i++) {
            // Add current element to heap
            maxHeap.offer(new int[] { arr[i], i });

            // Remove elements outside current window
            while (!maxHeap.isEmpty() && maxHeap.peek()[1] <= i - k)
                maxHeap.poll();

            // Record maximum for current window
            if (i >= k - 1)
                result[i - k + 1] = maxHeap.peek()[0];
        }
        return result;
    }

    /**
     * PROBLEM 5: MEDIAN FROM DATA STREAM
     * ==================================
     *
     * Design data structure to find median of stream of numbers
     *
     * Brilliant Strategy: Two heaps!
     * - Max heap for smaller half
     * - Min heap for larger half
     * - Keep sizes balanced
     *
     * Time: O(log n) for add, O(1) for median
     *
     * Key Insight: This pattern (two heaps) appears in many problems
     */
    static class MedianFinder {
        private PriorityQueue<Integer> maxHeap; // smaller half
        private PriorityQueue<Integer> minHeap; // larger half

        public MedianFinder() {
            maxHeap = new PriorityQueue<>((a, b) -> b - a); // max heap
            minHeap = new PriorityQueue<>(); // min heap
        }

        public void addNum(int num) {
            // Always add to max heap first
            maxHeap.offer(num);

            // Move largest from max heap to min heap
            minHeap.offer(maxHeap.poll());

            // Balance the heaps
            if (minHeap.size() > maxHeap.size())
                maxHeap.offer(minHeap.poll());
        }

        public double findMedian() {
            if (maxHeap.size() > minHeap.size())
                return maxHeap.peek();
            else
                return (maxHeap.peek() + minHeap.peek()) / 2.0;

        }
    }

    /**
     * PROBLEM 6: KTHLARGEST ELEMENT IN STREAM
     * =======================================
     *
     * Design class to find Kth largest element in stream
     *
     * Strategy: Min heap of size K
     * Root always contains Kth largest element
     *
     * Common in system design interviews too!
     */
    static class KthLargest {
        private PriorityQueue<Integer> minHeap;
        private int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            this.minHeap = new PriorityQueue<>();

            // Add all initial numbers
            for (int num : nums)
                add(num);

        }

        public int add(int val) {
            minHeap.offer(val);

            // Keep heap size at most K
            if (minHeap.size() > k)
                minHeap.poll();
            return minHeap.peek();
        }
    }

    /**
     * PROBLEM 7: MEETING ROOMS II
     * ===========================
     * You are given an array intevals where intervals[i] = [startOfMeeting, endOfMeeting]
     * representing the start and end times of the meetings that are independent of each other.
     * Return the minimum number of conference rooms required to hold all meetings.
     * Example:
     *  Input : [[0, 30], [5, 10], [15, 20]]
     *  Output: 2
     *  Explanation: Two meetings overlap, need two rooms.
     *
     * Example:
     *  Input : [[0,5], [0,18], [5,15], [16,40], [30, 50], [51,80], [85, 115], [90, 125]]
     *  Output:
     *  Explanation:
     * 1st overlaps with 2nd,
     * 2nd overlaps with 3rd and 4th,
     * 4th overlaps with 5th,
     * 7th overlaps with 8th,
     * so we need 5 rooms in total.
     *
     * Strategy:
     * 1. Sort by start time
     * 2. Use min heap to track end times of ongoing meetings
     * 3. For each meeting, check if any room is free
     *
     * Time: O(n log n)
     *
     * Pattern: Heap for tracking "active" elements
     */
    public static int minMeetingRooms(int[][] meetings) {
        if (meetings.length == 0) return 0;

        Arrays.sort(meetings, (a, b) -> a[0] - b[0]); // Sort by start time
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // To keep track of end times

        for (int[] interval : meetings) {
            while (!minHeap.isEmpty() && minHeap.peek() <= interval[0])
                minHeap.poll();
            // Add current meeting's end time
            minHeap.offer(interval[1]);
        }
        return minHeap.size();
    }

    /**
     * PROBLEM 8: UGLY NUMBER II
     * =========================
     *
     * Find nth ugly number (numbers whose only prime factors are 2, 3, 5)
     * Strategy: Generate ugly numbers in sorted order using min heap
     *
     * Key Challenge: Avoiding duplicates
     * Solution: Use Set to track generated numbers
     */
    public static int nthUglyNumber(int n) {
        PriorityQueue<Long> minHeap = new PriorityQueue<>();
        Set<Long> seen = new HashSet<>();
        int[] primes = { 2, 3, 5 };

        minHeap.offer(1L);
        seen.add(1L);

        long ugly = 1;
        for (int i = 0; i < n; i++) {
            ugly = minHeap.poll();

            // Generate next ugly numbers
            for (int prime : primes) {
                long nextUgly = ugly * prime;
                if (!seen.contains(nextUgly)) {
                    seen.add(nextUgly);
                    minHeap.offer(nextUgly);
                }
            }
        }

        return (int) ugly;
    }

    /**
     * HEAP SORT IMPLEMENTATION
     * ========================
     *
     * Educational value: Understanding heap operations
     * Time: O(n log n), Space: O(1)
     *
     * Process:
     * 1. Build max heap from array
     * 2. Repeatedly extract max and place at end
     */
    public static void heapSort(int[] arr) {
        int n = arr.length;

        // Build max heap
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // Extract elements one by one
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            swap(arr, 0, i);

            // Heapify reduced heap
            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && arr[left] > arr[largest])
            largest = left;

        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * TESTING AND DEMONSTRATION
     * =========================
     */
    public static void main(String[] args) {
        System.out.println("=== HEAP OPERATIONS DEMO ===");

        // Test custom MinHeap
        MinHeap heap = new MinHeap();
        System.out.println("Testing MinHeap:");

        int[] values = { 4, 2, 7, 1, 9, 3 };
        for (int val : values) {
            heap.insert(val);
            System.out.println("Inserted " + val + ", heap: " + heap);
        }

        System.out.println("Extracting elements:");
        while (!heap.isEmpty()) {
            System.out.println("Extracted: " + heap.extractMin() + ", remaining: " + heap);
        }

        System.out.println("\n=== PROBLEM SOLVING DEMO ===");

        // Test K largest elements
        int[] nums1 = { 3, 2, 1, 5, 6, 4 };
        System.out.println("K largest in " + Arrays.toString(nums1) +
                " (k=2): " + findKLargest(nums1, 2));

        // Test top K frequent
        int[] nums2 = { 1, 1, 1, 2, 2, 3 };
        System.out.println("Top 2 frequent in " + Arrays.toString(nums2) +
                ": " + topKFrequent(nums2, 2));

        // Test sliding window maximum
        int[] nums3 = { 1, 3, -1, -3, 5, 3, 6, 7 };
        System.out.println("Sliding window max (k=3): " +
                Arrays.toString(slidingWindowMaximum(nums3, 3)));

        // Test median finder
        System.out.println("\nTesting MedianFinder:");
        MedianFinder medianFinder = new MedianFinder();
        int[] stream = { 1, 2, 3, 4, 5 };
        for (int num : stream) {
            medianFinder.addNum(num);
            System.out.println("Added " + num + ", median: " + medianFinder.findMedian());
        }

        // Test heap sort
        int[] sortTest = { 12, 11, 13, 5, 6, 7 };
        System.out.println("\nBefore heap sort: " + Arrays.toString(sortTest));
        heapSort(sortTest);
        System.out.println("After heap sort: " + Arrays.toString(sortTest));

        System.out.println("\n=== INTERVIEW TIPS ===");
        System.out.println("1. Always clarify if it's min heap or max heap needed");
        System.out.println("2. For 'K largest', use MIN heap of size K");
        System.out.println("3. For 'K smallest', use MAX heap of size K");
        System.out.println("4. Two heaps pattern is powerful for median problems");
        System.out.println("5. Heap + HashMap is common for frequency problems");
        System.out.println("6. Consider heap when you need 'extreme' values efficiently");
        System.out.println("7. Practice heap operations until they're second nature");
    }
}

/**
 * SUMMARY OF KEY PATTERNS:
 * ========================
 *
 * 1. K Elements Pattern:
 * - K largest: Min heap of size K
 * - K smallest: Max heap of size K
 * - Extract K times from appropriate heap
 *
 * 2. Two Heaps Pattern:
 * - Useful for median, balanced partitioning
 * - Max heap for smaller half, min heap for larger half
 *
 * 3. Heap + HashMap:
 * - Frequency problems
 * - Track both value and metadata
 *
 * 4. Heap for Scheduling:
 * - Meeting rooms, task scheduling
 * - Track end times or deadlines
 *
 * 5. Heap for Merging:
 * - Merge K sorted structures
 * - Always extract minimum/maximum
 *
 * COMMON INTERVIEW VARIATIONS:
 * ===========================
 *
 * - Kth largest/smallest element
 * - Top K frequent elements
 * - Merge K sorted lists/arrays
 * - Sliding window maximum/minimum
 * - Find median in data stream
 * - Meeting rooms problems
 * - Task scheduling with priorities
 * - Connect ropes with minimum cost
 * - Ugly numbers generation
 * - IPO problem (project selection)
 *
 * DEBUGGING TIPS:
 * ==============
 *
 * 1. Check heap property after each operation
 * 2. Verify array bounds in heap operations
 * 3. Handle empty heap cases
 * 4. Be careful with integer overflow in calculations
 * 5. Test with duplicate elements
 * 6. Verify that comparator logic matches problem requirements
 *
 * Remember: Heaps are not just data structures, they're problem-solving tools!
 */