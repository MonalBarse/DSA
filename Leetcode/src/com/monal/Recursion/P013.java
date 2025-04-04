package com.monal.Recursion;

import java.util.*;

/*
  Problem: Print N-bit binary numbers having more 1’s than 0’s for any prefix
  Description: Given a number n, print all n-bit binary numbers such that no prefix of the binary number has more 0’s than 1’s.

  Example 1:
    Input: n = 3
    Output: 111, 110, 101
    Explanation:
      The valid 3-bit binary numbers are 111, 110, 101, and 011.
      However, only 111 101 and 110 have more 1’s than 0’s in all prefixes.
      prefixes of 111: 1, 11, 111 - valid
      prefixes of 110: 1, 11, 110 - valid
      prefixes of 101: 1, 10, 101 - valid
      prefixes of 011: 0, 01, 011 - invalid

  Example 2:
    Input: n = 4
    Output: 1111, 1110, 1101
    Explanation:
      The valid 4-bit binary numbers are 1111, 1110, 1101, 1100, 1011, and 1010.
      However, only 1111, 1110, 1101, and 1100 have more 1’s than 0’s in all prefixes.
      prefixes of 1111: 1, 11, 111, 1111 - valid
      prefixes of 1110: 1, 11, 111, 1110 - valid
      prefixes of 1101: 1, 11, 110, 1101 - valid
      prefixes of 1100: 1, 11, 110, 1100 - valid
      prefixes of other numbers: invalid

*/
public class P013 {
  private List<String> nBinaryNumbers(int n) {

    List<String> result = new ArrayList<>();
    generateBinaryNumbers(n, "", 0, 0, result);
    return result;
  }

  private void generateBinaryNumbers(int n, String current, int ones, int zeros, List<String> result) {
    if (current.length() == n) {
      result.add(current);
      return;
    }

    // Add 1 to the current binary number
    generateBinaryNumbers(n, current + "1", ones + 1, zeros, result);
    // Add 0 to the current binary number only if it doesn't violate the condition
    if (ones > zeros) {
      generateBinaryNumbers(n, current + "0", ones, zeros + 1, result);
    }

    // Note: We don't need to check for the condition of more 1's than 0's in all
    // prefixes
    // because we are only adding 0's when there are more 1's than 0's in the
    // current prefix.
    // This ensures that the condition is satisfied for all prefixes.

  }

  public static void main(String[] args) {
    P013 obj = new P013();
    List<String> result = obj.nBinaryNumbers(5);
    System.out.println("Valid " + 5 + "-bit binary numbers with more 1's than 0's in all prefixes: " + result);
    // Output: [111, 110, 101]

    List<String> result2 = obj.nBinaryNumbers(4);
    System.out.println("Valid " + 4 + "-bit binary numbers with more 1's than 0's in all prefixes: " + result2);
    // Output: [1111, 1110, 1101, 1100]
  }

}
