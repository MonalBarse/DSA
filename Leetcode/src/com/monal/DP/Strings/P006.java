package com.monal.DP.Strings;

/**
 * 🧩 Problem: Longest Common Substring
 * This is different from Longest Common Subsequence!
 *
 * 🚨 Problem Statement:
 * Given two strings s1 and s2, find the length of the longest substring that is
 * common to both strings.
 *
 * - A substring is a contiguous sequence of characters, unlike subsequences
 * which can skip characters.
 * - You just need to return the length of this substring (not the actual string
 * itself).
 *
 * Example
 * s1 = "abcde"
 * s2 = "abfce"
 * Output: 2
 * Explanation: The longest common substring is "ab".
 *
 */
public class P006 {

  public int lcsubstring(String S1, String S2) {
    int n = S1.length(), m = S2.length();
    // return lcsubstringRecr(S1, n, S2, m, 0);
    Integer[][][] memo = new Integer[n + 1][m + 1][Math.max(n, m) + 1]; // Include count as third dimension
    return lcsubstringMemo(S1, n, S2, m, 0, memo);
  }

  private int lcsubstringMemo(String S1, int n, String S2, int m, int count, Integer[][][] memo) {
    if (n == 0 || m == 0)
      return count;

    if (memo[n][m][count] != null)
      return memo[n][m][count];

    int newCount = count;
    if (S1.charAt(n - 1) == S2.charAt(m - 1)) {
      newCount = lcsubstringMemo(S1, n - 1, S2, m - 1, count + 1, memo);
    }

    int skipS1 = lcsubstringMemo(S1, n - 1, S2, m, 0, memo);
    int skipS2 = lcsubstringMemo(S1, n, S2, m - 1, 0, memo);

    memo[n][m][count] = Math.max(newCount, Math.max(skipS1, skipS2));
    return memo[n][m][count];
  }

  @SuppressWarnings("unused")
  private int lcsubstringRecr(String S1, int n, String S2, int m, int count) {
    if (n == 0 || m == 0)
      return count;

    if (S1.charAt(n - 1) == S2.charAt(m - 1)) {
      return lcsubstringRecr(S1, n - 1, S2, m - 1, count + 1);
    }
    return Math.max(count, Math.max(lcsubstringRecr(S1, n - 1, S2, m, 0), lcsubstringRecr(S1, n, S2, m - 1, 0)));
  }

  @SuppressWarnings("unused")
  private int lcsubstringTabulation(String S1, String S2) {
    int n = S1.length(), m = S2.length();
    int[][] dp = new int[n + 1][m + 1];
    int maxLength = 0;

    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= m; j++) {
        if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
          dp[i][j] = dp[i - 1][j - 1] + 1;
          maxLength = Math.max(maxLength, dp[i][j]);
        }
      }
    }
    return maxLength;
  }
}
