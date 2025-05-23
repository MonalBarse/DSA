package com.monal.DP.DP_Strings;

/**
 * 115. Distinct Subsequences
 * Given two strings s and t, return the number of distinct subsequences of s
 * which equals t.
 *
 * Example 1:
 * Input: s = "rabbbit", t = "rabbit"
 * Output: 3
 * Explanation: As shown below, there are 3 ways you can generate "rabbit" from
 * (underscores are wrapped to char that are used to form t):
 * ra _b_ _b_ bit
 * rab _b_ _b_ it
 * ra _b_ b _b_ it
 *
 *
 * Example 2:
 * Input: s = "babgbag", t = "bag"
 * Output: 5
 * Explanation: As shown below, there are 5 ways you can generate "bag" from
 * (underscores are wrapped to char that are used to form t):
 * _ba_ b _g_ bag
 * babg _bag_
 * _b_ abgb _ag_
 * ba _b_ gb _ag_
 * _ba_ bgba _g_
 *
 */
public class P006 {
  class Solution {

    // ============================================= //
    // -------------- Memoization ------------------ //
    // ============================================= //
    public int numDistinctMemo(String s, String t) {
      int n = s.length(), m = t.length();
      int memo[][] = new int[n + 1][m + 1];
      // fill it with -1
      for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= m; j++) {
          memo[i][j] = -1;
        }
      }

      return numDistinctMemoHelper(s, t, n, m, memo);
    }

    private int numDistinctMemoHelper(String s, String t, int n, int m, int[][] memo) {
      if (m == 0)
        return 1; // t is empty, one way to form it
      if (n == 0)
        return 0; // s is empty, no way to form t
      if (memo[n][m] != -1)
        return memo[n][m]; // check if already computed
      // base case

      if (s.charAt(n - 1) == t.charAt(m - 1)) {
        // If last characters match, we have two options
        // 1. Use this match: numDistinctMemoHelper(s, t, n - 1, m - 1, memo)
        // 2. Skip s[n-1]: numDistinctMemoHelper(s, t, n - 1, m, memo)
        memo[n][m] = numDistinctMemoHelper(s, t, n - 1, m - 1, memo) + numDistinctMemoHelper(s, t, n - 1, m, memo);
      } else {
        // If it doesn't match, we can only skip s[n-1]
        memo[n][m] = numDistinctMemoHelper(s, t, n - 1, m, memo);
      }

      return memo[n][m];
    }

    // ============================================= //
    // --------------- Tabulation ------------------ //
    // ============================================= //

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

    // ============================================= //
    // ------------ Space Optimization ------------- //
    // ============================================= //

    // used TWO rows
    public int numDistinctSpaceOptimized(String s, String t) {
      int n = s.length(), m = t.length();

      // we note that previously we used
      // only the previous row to calculate the current row and the current column
      // dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j]

      int[] prev = new int[m + 1]; // should be the length of t + 1
      prev[0] = 1; // base case

      for (int i = 1; i <= n; i++) {
        int[] curr = new int[m + 1];
        curr[0] = 1; // 1 way to form t when t is empty
        for (int j = 1; j <= m; j++) {
          if (s.charAt(i - 1) == t.charAt(j - 1)) {
            curr[j] = prev[j - 1] + prev[j];
          } else {
            curr[j] = prev[j];
          }
        }
        prev = curr; // update prev to current row
      }
      return prev[m]; // return the last element of the last row
    }

    // Using only ONE row
    @SuppressWarnings("unused")
    private int numDistinctMoreOptimized(String s, String t) {
      int n = s.length(), m = t.length();

      // we note that we used two rows to calculate the current row and the current
      // column
      // prev[] and curr[]
      // but we can optimize it to use only one row as we only need
      // the previous row to calculate the current row and the current column
      // thus we can just overwrite the previous row with the current row

      int[] prev = new int[m + 1]; // should be the length of t + 1

      prev[0] = 1; // base case

      for (int i = 1; i <= n; i++) {
        for (int j = m; j >= 1; j--) {
          if (s.charAt(i - 1) == t.charAt(j - 1)) {
            prev[j] = prev[j - 1] + prev[j]; // both cases
          }
        }
      }
      return prev[m]; // return the last element of the last row
    }

  }

}
