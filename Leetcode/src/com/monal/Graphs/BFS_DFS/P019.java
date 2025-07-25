package com.monal.Graphs.BFS_DFS;

import java.util.*;

/*
 You are given an n x n binary matrix grid where 1 represents land and 0 represents water.
An island is a 4-directionally connected group of 1's not connected to any other 1's. There are exactly two islands in grid.
You may change 0's to 1's to connect the two islands to form one island.
Return the smallest number of 0's you must flip to connect the two islands.
Example 1:
  Input: grid = [[0,1],[1,0]]
  Output: 1
Example 2:
  Input: grid = [[0,1,0],[0,0,0],[0,0,1]]
  Output: 2
Example 3:
  Input: grid = [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
  Output: 1
*/
public class P019 {
  public int shortestBridge(int[][] grid) {
    int n = grid.length, m = grid[0].length;
    boolean[][] visited = new boolean[n][m];
    Queue<int[]> queue = new LinkedList<>();
    // start a multiple bfs from the edge of first island
    boolean foundFirstIsland = false;
    for (int i = 0; i < n; i++) {
      if (foundFirstIsland) {
        break; // If we already found the first island, we can stop searching
      }
      for (int j = 0; j < m; j++) {
        if (grid[i][j] == 1 && !visited[i][j]) {
          if (foundFirstIsland) {
            // If we already found the first island, we can stop searching
            break;
          }
          // Start BFS from the first island
          foundFirstIsland = true;
          dfs(grid, visited, i, j, queue); // to mark the first island and add its edge to the queue
        }
      }
    }

    // Now perform BFS from all edges of first island
    int steps = 0;
    int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        int[] cell = queue.poll();
        int x = cell[0], y = cell[1];

        for (int[] dir : directions) {
          int newX = x + dir[0], newY = y + dir[1];
          if (newX >= 0 && newX < n && newY >= 0 && newY < m) {
            if (grid[newX][newY] == 1 && !visited[newX][newY]) {
              // If we reach the second island, return the steps
              return steps;
            } else if (grid[newX][newY] == 0 && !visited[newX][newY]) {
              // If it's water, mark it visited and add to queue
              visited[newX][newY] = true;
              queue.offer(new int[] { newX, newY });
            }
          }
        }
      }
      steps++;
    }
    return -1;
  }

  private void dfs(int[][] grid, boolean[][] visited, int x, int y, Queue<int[]> queue) {
    int n = grid.length, m = grid[0].length;
    if (x < 0 || x >= n || y < 0 || y >= m || visited[x][y] || grid[x][y] == 0)
      return;
    visited[x][y] = true;
    // Check if it's an edge cell of the island
    boolean isEdge = false;
    int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
    for (int[] dir : directions) {
      int newX = x + dir[0], newY = y + dir[1];
      if (newX < 0 || newX >= n || newY < 0 || newY >= m) {
        return;
      } else if (grid[newX][newY] == 0) {
        isEdge = true; // Mark as edge if adjacent cell is water
      }else if (!visited[newX][newY]) {
        dfs(grid, visited, newX, newY, queue); // Continue DFS to mark the entire island
      }
    }
    if (isEdge) {
      queue.offer(new int[] { x, y }); // Add the edge cell to the queue
    }
  }
}
