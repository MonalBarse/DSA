package com.monal;

import java.util.*;

/**
 * COMPREHENSIVE DYNAMIC PROGRAMMING GUIDE FOR DSA INTERVIEWS
 * ========================================================
 *
 * This guide covers all major DP patterns that appear in coding interviews and
 * OAs.
 * Each section includes:
 * - Problem description and intuition
 * - Step-by-step approach development
 * - Multiple solutions (recursive -> memoization -> tabulation)
 * - Time/Space complexity analysis
 * - Common pitfalls and optimization tips
 *
 * Author: Interview Preparation Guide
 * Target: DSA Interview and OA Success
 */

public class Revision {

    // ============================================================================
    // 1. ONE-DIMENSIONAL DYNAMIC PROGRAMMING (1D DP)
    // ============================================================================

    /**
     * PROBLEM: Climbing Stairs (Fibonacci Pattern)
     *
     * DESCRIPTION: You're climbing a staircase with n steps. You can climb either 1
     * or 2 steps at a time.
     * In how many distinct ways can you climb to the top?
     *
     * INTUITION:
     * - To reach step n, you can come from step (n-1) by taking 1 step, or from
     * step (n-2) by taking 2 steps
     * - This gives us the recurrence: ways(n) = ways(n-1) + ways(n-2)
     * - This is essentially the Fibonacci sequence!
     *
     * APPROACH DEVELOPMENT:
     * 1. Identify base cases: ways(0) = 1, ways(1) = 1
     * 2. Identify recurrence relation: ways(n) = ways(n-1) + ways(n-2)
     * 3. Optimize from recursion -> memoization -> tabulation -> space optimization
     */

    // Method 1: Pure Recursion (Exponential Time - DON'T USE IN INTERVIEWS)
    public int climbStairsRecursive(int n) {
        if (n <= 1)
            return 1;
        return climbStairsRecursive(n - 1) + climbStairsRecursive(n - 2);
        // Time: O(2^n), Space: O(n) - stack space
        // PITFALL: This will TLE for n > 30
    }

    // Method 2: Memoization (Top-Down DP)
    public int climbStairsMemo(int n) {
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return climbStairsHelper(n, memo);
    }

    private int climbStairsHelper(int n, int[] memo) {
        // Base cases
        if (n <= 1)
            return 1;

        // Check if already computed
        if (memo[n] != -1)
            return memo[n];

        // Compute and store result
        memo[n] = climbStairsHelper(n - 1, memo) + climbStairsHelper(n - 2, memo);
        return memo[n];
        // Time: O(n), Space: O(n)
    }

    // Method 3: Tabulation (Bottom-Up DP) - INTERVIEW PREFERRED
    public int climbStairsTabulation(int n) {
        if (n <= 1)
            return 1;

        int[] dp = new int[n + 1];
        dp[0] = 1; // Base case: 0 steps = 1 way
        dp[1] = 1; // Base case: 1 step = 1 way

        // Fill the dp array bottom-up
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
        // Time: O(n), Space: O(n)
    }

    // Method 4: Space Optimized (Best for interviews)
    public int climbStairsOptimized(int n) {
        if (n <= 1)
            return 1;

        int prev2 = 1, prev1 = 1;

        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
        // Time: O(n), Space: O(1) - OPTIMAL!
    }

    /**
     * PROBLEM: House Robber (Linear DP with Constraints)
     *
     * DESCRIPTION: You are a robber planning to rob houses along a street. Each
     * house has a certain amount of money.
     * Adjacent houses have security systems connected, so you cannot rob two
     * adjacent houses.
     * What is the maximum amount of money you can rob?
     *
     * INTUITION:
     * - For each house, you have two choices: rob it or skip it
     * - If you rob house i, you can't rob house i-1, so you get money[i] +
     * max_money_up_to(i-2)
     * - If you skip house i, you get max_money_up_to(i-1)
     * - Take the maximum of these two choices
     *
     * RECURRENCE: dp[i] = max(dp[i-1], dp[i-2] + nums[i])
     */

