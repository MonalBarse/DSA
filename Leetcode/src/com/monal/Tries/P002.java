package com.monal.Tries;

/*
Problem statement
Ninja has to implement a data structure ”TRIE” from scratch. Ninja has to complete some functions.
1) Trie(): Ninja has to initialize the object of this “TRIE” data structure.
2) insert(“WORD”): Ninja has to insert the string “WORD”  into this “TRIE” data structure.
3) countWordsEqualTo(“WORD”): Ninja has to return how many times this “WORD” is present in this “TRIE”.
4) countWordsStartingWith(“PREFIX”): Ninjas have to return how many words are there in this “TRIE” that have the string “PREFIX” as a prefix.
5) erase(“WORD”): Ninja has to delete one occurrence of the string “WORD” from the “TRIE”.

Note:
1. If erase(“WORD”) function is called then it is guaranteed that the “WORD” is present in the “TRIE”.
2. If you are going to use variables with dynamic memory allocation then you need to release the memory associated with them at the end of your solution.
Can you help Ninja implement the "TRIE" data structure?

Constraints:
1 <= “T” <= 50
1 <= “N” <= 10000
 “WORD” = {a to z}
1 <= | “WORD” | <= 1000

Where “T” is the number of test cases, “N” denotes how many times the functions(as discussed above) we call, “WORD” denotes the string on which we have to perform all the operations as we discussed above, and | “WORD” | denotes the length of the string “WORD”.

Time limit: 1 sec.
Sample Input 1:
  1 5
  insert coding
  insert ninja
  countWordsEqualTo coding
  countWordsStartingWith nin
  erase coding

Sample Output 1:
  1 1
    Explanation of sample input 1:
    After insertion of “coding” in “TRIE”:
    After insertion of “ninja” in “TRIE”:
    Count words equal to “coding” :
    Count words those prefix is “nin”:
    After deletion of the word “coding”, “TRIE” is:

Sample Input 2:
  1 6
  insert samsung
  insert samsung
  insert vivo
  erase vivo
  countWordsEqualTo samsung
  countWordsStartingWith vi

Sample Output 2:
  2 0

  Explanation for sample input 2:
    insert “samsung”: we are going to insert the word “samsung” into the “TRIE”.
    insert “samsung”: we are going to insert another “samsung” word into the “TRIE”.
    insert “vivo”: we are going to insert the word “vivo” into the “TRIE”.
    erase “vivo”: we are going to delete the word “vivo” from the “TRIE”.
    countWordsEqualTo “samsung”: There are two instances of “sumsung” is present in “TRIE”.
    countWordsStartingWith “vi”: There is not a single word in the “TRIE” that starts from the prefix “vi”.

 */
public class P002 {

  public class Trie {
    static class TrieNode {
      TrieNode[] children;
      int countEndsWith; // Stores the count of words ending at this node.
      int countPrefix; // Stores the count of words having this prefix.

      public TrieNode() {
        children = new TrieNode[26]; // For 'a' through 'z'
        countEndsWith = 0;
        countPrefix = 0;
      }
    }

    // The root of the Trie. It's an empty node that acts as the starting point.
    private final TrieNode root;

    // Initializes the Trie data structure.
    public Trie() {
      root = new TrieNode();
    }

    /**
     * Inserts a word into the Trie.
     * Time Complexity: O(L), where L is the length of the word.
     */
    public void insert(String word) {
      TrieNode currentNode = root;
      for (char ch : word.toCharArray()) {
        int index = ch - 'a';
        // If the child node for the character doesn't exist, create it.
        if (currentNode.children[index] == null) {
          currentNode.children[index] = new TrieNode();
        }
        // Move to the child node.
        currentNode = currentNode.children[index];
        // Increment the prefix count for every node on the path.
        currentNode.countPrefix++;
      }
      // At the end of the word, increment the word-end count.
      currentNode.countEndsWith++;
    }

    /**
     * Counts how many times a word is present in the Trie.
     * Time Complexity: O(L), where L is the length of the word.
     */
    public int countWordsEqualTo(String word) {
      TrieNode currentNode = root;
      for (char ch : word.toCharArray()) {
        int index = ch - 'a';
        // If the path doesn't exist, the word is not in the Trie.
        if (currentNode.children[index] == null) {
          return 0;
        }
        currentNode = currentNode.children[index];
      }
      // The count of the word is stored at the final node.
      return currentNode.countEndsWith;
    }

    /**
     * Counts how many words start with a given prefix.
     * Time Complexity: O(P), where P is the length of the prefix.
     */
    public int countWordsStartingWith(String prefix) {
      TrieNode currentNode = root;
      for (char ch : prefix.toCharArray()) {
        int index = ch - 'a';
        // If the path for the prefix doesn't exist, no words start with it.
        if (currentNode.children[index] == null) {
          return 0;
        }
        currentNode = currentNode.children[index];
      }
      // The number of words with this prefix is stored in the prefix count of the
      // final node.
      return currentNode.countPrefix;
    }

    /**
     * Deletes one occurrence of a word from the Trie.
     * Note: It is guaranteed that the word is present in the Trie.
     * Time Complexity: O(L), where L is the length of the word.
     */
    public void erase(String word) {
      TrieNode currentNode = root;
      for (char ch : word.toCharArray()) {
        int index = ch - 'a';
        // Move to the child node (guaranteed to exist).
        currentNode = currentNode.children[index];
        // Decrement the prefix count along the path.
        currentNode.countPrefix--;
      }
      // Decrement the word-end count at the final node.
      currentNode.countEndsWith--;
    }

  }
}
