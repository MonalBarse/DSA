package com.monal.Strings;

/*
A digit string is good if the digits (0-indexed) at even indices are even and the digits at odd indices are prime (2, 3, 5, or 7).
For example, "2582" is good because the digits (2 and 8) at even positions are even and the digits (5 and 2) at odd positions are prime.
However, "3245" is not good because 3 is at an even index but is not even.
Given an integer n, return the total number of good digit strings of length n.
Since the answer may be large, return it modulo 109 + 7.
A digit string is a string consisting of digits 0 through 9 that may contain leading zeros.

Example 1:
  Input: n = 1
  Output: 5
  Explanation: The good numbers of length 1 are "0", "2", "4", "6", "8".
Example 2:
  Input: n = 4
  Output: 400
Example 3:
  Input: n = 50
  Output: 564908303

  Constraints:
    1 <= n <= 1015 */
public class P003 {
  class Solution {

    public int countGoodNumbers(long n) {
      long mod = 1000000007;

      // For even positions (0-indexed): 0, 2, 4, 6, 8 (5 options)
      // For odd positions (0-indexed): 2, 3, 5, 7 (4 options)

      // Calculate how many even and odd positions we have
      long evenPositions = (n + 1) / 2; // ceiling division for even positions (including index 0)
      long oddPositions = n / 2; // floor division for odd positions

      // Calculate 5^evenPositions and 4^oddPositions using fast exponentiation
      long evenResult = modPow(5, evenPositions, mod);
      long oddResult = modPow(4, oddPositions, mod);

      // Multiply the results
      return (int) ((evenResult * oddResult) % mod);
    }

    // Fast exponentiation to calculate (base^exponent) % mod efficiently
    private long modPow(long base, long exponent, long mod) {
      long result = 1;
      base %= mod;

      while (exponent > 0) {
        // If exponent is odd, multiply result with base
        if (exponent % 2 == 1) {
          result = (result * base) % mod;
        }

        // Square the base
        base = (base * base) % mod;

        // Divide exponent by 2
        exponent /= 2;
      }

      return result;
    }

    public boolean isGood(String s) {
      // a number is good even indices are even and odd indices are prime
      // even indices are 0, 2, 4, 6, 8
      // odd indices are 2, 3, 5, 7
      for (int i = 0; i < s.length(); i++) {
        char digit = s.charAt(i);

        if (i % 2 == 0) {
          // Even positions should have even digits
          if (digit != '0' && digit != '2' && digit != '4' && digit != '6' && digit != '8') {
            return false;
          }
        } else {
          // Odd positions should have prime digits
          if (digit != '2' && digit != '3' && digit != '5' && digit != '7') {
            return false;
          }
        }

      }
      return true;
    }
  }
}
