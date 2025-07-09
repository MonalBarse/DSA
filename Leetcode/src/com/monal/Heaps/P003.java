package com.monal.Heaps;

import java.util.*;

/*
Given a sorted integer array arr, two integers k and x, return the k
closest integers to x in the array. The result should also be sorted in ascending order.
An integer a is closer to x than an integer b if:
|a - x| < |b - x|, or
|a - x| == |b - x| and a < b

Example 1:
  Input: arr = [1,2,3,4,5], k = 4, x = 3
  Output: [1,2,3,4]
Example 2:
  Input: arr = [1,1,2,3,4,5], k = 4, x = -1
  Output: [1,1,2,3]
*/
public class P003 {
  class Solution {
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
      PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> {
        if (a[0] != b[0])
          return b[0] - a[0]; // larger distance first
        return b[1] - a[1]; // if equal, remove larger number
      });

      for (int num : arr) {
        int distance = Math.abs(num - x);
        maxHeap.offer(new int[] { distance, num });
        if (maxHeap.size() > k) {
          maxHeap.poll();
        }
      }

      List<Integer> result = new ArrayList<>();
      while (!maxHeap.isEmpty()) {
        result.add(maxHeap.poll()[1]);
      }

      // Collections.sort(result);
      return result;
    }
  }

  public static void main(String[] args) {
    P003.Solution solution = new P003().new Solution();
    int[] arr1 = { 1, 2, 3, 4, 5 };
    int k1 = 4;
    int x1 = 3;
    System.out.println("Closest elements for arr1: " + solution.findClosestElements(arr1, k1, x1)); // Output: [1,2,3,4]

    int[] arr2 = { 1, 1, 2, 3, 4, 5 };
    int k2 = 4;
    int x2 = -1;
    System.out.println("Closest elements for arr2: " + solution.findClosestElements(arr2, k2, x2)); // Output: [1,1,2,3]
  }
}
