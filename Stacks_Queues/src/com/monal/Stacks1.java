package com.monal;

import java.util.*;

public class Stacks1 {

    /**
     * STACK PATTERN MASTERY - JAVA IMPLEMENTATIONS
     * Complete boilerplate code for common stack patterns in technical interviews
     */

    // =========== PATTERN 1: PARENTHESES/BRACKET MATCHING ========== //

    /**
     * BOILERPLATE: Bracket Matching Pattern
     * Template for all parentheses/bracket validation problems
     * Time: O(n), Space: O(n)
     */
    class BracketMatchingTemplate {
        public boolean isValid(String s) {
            // Stack to store opening brackets
            Stack<Character> stack = new Stack<>();

            // Map for bracket pairs (closing -> opening)
            Map<Character, Character> brackets = new HashMap<>();
            brackets.put(')', '(');
            brackets.put('}', '{');
            brackets.put(']', '[');

            for (char c : s.toCharArray()) {
                if (brackets.containsKey(c)) {
                    // Closing bracket: check if matches top of stack
                    if (stack.isEmpty() || stack.pop() != brackets.get(c)) {
                        return false;
                    }
                } else {
                    // Opening bracket: push to stack
                    stack.push(c);
                }
            }

            // Valid if all brackets are matched (stack is empty)
            return stack.isEmpty();
        }
    }

    /**
     * LEETCODE 20: Valid Parentheses
     * Given string with '()', '{}', '[]', determine if brackets are valid
     */
    class ValidParentheses {
        public boolean isValid(String s) {
            Stack<Character> stack = new Stack<>();

            for (char c : s.toCharArray()) {
                // Push opening brackets
                if (c == '(' || c == '{' || c == '[') {
                    stack.push(c);
                }
                // Check closing brackets
                else if (c == ')' || c == '}' || c == ']') {
                    if (stack.isEmpty())
                        return false;

                    char top = stack.pop();
                    if ((c == ')' && top != '(') ||
                            (c == '}' && top != '{') ||
                            (c == ']' && top != '[')) {
                        return false;
                    }
                }
            }

            return stack.isEmpty();
        }
    }

    // ==================== PATTERN 2: MONOTONIC STACK ====================

    /**
     * BOILERPLATE: Monotonic Stack Pattern
     * Template for next/previous greater/smaller element problems
     */
    class MonotonicStackTemplate {

        /**
         * Find next greater element for each element in array
         * Maintains decreasing monotonic stack
         */
        public int[] nextGreaterElements(int[] nums) {
            int n = nums.length;
            int[] result = new int[n];
            Arrays.fill(result, -1); // Default: no greater element found

            Stack<Integer> stack = new Stack<>(); // Store indices

            for (int i = 0; i < n; i++) {
                // Pop elements smaller than current element
                while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                    int index = stack.pop();
                    result[index] = nums[i]; // nums[i] is next greater for nums[index]
                }
                stack.push(i);
            }
            return result;
        }

