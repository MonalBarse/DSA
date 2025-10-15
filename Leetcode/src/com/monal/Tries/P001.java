package com.monal.Tries;

public class P001 {
  class Trie {
    private class TrieNode {
      private TrieNode[] children;
      // A boolean flag to indicate if this node marks the end of a complete word.
      private boolean isEndOfWord;

      public TrieNode() {
        // Initialize the children array for 26 lowercase English letters.
        children = new TrieNode[26];
        isEndOfWord = false;
      }
    }

    private final TrieNode root;

    public Trie() {
      root = new TrieNode();
    }

    public void insert(String word) {
      TrieNode current = root;
      for (char c : word.toCharArray()) {
        // Calculate the index for the character (0-25).
        int index = c - 'a';
        // If the path for this character doesn't exist, create a new node.
        if (current.children[index] == null)
          current.children[index] = new TrieNode();

        current = current.children[index];
      }
      current.isEndOfWord = true;
    }

    private TrieNode searchPrefix(String str) {
      TrieNode current = root;
      for (char c : str.toCharArray()) {
        int index = c - 'a';
        // If at any point the path is broken, the string doesn't exist.
        if (current.children[index] == null)
          return null;
        // Continue down the path.
        current = current.children[index];
      }
      return current;
    }

    public boolean search(String word) {
      TrieNode node = searchPrefix(word);
      // The word exists only if a path was found (node is not null) AND
      // that final node is marked as the end of a word.
      return node != null && node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
      TrieNode node = searchPrefix(prefix);
      // If a path for the prefix exists (node is not null), then we have a match.
      return node != null;
    }
  }
}
