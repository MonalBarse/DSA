package com.monal.DP.Knapsack;

import java.util.Arrays;

/*
🏚️ Problem: House Robber
You are a robber planning to rob houses along a street.
Each house has some money, but adjacent houses are connected to security, so:
You can’t rob two adjacent houses.

You want to maximize the total amount of money you can rob.
🧾 Input:
An array nums[] where nums[i] is the money in the i-th house.
✅ Output:
Return the maximum amount you can rob without robbing adjacent houses.

Input:  nums = [2, 7, 9, 3, 1]
Output: 12
  Explaination
    Rob house 0 → 2
    Skip 1
    Rob house 2 → 9
    Skip 3
    Rob house 4 → 1
    → Total = 2 + 9 + 1 = 12

Input :  nums = [1, 2, 3, 1, 4, 5, 7, 8, 2, 9]
Output : 30
  Explaination
    Rob house 0 → 1
    Skip 1
    Rob house 2 → 3
    Skip 3
    Rob house 4 → 4
    Skip 5
    Rob house 6 → 7
    Skip 7
    Rob house 8 → 2
    Skip 9
    → Total = 1 + 3 + 4 + 7 + 2 = 17
*/

public class P001 {

  @SuppressWarnings("unused")
  private int houseRobber(int money[]) {
    int n = money.length;
    if (n == 0)
      return 0;

    int[] dp = new int[n + 1];
    // Initialize the dp array
    Arrays.fill(dp, -1);
    // return houseRobbRecursive(money, n, 0);
    // return robTopDown(money, n, dp);
    return robBottomUP(money, n, dp);
  }

  private int houseRobbRecursive(int[] money, int n, int idx) {
    // Base case
    if (idx >= n)
      return 0;

    // Case 1: Rob this house and skip the next one
    int rob1 = houseRobbRecursive(money, n, idx + 2) + money[idx];

    // Case 2: Skip this house
    int rob2 = houseRobbRecursive(money, n, idx + 1);
    return Math.max(rob1, rob2);

  }

  private int robTopDown(int[] money, int n, int[] cache) {
    if (n == 0)
      return 0;

    if (cache[n] != -1)
      return cache[n];

    // Case 1: Rob this house and skip the next one
    int rob1 = (n >= 2) ? robTopDown(money, n - 2, cache) + money[n - 1] : money[n - 1];
    // Case 2: Skip this house
    int rob2 = robTopDown(money, n - 1, cache);
    // Store the result in cache
    cache[n] = Math.max(rob1, rob2);
    return cache[n];
  }

  private int robBottomUP(int[] money, int n, int[] dp) {
    dp[0] = 0;
    dp[1] = money[0];

    for (int i = 2; i <= n; i++) {
      dp[i] = Math.max(money[i - 1] + dp[i - 2], dp[i - 1]);
    }
    return dp[n];

  }

  /*
   * House robber part II
   *
   * You are a professional robber planning to rob houses along a street. Each
   * house has a certain amount of money stashed. All houses at this place are
   * arranged in a circle. That means the first house is the neighbor of the last
   * one. Meanwhile, adjacent houses have a security system connected, and it will
   * automatically contact the police if two adjacent houses were broken into on
   * the same night.
   *
   * Given an integer array nums representing the amount of money of each house,
   * return the maximum amount of money you can rob tonight without alerting the
   * police.
   *
   * Example 1:
   * Input: nums = [2,3,2]
   * Output: 3
   * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money =
   * 2), because they are adjacent houses.
   *
   * Example 2:
   * Input: nums = [1,2,3,1]
   * Output: 4
   * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
   * Total amount you can rob = 1 + 3 = 4.
   *
   * Example 3:
   * Input: nums = [1,2,3]
   * Output: 3
   */
  public int rob(int[] nums) {
    int n = nums.length;

    if (n == 0)
      return 0;
    if (n == 1)
      return nums[0];
    if (n == 2)
      return Math.max(nums[0], nums[1]);

    // Case 1: Consider houses from 0 to n-2 (exclude the last house)
    int case1 = robIIRecursive(nums, 0, n - 2);
    // Integer[] memo1 = new Integer[n];
    // int case1 = robMemo(nums, 0, n - 2, memo1);

    // Case 2: Consider houses from 1 to n-1 (exclude the first house)
    int case2 = robIIRecursive(nums, 1, n - 1);
    // Integer[] memo2 = new Integer[n];
    // int case2 = robMemo(nums, 1, n - 1, memo2);

    return Math.max(case1, case2);
  }

  private int robIIRecursive(int[] nums, int start, int end) {
    // Base cases
    if (start > end)
      return 0;
    if (start == end)
      return nums[start];

    // Rob current house and skip next, or skip current house
    int robCurrent = nums[start] + robIIRecursive(nums, start + 2, end);
    int skipCurrent = robIIRecursive(nums, start + 1, end);

    return Math.max(robCurrent, skipCurrent);
  }

  @SuppressWarnings("unused")
  private int robMemo(int[] nums, int start, int end, Integer[] memo) {
    // Base cases
    if (start > end)
      return 0;
    if (start == end)
      return nums[start];

    // Check if we've already computed this subproblem
    if (memo[start] != null)
      return memo[start];

    // Rob current house and skip next, or skip current house
    int robCurrent = nums[start] + robMemo(nums, start + 2, end, memo);
    int skipCurrent = robMemo(nums, start + 1, end, memo);

    // Memoize and return result
    memo[start] = Math.max(robCurrent, skipCurrent);
    return memo[start];
  }

  @SuppressWarnings("unused")
  private int robberIIBottomUp(int nums[]) {
    int n = nums.length;
    int dp1[] = new int[n + 1];
    if (n == 1)
      return nums[0];
    if (n == 2)
      return Math.max(nums[0], nums[1]);

    // Case 1: Rob the first house and skip the last one
    dp1[0] = nums[0];
    dp1[1] = Math.max(nums[0], nums[1]);
    for (int i = 2; i < n - 1; i++) {
      dp1[i] = Math.max(nums[i] + dp1[i - 2], dp1[i - 1]);
    }

    // Case 2: Skip the first house and rob the last one
    int dp2[] = new int[n + 1];
    dp2[0] = 0;
    dp2[1] = nums[1];
    for (int i = 2; i < n; i++) {
      dp2[i] = Math.max(nums[i] + dp2[i - 2], dp2[i - 1]);
    }

    // Return the maximum of both cases
    return Math.max(dp1[n - 2], dp2[n - 1]);

  }

  public static void main(String[] args) {

    P001 p001 = new P001();

    int[] money2 = { 1, 2, 3, 1, 4, 5, 7, 8, 2, 9 };
    int maxRobbed2 = p001.houseRobbRecursive(money2, money2.length, 0);
    System.out.println("Maximum amount that can be robbed: (solved using pure recursion) " + maxRobbed2);

    int[] money3 = { 1, 2, 3, 1, 4, 5, 7, 8, 2, 9 };
    int maxRobbed3 = p001.robTopDown(money3, money3.length, new int[money3.length + 1]);
    System.out.println("Maximum amount that can be robbed: (solved using memoziation)" + maxRobbed3);

    int[] money1 = { 22, 33, 12, 44, 55, 52, 42 };
    int maxRobbed1 = p001.robBottomUP(money1, money1.length, new int[money1.length + 1]);
    System.out.println("Maximum amount that can be robbed: (solved using bottom up DP) " + maxRobbed1);

  }

}
