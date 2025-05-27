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

public class P006 {
  class Solution {
    public int findNumberOfLIS(int[] nums) {
      int n = nums.length;

      /*
       * TWO ARRAYS APPROACH:
       * dp[i] = Length of the Longest Increasing Subsequence ending at index i
       * count[i] = Number of different LIS that end at index i
       *
       * KEY INSIGHT: We need to track both LENGTH and COUNT simultaneously
       * because multiple subsequences can have the same maximum length
       */
      int[] dp = new int[n], count = new int[n];

      Arrays.fill(dp, 1); // Minimum LIS length is 1 (the element itself)
      Arrays.fill(count, 1); // Each element has 1 way to form length-1 LIS

      int maxLenFound = 1; // Track the overall maximum LIS length found so far
      /*
       * Main Logic:
       * - For each element nums[i], check all previous elements nums[prev]
       * - if prevous element is less than current (Increasing order)
       * then we can extend the LIS ending at prev to include nums[i]
       * - Update dp[i] and count[i] accordingly
       */
      for (int i = 1; i < n; i++) {
        for (int prev = 0; prev < i; prev++) {
          /*
           * CONSTRAINT CHECK: Can we extend LIS from prev index to current index i?
           * We can only extend if nums[prev] < nums[i]
           */
          if (nums[i] > nums[prev]) {

            /*
             * CASE 1: FOUND A LONGER SUBSEQUENCE
             * If extending from j gives us a longer LIS at i than what we had before
             *
             * WHY dp[prev] + 1 > dp[i]?
             * - dp[i] currently holds the best LIS length ending at i
             * - If dp[prev] + 1 > dp[i], we found a way to make a longer LIS at i
             *
             * WHY dp[prev] + 1?
             * - dp[prev] is the LIS length ending at index `prev`
             * - Adding nums[i] extends it by 1, so new length = dp[prev] + 1
             */
            if (dp[prev] + 1 > dp[i]) {
              dp[i] = dp[prev] + 1; // Update to new longer length
              count[i] = count[prev]; // Reset count - all ways come from j now

              /*
               * WHY count[i] = count[prev]?
               * - We found a LONGER LIS, so previous shorter ones don't matter
               * - All the ways to form LIS at prev can now be extended to i
               * - So count[i] becomes exactly count[prev] (not added to existing)
               */
            }

            /*
             * CASE 2: FOUND ANOTHER WAY TO ACHIEVE SAME LENGTH
             * If extending from prev gives us the SAME length we already had at i
             *
             * WHY dp[prev] + 1 == dp[i]?
             * - We already found some LIS of length dp[i] ending at i
             * - Now we found another path: extend LIS from `prev`, gives length dp[i]
             * - This means we have MULTIPLE ways to achieve the same optimal length
             */
            else if (dp[prev] + 1 == dp[i]) {
              count[i] += count[prev]; // Add prev's ways to existing ways
              /*
               * WHY count[i] += count[prev]?
               * - We already had count[i] ways to form LIS of length dp[i] at i
               * - Now we found count[prev] MORE ways (by extending from prev)
               * - Total ways = ways from i + new ways from `prev`
               */
            }

            /*
             * WHY ONLY THESE TWO CASES?
             *
             * Mathematical proof:
             * - dp[prev] + 1 can be >, =, or < than dp[i]
             * - If dp[prev] + 1 < dp[i]: This means there this is no way to extend
             * - If dp[prev] + 1 = dp[i]: This path gives same length, count it (Case 2)
             * - If dp[prev] + 1 > dp[i]: This path is better, replace everything (Case 1)
             *
             * We don't handle dp[prev] + 1 < dp[i] because it's not useful
             */
          }
        }

        // Update the global maximum LIS length seen so far
        maxLenFound = Math.max(maxLenFound, dp[i]);
      }

      /*
       * FINAL STEP: Count all LIS that have the maximum length
       *
       * WHY THIS STEP?
       * - dp[i] tells us LIS length ending at position i
       * - count[i] tells us how many LIS of that length end at position i
       * - We want total count of ALL LIS that have maximum length (maxLen)
       * - Some might end at position 3, some at position 7, etc.
       */
      int totalWays = 0;
      for (int i = 0; i < n; i++) {
        if (dp[i] == maxLenFound) { // This position has max length LIS
          totalWays += count[i]; // Add its count to total
        }
      }

      return totalWays;
    }
  }

  /*
   * EXAMPLE WALKTHROUGH:
   * nums = [1, 3, 5, 4, 7]
   *
   * Initial: dp = [1,1,1,1,1], count = [1,1,1,1,1]
   *
   * i=1, j=0: nums[0]=1 < nums[1]=3
   * dp[0]+1 = 2 > dp[1]=1, so dp[1]=2, count[1]=count[0]=1
   * Result: dp=[1,2,1,1,1], count=[1,1,1,1,1]
   *
   * i=2, j=0: nums[0]=1 < nums[2]=5
   * dp[0]+1 = 2 > dp[2]=1, so dp[2]=2, count[2]=count[0]=1
   * i=2, j=1: nums[1]=3 < nums[2]=5
   * dp[1]+1 = 3 > dp[2]=2, so dp[2]=3, count[2]=count[1]=1
   * Result: dp=[1,2,3,1,1], count=[1,1,1,1,1]
   *
   * i=3, j=0: nums[0]=1 < nums[3]=4
   * dp[0]+1 = 2 > dp[3]=1, so dp[3]=2, count[3]=count[0]=1
   * i=3, j=1: nums[1]=3 < nums[3]=4
   * dp[1]+1 = 3 > dp[3]=2, so dp[3]=3, count[3]=count[1]=1
   * i=3, j=2: nums[2]=5 > nums[3]=4, skip
   * Result: dp=[1,2,3,3,1], count=[1,1,1,1,1]
   *
   * i=4, j=0: nums[0]=1 < nums[4]=7
   * dp[0]+1 = 2 > dp[4]=1, so dp[4]=2, count[4]=1
   * i=4, j=1: nums[1]=3 < nums[4]=7
   * dp[1]+1 = 3 > dp[4]=2, so dp[4]=3, count[4]=1
   * i=4, j=2: nums[2]=5 < nums[4]=7
   * dp[2]+1 = 4 > dp[4]=3, so dp[4]=4, count[4]=1
   * i=4, j=3: nums[3]=4 < nums[4]=7
   * dp[3]+1 = 4 = dp[4]=4, so count[4] += count[3] = 1+1 = 2
   *
   * Final: dp=[1,2,3,3,4], count=[1,1,1,1,2], maxLen=4
   * Only dp[4]==maxLen=4, so answer = count[4] = 2
   *
   * The two LIS of length 4 are: [1,3,5,7] and [1,3,4,7]
   */
}