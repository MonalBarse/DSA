package com.monal.Tries;

import java.util.*;

/*
You are given a 0-indexed array of unique strings words.
A palindrome pair is a pair of integers (i, j) such that:
0 <= i, j < words.length,
i != j, and
words[i] + words[j] (the concatenation of the two strings) is a palindrome.
Return an array of all the palindrome pairs of words.
You must write an algorithm with O(sum of words[i].length) runtime complexity.

Example 1:
  Input: words = ["abcd","dcba","lls","s","sssll"]
  Output: [[0,1],[1,0],[3,2],[2,4]]
  Explanation: The palindromes are ["abcddcba","dcbaabcd","slls","llssssll"]
Example 2:
  Input: words = ["bat","tab","cat"]
  Output: [[0,1],[1,0]]
  Explanation: The palindromes are ["battab","tabbat"]
Example 3:
  Input: words = ["a",""]
  Output: [[0,1],[1,0]]
  Explanation: The palindromes are ["a","a"]

Constraints:
  1 <= words.length <= 5000
  0 <= words[i].length <= 300
  words[i] consists of lowercase English letters.
*/
public class P003 {
  /**
   * Approach: Using a Hash Map to store reversed words.
   * Time Complexity: O(N * L^2) where N is the number of words, L is the max
   * length.
   * - Building the map: O(N * L).
   * - Main loop: N words * L split points * L for substring/palindrome check =
   * O(N * L^2).
   * Space Complexity: O(N * L) to store the words in the map.
   */

  public class Solution_1 {
    public List<List<Integer>> palindromePairs(String[] words) {
      List<List<Integer>> result = new ArrayList<>();
      Map<String, Integer> wordMap = new HashMap<>();

      for (int i = 0; i < words.length; i++)
        wordMap.put(new StringBuilder(words[i]).reverse().toString(), i);

      for (int i = 0; i < words.length; i++) {
        String word = words[i];
        int n = word.length();

        for (int cut = 0; cut <= n; cut++) {
          String prefix = word.substring(0, cut);
          String suffix = word.substring(cut, n);

          // If prefix is palindrome, check for reversed suffix in map
          if (isPalindrome(prefix)) {
            int j = wordMap.getOrDefault(suffix, -1);
            if (j != -1 && j != i)
              result.add(Arrays.asList(j, i));
          }

          // If suffix is palindrome, check for reversed prefix in map
          // cut != n to avoid duplicates
          if (cut != n && isPalindrome(suffix)) {
            int j = wordMap.getOrDefault(prefix, -1);
            if (j != -1 && j != i)
              result.add(Arrays.asList(i, j));
          }
        }
      }
      return result;
    }

    private boolean isPalindrome(String str) {
      int left = 0, right = str.length() - 1;
      while (left < right)
        if (str.charAt(left++) != str.charAt(right--))
          return false;
      return true;
    }

  }

  public class Solution_2 {

    private static class TrieNode {
      TrieNode[] children = new TrieNode[26];
      int index = -1; // Stores the index of the word ending at this node.
      List<Integer> palindromeIndices = new ArrayList<>(); // Stores indices of words whose suffixes from this node are
                                                           // palindromes.
    }

    /**
     * Approach: Using a Trie to optimize the search for reversed prefixes/suffixes.
     * Time Complexity: O(N * L^2) where N is the number of words, L is the max
     * length.
     * - Building the Trie: O(N * L^2) because of the palindrome check at each step.
     * - Searching: O(N * L^2) because of the palindrome check during the search.
     * Space Complexity: O((N * L)^2) in the worst case for palindromeIndices, but
     * practically much better.
     */
    public List<List<Integer>> palindromePairs(String[] words) {
      List<List<Integer>> result = new ArrayList<>();
      TrieNode root = new TrieNode();

      // 1. Build the Trie with the reverse of each word.
      for (int i = 0; i < words.length; i++) {
        addWord(root, words[i], i);
      }

      // 2. Search for pairs for each word.
      for (int i = 0; i < words.length; i++) {
        search(words[i], i, root, result);
      }

      return result;
    }

    // Inserts the reverse of a word into the Trie.
    private void addWord(TrieNode root, String word, int index) {
      for (int i = word.length() - 1; i >= 0; i--) {
        int charIndex = word.charAt(i) - 'a';
        if (root.children[charIndex] == null) {
          root.children[charIndex] = new TrieNode();
        }

        // Check if the remaining prefix of the original word is a palindrome.
        if (isPalindrome(word, 0, i)) {
          root.palindromeIndices.add(index);
        }
        root = root.children[charIndex];
      }

      // The node for the empty prefix is also a palindrome.
      root.palindromeIndices.add(index);
      root.index = index;
    }

    // Searches the Trie for pairs matching the given word.
    private void search(String word, int i, TrieNode root, List<List<Integer>> result) {
      for (int j = 0; j < word.length(); j++) {
        // Case 1: Partner word is shorter or equal length.
        // Check if the current node in the Trie marks the end of a word. If so,
        // it means we found a word that is the reverse of the current prefix of `word`.
        // Then, we check if the rest of `word` is a palindrome.
        if (root.index != -1 && root.index != i && isPalindrome(word, j, word.length() - 1)) {
          result.add(Arrays.asList(i, root.index));
        }

        int charIndex = word.charAt(j) - 'a';
        root = root.children[charIndex];
        if (root == null) {
          return; // No more potential matches.
        }
      }

      // Case 2: Partner word is longer.
      // We've finished traversing `word`. Any word in the Trie that continues from
      // this node with a palindromic suffix is a valid pair. We pre-calculated
      // these and stored them in `palindromeIndices`.
      for (int k : root.palindromeIndices) {
        if (k != i) {
          result.add(Arrays.asList(i, k));
        }
      }
    }

    // Helper to check if a substring is a palindrome.
    private boolean isPalindrome(String s, int left, int right) {
      while (left < right) {
        if (s.charAt(left++) != s.charAt(right--)) {
          return false;
        }
      }
      return true;
    }
  }
}