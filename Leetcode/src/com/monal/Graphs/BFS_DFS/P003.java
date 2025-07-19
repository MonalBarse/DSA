package com.monal.Graphs.BFS_DFS;

import java.util.*;

/*
 * Given a `crate` of Oranges few oranges are rotten to begin with, they are marked with crate[i][j] = 2
 * crate[i][j] = 1 then the oranges are fresh, the every unit of time passes makes the fresh oranges if surrounded by
 * rotten ones rotten (4 direction). Return how many unit time is required for the crate to have all rotten oranges
 * If it is not possible for the oranges to be all rotten return -1
 */
public class P003 {
  public class RottingOranges {

    public int orangesRottingMultiple(int[][] crate) {
      int m = crate.length, n = crate[0].length;
      Queue<int[]> queue = new LinkedList<>(); // {coordinate x , coordinate y} of rotten orange
      boolean[][] visited = new boolean[m][n];
      int freshCount = 0;

      // KEY INSIGHT: Add ALL initially rotten oranges to queue at once!
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          if (crate[i][j] == 2) {
            queue.offer(new int[] { i, j }); // Add all rotten oranges
            visited[i][j] = true; // Mark all initial rotten oranges as visited
          } else if (crate[i][j] == 1)
            freshCount++;
        }
      }

      if (freshCount == 0) return 0;

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
                && !visited[newRow][newCol] && crate[newRow][newCol] == 1) {
              visited[newRow][newCol] = true; // Mark as visited
              freshCount--;
              queue.offer(new int[] { newRow, newCol }); // add to new rotten ornage
              rottenThisMinute = true;
            }
          }
        }
        if (rottenThisMinute) minutes++;
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
          switch (grid[i][j]) {
            case 0:
              timeMatrix[i][j] = -1; // Empty cell
              break;
            case 1:
              timeMatrix[i][j] = -2; // Fresh orange, not yet rotten
              freshCount++;
              break;
            case 2:
              queue.offer(new int[] { i, j });
              timeMatrix[i][j] = 0; // Initially rotten at time 0
              break;
            default:
              // Optionally handle unexpected values
              break;
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

    int[][] grid1 = {
        { 2, 1, 0 },
        { 1, 1, 0 },
        { 0, 0, 0 }
    };

    int result1 = rottingOranges.orangesRottingMultiple(grid1);
    System.out.println("Test Case 1 Result: " + result1);

    // Expected Output: 4
    // Explanation: The rotten orange at (0, 0) will rot the fresh oranges in 4
    // minutes.

    int[][] grid2 = {
        { 2, 1, 0, 1, 1 },
        { 1, 1, 0, 1, 1 },
        { 0, 0, 0, 0, 0 },
        { 2, 0, 0, 1, 0 },
        { 1, 2, 1, 2, 0 }
    };

    int result2 = rottingOranges.orangesRottingWithTimeMatrix(grid2);
    System.out.println("Test Case 2 Result: " + result2);

  }
}
