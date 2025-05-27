package com.monal.DP.DP_LIS;

import java.util.*;

/*
You are given an array of words where each word consists of lowercase English letters.
wordA is a predecessor of wordB if and only if we can insert exactly one letter
anywhere in wordA without changing the order of the other characters to make it equal to wordB.

For example, "abc" is a predecessor of "abac", while "cba" is not a predecessor of "bcad".
A word chain is a sequence of words [word1, word2, ..., wordk] with k >= 1,
where word1 is a predecessor of word2, word2 is a predecessor of word3, and so on.
A single word is trivially a word chain with k == 1.

Return the longest possible word chain with words chosen from the given list of words.

Example 1:
  Input: words = ["a","b","ba","bca","bda","bdca"]
  Output: ["a","ba","bda","bdca"] or ["b","ba","bda","bdca"] or ["a","ba", "bca","bdca"] or ["b","ba","bca","bdca"]
Example 2:
  Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"]
  Output: ["xb", "xbc", "cxbc", "pcxbc", "pcxbcf"]
Example 3:
  Input: words = ["abcd","dbqca"]
  output: ["abcd"] or ["dbqca"]
*/
public class P005 {
  public class LongestStringChain {
    public static void main(String[] args) {
      String[] words = { "a", "b", "ba", "bca", "bda", "bdca" };
      Solution sol = new Solution();
      int length = sol.longestStrChain(words);
      System.out.println("Length of Longest String Chain: " + length);
    }

    static class Solution {
      public int longestStrChain(String[] words) {
        Arrays.sort(words, Comparator.comparingInt(String::length));
        int n = words.length;
        int[] dp = new int[n]; // dp[i] = length of longest chain ending at i
        int[] prev = new int[n]; // prev[i] = index of previous word in the chain

        Arrays.fill(dp, 1);
        Arrays.fill(prev, -1);

        int maxLength = 1;
        int lastIndex = 0;

        for (int i = 1; i < n; i++) {
          for (int j = 0; j < i; j++) {
            if (compare(words[i], words[j]) && dp[j] + 1 > dp[i]) {
              dp[i] = dp[j] + 1;
              prev[i] = j;
            }
          }
          if (dp[i] > maxLength) {
            maxLength = dp[i];
            lastIndex = i;
          }
        }

        // Reconstruct the chain
        List<String> chain = new ArrayList<>();
        while (lastIndex != -1) {
          chain.add(words[lastIndex]);
          lastIndex = prev[lastIndex];
        }
        Collections.reverse(chain);
        System.out.println("Longest String Chain: " + chain);
        return maxLength;
      }

      private boolean compare(String longer, String shorter) {
        if (longer.length() != shorter.length() + 1)
          return false;

        int i = 0, j = 0;
        while (i < longer.length()) {
          if (j < shorter.length() && longer.charAt(i) == shorter.charAt(j)) {
            i++;
            j++;
          } else {
            i++;
          }
        }
        return j == shorter.length();
      }
    }
  }

}
