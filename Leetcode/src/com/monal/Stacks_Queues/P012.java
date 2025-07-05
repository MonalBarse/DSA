package com.monal.Stacks_Queues;

import java.util.*;

// LEETCODE 85: https://leetcode.com/problems/maximal-rectangle/
/*
Given a rows x cols binary matrix filled with 0's and 1's,
find the largest rectangle containing only 1's and return its area.

Example 1:
  Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
  Output: 6
  Explanation: The maximal rectangle is shown in the above picture.

Example 2:
  Input: matrix = [["0"]]
  Output: 0
*/

public class P012 {
  public int maximalRectangle(char[][] matrix) {
    int n = matrix.length, m = matrix[0].length; // n x m matrix
    // Imagaine each row as a histogram - lets create that histogram
    int[][] H = new int[n][m]; // H[i] represents the histogram for row i
    // Let's go to each histogram
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < m; j++) {
        // If the cell is 1, we can extend the height of the histogram
        if (matrix[i][j] == '1') {
          H[i][j] = (i == 0) ? 1 : H[i - 1][j] + 1; // If first row, height is 1, else add to previous row's height
        } else {
          H[i][j] = 0; // If cell is 0, height is 0
        }
      }
    }

    // after this the matrix [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
    // will be transformed to:
    // H =
    // [[1,0,1,0,0],
    //  [2,0,1,1,1],
    //  [3,1,1,1,1],
    //  [4,0,0,1,0]]
    // where H[i][j] represents the height of the histogram

    // Now we have the histogram for each row, we can find the maximum rectangle
    // area
    int maxArea = 0;
    for (int i = 0; i < n; i++) {
      // For each histogram, we can use the largest rectangle in histogram approach
      maxArea = Math.max(maxArea, largestRectangleArea(H[i]));
    }

    return maxArea;

  }

  private int largestRectangleArea(int[] heights) {
    int n = heights.length;
    int[] pse = previousSmallerElements(heights); // Previous Smaller Elements
    int[] nse = nextSmallerElements(heights); // Next Smaller Elements

    int maxArea = 0;
    for (int i = 0; i < n; i++) {
      int left = pse[i];
      int right = nse[i];
      int width = right - left - 1; // careful! boundaries excluded
      maxArea = Math.max(maxArea, heights[i] * width);
    }
    return maxArea;
  }

  private int[] previousSmallerElements(int[] arr) {
    int n = arr.length;
    int[] res = new int[n];
    Arrays.fill(res, -1);
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && arr[stack.peek()] >= arr[i])
        stack.pop();
      if (!stack.isEmpty())
        res[i] = stack.peek(); // ← Return INDEX, not value
      stack.push(i);
    }
    return res;
  }

  private int[] nextSmallerElements(int[] arr) {
    int n = arr.length;
    int[] res = new int[n];
    Arrays.fill(res, n); // ✅ Fix: fill with n instead of -1
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
        int poppedIndex = stack.pop();
        res[poppedIndex] = i; // ← Return INDEX, not value
      }
      stack.push(i);
    }
    return res;
  }

}
