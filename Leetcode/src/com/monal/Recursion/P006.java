package com.monal.Recursion;

import java.util.*;

/*
Palindrome Partitioning

Given a string s, partition s such that every substring of the partition is a palindrome. Return all possible palindrome partitioning of s.
Example 1:
  Input: s = "aab"
  Output: [["a","a","b"],["aa","b"]]
Example 2:
  Input: s = "a"
  Output: [["a"]]
*/
public class P006 {
  class Solution {
    public List<List<String>> partition(String s) {
      List<List<String>> res = new ArrayList<>();
      helper_fn(s.toCharArray(), 0, new ArrayList<>(), res);
      return res;
    }

    private void helper_fn(char[] s, int idx, List<String> curr, List<List<String>> res) {
      if (idx == s.length) {
        res.add(new ArrayList<>(curr));
        return;
      }

      for (int i = idx; i < s.length; i++) {
        if (isPalindrome(s, idx, i)) {
          // if the substring is a palindrom
          curr.add(new String(s, idx, i - idx + 1)); // this is the substring
          helper_fn(s, i + 1, curr, res);
          curr.remove(curr.size() - 1); // Backtrack
        }
      }
    }

    private boolean isPalindrome(char[] s, int start, int end) {
      while (start < end) {
        if (s[start++] != s[end--]) {
          return false;
        }
      }
      return true;
    }

    // ----------------------------------------------------------- //
    public static void main(String[] args) {
      P006 p006 = new P006();
      Solution solution = p006.new Solution();
      System.out.println(solution.partition("aab"));
      System.out.println(solution.partition("a"));
    }
  }

}
