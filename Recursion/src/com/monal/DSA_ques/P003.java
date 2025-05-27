package com.monal.DSA_ques;

import java.util.*;

// 295. Find Median from Data Stream
/*
The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value, and the median is the mean of the two middle values.

For example, for arr = [2,3,4], the median is 3.
For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
Implement the MedianFinder class:

MedianFinder() initializes the MedianFinder object.
void addNum(int num) adds the integer num from the data stream to the data structure.
double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.


Example 1:

Input
["MedianFinder", "addNum", "addNum", "findMedian", "addNum", "findMedian"]
[[], [1], [2], [], [3], []]
Output
[null, null, null, 1.5, null, 2.0]

Explanation
MedianFinder medianFinder = new MedianFinder();
medianFinder.addNum(1);    // arr = [1]
medianFinder.addNum(2);    // arr = [1, 2]
medianFinder.findMedian(); // return 1.5 (i.e., (1 + 2) / 2)
medianFinder.addNum(3);    // arr[1, 2, 3]
medianFinder.findMedian(); // return 2.0 */
public class P003 {
  class MedianFinder {
    // Max-heap for the left half
    private PriorityQueue<Integer> maxHeap;

    // Min-heap for the right half
    private PriorityQueue<Integer> minHeap;

    public MedianFinder() {
      maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // max-heap
      minHeap = new PriorityQueue<>(); // min-heap
    }

    public void addNum(int num) {
      // Step 1: Add to maxHeap first
      maxHeap.offer(num);

      // Step 2: Balance → move maxHeap's top to minHeap
      minHeap.offer(maxHeap.poll());

      // Step 3: Make sure maxHeap is never smaller than minHeap
      if (maxHeap.size() < minHeap.size()) {
        maxHeap.offer(minHeap.poll());
      }
    }

    public double findMedian() {
      // If odd, maxHeap has more → top is median
      if (maxHeap.size() > minHeap.size()) {
        return maxHeap.peek();
      }

      // If even, average of both tops
      return (maxHeap.peek() + minHeap.peek()) / 2.0;
    }
  }

}
