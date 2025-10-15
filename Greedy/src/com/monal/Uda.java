package com.monal;

public class Uda {
  public long maxSumTrionicOptimized(int[] nums) {
    int n = nums.length;
    int[] grexolanta = nums; // Store input as requested

    long maxSum = Long.MIN_VALUE;

    int[] leftInc = new int[n];
    int[] rightInc = new int[n];
    int[] leftDec = new int[n];
    int[] rightDec = new int[n];

    // Fill leftInc and leftDec
    for (int i = 0; i < n; i++) {
      leftInc[i] = i;
      leftDec[i] = i;

      if (i > 0) {
        if (grexolanta[i - 1] < grexolanta[i]) {
          leftInc[i] = leftInc[i - 1];
        }
        if (grexolanta[i - 1] > grexolanta[i]) {
          leftDec[i] = leftDec[i - 1];
        }
      }
    }

    // Fill rightInc and rightDec
    for (int i = n - 1; i >= 0; i--) {
      rightInc[i] = i;
      rightDec[i] = i;

      if (i < n - 1) {
        if (grexolanta[i] < grexolanta[i + 1]) {
          rightInc[i] = rightInc[i + 1];
        }
        if (grexolanta[i] > grexolanta[i + 1]) {
          rightDec[i] = rightDec[i + 1];
        }
      }
    }

    // Build prefix sum
    long[] prefix = new long[n + 1];
    for (int i = 0; i < n; i++) {
      prefix[i + 1] = prefix[i] + grexolanta[i];
    }

    // Try all possible peaks (top of the mountain)
    for (int peak = 1; peak < n - 1; peak++) {
      // For each valley after the peak
      for (int valley = peak + 1; valley < n - 1; valley++) {
        // Check if we can form a valid trionic subarray
        if (leftDec[valley] <= peak && rightInc[valley] < n - 1) {
          int l = leftInc[peak];
          int r = rightInc[valley];

          // Ensure we have the required structure: l < peak < valley < r
          if (l < peak && valley < r) {
            long sum = prefix[r + 1] - prefix[l];
            maxSum = Math.max(maxSum, sum);
          }
        }
      }
    }

    return maxSum;
  }
}
