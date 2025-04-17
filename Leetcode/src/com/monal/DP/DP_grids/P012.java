package com.monal.DP.DP_grids;

import java.util.List;

/**
 * Given a triangle array, return the minimum path sum from top to bottom.
 * For each step, you may move to an adjacent number of the row below. More
 * formally, if you are on index i on the current row, you may move to either
 * index i or index i + 1 on the next row.
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
}
