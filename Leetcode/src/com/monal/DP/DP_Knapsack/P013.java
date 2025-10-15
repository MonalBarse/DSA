package com.monal.DP.DP_Knapsack;

import java.util.*;

/*
You are given an integer array nums of length n, and a positive integer k.
The power of a subsequence is defined as the minimum absolute difference between any two elements in the subsequence.
Return the sum of powers of all subsequences of nums which have length equal to k.
Since the answer may be large, return it modulo 109 + 7.

Example 1:
Input: nums = [1,2,3,4], k = 3
Output: 4
Explanation:
There are 4 subsequences in nums which have length 3: [1,2,3], [1,3,4], [1,2,4], and [2,3,4]. The sum of powers is |2 - 3| + |3 - 4| + |2 - 1| + |3 - 4| = 4.

Example 2:
Input: nums = [2,2], k = 2
Output: 0
Explanation:
The only subsequence in nums which has length 2 is [2,2]. The sum of powers is |2 - 2| = 0.

Example 3:
Input: nums = [4,3,-1], k = 2
Output: 10
Explanation:
There are 3 subsequences in nums which have length 2: [4,3], [4,-1], and [3,-1]. The sum of powers is |4 - 3| + |4 - (-1)| + |3 - (-1)| = 10.

*/
public class P013 {
  class Solution {
    private static final int MOD = (int) 1e9 + 7;
    private Map<String, Integer> memo;

    public int sumOfPowers(int[] nums, int k) {
      Arrays.sort(nums);
      memo = new HashMap<>();
      return help(nums, k, nums.length - 1, 0, Integer.MAX_VALUE, nums.length);
    }

    private int help(int[] nums, int k, int index, int picked, int minDiff, int prevIndex) {
      if (picked == k) return minDiff;
      if (index < 0) return Integer.MAX_VALUE;

      String key = index + "," + picked + "," + minDiff + "," + prevIndex;
      if (memo.containsKey(key)) return memo.get(key);
      long total = 0;
      // 0/ skip current element
      int skip = help(nums, k, index - 1, picked, minDiff, prevIndex);
      if (skip != Integer.MAX_VALUE)
        total = (skip + total) % MOD;

      // 1/ pick current element
      int newDiff = minDiff;
      if (prevIndex != nums.length) newDiff = Math.min(newDiff, nums[prevIndex] - nums[index]); // Calculate new minDiff
      int pick = help(nums, k, index - 1, picked + 1, newDiff, index); //
      if (pick != Integer.MAX_VALUE) total = (total + pick) % MOD;

      total %= MOD;
      memo.put(key, (int) total);
      return (int) total;
    }
  }

}
