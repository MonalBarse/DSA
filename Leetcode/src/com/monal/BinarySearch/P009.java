package com.monal.BinarySearch;
/*
Given an array of integers nums and an integer threshold, we will choose a positive integer divisor, divide all the array by it, and sum the division's result. Find the smallest divisor such that the result mentioned above is less than or equal to threshold.
Each result of the division is rounded to the nearest integer greater than or equal to that element. (For example: 7/3 = 3 and 10/2 = 5).
The test cases are generated so that there will be an answer.

Example 1:
  Input: nums = [1,2,5,9], threshold = 6
  Output: 5
  Explanation: We can get a sum to 17 (1+2+5+9) if the divisor is 1.
  If the divisor is 4 we can get a sum of 7 (1+1+2+3) and if the divisor is 5 the sum will be 5 (1+1+1+2).

Example 2:
  Input: nums = [44,22,33,11,1], threshold = 5
  Output: 44

Constraints:
  1 <= nums.length <= 5 * 104
  1 <= nums[i] <= 106
  nums.length <= threshold <= 106
*/

public class P009 {

  class Solution {
    public int smallestDivisor(int[] nums, int threshold) {

      // Define the search space
      // The search space is the divisor itself. The divisor lies between
      // 1 and max(nums[i])
      int start = 1, end = 0, res = 0;
      for (int num : nums) {
        end = Math.max(end, num);
      }

      // Binary search on the serach space

      while (start <= end) {
        int mid = start + (end - start) / 2;
        if (isValid(nums, threshold, mid)) {
          res = mid;
          end = mid - 1;
        } else {
          start = mid + 1;
        }
      }

      return res;
    }

    private boolean isValid(int[] nums, int threshold, int divisor) {
      int sum = 0;

      for (int num : nums) {
        sum = sum + num / divisor;
        if (num % divisor != 0) {
          sum++;
        }

        if (sum > threshold) {
          return false;
        }
      }
      return true;
    }
  }

  public static void main(String[] args) {
    P009 p009 = new P009();
    Solution obj = p009.new Solution();

    int[] nums1 = { 1, 2, 5, 9 };
    int threshold1 = 6;
    System.out.println(obj.smallestDivisor(nums1, threshold1));

    int[] nums2 = { 44, 22, 33, 11, 1 };
    int threshold2 = 5;
    System.out.println(obj.smallestDivisor(nums2, threshold2));

  }

}
