package com.monal.Stacks_Queues;

import java.util.*;

// LEETCODE 907. Sum of Subarray Minimums (https://leetcode.com/problems/sum-of-subarray-minimums/)

// Given an array of integers arr, find the sum of min(b), where b ranges
// over every (contiguous) subarray of arr.
// Since the answer may be large, return the answer modulo 109 + 7.

public class P009 {
  public int sumSubarrayMins(int[] arr) {
    int n = arr.length;
    int[] pse = previousSmallerElements(arr); // Previous Smaller Elements
    int[] nse = nextSmallerElements(arr); // Next Smaller Elements

    long sum = 0;

    for (int i = 0; i < n; i++) {
      // Calculate the number of subarrays where arr[i] is the minimum
      int leftCount = i - (pse[i] == -1 ? -1 : pse[i]); // since pse[i] gives the previous smaller element's index, we
                                                        // subtract it from i to get the count of elements to the left
      int rightCount = (nse[i] == -1 ? n : nse[i]) - i;// since nse[i] gives the next smaller element's index, we
                                                       // subtract i to get the count of elements to the right

      // Contribution of arr[i] as the minimum in all these subarrays
      sum += (long) arr[i] * leftCount * rightCount;
    }

    return (int) (sum % 1000000007); // Return the result modulo 10^9 + 7

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
    Arrays.fill(res, -1);
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

public class NextElements {

  /**
   * MONOTONIC STACK FUNDAMENTALS:
   *
   * A monotonic stack maintains elements in either increasing or decreasing
   * order.
   * When we encounter an element that breaks the monotonic property, we pop
   * elements
   * from the stack until the property is restored.
   *
   * KEY INSIGHT: The popping process is what gives us the "next" relationships!
   * - When we pop element X because of element Y, then Y is the "next
   * greater/smaller" of X
   *
   * PATTERN RECOGNITION:
   * - Next Greater/Smaller: Process left to right, pop when condition is met
   * - Previous Greater/Smaller: Process right to left, pop when condition is met
   *
   * WHY O(n) TIME COMPLEXITY?
   * Each element is pushed exactly once and popped at most once → O(n) total
   * operations
   */

  // NGE - Next Greater Elements
  // Given an array, find the next greater element for each element in the array
  public int[] nextGreaterElements(int[] arr) {
    int n = arr.length;
    int[] res = new int[n];
    Arrays.fill(res, -1); // Default: no next greater element found

    // MONOTONIC DECREASING STACK (stores indices)
    // Why decreasing? We want to find GREATER elements, so we maintain smaller ones
    // in stack
    Stack<Integer> stack = new Stack<>();

    // Process left to right for "NEXT" elements
    for (int i = 0; i < n; i++) {

      // CORE LOGIC: Pop all elements smaller than current element
      // WHY? Because arr[i] is the "next greater" for all these popped elements
      while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
        int poppedIndex = stack.pop();
        res[poppedIndex] = arr[i]; // arr[i] is next greater for arr[poppedIndex]
      }

      // Push current index - it might be next greater for future elements
      stack.push(i);
    }

    // Elements remaining in stack have no next greater element (already initialized
    // to -1)
    return res;
  }

  // NSE - Next Smaller Elements
  // Given an array, find the next smaller element for each element in the array
  public int[] nextSmallerElements(int[] arr) {
    int n = arr.length;
    int[] res = new int[n];
    Arrays.fill(res, -1); // Default: no next smaller element found

    // MONOTONIC INCREASING STACK (stores indices)
    // Why increasing? We want to find SMALLER elements, so we maintain larger ones
    // in stack
    Stack<Integer> stack = new Stack<>();

    // Process left to right for "NEXT" elements
    for (int i = 0; i < n; i++) {

      // CORE LOGIC: Pop all elements greater than current element
      // WHY? Because arr[i] is the "next smaller" for all these popped elements
      while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
        int poppedIndex = stack.pop();
        res[poppedIndex] = arr[i]; // arr[i] is next smaller for arr[poppedIndex]
      }

      // Push current index - it might be next smaller for future elements
      stack.push(i);
    }

    return res;
  }

  // PGE - Previous Greater Elements
  // Given an array, find the previous greater element for each element in the
  // array
  public int[] previousGreaterElements(int[] arr) {
    int n = arr.length;
    int[] res = new int[n];
    Arrays.fill(res, -1); // Default: no previous greater element found

    // MONOTONIC DECREASING STACK (stores indices)
    // For PREVIOUS elements, we process LEFT TO RIGHT and check stack top
    Stack<Integer> stack = new Stack<>();

    // Process left to right for "PREVIOUS" elements
    for (int i = 0; i < n; i++) {
      // CORE LOGIC: Pop all elements <= current element to maintain decreasing stack
      // We want to find the previous GREATER element for current element
      while (!stack.isEmpty() && arr[stack.peek()] <= arr[i])
        stack.pop(); // Remove elements that are not greater than current

      // If stack is not empty, top element is the previous greater element
      if (!stack.isEmpty())
        res[i] = arr[stack.peek()]; // Previous greater element found
      // Push current index - it might be previous greater for future elements
      stack.push(i);
    }

    return res;
  }

