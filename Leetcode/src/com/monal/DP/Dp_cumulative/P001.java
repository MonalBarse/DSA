package com.monal.DP.Dp_cumulative;

import java.util.*;

/*
Given an m x n matrix matrix and an integer k, return the max sum of a rectangle in the matrix such that its sum is no larger than k.
It is guaranteed that there will be a rectangle with a sum no larger than k.

Example 1:
  Input: matrix = [[1,0,1],[0,-2,3]], k = 2
  Output: 2
  Explanation: Because the sum of the blue rectangle [[0, 1], [-2, 3]] is 2, and 2 is the max number no larger than k (k = 2).
Example 2:
  Input: matrix = [[2,2,-1]], k = 3
  Output: 3

Constraints:
  m == matrix.length n == matrix[i].length
  1 <= m, n <= 100
  -100 <= matrix[i][j] <= 100
  -105 <= k <= 105
*/
public class P001 {
  class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) {
      // Work with the smaller dimension for better performance
      if (shouldTranspose(matrix)) matrix = transpose(matrix);

      int maxSum = Integer.MIN_VALUE;
      int n = matrix.length;

      // Try all possible row ranges
      for (int top = 0; top < n; top++) {
        int[] columnSums = new int[matrix[0].length];

        for (int bottom = top; bottom < n; bottom++) {
          updateColumnSums(columnSums, matrix[bottom]); // build col sum
          int currentMax = findMaxSubarraySum(columnSums, k);
          maxSum = Math.max(maxSum, currentMax);

          // Early termination if we find the optimal answer
          if (maxSum == k) return k;
        }
      }

      return maxSum;
    }

    private int findMaxSubarraySum(int[] nums, int k) {
      return findMaxSubarraySumWithPrefixSums(buildPrefixSums(nums), k);
    }

    private int[] buildPrefixSums(int[] nums) {
      int[] prefixSums = new int[nums.length + 1];
      for (int i = 0; i < nums.length; i++) prefixSums[i + 1] = prefixSums[i] + nums[i];
      return prefixSums;
    }

    private int findMaxSubarraySumWithPrefixSums(int[] prefixSums, int k) {
      int maxSum = Integer.MIN_VALUE;
      TreeSet<Integer> sortedPrefixes = new TreeSet<>();

      for (int currentPrefix : prefixSums) {
        // Find the smallest prefix that makes current subarray sum <= k
        Integer targetPrefix = sortedPrefixes.ceiling(currentPrefix - k);

        if (targetPrefix != null) maxSum = Math.max(maxSum, currentPrefix - targetPrefix);

        sortedPrefixes.add(currentPrefix);
      }

      return maxSum;
    }

    private boolean shouldTranspose(int[][] matrix) {
      return matrix.length > matrix[0].length;
    }

    private int[][] transpose(int[][] matrix) {
      int rows = matrix.length;
      int cols = matrix[0].length;
      int[][] transposed = new int[cols][rows];
      for (int i = 0; i < rows; i++) for (int j = 0; j < cols; j++) transposed[j][i] = matrix[i][j];
      return transposed;
    }

    private void updateColumnSums(int[] columnSums, int[] newRow) {
      for (int i = 0; i < columnSums.length; i++) columnSums[i] += newRow[i];
    }

  }
}
