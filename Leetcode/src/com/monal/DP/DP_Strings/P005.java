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

public class P005 {

  // =================================================== //
  // ------ CAUSES MEMORY ISSUES FOR LARGE INPUTS ------ //
  // =================================================== //
  static class MySolution {
    /**
     * ⚠️ NAIVE RECURSIVE APPROACH ⚠️
     *
     * 🔍 How it works:
     * 1. Base cases: If one string is empty, return the other string
     * 2. If last characters match, include it once and recurse
     * 3. If last characters don't match, try both possibilities and choose shorter
     *
     * ⚠️ Time Complexity: O(2^(n+m)) - Exponential!
     * ⚠️ Space Complexity: O(n+m) for call stack - Can cause stack overflow!
     */
    private String scsRecursive(String S1, int n, String S2, int m) {
      // Base cases
      if (n == 0)
        return S2.substring(0, m); // If S1 is empty, return S2
      if (m == 0)
        return S1.substring(0, n); // If S2 is empty, return S1

      // If last characters match, include this character once
      if (S1.charAt(n - 1) == S2.charAt(m - 1)) {
        // Add this character at the end and solve for remaining strings
        return scsRecursive(S1, n - 1, S2, m - 1) + S1.charAt(n - 1);
      }

      // If last characters don't match, try both options:
      // 1. Include last char of S1 and recurse
      String option1 = scsRecursive(S1, n - 1, S2, m) + S1.charAt(n - 1);

      // 2. Include last char of S2 and recurse
      String option2 = scsRecursive(S1, n, S2, m - 1) + S2.charAt(m - 1);

      // Return the shorter option
      return option1.length() < option2.length() ? option1 : option2;
    }

    /**
     * =============================================
     * 🧠 MEMOIZATION APPROACH - Top-down DP
     * =============================================
     *
     * 🔍 How it works:
     * - Similar to recursive, but store already computed results in memo table
     * - Avoids redundant calculations by checking if result already exists
     *
     * ⚡ Time Complexity: O(n*m) - Much better!
     * 🚨 Space Complexity: O(n*m) for memo table + O(n+m) for call stack
     * Still can have issues with very large inputs due to call stack
     */
    @SuppressWarnings("unused")
    private String scsMemoized(String S1, int n, String S2, int m, String[][] memo) {
      // Base cases
      if (n == 0)
        return S2.substring(0, m);
      if (m == 0)
        return S1.substring(0, n);

      // Check if already calculated
      if (memo[n][m] != null)
        return memo[n][m];

      // If last characters match
      if (S1.charAt(n - 1) == S2.charAt(m - 1)) {
        // Store result in memo table
        memo[n][m] = scsMemoized(S1, n - 1, S2, m - 1, memo) + S1.charAt(n - 1);
        return memo[n][m];
      }

      // If last characters don't match, try both options
      String option1 = scsMemoized(S1, n - 1, S2, m, memo) + S1.charAt(n - 1);
      String option2 = scsMemoized(S1, n, S2, m - 1, memo) + S2.charAt(m - 1);

      // Store the better result
      memo[n][m] = option1.length() < option2.length() ? option1 : option2;
      return memo[n][m];
    }

