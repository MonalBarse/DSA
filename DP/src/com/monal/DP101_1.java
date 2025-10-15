package DP.src.com.monal;

/**
 * Topics covered:
 * 1. Simple recursion
 * 2. Recursion with memoization (top-down DP)
 * 3. Tabulation (bottom-up DP)
 * 4. Space optimization techniques
 *
 * Example problems:
 * - Fibonacci sequence
 * - Climbing stairs problem
 * - Knapsack problem
 * - Longest common subsequence
 */

public class DP101_1 {

    // ====================== FIBONACCI SEQUENCE =====================//

    /**
     * Simple recursive implementation of Fibonacci
     * Time Complexity: O(2^n) - exponential
     * Space Complexity: O(n) - recursion stack
     */
    public int fibRecursive(int n) {
        // Base cases
        if (n <= 1)
            return n;

        // Recursive case: fib(n) = fib(n-1) + fib(n-2)
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }

    /**
     * Memoized (top-down) implementation of Fibonacci
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int fibMemoized(int n) {
        // Create memoization array
        Integer[] memo = new Integer[n + 1];
        return fibMemoizedHelper(n, memo);
    }

    private int fibMemoizedHelper(int n, Integer[] memo) {
        // Base cases
        if (n <= 1)
            return n;

        // Check if already computed
        if (memo[n] != null)
            return memo[n];

        // Compute and store result
        memo[n] = fibMemoizedHelper(n - 1, memo) + fibMemoizedHelper(n - 2, memo);
        return memo[n];
    }

    /**
     * Tabulated (bottom-up) implementation of Fibonacci
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int fibTabulated(int n) {
        // Handle base cases
        if (n <= 1)
            return n;

        // Create and initialize table
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        // Fill the table
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * Space-optimized implementation of Fibonacci
     * Time Complexity: O(n)
     * Space Complexity: O(1)
     */
    public int fibOptimized(int n) {
        // Handle base cases
        if (n <= 1)
            return n;

        // Use only variables instead of an array
        int prev2 = 0;
        int prev1 = 1;
        int current = 0;

        for (int i = 2; i <= n; i++) {
            current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }

        return current;
    }

    // ====================== CLIMBING STAIRS PROBLEM =====================//

    /**
     * Recursive implementation of the climbing stairs problem
     * You can climb 1 or 2 steps at a time. How many ways to reach the top?
     * Time Complexity: O(2^n)
     * Space Complexity: O(n) - recursion stack
     */
    public int climbStairsRecursive(int n) {
        // Base cases
        if (n <= 0)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        // Recursive case: ways(n) = ways(n-1) + ways(n-2)
        return climbStairsRecursive(n - 1) + climbStairsRecursive(n - 2);
    }

