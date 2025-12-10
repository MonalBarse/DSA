package DP.src.com.monal;

import java.util.Arrays;

/**
 * Advanced Dynamic Programming: Knapsack Variants
 *
 * <p>This file contains implementations of several knapsack-related DP problems: 1. Subset Sum
 * Problem 2. Equal Sum Partition Problem 3. Count of Subsets with Given Sum 4. Minimum Subset Sum
 * Difference 5. Count of Subsets with Given Difference 6. Target Sum Problem 7. Unbounded Knapsack
 * 8. Rod Cutting Problem
 */
public class KnapsackVariants {

  /*********************************
   * PROBLEM 1: SUBSET SUM PROBLEM *
   *********************************/

  /**
   * Problem: Given an array of non-negative integers and a target sum, determine if there exists a
   * subset of the array whose sum equals the target sum.
   *
   * <p>Recursive approach with memoization (Top-down)
   *
   * <p>SIMILARITY TO 0/1 KNAPSACK: Simplified Knapsack where all item values = weights Instead of
   * maximizing value, we're checking if we can exactly achieve a target sum Same decision for each
   * item: include it or exclude it (0/1 property)
   */
  public static boolean subsetSumRecursive(int[] arr, int sum) {
    // Initialize memoization array with null values (to distinguish unprocessed
    // states)
    Boolean[][] memo = new Boolean[arr.length + 1][sum + 1];
    return subsetSumMemo(arr, arr.length, sum, memo);
  }

  private static boolean subsetSumMemo(int[] arr, int n, int target, Boolean[][] memo) {
    // Base cases
    if (target == 0) return true; // Empty subset can always form sum 0

    if (n == 0 || target < 0) return false; // No elements left or sum became negative

    // Check if already computed
    if (memo[n][target] != null) return memo[n][target];

    // If current element is greater than sum, skip it
    if (arr[n - 1] > target) return memo[n][target] = subsetSumMemo(arr, n - 1, target, memo);

    // Either include or exclude current element
    memo[n][target] =
        subsetSumMemo(arr, n - 1, target - arr[n - 1], memo)
            || subsetSumMemo(arr, n - 1, target, memo);

    return memo[n][target];
  }

