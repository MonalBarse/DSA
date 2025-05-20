package com.monal.DP.DP_Knapsack;

import java.util.*;

/********************************
 * PROBLEM 9: Coin Change Problem *
 ********************************/

/**
 * Problem1 : Given an array of coin denominations and a target amount,
 * find the number of ways to make the target amount using the coins.
 *
 * This is similar to the unbounded knapsack problem where:
 * - weights[] = coin denominations
 * - values[] = 1 (each coin contributes 1 way)
 * - capacity = target amount
 *
 * Approach: Use unbounded knapsack solution to count the number of ways
 * to make the target amount.
 */
/**
 * Problem 2: Coing Change II - Minimum coins
 * You are given an integer array coins representing coins of different
 * denominations and an integer amount representing a total amount of money.
 *
 * Return the fewest number of coins that you need to make up that amount. If
 * that amount of money cannot be made up by any combination of the coins,
 * return -1.
 *
 * You may assume that you have an infinite number of each kind of coin.
 */

public class P005 {

  public static int coinChange(int[] coins, int amount) {
    // return coinChangeRecursive(coins, coins.length, amount);
    int[][] memo = new int[coins.length + 1][amount + 1];
    for (int m = 0; m < coins.length + 1; m++) {
      Arrays.fill(memo[m], -1);
    }
    return coinChangeMemo(coins, coins.length, amount, memo);
  }

  @SuppressWarnings("unused")
  private static int coinChangeRecursive(int[] coins, int n, int amount) {
    // Base cases
    if (amount == 0)
      return 1; // One way to make amount 0 (no coins)
    if (n == 0 || amount < 0)
      return 0; // No coins left or negative amount
    // If current coin is greater than amount, skip it
    if (coins[n - 1] > amount)
      return coinChangeRecursive(coins, n - 1, amount);

    // include or exclude and can reuse the coin while including
    int include = coinChangeRecursive(coins, n, amount - coins[n - 1]);
    int exclude = coinChangeRecursive(coins, n - 1, amount);
    return include + exclude;
  }

  private static int coinChangeMemo(int[] coins, int n, int amt, int[][] memo) {
    if (amt == 0)
      return 1; // One way to make amount 0 — take nothing
    if (n == 0)
      return 0; // No coins left to use
    if (memo[n][amt] != -1)
      return memo[n][amt];

    if (coins[n - 1] > amt) {
      return memo[n][amt] = coinChangeMemo(coins, n - 1, amt, memo);
    }

    int include = coinChangeMemo(coins, n, amt - coins[n - 1], memo); // take current coin again
    int exclude = coinChangeMemo(coins, n - 1, amt, memo); // skip current coin

    return memo[n][amt] = include + exclude;
  }

  @SuppressWarnings("unused")
  private static int coinChangeTab(int[] coins, int n, int amt) {
    // initialization
    int dp[][] = new int[n + 1][amt + 1];
    for (int i = 0; i <= n; i++) {
      dp[i][0] = 1; // 1 way to make 0 amount (use nothing)
    }

    // fill the dp table
    for (int i = 1; i <= coins.length; i++) {
      for (int j = 1; j <= amt; j++) {
        dp[i][j] = (coins[i - 1] > j) ? dp[i - 1][j] : dp[i - 1][j] + dp[i][j - coins[i - 1]];
      }
    }

    return dp[coins.length][amt];
  }

  // ----------------------------------------------------- //
  // Problem 2: Coin Change II - Minimum coins

  public static int minCoins(int coins[], int amount) {
    int[][] memo = new int[coins.length + 1][amount + 1];
    for (int m = 0; m < coins.length + 1; m++) {
      Arrays.fill(memo[m], -1);
    }

    return minCoinsMemoized(coins, coins.length, amount, memo);
  }

  private static int minCoinsMemoized(int[] coins, int n, int amt, int[][] memo) {
    // Base cases
    if (amt == 0)
      return 0; // 0 coins needed to make 0
    if (n == 0)
      return Integer.MAX_VALUE; // Cannot make amount with 0 coins

    // Return memoized result if available
    if (memo[n][amt] != -1)
      return memo[n][amt];

    // If current coin value is greater than remaining amount, skip it
    if (coins[n - 1] > amt) {
      return memo[n][amt] = minCoinsMemoized(coins, n - 1, amt, memo);
    }

    // Two choices: include current coin or exclude it
    int include = minCoinsMemoized(coins, n, amt - coins[n - 1], memo);
    // Only add 1 if include path is valid
    if (include != Integer.MAX_VALUE)
      include = 1 + include;

    int exclude = minCoinsMemoized(coins, n - 1, amt, memo);

    // Take minimum of the two choices
    return memo[n][amt] = Math.min(include, exclude);
  }

  @SuppressWarnings("unused")
  private static int minCoinsTabulated(int[] coins, int n, int amount) {
    int[][] dp = new int[n + 1][amount + 1];

    // Initialize first row with MAX_VALUE (except dp[0][0])
    for (int j = 1; j <= amount; j++) {
      dp[0][j] = Integer.MAX_VALUE;
    }

    // Fill the dp table
    for (int i = 1; i <= n; i++) {
      for (int j = 0; j <= amount; j++) {
        if (j == 0) {
          dp[i][j] = 0; // 0 coins needed to make amount 0
        } else if (coins[i - 1] > j) {
          // Can't use current coin, take value from above
          dp[i][j] = dp[i - 1][j];
        } else {
          // Two choices: include current coin or exclude it
          int include = dp[i][j - coins[i - 1]];
          // Only add 1 if include path is valid
          if (include != Integer.MAX_VALUE) {
            include = 1 + include;
          }

          int exclude = dp[i - 1][j];

          dp[i][j] = Math.min(include, exclude);
        }
      }
    }

    return dp[n][amount];
  }

  // Space Optimized approach
  public int coinChangeI(int[] coins, int amount) {
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, 1000001);

    dp[0] = 0;

    for (int a = 1; a <= amount; a++) {
      for (int coin : coins) {
        // If the coin can be used to make the amount
        // So for every a, we ask:
        // Is it better to not use coin c, or use it and solve for a - c?
        if (a - coin >= 0) {
          dp[a] = Math.min(dp[a], dp[a - coin] + 1);
        }
      }
    }
    return dp[amount] == 1000001 ? -1 : dp[amount];
  }

}
