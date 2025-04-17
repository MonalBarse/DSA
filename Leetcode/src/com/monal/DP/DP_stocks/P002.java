package com.monal.DP.DP_stocks;

// 122. Best Time to Buy and Sell Stock II
/*
You are given an integer array prices where prices[i] is the price of a given stock on the ith day.
On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day.
Find and return the maximum profit you can achieve.

Example 1:
  Input: prices = [7,1,5,3,6,4]
  Output: 7
  Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
  Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
  Total profit is 4 + 3 = 7.
Example 2:
  Input: prices = [1,2,3,4,5]
  Output: 4
  Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
  Total profit is 4.
Example 3:
  Input: prices = [7,6,4,3,1]
  Output: 0
  Explanation: There is no way to make a positive profit, so we never buy the stock to achieve the maximum profit of 0.

Constraints:

1 <= prices.length <= 3 * 104
0 <= prices[i] <= 104
*/
public class P002 {
  class Solution {
    public int maxProfit(int[] prices) {
      if (prices == null || prices.length <= 1) {
        return 0;
      }

      int n = prices.length;
      int totalProfit = 0;

      // For multiple transactions, we just need to add up all positive price
      // differences
      for (int i = 1; i < n; i++) {
        if (prices[i] > prices[i - 1]) {
          totalProfit += prices[i] - prices[i - 1];
        }
      }

      return totalProfit;
    }

    public int maxProfitMEMO(int[] prices) {
      int n = prices.length;
      Integer[][] memo = new Integer[n][2]; // memo[i][canBuyStock]

      return dfs(0, true, prices, memo);
    }

    private int dfs(int day, boolean canBuyStock, int[] prices, Integer[][] memo) {
      if (day == prices.length)
        return 0;
      if (memo[day][canBuyStock ? 1 : 0] != null)
        return memo[day][canBuyStock ? 1 : 0];

      int maxProfit;
      if (canBuyStock) {
        int buyStock = -prices[day] + dfs(day + 1, false, prices, memo);
        int skip = dfs(day + 1, true, prices, memo);
        maxProfit = Math.max(buyStock, skip);
      } else {
        int sellStock = prices[day] + dfs(day + 1, true, prices, memo);
        int holdStock = dfs(day + 1, false, prices, memo);
        maxProfit = Math.max(sellStock, holdStock);
      }
      return memo[day][canBuyStock ? 1 : 0] = maxProfit;
    }

    public int maxProfitTab(int[] prices) {
      int n = prices.length;
      int[][] dp = new int[n + 1][2]; // dp[day][canBuy]

      // Loop in reverse (bottom-up)
      for (int day = n - 1; day >= 0; day--) {
        for (int canBuy = 0; canBuy <= 1; canBuy++) {
          if (canBuy == 1) {
            int buy = -prices[day] + dp[day + 1][0];
            int skip = dp[day + 1][1];
            dp[day][canBuy] = Math.max(buy, skip);
          } else {
            int sell = prices[day] + dp[day + 1][1];
            int hold = dp[day + 1][0];
            dp[day][canBuy] = Math.max(sell, hold);
          }
        }
      }

      return dp[0][1]; // Start at day 0 with permission to buy
    }

  }
}
