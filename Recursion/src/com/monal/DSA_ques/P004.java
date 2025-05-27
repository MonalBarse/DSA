package com.monal.DSA_ques;

import java.util.*;
// 373. Find K Pairs with Smallest Sums

/*
You are given two integer arrays nums1 and nums2 sorted in non-decreasing order and an integer k.
Define a pair (u, v) which consists of one element from the first array and one element from the second array.
Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.

Example 1:
  Input: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
  Output: [[1,2],[1,4],[1,6]]
  Explanation: The first 3 pairs are returned from the sequence: [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]
Example 2:
  Input: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
  Output: [[1,1],[1,1]]
  Explanation: The first 2 pairs are returned from the sequence: [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]
*/
public class P004 {
  class Solution {
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {

      // Appraoch : Using minHeap
      /*
       * 1. Create a minHeap to store pairs of sums and their indices.
       * 2. Initialize the heap with the first k pairs from nums1 and nums2.
       * 3. While the heap is not empty and we have not found k pairs:
       * - Extract the pair with the smallest sum from the heap.
       * - Add the pair to the result list.
       * - If there are more elements in nums2, add the next pair with the same
       * element from nums1 and the next element from nums2 to the heap.
       * 4. Return the result list.
       */

      List<List<Integer>> result = new ArrayList<>();
      PriorityQueue<int[]> minHeap = new PriorityQueue<>((a, b) -> (a[0] + a[1]) - (b[0] + b[1]));

      for (int i = 0; i < Math.min(k, nums1.length); i++) {
        for (int j = 0; j < Math.min(k, nums2.length); j++) {
          minHeap.offer(new int[] { nums1[i], nums2[j] });
        }
      }

      while (k-- > 0 && !minHeap.isEmpty()) {
        int[] pair = minHeap.poll();
        result.add(Arrays.asList(pair[0], pair[1]));
      }
      return result;

    }
  }

}
