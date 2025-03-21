package com.monal.Array;

import java.util.ArrayList;
import java.util.List;

/*
Given an integer array of size n, find all elements that appear more than ⌊ n/3 ⌋ times.
  Example 1:
    Input: nums = [3,2,3]
    Output: [3]
  Example 2:
    Input: nums = [1]
    Output: [1]
  Example 3:
    Input: nums = [1,2]
    Output: [1,2]
*/
public class P012 {

  // FIRST THOUGHT APPROACH
  // Time - O(N) Space - O(N)
  // class Solution {
  // public List<Integer> majorityElement(int[] arr) {
  // Map<Integer, Integer> freq_track = new HashMap<>();
  // int n = arr.length;
  // List<Integer> result = new ArrayList<>();

  // for(int i =0; i < arr.length; i++){
  // freq_track.put(arr[i], freq_track.getOrDefault(arr[i], 0) + 1);
  // }

  // for(Integer digit : freq_track.keySet()){
  // Integer freq = freq_track.get(digit);
  // if (freq > n/3){
  // result.add(digit);
  // }
  // }

  // return result;

  // }
  // }

  // Optimized - Time O(N) Space - O(1)

  class Solution {
    public List<Integer> majorityElement(int[] nums) {
      int n = nums.length;

      // Finding potential candidates as only 2 candidates can be in the array which
      // has to be greater than n/3;
      int candidate_1 = 0, candidate_2 = 0, count_1 = 0, count_2 = 0;

      for (int num : nums) {
        if (num == candidate_1) {
          count_1++;
        } else if (num == candidate_2) {
          count_2++;
        } else if (count_1 == 0) {
          candidate_1 = num;
          count_1 = 1;
        } else if (count_2 == 0) {
          candidate_2 = num;
          count_2 = 1;
        } else {
          count_1--;
          count_2--;
        }
      }

      // actual counts
      count_1 = count_2 = 0;
      for (int num : nums) {
        if (num == candidate_1)
          count_1++;
        else if (num == candidate_2)
          count_2++;
      }

      List<Integer> result = new ArrayList<>();
      if (count_1 > n / 3)
        result.add(candidate_1);
      if (count_2 > n / 3)
        result.add(candidate_2);

      return result;
    }
  }

  public static void main(String[] args) {
    P012 p012 = new P012();
    Solution solution = p012.new Solution();
    int[] arr = { 3, 2, 3 };
    System.out.println(solution.majorityElement(arr)); // Output: [3]

    int[] arr2 = { 1, 5, 6, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3 };
    System.out.println(solution.majorityElement(arr2));

    int[] arr3 = { 1, 2 };
    System.out.println(solution.majorityElement(arr3)); // Output: [1, 2]
  }
}