        /**
         * Find next smaller element for each element in array
         * Maintains increasing monotonic stack
         */
        public int[] nextSmallerElements(int[] nums) {
            int n = nums.length;
            int[] result = new int[n];
            Arrays.fill(result, -1);

            Stack<Integer> stack = new Stack<>();

            for (int i = 0; i < n; i++) {
                // Pop elements greater than current element
                while (!stack.isEmpty() && nums[stack.peek()] > nums[i]) {
                    int index = stack.pop();
                    result[index] = nums[i];
                }
                stack.push(i);
            }

            return result;
        }
    }

    class MonotonicRevStackTemplate {

        /**
         * Find next greater element for each element in array
         * Maintains decreasing monotonic stack
         * Uses reverse iteration (right to left)
         */
        public int[] nextGreaterElements(int[] nums) {
            int n = nums.length;
            int[] result = new int[n];
            Arrays.fill(result, -1); // Default: no greater element

            Stack<Integer> stack = new Stack<>(); // stores elements, not indices

            for (int i = n - 1; i >= 0; i--) {
                // Pop elements smaller than or equal to current
                while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                    stack.pop();
                }

                // If stack is not empty, top is the next greater
                if (!stack.isEmpty()) {
                    result[i] = stack.peek();
                }

                // Push current element for use in future iterations
                stack.push(nums[i]);
            }

            return result;
        }

        /**
         * Find next smaller element for each element in array
         * Maintains increasing monotonic stack
         * Uses reverse iteration (right to left)
         */
        public int[] nextSmallerElements(int[] nums) {
            int n = nums.length;
            int[] result = new int[n];
            Arrays.fill(result, -1);

            Stack<Integer> stack = new Stack<>(); // stores elements

            for (int i = n - 1; i >= 0; i--) {
                // Remove all elements greater than or equal to current
                while (!stack.isEmpty() && stack.peek() >= nums[i]) {
                    stack.pop();
                }

                // If stack is not empty, top is the next smaller
                if (!stack.isEmpty()) {
                    result[i] = stack.peek();
                }

                // Push current element for future comparisons
                stack.push(nums[i]);
            }

            return result;
        }
    }

    /**
     * LEETCODE 739: Daily Temperatures
     * Given an array of integers temperatures represents the daily temperatures,
     * return an array answer such that answer[i] is the number of days you have
     * to wait after the ith day to get a warmer temperature.
     * If there is no future day for which this is possible, keep answer[i] == 0
     * instead.
     */
    class DailyTemperatures {
        public int[] dailyTemperatures(int[] temperatures) {
            int n = temperatures.length;
            int[] answer = new int[n];
            Stack<Integer> stack = new Stack<>(); // Store indices

            for (int i = 0; i < n; i++) {
                // While current temp is warmer than stack top
                while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                    int prevIndex = stack.pop();
                    answer[prevIndex] = i - prevIndex; // Days to wait
                }
                stack.push(i);
            }
            return answer; // Remaining elements default to 0 (no warmer day)
        }
    }

    /**
     * LEETCODE 496: Next Greater Element I
     * Find next greater element for each element in nums1 from nums2
     */
    class NextGreaterElementI {
        public int[] nextGreaterElement(int[] nums1, int[] nums2) {
            // Precompute next greater elements for nums2
            Map<Integer, Integer> nextGreater = new HashMap<>();
            Stack<Integer> stack = new Stack<>();

            for (int num : nums2) {
                while (!stack.isEmpty() && stack.peek() < num) {
                    nextGreater.put(stack.pop(), num);
                }
                stack.push(num);
            }

            // Build result for nums1
            int[] result = new int[nums1.length];
            for (int i = 0; i < nums1.length; i++) {
                result[i] = nextGreater.getOrDefault(nums1[i], -1);
            }

            return result;
        }

        /**
         * LEETCODE 503: Next Greater Element II
         * Given a circular integer array nums (i.e., the next element of
         * nums[nums.length - 1]
         * is nums[0]), return the next greater number for every element in nums.
         * The next greater number of a number x is the first greater number to its
         * traversing-order
         * next in the array, which means you could search circularly to find its next
         * greater number.
         * If it doesn't exist, return -1 for this number.
         */
        public int[] nextGreaterElementsCircular(int[] arr) {
            int n = arr.length;
            int[] result = new int[n];
            Arrays.fill(result, -1); // Default: no greater element found

            Stack<Integer> stack = new Stack<>(); // Store indices

            // Iterate twice to handle circular nature
            for (int i = 0; i < 2 * n; i++) {
                int index = i % n; // Wrap around for circular array
                // Pop elements smaller than current element
                while (!stack.isEmpty() && arr[stack.peek()] < arr[index]) {
                    int idx = stack.pop();
                    result[idx] = arr[index]; // arr[index] is next greater for arr[idx]
                }
                if (i < n)
                    stack.push(index); // Only push indices for first pass
            }
            return result;
        }

        /*
         * Next Smaller Element II
         * Given a circular integer array nums, return the next smaller number for every
         * element in nums
         * The next smaller number of a number x is the first smaller number to its
         * traversing-order
         * next in the array, which means you could search circularly to find its next
         * smaller
         */

        public int[] nextSmallerElementsCircular(int[] arr) {
            int n = arr.length;
            int[] result = new int[n];
            Arrays.fill(result, -1); // Default: no smaller element found
            Stack<Integer> stack = new Stack<>(); // Store indices

            // Iterate twice to handle circular nature
            for (int i = 0; i < 2 * n; i++) {
                int index = i % n; // Wrap around for circular array
                // Pop elements greater than current element
                while (!stack.isEmpty() && arr[stack.peek()] > arr[index]) {
                    int idx = stack.pop();
                    result[idx] = arr[index]; // arr[index] is next smaller for arr[idx]
                }
                if (i < n)
                    stack.push(index); // Only push indices for first pass
            }
            return result;
        }

        /*
         * TRAPPING RAIN WATER LEETCODE 42
         * Given n non-negative integers representing an elevation map where the width
         * of each bar is 1, compute how much water it can trap after raining.
         *
         * Approach:
         * 1. Use Stack to maintain indices of bars in decreasing height order.
         * 2. For each bar, calculate trapped water based on the height of the bar
         * and the height of the bar at the top of the stack.
         * 3. Pop from stack until the current bar is taller than the top of the
         * stack or the stack is empty.
         * 4. Calculate trapped water using the formula:
         * water += (Math.min(height[i], height[stack.peek()]) - height[stack.pop()]) *
         * (i - stack.peek() - 1);
         * 5. Continue until all bars are processed.
         */

        public int trap(int[] height) {
            int n = height.length;
            if (n == 0) return 0; // No bars, no water
            int waterTrapped = 0;
            // max height of left
            int maxLeft [] = new int[n], maxRight [] = new int[n];
            maxLeft[0] = height[0]; maxRight[n-1] = height[n-1];
            // Fill maxLeft array
            for (int i = 1; i < n; i++)
                maxLeft[i] = Math.max(maxLeft[i - 1], height[i]);
            for (int i = n-2; i >= 0; i--)
                maxRight[i] = Math.max(maxRight[i + 1], height[i]);
            // Calculate water trapped using maxLeft and maxRight
            for (int i = 0; i < n; i++)
                waterTrapped += Math.min(maxLeft[i], maxRight[i]) - height[i];
                // Water trapped at current index is the minimum of maxLeft and maxRight
            return waterTrapped;
        }

        public int trapStack(int []height) {
            int n = height.length;
            if (n == 0) return 0; // No bars, no water
            int waterTrapped = 0;
            Stack<Integer> stack = new Stack<>(); // Store indices

            for (int i = 0; i < n; i++) {
                // While current height is greater than height at stack top
                while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                    int topIndex = stack.pop();
                    if (stack.isEmpty()) break; // No left boundary, break

                    // Calculate width and height for trapped water
                    int width = i - stack.peek() - 1;
                    int boundedHeight = Math.min(height[i], height[stack.peek()]) - height[topIndex];
                    waterTrapped += width * boundedHeight;
                }
                stack.push(i); // Push current index to stack
            }
            return waterTrapped;
        }

        public int trapTwoPointer(int[] height) {
            int n= height.length;
            int left = 0, right = n - 1, waterTrapped = 0, maxLeft = 0, maxRight = 0;

            while(left<right){
                if(height[left] <= height[right]){
                    if(height[left] >= maxLeft) maxLeft = height[left];
                    else waterTrapped += maxLeft - height[left];
                    left++;
                }else{
                    if(height[right] >= maxRight) maxRight = height[right];
                    else waterTrapped += maxRight - height[right];
                    right--;
                }
            }
            return waterTrapped;
        }

    }

    // ==================== PATTERN 3: EXPRESSION EVALUATION ====================

    /**
     * BOILERPLATE: Expression Evaluation Pattern
     * Template for calculator and expression parsing problems
     */
    class ExpressionEvaluationTemplate {

        /**
         * Evaluate Reverse Polish Notation (Postfix)
         * Operators come after operands: "3 4 + 2 *" = (3+4)*2 = 14
         */
        public int evalRPN(String[] tokens) {
            Stack<Integer> stack = new Stack<>();

            for (String token : tokens) {
                if (isOperator(token)) {
                    // Pop two operands (order matters for - and /)
                    int b = stack.pop();
                    int a = stack.pop();
                    int result = calculate(a, b, token);
                    stack.push(result);
                } else {
                    // Push operand
                    stack.push(Integer.parseInt(token));
                }
            }

            return stack.pop(); // Final result
        }

        private boolean isOperator(String token) {
            return token.equals("+") || token.equals("-") ||
                    token.equals("*") || token.equals("/");
        }

        private int calculate(int a, int b, String operator) {
            switch (operator) {
                case "+":
                    return a + b;
                case "-":
                    return a - b;
                case "*":
                    return a * b;
                case "/":
                    return a / b;
                default:
                    throw new IllegalArgumentException("Invalid operator");
            }
        }
    }

    /**
     * LEETCODE 224: Basic Calculator
     * Evaluate expression with +, -, (, ) and spaces
     */
    class BasicCalculator {
        public int calculate(String s) {
            Stack<Integer> stack = new Stack<>();
            int result = 0;
            int number = 0;
            int sign = 1; // 1 for positive, -1 for negative

            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    number = number * 10 + (c - '0');
                } else if (c == '+') {
                    result += sign * number;
                    number = 0;
                    sign = 1;
                } else if (c == '-') {
                    result += sign * number;
                    number = 0;
                    sign = -1;
                } else if (c == '(') {
                    // Push current result and sign to stack
                    stack.push(result);
                    stack.push(sign);
                    // Reset for new sub-expression
                    result = 0;
                    sign = 1;
                } else if (c == ')') {
                    result += sign * number;
                    number = 0;
                    // Pop sign and previous result
                    result *= stack.pop(); // Previous sign
                    result += stack.pop(); // Previous result
                }
            }

            return result + (sign * number);
        }
    }

    // ==================== PATTERN 4: STACK SIMULATION ====================

    /**
     * LEETCODE 155: Min Stack
     * Design stack that supports push, pop, top, and retrieving minimum in O(1)
     */
    class MinStack {
        private Stack<Integer> stack;
        private Stack<Integer> minStack; // Keeps track of minimums

        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int val) {
            stack.push(val);
            // Push to minStack if it's new minimum or equal to current min
            if (minStack.isEmpty() || val <= minStack.peek()) {
                minStack.push(val);
            }
        }

        public void pop() {
            int popped = stack.pop();
            // Remove from minStack if it was the minimum
            if (popped == minStack.peek()) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    /**
     * LEETCODE 682: Baseball Game
     * Calculate final score based on operations
     */
    class BaseballGame {
        public int calPoints(String[] operations) {
            Stack<Integer> stack = new Stack<>();

            for (String op : operations) {
                switch (op) {
                    case "C":
                        // Cancel last score
                        stack.pop();
                        break;
                    case "D":
                        // Double last score
                        stack.push(2 * stack.peek());
                        break;
                    case "+":
                        // Sum of last two scores
                        int last = stack.pop();
                        int secondLast = stack.peek();
                        stack.push(last);
                        stack.push(last + secondLast);
                        break;
                    default:
                        // Regular score
                        stack.push(Integer.parseInt(op));
                        break;
                }
            }

            // Sum all scores
            int total = 0;
            while (!stack.isEmpty()) {
                total += stack.pop();
            }

            return total;
        }
    }

    // ==================== ADVANCED PATTERN: DECODE STRING ====================

    /**
     * LEETCODE 394: Decode String
     * Decode string with pattern k[encoded_string]
     * Example: "3[a2[c]]" -> "accaccacc"
     */
    class DecodeString {
        public String decodeString(String s) {
            Stack<Integer> countStack = new Stack<>(); // Store repeat counts
            Stack<StringBuilder> stringStack = new Stack<>(); // Store partial strings

            StringBuilder currentString = new StringBuilder();
            int currentCount = 0;

            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    currentCount = currentCount * 10 + (c - '0');
                } else if (c == '[') {
                    // Start new level: save current state
                    countStack.push(currentCount);
                    stringStack.push(currentString);
                    // Reset for new level
                    currentCount = 0;
                    currentString = new StringBuilder();
                } else if (c == ']') {
                    // Complete current level: repeat and merge
                    int repeatCount = countStack.pop();
                    StringBuilder temp = currentString;
                    currentString = stringStack.pop();

                    // Repeat temp string repeatCount times
                    for (int i = 0; i < repeatCount; i++) {
                        currentString.append(temp);
                    }
                } else {
                    // Regular character
                    currentString.append(c);
                }
            }

            return currentString.toString();
        }
    }

    /**
     * PRACTICE PROBLEMS TO MASTER:
     *
     * Easy:
     * - LC 20: Valid Parentheses ✓
     * - LC 155: Min Stack ✓
     * - LC 682: Baseball Game ✓
     * - LC 1047: Remove All Adjacent Duplicates In String
     *
     * Medium:
     * - LC 739: Daily Temperatures ✓
     * - LC 496: Next Greater Element I ✓
     * - LC 394: Decode String ✓
     * - LC 150: Evaluate Reverse Polish Notation ✓
     * - LC 503: Next Greater Element II
     * - LC 856: Score of Parentheses
     *
     * Hard:
     * - LC 84: Largest Rectangle in Histogram
     * - LC 42: Trapping Rain Water
     * - LC 85: Maximal Rectangle
     * - LC 224: Basic Calculator ✓
     */
}
