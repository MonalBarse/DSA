package com.monal.Stacks_Queues;

import java.util.*;

// Leetcode 84. Largest Rectangle in Histogram (https://leetcode.com/problems/largest-rectangle-in-histogram/)
/*
  Given an array of integers heights representing the histogram's bar height
  where the width of each bar is 1, return the area of the
  largest rectangle in the histogram.
 */
public class P011 {
  // APPROACH: we can use a two arrays to keep track of the previous and next
  // greater elements
  // for each element in the histogram. Then we can calculate the area of the
  // rectangle
  // formed by each bar using the formula:
  // area = heights[i]th element * how far we can extend to the left and right

public int largestRectangleArea(int[] heights) {
  int n = heights.length;
  int[] pse = previousSmallerElements(heights); // index of previous smaller
  int[] nse = nextSmallerElements(heights);     // index of next smaller

  int maxArea = 0;
  for (int i = 0; i < n; i++) {
    int left = pse[i];
    int right = nse[i];
    int width = right - left - 1;  // careful! boundaries excluded
    maxArea = Math.max(maxArea, heights[i] * width);
  }
  return maxArea;
}

  private int[] previousSmallerElements(int[] arr) {
    int n = arr.length;
    int[] res = new int[n];
    Arrays.fill(res, -1);
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && arr[stack.peek()] >= arr[i])
        stack.pop();
      if (!stack.isEmpty())
        res[i] = stack.peek(); // ← Return INDEX, not value
      stack.push(i);
    }
    return res;
  }

  private int[] nextSmallerElements(int[] arr) {
    int n = arr.length;
    int[] res = new int[n];
    Arrays.fill(res, n); // ✅ Fix: fill with n instead of -1
    Stack<Integer> stack = new Stack<>();
    for (int i = 0; i < n; i++) {
      while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
        int poppedIndex = stack.pop();
        res[poppedIndex] = i; // ← Return INDEX, not value
      }
      stack.push(i);
    }
    return res;
  }

}
