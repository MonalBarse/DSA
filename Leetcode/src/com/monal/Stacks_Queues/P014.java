package com.monal.Stacks_Queues;

import java.util.*;

/*
Given string num representing a non-negative integer num, and an integer k, return the smallest possible integer after removing k digits from num.
Example 1:
  Input: num = "1432219", k = 3
  Output: "1219"
  Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
Example 2:
  Input: num = "10200", k = 1
  Output: "200"
  Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
Example 3:
  Input: num = "10", k = 2
  Output: "0"
  Explanation: Remove all the digits from the number and it is left with nothing which is 0.
*/
public class P014 {
  class Solution {
    public String removeKdigits(String num, int k) {
      if (k >= num.length()) return "0";

      Stack<Character> stack = new Stack<>();
      char[] number = num.toCharArray();

      for (char digit : number) {
        while (!stack.isEmpty() && k > 0 && stack.peek() > digit) {
          stack.pop();
          k--;
        }
        stack.push(digit);
      }

      while (k-- > 0) stack.pop(); // Edge case ==> if we get increasing number

      StringBuilder sb = new StringBuilder();

      for (char digit : stack) sb.append(digit); // appending leads to right

      while (sb.length() > 0 && sb.charAt(0) == '0') sb.deleteCharAt(0);

      return sb.length() == 0 ? "0" : sb.toString();
    }
  }
  public static void main(String[] args) {
    Solution solution = new P014().new Solution();
    System.out.println(solution.removeKdigits("1432219", 3)); // Output: "1219"
    System.out.println(solution.removeKdigits("10200", 1)); // Output: "200"
    System.out.println(solution.removeKdigits("10", 2)); // Output: "0"
    System.out.println(solution.removeKdigits("123456789", 5)); // Output : "1234"
    System.out.println(solution.removeKdigits("987654321", 5)); // Output : "1234"
  }

}
