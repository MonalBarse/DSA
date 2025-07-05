package com.monal.Stacks_Queues;

import java.util.*;

/*
You are given an integer array nums. The range of a subarray of
nums is the difference between the largest and smallest element in the subarray.
Return the sum of all subarray ranges of nums.
A subarray is a contiguous non-empty sequence of elements within an array.

Example 1:
  Input: nums = [1,2,3]
  Output: 4
  Explanation: The 6 subarrays of nums are the following:
  [1], range = largest - smallest = 1 - 1 = 0
  [2], range = 2 - 2 = 0
  [3], range = 3 - 3 = 0
  [1,2], range = 2 - 1 = 1
  [2,3], range = 3 - 2 = 1
  [1,2,3], range = 3 - 1 = 2
  So the sum of all ranges is 0 + 0 + 0 + 1 + 1 + 2 = 4.

Example 2:
  Input: nums = [1,3,3]
  Output: 4
  Explanation: The 6 subarrays of nums are the following:
  [1], range = largest - smallest = 1 - 1 = 0
  [3], range = 3 - 3 = 0
  [3], range = 3 - 3 = 0
  [1,3], range = 3 - 1 = 2
  [3,3], range = 3 - 3 = 0
  [1,3,3], range = 3 - 1 = 2
  So the sum of all ranges is 0 + 0 + 0 + 2 + 0 + 2 = 4.

Example 3:
  Input: nums = [4,-2,-3,4,1]
  Output: 59
  Explanation: The sum of all subarray ranges of nums is 59.
*/
public class P013 {

  class Solution {
    public long subArrayRanges(int[] arr) {
      int n = arr.length;

      int[] pse = previousSmaller(arr); // strictly smaller
      int[] nse = nextSmallerOrEqual(arr); // smaller or equal

      // For maximum contribution (mirror pattern)
      int[] pge = previousGreater(arr); // strictly greater
      int[] nge = nextGreaterOrEqual(arr); // greater or equal

      long rangeSum = 0;

      for (int i = 0; i < n; i++) {
        // Count of subarrays where arr[i] is the minimum
        long leftSmallerCount = i - pse[i]; // number of elements to the left that are smaller
        long rightSmallerCount = nse[i] - i; // number of elements to the right that are smaller or equal
        long a = leftSmallerCount * rightSmallerCount; // total subarrays where arr[i] is the minimum

        // Count of subarrays where arr[i] is the maximum
        long leftGreaterCount = i - pge[i];
        long rightGreaterCount = nge[i] - i;
        long b = leftGreaterCount * rightGreaterCount; // total subarrays where arr[i] is the maximum

        long minContrib = arr[i] * a; // contribution of arr[i] as minimum
        long maxContrib = arr[i] * b; // contribution of arr[i] as maximum

        rangeSum += maxContrib - minContrib;
      }

      return rangeSum;
    }

    // Previous Smaller: strictly smaller (same as your working code)
    private int[] previousSmaller(int[] arr) {
      int n = arr.length;
      int[] res = new int[n];
      Deque<Integer> st = new ArrayDeque<>();

      for (int i = 0; i < n; i++) {
        while (!st.isEmpty() && arr[st.peek()] >= arr[i]) {
          st.pop();
        }
        res[i] = st.isEmpty() ? -1 : st.peek();
        st.push(i);
      }
      return res;
    }

    // Next Smaller or Equal: pop if >= (same as your working code)
    private int[] nextSmallerOrEqual(int[] arr) {
      int n = arr.length;
      int[] res = new int[n];
      Arrays.fill(res, n);
      Deque<Integer> st = new ArrayDeque<>();

      for (int i = 0; i < n; i++) {
        while (!st.isEmpty() && arr[st.peek()] >= arr[i]) {
          res[st.pop()] = i;
        }
        st.push(i);
      }
      return res;
    }

    // Previous Greater: strictly greater (mirror of previousSmaller)
    private int[] previousGreater(int[] arr) {
      int n = arr.length;
      int[] res = new int[n];
      Deque<Integer> st = new ArrayDeque<>();

      for (int i = 0; i < n; i++) {
        while (!st.isEmpty() && arr[st.peek()] <= arr[i]) {
          st.pop();
        }
        res[i] = st.isEmpty() ? -1 : st.peek();
        st.push(i);
      }
      return res;
    }

    // Next Greater or Equal: pop if <= (mirror of nextSmallerOrEqual)
    private int[] nextGreaterOrEqual(int[] arr) {
      int n = arr.length;
      int[] res = new int[n];
      Arrays.fill(res, n);
      Deque<Integer> st = new ArrayDeque<>();

      for (int i = 0; i < n; i++) {
        while (!st.isEmpty() && arr[st.peek()] <= arr[i]) {
          res[st.pop()] = i;
        }
        st.push(i);
      }
      return res;
    }
  }

  public static void main(String[] args) {
    P013 p013 = new P013();
    Solution solution = p013.new Solution();

    int[] nums1 = { 1, 2, 3 };
    System.out.println(solution.subArrayRanges(nums1)); // Output: 4

    int[] nums2 = { 1, 3, 3 };
    System.out.println(solution.subArrayRanges(nums2)); // Output: 4

    int[] nums3 = { 4, -2, -3, 4, 1 };
    System.out.println(solution.subArrayRanges(nums3)); // Output: 59

    System.out.println("The TIME COMPLEXITY is O(N) for each of the four helper methods, where N is the length of the input array. ");
    System.out.println("The SPACE COMPLEXITY is O(N) for the result arrays in each of the four helper methods.");
    System.out.println("The overall TIME COMPLEXITY is O(N) for the main method, as it iterates through the input array once and performs constant-time operations for each element.");
  }
}
