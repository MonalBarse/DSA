package com.monal.Graphs.MST;

import java.util.*;

/*
You are given an n x n binary matrix grid. You are allowed to change at most one 0 to be 1.
Return the size of the largest island in grid after applying this operation.
An island is a 4-directionally connected group of 1s.

Example 1:
  Input: grid = [[1,0],[0,1]]
  Output: 3
  Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.
Example 2:
  Input: grid = [[1,1],[1,0]]
  Output: 4
  Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.
Example 3:
  Input: grid = [[1,1],[1,1]]
  Output: 4
  Explanation: Can't change any 0 to 1, only one island with area = 4.

Constraints:
  n == grid.length
  n == grid[i].length
  1 <= n <= 500
  grid[i][j] is either 0 or 1.
*/
public class P005 {

  class Solution {
    class DisjointSet {
      final int[] parent;
      final int[] size;

      DisjointSet(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
          parent[i] = i;
          size[i] = 1;
        }
      }

      int finxParent(int node) {
        if (parent[node] == node) {
          return node;
        }
        return parent[node] = finxParent(parent[node]);
      }

      boolean union(int n1, int n2) {
        int rootX = finxParent(n1), rootY = finxParent(n2);
        if (rootX == rootY)
          return false; // Already in the same set
        if (size[rootX] < size[rootY]) {
          parent[rootX] = rootY;
          size[rootY] += size[rootX];
        } else if (size[rootX] > size[rootY]) {
          parent[rootY] = rootX;
          size[rootX] += size[rootY];
        } else {
          parent[rootY] = rootX;
          size[rootX] += size[rootY];
        }
        return true; // Successfully merged two different components
      }
    }

    public int largestIsland(int[][] grid) {
      int n = grid.length;
      DisjointSet ds = new DisjointSet(n * n);
      boolean[][] visited = new boolean[n][n];
      int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (grid[i][j] == 1 && !visited[i][j]) {
            int currentIndex = i * n + j;
            visited[i][j] = true;

            // Check all 4 directions
            for (int[] dir : directions) {
              int newX = i + dir[0], newY = j + dir[1];
              int nextIndex = newX * n + newY;
              // Check if newX and newY are valid AND the neighbor is already land
              if (newX >= 0 && newY >= 0 && newX < n && newY < n && grid[newX][newY] == 1) {
                ds.union(currentIndex, nextIndex);
              }
            }
          }
        }
      }

      // first pass : calculate the size of each island
      int maxSize = 0;
      for (int i = 0; i < n * n; i++) {
        if (ds.parent[i] == i && grid[i / n][i % n] == 1) {
          maxSize = Math.max(maxSize, ds.size[i]);
        }
      }

      // second pass : try to change each 0 to 1 and calculate the size of the island
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (grid[i][j] == 0) {
            Set<Integer> uniqueRoots = new HashSet<>();
            int currentSize = 1; // Start with the current 0 cell

            // Check all 4 directions
            for (int[] dir : directions) {
              int newX = i + dir[0], newY = j + dir[1];
              if (newX >= 0 && newY >= 0 && newX < n && newY < n && grid[newX][newY] == 1) {
                int neighborIndex = newX * n + newY;
                int root = ds.finxParent(neighborIndex);
                if (uniqueRoots.add(root)) { // Only add unique roots
                  currentSize += ds.size[root];
                }
              }
            }

            maxSize = Math.max(maxSize, currentSize);
          }
        }
      }
      return maxSize;
    }
  }

}
