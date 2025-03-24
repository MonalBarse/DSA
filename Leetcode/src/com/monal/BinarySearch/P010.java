package com.monal.BinarySearch;

/* Kth Missing Positive Number

Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
Return the kth positive integer that is missing from this array.

Example 1:
  Input: arr = [2,3,4,7,11], k = 5
  Output: 9
  Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.
Example 2:
  Input: arr = [1,2,3,4], k = 2
  Output: 6
  Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.*/

public class P010 {
  class Solution {
    private int findKthPositive(int[] arr, int k) {
      // Define the search space
      // The search space starts with 1 and ends with arr.length + k
      // Since the kth missing number can be at most arr.length + k

      int start = 1, end = arr.length + k;

      while (start < end) {
        int mid = start + (end - start) / 2;

        // Number of missing elements in the array from 1 to mid
        int missingCount = mid - countElementsLessThanOrEqual(arr, mid);

        // If the number of missing elements is greater than k, then the k-th missing
        if (missingCount >= k) {
          end = mid;
        } else {
          // If the number of missing elements is less than k, then the
          // k-th missing number is greater than mid
          start = mid + 1;
        }
      }

      return start; // start points to the k-th missing number
    }

    private int countElementsLessThanOrEqual(int[] arr, int num) {
      // Binary search to find the number of elements less than or equal to num

      // The search space is the array itself -
      int start = 0, end = arr.length;

      while (start < end) {
        int mid = start + (end - start) / 2;
        if (arr[mid] <= num) {
          start = mid + 1;
        } else {
          end = mid;
        }
      }

      return start; // Number of elements ≤ num
    }
  }

  public static void main(String[] args) {
    P010 po1o = new P010();
    P010.Solution obj = po1o.new Solution();

    int arr1[] = { 2, 3, 4, 7, 11 };
    int k1 = 5;
    System.out.println(obj.findKthPositive(arr1, k1)); // 9

    int arr2[] = { 1, 2, 3, 4 };
    int k2 = 2;
    System.out.println(obj.findKthPositive(arr2, k2)); // 6

    int arr3[] = { 12, 123, 1234, 12345, 23456, 345678, 4567890 };
    int k3 = 123453;
    System.out.println(obj.findKthPositive(arr3, k3));

    int arr4[] = { 1, 3, 5, 7, 9 };
    int k4 = 2;
    System.out.println(obj.findKthPositive(arr4, k4));
  }
}
