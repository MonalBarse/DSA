package com.monal.DP.Dp_cumulative;
import java.util.*;

public class P002 {
  class Solution {
    public int numSubmatrixSumTarget(int[][] matrix, int target) {
      int n = matrix.length, m = matrix[0].length;
      int res = 0;

      // Fix top row
      for (int top = 0; top < n; top++) {
        int[] colSum = new int[m]; // collapsed row sums
        for (int bottom = top; bottom < n; bottom++) {
          for (int c = 0; c < m; c++)
            colSum[c] += matrix[bottom][c];

          // Count subarrays in colSum with sum = target
          res += countSubarrays(colSum, target);
        }
      }

      return res;
    }

    private int countSubarrays(int[] arr, int target) {
      Map<Integer, Integer> freq = new HashMap<>();
      freq.put(0, 1); // to handle subarrays starting from index 0

      int prefix = 0, count = 0;
      for (int num : arr) {
        prefix += num;
        count += freq.getOrDefault(prefix - target, 0);
        freq.put(prefix, freq.getOrDefault(prefix, 0) + 1);
      }

      return count;
    }
  }

}
