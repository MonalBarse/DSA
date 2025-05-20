package com.monal.DP.DP_LIS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        int take = 1 + LISrecursive(arr, curr + 1, curr);
        return Math.max(take, not_take);
      } else {
        return not_take;
      }
    }

    @SuppressWarnings("unused")
    private int LIS_memoized(int[] arr, int curr, int prev, int[][] memo) {
      int n = arr.length;
      if (curr >= n) {
        return 0;
      }
      if (memo[curr][prev + 1] != -1) {
        return memo[curr][prev + 1];
      }
      int not_take = LIS_memoized(arr, curr + 1, prev, memo);
      // if prev is not present or its increasing
      if (prev == -1 || arr[curr] > arr[prev + 1]) {
        // take the current element
        int take = 1 + LIS_memoized(arr, curr + 1, curr, memo);
        memo[curr][prev + 1] = Math.max(not_take, take);
        return memo[curr][prev + 1];
      } else {
        // don't take the current element
        memo[curr][prev + 1] = not_take;
        return memo[curr][prev + 1];
      }
    }

    @SuppressWarnings("unused")
    private int LIS_tabulated(int[] arr, int n) {
      int[] dp = new int[n];
      Arrays.fill(dp, 1); // Every element is at least an LIS of length 1

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < i; j++) {
          if (arr[j] < arr[i]) {
            dp[i] = Math.max(dp[i], dp[j] + 1);
          }
        }
      }

      int maxLen = 0;
      for (int len : dp) {
        maxLen = Math.max(maxLen, len);
      }
      return maxLen;

    }

    @SuppressWarnings("unused")
    private int LIS_BS(int arr[], int n) {
      List<Integer> temp = new ArrayList<>();
      for (int nm : arr) {
        int idx = Collections.binarySearch(temp, nm);
        if (idx < 0)
          idx = -(idx + 1); // insertion point
        if (idx == temp.size()) {
          temp.add(nm); // extend the LIS
        } else {
          temp.set(idx, nm); // replace to keep potential LIS growing
        }
      }
      return temp.size(); // LIS length
    }

  }
}