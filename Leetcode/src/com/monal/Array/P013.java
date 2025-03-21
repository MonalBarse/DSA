package com.monal.Array;
// Three Sum - https://leetcode.com/problems/3sum/

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
Notice that the solution set must not contain duplicate triplets.

Example 1:
  Input: nums = [-1,0,1,2,-1,-4]
  Output: [[-1,-1,2],[-1,0,1]]
  Explanation:
    nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0.
    nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0.
    nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0.
  The distinct triplets are [-1,0,1] and [-1,-1,2].
  Notice that the order of the output and the order of the triplets does not matter.

Example 2:
  Input: nums = [0,1,1]
  Output: []
  Explanation: The only possible triplet does not sum up to 0.

Example 3:
  Input: nums = [0,0,0]
  Output: [[0,0,0]]
  Explanation: The only possible triplet sums up to 0.

Constraints:
  3 <= nums.length <= 3000
  -105 <= nums[i] <= 105
 */

public class P013 {
  class Solution {
    public List<List<Integer>> threeSum(int[] arr) {
      return new AbstractList<>() {
        List<List<Integer>> ans;

        @Override
        public int size() {
          if (ans == null)
            ans = createList(arr);
          return ans.size();
        }

        @Override
        public List<Integer> get(int index) {
          if (ans == null)
            ans = createList(arr);
          return ans.get(index);
        }
      };
    }

    private List<List<Integer>> createList(int[] arr) {
      List<List<Integer>> ans = new ArrayList<>();
      int len = arr.length;
      Arrays.sort(arr);
      for (int i = 0; i < len - 2 && arr[i] <= 0; ++i) {
        if (i > 0 && arr[i] == arr[i - 1])
          continue;
        twoSum(ans, arr, i + 1, len - 1, -arr[i]);
      }
      return ans;
    }

    private void twoSum(List<List<Integer>> ans, int[] arr, int left, int right, int target) {
      while (left < right) {
        if (arr[left] + arr[right] > target) {
          --right;
          continue;
        }
        if (arr[left] + arr[right] < target) {
          ++left;
          continue;
        }

        ans.add(Arrays.asList(-target, arr[left++], arr[right--]));

        while (left <= right && arr[left] == arr[left - 1])
          ++left;
      }
    }
  }

  public static void main(String[] args) {
    Solution solution = new P013().new Solution();

    int[] arr1 = { -1, 0, 1, 2, -1, -4 };
    int[] arr2 = { 0, 1, 1 };
    int[] arr3 = { 0, 0, 0 };

    System.out.println("Result 1: " + solution.threeSum(arr1) + "  Expected: [[-1, -1, 2], [-1, 0, 1]]");
    System.out.println("Result 2: " + solution.threeSum(arr2) + "  Expected: []");
    System.out.println("Result 3: " + solution.threeSum(arr3) + "  Expected: [[0, 0, 0]]");
  }
}
