package com.monal.DP.DP_stocks;

/*
You are given an array prices where prices[i] is the price of a given stock on the ith day.
Find the maximum profit you can achieve. You may complete at most two transactions.
Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).

Example 1:
  Input: prices = [3,3,5,0,0,3,1,4]
  Output: 6
  Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
  Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.
Example 2:
  Input: prices = [1,2,3,4,5]
  Output: 4
  Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
  Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging multiple transactions at the same time. You must sell before buying again.
Example 3:
  Input: prices = [7,6,4,3,1]
  Output: 0
  Explanation: In this case, no transaction is done, i.e. max profit = 0.
*/
public class P003 {
  class Solution {

    // ===================================================== //
    // ----------------- Tabulation ------------------------ //
    // ===================================================== //
    public int maxProfit(int[] prices) {
      int n = prices.length;
      int dp[][][] = new int[n + 1][2][3]; // dp[day][canBuy][transactionsLeft]:
      // day from 0 to n
      // canBuy = 1 if we can buy, 0 if we can sell
      // transactionsLeft from 0 to 2 (i.e., at most 2 transactions allowed)

      // our final asnwer will be dp[0][1][2] we start on day 0, can buy stock, and 2
      // transactions left

      for (int i = n - 1; i >= 0; i--) {
        for (int j = 0; j < 2; j++) {
          for (int cap = 1; cap < 3; cap++) {
            int profit = 0;
            if (j == 1) {
              // j==1 is we can buy
              // we can either buy or skip
              int buy = dp[i + 1][0][cap] - prices[i]; // buy
              int notBuy = dp[i + 1][1][cap]; // skip
              profit = Math.max(buy, notBuy);
            } else {
              // j == 0 is we cannot buy
              // so we are left with 2 options
              int sell = dp[i + 1][1][cap - 1] + prices[i]; // sell
              int hold = dp[i + 1][0][cap]; // skip
              profit = Math.max(sell, hold);
            }

            dp[i][j][cap] = profit;
          }

        }

      }

      return dp[0][1][2]; // we start on day 0, can buy stock, and 2 transactions left
    }

    // ====================================================== //
    // --------------- Space Optimization ------------------- //
    // ====================================================== //

    public static int maxProfitSpaceOpt(int[] prices) {
      int n = prices.length;
      int next[][] = new int[2][3]; // dp[day][canBuy][transactionsLeft]:

      // day from 0 to n
      // canBuy = 1 if we can buy, 0 if we can sell
      // transactionsLeft from 0 to 2 (i.e., at most 2 transactions allowed)
      // we have two arr (curr and next) our final asnwer will be
      // next[1][2] we start on day 0, can buy stock, and 2

      // transactions left
      for (int i = n - 1; i >= 0; i--) {
        int curr[][] = new int[2][3];
        for (int j = 0; j < 2; j++) {
          for (int cap = 1; cap < 3; cap++) {
            int profit = 0;
            if (j == 1) {
              // i.e we can buy, so options: buy or skip
              int buy = next[0][cap] - prices[i];
              int skip = next[1][cap];
              profit = Math.max(buy, skip);
            } else {
              // i.e we cannot buy, so options: sell or skip
              int sell = next[1][cap - 1] + prices[i];
              int hold = next[0][cap];
              profit = Math.max(sell, hold);
            }
            curr[j][cap] = profit;
          }
        }

        // update next to curr
        for (int j = 0; j <= 1; j++) {
          for (int k = 0; k <= 2; k++) {
            next[j][k] = curr[j][k];
          }
        }
      }

      return next[1][2]; // we start on day 0, can buy stock, and 2 transactions left
    }

  }

}