package com.monal.Stacks_Queues;

import java.util.*;

/*
239. Sliding Window Maximum
You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position.

Return the max sliding window.
*/
public class P016 {

  public int[] maxSlidingWindow(int[] nums, int k) {
    // We will use a monotonic stack to keep track of the indices of the elements in the current window.
    int n = nums.length;
    if (n == 0 || k == 0) return new int[0];
    int[] result = new int[n - k + 1];
    Deque<Integer> deque = new ArrayDeque<>();
    int left = 0;
    for(int right = 0; right < n; right++){

      // remove left elements that are out of the window
      while(deque.size() > 0 && deque.peekFirst() < left) deque.pollFirst();

      // Also remove elements that are smaller than the current element
      while(deque.size() > 0 && nums[deque.peekLast()] < nums[right])
        deque.pollLast();

      // Add the current element's index to the deque
      deque.offerLast(right);

      // If we have filled the first window, we can start adding results
      if (right >= k - 1) {
        result[left] = nums[deque.peekFirst()]; // The maximum for the current window
        left++; // Move the left pointer to the right for the next window
      }
    }
    return result;
  }

}
