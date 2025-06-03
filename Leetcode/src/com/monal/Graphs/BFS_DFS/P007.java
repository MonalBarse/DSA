package com.monal.Graphs.BFS_DFS;

// Leetcode : https://leetcode.com/problems/number-of-enclaves/
/*
  You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.
  A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking off the boundary of the grid.
  Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any number of moves.

  Example 1:
    Input: grid = [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
    Output: 3
    Explanation: There are three 1s that are enclosed by 0s, and one 1 that is not enclosed because its on the boundary.
  Example 2:
    Input: grid = [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
    Output: 0
    Explanation: All 1s are either on the boundary or can reach the boundary.
*/

public class P007 {
  class Solution {
    public int numEnclaves(int[][] grid) {
      // if (grid == null || grid.length == 0) return 0;
      int rows = grid.length, cols = grid[0].length;

      // Mark border-connected 1s
      for (int col = 0; col < cols; col++) {
        if (grid[0][col] == 1)
          dfs(grid, 0, col);
        if (grid[rows - 1][col] == 1)
          dfs(grid, rows - 1, col);
      }

      for (int row = 0; row < rows; row++) {
        if (grid[row][0] == 1)
          dfs(grid, row, 0);
        if (grid[row][cols - 1] == 1)
          dfs(grid, row, cols - 1);
      }

      int count = 0;
      for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
          if (grid[i][j] == 1) {
            count++;
          }
        }
      }
      return count;
    }

    private void dfs(int[][] grid, int row, int col) {
      if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length ||
          grid[row][col] != 1)
        return;

      grid[row][col] = 0;

      dfs(grid, row + 1, col);
      dfs(grid, row - 1, col);
      dfs(grid, row, col + 1);
      dfs(grid, row, col - 1);

    }
  }

  public static void main(String[] args) {
    P007 solution = new P007();
    Solution s = solution.new Solution();

    int[][] grid1 = {
        {0, 0, 0, 0},
        {1, 0, 1, 0},
        {0, 1, 1, 0},
        {0, 0, 0, 0}
    };
    System.out.println(s.numEnclaves(grid1)); // Output: 3

    int[][] grid2 = {
        {0, 1, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 1, 0},
        {0, 0, 0, 0}
    };
    System.out.println(s.numEnclaves(grid2)); // Output: 0
  }
}
