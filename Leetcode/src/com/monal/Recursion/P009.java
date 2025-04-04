package com.monal.Recursion;

import java.util.*;

/*
Consider a rat placed at position (0, 0) in an n x n square matrix mat. The rat's goal is to reach the destination at position (n-1, n-1). The rat can move in four possible directions: 'U'(up), 'D'(down), 'L' (left), 'R' (right).
The matrix contains only two possible values:

  0: A blocked cell through which the rat cannot travel.
  1: A free cell that the rat can pass through.
  Note: In a path, no cell can be visited more than one time. If the source cell is 0, the rat cannot move to any other cell. In case of no path, return an empty list.

  The task is to find all possible paths the rat can take to reach the destination, starting from (0, 0) and ending at (n-1, n-1), under the condition that the rat cannot revisit any cell along the same path. Furthermore, the rat can only move to adjacent cells that are within the bounds of the matrix and not blocked.

Return the final result vector in lexicographically smallest order.
Examples:
  Input: mat[][] = [[1, 0, 0, 0], [1, 1, 0, 1], [1, 1, 0, 0], [0, 1, 1, 1]]
  Output: ["DDRDRR", "DRDDRR"]
  Explanation: The rat can reach the destination at (3, 3) from (0, 0) by two paths - DRDDRR and DDRDRR, when printed in sorted order we get DDRDRR DRDDRR.

  Input: mat[][] = [[1, 0], [1, 0]]
  Output: []
  Explanation: No path exists and the destination cell is blocked.

  Input: mat = [[1, 1, 1], [1, 0, 1], [1, 1, 1]]
  Output: ["DDRR", "RRDD"]
  Explanation: The rat has two possible paths to reach the destination: 1. "DDRR" 2. "RRDD", These are returned in lexicographically sorted order.
*/
public class P009 {

  class Solution {

    // Function to find all possible paths
    public ArrayList<String> findPath(ArrayList<ArrayList<Integer>> arr) {
      int n = arr.size();
      ArrayList<String> res = new ArrayList<>();

      if (arr.get(0).get(0) == 0 || arr.get(n - 1).get(n - 1) == 0)
        return res; // if start or end is blocked, return empty result

      boolean[][] visited = new boolean[n][n]; // Visited matrix
      helper_fn(arr, 0, 0, n, "", res, visited);
      Collections.sort(res); // Sort results lexicographically
      return res;
    }

    private void helper_fn(ArrayList<ArrayList<Integer>> arr, int row, int col, int n, String path,
        ArrayList<String> res, boolean[][] visited) {
      // Base case: If reached bottom-right corner, add path to result
      if (row == n - 1 && col == n - 1) {
        res.add(path);
        return;
      }
      // Mark current cell as visited
      visited[row][col] = true;

      // Try to move Down
      if (isSafe(row + 1, col, n, arr, visited)) {
        helper_fn(arr, row + 1, col, n, path + "D", res, visited);
      }

      // Try to move Left
      if (isSafe(row, col - 1, n, arr, visited)) {
        helper_fn(arr, row, col - 1, n, path + "L", res, visited);
      }

      // Try to move Right
      if (isSafe(row, col + 1, n, arr, visited)) {
        helper_fn(arr, row, col + 1, n, path + "R", res, visited);
      }

      // Try to move Up
      if (isSafe(row - 1, col, n, arr, visited)) {
        helper_fn(arr, row - 1, col, n, path + "U", res, visited);
      }

      // Backtrack: Unmark cell
      visited[row][col] = false;
    }

    private boolean isSafe(int row, int col, int n, ArrayList<ArrayList<Integer>> arr, boolean[][] visited) {
      return row >= 0 && col >= 0 && row < n && col < n &&
          arr.get(row).get(col) == 1 && !visited[row][col];
    }
  }

  public static void main(String[] args) {

  }

}
