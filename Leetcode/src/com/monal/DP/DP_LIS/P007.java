package com.monal.DP.DP_LIS;

class P007 {
  class Solution {
    private static final int MOD = (int) 1e9 + 7;

    public int countOfPairs(int[] nums) {
      int n = nums.length;
      // dp[i][val] = number of valid sequences ending at position i with arr1[i] =
      // val
      int[][] dp = new int[n][51]; // nums[i] <= 50

      for (int val = 0; val <= nums[0]; val++)
        dp[0][val] = 1;

      for (int i = 1; i < n; i++) {
        for (int curr = 0; curr <= nums[i]; curr++) {
          // Current: arr1[i] = curr, arr2[i] = nums[i] - curr
          // Previous: arr1[i-1] = prev, arr2[i-1] = nums[i-1] - prev

          // 1. arr1 non-decreasing: prev <= curr
          // 2. arr2 non-increasing: nums[i-1] - prev >= nums[i] - curr
          // => prev <= nums[i-1] - nums[i] + curr
          // 3. prev >= 0 and prev <= nums[i-1]

          int maxPrev = Math.min(curr, nums[i - 1] - nums[i] + curr);
          maxPrev = Math.min(maxPrev, nums[i - 1]);
          for (int prev = 0; prev <= maxPrev; prev++)
            dp[i][curr] = (dp[i][curr] + dp[i - 1][prev]) % MOD;
        }
      }

      // Sum all possibilities for the last position
      int result = 0;
      for (int val = 0; val <= nums[n - 1]; val++)
        result = (result + dp[n - 1][val]) % MOD;
      return result;
    }
  }
}