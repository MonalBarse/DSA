package com.monal.Array;

// https://leetcode.com/problems/subarray-sum-equals-k/
import java.util.HashMap;

/*
 * Given an array of integers nums and an integer k, return the total number of continuous subarrays whose sum equals to k.
 * Example 1:
 *   Input: nums = [1,1,1], k = 2
 *   Output: 2;  [1,1] and [1,1]
 *
 * Example 2:
 *   Input: nums = [1,2,3], k = 3
 *   Output: 2;  [1,2] and [3]
 *
 * Example 3:
 *   Input: nums = [1,4,12,2,3,4,5,2,6,7,8,9,10], k = 16
 *   Output: 2;   [4,12] and [2,3,4,5,2]
 */

public class P010 {
  class Solution {
    public int subarraySum(int[] arr, int k) {
      int count = 0;
      int prefixSum = 0;
      HashMap<Integer, Integer> map = new HashMap<>(); // to store {prefixSum, count}
      map.put(0, 1); // Initialize map with {0, 1} to handle the case when prefixSum - k = 0

      // check if prefixSum - k exists in the map i.e some subarray sum is equal to k
      for (int num : arr) {
        prefixSum += num;
        // Check if (prefixSum - k) exists in the map
        if (map.containsKey(prefixSum - k)) {
          count += map.get(prefixSum - k);
        }
        // Store prefixSum in the map
        map.put(prefixSum, map.getOrDefault(prefixSum, 0) + 1);
      }
      return count;
    }
  }

  public static void main(String[] args) {
    P010 p010 = new P010();
    Solution solution = p010.new Solution();
    int[] arr = { 1, 1, 1, 2, 1, 1, 2 };
    int k = 2;
    System.out.println(solution.subarraySum(arr, k)); // Output: 5

    int arr2[] = { 1, 4, 12, 2, 3, 4, 5, 2, 6, 7, 8, 9, 10 };
    int k2 = 16;
    System.out.println(solution.subarraySum(arr2, k2)); // Output: 2
  }
}
