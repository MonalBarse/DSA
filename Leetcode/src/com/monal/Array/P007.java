package com.monal.Array;

import java.util.Arrays;

// https://leetcode.com/problems/next-permutation/

/*
    A permutation of an array of integers is an arrangement of its members into a sequence or linear order.

    For example, for arr = [1,2,3], the following are all the permutations of arr: [1,2,3], [1,3,2], [2, 1, 3], [2, 3, 1], [3,1,2], [3,2,1].
    The next permutation of an array of integers is the next lexicographically greater permutation of its integer. More formally, if all the permutations of the array are sorted in one container according to their lexicographical order, then the next permutation of that array is the permutation that follows it in the sorted container. If such arrangement is not possible, the array must be rearranged as the lowest possible order (i.e., sorted in ascending order).

    For example, the next permutation of arr = [1,2,3] is [1,3,2].

    Similarly, the next permutation of arr = [2,3,1] is [3,1,2].
    While the next permutation of arr = [3,2,1] is [1,2,3] because [3,2,1] does not have a lexicographical larger rearrangement.
    Given an array of integers nums, find the next permutation of nums.

    The replacement must be in place and use only constant extra memory.

    Example 1:
      Input: nums = [1,2,3]
      Output: [1,3,2]
    Example 2:
      Input: nums = [3,2,1]
      Output: [1,2,3]
    Example 3:
      Input: nums = [1,1,5]
      Output: [1,5,1]

    Constraints:
      1 <= nums.length <= 100
      0 <= nums[i] <= 100
 */
public class P007 {
  class Solution {
    public void nextPermutation(int[] arr) {
      int n = arr.length;
      int i = n - 2;

      // Step 1: Find the first decreasing element from the right
      while (i >= 0 && arr[i] >= arr[i + 1]) {
        i--;
      }

      if (i >= 0) { // If a breakpoint was found
        int j = n - 1;

        // Step 2: Find the smallest element in arr[i+1:] that is larger than arr[i]
        // since the array is in decreasing order from i+1 to n-1 (checked in Step 1)
        while (arr[j] <= arr[i]) {
          j--;
        }

        // Swap arr[i] and arr[j]
        swap(arr, i, j);
      }

      // Step 3: Reverse arr[i+1:] to get the next permutation
      reverse(arr, i + 1, n - 1);
    }

    private void swap(int[] arr, int i, int j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }

    private void reverse(int[] arr, int start, int end) {
      while (start < end) {
        swap(arr, start, end);
        start++;
        end--;
      }
    }
  }

  public static void main(String[] args) {
    Solution solution = new P007().new Solution();

    int[] arr1 = { 1, 2, 3 };
    int[] arr2 = { 3, 2, 1 };
    int[] arr3 = { 5, 2, 1, 3, 21 };

    System.out.println("\n");
    System.out.println("Before next permutation:");
    printArray(arr1);
    printArray(arr2);
    printArray(arr3);

    solution.nextPermutation(arr1);
    solution.nextPermutation(arr2);
    solution.nextPermutation(arr3);

    System.out.println("\n");
    System.out.println("After next permutation:");
    printArray(arr1);
    System.out.println(" Expected: [1, 3, 2]");
    printArray(arr2);

    System.out.println(" Expected: [1, 2, 3]");
    printArray(arr3);

    System.out.println(" Expected: [5, 2, 1, 21, 3]");
    System.out.println("\n");
  }

  private static void printArray(int[] arr) {
    System.out.print(Arrays.toString(arr) + "  ");
  }
}
