package com.monal.Stacks_Queues;

import java.util.*;
/*
https://leetcode.com/problems/sliding-window-maximum/description/

239. Sliding Window Maximum

You are given an array of integers nums, there is a sliding window of size k which is moving
from the very left of the array to the very right. You can only
see the k numbers in the window. Each time the sliding window moves right by one position.
Return the max sliding window.

Example 1:
  Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
  Output: [3,3,5,5,6,7]
  Explanation:
    Window position                Max
    ---------------               -----
    [1  3  -1] -3  5  3  6  7       3
    1 [3  -1  -3]  5  3  6  7       3
    1  3 [-1  -3  5]  3  6  7       5
    1  3  -1 [-3  5  3]  6  7       5
    1  3  -1  -3 [5  3  6]  7       6
    1  3  -1  -3  5 [3   6  7]      7
Example 2:
  Input: nums = [1], k = 1
  Output: [1]

Example 3:
  Input: nums = [2, 3, -12, -1, 5, -2, -1, 1, 2, 2, -2, -5, -1], k = 4
  Output: [3, 5, 5, 5, 5, 2, ,2 ,2 ,2 ]
*/

public class P007 {
  class Solution {
    public int[] maxSlidingWindow(int[] arr, int k) {
      int n = arr.length;
      int[] res = new int[n - k + 1];
      Deque<Integer> dq = new ArrayDeque<>();

      for (int i = 0; i < n; i++) {
        // 1) Evict out-of-window index
        if (!dq.isEmpty() && dq.peekFirst() == i - k) {
          dq.pollFirst();
        }
        // 2) Maintain decreasing order in deque:
        // drop any index whose value < arr[i]
        while (!dq.isEmpty() && arr[dq.peekLast()] < arr[i]) {
          dq.pollLast();
        }
        // 3) Add current index as a future/max candidate
        dq.offerLast(i);

        // 4) Once first full window is ready, record its max
        // (window [i-k+1 ... i])
        if (i >= k - 1) {
          res[i - k + 1] = arr[dq.peekFirst()];
        }
      }
      return res;
    }
  }

  public static void main(String[] args) {
    P007 p007 = new P007();
    Solution solution = p007.new Solution();
    int[] nums1 = { 1, 3, -1, -3, 5, 3, 6, 7 };
    int k1 = 3;
    System.out.println(Arrays.toString(solution.maxSlidingWindow(nums1, k1))); // Output: [3, 3, 5, 5, 6, 7]

    int[] nums2 = { 1 };
    int k2 = 1;
    System.out.println(Arrays.toString(solution.maxSlidingWindow(nums2, k2))); // Output: [1]

    int[] nums3 = { 2, 3, -12, -1, 5, -2, -1, 1, 2, 2, -2, -5, -1 };
    int k3 = 4;
    System.out.println(Arrays.toString(solution.maxSlidingWindow(nums3, k3))); // Output: [3, 5, 5, 5, 5, 2]
  }

}