  /** Tabulation approach (Bottom-up) */
  public static boolean subsetSumTabulation(int[] arr, int sum) {
    int n = arr.length;
    boolean[][] dp =
        new boolean[n + 1][sum + 1]; // dp[i][j] = true if subset of first i elements can form sum j

    // Empty subset can form sum 0
    for (int i = 0; i <= n; i++) dp[i][0] = true;

    // Fill dp table in bottom-up manner
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= sum; j++) {
        // If current element is greater than sum, exclude it
        if (arr[i - 1] > j) dp[i][j] = dp[i - 1][j];
        else {
          // Either include or exclude current element
          dp[i][j] = dp[i - 1][j] || dp[i - 1][j - arr[i - 1]];
        }
      }
    }

    return dp[n][sum];
  }

  /** Space optimized solution - O(sum) space instead of O(n*sum) */
  public static boolean subsetSumOptimized(int[] arr, int sum) {
    boolean[] dp = new boolean[sum + 1];

    // Empty subset can form sum 0
    dp[0] = true;

    // Process all elements one by one
    for (int i = 0; i < arr.length; i++) {
      // Process all possible sums from right to left
      // (to avoid using results from current iteration)
      for (int j = sum; j >= arr[i]; j--) {
        dp[j] = dp[j] || dp[j - arr[i]];
      }
    }

    return dp[sum];
  }

  /****************************************
   * PROBLEM 2: EQUAL SUM PARTITION PROBLEM *
   ****************************************/

  /**
   * Problem: Given an array, determine if it can be partitioned into two subsets such that the sum
   * of elements in both subsets is equal.
   *
   * <p>Approach: This reduces to checking if there exists a subset whose sum equals half of the
   * total array sum. If total sum is odd, partition is impossible.
   *
   * <p>SIMILARITY TO SUBSET SUM: Special case of Subset Sum Find if there's a subset with sum =
   * total_sum/2 Uses same dynamic programming approach as Knapsack
   */
  public static boolean canPartitionEqual(int[] nums) {
    int sum = 0;

    // Calculate total sum
    for (int num : nums) sum += num;

    // If sum is odd, we cannot partition into equal subsets
    if (sum % 2 != 0) return false;

    // Find if subset with sum = sum/2 exists
    return subsetSumTabulation(nums, sum / 2);
  }

  /*****************************************
   * PROBLEM 3: COUNT OF SUBSETS WITH GIVEN SUM *
   *****************************************/

  /**
   * Problem: Given an array of non-negative integers and a target sum, count the total number of
   * subsets whose sum equals the target sum.
   *
   * <p>Recursive approach with memoization
   */
  public static int countSubsetSum(int[] arr, int sum) {
    Integer[][] memo = new Integer[arr.length + 1][sum + 1];
    return countSubsetMemo(arr, arr.length, sum, memo);
  }

  private static int countSubsetMemo(int[] arr, int n, int sum, Integer[][] memo) {
    // Base cases
    if (sum == 0) return 1; // Empty subset gives sum 0

    if (n == 0) {
      if (sum == 0 && arr[0] == 0) return 2; // [] and [0] are two subsets
      if (sum == 0 || arr[0] == sum) return 1;
      return 0;
    }

    // Check if already computed
    if (memo[n][sum] != null) return memo[n][sum];

    // If current element is greater than sum, skip it
    if (arr[n - 1] > sum) return memo[n][sum] = countSubsetMemo(arr, n - 1, sum, memo);

    // Count subsets by either including or excluding current element
    memo[n][sum] =
        countSubsetMemo(arr, n - 1, sum - arr[n - 1], memo)
            + countSubsetMemo(arr, n - 1, sum, memo);
    return memo[n][sum];
  }

  /** Tabulation approach */
  public static int countSubsetSumTabulation(int[] arr, int sum) {
    int n = arr.length;
    int[][] dp =
        new int[n + 1][sum + 1]; // dp[i][j] = number of subsets of first i elements that form sum j

    // Empty subset can form sum 0 (exactly 1 way)
    for (int i = 0; i <= n; i++) dp[i][0] = 1;

    // Fill dp table
    for (int i = 1; i <= n; i++) {
      for (int j = 0; j <= sum; j++) {
        // If current element is greater than sum, exclude it
        if (arr[i - 1] > j) dp[i][j] = dp[i - 1][j];
        else {
          // Either include or exclude current element
          dp[i][j] = dp[i - 1][j] + dp[i - 1][j - arr[i - 1]];
        }
      }
    }

    return dp[n][sum];
  }

  /** Space optimized solution */
  public static int countSubsetSumOptimized(int[] arr, int sum) {
    int[] dp = new int[sum + 1];

    // Empty subset makes sum 0 (1 way)
    dp[0] = 1;

    // Process all elements
    for (int i = 0; i < arr.length; i++)
      for (int j = sum; j >= arr[i]; j--) dp[j] += dp[j - arr[i]];

    return dp[sum];
  }

  /****************************************
   * PROBLEM 4: MINIMUM SUBSET SUM DIFFERENCE *
   ****************************************/

  /**
   * Problem: Given an array, partition it into two subsets such that the absolute difference
   * between their sums is minimized.
   *
   * <p>Approach: 1. Calculate the total sum of array 2. Find all possible subset sums up to
   * total_sum/2 3. Find the subset sum closest to total_sum/2 4. The minimum difference will be
   * total_sum - 2*closest_subset_sum
   *
   * <p>SIMILARITY TO SUBSET SUM: Count Subsets with Given Sum:
   *
   * <p>Variant of Subset Sum Instead of boolean (can we achieve sum?), we count how many ways Same
   * recurrence relation but using addition instead of logical OR
   */
  // Using Memoization
  public static int minSubSetSumDifference(int[] arr) {
    int n = arr.length;
    int totalSum = 0;
    for (int num : arr) totalSum += num;
    // TO partition totalsum into 2 subsets, we can find sum upto totalSum/2 and
    // then find the closest sum
    // that can be achieved
    int target = totalSum / 2;
    int[][] memo = new int[n + 1][target + 1];
    for (int i = 0; i <= n; i++) Arrays.fill(memo[i], -1); // Initialize memoization table with -1

    int closestSum = helper_subsetSum(arr, n, target, memo);
    // The minimum difference will be totalSum - 2*closestSum
    return totalSum - 2 * closestSum;
  }

  private static int helper_subsetSum(int[] arr, int n, int target, int[][] memo) {
    if (target == 0) return 0;
    if (n == 0) return Integer.MAX_VALUE; // No elements left to form sum

    if (memo[n][target] != -1) return memo[n][target];
    // If current element is greater than target, skip it
    if (arr[n - 1] > target) return memo[n][target] = helper_subsetSum(arr, n - 1, target, memo);
    int include = helper_subsetSum(arr, n - 1, target - arr[n - 1], memo);
    int exclude = helper_subsetSum(arr, n - 1, target, memo);
    // If we include the current element, we add its value to the closest sum
    if (include != Integer.MAX_VALUE) include += arr[n - 1]; // Include current element's value

    // We take the maximum of the two options
    memo[n][target] = Math.max(include, exclude);
    return memo[n][target];
  }

  public static int minSubsetSumDifference(int[] arr) {
    int totalSum = 0;
    for (int num : arr) totalSum += num;

    int n = arr.length;
    boolean[][] dp = new boolean[n + 1][totalSum / 2 + 1];

    // Initialize dp for sum = 0
    for (int i = 0; i <= n; i++) dp[i][0] = true;

    // Find all possible subset sums
    for (int i = 1; i <= n; i++) {
      for (int j = 1; j <= totalSum / 2; j++) {
        if (arr[i - 1] > j) dp[i][j] = dp[i - 1][j];
        else dp[i][j] = dp[i - 1][j] || dp[i - 1][j - arr[i - 1]];
      }
    }

    // Find the largest sum <= totalSum/2 that can be achieved
    int closestSum = 0;
    for (int j = totalSum / 2; j >= 0; j--) {
      if (dp[n][j]) {
        closestSum = j;
        break;
      }
    }

    // The minimum difference will be totalSum - 2*closestSum
    return totalSum - 2 * closestSum;
  }

  /********************************************
   * PROBLEM 5: COUNT SUBSETS WITH GIVEN DIFFERENCE *
   ********************************************/

  /**
   * Problem: Given an array and a difference 'diff', count the number of partitions such that the
   * difference between the sum of elements in both partitions equals 'diff'.
   *
   * <p>Approach: Let S1 and S2 be the two subsets S1 + S2 = totalSum S1 - S2 = diff
   *
   * <p>Solving these equations: 2*S1 = totalSum + diff S1 = (totalSum + diff) / 2
   *
   * <p>So we need to count subsets with sum = (totalSum + diff) / 2
   *
   * <p>SIMILARITY Mathematical transformation of the problem reduces it to Count Subsets with Sum
   * S1 - S2 = diff, S1 + S2 = total_sum → S1 = (total_sum + diff)/2
   */
  public static int countSubsetsWithDiff(int[] arr, int diff) {
    int totalSum = 0;
    for (int num : arr) {
      totalSum += num;
    }

    // If totalSum + diff is odd or diff > totalSum, no solution exists
    if ((totalSum + diff) % 2 != 0 || diff > totalSum) {
      return 0;
    }

    int targetSum = (totalSum + diff) / 2;
    return countSubsetSumTabulation(arr, targetSum);
  }

  // VARIATION : IF THE - Zero variation of the Count of Subsets With Given
  // Difference
  /*
   * Let’s say the input is:
   * arr = [0, 0, 1, 2, 3]
   * diff = 1
   * Without considering zeros, our usual DP gives 3 ways.
   * But with zeros, the number of ways doubles for every zero.
   * ✅ Why?
   * Because each 0 can be either included or not — and it doesn't change the sum.
   * So for every subset that makes the target, each zero gives 2 choices (±0).
   */
  public static int countSubsetsWithDiffZeroAware(int[] arr, int diff) {
    int n = arr.length, S = 0, zeroCount = 0;
    for (int elem : arr) {
      S += elem;
      if (elem == 0) zeroCount++;
    }

    // (S - diff) must be non-negative and even
    if ((S - diff) < 0 || (S - diff) % 2 != 0) return 0;

    int target = (S - diff) / 2;
    int count = countTargetSubsetZeroAware(arr, target, n);

    // Each zero gives 2 choices: include or exclude (±0)
    return (int) (count * Math.pow(2, zeroCount));
  }

  private static int countTargetSubsetZeroAware(int[] arr, int target, int n) {
    int[][] dp = new int[n + 1][target + 1];
    dp[0][0] = 1; // one way to get sum 0 with 0 elements

    for (int i = 1; i <= n; i++) {
      for (int j = 0; j <= target; j++) {
        if (arr[i - 1] <= j)
          // include + exclude
          dp[i][j] = dp[i - 1][j] + dp[i - 1][j - arr[i - 1]];
        else dp[i][j] = dp[i - 1][j];
      }
    }
    return dp[n][target];
  }

  /***************************
   * PROBLEM 6: TARGET SUM *
   ***************************/

  /**
   * Problem: Given an array and a target, assign + or - signs to each element such that the final
   * expression adds up to the target. Count the number of ways.
   *
   * <p>Approach: This is equivalent to partitioning the array into two subsets S1 and S2 S1 - S2 =
   * target S1 + S2 = totalSum
   *
   * <p>Solving for S1: S1 = (totalSum + target) / 2
   *
   * <p>So we need to count subsets with sum = (totalSum + target) / 2 Identical to Count Subsets
   * with Given Difference Just framed differently (assigning + and - signs)
   */
  public static int targetSum(int[] nums, int target) {
    int totalSum = 0;
    for (int num : nums) totalSum += num;
    // If totalSum + target is odd or target's absolute value > totalSum, no
    // solution exists
    if ((totalSum + target) % 2 != 0 || Math.abs(target) > totalSum) {
      return 0;
    }
    int sum = (totalSum + target) / 2;

    return countSubsetSumTabulation(nums, sum);
  }

  /*********************************
   * PROBLEM 7: UNBOUNDED KNAPSACK *
   *********************************/

  /**
   * Problem: Similar to 0/1 knapsack, but here we can use items multiple times. Given weights and
   * values of items, and a capacity, find the maximum value that can be obtained by picking items
   * (allowed to pick an item multiple times).
   *
   * <p>Recursive approach with memoization
   *
   * <p>SIMILARITY TO 0/1 KNAPSACK: Direct variation of original Knapsack Key difference: can use
   * each item multiple times Recurrence relation modified to allow item reuse
   */
  public static int unboundedKnapsackRecursive(int[] values, int[] weights, int capacity) {
    Integer[][] memo = new Integer[values.length + 1][capacity + 1];
    return unboundedKnapsackMemo(values, weights, values.length, capacity, memo);
  }

  private static int unboundedKnapsackMemo(
      int[] values, int[] weights, int n, int capacity, Integer[][] memo) {
    // Base cases
    if (n == 0 || capacity == 0) return 0;

    // Check if already computed
    if (memo[n][capacity] != null) return memo[n][capacity];

    // If current item's weight is more than capacity, skip it
    if (weights[n - 1] > capacity) {
      memo[n][capacity] = unboundedKnapsackMemo(values, weights, n - 1, capacity, memo);
      return memo[n][capacity];
    } else {
      // Either include or exclude current item
      // Note: When including, we don't reduce n (allowing reuse)
      memo[n][capacity] =
          Math.max(
              values[n - 1]
                  + unboundedKnapsackMemo(values, weights, n, capacity - weights[n - 1], memo),
              unboundedKnapsackMemo(values, weights, n - 1, capacity, memo));
    }
    return memo[n][capacity];
  }

  /** Tabulation approach */
  public static int unboundedKnapsackTabulation(int[] values, int[] weights, int capacity) {
    int n = values.length;
    int[][] dp = new int[n + 1][capacity + 1];

    // Fill dp table
    for (int i = 1; i <= n; i++) {
      for (int j = 0; j <= capacity; j++) {
        // If current item's weight is more than capacity, skip it
        if (weights[i - 1] > j) {
          dp[i][j] = dp[i - 1][j];
        } else {
          // Either include (with reuse) or exclude current item
          dp[i][j] =
              Math.max(
                  values[i - 1] + dp[i][j - weights[i - 1]], // Include with reuse
                  dp[i - 1][j] // Exclude
                  );
        }
      }
    }

    return dp[n][capacity];
  }

  /** Space optimized solution */
  public static int unboundedKnapsackOptimized(int[] values, int[] weights, int capacity) {
    int n = values.length;
    int[] dp = new int[capacity + 1];

    // Fill dp array
    for (int i = 0; i <= capacity; i++) {
      for (int j = 0; j < n; j++) {
        if (weights[j] <= i) {
          // if weight is less than or equal to cap we
          // either include w
          dp[i] = Math.max(dp[i], dp[i - weights[j]] + values[j]);
        }
      }
    }

    return dp[capacity];
  }

  /********************************
   * PROBLEM 8: ROD CUTTING PROBLEM *
   ********************************/

  /**
   * Problem: Given a rod of length n and prices for different pieces, find the maximum value
   * obtainable by cutting the rod.
   *
   * <p>This is essentially the unbounded knapsack problem where: - weights[] = length of each piece
   * (1, 2, 3, ..., n) - values[] = price of each piece - capacity = total rod length
   *
   * <p>Direct application of Unbounded Knapsack Lengths = weights, prices = values, rod length =
   * capacity Can cut the same length multiple times (unbounded property)
   */
  public static int rodCutting(int[] prices, int rodLength) {
    int n = prices.length;

    // Create length array (1, 2, 3, ..., n)
    int[] lengths = new int[n];
    for (int i = 0; i < n; i++) {
      lengths[i] = i + 1;
    }

    // Use unbounded knapsack solution
    return unboundedKnapsackTabulation(prices, lengths, rodLength);
  }

  /** Direct implementation of rod cutting (without reusing unbounded knapsack) */
  public static int rodCuttingDirect(int[] prices, int rodLength) {
    int[] dp = new int[rodLength + 1];

    // Fill dp array for each possible rod length
    for (int i = 1; i <= rodLength; i++) {
      for (int j = 0; j < i; j++) {
        // Try all possible cuts and take the maximum
        dp[i] = Math.max(dp[i], prices[j] + dp[i - j - 1]);
      }
    }

    return dp[rodLength];
  }

  /********************************
   * PROBLEM 9: Coin Change Problem *
   ********************************/

  /**
   * Problem: Given an array of coin denominations and a target amount, find the number of ways to
   * make the target amount using the coins.
   *
   * <p>This is similar to the unbounded knapsack problem where: - weights[] = coin denominations -
   * values[] = 1 (each coin contributes 1 way) - capacity = target amount
   *
   * <p>Approach: Use unbounded knapsack solution to count the number of ways to make the target
   * amount.
   */
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
    if (amount == 0) return 1; // One way to make amount 0 (no coins)
    if (n == 0 || amount < 0) return 0; // No coins left or negative amount
    // If current coin is greater than amount, skip it
    if (coins[n - 1] > amount) return coinChangeRecursive(coins, n - 1, amount);

    // include or exclude and can reuse the coin while including
    int include = coinChangeRecursive(coins, n, amount - coins[n - 1]);
    int exclude = coinChangeRecursive(coins, n - 1, amount);
    return include + exclude;
  }

  private static int coinChangeMemo(int[] coins, int n, int amt, int[][] memo) {
    if (amt == 0) return 1; // One way to make amount 0 — take nothing
    if (n == 0) return 0; // No coins left to use
    if (memo[n][amt] != -1) return memo[n][amt];

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

  // COIN CHANGE WITH MINIMUM COINS
  public static int minCoins(int coins[], int amount) {
    int[][] memo = new int[coins.length + 1][amount + 1];
    for (int m = 0; m < coins.length + 1; m++) {
      Arrays.fill(memo[m], -1);
    }

    return minCoinsMemoized(coins, coins.length, amount, memo);
  }

  @SuppressWarnings("unused")
  private static int minCoinsRecur(int[] coins, int n, int amt, int[][] memo) {
    if (amt == 0) return 0; // 0 coins needed to make 0
    if (n == 0) return Integer.MAX_VALUE - 1; // Cannot make amount with 0 coins

    if (memo[n][amt] != -1) return memo[n][amt];

    if (coins[n - 1] > amt) {
      return memo[n][amt] = minCoinsRecur(coins, n - 1, amt, memo);
    }

    int include = 1 + minCoinsRecur(coins, n, amt - coins[n - 1], memo); // take current coin again
    int exclude = minCoinsRecur(coins, n - 1, amt, memo); // skip current coin

    return memo[n][amt] = Math.min(include, exclude);
  }

  private static int minCoinsMemoized(int[] coins, int n, int amt, int[][] memo) {
    // Base cases
    if (amt == 0) return 0; // 0 coins needed to make 0
    if (n == 0) return Integer.MAX_VALUE; // Cannot make amount with 0 coins

    // Return memoized result if available
    if (memo[n][amt] != -1) return memo[n][amt];

    // If current coin value is greater than remaining amount, skip it
    if (coins[n - 1] > amt) {
      return memo[n][amt] = minCoinsMemoized(coins, n - 1, amt, memo);
    }

    // Two choices: include current coin or exclude it
    int include = minCoinsMemoized(coins, n, amt - coins[n - 1], memo);
    // Only add 1 if include path is valid
    if (include != Integer.MAX_VALUE) include = 1 + include;

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

  /** Test methods for each problem */
  private static void testSubsetSum() {
    System.out.println("\n----- Subset Sum Problem -----");
    int[] arr = {3, 34, 4, 12, 5, 2};
    int sum1 = 9;
    int sum2 = 30;

    System.out.println("Can form sum " + sum1 + " (recursive): " + subsetSumRecursive(arr, sum1));
    System.out.println("Can form sum " + sum1 + " (tabulation): " + subsetSumTabulation(arr, sum1));
    System.out.println("Can form sum " + sum1 + " (optimized): " + subsetSumOptimized(arr, sum1));

    System.out.println("Can form sum " + sum2 + " (recursive): " + subsetSumRecursive(arr, sum2));
    System.out.println("Can form sum " + sum2 + " (tabulation): " + subsetSumTabulation(arr, sum2));
    System.out.println("Can form sum " + sum2 + " (optimized): " + subsetSumOptimized(arr, sum2));
  }

  private static void testEqualSumPartition() {
    System.out.println("\n----- Equal Sum Partition Problem -----");
    int[] arr1 = {1, 5, 11, 5}; // Can be partitioned as {1, 5, 5} and {11}
    int[] arr2 = {1, 2, 3, 5}; // Cannot be partitioned equally

    System.out.println("Can partition arr1 equally: " + canPartitionEqual(arr1));
    System.out.println("Can partition arr2 equally: " + canPartitionEqual(arr2));
  }

  private static void testCountSubsetSum() {
    System.out.println("\n----- Count of Subsets with Given Sum -----");
    int[] arr = {2, 3, 5, 6, 8, 10};
    int sum = 10;

    System.out.println(
        "Number of subsets with sum " + sum + " (recursive): " + countSubsetSum(arr, sum));
    System.out.println(
        "Number of subsets with sum "
            + sum
            + " (tabulation): "
            + countSubsetSumTabulation(arr, sum));
    System.out.println(
        "Number of subsets with sum " + sum + " (optimized): " + countSubsetSumOptimized(arr, sum));
  }

  private static void testMinSubsetSumDiff() {
    System.out.println("\n----- Minimum Subset Sum Difference -----");
    int[] arr1 = {1, 6, 11, 5}; // Minimum difference is 1 ({1, 11} and {6, 5})
    int[] arr2 = {3, 1, 4, 2, 2, 1}; // Minimum difference is 1

    System.out.println("Minimum subset sum difference for arr1: " + minSubsetSumDifference(arr1));
    System.out.println("Minimum subset sum difference for arr2: " + minSubsetSumDifference(arr2));
  }

  private static void testCountSubsetsWithDiff() {
    System.out.println("\n----- Count of Subsets with Given Difference -----");
    int[] arr = {1, 1, 2, 3};
    int diff = 1; // There are 3 ways: {1,3} and {1,2}, {1,1,2} and {3}, {1,2} and {1,1}

    System.out.println(
        "Number of ways to partition with diff " + diff + ": " + countSubsetsWithDiff(arr, diff));
  }

  private static void testTargetSum() {
    System.out.println("\n----- Target Sum Problem -----");
    int[] arr = {1, 1, 1, 1, 1};
    int target = 3;
    // Ways: +1+1+1+1-1=3, +1+1+1-1+1=3, +1+1-1+1+1=3, +1-1+1+1+1=3, -1+1+1+1+1=3

    System.out.println(
        "Number of ways to assign signs to reach target " + target + ": " + targetSum(arr, target));
  }

  private static void testUnboundedKnapsack() {
    System.out.println("\n----- Unbounded Knapsack Problem -----");
    int[] values = {10, 40, 50, 70};
    int[] weights = {1, 3, 4, 5};
    int capacity = 8;

    System.out.println(
        "Maximum value with capacity "
            + capacity
            + " (recursive): "
            + unboundedKnapsackRecursive(values, weights, capacity));
    System.out.println(
        "Maximum value with capacity "
            + capacity
            + " (tabulation): "
            + unboundedKnapsackTabulation(values, weights, capacity));
    System.out.println(
        "Maximum value with capacity "
            + capacity
            + " (optimized): "
            + unboundedKnapsackOptimized(values, weights, capacity));
  }

  private static void testRodCutting() {
    System.out.println("\n----- Rod Cutting Problem -----");
    int[] prices = {1, 5, 8, 9, 10, 17, 17, 20}; // Prices for lengths 1 to 8
    int rodLength = 8;

    System.out.println(
        "Maximum value from rod of length " + rodLength + ": " + rodCutting(prices, rodLength));
    System.out.println(
        "Maximum value from rod of length "
            + rodLength
            + " (direct): "
            + rodCuttingDirect(prices, rodLength));
  }

  public static void main(String[] args) {
    // Test cases for each problem
    testSubsetSum();
    testEqualSumPartition();
    testCountSubsetSum();
    testMinSubsetSumDiff();
    testCountSubsetsWithDiff();
    testTargetSum();
    testUnboundedKnapsack();
    testRodCutting();
  }
}