    public int rob(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        if (nums.length == 1)
            return nums[0];

        int n = nums.length;
        int[] dp = new int[n];

        // Base cases
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        // Fill dp array
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[n - 1];
        // Time: O(n), Space: O(n)
    }

    // Space optimized version
    public int robOptimized(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;
        if (nums.length == 1)
            return nums[0];

        int prev2 = nums[0];
        int prev1 = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            int current = Math.max(prev1, prev2 + nums[i]);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
        // Time: O(n), Space: O(1)
    }

    // ============================================================================
    // 2. TWO-DIMENSIONAL DP AND DP ON GRIDS
    // ============================================================================

    /**
     * PROBLEM: Unique Paths (2D Grid DP)
     *
     * DESCRIPTION: A robot is located at the top-left corner of an m x n grid.
     * The robot can only move either down or right at any point in time.
     * How many possible unique paths are there to reach the bottom-right corner?
     *
     * INTUITION:
     * - To reach any cell (i,j), robot must come from either (i-1,j) or (i,j-1)
     * - So paths to (i,j) = paths to (i-1,j) + paths to (i,j-1)
     * - Base cases: first row and first column all have 1 path
     *
     * APPROACH:
     * 1. Create 2D DP table
     * 2. Initialize first row and column with 1
     * 3. Fill remaining cells using recurrence relation
     */

    public int uniquePaths(int m, int n) {
        // Create DP table
        int[][] dp = new int[m][n];

        // Initialize first row and column
        for (int i = 0; i < m; i++)
            dp[i][0] = 1;
        for (int j = 0; j < n; j++)
            dp[0][j] = 1;

        // Fill the DP table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }

        return dp[m - 1][n - 1];
        // Time: O(m*n), Space: O(m*n)
    }

