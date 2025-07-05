package com.monal;

import java.util.*;
public class Stacks2 {

/**
 * ADDITIONAL STACK PRACTICE PROBLEMS
 * Master these to solidify your understanding of stack patterns
 */

// ============ PATTERN REINFORCEMENT PROBLEMS =========== //

/**
 * LC 1047: Remove All Adjacent Duplicates In String
 * Given string, remove all adjacent duplicates
 * Example: "abbaca" -> "ca" (remove bb, then aa)
 */
class RemoveAdjacentDuplicates {
    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            // If current char matches top of stack, remove both (adjacent duplicates)
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                // Otherwise, add current char to stack
                stack.push(c);
            }
        }

        // Build result string from stack
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.reverse().toString();
    }

    // Optimized version using StringBuilder directly
    public String removeDuplicatesOptimized(String s) {
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            // Check if last character matches current
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) == c) {
                sb.deleteCharAt(sb.length() - 1); // Remove last char
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }
}

/**
 * LC 503: Next Greater Element II (Circular Array)
 * Find next greater element in circular array
 * Example: [1,2,1] -> [2,-1,2] (for last 1, next greater is 2 at index 0)
 */
class NextGreaterElementII {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1); // Default: no greater element

        Stack<Integer> stack = new Stack<>(); // Store indices

        // Traverse array twice to simulate circular behavior
        for (int i = 0; i < 2 * n; i++) {
            int currentIndex = i % n; // Circular index

            // Pop elements smaller than current element
            while (!stack.isEmpty() && nums[stack.peek()] < nums[currentIndex]) {
                int index = stack.pop();
                result[index] = nums[currentIndex];
            }

            // Only push indices in first traversal to avoid duplicates
            if (i < n) {
                stack.push(currentIndex);
            }
        }

        return result;
    }
}

/**
 * LC 856: Score of Parentheses
 * Calculate score of balanced parentheses string
 * Rules: () = 1, AB = A + B, (A) = 2 * A
 * Example: "(()(()))" -> 6
 */
class ScoreOfParentheses {
    public int scoreOfParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0); // Base score

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(0); // Start new group
            } else { // c == ')'
                int current = stack.pop(); // Score of current group
                int previous = stack.pop(); // Score of outer group

                // () contributes 1, (A) contributes 2*A
                stack.push(previous + Math.max(2 * current, 1));
            }
        }

        return stack.pop();
    }
}

/**
 * LC 227: Basic Calculator II
 * Evaluate expression with +, -, *, / (no parentheses)
 * Example: "3+2*2" -> 7, "3/2" -> 1
 */
class BasicCalculatorII {
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int number = 0;
        char operation = '+'; // Start with + to handle first number

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                number = number * 10 + (c - '0');
            }

            // Process operation when we hit operator or end of string
            if (!Character.isDigit(c) && c != ' ' || i == s.length() - 1) {
                switch (operation) {
                    case '+':
                        stack.push(number);
                        break;
                    case '-':
                        stack.push(-number);
                        break;
                    case '*':
                        stack.push(stack.pop() * number);
                        break;
                    case '/':
                        stack.push(stack.pop() / number);
                        break;
                }
                operation = c;
                number = 0;
            }
        }

        // Sum all numbers in stack
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }

        return result;
    }
}

/**
 * LC 84: Largest Rectangle in Histogram
 * Find area of largest rectangle that can be formed in histogram
 * Example: heights = [2,1,5,6,2,3] -> 10 (rectangle of height 5 and width 2)
 */
class LargestRectangleInHistogram {
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>(); // Store indices
        int maxArea = 0;

        for (int i = 0; i <= heights.length; i++) {
            // Use 0 as sentinel value for last iteration
            int currentHeight = (i == heights.length) ? 0 : heights[i];

            // Process all bars higher than current bar
            while (!stack.isEmpty() && heights[stack.peek()] > currentHeight) {
                int heightIndex = stack.pop();
                int height = heights[heightIndex];

                // Width = distance between current bar and previous bar in stack
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;

                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }
}

/**
 * LC 42: Trapping Rain Water (Stack Solution)
 * Calculate how much water can be trapped after raining
 * Example: [0,1,0,2,1,0,1,3,2,1,2,1] -> 6
 */
class TrappingRainWater {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>(); // Store indices
        int totalWater = 0;

