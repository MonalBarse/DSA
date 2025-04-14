package com.monal.DP.DP_grids;

/** DP-10: Unique Paths

📝 Problem Statement:
Given an m x n grid, count how many unique paths exist from the top-left to the bottom-right, moving only right or down.
🔍 General Idea:
Every cell (i, j) is reachable from either the cell above it (i-1, j) or the cell to the left (i, j-1).
So, the total paths to a cell = paths to top cell + paths to left cell.

🧠 Thought Process:
We’re counting "ways" → classic counting DP.
We form a DP table where each cell means "ways to reach this cell".
Base cases: 1 way to reach first row or first column (only one direction possible).
*/

/**
 * DP-11: Unique Paths II (with Obstacles)
 * 📝 Problem Statement:
 * Same as Unique Paths, but now some cells are blocked (marked by 1), and
 * cannot be part of the path.
 *
 * 🔍 General Idea:
 * We do exactly the same as DP-10 but treat obstacles as 0 (no path through
 * them).
 *
 * 🧠 Thought Process:
 * Similar to DP-10, but now "state blocking" is introduced.
 * At each cell, if it’s an obstacle → 0 ways.
 * Else → sum of top and left like before.
 */
public class P010 {
  class SolutionI {
    public int uniquePaths(int m, int n) {
      int dp[][] = new int[m][n];
      // initialize
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (i == 0 || j == 0) {
            dp[i][j] = 1; // only one way to reach first row or first column
          } else {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1]; // sum of top and left cells
          }
        }
      }
      return dp[m - 1][n - 1]; // return the bottom-right cell
    }
  }

  class SolutionII {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
      // Obstacle marked as 1, free cell as 0
      int m = obstacleGrid.length, n = obstacleGrid[0].length;
      int[][] dp = new int[m][n];

      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {

          // ❌ If there's an obstacle in current cell, no way to reach here
          if (obstacleGrid[i][j] == 1) {
            dp[i][j] = 0; // no path possible
          }

          // ✅ Otherwise, we compute based on previous cells
          else if (i == 0 && j == 0) {
            // Start point: only one way to be at the beginning
            dp[i][j] = 1;
          } else if (i == 0) {
            // First row: can only come from the left
            dp[i][j] = dp[i][j - 1];
          } else if (j == 0) {
            // First column: can only come from the top
            dp[i][j] = dp[i - 1][j];
          } else {
            // For all other cells: sum of top and left
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
          }
        }
      }

      // Final cell has the total number of unique paths
      return dp[m - 1][n - 1];
    }
  }

}
