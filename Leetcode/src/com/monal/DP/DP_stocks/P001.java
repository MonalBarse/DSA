package com.monal.DP.DP_stocks;

// 121. Best Time to Buy and Sell Stock I
/**
 * You are given an array prices where prices[i] is the price of a given stock
 * on the ith day.
 * You want to maximize your profit by choosing a single day to buy one stock
 * and choosing a different day in the future to sell that stock.
 * Return the maximum profit you can achieve from this transaction. If you
 * cannot achieve any profit, return 0.
 *
 * - Example 1:
 * Input: prices = [7,1,5,3,6,4]
 * Output: 5
 * Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit =
 * 6-1 = 5.
 * Note that buying on day 2 and selling on day 1 is not allowed because you
 * must buy before you sell.
 *
 * - Example 2:
 * Input: prices = [7,6,4,3,1]
 * Output: 0
 * Explanation: In this case, no transactions are done and the max profit = 0.
 *
 * Constraints:
 *
 * 1 <= prices.length <= 105
 * 0 <= prices[i] <= 104
 */
public class P001 {
  class Solution {

    public int maxProfitGreedy(int[] arr) {
      int maxProfit = 0;
      int buy = arr[0];
      for (int i = 1; i < arr.length; i++) {
        buy = Math.min(buy, arr[i]); // Find the minimum buy price
        maxProfit = Math.max(maxProfit, arr[i] - buy); // Compute max profit
      }
      return maxProfit;
    }

    public int maxProfitTab(int[] prices) {
      if (prices == null || prices.length <= 1) {
        return 0;
      }

      int n = prices.length;

      // dp[i] represents the maximum profit we can get up to day i
      int[] dp = new int[n];
      dp[0] = 0; // No profit on first day as we can only buy

      int minPrice = prices[0]; // Minimum price seen so far

      for (int i = 1; i < n; i++) {
        // We have two choices:
        // 1. Don't sell on day i, so profit remains same as previous day
        // 2. Sell on day i, profit = prices[i] - minPrice seen so far
        dp[i] = Math.max(dp[i - 1], prices[i] - minPrice);

        // Update minimum price
        minPrice = Math.min(minPrice, prices[i]);
      }

      // Maximum profit will be at the last day
      return dp[n - 1];
    }

  }

  public static void main(String[] args) {
    P001 p001 = new P001();
    Solution solution = p001.new Solution();
    int[] prices1 = { 7, 1, 5, 3, 6, 4, 2, 3, 12, 1, 23, 11, 5, 12, 15, 24, 25, 6, 45, 78, 83, 12, 23, 45, 67, 89, 90,
        23, 100 };
    int[] prices2 = { 12, 1, 1, 1, 2, 3, 3, 4, 5, 5, 55, 27, 6, 4, 3, 1, 7, 6, 4, 3, 1, 7, 6, 4, 3, 1 };

    // Time taken for bth methods
    long startTime = System.nanoTime();
    int maxProfitGreedy = solution.maxProfitGreedy(prices1);
    long endTime = System.nanoTime();
    long duration = endTime - startTime;
    System.out.println("Max Profit (Greedy): " + maxProfitGreedy);

    System.out.println("Time taken (Greedy): " + duration + " nanoseconds");
    startTime = System.nanoTime();
    int maxProfitTab = solution.maxProfitTab(prices1);
    endTime = System.nanoTime();

    duration = endTime - startTime;
    System.out.println("Max Profit (Tabulation): " + maxProfitTab);
    System.out.println("Time taken (Tabulation): " + duration + " nanoseconds");

    System.out.println("-----------------------------------");
    // INPUT 2
    startTime = System.nanoTime();
    maxProfitGreedy = solution.maxProfitGreedy(prices2);
    endTime = System.nanoTime();
    duration = endTime - startTime;
    System.out.println("Max Profit (Greedy): " + maxProfitGreedy);
    System.out.println("Time taken (Greedy): " + duration + " nanoseconds");
    startTime = System.nanoTime();
    maxProfitTab = solution.maxProfitTab(prices2);
    endTime = System.nanoTime();
    duration = endTime - startTime;
    System.out.println("Max Profit (Tabulation): " + maxProfitTab);
    System.out.println("Time taken (Tabulation): " + duration + " nanoseconds");
  }
}
