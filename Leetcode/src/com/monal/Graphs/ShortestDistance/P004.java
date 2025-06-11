package com.monal.Graphs.ShortestDistance;

import java.util.*;

/*
You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, where heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0), and you hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). You can move up, down, left, or right, and you wish to find a route that requires the minimum effort.
A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.
Return the minimum effort required to travel from the top-left cell to the bottom-right cell.

Example 1:
  Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
  Output: 2
  Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
  This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.
Example 2:
  Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
  Output: 1
  Explanation: The route of [1,2,3,4,5] has a maximum absolute difference of 1 in consecutive cells, which is better than route [1,3,5,3,5].
Example 3:
  Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
  Output: 0
  Explanation: This route does not require any effor
*/
public class P004 {

  class Solution {
    public int minimumEffortPath(int[][] heights) {
      int n = heights.length, m = heights[0].length;

      // PriorityQueue: [maxEffortSoFar, row, col]
      // We want to explore paths with minimum effort first
      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

      // Track minimum effort to reach each cell
      int[][] minEffort = new int[n][m];
      for (int i = 0; i < n; i++) {
        Arrays.fill(minEffort[i], Integer.MAX_VALUE);
      }

      // Start from (0,0) with 0 effort
      pq.offer(new int[] { 0, 0, 0 });
      minEffort[0][0] = 0;

      int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

      while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int currentEffort = curr[0];
        int x = curr[1], y = curr[2];

        // If we reached destination, return the effort
        if (x == n - 1 && y == m - 1) {
          return currentEffort;
        }

        // Skip if we've already found a better path to this cell
        if (currentEffort > minEffort[x][y]) {
          continue;
        }

        // Explore all 4 directions
        for (int[] dir : directions) {
          int newX = x + dir[0], newY = y + dir[1];

          if (newX >= 0 && newY >= 0 && newX < n && newY < m) {
            // Calculate effort to move to new cell
            int edgeEffort = Math.abs(heights[newX][newY] - heights[x][y]);

            // Maximum effort for this path = max(current path effort, edge effort)
            int newPathEffort = Math.max(currentEffort, edgeEffort);

            // Only proceed if we found a better path to the new cell
            if (newPathEffort < minEffort[newX][newY]) {
              minEffort[newX][newY] = newPathEffort;
              pq.offer(new int[] { newPathEffort, newX, newY });
            }
          }
        }
      }

      return -1; // Should never reach here for valid input
    }
  }

  public static void main(String[] args) {
    P004 p = new P004();
    Solution solution = p.new Solution();

    // Example usage of minimumEffortPath
    int[][] heights = {
        { 1, 2, 2 },
        { 3, 8, 2 },
        { 5, 3, 5 }
    };
    int result = solution.minimumEffortPath(heights);
    System.out.println("Minimum effort required: " + result); // Output: 2

    int[][] heights2 = {
        { 1, 2, 3 },
        { 3, 8, 4 },
        { 5, 3, 5 }
    };
    int result2 = solution.minimumEffortPath(heights2);
    System.out.println("Minimum effort required: " + result2); // Output: 1

    int[][] heights3 = {
        { 1, 2, 1, 1, 1 },
        { 1, 2, 1, 2, 1 },
        { 1, 2, 1, 2, 1 },
        { 1, 2, 1, 2, 1 },
        { 1, 1, 1, 2, 1 }
    };
    int result3 = solution.minimumEffortPath(heights3);
    System.out.println("Minimum effort required: " + result3); // Output: 0

  }
}
