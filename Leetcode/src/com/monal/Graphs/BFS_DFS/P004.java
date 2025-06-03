package com.monal.Graphs.BFS_DFS;

/*
 * Problem: Flood Fill
 * Leetcode: https://leetcode.com/problems/flood-fill/
 *
 * Description:
 *  An image is represented by a 2D array of integers, where each integer represents the color of a pixel.
 *  You are given a starting pixel (sr, sc) and a new color. The task is to change the color of the starting pixel
 *  and all connected pixels with the same color to the new color.
 *  The connected pixels are those that share a side (up, down, left, right).
 *  You need to implement a function that performs this flood fill operation.
 *
 *  Example:
 *   Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
 *   Output: [[2,2,2],[2,2,0],[2,0,1]]
 *   Explanation: The pixel at (1, 1) and all connected pixels with the same color (1) are changed to color 2.
 *
 *  Example:
 *   Input: image = [[0,0,0],[0,1,1]], sr = 1, sc = 1, color = 1
 *   Output: [[0,0,0],[0,1,1]]
 *   Explanation: The pixel at (1, 1) is already color 1, so no change is made.
 *
*/
public class P004 {
  class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {

      if (image[sr][sc] == color) {
        return image;
      }
      int prevColor = image[sr][sc];
      dfs(image, color, sr, sc, prevColor);

      return image;
    }

    public void dfs(int[][] image, int color, int i, int j, int prevColor) {
      if (i < 0 || i >= image.length || j < 0 || j >= image[0].length || image[i][j] == color) {

      } else if (image[i][j] == prevColor) {
        image[i][j] = color;
        dfs(image, color, i + 1, j, prevColor);
        dfs(image, color, i - 1, j, prevColor);
        dfs(image, color, i, j + 1, prevColor);
        dfs(image, color, i, j - 1, prevColor);
      }

    }
  }

  public static void main(String[] args) {
    P004 solution = new P004();
    Solution s = solution.new Solution();

    int[][] image = {
        { 1, 1, 1 },
        { 1, 1, 0 },
        { 1, 0, 1 }
    };
    int sr = 1, sc = 1, color = 2;

    int[][] result = s.floodFill(image, sr, sc, color);

    for (int[] row : result) {
      for (int val : row) {
        System.out.print(val + " ");
      }
      System.out.println();
    }

    // Expected Output:
    // 2 2 2
    // 2 2 0
    // 2 0 1

    // Explanation: The pixel at (1, 1) and all connected pixels with the same color
    // (1) are changed to color 2.
  }
}
