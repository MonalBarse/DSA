package com.monal.Graphs.ShortestDistance;

import java.util.*;

/*
Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix.
If there is no clear path, return -1. A clear path in a binary matrix is a path from the
`start` cell to the `end` cell such that:
All the visited cells of the path are 0.
All the adjacent cells of the path are 8-directionally connected
(i.e., they are different and they share an edge or a corner).
The length of a clear path is the number of visited cells of this path.

Example 1:
  Input: grid = [[0,1],[1,0]], start = [0,0], end = [1,1]
  Output: 2
Example 2:
  Input: grid = [[0,0,0],[1,1,0],[1,1,0]], start = [0,0], end = [2,2]
  Output: 4
Example 3:
  Input: grid = [[1,0,0],[1,1,0],[1,1,0]], start = [0,0], end = [1,2]
  Output: -1

*/
public class P003 {

  class Solution {
    class Pair {
      int x, y;

      Pair(int x, int y) {
        this.x = x;
        this.y = y;
      }

      public int getX() {
        return x;
      }

      public int getY() {
        return y;
      }
    }

    public int shortestPathBinaryMatrix(int[][] grid, Pair start, Pair end) {
      int n = grid.length;
      if (grid[start.getX()][start.getY()] == 1 || grid[end.getX()][end.getY()] == 1) {
        return -1;
      }

      boolean[][] visited = new boolean[n][n];
      Queue<Pair> q = new ArrayDeque<>();
      q.offer(start);
      visited[start.getX()][start.getY()] = true;

      int[][] directions = {
          { -1, -1 }, { -1, 0 }, { -1, 1 },
          { 0, -1 }, { 0, 1 },
          { 1, -1 }, { 1, 0 }, { 1, 1 }
      };

      int distance = 1; // Starting cell counts as step 1

      while (!q.isEmpty()) {
        int size = q.size();
        for (int i = 0; i < size; i++) {
          Pair curr = q.poll();
          int x = curr.getX(), y = curr.getY();

          if (x == end.getX() && y == end.getY()) {
            return distance;
          }

          for (int[] dir : directions) {
            int newX = x + dir[0], newY = y + dir[1];
            if (newX >= 0 && newY >= 0 && newX < n && newY < n
                && !visited[newX][newY] && grid[newX][newY] == 0) {
              visited[newX][newY] = true;
              q.offer(new Pair(newX, newY));
            }
          }
        }
        distance++;
      }
      return -1;
    }
  }

}