    /**
     * Memoized implementation of the climbing stairs problem
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int climbStairsMemoized(int n) {
        Integer[] memo = new Integer[n + 1];
        return climbStairsMemoizedHelper(n, memo);
    }

    private int climbStairsMemoizedHelper(int n, Integer[] memo) {
        // Base cases
        if (n <= 0)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        // Check if already computed
        if (memo[n] != null)
            return memo[n];

        // Compute and store result
        memo[n] = climbStairsMemoizedHelper(n - 1, memo) + climbStairsMemoizedHelper(n - 2, memo);
        return memo[n];
    }

    /**
     * Tabulated implementation of the climbing stairs problem
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public int climbStairsTabulated(int n) {
        // Handle base cases
        if (n <= 0)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        // Create and initialize table
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        // Fill the table
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    // SPace optimized
    public int climbStairsSpaceOpt(int n) {
        if (n <= 2)
            return n;

        int first = 1;
        int second = 2;

        for (int i = 3; i <= n; i++) {
            int current = first + second;
            first = second;
            second = current;
        }

        return second;
    }

    // Minimum Cost to Climb Stairs

    // Recursive MEMO implementation
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        Integer[] memo = new Integer[n + 1]; // memo[i] represents the minimum cost to reach step i
        return minCostClimbingStairsHelper(cost, n, memo);
    }

    private int minCostClimbingStairsHelper(int[] cost, int n, Integer[] memo) {
        // Base cases
        if (n <= 1)
            return 0;
        if (n == 2)
            return Math.min(cost[0], cost[1]);

        if (memo[n] != null)
            return memo[n];
        // min cost to reach step n is the cost of step n-1 or n-2
        memo[n] = Math.min(
                minCostClimbingStairsHelper(cost, n - 1, memo) + cost[n - 1],
                minCostClimbingStairsHelper(cost, n - 2, memo) + cost[n - 2]);
        return memo[n];
    }

    // Tabulated implementation
    public int minCostClimbingStairsTabulated(int[] cost) {
        // Build an array dp where dp[i] is the minimum cost to climb to the top
        // starting from the ith staircase.
        // Hint 2
        // Assuming we have n staircase labeled from 0 to n - 1 and assuming the top is
        // n, then dp[n] = 0, marking that if you are at the top, the cost is 0.
        // Hint 3
        // Now, looping from n - 1 to 0, the dp[i] = cost[i] + min(dp[i + 1], dp[i +
        // 2]). The answer will be the minimum of dp[0] and dp[1]

        int n = cost.length;
        int[] dp = new int[n + 1];
        dp[n] = 0; // Cost to reach the top from the top is 0
        dp[n - 1] = cost[n - 1]; // Cost to reach the top from the last step is just the cost of that step
        for (int i = n - 2; i >= 0; i--) {
            dp[i] = cost[i] + Math.min(dp[i + 1], dp[i + 2]);
        }
        // The answer is the minimum cost to reach the top from either step 0 or step 1
        return Math.min(dp[0], dp[1]);

    }

    // DIVISER GAME
    // Alice and bob palys a game, alice starting first,
    // they take turns choosing a number x such that 1 <= x < n and n % x == 0
    // A player loses if they cannot choose a number x, if they cannot make a move
    // they lose
    // Given an integer n, return true if alice wins the game, otherwise return
    // false

    public boolean divisorGame(int n) {
        // Alice wins if n is even, otherwise Bob wins
        return n % 2 == 0;
    }

    // solving it with dp
    public boolean divisorGameDP(int n) {
        // Create a DP array where dp[i] is true if Alice can win with n = i
        boolean[] dp = new boolean[n + 1];
        dp[0] = false; // Base case: if n = 0, Alice cannot make a move
        dp[1] = false; // Base case: if n = 1, Alice cannot make a move

        for (int i = 2; i <= n; i++) {
            for (int x = 1; x < i; x++) {
                if (i % x == 0 && !dp[i - x]) { // dp[i]
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    // ====================== 0/1 KNAPSACK PROBLEM =====================//

    /**
     * Recursive implementation of the 0/1 Knapsack problem
     * Time Complexity: O(2^n)
     * Space Complexity: O(n) - recursion stack
     */
    /*
     * Knapsack problem: Given weights and values of n items, put these items
     * in a knapsack of capacity W to get the maximum total value in the knapsack.
     * You cannot break an item, either pick it or not.
     *
     * Example:
     * weights = [2,3,5,12,23,30] values = [220, 320, 121, 240, 200, 100]
     * capacity = 49
     *
     * Output: 620
     */
    public int knapsackRecursive(int[] weights, int[] values, int capacity, int n) {
        return helper_fn1(weights, values, capacity, 0, n);
    }

    private int helper_fn1(int[] W, int[] V, int capacity, int idx, int n) {
        // Base cases
        if (idx == n || capacity == 0)
            return 0; // No more value can be obtained

        // If current item is too heavy, skip it
        if (W[idx] > capacity)
            return helper_fn1(W, V, capacity, idx + 1, n);

        // Try including the current item
        int include = V[idx] + helper_fn1(W, V, capacity - W[idx], idx + 1, n);

        // Try excluding the current item
        int exclude = helper_fn1(W, V, capacity, idx + 1, n);

        // Return the better option
        return Math.max(include, exclude);
    }

