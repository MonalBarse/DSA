package com.monal.DP.DP_one_twoD;
/*
  Statement:
  You are given an array heights[] where heights[i] is the height of the i-th stair.
  The frog is initially on stair 0. It can jump from stair i to any stair j
  such that 1 <= j - i <= k. The cost of a jump from i to j is abs(heights[i] - heights[j]).
  Return the minimum cost to reach the last stair (index n - 1).

  Example 1:
    Input: heights = [10,30,40,50,20], k = 2
    Output: 30
    Explanation: The frog can jump from stair 0 to stair 1 with a cost of abs(10 - 30) = 20.
    Then it can jump from stair 1 to stair 4 with a cost of abs(30 - 20) = 10.
    The total cost is 20 + 10 = 30.

  Example 2:
    Input: heights = [10,20,30,40,50], k = 3
    Output: 40
    Explanation: The frog can jump from stair 0 to stair 4 with a cost of abs(10 - 50) = 40.

*/

public class P001 {

  class Solution {
    public int frogJump(int k, int[] heights) {
      int n = heights.length;
      int dp[] = new int[n]; // dp[i] = min cost to reach stair i
      dp[0] = 0; // cost to reach stair 0 is 0

      // Build the dp array
      for (int i = 1; i < n; i++) {
        int minCost = Integer.MAX_VALUE;
        // Check the last k stairs
        for (int j = 1; j <= k && i - j >= 0; j++) {
          int cost = dp[i - j] + Math.abs(heights[i] - heights[i - j]);
          minCost = Math.min(minCost, cost);
        }
        dp[i] = minCost;
      }

      return dp[n - 1]; // Return the cost to reach the last stair
    }
  }

  // In this question instead
  class Solution2 {

    public int frogJumpMemo(int k, int[] heights) {
      int n = heights.length;
      int dp[] = new int[n]; // dp[i] = min cost to reach stair i
      dp[0] = 0; // cost to reach stair 0 is 0

      return frogJumpHelper(k, heights, n - 1, dp);
    }

    private int frogJumpHelper(int k, int[] heights, int i, int[] dp) {
      if (i == 0)
        return 0; // Base case: cost to reach stair 0 is 0
      if (dp[i] != 0)
        return dp[i]; // Return the cached result

      int minCost = Integer.MAX_VALUE;
      // Check the last k stairs
      for (int j = 1; j <= k && i - j >= 0; j++) {
        int cost = frogJumpHelper(k, heights, i - j, dp) + Math.abs(heights[i] - heights[i - j]);
        minCost = Math.min(minCost, cost);
      }
      dp[i] = minCost; // Cache the result
      return minCost;
    }

  }

  public static void main(String[] args) {
    Solution solution = new P001().new Solution();
    int[] heights = { 10, 30, 40, 50, 20 };
    int k = 2;
    System.out.println(solution.frogJump(k, heights)); // Output: 30
    int[] heights2 = { 10, 20, 30, 40, 50 };
    int k2 = 3;
    System.out.println(solution.frogJump(k2, heights2)); // Output: 40

    Solution2 solution2 = new P001().new Solution2();
    System.out.println(solution2.frogJumpMemo(k, heights)); // Output: 30
    System.out.println(solution2.frogJumpMemo(k2, heights2)); // Output: 40
  }
}