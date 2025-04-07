package com.monal.Recursion;

import java.util.ArrayList;

/*
Given an array arr[] of length n and a number k, the task is to find all the subsequences of the array with sum of its elements equal to k.
Note: A subsequence is a subset that can be derived from an array by removing zero or more elements, without changing the order of the remaining elements.
Examples:
  Input: arr[] = [1, 2, 3], k = 3
  Output: [ [1, 2], [3] ]
  Explanation: All the subsequences of the given array are:
  [ [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3], [] ]
Out of which only two subsequences have sum of their elements equal to 3.

  Input: arr[] = [1, 2, 3], k = 7
  Output: []
  Explanation: Sum of all the elements of the array is 6, which is smaller than the required sum, thus they are no subsequences with sum of its elements equal to 7.

  Input: arr[] = [17, 18, 6, 11, 2, 4], k = 6
  Output: [ [2, 4], [6] ]
*/
public class P002 {

  /*
   * For every element in the array, there are two choices, either to include it
   * in the subsequence or not include it.
   * Apply this for every element in the array starting from index 0 until we
   * reach the last index.
   * Check if the sum of elements is equal to k, if so, store the subsequence in
   * the result array.
   */
  private static void findSubsequence(ArrayList<Integer> arr, int k) {
    ArrayList<ArrayList<Integer>> result = new ArrayList<>();
    ArrayList<Integer> processed = new ArrayList<>();

    // we have two choices for every elem in the array
    // either to include it in the subsequence or not include it
    helper_fn(arr, processed, 0, k, result);
    System.out.println(result);
  }

  private static void helper_fn(
      ArrayList<Integer> unprocessed, ArrayList<Integer> processed, int index, int k,
      ArrayList<ArrayList<Integer>> result) {
    // Base case
    if (index == unprocessed.size()) {
      int sum = 0;
      for (int i = 0; i < processed.size(); i++) {
        sum += processed.get(i);
      }
      if (sum == k) {
        result.add(new ArrayList<>(processed));
      }
      return;
    }

    // Include the current element
    processed.add(unprocessed.get(index));
    helper_fn(unprocessed, processed, index + 1, k, result);
    processed.remove(processed.size() - 1);

    // Donot include the current elem
    helper_fn(unprocessed, processed, index + 1, k, result);

  }

  public static void main(String[] args) {
    ArrayList<Integer> arr = new ArrayList<>();
    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(5);
    arr.add(4);
    arr.add(-1);
    arr.add(-2);
    int k = 5;
    findSubsequence(arr, k);
  }

}
