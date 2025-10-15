package com.monal.DP.DP_Strings;

public class P009 {
  class Solution {
    String[] words;
    String target;
    int numWords;
    int m;
    int n;
    Integer[][] memo; // memo[i][j] = number of ways to form target[0..i-1] using columns [0..j-1]
    int[][] charCount; // charCount[i][c] = count of character c at column i across all words
    private static final int MOD = (int) 1e9 + 7;

    public int numWays(String[] words, String target) {
      this.words = words;
      this.target = target;
      this.numWords = words.length;
      this.m = words[0].length();
      this.n = target.length();
      this.memo = new Integer[n + 1][m + 1];

      // Precompute character counts for each column
      // This optimization avoids counting characters repeatedly in recursion
      this.charCount = new int[m][26];
      for (int col = 0; col < m; col++)
        for (int row = 0; row < numWords; row++)
          charCount[col][words[row].charAt(col) - 'a']++;

      return dp(0, 0);
    }

    private int dp(int targetIdx, int wordCol) {
      //  successfully formed entire target string
      if (targetIdx == n) return 1;
      // ran out of columns but haven't formed complete target
      if (wordCol == m) return 0;
      if (memo[targetIdx][wordCol] != null) return memo[targetIdx][wordCol];

      long result = 0;
      // Choice 1 -> Skip current column entirely (dnt use any character from this col)
      result = dp(targetIdx, wordCol + 1);

      // Choice 2 _> Use current column to match target[targetIdx]
      char neededChar = target.charAt(targetIdx);
      int charCountAtCol = charCount[wordCol][neededChar - 'a'];

      if (charCountAtCol > 0) {
        // We have charCountAtCol ways to pick the needed character from this column
        // For each such choice, we need to form target[targetIdx+1..] using columns
        // [wordCol+1..]
        long waysUsingThisCol = (1L * charCountAtCol * dp(targetIdx + 1, wordCol + 1)) % MOD;
        result = (result + waysUsingThisCol) % MOD;
      }

      return memo[targetIdx][wordCol] = (int) result;
    }
  }
}
