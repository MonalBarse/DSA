package com.monal.DP.DP_Strings;

import java.util.*;

/*
Given a string s and an integer k, partition s into k substrings such that the letter changes needed to make each substring a semi-palindrome are minimized.

Return the minimum number of letter changes required.

A semi-palindrome is a special type of string that can be divided into palindromes based on a repeating pattern. To check if a string is a semi-palindrome:​

Choose a positive divisor d of the string's length. d can range from 1 up to, but not including, the string's length. For a string of length 1, it does not have a valid divisor as per this definition, since the only divisor is its length, which is not allowed.
For a given divisor d, divide the string into groups where each group contains characters from the string that follow a repeating pattern of length d. Specifically, the first group consists of characters at positions 1, 1 + d, 1 + 2d, and so on; the second group includes characters at positions 2, 2 + d, 2 + 2d, etc.
The string is considered a semi-palindrome if each of these groups forms a palindrome.
Consider the string "abcabc":

The length of "abcabc" is 6. Valid divisors are 1, 2, and 3.
For d = 1: The entire string "abcabc" forms one group. Not a palindrome.
For d = 2:
Group 1 (positions 1, 3, 5): "acb"
Group 2 (positions 2, 4, 6): "bac"
Neither group forms a palindrome.
For d = 3:
Group 1 (positions 1, 4): "aa"
Group 2 (positions 2, 5): "bb"
Group 3 (positions 3, 6): "cc"
All groups form palindromes. Therefore, "abcabc" is a semi-palindrome.


Example 1:

Input: s = "abcac", k = 2

Output: 1

Explanation: Divide s into "ab" and "cac". "cac" is already semi-palindrome. Change "ab" to "aa", it becomes semi-palindrome with d = 1.

Example 2:

Input: s = "abcdef", k = 2

Output: 2

Explanation: Divide s into substrings "abc" and "def". Each needs one change to become semi-palindrome.

Example 3:

Input: s = "aabbaa", k = 3

Output: 0

Explanation: Divide s into substrings "aa", "bb" and "aa". All are already semi-palindromes.



Constraints:

2 <= s.length <= 200
1 <= k <= s.length / 2
s contains only lowercase English letters.
 */
public class P010 {

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();

        // cost[l][r] = min changes to make s[l..r] a semi-palindrome
        int[][] cost = new int[n][n];
        for (int l = 0; l < n; l++) for (int r = l; r < n; r++) cost[l][r] = semiPalindromeCost(s, l, r);

        // dp[i][t] = min total cost to split s[0..i] into exactly t semi-palindromes
        int[][] dp = new int[n][k + 1];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);

        // one part: s[0..i]
        for (int i = 0; i < n; i++) dp[i][1] = cost[0][i];

        for (int parts = 2; parts <= k; parts++) {
            for (int i = 0; i < n; i++) {
                for (int j = parts - 2; j < i; j++) { // ensure enough chars to form (parts-1) pieces before j
                    if (dp[j][parts - 1] == Integer.MAX_VALUE) continue;
                    int c = cost[j + 1][i];
                    if (c == Integer.MAX_VALUE) continue;           // last piece invalid (e.g., length 1)
                    dp[i][parts] = Math.min(dp[i][parts], dp[j][parts - 1] + c);
                }
            }
        }

        return dp[n - 1][k];
    }

    // Minimum changes to make s[l..r] a semi-palindrome under the proper divisor rule
    private int semiPalindromeCost(String s, int l, int r) {
        int len = r - l + 1;

        // len == 1 is NOT allowed (no proper divisor), so mark impossible
        if (len == 1) return Integer.MAX_VALUE;

        int best = Integer.MAX_VALUE;

        // try all proper divisors d (1 <= d < len, len % d == 0)
        for (int d = 1; d < len; d++) {
            if (len % d != 0) continue;

            int changes = 0;
            // d groups: for each residue class g in [0..d-1]
            for (int g = 0; g < d; g++) {
                // group positions are: l+g, l+g+d, l+g+2d, ...
                int m = ((len - 1 - g) / d) + 1; // number of elements in this group
                for (int t = 0; t < m / 2; t++) {
                    int i1 = l + g + t * d;
                    int i2 = l + g + (m - 1 - t) * d;
                    if (s.charAt(i1) != s.charAt(i2)) changes++;
                }
            }
            best = Math.min(best, changes);
        }

        return best;
    }
}

}
