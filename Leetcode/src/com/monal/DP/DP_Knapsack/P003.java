package com.monal.DP.DP_Knapsack;

/*
You have intercepted a secret message encoded as a string of numbers.
The message is decoded via the following mapping:
"1" -> 'A'
"2" -> 'B'
...
"25" -> 'Y'
"26" -> 'Z'
However, while decoding the message, you realize that there are many different ways you can
decode the message because some codes are contained in other codes ("2" and "5" vs "25").

For example, "11106" can be decoded into:

"AAJF" with the grouping (1, 1, 10, 6)
"KJF" with the grouping (11, 10, 6)
The grouping (1, 11, 06) is invalid because "06" is not a valid code (only "6" is valid).
Note: there may be strings that are impossible to decode.

Given a string s containing only digits, return the number of ways to decode it.
If the entire string cannot be decoded in any valid way, return 0.


Example 1:
  Input: s = "12"
  Output: 2
  Explanation:
  "12" could be decoded as "AB" (1 2) or "L" (12).

Example 2:
  Input: s = "226"
  Output: 3
  Explanation:
  "226" could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).

Example 3:
  Input: s = "06"
  Output: 0
  Explanation:
  "06" cannot be mapped to "F" because of the leading zero ("6" is different from "06"). In this case, the string is not a valid encoding, so return 0.
*/
public class P003 {
  class Solution {

    @SuppressWarnings("unused")
    private int numDecodingRecusive(String s) {
      if (s.length() == 0 || s.charAt(0) == '0') {
        return 0;
      }
      if (s.length() == 1) {
        return 1;
      }
      int count = 0;

      // Check if the first character is valid
      if (s.charAt(0) != '0') {
        count += numDecodingRecusive(s.substring(1));
      }

      // Check if the first two characters form a valid number
      if (s.length() >= 2) {
        int twoDigit = Integer.parseInt(s.substring(0, 2));
        if (twoDigit >= 10 && twoDigit <= 26) {
          count += numDecodingRecusive(s.substring(2));
        }
      }

      return count;
    }

    @SuppressWarnings("unused")
    private int numDecodingMemo(String s) {
      Integer[] memo = new Integer[s.length() + 1];
      return numDecodingMemoHelper(s, 0, memo);
    }

    private int numDecodingMemoHelper(String s, int index, Integer[] memo) {
      if (index == s.length()) {
        return 1; // Reached the end of the string
      }
      if (s.charAt(index) == '0') {
        return 0; // Invalid encoding
      }
      if (memo[index] != null) {
        return memo[index]; // Return cached result
      }

      int count = 0;

      // Check if the first character is valid
      if (s.charAt(index) != '0') {
        count += numDecodingMemoHelper(s, index + 1, memo);
      }

      // Check if the first two characters form a valid number
      if (index + 1 < s.length()) {
        int twoDigit = Integer.parseInt(s.substring(index, index + 2));
        if (twoDigit >= 10 && twoDigit <= 26) {
          count += numDecodingMemoHelper(s, index + 2, memo);
        }
      }

      memo[index] = count; // Cache the result
      return count;
    }

    public int numDecodingsTabulation(String s) {
      int n = s.length();
      if (n == 0 || s.charAt(0) == '0') {
        return 0;
      }
      int[] dp = new int[n + 1]; // dp[i] = number of ways to decode string of length i
      dp[0] = 1; // Base case: empty string can be decoded in one way
      dp[1] = 1; // Base case: single character string can be decoded in one way

      // start from the i = 2 position as we have already handled the first two
      // characters
      for (int i = 2; i <= n; i++) {
        // Check if the last digit is valid
        if (s.charAt(i - 1) != '0') {
          dp[i] += dp[i - 1];
        }
        // Check if the last two digits form a valid number
        int twoDigit = Integer.parseInt(s.substring(i - 2, i));
        if (twoDigit >= 10 && twoDigit <= 26) {
          dp[i] += dp[i - 2];
        }
      }

      return dp[n]; // The last element of dp array contains the total ways to decode the string
    }

    public int numDecodingsSpaceOptimized(String s) {
      int n = s.length();

      // we need only the last two results to calculate the current result

      // Base case
      if (s.length() == 0 || s.charAt(0) == '0') {
        return 0;
      }
      int prev1 = 1; // dp[i-1]
      int prev2 = 1; // dp[i-2]

      for (int i = 2; i <= n; i++) {
        int curr = 0;

        // Check if the last digit is valid
        if (s.charAt(i - 1) != '0') {
          curr += prev1;
        }

        // Check if the last two digits form a valid number
        int twoDigit = Integer.parseInt(s.substring(i - 2, i));
        if (twoDigit >= 10 && twoDigit <= 26) {
          curr += prev2;
        }

        // Update prev1 and prev2 for the next iteration
        prev2 = prev1;
        prev1 = curr;
      }

      return prev1; // The last calculated value is the total ways to decode the string
    }
  }
}
