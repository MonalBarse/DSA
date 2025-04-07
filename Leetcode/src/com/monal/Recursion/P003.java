package com.monal.Recursion;
// COMBINATION SUM PROBLEMS

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P003 {

  /*
   * COMBINATION SUM 1
   * Given an array of distinct integers candidates and a target integer target,
   * return a list of all unique combinations of candidates where the chosen
   * numbers sum to target. You may return the combinations in any order.
   * The same number may be chosen from candidates an unlimited number of times.
   * Two combinations are unique if the frequency of at least one of the chosen
   * numbers is different.
   * The test cases are generated such that the number of unique combinations that
   * sum up to target is less than 150 combinations for the given input.
   *
   * Example 1:
   * Input: candidates = [2,3,6,7], target = 7
   * Output: [[2,2,3],[7]]
   * Explanation:
   * 2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple
   * times, 7 is a candidate, and 7 = 7. These are the only two combinations.
   *
   * Example 2:
   * Input: candidates = [2,3,5], target = 8
   * Output: [[2,2,2,2],[2,3,3],[3,5]]
   *
   * Example 3:
   * Input: candidates = [2], target = 1
   * Output: []
   */
  public List<List<Integer>> combinationSum1(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    helper_fn(candidates, target, 0, new ArrayList<>(), result);
    return result;
  }

  // Here we had two choices for every element in the array
  public void helper_fn(int[] arr, int remaining, int idx, ArrayList<Integer> current, List<List<Integer>> result) {
    // when remaiing == 0, we have found a valid combination
    if (remaining == 0) {
      result.add(new ArrayList<>(current));
      return;
    }
    // if remaining < 0, we have exceeded the target
    if (remaining < 0) {
      return;
    }

    // Include the current elem
    current.add(arr[idx]);
    helper_fn(arr, remaining - arr[idx], idx, current, result); // not incrementing idx as we can use the same element
    current.remove(current.size() - 1); // backtrack

    // exclude the current elem
    if (idx + 1 < arr.length) {
      helper_fn(arr, remaining, idx + 1, current, result);
    }
  }

  // -------------------------------------------------------------------- //

  /*
   * COMBINATION SUM 2
   * Given a collection of candidate numbers (candidates) and a target number
   * (target),
   * find all unique combinations in candidates where the candidate numbers sum to
   * target.
   * Each number in candidates may only be used once in the combination.
   * Note: The solution set must not contain duplicate combinations.
   *
   * Example 1:
   * Input: candidates = [10,1,2,7,6,1,5], target = 8
   * Output:
   * [
   * [1,1,6],
   * [1,2,5],
   * [1,7],
   * [2,6]
   * ]
   *
   * Example 2:
   * Input: candidates = [2,5,2,1,2], target = 5
   * Output:
   * [
   * [1,2,2],
   * [5]
   * ]
   */

  public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    List<List<Integer>> result = new ArrayList<>();
    Arrays.sort(candidates);
    helper_fn2(candidates, target, 0, new ArrayList<>(), result);
    return result;
  }

  // Here unlike prev prob, we donot have two choices, we need to include curr
  // elem
  // if its not duplicate of previous elem
  public void helper_fn2(int[] arr, int remaining, int idx, ArrayList<Integer> current, List<List<Integer>> result) {
    // when remaiing == 0, we have found a valid combination
    if (remaining < 0)
      return;
    if (remaining == 0) {
      result.add(new ArrayList<>(current));
      return;
    }

    for (int i = idx; i < arr.length; i++) {
      if (i > idx && arr[i] == arr[i - 1]) {
        continue;
      }
      current.add(arr[i]);
      helper_fn2(arr, remaining - arr[i], i + 1, current, result);
      current.remove(current.size() - 1);
    }
  }

  // -------------------------------------------------------------------- //
  /*
   * COMBINATION SUM 3
   * Find all valid combinations of k numbers that sum up to n such that the
   * following conditions are true:
   * Only numbers 1 through 9 are used.
   * Each number is used at most once.
   * Return a list of all possible valid combinations. The list must not contain
   * the same combination twice, and the combinations may be returned in any
   * order.
   *
   * Example 1:
   * Input: k = 3, n = 7
   * Output: [[1,2,4]]
   * Explanation:
   * 1 + 2 + 4 = 7
   * There are no other valid combinations.
   *
   * Example 2:
   * Input: k = 3, n = 9
   * Output: [[1,2,6],[1,3,5],[2,3,4]]
   * Explanation:
   * 1 + 2 + 6 = 9
   * 1 + 3 + 5 = 9
   * 2 + 3 + 4 = 9
   * There are no other valid combinations.
   *
   * Example 3:
   * Input: k = 4, n = 1
   * Output: []
   * Explanation: There are no valid combinations.
   * Using 4 different numbers in the range [1,9], the smallest sum we can get is
   * 1+2+3+4 = 10 and since 10 > 1, there are no valid combination.
   */

  public List<List<Integer>> combinationSum3(int k, int n) {
    List<List<Integer>> result = new ArrayList<>();
    helper_fn3(k, n, 1, new ArrayList<>(), result);
    return result;
  }

  public void helper_fn3(int k, int remaining, int idx, ArrayList<Integer> current, List<List<Integer>> result) {
    if (remaining < 0)
      return;
    if (remaining == 0 && current.size() == k) {
      result.add(new ArrayList<>(current));
      return;
    }

    // we can only use numbers from 1 to 9
    for (int i = idx; i < 10; i++) {
      current.add(i);
      helper_fn3(k, remaining - i, i + 1, current, result);
      current.remove(current.size() - 1);
    }
  }

  public static void main(String[] args) {

    P003 p003 = new P003();

    int[] arr1 = { 2, 3, 6, 7 };
    int[] arr2 = { 10, 1, 2, 7, 6, 1, 5 };
    int[] arr3 = { 2, 5, 2, 1, 2 };
    int[] arr4 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    System.out.println("Combination Sum 1: " + p003.combinationSum1(arr1, 7));
    System.out.println("Combination Sum 1: " + p003.combinationSum1(arr4, 8));
    System.out.println("Combination Sum 2: " + p003.combinationSum2(arr2, 8));
    System.out.println("Combination Sum 2: " + p003.combinationSum2(arr3, 5));
    System.out.println("Combination Sum 3: " + p003.combinationSum3(3, 7));
    System.out.println("Combination Sum 3: " + p003.combinationSum3(3, 9));

  }

}
