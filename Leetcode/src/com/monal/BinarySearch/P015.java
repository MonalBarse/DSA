package com.monal.BinarySearch;

import java.util.Arrays;

/*
The distance of a pair of integers a and b is defined as the absolute difference between a and b.
Given an integer array nums and an integer k, return the kth smallest distance among all the pairs nums[i] and nums[j] where 0 <= i < j < nums.length.

Example 1:
  Input: nums = [1,3,1], k = 1
  Output: 0
  Explanation: Here are all the pairs:
  (1,3) -> 2
  (1,1) -> 0
  (3,1) -> 2
  Then the 1st smallest distance pair is (1,1), and its distance is 0.

Example 2:
  Input: nums = [1,1,1], k = 2
  Output: 0

Example 3:
  Input: nums = [1,6,1], k = 3
  Output: 5

  Constraints:
    n == nums.length
    2 <= n <= 104
    0 <= nums[i] <= 106
    1 <= k <= n * (n - 1) / 2
*/
/*
🚀 Plan
1️ Sort the array nums[]
  This helps us calculate absolute differences efficiently.

2️ Binary Search on Possible Distances

  Smallest possible distance = 0 (if nums contains duplicate values).
  Largest possible distance = max(nums) - min(nums).

3️ Check if the Mid Distance is Valid
  Count how many pairs have a distance ≤ mid.
  If count ≥ k, shrink search space.

  Otherwise, increase the search space
*/
public class P015 {
  class Solution {
    public int smallestDistancePair(int[] arr, int k) {
      int n = arr.length;
      Arrays.sort(arr);

      // Step 1 : define the search space

      int start = 0; // Smallest possible distance
      int end = arr[n - 1] - arr[0]; // Largest possible distance (sorted)

      while (start < end) {

        int mid = start + (end - start) / 2;

        if (countPairs(arr, mid) >= k) {
          end = mid; // Reduce search space
        } else {
          start = mid + 1;
        }
      }
      return start;
    }

    private int countPairs(int[] arr, int maxDiff) {
      int count = 0, left = 0;

      for (int right = 0; right < arr.length; right++) {
        while (arr[right] - arr[left] > maxDiff) {
          left++;
        }
        count += right - left;
      }
      return count;
    }
  }

}
