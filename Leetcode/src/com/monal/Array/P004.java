package com.monal.Array;

// https://leetcode.com/problems/sort-colors/description/
/*
    Given an array nums with n objects colored red, white, or blue, sort them in-place so that objects of the same color are adjacent, with the colors in the order red, white, and blue.
    We will use the integers 0, 1, and 2 to represent the color red, white, and blue, respectively.
    You must solve this problem without using the library's sort function.

    Example 1:
      Input: nums = [2,0,2,1,1,0]
      Output: [0,0,1,1,2,2]
    Example 2:
      Input: nums = [2,0,1]
      Output: [0,1,2]
 */
public class P004 {
  class Solution {
    public void sortColors(int[] arr) {
      // THREE POINTER METHOD
      // Dutch National Flag algorithm
      // low represents the boundary of 0s (red)
      // mid represents the current element being examined
      // high represents the boundary of 2s (blue)

      int low = 0, mid = 0, high = arr.length - 1;

      while (mid <= high) {
        switch (arr[mid]) {
          case 0:
            swap(arr, low, mid);
            low++;
            mid++;
            break;

          case 1:
            mid++;
            break;

          case 2:
            swap(arr, mid, high);
            high--;
            break;
        }
      }
    }

    private void swap(int[] arr, int i, int j) {
      int temp = arr[i];
      arr[i] = arr[j];
      arr[j] = temp;
    }
  }

  // ======================================================= //
  public static void main(String[] args) {
    Solution solution = new P004().new Solution();

    int[] arr1 = { 2, 0, 2, 1, 1, 0 };
    int[] arr2 = { 2, 0, 1 };

    System.out.println("Before sorting arr1 and arr2:");
    printArray(arr1);
    printArray(arr2);

    solution.sortColors(arr1);
    solution.sortColors(arr2);

    System.out.println("After sorting arr1 and arr2:");
    printArray(arr1);
    printArray(arr2);
  }

  private static void printArray(int[] arr) {
    for (int i : arr) {
      System.out.print(i + " ");
    }
    System.out.println();
  }
}
