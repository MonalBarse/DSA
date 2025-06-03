package com.monal.Graphs.BFS_DFS;

import java.util.ArrayDeque;
import java.util.Queue;

/*
Given an m x n binary matrix mat, return the distance of the nearest 0 for each cell.
The distance between two cells sharing a common edge is 1.

Example 1:
  Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
  Output: [[0,0,0],[0,1,0],[0,0,0]]
Example 2:
  Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
  Output: [[0,0,0],[0,1,0],[1,2,1]]
*/
public class P005 {
  class Solution1 {
    public int[][] updateMatrixLevelBFS(int[][] mat) {
      int n = mat.length, m = mat[0].length;

      boolean[][] visited = new boolean[n][m];
      int[][] res = new int[n][m];
      Queue<int[]> q = new ArrayDeque<>();

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          if (mat[i][j] == 0) {
            q.offer(new int[] { i, j }); // Only {row, col}
            visited[i][j] = true;
            res[i][j] = 0;
          }
        }
      }

      int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
      int dist = 0;

      while (!q.isEmpty()) {
        int size = q.size(); // Process all nodes at current distance level

        for (int i = 0; i < size; i++) {
          int[] currNode = q.poll();
          int r = currNode[0], c = currNode[1];

          // Check all 4 directions
          for (int[] dir : directions) {
            int newRow = r + dir[0];
            int newCol = c + dir[1];

            if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < m && !visited[newRow][newCol]) {
              visited[newRow][newCol] = true;
              res[newRow][newCol] = dist + 1;
              q.offer(new int[] { newRow, newCol });
            }
          }
        }
        dist++; // Increment distance after processing current level
      }

      return res;
    }

  }

  /*
   * ALGORITHM EXPLANATION: Two-Pass Dynamic Programming
   *
   * CORE IDEA: The shortest distance to any cell must come from one of 4
   * directions (up, down, left, right).
   * Instead of exploring all directions at once (like BFS), we can be smart and
   * do this in two passes:
   *
   * PASS 1 (Top-Left → Bottom-Right):
   * - For each cell, only consider distances coming from TOP and LEFT neighbors
   * - This finds the shortest path that approaches each cell from the
   * "top-left region"
   *
   * PASS 2 (Bottom-Right → Top-Left):
   * - For each cell, only consider distances coming from RIGHT and BOTTOM
   * neighbors
   * - This finds the shortest path that approaches each cell from the
   * "bottom-right region"
   *
   * WHY THIS WORKS:
   * - Any shortest path must come from one of the 4 directions
   * - Pass 1 captures all optimal paths from top-left side
   * - Pass 2 captures all optimal paths from bottom-right side
   * - Taking the minimum of both passes gives us the globally optimal answer
   *
   * EXAMPLE WALKTHROUGH:
   * Initial: Pass 1: Pass 2 (Final):
   * [0, 0, 0] [0, 0, 0] [0, 0, 0]
   * [0, 1, 0] -> [0, 1, 0] -> [0, 1, 0]
   * [1, 1, 1] [1, 2, 1] [1, 2, 1]
   *
   * TIME: O(m×n) - two passes through matrix
   * SPACE: O(1) - modify matrix in-place, only use a few variables
   */

  class Solution2 {
    public int[][] updateMatrix(int[][] matrix) {
      int lastRow = matrix.length - 1;
      int lastCol = matrix[0].length - 1;
      int maxPossibleDistance = lastRow + lastCol + 2; // Max distance from corner to corner

      // PASS 1: Scan from TOP-LEFT to BOTTOM-RIGHT
      // Only consider paths coming from TOP and LEFT directions

      int[] currentRow = matrix[0];
      int[] previousRow;

      // Process first row: can only get distance from LEFT neighbor
      if (currentRow[0] == 1) {
        currentRow[0] = maxPossibleDistance; // Initialize 1s to "infinity"
      }

      for (int col = 1; col <= lastCol; col++) {
        if (currentRow[col] == 1) {
          currentRow[col] = currentRow[col - 1] + 1; // Distance from left + 1
        }
      }

      // Process remaining rows: can get distance from TOP or LEFT
      for (int row = 1; row <= lastRow; row++) {
        previousRow = currentRow;
        currentRow = matrix[row];

        // First column: can only come from TOP
        if (currentRow[0] == 1) {
          currentRow[0] = previousRow[0] + 1; // Distance from top + 1
        }

        // Other columns: take minimum of LEFT and TOP neighbors
        for (int col = 1; col <= lastCol; col++) {
          if (currentRow[col] == 1) {
            int distanceFromLeft = currentRow[col - 1] + 1;
            int distanceFromTop = previousRow[col] + 1;
            currentRow[col] = Math.min(distanceFromLeft, distanceFromTop);
          }
        }
      }

      // PASS 2: Scan from BOTTOM-RIGHT to TOP-LEFT
      // Now consider paths coming from RIGHT and BOTTOM directions

      // Process last row: can only get distance from RIGHT neighbor
      currentRow = matrix[lastRow];
      for (int col = lastCol - 1; col >= 0; col--) {
        if (currentRow[col] > 1) { // Skip 0s and don't make distances worse
          int distanceFromRight = currentRow[col + 1] + 1;
          currentRow[col] = Math.min(currentRow[col], distanceFromRight);
        }
      }

      // Process remaining rows (bottom to top): consider RIGHT and BOTTOM
      for (int row = lastRow - 1; row >= 0; row--) {
        previousRow = currentRow; // previousRow is now the row BELOW current
        currentRow = matrix[row];

        // Last column: can only come from BOTTOM
        if (currentRow[lastCol] > 1) {
          int distanceFromBottom = previousRow[lastCol] + 1;
          currentRow[lastCol] = Math.min(currentRow[lastCol], distanceFromBottom);
        }

        // Other columns: consider current value, RIGHT neighbor, and BOTTOM neighbor
        for (int col = lastCol - 1; col >= 0; col--) {
          if (currentRow[col] > 1) {
            int currentDistance = currentRow[col];
            int distanceFromRight = currentRow[col + 1] + 1;
            int distanceFromBottom = previousRow[col] + 1;

            currentRow[col] = Math.min(currentDistance,
                Math.min(distanceFromRight, distanceFromBottom));
          }
        }
      }

      return matrix;
    }
  }

  /*
   * KEY INSIGHTS:
   *
   * 1. **Why Two Passes?**
   * - Single pass can't capture all shortest paths
   * - We need to consider all 4 directions, but doing it smartly
   * - Two passes ensure we don't miss any shorter path from any direction
   *
   * 2. **Why These Specific Directions?**
   * - Pass 1 (↘): TOP + LEFT covers upper-left quadrant influence
   * - Pass 2 (↖): RIGHT + BOTTOM covers lower-right quadrant influence
   * - Together they cover all possible shortest path directions
   *
   * 3. **Initialization Strategy**:
   * - 0s stay 0 (they're the sources)
   * - 1s start as "infinity" (maxPossibleDistance)
   * - Gradually updated to correct distances through the passes
   *
   * 4. **Space Optimization**:
   * - Reuses matrix for storing results (in-place modification)
   * - Only needs a few pointer variables, no extra arrays or queues
   *
   * 5. **Comparison with BFS**:
   * - BFS: O(mn) time, O(mn) space (queue + visited array)
   * - DP: O(mn) time, O(1) space (much more memory efficient!)
   */

  public static void main(String[] args) {
    P005 solution = new P005();
    Solution1 s1 = solution.new Solution1();
    Solution2 s2 = solution.new Solution2();

    int[][] mat = {
        { 0, 0, 0 },
        { 0, 1, 0 },
        { 0, 0, 0 }
    };

    int[][] result1 = s1.updateMatrixLevelBFS(mat);
    for (int[] row : result1) {
      for (int val : row) {
        System.out.print(val + " ");
      }
      System.out.println();
    }

    int[][] mat2 = {
        { 0, 0, 0 },
        { 0, 1, 0 },
        { 1, 1, 1 }
    };

    int[][] result2 = s2.updateMatrix(mat2);
    for (int[] row : result2) {
      for (int val : row) {
        System.out.print(val + " ");
      }
      System.out.println();
    }
  }
}