        for (int i = 0; i < height.length; i++) {
            // While current bar is higher than stack top
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int bottomIndex = stack.pop(); // This will be the bottom of water

                if (stack.isEmpty()) break; // No left boundary

                int leftIndex = stack.peek(); // Left boundary
                int rightIndex = i; // Right boundary (current position)

                // Calculate trapped water
                int width = rightIndex - leftIndex - 1;
                int waterHeight = Math.min(height[leftIndex], height[rightIndex]) - height[bottomIndex];

                totalWater += width * waterHeight;
            }

            stack.push(i);
        }

        return totalWater;
    }
}

/**
 * LC 85: Maximal Rectangle
 * Find largest rectangle containing only 1s in binary matrix
 * Uses histogram approach for each row
 */
class MaximalRectangle {
    public int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols]; // Heights for current row's histogram
        int maxArea = 0;

        for (int i = 0; i < rows; i++) {
            // Update heights array for current row
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    heights[j]++; // Extend height
                } else {
                    heights[j] = 0; // Reset height
                }
            }

            // Find largest rectangle in current histogram
            maxArea = Math.max(maxArea, largestRectangleInHistogram(heights));
        }

        return maxArea;
    }

    // Helper method: same as LC 84
    private int largestRectangleInHistogram(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;

        for (int i = 0; i <= heights.length; i++) {
            int currentHeight = (i == heights.length) ? 0 : heights[i];

            while (!stack.isEmpty() && heights[stack.peek()] > currentHeight) {
                int heightIndex = stack.pop();
                int height = heights[heightIndex];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }
}

// ==================== STACK IMPLEMENTATION PROBLEMS ====================

/**
 * LC 225: Implement Stack using Queues
 * Design a stack using only queue operations
 */
class MyStack {
    private Queue<Integer> queue1;
    private Queue<Integer> queue2;

    public MyStack() {
        queue1 = new LinkedList<>();
        queue2 = new LinkedList<>();
    }

    // Push: O(1)
    public void push(int x) {
        queue1.offer(x);
    }

    // Pop: O(n) - move all elements except last to queue2
    public int pop() {
        // Move all elements except last to queue2
        while (queue1.size() > 1) {
            queue2.offer(queue1.poll());
        }

        int result = queue1.poll(); // Last element (top of stack)

        // Swap queues
        Queue<Integer> temp = queue1;
        queue1 = queue2;
        queue2 = temp;

        return result;
    }

    // Top: O(n) - similar to pop but don't remove
    public int top() {
        while (queue1.size() > 1) {
            queue2.offer(queue1.poll());
        }

        int result = queue1.peek();
        queue2.offer(queue1.poll()); // Move last element too

        Queue<Integer> temp = queue1;
        queue1 = queue2;
        queue2 = temp;

        return result;
    }

    public boolean empty() {
        return queue1.isEmpty();
    }
}

/**
 * LC 946: Validate Stack Sequences
 * Check if popped sequence is possible from pushed sequence
 * Example: pushed = [1,2,3,4,5], popped = [4,5,3,2,1] -> true
 */
class ValidateStackSequences {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int popIndex = 0;

        for (int pushValue : pushed) {
            stack.push(pushValue);

            // Pop elements while stack top matches expected pop value
            while (!stack.isEmpty() && stack.peek() == popped[popIndex]) {
                stack.pop();
                popIndex++;
            }
        }

        return stack.isEmpty(); // All elements should be popped
    }
}

/**
 * Design Problem: Browser History
 * Implement browser history with back/forward functionality using stacks
 */
class BrowserHistory {
    private Stack<String> backStack;
    private Stack<String> forwardStack;
    private String currentPage;

    public BrowserHistory(String homepage) {
        backStack = new Stack<>();
        forwardStack = new Stack<>();
        currentPage = homepage;
    }

    // Visit new page: clear forward history, save current to back
    public void visit(String url) {
        backStack.push(currentPage);
        currentPage = url;
        forwardStack.clear(); // Clear forward history
    }

