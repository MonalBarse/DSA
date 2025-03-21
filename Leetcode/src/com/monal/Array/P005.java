package com.monal.Array;

import java.util.HashMap;
// https://leetcode.com/problems/majority-element/description/

/*
  Given an array nums of size n, return the majority element.
  The majority element is the element that appears more than ⌊n / 2⌋ times. You may assume that the majority element always exists in the array.

    Example 1:
      Input: nums = [3,2,3]
      Output: 3

    Example 2:
      Input: nums = [2,2,1,1,1,2,2]
      Output: 2

    Constraints:
      n == nums.length
      1 <= n <= 5 * 104
    -109 <= nums[i] <= 109
 */

public class P005 {
  class Solution {
    public int majorityElement(int[] arr) {
      int n = arr.length;
      int majority = n / 2;

      HashMap<Integer, Integer> map = new HashMap<>(); // (number -> freq)

      // Count frequency of each element
      for (int i = 0; i < arr.length; i++) {
        // Get current frequency (default 0 if not found) and increment by 1
        map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
      }

      // Find the element with frequency > n/2
      for (int key : map.keySet()) {
        if (map.get(key) > majority) {

          return key;
        }
      }

      // This return should never be reached
      return -1;
    }

    // Boyer-Moore Voting Algorithm
    // If there is no majority element, the algorithm may return an incorrect
    // result. However, for this problem, the assumption is that a majority element
    // always exists.
    public int majorityElement1(int[] arr) {
      // Candidate for majority element and its frequency is count = 0
      int candidate = 0, count = 0;

      // Find the majority element
      for (int num : arr) {
        // If count is 0, set the current element as the candidate
        if (count == 0) {
          candidate = num;
        }
        // If the current element is the candidate, increment the count if not
        // decrement the count
        count = count + ((num == candidate) ? 1 : -1);
      }
      return candidate;
    }

  }

  public static void main(String[] args) {
    Solution solution = new P005().new Solution();

    int[] arr1 = { 3, 2, 3 };
    int[] arr2 = { 2, 2, 1, 1, 1, 2, 2 };

    System.out.println("Result 1: " + solution.majorityElement(arr1)); // Expected: 3
    System.out.println("Result 2: " + solution.majorityElement(arr2)); // Expected: 2
  }
}
