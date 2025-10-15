package com.monal.DP.DP_Strings;

import java.util.Arrays;

public class P008 {
  public class Solution {
    String s;
    int[][] memo; // dp[l][r] = maximum divisin possible for the substring text[l...r].

    public int longestDecomposition(String text) {
      this.s = text;
      int n = text.length();
      memo = new int[n][n];
      for (int[] row : memo) Arrays.fill(row, -1);

      return solve(0, n - 1);
    }

    private int solve(int start, int end) {
      if (start > end) return 0; // no characters
      if (memo[start][end] != -1) return memo[start][end];

      int maxK = 1; // at least the whole string
      int len = end - start + 1;

      // try all prefix lengths
      for (int size = 1; size <= len / 2; size++) {
        if (isEqual(start, start + size - 1, end - size + 1, end)) {
          maxK = Math.max(maxK, 2 + solve(start + size, end - size));
          break; // Important: take the smallest prefix match first
        }
      }
      return memo[start][end] = maxK;
    }

    // helper to check if substring [l1..r1] equals [l2..r2]
    private boolean isEqual(int l1, int r1, int l2, int r2) {
      while (l1 <= r1 && l2 <= r2) {
        if (s.charAt(l1++) != s.charAt(l2++))
          return false;
      }
      return true;
    }
  }
}
