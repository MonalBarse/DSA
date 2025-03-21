package com.monal.Array;
// https://leetcode.com/problems/two-sum/

import java.util.HashMap;

/*
  Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
  You may assume that each input would have exactly one solution, and you may not use the same element twice.
  You can return the answer in any order.

  Example 1:

  Input: nums = [2,7,11,15], target = 9
  Output: [0,1]
  Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].

  Example 2:

  Input: nums = [3,2,4], target = 6
  Output: [1,2]
  Example 3:

  Input: nums = [3,3], target = 6
  Output: [0,1]
*/

public class P003 {

  class Solution {
    public int[] twoSum(int[] arr, int target) {
      HashMap<Integer, Integer> map = new HashMap<>();
      for (int i = 0; i < arr.length; i++) {
        int first = arr[i];

        if (map.containsKey(target - arr[i])) {
          return new int[] { i, map.get(target - arr[i]) };
        }
        map.put(first, i);
      }
      return new int[] { -1, -1 };
    }
  }

  public static void main(String[] args) {
    Solution solution = new P003().new Solution();

    int[] arr1 = { 2, 7, 11, 15 };
    int target1 = 9;
    int[] arr2 = { 3, 2, 4 };
    int target2 = 6;
    int[] arr3 = { 3, 3 };
    int target3 = 6;

    int[] result1 = solution.twoSum(arr1, target1);
    int[] result2 = solution.twoSum(arr2, target2);
    int[] result3 = solution.twoSum(arr3, target3);

    System.out.println("Result 1: " + result1[0] + ", " + result1[1]); // Expected: 0, 1
    System.out.println("Result 2: " + result2[0] + ", " + result2[1]); // Expected: 1, 2
    System.out.println("Result 3: " + result3[0] + ", " + result3[1]); // Expected: 0, 1
  }

}
