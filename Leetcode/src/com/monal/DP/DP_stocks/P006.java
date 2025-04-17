package com.monal.DP.DP_stocks;

/*
You are given an array prices where prices[i] is the price of a given stock on the ith day, and an integer fee representing a transaction fee.
Find the maximum profit you can achieve. You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction.
Note:
You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
The transaction fee is only charged once for each stock purchase and sale.

Example 1:
  Input: prices = [1,3,2,8,4,9], fee = 2
  Output: 8
  Explanation: The maximum profit can be achieved by:
  - Buying at prices[0] = 1
  - Selling at prices[3] = 8
  - Buying at prices[4] = 4
  - Selling at prices[5] = 9
  The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.
Example 2:
  Input: prices = [1,3,7,5,10,3], fee = 3
  Output: 6
*/
public class P006 {
  class Solution {
    public int maxProfit(int[] prices, int fee) {
      int n = prices.length;
      int dp[][] = new int[n + 1][2]; // dp[day][canBuy]:

      // day from 0 to n
      // can buy = 1 if we can buy, 0 if we can sell
      // our final answer will be dp[0][1] we start on day 0, can buy stock

      // on selling day, we need to pay the fee

      for (int day = n - 1; day >= 0; day--) {
        for (int canBuy = 0; canBuy < 2; canBuy++) {
          int profit = 0;
          if (canBuy == 1) {
            // canBuy == 1 is we can buy
            // we can either buy or skip
            int buy = dp[day + 1][0] - prices[day]; // buy
            int notBuy = dp[day + 1][1]; // skip
            profit = Math.max(buy, notBuy);
          } else {
            // canBuy == 0 is we cannot buy
            // so we are left with 2 options
            int sell = dp[day + 1][1] + prices[day] - fee; // sell
            int hold = dp[day + 1][0]; // skip
            profit = Math.max(sell, hold);
          }

          dp[day][canBuy] = profit;
        }
      }

      return dp[0][1];

    }
  }
}
