package com.monal.DP.DP_Strings;

public class P001 {
  // ====================== LONGEST COMMON SUBSEQUENCE =====================//
  /**
   * Recursive implementation of the Longest Common Subsequence (LCS) problem
   * Time Complexity: O(2^(m+n))
   * Space Complexity: O(m+n) - recursion stack
   */

  /*
   * LCS problem: Given two strings, find the length of the longest subsequence
   * present in both of them.
   * Example 1:
   * text1 = "ABCXDEFYHIJZKFLMGNOPQRSTUVWXYZ"
   * text2 = "ABCXDEFYHIJZKFLMGNOPQRSTUVWXYZ"
   * Output: 26
   *
   * Example 2:
   * text1 = "abcdetuvwxyz"
   * text2 = "abcdefghijkl"
   * Output: 5
   * Explanation: The longest common subsequence is "abcde".
   */

  public int lcsRecursive(String text1, String text2, int m, int n) {
    // Base case
    if (m == 0 || n == 0)
      return 0;

    // If last characters match
    if (text1.charAt(m - 1) == text2.charAt(n - 1)) {
      return 1 + lcsRecursive(text1, text2, m - 1, n - 1);
    }

    // If last characters don't match
    return Math.max(
        lcsRecursive(text1, text2, m - 1, n),
        lcsRecursive(text1, text2, m, n - 1));
  }

  /**
   * Memoized implementation of the LCS problem
   * Time Complexity: O(m * n)
   * Space Complexity: O(m * n)
   */
  public int lcsMemoized(String text1, String text2) {
    int m = text1.length();
    int n = text2.length();
    Integer[][] memo = new Integer[m + 1][n + 1];
    return lcsMemoizedHelper(text1, text2, m, n, memo);
  }

  private int lcsMemoizedHelper(String text1, String text2, int m, int n, Integer[][] memo) {
    // Base case
    if (m == 0 || n == 0)
      return 0;

    // Check if already computed
    if (memo[m][n] != null)
      return memo[m][n];

    // If last characters match
    if (text1.charAt(m - 1) == text2.charAt(n - 1)) {
      memo[m][n] = 1 + lcsMemoizedHelper(text1, text2, m - 1, n - 1, memo);
    } else {
      // If last characters don't match
      memo[m][n] = Math.max(
          lcsMemoizedHelper(text1, text2, m - 1, n, memo),
          lcsMemoizedHelper(text1, text2, m, n - 1, memo));
    }

    return memo[m][n];
  }

  /**
   * Tabulated implementation of the LCS problem
   * Time Complexity: O(m * n)
   * Space Complexity: O(m * n)
   */

  public int lcsTabulated(String text1, String text2) {
    int m = text1.length();
    int n = text2.length();

    // Create and initialize table
    int[][] dp = new int[m + 1][n + 1];

    // Fill the table
    for (int i = 0; i <= m; i++) {
      for (int j = 0; j <= n; j++) {
        // Base case
        if (i == 0 || j == 0) {
          dp[i][j] = 0;
        }
        // If characters match
        else if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
          dp[i][j] = 1 + dp[i - 1][j - 1];
        }
        // If characters don't match
        else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    return dp[m][n];
  }

  @SuppressWarnings("unused")
  private int lcsSpaceOptimized(String text1, String text2) {
    int n = text1.length();
    int m = text2.length();

    // We note that dp[i][j] only depends on dp[i-1][j] and dp[i][j-1]
    // So we can use a 1D array to save space
    int[] prev = new int[m + 1];
    int[] curr = new int[m + 1];

    // Fill the table
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= m; j++) {
        // Base case: if any string is empty its LCS is 0
        if (i == 0 || j == 0) {
          curr[j] = 0;
        }

        // If characters match
        else if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
          curr[j] = 1 + prev[j - 1];
        }

        // If characters don't match
        else {
          curr[j] = Math.max(prev[j], curr[j - 1]);
        }
      }
    }

    // The last cell of the current row contains the length of LCS
    return curr[m];
  }
}
