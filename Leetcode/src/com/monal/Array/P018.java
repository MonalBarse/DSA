package com.monal.Array;

import java.util.*;
// https://leetcode.com/problems/find-the-duplicate-number
/*
 * Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
 * There is only one repeated number in nums, return this repeated number.
 * You must solve the problem without modifying the array nums and uses only constant extra space.
 *
 * Example 1:
 *   Input: nums = [1,3,4,2,2]
 *   Output: 2
 *
 * Example 2:
 *  Input: nums = [3,1,3,4,2]
 *  Output: 3
 *
 * Example 3:
 *   Input: nums = [1,1.1.1]
 *   Output: 1
 */

/* PROBLEM 2  -  https://www.interviewbit.com/problems/repeat-and-missing-number-array/
  You are given a read only array of n integers from 1 to n.
  Each integer appears exactly once except A which appears twice and B which is missing.
  Return A and B.
  Note: Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?

  Example:
    Input:[3 1 2 5 3]
    Output:[3, 4]    --> A = 3, B = 4

  Example 2:
    Input:[1 2 3 4 5 6 8 8]
    Output:[8, 7]    --> A = 8, B = 7
 */
public class P018 {

  class Solution1 {
    // Floyd’s Cycle Detection
    public int findDuplicate(int[] nums) {
      int slow = nums[0], fast = nums[0];

      do {
        slow = nums[slow];
        fast = nums[nums[fast]];
      } while (slow != fast);

      slow = nums[0];
      while (slow != fast) {
        slow = nums[slow];
        fast = nums[fast];
      }
      return slow;

    }

  }

  public class Solution2 {
    // DO NOT MODIFY THE LIST. IT IS READ ONLY
    /*
     * - Sum of Natural Numbers
     * S_n = n * (n + 1) / 2
     *
     * - Sum of Squares
     * S2_n = n * (n + 1) * (2 * n + 1) / 6
     *
     * - Actual Sums
     * S = ∑(array elements)
     * S2 = ∑(array elements)^2
     *
     * Differences
     * sumDiff = S - S_n
     * squareSumDiff = S2 - S2_n
     *
     * - Algebraic Identity
     * squareSumDiff = (A - B)(A + B)
     *
     * Solving for A and B
     * A = (sumDiff + sumPlus) / 2
     * B = (sumPlus - sumDiff) / 2
     */
    public ArrayList<Integer> repeatedNumber(final List<Integer> A) {
      int n = A.size();
      long S_n = (long) n * (n + 1) / 2;
      long S2_n = (long) n * (n + 1) * (2 * n + 1) / 6;

      long S = 0, S2 = 0;
      for (int num : A) {
        S += num;
        S2 += (long) num * num;
      }

      long sumDiff = S - S_n;
      long squareSumDiff = S2 - S2_n;
      long sumPlus = squareSumDiff / sumDiff;

      int duplicate = (int) ((sumDiff + sumPlus) / 2);
      int missing = (int) ((sumPlus - sumDiff) / 2);

      return new ArrayList<>(Arrays.asList(duplicate, missing));
    }
  }

  public static void main(String[] args) {
    P018 p018 = new P018();
    Solution1 solution = p018.new Solution1();
    int[] nums = { 1, 3, 4, 2, 2 };
    System.out.println(solution.findDuplicate(nums)); // Output: 2

    int[] nums2 = { 3, 1, 3, 4, 2 };
    System.out.println(solution.findDuplicate(nums2)); // Output: 3

    int[] nums5 = { 2, 2, 2, 2, 2 };
    System.out.println(solution.findDuplicate(nums5)); // Output: 2

    Solution2 solution2 = p018.new Solution2();
    List<Integer> list = Arrays.asList(3, 1, 2, 5, 3);
    System.out.println(solution2.repeatedNumber(list)); // Output: [3, 4]

    List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6, 8, 8);
    System.out.println(solution2.repeatedNumber(list2)); // Output: [8, 7]

    List<Integer> list3 = Arrays.asList(1, 1, 3, 4, 5, 6, 7, 8, 9, 10);
    System.out.println(solution2.repeatedNumber(list3)); // Output: [1, 2]
  }
}