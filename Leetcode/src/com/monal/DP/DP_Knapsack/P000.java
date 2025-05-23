package com.monal.DP.DP_Knapsack;

import java.util.*;

/* Knapsack Problem */
/*
  1. 0/1 Knapsack Problem

  2. Unbounded Knapsack Problem

  3. Fractional Knapsack Problem

*/
public class P000 {
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
    if (idx == n || capacity == 0) {
      return 0; // No more value can be obtained
    }

    // If current item is too heavy, skip it
    if (W[idx] > capacity) {
      return helper_fn1(W, V, capacity, idx + 1, n);
    }

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
        if (i == 0 || w == 0) {
          dp[i][w] = 0;
        }
        // If current item's weight can fit in the knapsack
        else if (weights[i - 1] <= w) {
          // Max of: (1) including current item, (2) excluding current item
          dp[i][w] = Math.max(
              values[i - 1] + dp[i - 1][w - weights[i - 1]], // Include item
              dp[i - 1][w] // Exclude item
          );
        }
        // If current item is too heavy, skip it
        else {
          dp[i][w] = dp[i - 1][w];
        }
      }
    }
    // Return the maximum value that can be obtained
    return dp[n][capacity];
  }

  public int knapsackBottomUp1(int[] weights, int[] values, int capacity, int n) {
    int[] prev = new int[capacity + 1];

    // Fill the DP table bottom-up

    for (int i = 0; i <= n; i++) {
      int[] curr = new int[capacity + 1];
      for (int w = 0; w <= capacity; w++) {
        // Base case: no items or no capacity
        if (i == 0 || w == 0) {
          curr[w] = 0;
        } else if (weights[i - 1] <= w) {
          curr[w] = Math.max(
              values[i - 1] + prev[w - weights[i - 1]], // Include item
              prev[w] // Exclude item
          );
        } else {
          curr[w] = prev[w];
        }
      }
      // Update prev to the current row
      prev = curr;
    }
    // Return the maximum value that can be obtained
    return prev[capacity];
  }

  /**
   * Space-optimized implementation of the 0/1 Knapsack problem
   * Time Complexity: O(n * capacity)
   * Space Complexity: O(capacity)
   */

  public int knapsackSpaceOptimized(int[] weights, int[] values, int capacity, int n) {
    // Create a 1D DP array of size [capacity+1]
    int[] dp = new int[capacity + 1];

    // Fill the DP array
    for (int i = 0; i < n; i++) {
      for (int w = capacity; w >= weights[i]; w--) {
        dp[w] = Math.max(dp[w], values[i] + dp[w - weights[i]]);
      }
    }
    // Return the maximum value that can be obtained
    return dp[capacity];

  } // End of knapsackSpaceOptimized

  // ====================== UNBOUNDED KNAPSACK PROBLEM =====================//
  /**
   * The problem states that:
   * Given weigts and values of n items, put these items in a knapsack of capacity
   * W, to get the maximum total value in the knapsack. The same type of item can
   * be picked multiple times. (consider unlimited supply of each item)
   *
   * Recursive implementation of the Unbounded Knapsack problem
   * Time Complexity: O(2^n)
   * Space Complexity: O(n) - recursion stack
   */
  public int unboundedKnapsackRecursive(int[] weights, int[] values, int capacity, int n) {
    return unboundedKnapsackHelper(weights, values, capacity, 0, n);
  }

  private int unboundedKnapsackHelper(int[] W, int[] V, int capacity, int idx, int n) {
    // Base cases
    if (idx == n || capacity == 0) {
      return 0; // No more value can be obtained
    }

    // If current item is too heavy, skip it
    if (W[idx] > capacity) {
      return unboundedKnapsackHelper(W, V, capacity, idx + 1, n);
    }

    // Try including the current item
    int include = V[idx] + unboundedKnapsackHelper(W, V, capacity - W[idx], idx, n);

    // Try excluding the current item
    int exclude = unboundedKnapsackHelper(W, V, capacity, idx + 1, n);

    // Return the better option
    return Math.max(include, exclude);
  }

  /**
   * Memoized implementation of the Unbounded Knapsack problem
   * Time Complexity: O(n * capacity)
   * Space Complexity: O(n * capacity)
   */
  public int unboundedKnapsackMemoized(int[] weights, int[] values, int capacity, int n) {
    // Create memoization table
    Integer[][] memo = new Integer[n + 1][capacity + 1];
    return unboundedKnapsackMemoizedHelper(weights, values, capacity, n, memo);
  }

  private int unboundedKnapsackMemoizedHelper(int[] weights, int[] values, int capacity, int n, Integer[][] memo) {
    // Base case
    if (n == 0 || capacity == 0)
      return 0;

    // Check if already computed
    if (memo[n][capacity] != null)
      return memo[n][capacity];

    // If weight of nth item is greater than capacity, skip it
    if (weights[n - 1] > capacity) {
      memo[n][capacity] = unboundedKnapsackMemoizedHelper(weights, values, capacity, n - 1, memo);
      return memo[n][capacity];
    }

    int include = values[n - 1] + unboundedKnapsackMemoizedHelper(weights, values, capacity - weights[n - 1], n, memo);
    int exclude = unboundedKnapsackMemoizedHelper(weights, values, capacity, n - 1, memo);
    // Store the result of max value
    memo[n][capacity] = Math.max(include, exclude);

    return memo[n][capacity];
  }

  /**
   * Tabulated implementation of the Unbounded Knapsack problem
   * Time Complexity: O(n * capacity)
   * Space Complexity: O(n * capacity)
   */
  public int unboundedKnapsackBottomUp(int[] weights, int[] values, int capacity, int n) {
    // Create DP table of size [n+1][capacity+1]
    int[][] dp = new int[n + 1][capacity + 1];

    // Fill the DP table bottom-up
    for (int i = 0; i <= n; i++) {
      for (int w = 0; w <= capacity; w++) {
        // Base case: no items or no capacity
        if (i == 0 || w == 0) {
          dp[i][w] = 0;
        }
        // If current item's weight can fit in the knapsack
        else if (weights[i - 1] <= w) {
          // Max of: (1) including current item, (2) excluding current item
          dp[i][w] = Math.max(
              values[i - 1] + dp[i][w - weights[i - 1]], // Include item
              dp[i - 1][w] // Exclude item
          );
        }
        // If current item is too heavy, skip it
        else {
          dp[i][w] = dp[i - 1][w];
        }
      }
    }
    // Return the maximum value that can be obtained
    return dp[n][capacity];
  }

  /**
   * Space-optimized implementation of the Unbounded Knapsack problem
   * Time Complexity: O(n * capacity)
   * Space Complexity: O(capacity)
   */
  public int unboundedKnapsackSpaceOptimized(int[] weights, int[] values, int capacity, int n) {
    // Create a 1D DP array of size [capacity+1]
    int[] dp = new int[capacity + 1];

    // Fill the DP array
    for (int i = 0; i < n; i++) {
      for (int w = weights[i]; w <= capacity; w++) {
        dp[w] = Math.max(dp[w], values[i] + dp[w - weights[i]]);
      }
    }
    // Return the maximum value that can be obtained
    return dp[capacity];
  }

  // NOT DYNAMIC PROGRAMMING

  /**
   * // ====================== FRACTIONAL KNAPSACK PROBLEM =====================//
   * Greedy implementation of the Fractional Knapsack problem
   * Time Complexity: O(n log n)
   * Space Complexity: O(1)
   */

  /**
   * Helper class to represent an item with weight and value
   */
  class Item {
    int weight;
    int value;

    public Item(int weight, int value) {
      this.weight = weight;
      this.value = value;
    }

    public double valuePerWeight() {
      return (double) value / weight;
    }
  }

  public double fractionalKnapsack(int[] weights, int[] values, int capacity) {
    // Create an array of Item objects
    Item[] items = new Item[weights.length];
    for (int i = 0; i < weights.length; i++) {
      items[i] = new Item(weights[i], values[i]);
    }

    // Sort the items by value-to-weight ratio in descending order
    Arrays.sort(items, (a, b) -> Double.compare(b.valuePerWeight(), a.valuePerWeight()));

    double totalValue = 0.0;

    // Iterate through the sorted items
    for (Item item : items) {
      if (capacity <= 0) {
        break; // No more capacity left
      }
      // If the item can fit in the knapsack, take it all
      if (item.weight <= capacity) {
        totalValue += item.value;
        capacity -= item.weight;
      } else { // Take the fraction of the item that fits
        totalValue += item.valuePerWeight() * capacity;
        capacity = 0; // Knapsack is full
      }
    }
    return totalValue;
  }

}
