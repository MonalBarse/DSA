package com.monal.Graphs.BFS_DFS;

import java.util.*;

/*

There is an m x n rectangular island that borders both the Pacific Ocean and Atlantic Ocean.
The Pacific Ocean touches the island's left and top edges, and the Atlantic Ocean touches
the island's right and bottom edges.

The island is partitioned into a grid of square cells.
You are given an m x n integer matrix heights where heights[r][c] represents the
height above sea level of the cell at coordinate (r, c).

The island receives a lot of rain, and the rain water can flow to neighboring cells
directly north, south, east, and west if the neighboring cell's height is less than
or equal to the current cell's height. Water can flow from any cell adjacent to an ocean into the ocean.

Return a 2D list of grid coordinates result where result[i] = [ri, ci] denotes that
rain water can flow from cell (ri, ci) to both the Pacific and Atlantic oceans.

*/

// Approach:
/*
 * Start with two corners:  top-right and bottom-left which touches both oceans.
 * Use BFS/DFS to traverse the grid from these corners.
 * For each cell, check if it can flow to the ocean.
 * If it can, mark it as reachable.
 * Continue until all reachable cells are marked.
 * Finally, return the list of reachable cells.
*/
public class P014 {
  public List<List<Integer>> pacificAtlantic(int[][] heights) {
    List<List<Integer>> result = new ArrayList<>();
    if (heights == null || heights.length == 0 || heights[0].length == 0)
      return result;

    int n = heights.length, m = heights[0].length;
    boolean[][] canReachPacific = new boolean[n][m];
    boolean[][] canReachAtlantic = new boolean[n][m];

    for (int i = 0; i < n; i++)
      dfs(heights, i, 0, canReachPacific); // Left edge for Pacific
    for (int j = 0; j < m; j++)
      dfs(heights, 0, j, canReachPacific); // Top edge for Pacific
    for (int i = 0; i < n; i++)
      dfs(heights, i, m - 1, canReachAtlantic); // Right edge for Atlantic
    for (int j = 0; j < m; j++)
      dfs(heights, n - 1, j, canReachAtlantic); // Bottom edge for Atlantic

    // Collect cells that can reach both oceans
    for (int i = 0; i < n; i++)
      for (int j = 0; j < m; j++)
        if (canReachPacific[i][j] && canReachAtlantic[i][j])
          result.add(Arrays.asList(i, j));

    return result;
  }

  private void dfs(int[][] heights, int x, int y, boolean[][] canReach) {
    int n = heights.length, m = heights[0].length;
    // Boundary check and visited check
    if (x < 0 || x >= n || y < 0 || y >= m || canReach[x][y])
      return;
    canReach[x][y] = true;
    // Define directions for cleaner code
    int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } }; // Up, Down, Left, Right
    for (int[] dir : directions) {
      int newX = x + dir[0];
      int newY = y + dir[1];
      if (newX >= 0 && newX < n && newY >= 0 && newY < m)
        if (heights[newX][newY] >= heights[x][y])
          dfs(heights, newX, newY, canReach);

    }
  }

  public static void main(String[] args) {
    P014 solution = new P014();
    int[][] heights = {
        { 1, 2, 3 },
        { 8, 9, 4 },
        { 7, 6, 5 }
    };
    List<List<Integer>> result = solution.pacificAtlantic(heights);
    for (List<Integer> cell : result) {
      System.out.println(cell);
    }

    // Expected output: [[0, 0], [0, 1], [0, 2], [1, 2], [2, 2], [2, 1], [2, 0]]

    int[][] heights_1 = {
        { 1, 2, 3, 4 },
        { 5, 6, 7, 8 },
        { 9, 10, 11, 12 },
        { 13, 14, 15, 16 }
    };
    List<List<Integer>> result_1 = solution.pacificAtlantic(heights_1);
    for (List<Integer> cell : result_1) {
      System.out.println(cell);
    }
    // Expected output: [[0, 0], [0, 1], [0, 2], [0, 3], [1, 3], [2, 3], [3, 3], [3,
    // 2], [3, 1], [3, 0],
  }

  class Solution {
    public int minReorder(int n, int[][] connections) {
      List<List<int[]>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }

      for (int[] conn : connections) {
        int u = conn[0], v = conn[1];
        graph.get(u).add(new int[] { v, 1 }); // 1 = u -> v
        graph.get(v).add(new int[] { u, -1 }); // -1 = v -> u (or u <- v)
      }

      int[] count = { 0 };
      boolean visited[] = new boolean[n];
      dfs(graph, count, visited, 0);
      return count[0];
    }

    private void dfs(List<List<int[]>> graph, int[] count, boolean[] visited, int node) {
      if (visited[node])
        return;
      visited[node] = true;

      for (int[] neighbor : graph.get(node)) {
        if (!visited[neighbor[0]]) {
          if (neighbor[1] == 1)
            count[0]++; // If the edge is directed u -> v, we need to reverse it
          dfs(graph, count, visited, neighbor[0]);
        }
      }
    }
  }
}
