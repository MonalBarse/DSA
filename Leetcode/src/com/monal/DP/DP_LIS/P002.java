package com.monal.DP.DP_LIS;

import java.util.*;

/*
Given an integer array nums, return the longest strictly increasing subsequence.

Example 1:
  Input: nums = [10,9,2,5,3,7,101,18]
  Output: [2,5,7,18] or [2,5,7,101]
  Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
Example 2:
  Input: nums = [0,1,0,3,2,3]
  Output: [0,1,2,3]
Example 3:
  Input: nums = [7,7,7,7,7,7,7]
  Output: [7]
*/
public class P002 {
  class Solution {

    public List<Integer> getLIS(int[] nums) {
      int n = nums.length;
      int[] dp = new int[n];
      int[] prev = new int[n];
      Arrays.fill(dp, 1);
      Arrays.fill(prev, -1);

      int maxLength = 1;
      int lastIndex = 0;

      // Calculate the length of the longest increasing subsequence
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < i; j++) {
          if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
            dp[i] = dp[j] + 1;
            prev[i] = j;
          }
        }

        if (dp[i] > maxLength) {
          maxLength = dp[i];
          lastIndex = i;
        }
      }
      // reconstruct LIS
      List<Integer> lis = new ArrayList<>();
      while (lastIndex != -1) {
        lis.add(nums[lastIndex]);
        lastIndex = prev[lastIndex];
      }
      Collections.reverse(lis); // because we added from back to front
      return lis;
    }
  }

}
