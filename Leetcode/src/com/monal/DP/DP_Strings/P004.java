package com.monal.DP.DP_Strings;

import java.util.Arrays;

/**
 * Given two strings str1 and str2, return the length of the shortest string
 * that has both str1 and str2 as subsequences.
 *
 * 🧠 A supersequence is a string that contains both original strings as
 * subsequences (characters in the same order, not necessarily contiguous).
 *
 * Example 1 :
 * Input: str1 = "abac", str2 = "cab"
 * Output: 5
 * Explanation: One of the shortest supersequences is "cabac"
 *
 * Example 2 :
 * Input: str1 = "abc", str2 = "def"
 * Output: 6
 * Explanation: One of the shortest supersequences is "abcdef"
 *
 * Example 3 :
 * Input: str1 = "abc", str2 = "abc"
 * Output: 3
 * Explanation: One of the shortest supersequences is "abc"
 *
 * Appraoch:
 * 1. Find the length of the longest common subsequence (LCS) of str1 and str2.
 * 2. The length of the shortest supersequence is given by:
 * length of str1 + length of str2 - length of LCS
 * 3. This is because the characters in the LCS are counted in both str1 and
 * str2, so we subtract them once to avoid double counting.
 * 4. The result is the total length of both strings minus the length of LCS.
 * 5. The LCS can be found using dynamic programming.
 *
 */
public class P004 {
  public int shortestSupersequence(String s1, String s2) {
    int m = s2.length(), n = s1.length();
    int[][] memo = new int[n + 1][m + 1];
    for (int[] arr : memo) {
      Arrays.fill(arr, -1);
    }
    // int lcs = lcsRecursive(s1, s1.length(), s2, s2.length());
    int lcs = lcsMemoized(s1, n, s2, m, memo);
    // int lcs = lcsTabulated(s1, n, s2, m);
    return n + m - lcs;
  }

  @SuppressWarnings("unused")
  private int lcsRecursive(String text1, int n, String text2, int m) {
    if (m == 0 || n == 0)
      return 0;

    // If last characters match
    if (text1.charAt(n - 1) == text2.charAt(m - 1)) {
      return 1 + lcsRecursive(text1, n - 1, text2, m - 1);
    }
    // If last characters don't match
    return Math.max(
        lcsRecursive(text1, n, text2, m - 1),
        lcsRecursive(text1, n - 1, text2, m));
  }

  private int lcsMemoized(String S1, int n, String S2, int m, int[][] memo) {

    if (m == 0 || n == 0)
      return 0;
    if (memo[n][m] != -1)
      return memo[n][m];

    // If last characters match
    if (S1.charAt(n - 1) == S2.charAt(m - 1)) {
      memo[n][m] = 1 + lcsMemoized(S1, n - 1, S2, m - 1, memo);
      return memo[n][m];
    } else {
      memo[n][m] = Math.max(lcsMemoized(S1, n - 1, S2, m, memo), lcsMemoized(S1, n, S2, m - 1, memo));
    }

    return memo[n][m];
  }

  @SuppressWarnings("unused")
  private int lcsTabulated(String S1, int n, String S2, int m) {
    int[][] dp = new int[n + 1][m + 1];
    // Initaialize
    for (int i = 0; i <= n; i++) {
      for (int j = 0; j <= m; j++) {
        dp[0][j] = 0;
        dp[i][0] = 0;
      }
    }

    // fill the table
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          dp[i][j] = 1 + dp[i - 1][j - 1];
        } else {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
        }
      }
    }

    return dp[n][m];
  }
}
