package com.monal.DP;

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
