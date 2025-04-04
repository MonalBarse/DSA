package com.monal.Recursion;

import java.util.*;

/*
Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.
Note that the same word in the dictionary may be reused multiple times in the segmentation.

Example 1:
  Input: s = "leetcode", wordDict = ["leet","code"]
  Output: true
  Explanation: Return true because "leetcode" can be segmented as "leet code".
Example 2:
  Input: s = "applepenapple", wordDict = ["apple","pen"]
  Output: true
  Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
  Note that you are allowed to reuse a dictionary word.
Example 3:
  Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
  Output: false
*/

/*
 Approach:
  We need to check if a given string s can be split into words from wordDict.
    ✅ Words in wordDict can be reused.
    ✅ The order of words must match the order in s.

  We recursively check whether s can be split by:
    Trying all possible prefixes of s.
    If a prefix is in wordDict, recursively check the remaining part.
    Store the results in a memoization map to avoid recomputation.

  Let's define a recursive function wordBreak(s) that returns true if s can be split into words from wordDict and false otherwise.
*/
public class P010 {
  // class Solution {
  // public boolean wordBreak(String s, List<String> wordDict) {

  // // Create a set of words for faster lookup
  // Set<String> wordSer = new HashSet<>(wordDict); // O(1) lookup
  // Map<Integer, Boolean> memo = new HashMap<>(); // memoization map

  // return helper_fn(s, 0, wordSer, memo);
  // }

  // private boolean helper_fn(String s, int start, Set<String> wordSet,
  // Map<Integer, Boolean> memo) {
  // if (start == s.length())
  // return true;
  // if (memo.containsKey(start))
  // return memo.get(start);

  // for (int end = start + 1; end <= s.length(); end++) {
  // if (wordSet.contains(s.substring(start, end)) && helper_fn(s, end, wordSet,
  // memo)) {
  // memo.put(start, true);
  // return true;
  // }
  // }

  // memo.put(start, false);
  // return false;
  // }
  // }

  /**
   * Solution for the Word Break problem.
   *
   * Problem: Given a string s and a dictionary of words, determine if s can be
   * segmented into a space-separated sequence of dictionary words.
   */
  class Solution {

    /**
     * Main method to solve the word break problem.
     * This method uses approach #1 (checking if string starts with each dictionary
     * word).
     */
    public boolean wordBreak(String s, List<String> wordDict) {
      // Create a memoization array to store results of subproblems
      Boolean[] memo = new Boolean[s.length() + 1];
      // Start the recursive solution from index 0
      return canBreakFromIndex(s, wordDict, 0, memo);
    }

    /**
     * Approach #1: For each position, try all words in dictionary and check if they
     * match.
     *
     * Time Complexity: O(n * m * k) where:
     * - n is the length of string s
     * - m is the number of words in wordDict
     * - k is the average length of words in wordDict
     *
     * Space Complexity: O(n) for the memoization array
     */
    private boolean canBreakFromIndex(String s, List<String> wordDict, int startIndex, Boolean[] memo) {
      // Base case: if we've reached the end of the string, we've successfully broken
      if (startIndex == s.length()) {
        return true;
      }

      // Check if we've already solved this subproblem
      if (memo[startIndex] != null) {
        return memo[startIndex];
      }

      // Try each word in the dictionary
      for (String word : wordDict) {
        // Check if the current word matches the substring starting at current position
        if (s.startsWith(word, startIndex)) {
          // If it matches, recursively check if we can break the rest of the string
          if (canBreakFromIndex(s, wordDict, startIndex + word.length(), memo)) {
            // Store result in memo and return true
            memo[startIndex] = true;
            return true;
          }
        }
      }

      // If no word in dictionary can break the string from this position
      memo[startIndex] = false;
      return false;
    }

    /**
     * Alternative approach #2: For each starting position, try all possible word
     * lengths.
     * This approach converts the list to a set for O(1) lookup and then checks all
     * possible
     * substring lengths.
     */
    public boolean wordBreakAlternative(String s, List<String> wordDict) {
      // Create a memoization array
      Boolean[] memo = new Boolean[s.length() + 1];
      // Convert list to set for O(1) lookup
      Set<String> wordSet = new HashSet<>(wordDict);
      // Start the recursive solution
      return canBreakByCheckingLengths(s, wordSet, 0, memo);
    }

    /**
     * Approach #2: For each position, try all possible word lengths.
     *
     * Time Complexity: O(n²) where n is the length of string s
     * Space Complexity: O(n) for the memoization array
     */
    private boolean canBreakByCheckingLengths(String s, Set<String> wordSet, int startIndex, Boolean[] memo) {
      int n = s.length();

      // Base case: if we've reached the end of the string
      if (startIndex == n) {
        return true;
      }

      // Check memoization
      if (memo[startIndex] != null) {
        return memo[startIndex];
      }

      // Try all possible word lengths from this position
      for (int endIndex = startIndex + 1; endIndex <= n; endIndex++) {
        // Extract potential word
        String potentialWord = s.substring(startIndex, endIndex);

        // Check if this potential word exists in dictionary AND
        // the rest of the string can be broken into words
        if (wordSet.contains(potentialWord) &&
            canBreakByCheckingLengths(s, wordSet, endIndex, memo)) {
          memo[startIndex] = true;
          return true;
        }
      }

      // If no valid word segmentation is found
      memo[startIndex] = false;
      return false;
    }
  }

  public static void main(String[] args) {
    P010 p010 = new P010();
    Solution solution = p010.new Solution();
    System.out.println(solution.wordBreak("leetcode", Arrays.asList("leet", "code")));
    System.out.println(solution.wordBreak("applepenapple", Arrays.asList("apple", "pen")));
    System.out.println(solution.wordBreak("catsandog", Arrays.asList("cats", "dog", "sand", "cat")));
  }
}
