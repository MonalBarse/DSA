package com.monal.Graphs.BFS_DFS;
/*
You are given an m x n binary matrix grid. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical.) You may assume all four edges of the grid are surrounded by water.
The area of an island is the number of cells with a value 1 in the island.
Return the maximum area of an island in grid. If there is no island, return 0.
*/

/*
 * This is a classic problem of finding the largest connected component in a grid.
 * Approach:
 * 1. Use DFS or BFS to traverse the grid.
 * 2. For each unvisited cell with value 1, start a DFS/BFS to explore the entire island.
 * 3. Count the number of cells in the island during the traversal.
 * 4. Keep track of the maximum area found during the traversal.
 * 5. Return the maximum area after traversing the entire grid.
 */

public class P017 {
  public class MaxAreaOfIsland {

    public int maxAreaOfIsland(int[][] grid) {
      int n = grid.length, m = grid[0].length;
      boolean[][] visited = new boolean[n][m];
      int maxArea = 0;

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          if (grid[i][j] == 1 && !visited[i][j]) {
            int area = dfs(grid, visited, i, j);
            maxArea = Math.max(maxArea, area);
          }
        }
      }
      return maxArea;
    }

    private int dfs(int[][] grid, boolean[][] visited, int x, int y) {
      int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
      int n = grid.length, m = grid[0].length;
      if (x < 0 || x >= n || y < 0 || y >= m || visited[x][y] || grid[x][y] == 0)
        return 0;
      visited[x][y] = true;
      int area = 1; // Count current cell

      for (int[] dir : directions) {
        int newX = x + dir[0], newY = y + dir[1];
        area += dfs(grid, visited, newX, newY);
      }
      return area;
    }
  }

  /*
   * Given an m x n 2D binary grid grid which represents a map of '1's (land) and
   * '0's (water), return the number of islands.
   *
   * An island is surrounded by water and is formed by connecting adjacent lands
   * horizontally or vertically. You may assume all four edges of the grid are all
   * surrounded by water.
   *
   */
  public class NumberOfIslands {
    public int numIslands(char[][] grid) {
       if (grid == null || grid.length == 0 || grid[0].length == 0)
         return 0;
      int n = grid.length, m = grid[0].length;
      boolean[][] visited = new boolean[n][m];
      int islandCount = 0;

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
          if (grid[i][j] == '1' && !visited[i][j]) {
            dfs(grid, visited, i, j);
            islandCount++;
          }
        }
      }

      return islandCount;
    }

    private void dfs(char[][] grid, boolean[][] visited, int x, int y) {
      int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
      int n = grid.length, m = grid[0].length;
      if (x < 0 || x >= n || y < 0 || y >= m || visited[x][y] || grid[x][y] == '0')
        return;
      visited[x][y] = true;
      for (int[] dir : directions) {
        int newX = x + dir[0], newY = y + dir[1];
        dfs(grid, visited, newX, newY);
      }
    }

  }
}
