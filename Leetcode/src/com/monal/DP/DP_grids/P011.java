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
  }

}
