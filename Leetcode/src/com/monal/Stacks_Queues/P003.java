package com.monal.Stacks_Queues;

import java.util.*;

/*
Given an array of integers heights representing the histogram's bar height where
the width of each bar is 1, return the area of the largest rectangle in the histogram.

Example 1:
  Input: heights = [2,1,5,6,2,3]
  Output: 10
  Explanation: The above is a histogram where width of each bar is 1.
  The largest rectangle is shown in the red area, which has an area = 10 units.
Example 2:
  Input: heights = [2,4]
  Output: 4

Constraints:
  1 <= heights.length <= 105
  0 <= heights[i] <= 104
 */
class P003 {

  class Solution {
    public int largestRectangleArea(int[] heights) {
      // We decide to use stack because it allows us to efficiently keep track
      // of the indices of the bars in the histogram. Tracking indices will help us
      // calculate the width of the rectangle formed by the bars
      Stack<Integer> stack = new Stack<>();
      int maxArea = 0; // Keep track of the maximum area found so far
      // Approach :
      // 1. Iterate through each bar in the histogram
      for (int i = 0; i <= heights.length; i++) {
        // We use a sentinel value at the end to ensure we process all bars
        int currentHeight = (i == heights.length) ? 0 : heights[i];

        // While the stack is not empty and the current height is less than the height
        // of the bar at the index stored at the top of the stack, we pop from the stack
        while (!stack.isEmpty() && currentHeight < heights[stack.peek()]) {
          int height = heights[stack.pop()]; // Get the height of the popped bar
          int width = stack.isEmpty() ? i : i - stack.peek() - 1; // Calculate width
          maxArea = Math.max(maxArea, height * width); // Update max area
        }
        stack.push(i); // Push current index onto the stack
      }

      return maxArea; // Return the maximum area found

    }
  }

  public static void main(String[] args) {
    Solution solution = new P003().new Solution();
    int[] heights1 = {2, 1, 5, 6, 2, 3};
    System.out.println(solution.largestRectangleArea(heights1)); // Output: 10

    int[] heights2 = {2, 4};
    System.out.println(solution.largestRectangleArea(heights2)); // Output: 4
  }
}
