package com.monal.Array;

import java.util.*;

// https://leetcode.com/problems/longest-consecutive-sequence/
/*
Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
You must write an algorithm that runs in O(n) time.

Example 1:
  Input: nums = [100,4,200,1,3,2]
  Output: 4
  Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
Example 2:
  Input: nums = [0,3,7,2,5,8,4,6,0,1]
  Output: 9
Example 3:
  Input: nums = [1,0,1,2]
  Output: 3
Constraints:
  0 <= nums.length <= 105 */
public class P008 {

  class Solution {
    public int longestConsecutive(int[] arr) {
      if (arr.length == 0)
        return 0;
      // Using HashSet as it provides O(1) time complexity for search
      Set<Integer> set = new HashSet<>();
      for (int num : arr) {
        set.add(num); // O(n) insert all elements
      }
      int maxCount = 0;

      for (int elem : set) {
        if (!set.contains(elem - 1)) {
          int count = 1;
          int currentElem = elem;
          while (set.contains(currentElem + 1)) {
            count++;
            currentElem++;
          }
          maxCount = Math.max(maxCount, count);
        }
      }

      return maxCount;
    }
  }

  public static void main(String[] args) {
    Solution solution = new P008().new Solution();

    int[] arr0 = {};
    int[] arr1 = { 100, 4, 200, 1, 3, 2 };
    int[] arr2 = { 0, 3, 7, 2, 5, 8, 4, 6, 0, 1 };
    int[] arr3 = { 1, 0, 1, 2 };
    int[] arr4 = { 3, 3, 2, 1, 201, 10, 204, 200, 1, 203, 202 };

    System.out.println("Result 0: " + solution.longestConsecutive(arr0) + "  Expected: 0");
    System.out.println("Result 1: " + solution.longestConsecutive(arr1) + "  Expected: 4");
    System.out.println("Result 2: " + solution.longestConsecutive(arr2) + "  Expected: 9");
    System.out.println("Result 3: " + solution.longestConsecutive(arr3) + "  Expected: 3");
    System.out.println("Result 4: " + solution.longestConsecutive(arr4) + "  Expected: 5");
  }
}
