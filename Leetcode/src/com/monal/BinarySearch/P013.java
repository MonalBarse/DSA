package com.monal.BinarySearch;

/* Problem statement
Given an integer array arr and an integer k, split arr into k non-empty subarrays such that the largest sum of any subarray is minimized.
Return the minimized largest sum of the split.
A subarray is a contiguous part of the array.

Example 1:
  Input: arr = [7,2,5,10,8], k = 2
  Output: 18
  Explanation: There are four ways to split arr into two subarrays.
  The best way is to split it into [7,2,5] and [10,8], where the largest sum among the two subarrays is only 18.

Example 2:
  Input: arr = [1,2,3,4,5], k = 2
  Output: 9
  Explanation: There are four ways to split arr into two subarrays.
  The best way is to split it into [1,2,3] and [4,5], where the largest sum among the two subarrays is only 9.
Constraints:

1 <= arr.length <= 1000
0 <= arr[i] <= 106
1 <= k <= min(50, arr.length)
*/

public class P013 {

  class Solution {
    public int splitArray(int[] arr, int k) {
      // Step 1: Define the search space
      // The minimum value of the search space is the maximum element in the array
      // The maximum value of the search space is the sum of all elements in the array

      int start = 0, end = 0, res = 0;
      for (int num : arr) {
        start = Math.max(start, num);
        end += num;
      }

      // Step 2: Perform binary search to find the optimal value

      while (start <= end) {
        int mid = start + (end - start) / 2;
        if (isPossible(arr, k, mid)) {
          res = mid;
          end = mid - 1;
        } else {
          start = mid + 1;
        }
      }
      return res;
    }

    private boolean isPossible(int arr[], int k, int maxSum) {
      int partitions = 1;
      int sum = 0;
      for (int num : arr) {
        sum += num;
        if (sum > maxSum) {
          partitions++;
          sum = num;
        }
        if (partitions > k) {
          return false;
        }
      }
      return true;
    }
  }

  public static void main(String[] args) {
    P013 p013 = new P013();
    Solution solution = p013.new Solution();
    int[] nums1 = { 7, 2, 5, 10, 8 };
    int k1 = 2;

    int[] nums2 = { 1, 2, 3, 4, 5 };
    int k2 = 2;

    System.out.println(solution.splitArray(nums1, k1)); // Output: 18
    System.out.println(solution.splitArray(nums2, k2)); // Output: 9

  }

}