    // Go back: move current to forward, pop from back
    public String back(int steps) {
        while (steps > 0 && !backStack.isEmpty()) {
            forwardStack.push(currentPage);
            currentPage = backStack.pop();
            steps--;
        }
        return currentPage;
    }

    // Go forward: move current to back, pop from forward
    public String forward(int steps) {
        while (steps > 0 && !forwardStack.isEmpty()) {
            backStack.push(currentPage);
            currentPage = forwardStack.pop();
            steps--;
        }
        return currentPage;
    }
}

// ==================== ADVANCED STRING MANIPULATION ====================

/**
 * LC 71: Simplify Path
 * Simplify Unix-style absolute path
 * Example: "/a/./b/../../c/" -> "/c"
 */
class SimplifyPath {
    public String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        String[] components = path.split("/");

        for (String component : components) {
            if (component.equals("") || component.equals(".")) {
                // Skip empty and current directory
                continue;
            } else if (component.equals("..")) {
                // Go to parent directory
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else {
                // Regular directory name
                stack.push(component);
            }
        }

        // Build simplified path
        if (stack.isEmpty()) return "/";

        StringBuilder result = new StringBuilder();
        for (String dir : stack) {
            result.append("/").append(dir);
        }

        return result.toString();
    }
}

/**
 * LC 1003: Check If Word Is Valid After Substitutions
 * Check if string is valid after removing all "abc" substrings
 * Example: "aabcbc" -> true (remove "abc" to get "abc", then remove "abc" to get "")
 */
class ValidAfterSubstitutions {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            stack.push(c);

            // Check if last 3 characters form "abc"
            if (stack.size() >= 3) {
                char third = stack.pop();
                char second = stack.pop();
                char first = stack.pop();

                // If not "abc", push them back
                if (!(first == 'a' && second == 'b' && third == 'c')) {
                    stack.push(first);
                    stack.push(second);
                    stack.push(third);
                }
            }
        }

        return stack.isEmpty();
    }
}

// ==================== PRACTICE TEMPLATES FOR COMMON PATTERNS ====================

/**
 * TEMPLATE: Monotonic Stack (Decreasing)
 * Use when finding next/previous greater elements
class MonotonicStackDecreasing {
    public int[] template(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>(); // Store indices

        for (int i = 0; i < n; i++) {
            // Pop elements that are smaller than current
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i]) {
                int index = stack.pop();
                result[index] = nums[i]; // Process popped element
            }
            stack.push(i);
        }

        return result;
    }
}

/**
 * TEMPLATE: Expression Evaluation with Stack
 * Use for calculator problems and expression parsing
class ExpressionTemplate {
    public int evaluateExpression(String expression) {
        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                // Parse number
                int num = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    num = num * 10 + (expression.charAt(i) - '0');
                    i++;
                }
                i--; // Adjust for loop increment
                operands.push(num);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                // Evaluate until opening parenthesis
                while (operators.peek() != '(') {
                    operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.pop(); // Remove '('
            } else if (isOperator(c)) {
                // Evaluate higher precedence operators
                while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                    operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
                }
                operators.push(c);
            }
        }

        // Evaluate remaining operations
        while (!operators.isEmpty()) {
            operands.push(applyOperation(operators.pop(), operands.pop(), operands.pop()));
        }

        return operands.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private int applyOperation(char op, int b, int a) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            default: return 0;
        }
    }
}
*/

/**
 * STUDY SCHEDULE RECOMMENDATION:
 *
 * Day 1-2: Master the boilerplate templates above
 * Day 3-4: Solve Easy problems (Valid Parentheses, Min Stack, etc.)
 * Day 5-7: Practice Monotonic Stack problems
 * Day 8-10: Expression evaluation and string manipulation
 * Day 11-14: Advanced problems (Histogram, Trapping Rain Water)
 * Day 15-21: Mixed practice and pattern recognition
 *
 * TIPS FOR SUCCESS:
 * 1. Always draw the stack state for small examples
 * 2. Identify the pattern before coding
 * 3. Handle edge cases (empty stack, single element)
 * 4. Practice time management (15-20 minutes per problem)
 * 5. Focus on the most common patterns first
 */
}
