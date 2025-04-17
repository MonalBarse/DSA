package com.monal.DP.DP_Strings;

/**
 * 115. Distinct Subsequences
 * Given two strings s and t, return the number of distinct subsequences of s
 * which equals t.
 *
 * Example 1:
 * Input: s = "rabbbit", t = "rabbit"
 * Output: 3
 *
 * Example 2:
 * Input: s = "babgbag", t = "bag"
 * Output: 5
 *
 */
public class P014 {
  class Solution {
    public int numDistinct(String s, String t) {
      int n = s.length(), m = t.length();
      int dp[][] = new int[n + 1][m + 1];

      for (int i = 0; i <= n; i++) {
        dp[i][0] = 1;
      }

      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
          if (s.charAt(i - 1) == t.charAt(j - 1)) {
            // If char mathces we have two options
            // 1. Use this match: dp[i - 1][j - 1]
            // 2. Skip s[i-1]: dp[i - 1][j]
            dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];
          } else {
            // If it doesn't match, we can only skip s[i-1]
            // 1. Skip s[i-1]: dp[i - 1][j]
            dp[i][j] = dp[i - 1][j];
          }
        }
      }

      return dp[n][m];
    }
  }

}
