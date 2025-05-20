package com.monal.DP.DP_LIS;

import java.util.*;
/*
Given an integer array nums, return the number of longest increasing subsequences.
Notice that the sequence has to be strictly increasing.

Example 1:
  Input: nums = [1,3,5,4,7]
  Output: 2
  Explanation: The two longest increasing subsequences are [1, 3, 4, 7] and [1, 3, 5, 7].
Example 2:
  Input: nums = [2,2,2,2,2]
  Output: 5
  Explanation: The length of the longest increasing subsequence is 1, and there are 5 increasing subsequences of length 1, so output 5.
*/

public class P005 {
  class Solution {
    public int findNumberOfLIS(int[] nums) {
      int n = nums.length;
      int[] dp = new int[n]; // length of longest increasing subsequence ending at i
      int[] count = new int[n]; // count of longest increasing subsequences ending at i
      Arrays.fill(dp, 1); // every single elem is inc subseq of len 1
      Arrays.fill(count, 1); // there's at least one way forming subseq of len 1
      int maxLen = 1;

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < i; j++) {
          // if current element is greater than previous element, we can extend
          if (nums[j] < nums[i]) {
            // if we found a longer increasing subsequence, update dp and reset count
            if (dp[j] + 1 > dp[i]) {
              dp[i] = dp[j] + 1; // new length is one more than previous longest increasing subsequence
              count[i] = count[j]; // reset count to the count of previous longest increasing subsequence
            }
            // if we found another way to get the same length of increasing
            // subsequence add to the count
            else if (dp[j] + 1 == dp[i]) {
              count[i] += count[j]; // add count of previous longest increasing subsequence to current count
            }
          }
        }
        maxLen = Math.max(maxLen, dp[i]);
      }

      int total = 0;
      for (int i = 0; i < n; i++) {
        // if the length of increasing subsequence ending at i is equal to maxLen, add
        // count to total
        if (dp[i] == maxLen) {
          total += count[i];
        }
      }
      return total;
    }
  }
}