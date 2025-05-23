package com.monal.DP.DP_grids;

/**
 * You are given an n x n grid representing a field of cherries, each cell is
 * one of three possible integers.
 * 0 means the cell is empty, so you can pass through,
 * 1 means the cell contains a cherry that you can pick up and pass through, or
 * -1 means the cell contains a thorn that blocks your way.
 * Return the maximum number of cherries you can collect by following the rules
 * below:
 *
 * Starting at the position (0, 0) and reaching (n - 1, n - 1) by moving right
 * or down through valid path cells (cells with value 0 or 1).
 * After reaching (n - 1, n - 1), returning to (0, 0) by moving left or up
 * through valid path cells.
 * When passing through a path cell containing a cherry, you pick it up, and the
 * cell becomes an empty cell 0.
 * If there is no valid path between (0, 0) and (n - 1, n - 1), then no cherries
 * can be collected.
 *
 * Input: grid = [[0,1,-1],[1,0,-1],[1,1,1]]
 * Output: 5
 * Explanation: The player started at (0, 0) and went down, down, right right to
 * reach (2, 2).
 * 4 cherries were picked up during this single trip, and the matrix becomes
 * [[0,1,-1],[0,0,-1],[0,0,0]].
 * Then, the player went left, up, up, left to return home, picking up one more
 * cherry.
 * The total number of cherries picked up is 5, and this is the maximum
 * possible.
 *
 * Example 2:
 *
 * Input: grid = [[1,1,-1],[1,-1,1],[-1,1,1]]
 * Output: 0
 * Explaination : No Valid path to reach bottom right;
 */

public class P013 {
  // Two people starting at (0, 0), moving to (n-1, n-1) at the same time,
  // collecting cherries. Each step, both move either down or right,
  // so total steps t = r1 + c1 = r2 + c2. If they land on the same cell,
  // count the cherry only once. We use a 3D DP: dp[r1][c1][c2]
  // (we derive r2 = r1 + c1 - c2).

  public class Solution {
    public int cherryPickup(int[][] grid) {
      int n = grid.length;
      Integer[][][] dp = new Integer[n][n][n];
      int result = Math.max(0, dfs(grid, 0, 0, 0, dp));
      return result;
    }

    private int dfs(int[][] grid, int r1, int c1, int c2, Integer[][][] dp) {
      int r2 = r1 + c1 - c2; // since r1 + c1 == r2 + c2 always as the grid is square
      int n = grid.length;
      // Bounds or thorn check
      if (r1 >= n || c1 >= n || r2 >= n || c2 >= n ||
          grid[r1][c1] == -1 || grid[r2][c2] == -1) {
        return Integer.MIN_VALUE;
      }
      // Base case: both reach bottom-right
      if (r1 == n - 1 && c1 == n - 1) {
        return grid[r1][c1];
      }

      if (dp[r1][c1][c2] != null) {
        return dp[r1][c1][c2];
      }

      int cherries = grid[r1][c1];
      if (r1 != r2 || c1 != c2) {
        cherries += grid[r2][c2]; // avoid double count
      }

      // Try all movement combinations (RR, RD, DR, DD)
      int maxNext = Math.max(
          Math.max(dfs(grid, r1 + 1, c1, c2, dp), dfs(grid, r1, c1 + 1, c2 + 1, dp)),
          Math.max(dfs(grid, r1 + 1, c1, c2 + 1, dp), dfs(grid, r1, c1 + 1, c2, dp)));

      cherries += maxNext;
      dp[r1][c1][c2] = cherries;
      return cherries;
    }
  }

  /*
   * public class Solution {
   * public int cherryPickup(int[][] grid) {
   * int n = grid.length;
   *
   * // First DP from (0,0) to (n-1,n-1)
   * int[][] dp = new int[n][n];
   * for (int[] row : dp)
   * Arrays.fill(row, Integer.MIN_VALUE);
   * dp[0][0] = grid[0][0] == -1 ? Integer.MIN_VALUE : grid[0][0];
   *
   * // Fill the dp table
   * for (int i = 0; i < n; i++) {
   * for (int j = 0; j < n; j++) {
   * if (grid[i][j] == -1)
   * continue;
   *
   * if (i > 0 && dp[i - 1][j] != Integer.MIN_VALUE)
   * dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] + grid[i][j]);
   *
   * if (j > 0 && dp[i][j - 1] != Integer.MIN_VALUE)
   * dp[i][j] = Math.max(dp[i][j], dp[i][j - 1] + grid[i][j]);
   * }
   * }
   *
   * if (dp[n - 1][n - 1] == Integer.MIN_VALUE)
   * return 0; // No path to reach end
   *
   * // Step 2: Backtrack path and mark cherries picked as 0
   * int i = n - 1,
   * j = n - 1;
   *
   * while (i != 0 || j != 0) {
   * grid[i][j] = 0; // Mark cherry picked
   *
   * if (i > 0 && j > 0) {
   * if (dp[i - 1][j] > dp[i][j - 1]) {
   * i--;
   * } else {
   * j--;
   * }
   * } else if (i > 0) {
   * i--;
   * } else {
   * j--;
   * }
   * }
   * grid[0][0] = 0; // Mark start cell too
   *
   * // Step 3: Run DP again on updated grid
   * for (int[] row : dp)
   * Arrays.fill(row, Integer.MIN_VALUE);
   * dp[0][0] = grid[0][0] == -1 ? Integer.MIN_VALUE : grid[0][0];
   *
   * for (i = 0; i < n; i++) {
   * for (j = 0; j < n; j++) {
   * if (grid[i][j] == -1)
   * continue;
   *
   * if (i > 0 && dp[i - 1][j] != Integer.MIN_VALUE)
   * dp[i][j] = Math.max(dp[i][j], dp[i - 1][j] + grid[i][j]);
   *
   * if (j > 0 && dp[i][j - 1] != Integer.MIN_VALUE)
   * dp[i][j] = Math.max(dp[i][j], dp[i][j - 1] + grid[i][j]);
   * }
   * }
   *
   * if (dp[n - 1][n - 1] == Integer.MIN_VALUE)
   * return 0; // No return path
   *
   * int firstTrip = dp[n - 1][n - 1];
   * int secondTrip = dp[n - 1][n - 1]; // both same here since DP array reused
   *
   * return firstTrip + secondTrip;
   * }
   * }
   */

}
