package com.monal.DP.DP_grids;

/**
 * Problem - Minimum Path SUM
 * Given a m x n grid filled with non-negative numbers, find a path from top
 * left to bottom right, which minimizes the sum of all numbers along its path.
 * Note: You can only move either down or right at any point in time.
 *
 * Input: grid = [[1,3,1],[1,5,1],[4,2,1]]
 * Output: 7
 * Explanation: Because the path 1 → 3 → 1 → 1 → 1 minimizes the sum.
 */
public class P011 {
  class Solution {
    public int minPathSum(int[][] grid) {
      int n = grid.length, m = grid[0].length;
      int dp[][] = new int[n + 1][m + 1];

      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {

          if (i == 1 && j == 1) {
            // Base case: starting point
            dp[i][j] = grid[i - 1][j - 1];
          } else if (i == 1) {
            // First row: can only come from left
            dp[i][j] = dp[i][j - 1] + grid[i - 1][j - 1];
          } else if (j == 1) {
            // First column: can only come from top
            dp[i][j] = dp[i - 1][j] + grid[i - 1][j - 1];
          } else {
            // Regular case: take min of top or left
            dp[i][j] = grid[i - 1][j - 1] + Math.min(dp[i - 1][j], dp[i][j - 1]);
          }
        }
      }
      return dp[n][m];
    }

    public int minPathSum1(int[][] grid) {
      int n = grid.length, m = grid[0].length;
      int dp[][] = new int[n][m];
      dp[0][0] = grid[0][0];
      // Fill first row
      for (int j = 1; j < m; j++) {
        dp[0][j] = dp[0][j - 1] + grid[0][j];
      }
      // Fill first column
      for (int i = 1; i < n; i++) {
        dp[i][0] = dp[i - 1][0] + grid[i][0];
      }
      // Fill the rest of the dp table
      for (int i = 1; i < n; i++) {
        for (int j = 1; j < m; j++) {
          dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
        }
      }
      return dp[n - 1][m - 1];
    }

    @SuppressWarnings("unused")
    private int minPathSumSpaceOptimized(int[][] grid) {
      int n = grid.length, m = grid[0].length;
      int prev[] = new int[m];
      prev[0] = grid[0][0];

      // Fill first row
      for (int j = 1; j < m; j++) {
        prev[j] = prev[j - 1] + grid[0][j];
      }

      // Fill the rest of the dp table
      for (int i = 0; i < n; i++) {
        int curr[] = new int[m];
        curr[0] = prev[0] + grid[i][0];
        for (int j = 1; j < m; j++) {
          curr[j] = grid[i][j] + Math.min(prev[j], curr[j - 1]);
        }
        prev = curr; // Update prev to the current row

      }

      return prev[m - 1]; // Return the last element of the last row
    }

    @SuppressWarnings("unused")
    private int minPathSumMemo(int[][] grid) {
      int n = grid.length, m = grid[0].length;
      int memo[][] = new int[n][m];
      // Initialize memo array with -1
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          memo[i][j] = -1;
        }
      }
      return minPathSumHelper(grid, n - 1, m - 1, memo);
    }

    private int minPathSumHelper(int[][] grid, int i, int j, int[][] memo) {
      // Base case: if we reach the top left corner
      if (i == 0 && j == 0) {
        return grid[0][0];
      }

      // If out of bounds, return a large value
      if (i < 0 || j < 0) {
        return Integer.MAX_VALUE;
      }
      // If already computed, return the cached result
      if (memo[i][j] != -1) {
        return memo[i][j];
      }

      // Min path of top or left
      int goUP = minPathSumHelper(grid, i - 1, j, memo);
      int goLEFT = minPathSumHelper(grid, i, j - 1, memo);

      // Store the result in memo array
      memo[i][j] = grid[i][j] + Math.min(goUP, goLEFT);
      return memo[i][j];

    }
  }

}