    /**
     * =============================================
     * 🚀 TABULATION APPROACH - Bottom-up DP
     * =============================================
     *
     * 🔍 How it works:
     * - Build table iteratively from smaller to larger subproblems
     * - Avoid recursion entirely by using nested loops
     *
     * ⚡ Time Complexity: O(n*m)
     * 💯 Space Complexity: O(n*m) - No call stack issues!
     * Most efficient approach for large inputs!
     */
    private String scsTabulated(String S1, int n, String S2, int m) {
      // Create DP table to store results
      String[][] dp = new String[n + 1][m + 1];

      // Initialize first row and column
      for (int i = 0; i <= n; i++) {
        dp[i][0] = S1.substring(0, i);
      }
      for (int j = 0; j <= m; j++) {
        dp[0][j] = S2.substring(0, j);
      }

      // Fill the DP table systematically
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
          // If characters match
          if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
            dp[i][j] = dp[i - 1][j - 1] + S1.charAt(i - 1);
          } else {
            // If characters don't match, choose the shorter option
            String option1 = dp[i - 1][j] + S1.charAt(i - 1);
            String option2 = dp[i][j - 1] + S2.charAt(j - 1);
            dp[i][j] = option1.length() < option2.length() ? option1 : option2;
          }
        }
      }

      // Return the final cell containing our answer
      return dp[n][m];
    }

    /**
     * Main method that decides which implementation to use
     *
     * @param S1             First string
     * @param S2             Second string
     * @param forceRecursive If true, forces the use of pure recursive approach
     * @return The shortest common supersequence
     */
    public String shortestCommoString(String S1, String S2, boolean forceRecursive) {
      int n = S1.length(), m = S2.length();

      if (forceRecursive) {
        // 🚨 CAUTION: This will cause stack overflow for large inputs!
        System.out.println("⚠️ Using pure recursive approach - MEMORY INTENSIVE!");
        return scsRecursive(S1, n, S2, m);
      }

      // Choose between memoization and tabulation
      // Uncomment to use memoization approach:
      /*
       * System.out.println("Using memoized approach");
       * String[][] memo = new String[n + 1][m + 1];
       * for (String[] arr : memo) {
       * Arrays.fill(arr, null);
       * }
       * return scsMemoized(S1, n, S2, m, memo);
       */

      // Default to tabulation (most efficient)
      System.out.println("Using tabulated approach (most efficient)");
      return scsTabulated(S1, n, S2, m);
    }

    /**
     * Overloaded method for simpler calls
     */
    public String shortestCommoString(String S1, String S2) {
      return shortestCommoString(S1, S2, false);
    }

  }

  // =================================================== //
  // ------------- OPTIMIZED SOLUTION ------------------ //
  // =================================================== //

  static class Solution {
    /**
     * Main method to get the shortest common supersequence
     * Provides option to use either top-down or bottom-up approach
     */
    public String shortestCommonSupersequence(String str1, String str2) {
      // Choose either top-down or bottom-up approach
      // return topDownSCS(str1, str2); // Memoized approach
      return bottomUpSCS(str1, str2); // Tabulated approach (most efficient)
    }

    /**
     *
     * // =================================================== //
     * 🧠 TOP-DOWN APPROACH (Memoized)
     * // =================================================== //
     *
     * 🔍 How it works:
     * 1. First find the Longest Common Subsequence (LCS) using memoization
     * 2. Then construct SCS by working backwards through the LCS
     *
     * ⚡ Time Complexity: O(n*m)
     * 🚨 Space Complexity: O(n*m) + O(n+m) for call stack
     */

    @SuppressWarnings("unused")
    private String topDownSCS(String str1, String str2) {
      int n = str1.length();
      int m = str2.length();

      // Create memoization table for LCS lengths
      int[][] memo = new int[n + 1][m + 1];
      for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= m; j++) {
          memo[i][j] = -1; // Initialize with -1 to indicate not computed yet
        }
      }

      // Build LCS table using memoization
      lcs(str1, str2, n, m, memo);

      // Reconstruct SCS from LCS table
      StringBuilder result = new StringBuilder();
      int i = n, j = m;

      // Work backwards through the LCS table
      while (i > 0 && j > 0) {
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          // Common character - add once
          result.append(str1.charAt(i - 1));
          i--;
          j--;
        } else if (memo[i - 1][j] > memo[i][j - 1]) {
          // Character from str1
          result.append(str1.charAt(i - 1));
          i--;
        } else {
          // Character from str2
          result.append(str2.charAt(j - 1));
          j--;
        }
      }

      // Add remaining characters from either string
      while (i > 0) {
        result.append(str1.charAt(i - 1));
        i--;
      }
      while (j > 0) {
        result.append(str2.charAt(j - 1));
        j--;
      }

      // Need to reverse as we built it backwards
      return result.reverse().toString();
    }

    /**
     * Helper method to compute LCS using memoization
     *
     * 🔍 How it works:
     * - Recursively find length of LCS with memoization
     * - If characters match, LCS length increases by 1
     * - If not, take max of two options (excluding one char from either string)
     */
    private int lcs(String str1, String str2, int i, int j, int[][] memo) {
      // Base case: if either string is empty
      if (i == 0 || j == 0)
        return 0;

      // Return if already computed
      if (memo[i][j] != -1)
        return memo[i][j];

      // If characters match
      if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
        memo[i][j] = 1 + lcs(str1, str2, i - 1, j - 1, memo);
      } else {
        // Characters don't match - take max of two options
        memo[i][j] = Math.max(lcs(str1, str2, i - 1, j, memo), lcs(str1, str2, i, j - 1, memo));
      }

      return memo[i][j];
    }

    /**
     *
     * // =================================================== //
     * 🚀 BOTTOM-UP APPROACH (Tabulated) - Most efficient!
     * // =================================================== //
     * 🔍 How it works:
     * 1. First find the LCS using tabulation (no recursion)
     * 2. Then construct SCS by working backwards through the LCS table
     *
     * ⚡ Time Complexity: O(n*m)
     * 💯 Space Complexity: O(n*m) - No call stack issues!
     */
    private String bottomUpSCS(String str1, String str2) {
      int n = str1.length();
      int m = str2.length();

      // Create and fill LCS table using iteration
      int[][] dp = new int[n + 1][m + 1];

      // Build LCS table bottom-up
      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
          if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
            // Characters match - LCS increases by 1
            dp[i][j] = 1 + dp[i - 1][j - 1];
          } else {
            // Characters don't match - take max of two options
            dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
          }
        }
      }

      // Reconstruct SCS from LCS table
      StringBuilder result = new StringBuilder();
      int i = n, j = m;

      // Work backwards through the table
      while (i > 0 && j > 0) {
        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
          // Common character - add once
          result.append(str1.charAt(i - 1));
          i--;
          j--;
        } else if (dp[i - 1][j] > dp[i][j - 1]) {
          // Take character from str1
          result.append(str1.charAt(i - 1));
          i--;
        } else {
          // Take character from str2
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

      // Need to reverse as we built it backwards
      return result.reverse().toString();
    }
  }

  public static void main(String[] args) {
    // Test cases for demonstration
    testCase("abac", "cab", "Memory Intensive (Small)"); // Small case for both approaches
    testCase("abc", "def", "Memory Intensive (Small)"); // No common characters
    testCase("abc", "abc", "Memory Intensive (Small)"); // Identical strings

    // Large test case to demonstrate memory issue with recursive approach
    String longStr1 = generateLongString(30); // Length that will cause stack overflow
    String longStr2 = generateLongString(30);
    System.out.println("\n=== Testing with large inputs ===");
    System.out
        .println("String 1 (truncated): " + longStr1.substring(0, 20) + "... (length: " + longStr1.length() + ")");
    System.out
        .println("String 2 (truncated): " + longStr2.substring(0, 20) + "... (length: " + longStr2.length() + ")");

    // Should cause memory issues with naive recursive approach
    testCase(longStr1, longStr2, "Memory Intensive (Large)");

    // More practical test cases for optimized solution
    testCase("banana", "anaphile", "Optimized Solution");
    testCase("AGGTAB", "GXTXAYB", "Optimized Solution");
  }

  /**
   * Helper method to generate a long random string to demonstrate memory issues
   */
  private static String generateLongString(int length) {
    StringBuilder sb = new StringBuilder();
    String chars = "abcdefghijklmnopqrstuvwxyz";
    for (int i = 0; i < length; i++) {
      sb.append(chars.charAt((int) (Math.random() * chars.length())));
    }
    return sb.toString();
  }

  /**
   * Test case runner to show different approaches
   */
  private static void testCase(String s1, String s2, String approach) {
    System.out.println("\n=== Testing: " + s1 + ", " + s2 + " ===");

    if (approach.equals("Memory Intensive (Small)") || approach.equals("Memory Intensive (Large)")) {
      // Only run recursive solution on small inputs or explicitly for demonstration
      MySolution mySolution = new MySolution();
      try {
        System.out.println("Running memory intensive approach...");
        long startTime = System.currentTimeMillis();

        // Force recursive execution by setting a flag to use recursive approach
        String result = mySolution.shortestCommoString(s1, s2, true);

        long endTime = System.currentTimeMillis();
        System.out.println("Result (Recursive/Memory Intensive): " + result);
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
      } catch (StackOverflowError e) {
        System.out.println("❌ STACK OVERFLOW ERROR! The recursive approach ran out of memory!");
        System.out.println("This demonstrates why naive recursive approaches can fail on larger inputs.");
      } catch (Exception e) {
        System.out.println("❌ Exception: " + e.getMessage());
      }
    }

    // Always run the optimized solution
    Solution optimizedSolution = new Solution();
    try {
      System.out.println("Running optimized approach...");
      long startTime = System.currentTimeMillis();
      String result = optimizedSolution.shortestCommonSupersequence(s1, s2);
      long endTime = System.currentTimeMillis();
      System.out.println("Result (Optimized): " + result);
      System.out.println("Time taken: " + (endTime - startTime) + "ms");

      // Verify the result is correct by checking if both strings are subsequences
      boolean isS1Subsequence = isSubsequence(s1, result);
      boolean isS2Subsequence = isSubsequence(s2, result);
      System.out.println("Validation - Contains s1 as subsequence: " + isS1Subsequence);
      System.out.println("Validation - Contains s2 as subsequence: " + isS2Subsequence);
    } catch (Exception e) {
      System.out.println("❌ Exception in optimized solution: " + e.getMessage());
    }
  }

  /**
   * Helper method to check if a string is a subsequence of another
   */
  private static boolean isSubsequence(String sub, String str) {
    int i = 0, j = 0;
    while (i < sub.length() && j < str.length()) {
      if (sub.charAt(i) == str.charAt(j)) {
        i++;
      }
      j++;
    }
    return i == sub.length();
  }

}