    /**
     * Memoized implementation of the 0/1 Knapsack problem
     * Time Complexity: O(n * capacity)
     * Space Complexity: O(n * capacity)
     */
    public int knapsackMemoized(int[] weights, int[] values, int capacity, int n) {
        // Create memoization table
        Integer[][] memo = new Integer[n + 1][capacity + 1];
        return knapsackMemoizedHelper(weights, values, capacity, n, memo);
    }

    private int knapsackMemoizedHelper(int[] weights, int[] values, int capacity, int n, Integer[][] memo) {
        // Base case
        if (n == 0 || capacity == 0)
            return 0;

        // Check if already computed
        if (memo[n][capacity] != null)
            return memo[n][capacity];

        // If weight of nth item is greater than capacity, skip it
        if (weights[n - 1] > capacity) {
            memo[n][capacity] = knapsackMemoizedHelper(weights, values, capacity, n - 1, memo);
            return memo[n][capacity];
        }

        int include = values[n - 1] + knapsackMemoizedHelper(weights, values, capacity - weights[n - 1], n - 1, memo);
        int exclude = knapsackMemoizedHelper(weights, values, capacity, n - 1, memo);
        // Store the result of max value
        memo[n][capacity] = Math.max(include, exclude);

        return memo[n][capacity];
    }

