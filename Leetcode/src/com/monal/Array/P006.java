package com.monal.Array;

import java.util.Arrays;

// https://leetcode.com/problems/maximum-subarray/description/
/*
 Given an integer array nums, find the subarray with the largest sum, and return its sum.

Example 1:
  Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
  Output: 6
  Explanation: The subarray [4,-1,2,1] has the largest sum 6.
Example 2:
  Input: nums = [1]
  Output: 1
  Explanation: The subarray [1] has the largest sum 1.
Example 3:
  Input: nums = [5,4,-1,7,8]
  Output: 23
  Explanation: The subarray [5,4,-1,7,8] has the largest sum 23.

Constraints:
   1 <= nums.length <= 105
  -104 <= nums[i] <= 104

 */
public class P006 {
  class Solution {
    private int[] prefix;

    public int maxSubArray(int[] arr) {
      int n = arr.length;
      prefix = new int[n];
      prefix[0] = arr[0];
      for (int i = 1; i < n; i++) {
        prefix[i] = prefix[i - 1] + arr[i];
      }

      return greatestSum(arr);
    }

    private int greatestSum(int[] arr) {
      int maxSum = prefix[0];
      int minimum_prefix = 0;

      for (int i = 0; i < arr.length; i++) {
        maxSum = Math.max(maxSum, prefix[i] - minimum_prefix);
        minimum_prefix = Math.min(minimum_prefix, prefix[i]);
      }

      return maxSum;

    }

    // Kaden's Algorithm

    public int maxSubArray2(int[] arr) {
      int maxSum = arr[0];
      int currentSum = 0;
      for (int elem : arr) {
        if (currentSum < 0) {
          currentSum = 0;
        }
        currentSum += elem;
        maxSum = Math.max(maxSum, currentSum);
      }
      return maxSum;
    }

    public void printMaxSumSubArray(int[] arr) {
      int maxSum = arr[0];
      int currentSum = 0;
      int start = 0, end = 0;
      int tempStart = 0;

      for (int i = 0; i < arr.length; i++) {
        if (currentSum < 0) {
          currentSum = 0;
          // Reset the start index
          tempStart = i; // Reset start index

        }
        currentSum += arr[i];

        if (maxSum < currentSum) {
          maxSum = currentSum;
          start = tempStart;
          end = i;
        }

      }

      System.out.print("Subarray: ");
      // Print the subarray

      System.out.print("[");
      for (int i = start; i <= end; i++) {
        if (i == end) {
          System.out.print(arr[i]);
        } else {
          System.out.print(arr[i] + ", ");
        }
      }
      System.out.print("]");
      System.out.println("\n");
    }
  }

  public static void main(String[] args) {
    Solution solution = new P006().new Solution();

    int[] arr1 = { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
    int[] arr2 = { 1 };
    int[] arr3 = { -3, 5, 4, -1, 7, 8 };

    // System.out.println("Result 1: " + solution.maxSubArray2(arr1)); // Expected:
    // 6

    System.out.println("Max Sum of: " + Arrays.toString(arr1) + " is " + solution.maxSubArray2(arr1)); // Expected: 6

    solution.printMaxSumSubArray(arr1);
    System.out.println("Max Sum of: " + Arrays.toString(arr2) + " is " + solution.maxSubArray2(arr2)); // Expected: 1

    solution.printMaxSumSubArray(arr2);
    System.out.println("Max Sum of " + Arrays.toString(arr3) + " is " + solution.maxSubArray2(arr3)); // Expected: 23

    solution.printMaxSumSubArray(arr3);
  }
}