  // PSE - Previous Smaller Elements
  // Given an array, find the previous smaller element for each element in the
  // array
  public int[] previousSmallerElements(int[] arr) {
    int n = arr.length;
    int[] res = new int[n];
    Arrays.fill(res, -1); // Default: no previous smaller element found

    // MONOTONIC INCREASING STACK (stores indices)
    // For PREVIOUS elements, we process LEFT TO RIGHT and check stack top
    Stack<Integer> stack = new Stack<>();

    // Process left to right for "PREVIOUS" elements
    for (int i = 0; i < n; i++) {

      // CORE LOGIC: Pop all elements >= current element to maintain increasing stack
      // We want to find the previous SMALLER element for current element
      while (!stack.isEmpty() && arr[stack.peek()] >= arr[i])
        stack.pop(); // Remove elements that are not smaller than current

      // If stack is not empty, top element is the previous smaller element
      if (!stack.isEmpty())
        res[i] = arr[stack.peek()]; // Previous smaller element found

      // Push current index - it might be previous smaller for future elements
      stack.push(i);
    }

    return res;
  }

  /**
   * VISUAL EXAMPLE for NGE with array [2, 1, 2, 4, 3, 1]:
   *
   * i=0, arr[0]=2: stack=[0]
   * i=1, arr[1]=1: stack=[0,1] (1 < 2, so no popping)
   * i=2, arr[2]=2: arr[2] > arr[1], so pop 1 → res[1]=2
   * stack=[0,2] (2 == 2, so no popping of index 0)
   * i=3, arr[3]=4: arr[3] > arr[2], so pop 2 → res[2]=4
   * arr[3] > arr[0], so pop 0 → res[0]=4
   * stack=[3]
   * i=4, arr[4]=3: stack=[3,4] (3 < 4, so no popping)
   * i=5, arr[5]=1: stack=[3,4,5] (1 < 3, so no popping)
   *
   * Final result: [4, 2, 4, -1, -1, -1]
   *
   * VISUAL EXAMPLE for PGE with array [2, 1, 2, 4, 3, 1]:
   *
   * i=0, arr[0]=2: stack empty, res[0]=-1, stack=[0]
   * i=1, arr[1]=1: stack=[0], arr[0]=2 > arr[1]=1, so res[1]=2, stack=[0,1]
   * i=2, arr[2]=2: pop 1 (arr[1]=1 < arr[2]=2), stack=[0], arr[0]=2 == arr[2]=2,
   * so pop 0 too
   * stack empty, res[2]=-1, stack=[2]
   * i=3, arr[3]=4: stack=[2], arr[2]=2 < arr[3]=4, so res[3]=-1, stack=[2,3]
   * i=4, arr[4]=3: stack=[2,3], arr[3]=4 > arr[4]=3, so res[4]=4, stack=[2,3,4]
   * i=5, arr[5]=1: stack=[2,3,4], arr[4]=3 > arr[5]=1, so res[5]=3,
   * stack=[2,3,4,5]
   *
   * Final result: [-1, 2, -1, -1, 4, 3]
   *
   * KEY DIFFERENCE:
   * - NEXT problems: We pop from stack and SET result for POPPED elements
   * - PREVIOUS problems: We pop from stack to FIND result for CURRENT element
   *
   * MEMORY TRICK:
   * - NEXT problems: Process LEFT→RIGHT, pop when condition met, set result for
   * popped elements
   * - PREVIOUS problems: Process LEFT→RIGHT, pop to maintain stack, check stack
   * top for current element
   * - GREATER problems: Use decreasing stack (keep smaller elements)
   * - SMALLER problems: Use increasing stack (keep larger elements)
   */

  /**
   * COMMON APPLICATIONS:
   *
   * 1. Sum of Subarray Minimums: Use PSE + NSE to find range where each element
   * is minimum
   * 2. Largest Rectangle in Histogram: Use PSE + NSE to find width of rectangle
   * for each bar
   * 3. Trapping Rain Water: Use PGE + NGE to find water level boundaries
   * 4. Daily Temperatures: Direct application of NGE
   * 5. Stock Span Problem: Use PGE to find consecutive smaller elements
   *
   * The key insight is that these problems all involve finding "ranges of
   * influence"
   * for each element, which is exactly what monotonic stacks excel at!
   */
}
}
