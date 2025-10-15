package com.monal.DP.DP_Knapsack;

public class P014 {
    private int goodSubset(int n) {
        int dp[] = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j * j <= n; j++) {
                if (i % j == 0) {
                    if (j < i)
                        dp[i] += dp[j];
                    int k = i / j; // fill from lower divisor
                    if (k < i)
                        dp[i] += dp[k];
                }
            }
        }
        return dp[n];
    }

}

class Solution_Tab {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n + 2];
        arr[0] = 1;
        arr[n + 1] = 1;
        System.arraycopy(nums, 0, arr, 1, n);
        int[][] dp = new int[n + 2][n + 2]; // dp[i][j] = max coins from (i,j) exclusive

        for (int i = 1; i < n + 1; i++) { // length of the window
            for (int left = 0; left <= n - i; left++) { // left boundary
                int right = left + i + 1;
                int product = arr[left] * arr[right];
                int maxCoins = 0;
                for (int k = left + 1; k < right; k++) // burst k last in (left, right)
                    maxCoins = Math.max(maxCoins, product * arr[k] + dp[left][k] + dp[k][right]);
                dp[left][right] = maxCoins;
            }
        }
        return dp[0][n + 1];
    }
}


class Solution {
    public int[] maxValue(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n + 1];  // prefix maximum
        int[] suffix = new int[n + 1];  // suffix minimum
        int[] result = new int[n];
        int index = 0, max = 0;

        // Initialize suffix with MAX_VALUE
        for (int i = 0; i <= n; i++) suffix[i] = Integer.MAX_VALUE;

        // Build prefix maximum array
        for (int i = 0; i < n; i++) prefix[i + 1] = Math.max(prefix[i], nums[i]);

        // Build suffix minimum array
        for (int i = n - 1; i >= 0; i--) suffix[i] = Math.min(suffix[i + 1], nums[i]);

        // Process segments where elements can reach each other
        for (int i = 0; i < n - 1; i++) {
            max = Math.max(max, nums[i]);
            // If max of left part <= min of right part, they form separate components
            if (prefix[i + 1] <= suffix[i + 1]) {
                // All elements in current segment can reach the max value
                for (int j = index; j <= i; j++) result[j] = max;
                max = nums[index = i + 1]; // Start new segment
            }
        }

        // Handle the last segment
        for (int i = index; i < n; i++) max = Math.max(max, nums[i]);
        for (int i = index; i < n; i++) result[i] = max;
        return result;
    }
}
