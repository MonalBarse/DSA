package com.monal.DP.DP_Strings;

/**
 * PROBLEM : Print Shortest Common Supersequence
 * ❓ Problem Statement
 * Given two strings s1 and s2, return the shortest string that has both s1 and
 * s2 as subsequences. If multiple solutions exist, return any.
 *
 * Input: s1 = "abac", s2 = "cab"
 * Output: "cabac" or "cabac"
 *
 * Input: s1 = "abc", s2 = "def"
 * Output: "abcdef"
 *
 * Input: s1 = "abc", s2 = "abc"
 * Output: "abc"
 *
 * Input: s1 = "banana" s2 = "anaphile"
 * Output: "bananaphile" or "anaphilebanana"
 */

public class P008 {

  public String shortestCommoString(String S1, String S2) {
    int n = S1.length(), m = S2.length();

    // return scsRecursive(S1, n, S2, m);

    // String[][] memo = new String[n + 1][m + 1];
    // for (String[] arr : memo) {
    // Arrays.fill(arr, null);
    // }
    // return scsMemoized(S1, n, S2, m, memo);

    return scsTabulated(S1, n, S2, m);
  }
  // ------------ MEMORY LIMIT EXCEEDED ---------------- //

  @SuppressWarnings("unused")
  private String scsRecursive(String S1, int n, String S2, int m) {
    if (n == 0)
      return S2;
    if (m == 0)
      return S1;

    // If last characters match
    if (S1.charAt(n - 1) == S2.charAt(m - 1)) {
      return scsRecursive(S1, n - 1, S2, m - 1) + S1.charAt(n - 1);
    }

    // If last characters don't match
    String option1 = scsRecursive(S1, n - 1, S2, m);
    String option2 = scsRecursive(S1, n, S2, m - 1);

    return option1.length() < option2.length() ? option1 + S1.charAt(n - 1) : option2 + S2.charAt(m - 1);
  }

  @SuppressWarnings("unused")
  private String scsMemoized(String S1, int n, String S2, int m, String[][] memo) {
    if (n == 0)
      return S2;
    if (m == 0)
      return S1;

    if (memo[n][m] != null)
      return memo[n][m];

    if (S1.charAt(n - 1) == S2.charAt(m - 1)) {
      memo[n][m] = scsMemoized(S1, n - 1, S2, m - 1, memo) + S1.charAt(n - 1);
      return memo[n][m];
    }
    // If last characters don't match
    String option1 = scsMemoized(S1, n - 1, S2, m, memo);
    String option2 = scsMemoized(S1, n, S2, m - 1, memo);

    memo[n][m] = option1.length() < option2.length() ? option1 + S1.charAt(n - 1) : option2 + S2.charAt(m - 1);
    return memo[n][m];
  }

  private String scsTabulated(String S1, int n, String S2, int m) {
    String[][] dp = new String[n + 1][m + 1];

    for (int i = 0; i <= n; i++) {
      dp[i][0] = S1.substring(0, i);
    }
    for (int j = 0; j <= m; j++) {
      dp[0][j] = S2.substring(0, j);
    }

    // Fill the DP table
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + S1.charAt(i - 1);
        } else {
          String option1 = dp[i - 1][j];
          String option2 = dp[i][j - 1];
          dp[i][j] = option1.length() < option2.length() ? option1 + S1.charAt(i - 1) : option2 + S2.charAt(j - 1);
        }
      }
    }

    return dp[n][m];
  }

  class Solution {
    public String shortestCommonSupersequence(String str1, String str2) {
      // Choose either top-down or bottom-up approach
      // return topDownSCS(str1, str2);
      return bottomUpSCS(str1, str2);
    }

    // Optimal Top-Down (Memoized) Approach
    @SuppressWarnings("unused")
    private String topDownSCS(String str1, String str2) {
      int n = str1.length();
      int m = str2.length();

      // Create memoization table for LCS lengths
      int[][] memo = new int[n + 1][m + 1];
      for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= m; j++) {
          memo[i][j] = -1;
        }
      }

      // Build LCS table using memoization
      lcs(str1, str2, n, m, memo);

      // Reconstruct SCS from LCS table
      StringBuilder result = new StringBuilder();
      int i = n, j = m;

      while (i > 0 && j > 0) {
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          result.append(str1.charAt(i - 1));
          i--;
          j--;
        } else if (memo[i - 1][j] > memo[i][j - 1]) {
          result.append(str1.charAt(i - 1));
          i--;
        } else {
          result.append(str2.charAt(j - 1));
          j--;
        }
      }

      // Add remaining characters
      while (i > 0) {
        result.append(str1.charAt(i - 1));
        i--;
      }
      while (j > 0) {
        result.append(str2.charAt(j - 1));
        j--;
      }

      return result.reverse().toString();
    }

    // Helper method for top-down approach
    private int lcs(String str1, String str2, int i, int j, int[][] memo) {
      if (i == 0 || j == 0)
        return 0;
      if (memo[i][j] != -1)
        return memo[i][j];

      if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
        memo[i][j] = 1 + lcs(str1, str2, i - 1, j - 1, memo);
      } else {
        memo[i][j] = Math.max(lcs(str1, str2, i - 1, j, memo), lcs(str1, str2, i, j - 1, memo));
      }

      return memo[i][j];
    }

    // Optimal Bottom-Up (Tabulated) Approach
    private String bottomUpSCS(String str1, String str2) {
      int n = str1.length();
      int m = str2.length();

      // Build LCS table
      int[][] dp = new int[n + 1][m + 1];

      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
          if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
            dp[i][j] = 1 + dp[i - 1][j - 1];
          } else {
            dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
          }
        }
      }

      // Reconstruct SCS
      StringBuilder result = new StringBuilder();
      int i = n, j = m;

      while (i > 0 && j > 0) {
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          result.append(str1.charAt(i - 1));
          i--;
          j--;
        } else if (dp[i - 1][j] > dp[i][j - 1]) {
          result.append(str1.charAt(i - 1));
          i--;
        } else {
          result.append(str2.charAt(j - 1));
          j--;
        }
      }

      // Add remaining characters
      while (i > 0) {
        result.append(str1.charAt(i - 1));
        i--;
      }
      while (j > 0) {
        result.append(str2.charAt(j - 1));
        j--;
      }

      return result.reverse().toString();
    }
  }
}
