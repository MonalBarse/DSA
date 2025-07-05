package com.monal.Stacks_Queues;

import java.util.*;

/*
347. Top K Frequent Elements ( https://leetcode.com/problems/top-k-frequent-elements/ )

Given an integer array nums and an integer k, return the k most frequent elements.
You may return the answer in any order.
Example 1:
  Input: nums = [1,1,1,2,2,3], k = 2
  Output: [1,2]
Example 2:
  Input: nums = [1], k = 1
  Output: [1]
*/
public class P008 {
  class Solution {
    public int[] topKFrequent(int[] nums, int k) {
      Map<Integer, Integer> freqMap = new HashMap<>();
      for (int num : nums) {
        freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
      }
      // Create a min-heap based on frequency
      PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
        new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

      for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
        minHeap.offer(entry);
        // If the heap size exceeds k, remove the smallest frequency element
        if (minHeap.size() > k) minHeap.poll();
      }

      // Extract the k most frequent elements from the min-heap
      int[] result = new int[k];
      for (int i = 0; i < k; i++) {
        result[i] = minHeap.poll().getKey();
      }
      return result;

    }
  }

  public static void main(String[] args) {
    Solution solution = new P008().new Solution();
    int[] nums1 = { 1, 1, 1, 2, 2, 3 };
    int k1 = 2;
    System.out.println(Arrays.toString(solution.topKFrequent(nums1, k1))); // Output: [1, 2]

    int[] nums2 = { 1 };
    int k2 = 1;
    System.out.println(Arrays.toString(solution.topKFrequent(nums2, k2))); // Output: [1]

    int[] nums3 = { 1, 2, 2, 3, 3, 3, 4, 4, 4, 4 };
    int k3 = 2;
    System.out.println(Arrays.toString(solution.topKFrequent(nums3, k3))); // Output: [4, 3] or [3, 4]
  }
}
