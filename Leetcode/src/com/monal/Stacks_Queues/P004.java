
package com.monal.Stacks_Queues;

import java.util.*;

/*
Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
An input string is valid if:
  Open brackets must be closed by the same type of brackets.
  Open brackets must be closed in the correct order.
  Every close bracket has a corresponding open bracket of the same type.

Example 1:
  Input: s = "()"
  Output: true

Example 2:
  Input: s = "()[]{}"
  Output: true

Example 3:
  Input: s = "(]"
  Output: false

Example 4:
  Input: s = "([])"
  Output: true

Constraints:
  1 <= s.length <= 104
  s consists of parentheses only '()[]{}'.
*/
public class P004 {
  // =============================================== //
  // ------------ Stack Implementation ------------- //
  // =============================================== //
  /**
   * This method checks if the given string of brackets is valid.
   * It uses a stack to keep track of opening brackets and ensures
   * that every closing bracket matches the most recent opening bracket.
   *
   * @param s The string containing brackets to validate.
   * @return true if the string is valid, false otherwise.
   */
  public boolean isValid(String s) {
    if (s.length() % 2 == 1)
      return false;

    // Use actual Stack data structure
    Stack<Character> stack = new Stack<>();

    // Map closing → opening for easy checks
    Map<Character, Character> match = Map.of(
        ')', '(',
        '}', '{',
        ']', '[');

    for (char c : s.toCharArray()) {
      if (c == '(' || c == '{' || c == '[') {
        // Opening bracket → push onto stack
        stack.push(c);
      } else {
        // Closing bracket → stack must not be empty and top must match

        if (stack.isEmpty() || !stack.pop().equals(match.get(c))) {
          return false;
        }
      }
    }

    // If stack is empty, every opening was matched
    return stack.isEmpty();
  }

  // =============================================== //
  // ------------ Arrayed Implementation ----------- //
  // =============================================== //

  /**
   * This method checks if the given string of brackets is valid using an array as
   * a stack.
   * Using an array can be more efficient than using a Stack object, since it
   * avoids the overhead of method calls and object creation.
   *
   * @param s The string containing brackets to validate.
   * @return true if the string is valid, false otherwise.
   */
  public boolean isValidArrayed(String s) {
    if (s.length() % 2 == 1)
      return false;

    // Use a char[] as stack for speed
    char[] stack = new char[s.length()];
    int top = -1;

    // Map closing → opening for easy checks
    Map<Character, Character> match = Map.of(
        ')', '(',
        '}', '{',
        ']', '[');

    for (char c : s.toCharArray()) {
      if (c == '(' || c == '{' || c == '[') {
        // Opening bracket → push onto stack
        stack[++top] = c;
      } else {
        // Closing bracket → stack must not be empty and top must match
        if (top < 0 || stack[top] != match.get(c)) {
          return false;
        }
        top--; // Pop the matched opening
      }
    }

    // If stack is empty, every opening was matched
    return (top == -1);
  }

}
