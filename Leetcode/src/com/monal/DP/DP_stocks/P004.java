package com.monal.DP.DP_stocks;

/*
You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.
Find the maximum profit you can achieve. You may complete at most k transactions: i.e. you may buy at most k times and sell at most k times.
Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

Example 1:
  Input: k = 2, prices = [2,4,1]
  Output: 2
  Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.
Example 2:
  Input: k = 2, prices = [3,2,6,5,0,3]
  Output: 7
  Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
Constraints:
  1 <= k <= 100
  1 <= prices.length <= 1000
  0 <= prices[i] <= 1000
*/
public class P004 {

  class Solution {
    public int maxProfit(int k, int[] prices) {
      int n = prices.length;
      int dp[][][] = new int[n + 1][2][k + 1]; // dp[day][canBuy][transactionsLeft]:
      // day from 0 to n
      // canBuy = 1 if we can buy, 0 if we can sell
      // transactionsLeft from 0 to k (i.e., at most k transactions allowed)
      // our final asnwer will be dp[0][1][k] we start on day 0, can buy stock, and k

      for (int day = n - 1; day >= 0; day--) {
        for (int canBuy = 0; canBuy < 2; canBuy++) {
          for (int transactionsLeft = 1; transactionsLeft <= k; transactionsLeft++) {
            int profit = 0;
            if (canBuy == 1) {
              // canBuy == 1 is we can buy
              // we can either buy or skip
              int buy = dp[day + 1][0][transactionsLeft] - prices[day]; // buy
              int notBuy = dp[day + 1][1][transactionsLeft]; // skip
              profit = Math.max(buy, notBuy);
            } else {
              // canBuy == 0 is we cannot buy
              // so we are left with 2 options
              int sell = dp[day + 1][1][transactionsLeft - 1] + prices[day]; // sell
              int hold = dp[day + 1][0][transactionsLeft]; // skip
              profit = Math.max(sell, hold);
            }
            dp[day][canBuy][transactionsLeft] = profit;
          }
        }
      }
      return dp[0][1][k]; // we start on day 0, can buy stock, and k transactions left
    }
  }
}
