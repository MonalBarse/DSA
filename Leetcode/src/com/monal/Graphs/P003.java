package com.monal.Graphs;

import java.util.*;

public class P003 {
  public class RottingOranges {
    // CASE 1: Single Rotten Orange BFS with Visited Matrix
    public int orangesRottingSingle(int[][] grid) {
      int m = grid.length, n = grid[0].length;
      Queue<int[]> queue = new LinkedList<>();
      boolean[][] visited = new boolean[m][n];
      int freshCount = 0;

      // Find the single rotten orange and count fresh oranges
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (grid[i][j] == 2) {
            queue.offer(new int[] { i, j });
            visited[i][j] = true; // Mark initial rotten orange as visited
          } else if (grid[i][j] == 1) {
            freshCount++;
          }
        }
      }

      if (freshCount == 0)
        return 0; // No fresh oranges

      int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
      int minutes = 0;

      while (!queue.isEmpty()) {
        int size = queue.size();
        boolean rottenThisMinute = false;

        // Process all oranges that are rotten at current minute
        for (int i = 0; i < size; i++) {
          int[] curr = queue.poll();

          // Check all 4 directions
          for (int[] dir : directions) {
            int newRow = curr[0] + dir[0];
            int newCol = curr[1] + dir[1];

            // Check bounds, not visited, and if it's a fresh orange
            if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n
                && !visited[newRow][newCol] && grid[newRow][newCol] == 1) {

              visited[newRow][newCol] = true; // Mark as visited
              freshCount--;
              queue.offer(new int[] { newRow, newCol });
              rottenThisMinute = true;
            }
          }
        }

        if (rottenThisMinute)
          minutes++;
      }

      return freshCount == 0 ? minutes : -1;
    }

    // CASE 2: Multiple Rotten Oranges with Visited Matrix
    public int orangesRottingMultiple(int[][] grid) {
      int m = grid.length, n = grid[0].length;
      Queue<int[]> queue = new LinkedList<>();
      boolean[][] visited = new boolean[m][n];
      int freshCount = 0;

      // KEY INSIGHT: Add ALL initially rotten oranges to queue at once!
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (grid[i][j] == 2) {
            queue.offer(new int[] { i, j }); // Add all rotten oranges
            visited[i][j] = true; // Mark all initial rotten oranges as visited
          } else if (grid[i][j] == 1) {
            freshCount++;
          }
        }
      }

      if (freshCount == 0)
        return 0;

      int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
      int minutes = 0;

      // BFS - but now starting from multiple sources simultaneously!
      while (!queue.isEmpty()) {
        int size = queue.size();
        boolean rottenThisMinute = false;

        // Process all oranges rotten at current time level
        for (int i = 0; i < size; i++) {
          int[] curr = queue.poll();

          for (int[] dir : directions) {
            int newRow = curr[0] + dir[0];
            int newCol = curr[1] + dir[1];

            // Check bounds, not visited, and if it's a fresh orange
            if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n
                && !visited[newRow][newCol] && grid[newRow][newCol] == 1) {

              visited[newRow][newCol] = true; // Mark as visited
              freshCount--;
              queue.offer(new int[] { newRow, newCol });
              rottenThisMinute = true;
            }
          }
        }

        if (rottenThisMinute)
          minutes++;
      }

      return freshCount == 0 ? minutes : -1;
    }

    // BONUS: Alternative approach - Using visited matrix to store time
    public int orangesRottingWithTimeMatrix(int[][] grid) {
      int m = grid.length, n = grid[0].length;
      Queue<int[]> queue = new LinkedList<>();
      int[][] timeMatrix = new int[m][n]; // Store the time when each cell gets rotten
      int freshCount = 0;

      // Initialize: -1 for empty cells, 0 for initially rotten, -2 for fresh (to be
      // updated)
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (grid[i][j] == 0) {
            timeMatrix[i][j] = -1; // Empty cell
          } else if (grid[i][j] == 2) {
            queue.offer(new int[] { i, j });
            timeMatrix[i][j] = 0; // Initially rotten at time 0
          } else { // grid[i][j] == 1
            timeMatrix[i][j] = -2; // Fresh orange, not yet rotten
            freshCount++;
          }
        }
      }

      if (freshCount == 0)
        return 0;

      int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
      int maxTime = 0;

      while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int currentTime = timeMatrix[curr[0]][curr[1]];

        for (int[] dir : directions) {
          int newRow = curr[0] + dir[0];
          int newCol = curr[1] + dir[1];

          // Check bounds and if it's a fresh orange (timeMatrix == -2)
          if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n
              && timeMatrix[newRow][newCol] == -2) {

            timeMatrix[newRow][newCol] = currentTime + 1;
            maxTime = Math.max(maxTime, currentTime + 1);
            freshCount--;
            queue.offer(new int[] { newRow, newCol });
          }
        }
      }

      return freshCount == 0 ? maxTime : -1;
    }
  }

  // Test the solution
  public static void main(String[] args) {

    P003 solution = new P003();
    RottingOranges rottingOranges = solution.new RottingOranges();

    // Test Case 1: Single Rotten Orange
    int[][] grid1 = {
        { 2, 1, 0 },
        { 1, 1, 0 },
        { 0, 0, 0 }
    };

    int result1 = rottingOranges.orangesRottingSingle(grid1);
    System.out.println("Single Rotten Orange Result: " + result1); // Expected: 4

    // Test Case 2: Multiple Rotten Oranges
    int[][] grid2 = {
        { 2, 1, 0 },
        { 1, 1, 2 },
        { 0, 0, 0 }
    };
    int result2 = rottingOranges.orangesRottingMultiple(grid2);
    System.out.println("Multiple Rotten Oranges Result: " + result2); // Expected: 2

    // Test Case 3: Using Time Matrix
    int[][] grid3 = {
        { 2, 1, 0 },
        { 1, 1, 2 },
        { 0, 0, 0 }
    };
    int result3 = rottingOranges.orangesRottingWithTimeMatrix(grid3);
    System.out.println("Time Matrix Result: " + result3); // Expected: 2

  }
}
