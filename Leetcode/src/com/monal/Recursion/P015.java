package com.monal.Recursion;

import java.util.ArrayList;
import java.util.List;

/*
Given a string s, partition it such that every substring is a palindrome.
Return all the ways of partitioning s with the minimum number of cuts.

Example:
  Input: s = "aab"
  Output: [["aa","b"]]
  Explanation: The only possible palindrome partitions are:
  - ["a","a","b"] → 2 cuts
  - ["aa","b"] → 1 cut ✅ (minimal)

  So we return [["aa","b"]] only.


  Appraoch:
    At every index, try placing a "cut" → form a substring.
    If that substring is a palindrome → recursively partition the rest.
    Keep track of the minimum number of cuts used to get to the end.
    Store only the paths that use this minimum number of cuts.
*/
public class P015 {
  class Solution {
    int minCuts = Integer.MAX_VALUE;

    public List<List<String>> minCutPartitions(String s) {
      List<List<String>> result = new ArrayList<>();
      helper_fn(s, 0, new ArrayList<>(), result);
      return result;
    }

    private void helper_fn(String s, int start, List<String> path, List<List<String>> result) {
      // Base case: if we reached the end of the string
      if (start == s.length()) {
        int cuts = path.size() - 1; // set cuts to the number of palindromes - 1
        if (cuts < minCuts) {
          result.clear(); // we found a better (shorter cut) path
          result.add(new ArrayList<>(path));
          minCuts = cuts;
        } else if (cuts == minCuts) {
          result.add(new ArrayList<>(path));
        }
        return;
      }

      // Try every substring starting from `start`
      for (int end = start; end < s.length(); end++) {
        if (isPalindrome(s, start, end)) {
          // Choose this palindrome
          path.add(s.substring(start, end + 1));

          // Recurse
          helper_fn(s, end + 1, path, result);

          // Backtrack
          path.remove(path.size() - 1);
        }
      }
    }

    private boolean isPalindrome(String s, int left, int right) {
      while (left < right) {
        if (s.charAt(left++) != s.charAt(right--))
          return false;
      }
      return true;
    }
  }

}
