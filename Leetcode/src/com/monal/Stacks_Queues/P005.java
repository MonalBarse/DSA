package com.monal.Stacks_Queues;
import java.util.Stack;

/*
Add to make a Valid Parentheses String
Question: Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine
the number of characters that need to be added to make the input string valid.

Valid String parentheses :
   1. Open brackets must be closed by the same type of brackets.
   2. Open brackets must be closed in the correct order.
   3. Every close bracket has a corresponding open bracket of the same type.

*/
public class P005 {

  public int minAddToMakeValid(String s) {
    Stack<Character> stack = new Stack<>();
    int count = 0; // Count of characters to be added

    for (char c : s.toCharArray()) {
      if (c == '(' || c == '{' || c == '[') {
        // If it's an opening bracket, push it onto the stack
        stack.push(c);
      } else {
        // If it's a closing bracket, check if the stack is empty or if the top of the
        // stack does not match the corresponding opening bracket
        if (stack.isEmpty() ||
            (c == ')' && stack.peek() != '(') ||
            (c == '}' && stack.peek() != '{') ||
            (c == ']' && stack.peek() != '[')) {
          // If it doesn't match, we need to add a corresponding opening bracket
          count++;
        } else {
          // If it matches, pop the top of the stack
          stack.pop();
        }
      }
    }
    // After processing all characters, the stack will contain unmatched opening
    // Each unmatched opening bracket will require a closing bracket to be added
    count += stack.size(); // Add the number of unmatched opening brackets to the count
    return count; // Return the total count of characters to be added
  }
}
