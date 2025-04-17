package com.monal.DP.DP_Strings;

/**
 * 🧩 Problem: Longest Palindromic Subsequence (LPS)
 * Given a string s, find the length of the longest subsequence that is a
 * palindrome.
 * A subsequence is a sequence that can be derived from s by deleting some
 * characters without changing the order.
 * The result should be the length of the longest such subsequence.
 *
 * 🔍 Example:
 * Input: "bbabcbcab"
 * Output: 7
 * Explanation: One LPS is "babcbab" or "bacbcab" — both have length 7.
 *
 */
public class P005 {
  public int lps(String S) {
    int n = S.length();
    return lpsRecur(S, 0, n - 1);
  }

  private int lpsRecur(String S, int start, int end) {
    if (start > end)
      return 0;

    if (start == end)
      return 1;

    if (S.charAt(start) == S.charAt(end)) {
      return 2 + lpsRecur(S, start + 1, end - 1);
    }
    return Math.max(lpsRecur(S, start + 1, end), lpsRecur(S, start, end - 1));
  }

  @SuppressWarnings("unused")
  private int lpsTabulation(String S) {
    int n = S.length();
    int[][] dp = new int[n][n];

    // Base case: every single character is a palindrome
    for (int i = 0; i < n; i++) {
      dp[i][i] = 1;
    }

    // Fill the table
    // i starts from n-1 to 0 and j moves from i+1 to n-1
    for (int i = n - 1; i >= 0; i--) {
      for (int j = i + 1; j < n; j++) {
        if (S.charAt(i) == S.charAt(j)) {
          dp[i][j] = 2 + dp[i + 1][j - 1];
        } else {
          dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
        }
      }
    }

    return dp[0][n - 1];
  }
}
