package com.monal.BinarySearch;

/*
  AGGRESSIVE COWS:

  You are given an array stalls[] where each element represents the position of a
  stall in a straight line. You have k cows and you want to place them in the stalls
  such that the minimum distance between any two cows is maximized.

  Return the largest minimum distance possible.

  Example 1:
      Input: stalls = [1, 2, 4, 8, 9], k = 3
      Output: 3
      Explanation: The optimal way to place the cows is in stalls 1, 4, and 8.

  Example 2:
      Input: stalls = [1, 2, 3, 4, 5, 6, 7, 8, 9], k = 4
      OutPut: 2
      Explanation: The optimal way to place the cows is in stalls 1, 3, 6, and 9.
 */
public class P003 {

  public static void main(String[] args) {
    int[] stalls1 = { 1, 2, 4, 8, 9 };
    int k1 = 3;
    System.out.println(maxMinDistance(stalls1, k1));
    System.out.println(minMaxDistance(stalls1, k1));

    int[] stalls2 = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 };
    int k2 = 4;
    System.out.println(maxMinDistance(stalls2, k2));
    System.out.println(minMaxDistance(stalls2, k2));

    int[] stalls3 = { 1, 17, 22, 47, 68, 72, 80, 99, 109, 113 };
    int k3 = 5;
    System.out.println(maxMinDistance(stalls3, k3));
    System.out.println(minMaxDistance(stalls3, k3));
  }

  private static int maxMinDistance(int[] stalls, int k) {
    int n = stalls.length;
    // We are applying Binary search on the Distance between the cows.
    // Step 1 is to think about the range of the search space.
    // The minimum distance between any two cows can be 1 and the maximum distance
    // can be the distance between the first and the last stall.
    int start = 1;
    int end = stalls[n - 1] - stalls[0];

    int res = 0;

    // Step 2 is to apply Binary search on the search space.
    while (start <= end) {
      int mid = start + (end - start) / 2;

      if (isValid(stalls, n, k, mid)) {
        // If the current distance is valid, then we store the result and move to the
        // right side
        // as we need to maximize the minimum distance between the cows.
        res = mid;
        start = mid + 1;
      } else {
        // If the current distance is not valid, then we move to the left side.
        end = mid - 1;
      }

    }
    return res;
  }

  private static boolean isValid(int[] stalls, int n, int cows, int minDist) {
    // To check if the current distance is valid, we need to place the cows in the
    // stalls and check if the number of cows placed is greater than or equal to k.

    int cow_count = 1;
    int lastPlaced = stalls[0];

    for (int m = 1; m < n; m++) {
      // If the distance between the current stall and the last placed stall is
      // greater than or equal to the minimum distance, then we can place the cow.
      if (stalls[m] - lastPlaced >= minDist) {
        cow_count++;
        lastPlaced = stalls[m];
        if (cow_count == cows)
          return true;
      }
    }

    return false;
  }

  private static int minMaxDistance(int[] stalls, int k) {
    int n = stalls.length;
    // To minimize the maximum distance between the cows
    int start = 1, end = stalls[n - 1] - stalls[0];
    int res = end;

    while (start <= end) {
      int mid = start + (end - start) / 2;

      if (isValid(stalls, n, k, mid)) {
        end = mid - 1;
        res = mid;
      } else {
        start = mid + 1;
      }
    }
    return res;

    // int[][] dp = new int[k + 1][n + 1];

    // for (int i = 0; i <= n; i++) {
    // dp[0][i] = Integer.MAX_VALUE;
    // }

    // for (int i = 0; i <= k; i++) {
    // dp[i][0] = 0;
    // }

    // for (int i = 1; i <= k; i++) {
    // for (int j = 1; j <= n; j++) {
    // int minDist = Integer.MAX_VALUE;
    // for (int m = 0; m < j; m++) {
    // minDist = Math.min(minDist, Math.max(dp[i - 1][m], stalls[j - 1] -
    // stalls[m]));
    // }
    // dp[i][j] = minDist;
    // }
    // }

    // return dp[k][n];
  }
}
