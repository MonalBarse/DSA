package com.monal.DP.DP_stocks;

/*
You are given an array prices where prices[i] is the price of a given stock on the ith day.
Find the maximum profit you can achieve. You may complete as many transactions as you like (i.e., buy one and sell one share of the stock multiple times) with the following restrictions:
After you sell your stock, you cannot buy stock on the next day (i.e., cooldown one day).
Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

Example 1:
  Input: prices = [1,2,3,0,2]
  Output: 3
  Explanation: transactions = [buy, sell, cooldown, buy, sell]
Example 2:
  Input: prices = [1]
  Output: 0
*/
public class P005 {
  class Solution {
    public int maxProfit(int[] prices) {
      int n = prices.length;
      int dp[][][] = new int[n + 1][2][2]; // dp[day][canBuy][coolDown];
      // our answer will be dp[0][1][0] we start on day n, can buy stock, no cooldown
      // canBuy = 1 if we can buy, 0 if we can sell
      // coolDown = 1 if cooldown(can't buy), 0 if not cooldown (can buy)
      // day from 0 to n

      for (int day = n - 1; day >= 0; day--) {
        for (int canBuy = 0; canBuy < 2; canBuy++) {
          for (int cooldown = 0; cooldown < 2; cooldown++) {

            int profit = 0;
            if (canBuy == 1) {
              // canBuy == 1 is we can buy
              // we can either buy or skip if no cooldown
              if (cooldown != 1) {
                int buy = dp[day + 1][0][0] - prices[day]; // buy
                int notBuy = dp[day + 1][1][0]; // skip
                profit = Math.max(buy, notBuy);
              } else {
                // if cooldown skip the day and reset cooldown
                profit = dp[day + 1][1][0];
              }
            } else {
              // canBuy == 0 is we cannot buy
              // we can either hold or sell
              int sell = dp[day + 1][1][1] + prices[day]; // sell
              int hold = dp[day + 1][0][0]; // skip
              profit = Math.max(sell, hold);

            }
            dp[day][canBuy][cooldown] = profit;
          }

        }

      }
      return dp[0][1][0]; // we start on day 0, can buy stock, and no cooldown
    }
  }
}
