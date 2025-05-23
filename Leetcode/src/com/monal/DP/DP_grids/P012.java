package com.monal.DP.DP_grids;

import java.util.*;

/**
 * Given a triangle array, return the minimum path sum from top to bottom.
 * For each step, you may move to an adjacent number of the row below, or
 * you may move to the number directly below it.
 * More formally, if you are on index i on the current row, you may move to
 * either index i or index i + 1 on the next row.
 *
 * Example 1:
 * Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
 * Output: 11
 * Explanation: The triangle looks like:
 * 2
 * 3 4
 * 6 5 7
 * 4 1 8 3
 * The minimum path sum from top to bottom is 2 + 3 + 5 + 1 = 11 (underlined
 *
 * Example 2:
 * Input: triangle = [[-10]]
 * Output: -10
 *
 * Constraints:
 * 1 <= triangle.length <= 200
 * triangle[0].length == 1
 * triangle[i].length == triangle[i - 1].length + 1
 * -104 <= triangle[i][j] <= 104
 */
public class P012 {

  public int triangle(List<List<Integer>> triangle) {
    int n = triangle.size();
    int[][] dp = new int[n][n];
    // Fill the last row of dp with the last row of triangle
    for (int j = 0; j < n; j++) {
      dp[n - 1][j] = triangle.get(n - 1).get(j);
    }
    // Fill the dp table from bottom to top
    for (int i = n - 2; i >= 0; i--) {
      for (int j = 0; j <= i; j++) {
        dp[i][j] = triangle.get(i).get(j) + Math.min(dp[i + 1][j], dp[i + 1][j + 1]);
      }
    }
    return dp[0][0]; // The top element contains the minimum path sum
  }

  // my solution was making a dp like traingle

  @SuppressWarnings("unused")
  private int triangle1(List<List<Integer>> triangle) {
    int n = triangle.size();
    List<List<Integer>> dp = new ArrayList<>(n);
    // Initialize the dp list with the same size as triangle
    // and fill it with -1
    for (int i = 0; i < n; i++) {
      dp.add(new ArrayList<>(Collections.nCopies(i + 1, -1)));
    }

    // Base case: first element
    dp.get(0).set(0, triangle.get(0).get(0));

    // Lets fill the table form top taking the min of the two elements below
    for (int i = 1; i < n; i++) {
      for (int j = 0; j <= i; j++) { // Ensures we are making a triangle
        if (j == 0) {
          // First element of the row can only come from the first element of the previous
          dp.get(i).set(j, dp.get(i - 1).get(j) + triangle.get(i).get(j));
        } else if (j == i) {
          // Last element of the row can only come from the last element of the previous
          dp.get(i).set(j, dp.get(i - 1).get(j - 1) + triangle.get(i).get(j));
        } else {
          dp.get(i).set(j, Math.min(dp.get(i - 1).get(j), dp.get(i - 1).get(j - 1)) + triangle.get(i).get(j));
        }
      }
    }

    // The minimum path sum is the minimum value in the last row of dp
    int minPathSum = Integer.MAX_VALUE;
    for (int j = 0; j < n; j++) {
      minPathSum = Math.min(minPathSum, dp.get(n - 1).get(j));
    }
    return minPathSum;
  }
}
