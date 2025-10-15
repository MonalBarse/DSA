package com.monal.DP.DP_Knapsack;

/*
You are given two integer arrays of the same length nums1 and nums2. In one operation, you are allowed to swap nums1[i] with nums2[i].

For example, if nums1 = [1,2,3,8], and nums2 = [5,6,7,4], you can swap the element at i = 3 to obtain nums1 = [1,2,3,4] and nums2 = [5,6,7,8].
Return the minimum number of needed operations to make nums1 and nums2 strictly increasing. The test cases are generated so that the given input always makes it possible.

An array arr is strictly increasing if and only if arr[0] < arr[1] < arr[2] < ... < arr[arr.length - 1].



Example 1:
  Input: nums1 = [1,3,5,4], nums2 = [1,2,3,7]
  Output: 1
  Explanation:
  Swap nums1[3] and nums2[3]. Then the sequences are:
  nums1 = [1, 3, 5, 7] and nums2 = [1, 2, 3, 4]
  which are both strictly increasing.

Example 2:
  Input: nums1 = [0,3,5,8,9], nums2 = [2,1,4,6,9]
  Output: 1

*/
public class P011 {
  public int minSwap(int arr1[], int arr2[]) {
    int n = arr1.length;
    int[][] dp = new int[n][2]; // dp[i][0] = min swaps to make arr1[0..i] and arr2[0..i] increasing without
                                // swap at i
                                // dp[i][1] = min swaps to make arr1[0..i] and arr2[0..i] increasing with swap
                                // at i
                                // our answer will be min(dp[n-1][0], dp[n-1][1])
    dp[0][0] = 0; // No swap at first element
    dp[0][1] = 1; // Swap at first element

    for (int i = 1; i < n; i++) {
      dp[i][0] = dp[i][1] = Integer.MAX_VALUE;

      // Case 1: No swap at i
      if (arr1[i - 1] < arr1[i] && arr2[i - 1] < arr2[i]) {
        dp[i][0] = Math.min(dp[i][0], dp[i - 1][0]); // No swap at i-1
        dp[i][0] = Math.min(dp[i][0], dp[i - 1][1]); // Swap at i-1
      }

      // Case 2: Swap at i
      if (arr1[i - 1] < arr2[i] && arr2[i - 1] < arr1[i]) {
        dp[i][1] = Math.min(dp[i][1], dp[i - 1][0] + 1); // No swap at i-1, swap at i
        dp[i][1] = Math.min(dp[i][1], dp[i - 1][1] + 1); // Swap at i-1, swap at i
      }
    }

    return Math.min(dp[n - 1][0], dp[n - 1][1]); // Return the minimum swaps needed
  }

  // Alternative cleaner version with better parameter naming
  class Solution_Memo {
    private int[][] memo;
    private int[] arr1, arr2;
    private int n;

    public int minSwap(int[] nums1, int[] nums2) {
      this.arr1 = nums1;
      this.arr2 = nums2;
      this.n = nums1.length;

      // memo[i][swapped] = minimum swaps from position i onwards
      // swapped: 0 = no swap at previous position, 1 = swap at previous position
      this.memo = new int[n][2];
      for (int i = 0; i < n; i++) memo[i][0] = memo[i][1] = -1;

      // Start from position 0, no previous swap constraint
      return solve(0, 0);
    }

    private int solve(int i, int prevSwapped) {
      if (i == n) return 0;
      if (memo[i][prevSwapped] != -1) return memo[i][prevSwapped];
      int result = Integer.MAX_VALUE;

      // Get previous values based on whether we swapped at i-1
      int prevA, prevB;
      if (i == 0) {
        // No previous element, any choice is valid
        prevA = -1;
        prevB = -1;
      } else if (prevSwapped == 0) {
        // No swap at i-1
        prevA = arr1[i - 1];
        prevB = arr2[i - 1];
      } else {
        // Swap at i-1
        prevA = arr2[i - 1];
        prevB = arr1[i - 1];
      }

      // 1 Don't swap at position i
      int currA1 = arr1[i];
      int currB1 = arr2[i];
      if (i == 0 || (currA1 > prevA && currB1 > prevB)) {
        // Valid to not swap
        int cost = solve(i + 1, 0); // Next position with no swap at current
        if (cost != Integer.MAX_VALUE) result = Math.min(result, cost);
      }

      //  2. Swap at position i
      int currA2 = arr2[i];
      int currB2 = arr1[i];
      if (i == 0 || (currA2 > prevA && currB2 > prevB)) {
        // Valid to swap
        int cost = solve(i + 1, 1); // Next position with swap at current
        if (cost != Integer.MAX_VALUE) result = Math.min(result, cost + 1); // +1 for current swap
      }
      memo[i][prevSwapped] = result;
      return result;
    }
  }
}
