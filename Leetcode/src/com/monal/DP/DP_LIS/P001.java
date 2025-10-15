package com.monal.DP.DP_LIS;

import java.util.*;

/*
Given an integer array nums, return the length of the longest strictly increasing subsequence.
Example 1:
  Input: nums = [10,9,2,5,3,7,101,18]
  Output: 4
  Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
Example 2:
  Input: nums = [0,1,0,3,2,3]
  Output: 4
Example 3:
  Input: nums = [7,7,7,7,7,7,7]
  Output: 1
*/
public class P001 {

  class Solution {
    public int lengthOfLIS(int[] arr) {
      return LISrecursive(arr, 0, -1);
    }

    private int LISrecursive(int[] arr, int curr, int prev) {
      int n = arr.length;
      // base case
      if (curr == n)
        return 0;

      // we have two choices - take the current element or not
      int not_take = LISrecursive(arr, curr + 1, prev);
      if (prev == -1 || arr[curr] > arr[prev]) {
        // take the current element
        // 1 is added to the length of LIS since we are taking the current element
        int take = 1 + LISrecursive(arr, curr + 1, curr);
        return Math.max(take, not_take);
      } else {
        return not_take;
      }
    }

    public int LIS_memo(int arr[]) {
      int n = arr.length;
      int[][] memo = new int[n + 1][n + 1];
      for (int[] a : memo) {
        Arrays.fill(a, -1);
      }
      return LIS_memoized(arr, 0, -1, memo);
    }

    private int LIS_memoized(int[] arr, int curr, int prev, int[][] memo) {
      int n = arr.length;
      if (curr == n)
        return 0;

      if (memo[curr][prev + 1] != -1)
        return memo[curr][prev + 1];

      int not_take = LIS_memoized(arr, curr + 1, prev, memo);
      int take = 0;
      if (prev == -1 || arr[curr] > arr[prev]) {
        take = 1 + LIS_memoized(arr, curr + 1, curr, memo);
      }
      return memo[curr][prev + 1] = Math.max(take, not_take);
    }

    public int LIStab(int arr[]) {
      int n = arr.length;
      int[][] dp = new int[n][n + 1];

      for (int i = n - 1; i >= 0; i--) {
        for (int prev = i - 1; prev >= -1; prev--) {
          int not_take = dp[i + 1][prev + 1];
          int take = 0;
          if (prev == -1 || arr[i] > arr[prev]) {
            take = 1 + dp[i + 1][i + 1];
          }
          dp[i][prev + 1] = Math.max(take, not_take);
        }
      }

      return dp[0][-1 + 1];
    }

    @SuppressWarnings("unused")
    private int LIS_tabulated(int[] arr, int n) {
      int[] dp = new int[n]; // dp[i] = length of LIS ending at index i
      Arrays.fill(dp, 1); // Every element is at least an LIS of length 1

      for (int i = 0; i < n; i++)
        for (int j = 0; j < i; j++)
          if (arr[j] < arr[i]) // if current element is greater than previous
            // then can either increase the length of LIS (dp[j] + 1) or
            // keep the previous length and skip the current element (dp[i])
            dp[i] = Math.max(dp[i], dp[j] + 1);

      int maxLen = 0;
      for (int len : dp)
        maxLen = Math.max(maxLen, len);
      return maxLen;
    }

    @SuppressWarnings("unused")
    private int LIS_BS(int arr[], int n) {
      List<Integer> temp = new ArrayList<>();
      for (int nm : arr) {
        int idx = Collections.binarySearch(temp, nm);
        if (idx < 0)
          idx = -(idx + 1); // insertion point
        if (idx == temp.size())
          temp.add(nm); // extend the LIS
        else
          temp.set(idx, nm); // replace to keep potential LIS growing
      }
      return temp.size(); // LIS length
    }
  }
}
