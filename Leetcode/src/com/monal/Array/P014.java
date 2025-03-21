package com.monal.Array;

import java.util.*;

// 4Sum - https://leetcode.com/problems/4sum/

/*
Given an array nums of n integers, return an array of all the unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
0 <= a, b, c, d < n          a, b, c, and d are distinct.
  nums[a] + nums[b] + nums[c] + nums[d] == target
You may return the answer in any order.

Example 1:
  Input: nums = [1,0,-1,0,-2,2], target = 0
  Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
Example 2:
  Input: nums = [2,2,2,2,2], target = 8
  Output: [[2,2,2,2]]
*/
public class P014 {

  class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
      List<List<Integer>> res = new ArrayList<>();
      int n = nums.length;
      Arrays.sort(nums); // Step 1: Sort the array

      for (int i = 0; i < n - 3; i++) {
        if (i > 0 && nums[i] == nums[i - 1])
          continue; // Skip duplicate values

        for (int j = i + 1; j < n - 2; j++) {
          if (j > i + 1 && nums[j] == nums[j - 1])
            continue; // Skip duplicate values

          int left = j + 1, right = n - 1;

          while (left < right) {
            long sum = (long) nums[i] + nums[j] + nums[left] + nums[right]; // Avoid integer overflow

            if (sum == target) {
              res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
              left++;
              right--;

              // Skip duplicates
              while (left < right && nums[left] == nums[left - 1])
                left++;
              while (left < right && nums[right] == nums[right + 1])
                right--;
            } else if (sum < target) {
              left++;
            } else {
              right--;
            }
          }
        }
      }
      return res;
    }
  }

}