    /**
     * Tabulated implementation of the 0/1 Knapsack problem
     * Time Complexity: O(n * capacity)
     * Space Complexity: O(n * capacity)
     */
    public int knapsackBottomUp(int[] weights, int[] values, int capacity, int n) {
        // Create DP table of size [n+1][capacity+1]
        int[][] dp = new int[n + 1][capacity + 1];

        // Fill the DP table bottom-up
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                // Base case: no items or no capacity
                if (i == 0 || w == 0)
                    dp[i][w] = 0;
                // If current item's weight can fit in the knapsack
                else if (weights[i - 1] <= w) {
                    // Max of: (1) including current item, (2) excluding current item
                    dp[i][w] = Math.max(
                            values[i - 1] + dp[i - 1][w - weights[i - 1]], // Include item
                            dp[i - 1][w] // Exclude item
                    );
                }
                // If current item is too heavy, skip it
                else dp[i][w] = dp[i - 1][w];
            }
        }
        // Return the maximum value that can be obtained
        return dp[n][capacity];
    }

    // ====================== LONGEST COMMON SUBSEQUENCE =====================//

    /**
     * Recursive implementation of the Longest Common Subsequence (LCS) problem
     * Time Complexity: O(2^(m+n))
     * Space Complexity: O(m+n) - recursion stack
     */

    /*
     * LCS problem: Given two strings, find the length of the longest subsequence
     * present in both of them.
     * Example 1:
     * text1 = "ABCXDEFYHIJZKFLMGNOPQRSTUVWXYZ"
     * text2 = "ABCXDEFYHIJZKFLMGNOPQRSTUVWXYZ"
     * Output: 26
     *
     * Example 2:
     * text1 = "abcdetuvwxyz"
     * text2 = "abcdefghijkl"
     *
     * Output: 5
     * Explanation: The longest common subsequence is "abcde".
     */

    public int lcsRecursive(String text1, String text2, int m, int n) {
        // Base case
        if (m == 0 || n == 0)
            return 0;

        // If last characters match
        if (text1.charAt(m - 1) == text2.charAt(n - 1)) {
            return 1 + lcsRecursive(text1, text2, m - 1, n - 1);
        }

        // If last characters don't match
        return Math.max(
                lcsRecursive(text1, text2, m - 1, n),
                lcsRecursive(text1, text2, m, n - 1));
    }

    /**
     * Memoized implementation of the LCS problem
     * Time Complexity: O(m * n)
     * Space Complexity: O(m * n)
     */
    public int lcsMemoized(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        Integer[][] memo = new Integer[m + 1][n + 1];
        return lcsMemoizedHelper(text1, text2, m, n, memo);
    }

    private int lcsMemoizedHelper(String text1, String text2, int m, int n, Integer[][] memo) {
        // Base case
        if (m == 0 || n == 0)
            return 0;

        // Check if already computed
        if (memo[m][n] != null)
            return memo[m][n];

        // If last characters match
        if (text1.charAt(m - 1) == text2.charAt(n - 1)) {
            memo[m][n] = 1 + lcsMemoizedHelper(text1, text2, m - 1, n - 1, memo);
        } else {
            // If last characters don't match
            memo[m][n] = Math.max(
                    lcsMemoizedHelper(text1, text2, m - 1, n, memo),
                    lcsMemoizedHelper(text1, text2, m, n - 1, memo));
        }

        return memo[m][n];
    }

    /**
     * Tabulated implementation of the LCS problem
     * Time Complexity: O(m * n)
     * Space Complexity: O(m * n)
     */

    public int lcsTabulated(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();

        // Create and initialize table
        int[][] dp = new int[m + 1][n + 1];

        // Fill the table
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                // Base case
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                }
                // If characters match
                else if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                }
                // If characters don't match
                else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[m][n];
    }

    public static void main(String[] args) {
        DP101_1 dp = new DP101_1();

        System.out.println("========== FIBONACCI SEQUENCE ==========");
        int n = 30;

        // 1. Simple recursion
        long startTime = System.nanoTime();
        System.out.println("Recursive Fibonacci of " + n + ": " + dp.fibRecursive(n));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        // 2. Memoization (top-down DP)
        startTime = System.nanoTime();
        System.out.println("Memoized Fibonacci of " + n + ": " + dp.fibMemoized(n));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        // 3. Tabulation (bottom-up DP)
        startTime = System.nanoTime();
        System.out.println("Tabulated Fibonacci of " + n + ": " + dp.fibTabulated(n));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        // 4. Space optimization
        startTime = System.nanoTime();
        System.out.println("Space-optimized Fibonacci of " + n + ": " + dp.fibOptimized(n));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        System.out.println("========== CLIMBING STAIRS PROBLEM ==========");
        int stairs = 30;

        startTime = System.nanoTime();
        System.out.println("Ways to climb " + stairs + " stairs (recursive): " + dp.climbStairsRecursive(stairs));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        startTime = System.nanoTime();
        System.out.println("Ways to climb " + stairs + " stairs (memoized): " + dp.climbStairsMemoized(stairs));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        startTime = System.nanoTime();
        System.out.println("Ways to climb " + stairs + " stairs (tabulated): " + dp.climbStairsTabulated(stairs));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        System.out.println("========== 0/1 KNAPSACK PROBLEM ==========");
        int[] weights = { 10, 30, 20, 50, 40 };
        int[] values = { 60, 100, 120, 240, 200 };
        int capacity = 50;

        startTime = System.nanoTime();
        System.out.println(
                "Maximum value (recursive): " + dp.knapsackRecursive(weights, values, capacity, values.length));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        startTime = System.nanoTime();
        System.out
                .println("Maximum value (memoized): " + dp.knapsackMemoized(weights, values, capacity, values.length));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        startTime = System.nanoTime();
        System.out.println(
                "Maximum value (tabulated): " + dp.knapsackBottomUp(weights, values, capacity, values.length));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        System.out.println("========== LONGEST COMMON SUBSEQUENCE ==========");
        String text1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String text2 = "ABCXDEFYHIJZKFLMGNOPQRSTUVWXYZ";

        startTime = System.nanoTime();
        System.out.println("LCS length (recursive): " + dp.lcsRecursive(text1, text2, text1.length(), text2.length()));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        startTime = System.nanoTime();
        System.out.println("LCS length (memoized): " + dp.lcsMemoized(text1, text2));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        startTime = System.nanoTime();
        System.out.println("LCS length (tabulated): " + dp.lcsTabulated(text1, text2));
        System.out.println("Time taken: " + (System.nanoTime() - startTime) / 1000000 + " ms\n");

        System.out.println("========== DP PATTERNS SUMMARY ==========");
        System.out.println("1. Identify if problem has optimal substructure and overlapping subproblems");
        System.out.println("2. Define the state clearly (what each position in your array/table represents)");
        System.out.println("3. Establish the recurrence relation (how states relate to each other)");
        System.out.println("4. Identify base cases");
        System.out
                .println("5. Decide implementation approach (recursive with memoization or iterative with tabulation)");
        System.out.println("6. Consider space optimizations if applicable");
    }
}