    // Space optimized version - only need previous row
    public int uniquePathsOptimized(int m, int n) {
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // First row is all 1s

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] = dp[j] + dp[j - 1]; // dp[j] represents dp[i-1][j], dp[j-1] represents dp[i][j-1]
            }
        }

        return dp[n - 1];
        // Time: O(m*n), Space: O(n)
    }

    /**
     * PROBLEM: Minimum Path Sum (2D Grid DP with Costs)
     *
     * DESCRIPTION: Given a grid filled with non-negative numbers, find a path from
     * top left to bottom right
     * which minimizes the sum of all numbers along its path. You can only move
     * right or down.
     *
     * INTUITION:
     * - Similar to unique paths, but now we're minimizing cost instead of counting
     * paths
     * - To reach (i,j), we take minimum of coming from (i-1,j) or (i,j-1) and add
     * current cell cost
     *
     * RECURRENCE: dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])
     */

    public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];

        // Initialize first cell
        dp[0][0] = grid[0][0];

        // Initialize first row
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        // Initialize first column
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        // Fill the DP table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i - 1][j], dp[i][j - 1]);
            }
        }

        return dp[m - 1][n - 1];
        // Time: O(m*n), Space: O(m*n)
    }

    // ============================================================================
    // 3. DP ON SUBSEQUENCES
    // ============================================================================

    /**
     * PROBLEM: Subset Sum (Classic Subsequence DP)
     *
     * DESCRIPTION: Given a set of non-negative integers and a value sum, determine
     * if there is a subset
     * of the given set with sum equal to given sum.
     *
     * INTUITION:
     * - For each element, we have two choices: include it or exclude it
     * - If we include nums[i], we need to find subset with sum (target - nums[i])
     * in remaining elements
     * - If we exclude nums[i], we need to find subset with sum target in remaining
     * elements
     *
     * APPROACH:
     * - Use 2D DP where dp[i][j] = can we make sum j using first i elements
     * - Base cases: dp[i][0] = true (empty subset), dp[0][j] = false (except j=0)
     * - Recurrence: dp[i][j] = dp[i-1][j] || (j >= nums[i-1] &&
     * dp[i-1][j-nums[i-1]])
     */

    public boolean canPartition(int[] nums, int target) {
        int n = nums.length;

        // DP table: dp[i][j] = can we make sum j using first i elements
        boolean[][] dp = new boolean[n + 1][target + 1];

        // Base case: empty subset can make sum 0
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        // Fill the DP table
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                // Don't include current element
                dp[i][j] = dp[i - 1][j];

                // Include current element if possible
                if (j >= nums[i - 1]) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - nums[i - 1]];
                }
            }
        }

        return dp[n][target];
        // Time: O(n*target), Space: O(n*target)
    }

    // Space optimized version
    public boolean canPartitionOptimized(int[] nums, int target) {
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;

        for (int num : nums) {
            // IMPORTANT: Traverse backwards to avoid using updated values
            for (int j = target; j >= num; j--) {
                dp[j] = dp[j] || dp[j - num];
            }
        }

        return dp[target];
        // Time: O(n*target), Space: O(target)
    }

    // ============================================================================
    // 4. DP ON STRINGS
    // ============================================================================

    /**
     * PROBLEM: Longest Common Subsequence (LCS) - Foundation of String DP
     *
     * DESCRIPTION: Given two strings text1 and text2, return the length of their
     * longest common subsequence.
     * A subsequence is a sequence that can be derived from another sequence by
     * deleting some or no elements
     * without changing the order of the remaining elements.
     *
     * INTUITION:
     * - If characters match: LCS(i,j) = 1 + LCS(i-1,j-1)
     * - If characters don't match: LCS(i,j) = max(LCS(i-1,j), LCS(i,j-1))
     * - We're trying all possibilities: skip char from text1, skip char from text2,
     * or match both
     *
     * APPROACH:
     * - Use 2D DP where dp[i][j] = LCS length for text1[0..i-1] and text2[0..j-1]
     * - Base cases: dp[i][0] = 0, dp[0][j] = 0 (empty string has LCS 0 with any
     * string)
     */

    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length(), n = text2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Base cases are already handled (0 initialization)

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    // Characters match - take diagonal + 1
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    // Characters don't match - take max of excluding one character
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
        // Time: O(m*n), Space: O(m*n)
    }

    /**
     * PROBLEM: Edit Distance (Levenshtein Distance)
     *
     * DESCRIPTION: Given two strings word1 and word2, return the minimum number of
     * operations required
     * to convert word1 to word2. You can insert, delete, or replace a character.
     *
     * INTUITION:
     * - If characters match: no operation needed, dp[i][j] = dp[i-1][j-1]
     * - If characters don't match, we have 3 options:
     * 1. Replace: dp[i-1][j-1] + 1
     * 2. Delete from word1: dp[i-1][j] + 1
     * 3. Insert into word1: dp[i][j-1] + 1
     * - Take minimum of these three options
     */

    public int minDistance(String word1, String word2) {
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m + 1][n + 1];

        // Base cases: converting empty string to/from another string
        for (int i = 0; i <= m; i++)
            dp[i][0] = i; // Delete all characters
        for (int j = 0; j <= n; j++)
            dp[0][j] = j; // Insert all characters

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No operation needed
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1], // Replace
                            Math.min(dp[i - 1][j], dp[i][j - 1]) // Delete or Insert
                    );
                }
            }
        }

        return dp[m][n];
        // Time: O(m*n), Space: O(m*n)
    }

    // ============================================================================
    // 5. DP ON STOCKS
    // ============================================================================

    /**
     * PROBLEM: Best Time to Buy and Sell Stock II (Unlimited Transactions)
     *
     * DESCRIPTION: You are given an array prices where prices[i] is the price of a
     * given stock on day i.
     * Find the maximum profit you can achieve. You may complete as many
     * transactions as you like
     * (buy and sell the stock multiple times).
     *
     * INTUITION:
     * - At each day, we can be in one of two states: holding a stock or not holding
     * a stock
     * - If holding: we can sell today or keep holding
     * - If not holding: we can buy today or stay without stock
     * - We want to maximize profit in both states
     *
     * STATE DEFINITION:
     * - hold[i] = max profit on day i if we hold a stock
     * - sold[i] = max profit on day i if we don't hold a stock
     *
     * TRANSITIONS:
     * - hold[i] = max(hold[i-1], sold[i-1] - prices[i])
     * - sold[i] = max(sold[i-1], hold[i-1] + prices[i])
     */

    public int maxProfitUnlimited(int[] prices) {
        int n = prices.length;
        if (n <= 1)
            return 0;

        // hold[i] = max profit on day i if holding stock
        // sold[i] = max profit on day i if not holding stock
        int[] hold = new int[n];
        int[] sold = new int[n];

        // Base case
        hold[0] = -prices[0]; // Buy on day 0
        sold[0] = 0; // Don't buy on day 0

        for (int i = 1; i < n; i++) {
            hold[i] = Math.max(hold[i - 1], sold[i - 1] - prices[i]);
            sold[i] = Math.max(sold[i - 1], hold[i - 1] + prices[i]);
        }

        return sold[n - 1]; // We should not hold stock at the end
        // Time: O(n), Space: O(n)
    }

    // Space optimized version
    public int maxProfitUnlimitedOptimized(int[] prices) {
        int hold = -prices[0]; // Max profit when holding stock
        int sold = 0; // Max profit when not holding stock

        for (int i = 1; i < prices.length; i++) {
            int prevHold = hold;
            hold = Math.max(hold, sold - prices[i]);
            sold = Math.max(sold, prevHold + prices[i]);
        }

        return sold;
        // Time: O(n), Space: O(1)
    }

    /**
     * PROBLEM: Best Time to Buy and Sell Stock with Cooldown
     *
     * DESCRIPTION: You are given an array prices where prices[i] is the price of a
     * given stock on day i.
     * Find the maximum profit you can achieve with the following restrictions:
     * - You may complete as many transactions as you like
     * - After you sell your stock, you cannot buy stock on next day (cooldown
     * period)
     *
     * INTUITION:
     * - Now we have 3 states: hold, sold (just sold), rest (can buy)
     * - After selling, we must rest for one day before buying again
     *
     * STATE TRANSITIONS:
     * - hold[i] = max(hold[i-1], rest[i-1] - prices[i])
     * - sold[i] = hold[i-1] + prices[i]
     * - rest[i] = max(rest[i-1], sold[i-1])
     */

    public int maxProfitWithCooldown(int[] prices) {
        int n = prices.length;
        if (n <= 1)
            return 0;

        int[] hold = new int[n]; // Max profit when holding stock
        int[] sold = new int[n]; // Max profit when just sold stock
        int[] rest = new int[n]; // Max profit when resting (can buy)

        // Base case
        hold[0] = -prices[0];
        sold[0] = 0;
        rest[0] = 0;

        for (int i = 1; i < n; i++) {
            hold[i] = Math.max(hold[i - 1], rest[i - 1] - prices[i]);
            sold[i] = hold[i - 1] + prices[i];
            rest[i] = Math.max(rest[i - 1], sold[i - 1]);
        }

        return Math.max(sold[n - 1], rest[n - 1]);
        // Time: O(n), Space: O(n)
    }

    // ============================================================================
    // 6. DP ON LONGEST INCREASING SUBSEQUENCE (LIS)
    // ============================================================================

    /**
     * PROBLEM: Longest Increasing Subsequence
     *
     * DESCRIPTION: Given an integer array nums, return the length of the longest
     * strictly increasing subsequence.
     *
     * INTUITION:
     * - For each element, we can either start a new subsequence or extend an
     * existing one
     * - dp[i] = length of LIS ending at index i
     * - For each i, check all previous elements j where nums[j] < nums[i]
     * - dp[i] = max(dp[i], dp[j] + 1) for all valid j
     *
     * APPROACH 1: O(n²) DP Solution
     */

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Each element forms a subsequence of length 1

        int maxLength = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLength = Math.max(maxLength, dp[i]);
        }

        return maxLength;
        // Time: O(n²), Space: O(n)
    }

    /**
     * OPTIMIZED APPROACH: Binary Search + Patience Sorting
     *
     * INTUITION:
     * - Maintain an array 'tails' where tails[i] is the smallest tail of all
     * increasing subsequences of length i+1
     * - For each number, find the position to place it using binary search
     * - This doesn't give us the actual LIS, but the correct length
     */

    public int lengthOfLISOptimized(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;

        for (int num : nums) {
            int left = 0, right = size;

            // Binary search for the position to insert/replace
            while (left < right) {
                int mid = (left + right) / 2;
                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }

            tails[left] = num;
            if (left == size)
                size++; // We extended the sequence
        }

        return size;
        // Time: O(n log n), Space: O(n)
    }

    // ============================================================================
    // 7. MATRIX CHAIN MULTIPLICATION (MCM) / PARTITION DP
    // ============================================================================

    /**
     * PROBLEM: Matrix Chain Multiplication
     *
     * DESCRIPTION: Given a chain of matrices A1, A2, ..., An, find the minimum
     * number of scalar multiplications
     * needed to compute the product A1 × A2 × ... × An.
     *
     * INTUITION:
     * - We need to find the optimal way to parenthesize the matrix chain
     * - For matrices from i to j, we try all possible split points k
     * - Cost = cost(i,k) + cost(k+1,j) + cost of multiplying two resulting matrices
     *
     * APPROACH:
     * - Use 2D DP where dp[i][j] = minimum cost to multiply matrices from i to j
     * - Try all possible partitions and take minimum
     *
     * NOTE: p[i] represents the dimension, where matrix i has dimensions p[i-1] ×
     * p[i]
     */

    public int matrixChainOrder(int[] p) {
        int n = p.length - 1; // Number of matrices
        int[][] dp = new int[n][n];

        // l is chain length
        for (int l = 2; l <= n; l++) {
            for (int i = 0; i < n - l + 1; i++) {
                int j = i + l - 1;
                dp[i][j] = Integer.MAX_VALUE;

                // Try all possible split points
                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j] + p[i] * p[k + 1] * p[j + 1];
                    dp[i][j] = Math.min(dp[i][j], cost);
                }
            }
        }

        return dp[0][n - 1];
        // Time: O(n³), Space: O(n²)
    }

    /**
     * PROBLEM: Palindrome Partitioning II
     *
     * DESCRIPTION: Given a string s, partition s such that every substring of the
     * partition is a palindrome.
     * Return the minimum cuts needed for a palindrome partitioning of s.
     *
     * INTUITION:
     * - Use MCM pattern: for each substring, try all possible cuts
     * - If substring is already palindrome, no cut needed
     * - Otherwise, try all cuts and take minimum
     *
     * OPTIMIZATION: Pre-compute palindrome information to avoid repeated checks
     */

    public int minCut(String s) {
        int n = s.length();

        // Pre-compute palindrome information
        boolean[][] isPalindrome = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(i) == s.charAt(j) && (i - j < 2 || isPalindrome[j + 1][i - 1])) {
                    isPalindrome[j][i] = true;
                }
            }
        }

        // DP for minimum cuts
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            if (isPalindrome[0][i]) {
                dp[i] = 0; // Entire substring is palindrome
            } else {
                dp[i] = i; // Worst case: cut after every character
                for (int j = 1; j <= i; j++) {
                    if (isPalindrome[j][i]) {
                        dp[i] = Math.min(dp[i], dp[j - 1] + 1);
                    }
                }
            }
        }

        return dp[n - 1];
        // Time: O(n²), Space: O(n²)
    }

    // ============================================================================
    // 8. DP ON SQUARES
    // ============================================================================

    /**
     * PROBLEM: Maximal Square
     *
     * DESCRIPTION: Given an m x n binary matrix filled with 0's and 1's, find the
     * largest square containing
     * only 1's and return its area.
     *
     * INTUITION:
     * - For each cell (i,j), if it's 1, it can potentially be part of a square
     * - dp[i][j] = side length of largest square with bottom-right corner at (i,j)
     * - If current cell is 1: dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])
     * + 1
     * - The minimum ensures we can form a square (all three directions must have
     * squares)
     *
     * APPROACH:
     * - Build DP table where dp[i][j] represents max square side length ending at
     * (i,j)
     * - Track maximum side length seen so far
     * - Return square of maximum side length (area)
     */

    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return 0;

        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        int maxSide = 0;

        // Fill first row and column
        for (int i = 0; i < m; i++) {
            dp[i][0] = matrix[i][0] - '0';
            maxSide = Math.max(maxSide, dp[i][0]);
        }

        for (int j = 0; j < n; j++) {
            dp[0][j] = matrix[0][j] - '0';
            maxSide = Math.max(maxSide, dp[0][j]);
        }

        // Fill the rest of the DP table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
                // If matrix[i][j] == '0', dp[i][j] remains 0 (default)
            }
        }

        return maxSide * maxSide; // Return area
        // Time: O(m*n), Space: O(m*n)
    }

    // Space optimized version - only need previous row
    public int maximalSquareOptimized(char[][] matrix) {
        if (matrix == null || matrix.length == 0)
            return 0;

        int m = matrix.length, n = matrix[0].length;
        int[] dp = new int[n];
        int maxSide = 0, prev = 0;

        // Initialize first row
        for (int j = 0; j < n; j++) {
            dp[j] = matrix[0][j] - '0';
            maxSide = Math.max(maxSide, dp[j]);
        }

        // Process remaining rows
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int temp = dp[j];
                if (j == 0) {
                    dp[j] = matrix[i][j] - '0';
                } else if (matrix[i][j] == '1') {
                    dp[j] = Math.min(dp[j], Math.min(dp[j - 1], prev)) + 1;
                } else {
                    dp[j] = 0;
                }
                prev = temp;
                maxSide = Math.max(maxSide, dp[j]);
            }
        }

        return maxSide * maxSide;
        // Time: O(m*n), Space: O(n)
    }

    /**
     * PROBLEM: Count Square Submatrices with All Ones
     *
     * DESCRIPTION: Given a m * n matrix of ones and zeros, return how many square
     * submatrices have all ones.
     *
     * INTUITION:
     * - Similar to maximal square, but instead of finding the largest, we count all
     * squares
     * - dp[i][j] = number of squares with bottom-right corner at (i,j)
     * - If dp[i][j] = k, it means there are k squares ending at (i,j): sizes 1x1,
     * 2x2, ..., kxk
     * - Total count = sum of all dp[i][j] values
     *
     * KEY INSIGHT: dp[i][j] also represents the side length of the largest square
     * ending at (i,j)
     * AND the count of squares ending at (i,j)
     */

    public int countSquares(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        int[][] dp = new int[m][n];
        int totalSquares = 0;

        // Fill first row and column
        for (int i = 0; i < m; i++) {
            dp[i][0] = matrix[i][0];
            totalSquares += dp[i][0];
        }

        for (int j = 1; j < n; j++) {
            dp[0][j] = matrix[0][j];
            totalSquares += dp[0][j];
        }

        // Fill the rest of the DP table
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 1) {
                    dp[i][j] = Math.min(dp[i - 1][j], Math.min(dp[i][j - 1], dp[i - 1][j - 1])) + 1;
                }
                totalSquares += dp[i][j];
            }
        }

        return totalSquares;
        // Time: O(m*n), Space: O(m*n)
    }

    // ============================================================================
    // BONUS: ADVANCED DP PATTERNS AND INTERVIEW TIPS
    // ============================================================================

    /**
     * PROBLEM: Coin Change (Unbounded Knapsack)
     *
     * DESCRIPTION: You are given coins of different denominations and a total
     * amount of money.
     * Write a function to compute the fewest number of coins that you need to make
     * up that amount.
     *
     * INTUITION:
     * - This is unbounded knapsack: we can use each coin unlimited times
     * - For each amount, try using each coin and take minimum
     * - dp[i] = minimum coins needed to make amount i
     *
     * RECURRENCE: dp[i] = min(dp[i], dp[i-coin] + 1) for all coins <= i
     */

    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // Initialize with impossible value
        dp[0] = 0; // Base case: 0 coins needed for amount 0

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
        // Time: O(amount * coins.length), Space: O(amount)
    }

    /**
     * PROBLEM: Word Break (String DP)
     *
     * DESCRIPTION: Given a string s and a dictionary of strings wordDict, return
     * true if s can be
     * segmented into a space-separated sequence of dictionary words.
     *
     * INTUITION:
     * - For each position i, check if we can break the string up to position i
     * - dp[i] = true if string s[0...i-1] can be segmented
     * - For each j < i, if dp[j] is true and s[j...i-1] is in dictionary, then
     * dp[i] = true
     */

    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> wordSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true; // Empty string can always be segmented

        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break; // Found one valid segmentation
                }
            }
        }

        return dp[s.length()];
        // Time: O(n² * m) where n = s.length(), m = average word length
        // Space: O(n + dictionary size)
    }

    // ============================================================================
    // INTERVIEW TIPS AND BEST PRACTICES
    // ============================================================================

    /**
     * DYNAMIC PROGRAMMING INTERVIEW STRATEGY:
     *
     * 1. PROBLEM IDENTIFICATION:
     * - Look for keywords: "optimal", "minimum/maximum", "count ways",
     * "possible/impossible"
     * - Overlapping subproblems: same subproblems appear multiple times
     * - Optimal substructure: optimal solution contains optimal solutions to
     * subproblems
     *
     * 2. APPROACH DEVELOPMENT:
     * Step 1: Define state clearly
     * Step 2: Identify state transitions (recurrence relation)
     * Step 3: Determine base cases
     * Step 4: Decide iteration order
     * Step 5: Optimize space if needed
     *
     * 3. COMMON PATTERNS:
     * - Linear DP: dp[i] depends on dp[i-1], dp[i-2], etc.
     * - Grid DP: dp[i][j] depends on dp[i-1][j], dp[i][j-1], etc.
     * - Interval DP: dp[i][j] depends on dp[i][k] and dp[k+1][j]
     * - Tree DP: state depends on children states
     *
     * 4. OPTIMIZATION TECHNIQUES:
     * - Space optimization: if dp[i] only depends on dp[i-1], use O(1) space
     * - Rolling array: if dp[i] depends on dp[i-1] and dp[i-2], use array of size 2
     * - Memoization vs Tabulation: choose based on problem constraints
     *
     * 5. COMMON MISTAKES TO AVOID:
     * - Off-by-one errors in array indexing
     * - Wrong initialization of base cases
     * - Incorrect iteration order
     * - Not handling edge cases (empty input, single element)
     * - Integer overflow in large problems
     *
     * 6. TIME AND SPACE COMPLEXITY:
     * - Time: Usually O(states * transitions)
     * - Space: O(states) for tabulation, O(states) for memoization + recursion
     * stack
     * - Always consider space optimization opportunities
     *
     * 7. IMPLEMENTATION TIPS:
     * - Start with brute force recursive solution
     * - Add memoization to make it efficient
     * - Convert to iterative (tabulation) if needed
     * - Optimize space in final step
     * - Test with edge cases and provided examples
     */

    // ============================================================================
    // PRACTICE PROBLEMS BY DIFFICULTY
    // ============================================================================

    /**
     * EASY PROBLEMS TO START WITH:
     * 1. Climbing Stairs (Fibonacci pattern)
     * 2. House Robber (Linear DP with constraint)
     * 3. Maximum Subarray (Kadane's algorithm)
     * 4. Best Time to Buy and Sell Stock (Single transaction)
     * 5. Range Sum Query - Immutable (Prefix sum)
     *
     * MEDIUM PROBLEMS:
     * 1. Coin Change (Unbounded knapsack)
     * 2. Longest Common Subsequence (2D DP)
     * 3. Unique Paths (Grid DP)
     * 4. House Robber II (Circular array)
     * 5. Word Break (String DP)
     * 6. Longest Increasing Subsequence (LIS)
     * 7. Edit Distance (String DP)
     * 8. Maximal Square (2D DP)
     *
     * HARD PROBLEMS:
     * 1. Best Time to Buy and Sell Stock with Cooldown (State machine DP)
     * 2. Palindrome Partitioning II (MCM pattern)
     * 3. Longest Valid Parentheses (String DP)
     * 4. Dungeon Game (2D DP with constraints)
     * 5. Burst Balloons (Interval DP)
     * 6. Regular Expression Matching (String DP)
     * 7. Interleaving String (2D string DP)
     * 8. Distinct Subsequences (2D DP)
     */

    // ============================================================================
    // MAIN METHOD FOR TESTING
    // ============================================================================

    public static void main(String[] args) {
        Revision dp = new Revision();

        // Test 1D DP
        System.out.println("=== 1D DP Tests ===");
        System.out.println("Climbing Stairs (5): " + dp.climbStairsOptimized(5));
        System.out.println("House Robber [2,7,9,3,1]: " + dp.robOptimized(new int[] { 2, 7, 9, 3, 1 }));

        // Test 2D DP
        System.out.println("\n=== 2D DP Tests ===");
        System.out.println("Unique Paths (3x7): " + dp.uniquePathsOptimized(3, 7));
        System.out.println("Min Path Sum [[1,3,1],[1,5,1],[4,2,1]]: " +
                dp.minPathSum(new int[][] { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } }));

        // Test String DP
        System.out.println("\n=== String DP Tests ===");
        System.out.println("LCS 'abcde' and 'ace': " + dp.longestCommonSubsequence("abcde", "ace"));
        System.out.println("Edit Distance 'horse' and 'ros': " + dp.minDistance("horse", "ros"));

        // Test LIS
        System.out.println("\n=== LIS Tests ===");
        System.out.println(
                "LIS [10,9,2,5,3,7,101,18]: " + dp.lengthOfLISOptimized(new int[] { 10, 9, 2, 5, 3, 7, 101, 18 }));

        // Test Stock Problems
        System.out.println("\n=== Stock DP Tests ===");
        System.out.println("Max Profit Unlimited [7,1,5,3,6,4]: "
                + dp.maxProfitUnlimitedOptimized(new int[] { 7, 1, 5, 3, 6, 4 }));
        System.out.println(
                "Max Profit with Cooldown [1,2,3,0,2]: " + dp.maxProfitWithCooldown(new int[] { 1, 2, 3, 0, 2 }));

        // Test Square Problems
        System.out.println("\n=== Square DP Tests ===");
        char[][] matrix = { { '1', '0', '1', '0', '0' }, { '1', '0', '1', '1', '1' }, { '1', '1', '1', '1', '1' },
                { '1', '0', '0', '1', '0' } };
        System.out.println("Maximal Square: " + dp.maximalSquareOptimized(matrix));

        // Test Advanced Problems
        System.out.println("\n=== Advanced DP Tests ===");
        System.out.println("Coin Change [1,3,4] amount 6: " + dp.coinChange(new int[] { 1, 3, 4 }, 6));
        System.out.println("Word Break 'leetcode' ['leet','code']: " +
                dp.wordBreak("leetcode", Arrays.asList("leet", "code")));

        System.out.println("\n=== All Tests Completed ===");
    }
}