package com.monal.Tries;

import java.util.*;

/**
 * COMPREHENSIVE TRIE DATA STRUCTURE TUTORIAL
 *
 * A Trie (pronounced "try") is a tree-like data structure used to store strings efficiently.
 * Also known as a Prefix Tree, it's particularly useful for:
 * 1. Auto-complete/suggestions
 * 2. Spell checkers
 * 3. IP routing tables
 * 4. Word games (like Boggle)
 * 5. String matching problems
 *
 * KEY INSIGHTS:
 * - Each node represents a character
 * - Path from root to node represents a prefix
 * - Leaf nodes (or marked nodes) represent complete words
 * - Common prefixes are shared, saving space
 *
 * TIME COMPLEXITIES:
 * - Insert: O(m) where m is length of word
 * - Search: O(m) where m is length of word
 * - Delete: O(m) where m is length of word
 *
 * SPACE COMPLEXITY: O(ALPHABET_SIZE * N * M) in worst case
 * where N is number of words and M is average length
 */

public class Treis {

    // ==================== BASIC TRIE NODE IMPLEMENTATION ====================

    /**
     * TrieNode class - The building block of our Trie
     *
     * DESIGN DECISIONS:
     * 1. Using array for children - faster access O(1) vs HashMap O(1) average but with overhead
     * 2. Boolean flag to mark end of words - distinguishes prefixes from complete words
     * 3. Static ALPHABET_SIZE - assumes lowercase English letters only
     *
     * INTERVIEW TIP: Always clarify the character set with interviewer!
     * - Only lowercase? Only uppercase? Numbers? Special characters?
     */
    static class TrieNode {
        private static final int ALPHABET_SIZE = 26; // a-z
        private TrieNode[] children;
        private boolean isEndOfWord;

        // OPTIMIZATION: You might see this in advanced implementations
        // private int wordCount; // Count of words ending at this node (for duplicates)
        // private int prefixCount; // Count of words passing through this node

        public TrieNode() {
            children = new TrieNode[ALPHABET_SIZE];
            isEndOfWord = false;

            // COMMON MISTAKE: Forgetting to initialize children array
            // Arrays in Java are automatically initialized to null, which is what we want
        }

        // Helper method to check if node has any children
        public boolean hasChildren() {
            for (TrieNode child : children) if (child != null) return true;

            return false;
        }
    }

    // ==================== MAIN TRIE CLASS ====================

    static class Trie {
        private TrieNode root;

        public Trie() {
            root = new TrieNode();
            // ROOT NODE INSIGHT: Root doesn't represent any character
            // It's just the starting point for all words
        }

        /**
         * INSERT OPERATION
         *
         * ALGORITHM:
         * 1. Start from root
         * 2. For each character, check if child exists
         * 3. If not, create new node
         * 4. Move to child node
         * 5. Mark last node as end of word
         *
         * EDGE CASES TO HANDLE:
         * - Empty string
         * - Null input
         * - Inserting same word multiple times
         * - Inserting word that's prefix of existing word
         */
        public void insert(String word) {
            // INPUT VALIDATION - Always do this in interviews!
            if (word == null || word.isEmpty()) {
                return; // or throw exception based on requirements
            }

            TrieNode current = root;

            // Traverse/create path for each character
            for (int i = 0; i < word.length(); i++) {
                char ch = word.charAt(i);
                int index = ch - 'a'; // Convert to array index

                // BOUNDS CHECK - Important for interview edge cases
                if (index < 0 || index >= 26) {
                    throw new IllegalArgumentException("Only lowercase letters supported");
                }

                // Create new node if doesn't exist
                if (current.children[index] == null) {
                    current.children[index] = new TrieNode();
                }

                current = current.children[index];
            }

            // Mark end of word
            current.isEndOfWord = true;

            // DUPLICATE HANDLING: If you need to count word frequencies
            // current.wordCount++;
        }

        /**
         * SEARCH OPERATION
         *
         * ALGORITHM:
         * 1. Start from root
         * 2. For each character, check if child exists
         * 3. If any child missing, word doesn't exist
         * 4. If we reach end, check if it's marked as complete word
         *
         * INTERVIEW INSIGHT: This is different from "startsWith"!
         * search("cat") returns false if only "catch" was inserted
         */
        public boolean search(String word) {
            if (word == null || word.isEmpty()) {
                return false; // or return true based on requirements
            }

            TrieNode node = searchNode(word);
            return node != null && node.isEndOfWord;

            // COMMON MISTAKE: Forgetting to check isEndOfWord
            // Just finding the path isn't enough - must be a complete word
        }

        /**
         * STARTS WITH (PREFIX SEARCH)
         *
         * This is where Trie really shines! O(m) prefix search
         * Much better than naive string matching approaches
         */
        public boolean startsWith(String prefix) {
            if (prefix == null || prefix.isEmpty()) {
                return true; // Empty prefix matches everything
            }

            return searchNode(prefix) != null;
        }

        /**
         * Helper method to find node corresponding to a string
         * Returns null if path doesn't exist
         *
         * CODE REUSE PRINCIPLE: Both search and startsWith use this
         */
        private TrieNode searchNode(String str) {
            TrieNode current = root;

            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                int index = ch - 'a';

                if (index < 0 || index >= 26 || current.children[index] == null) {
                    return null;
                }

                current = current.children[index];
            }

            return current;
        }

        /**
         * DELETE OPERATION - Most Complex!
         *
         * ALGORITHM:
         * 1. Find the word using DFS
         * 2. Unmark the end node
         * 3. Remove nodes that are no longer needed (bottom-up cleanup)
         *
         * COMPLEXITY: A node can be deleted only if:
         * - It's not end of another word
         * - It has no children (not part of other words)
         *
         * INTERVIEW STRATEGY: Explain the recursive approach step by step
         */
        public void delete(String word) {
            if (word == null || word.isEmpty()) {
                return;
            }

            deleteHelper(root, word, 0);
        }

        /**
         * Recursive helper for delete operation
         * Returns true if current node should be deleted
         *
         * RECURSIVE THINKING:
         * - Base case: reached end of word
         * - Recursive case: go deeper, then decide based on return value
         */
        private boolean deleteHelper(TrieNode current, String word, int index) {
            // BASE CASE: reached end of word
            if (index == word.length()) {
                // If not end of word, nothing to delete
                if (!current.isEndOfWord) {
                    return false;
                }

                // Unmark as end of word
                current.isEndOfWord = false;

                // Delete current node only if it has no children
                // (i.e., it's not part of other words)
                return !current.hasChildren();
            }

            char ch = word.charAt(index);
            int charIndex = ch - 'a';
            TrieNode node = current.children[charIndex];

            // Character doesn't exist, word not in trie
            if (node == null) {
                return false;
            }

            // RECURSIVE STEP
            boolean shouldDeleteChild = deleteHelper(node, word, index + 1);

            if (shouldDeleteChild) {
                current.children[charIndex] = null;

                // Delete current node if:
                // 1. It's not end of another word
                // 2. It has no other children
                return !current.isEndOfWord && !current.hasChildren();
            }

            return false;
        }

        /**
         * GET ALL WORDS WITH PREFIX - Common Interview Problem
         *
         * APPROACH:
         * 1. Find the node corresponding to prefix
         * 2. DFS from that node to collect all words
         *
         * USE CASE: Auto-complete functionality
         */
        public List<String> getAllWordsWithPrefix(String prefix) {
            List<String> result = new ArrayList<>();
            TrieNode prefixNode = searchNode(prefix);

            if (prefixNode == null) return result; // No words with this prefix

            // DFS to collect all words from this node
            collectWords(prefixNode, prefix, result);
            return result;
        }

        /**
         * DFS helper to collect all words from a given node
         *
         * TECHNIQUE: Building string character by character
         * OPTIMIZATION: Using StringBuilder for better performance
         */
        private void collectWords(TrieNode node, String currentWord, List<String> result) {
            if (node.isEndOfWord) {
                result.add(currentWord);
            }

            // Explore all possible children
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    char nextChar = (char) ('a' + i);
                    collectWords(node.children[i], currentWord + nextChar, result);
                }
            }
        }

        /**
         * COUNT WORDS WITH PREFIX - Another Common Problem
         */
        public int countWordsWithPrefix(String prefix) {
            TrieNode prefixNode = searchNode(prefix);
            if (prefixNode == null) return 0;

            return countWordsHelper(prefixNode);
        }

        private int countWordsHelper(TrieNode node) {
            int count = 0;
            if (node.isEndOfWord) count++;

            for (TrieNode child : node.children) {
                if (child != null) {
                    count += countWordsHelper(child);
                }
            }
            return count;
        }

        /**
         * LONGEST COMMON PREFIX - Classic Problem
         *
         * ALGORITHM:
         * 1. Insert all strings
         * 2. Traverse from root until we find a branching or end
         */
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) return "";

            // Insert all strings
            for (String str : strs) {
                if (str.isEmpty()) return ""; // Any empty string means no common prefix
                insert(str);
            }

            StringBuilder lcp = new StringBuilder();
            TrieNode current = root;

            // Keep going while there's exactly one child and we haven't reached end of any word
            while (true) {
                int childCount = 0;
                int childIndex = -1;

                // Count non-null children
                for (int i = 0; i < 26; i++) {
                    if (current.children[i] != null) {
                        childCount++;
                        childIndex = i;
                    }
                }

                // Stop if more than one child (branching) or current node is end of word
                if (childCount != 1 || current.isEndOfWord) {
                    break;
                }

                // Move to the single child
                lcp.append((char) ('a' + childIndex));
                current = current.children[childIndex];
            }

            return lcp.toString();
        }
    }

    // ==================== ADVANCED TRIE PROBLEMS ====================

    /**
     * WORD SEARCH II (LeetCode 212) - Advanced Trie Application
     *
     * PROBLEM: Given a 2D board and a list of words, find all words on the board
     *
     * WHY TRIE? Instead of searching each word individually (expensive),
     * we build a trie of all words and do ONE DFS traversal
     *
     * TIME COMPLEXITY: O(M*N*4^L) where L is max word length
     * Much better than naive O(W*M*N*4^L) where W is number of words
     */
    static class WordSearchII {
        private Trie trie;
        private List<String> result;
        private boolean[][] visited;

        public List<String> findWords(char[][] board, String[] words) {
            // Build trie with all words
            trie = new Trie();
            for (String word : words) {
                trie.insert(word);
            }

            result = new ArrayList<>();
            visited = new boolean[board.length][board[0].length];

            // Try starting DFS from each cell
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    dfs(board, i, j, trie.root, new StringBuilder());
                }
            }

            return result;
        }

        /**
         * DFS with Trie traversal
         *
         * KEY INSIGHT: We traverse the trie and board simultaneously
         * If trie path doesn't exist, we can prune the entire branch!
         */
        private void dfs(char[][] board, int i, int j, TrieNode node, StringBuilder word) {
            // Boundary and visited checks
            if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || visited[i][j]) {
                return;
            }

            char ch = board[i][j];
            int index = ch - 'a';

            // PRUNING: If this character doesn't exist in trie, stop
            if (index < 0 || index >= 26 || node.children[index] == null) {
                return;
            }

            // Move to next trie node
            node = node.children[index];
            word.append(ch);
            visited[i][j] = true;

            // Found a word!
            if (node.isEndOfWord) {
                result.add(word.toString());
                node.isEndOfWord = false; // Avoid duplicates
            }

            // Explore all 4 directions
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};

            for (int k = 0; k < 4; k++) {
                dfs(board, i + dx[k], j + dy[k], node, word);
            }

            // BACKTRACK
            word.deleteCharAt(word.length() - 1);
            visited[i][j] = false;
        }
    }

    // ==================== MAIN METHOD - COMPREHENSIVE TESTING ====================

    public static void main(String[] args) {
        System.out.println("=== COMPREHENSIVE TRIE TUTORIAL ===\n");

        // Test basic operations
        testBasicOperations();

        // Test edge cases
        testEdgeCases();

        // Test advanced operations
        testAdvancedOperations();

        // Test interview problems
        testInterviewProblems();
    }

    /**
     * Test basic Trie operations
     *
     * LEARNING TIP: Always test your data structure with simple cases first
     */
    private static void testBasicOperations() {
        System.out.println("=== BASIC OPERATIONS TEST ===");

        Trie trie = new Trie();

        // Test insertions
        String[] words = {"cat", "cats", "dog", "doggy", "dodge", "car", "card"};
        System.out.println("Inserting words: " + Arrays.toString(words));

        for (String word : words) {
            trie.insert(word);
        }

        // Test searches
        System.out.println("\n--- Search Tests ---");
        String[] searchTests = {"cat", "cats", "car", "care", "dog", "do"};

        for (String word : searchTests) {
            boolean found = trie.search(word);
            System.out.printf("search('%s'): %s\n", word, found);
        }

        // Test prefix searches
        System.out.println("\n--- Prefix Tests ---");
        String[] prefixTests = {"ca", "do", "dodge", "xyz"};

        for (String prefix : prefixTests) {
            boolean hasPrefix = trie.startsWith(prefix);
            System.out.printf("startsWith('%s'): %s\n", prefix, hasPrefix);
        }

        System.out.println();
    }

    /**
     * Test edge cases - Critical for interviews!
     *
     * INTERVIEW TIP: Always discuss edge cases with interviewer
     */
    private static void testEdgeCases() {
        System.out.println("=== EDGE CASES TEST ===");

        Trie trie = new Trie();

        // Test empty string
        System.out.println("Testing empty string operations:");
        trie.insert("");
        System.out.println("search(''): " + trie.search(""));
        System.out.println("startsWith(''): " + trie.startsWith(""));

        // Test null handling (should not crash)
        System.out.println("\nTesting null handling:");
        try {
            trie.insert(null);
            System.out.println("insert(null): handled gracefully");
        } catch (Exception e) {
            System.out.println("insert(null): threw " + e.getClass().getSimpleName());
        }

        // Test prefix vs complete word
        System.out.println("\nTesting prefix vs complete word:");
        trie.insert("test");
        trie.insert("testing");
        System.out.println("After inserting 'test' and 'testing':");
        System.out.println("search('test'): " + trie.search("test"));
        System.out.println("search('tes'): " + trie.search("tes"));
        System.out.println("startsWith('tes'): " + trie.startsWith("tes"));

        System.out.println();
    }

    /**
     * Test advanced operations
     */
    private static void testAdvancedOperations() {
        System.out.println("=== ADVANCED OPERATIONS TEST ===");

        Trie trie = new Trie();
        String[] words = {"cat", "cats", "dog", "doggy", "car", "card", "care"};

        for (String word : words) {
            trie.insert(word);
        }

        // Test getting all words with prefix
        System.out.println("All words with prefix 'ca': " + trie.getAllWordsWithPrefix("ca"));
        System.out.println("All words with prefix 'dog': " + trie.getAllWordsWithPrefix("dog"));
        System.out.println("All words with prefix 'xyz': " + trie.getAllWordsWithPrefix("xyz"));

        // Test counting words with prefix
        System.out.println("Count words with prefix 'ca': " + trie.countWordsWithPrefix("ca"));
        System.out.println("Count words with prefix 'dog': " + trie.countWordsWithPrefix("dog"));

        // Test deletion
        System.out.println("\n--- Deletion Tests ---");
        System.out.println("Before deletion - search('cat'): " + trie.search("cat"));
        System.out.println("Before deletion - search('cats'): " + trie.search("cats"));

        trie.delete("cat");
        System.out.println("After deleting 'cat':");
        System.out.println("search('cat'): " + trie.search("cat"));
        System.out.println("search('cats'): " + trie.search("cats"));
        System.out.println("startsWith('ca'): " + trie.startsWith("ca"));

        System.out.println();
    }

    /**
     * Test common interview problems
     */
    private static void testInterviewProblems() {
        System.out.println("=== INTERVIEW PROBLEMS TEST ===");

        // Test longest common prefix
        Trie trie = new Trie();
        String[] testStrings = {"flower", "flow", "flight"};
        String lcp = trie.longestCommonPrefix(testStrings);
        System.out.println("Longest common prefix of " + Arrays.toString(testStrings) + ": '" + lcp + "'");

        String[] testStrings2 = {"dog", "racecar", "car"};
        String lcp2 = trie.longestCommonPrefix(testStrings2);
        System.out.println("Longest common prefix of " + Arrays.toString(testStrings2) + ": '" + lcp2 + "'");

        System.out.println("\n=== TUTORIAL COMPLETE ===");
        System.out.println("Key Takeaways:");
        System.out.println("1. Tries excel at prefix-based operations");
        System.out.println("2. Always handle edge cases (null, empty strings)");
        System.out.println("3. Deletion is the most complex operation");
        System.out.println("4. Memory usage can be high - consider compression for production");
        System.out.println("5. Great for auto-complete, spell check, and word games");
    }
}

/**
 * INTERVIEW TIPS & COMMON PITFALLS:
 *
 * 1. ALWAYS CLARIFY CHARACTER SET
 *    - Only lowercase letters? What about numbers/symbols?
 *    - This affects your array size and index calculation
 *
 * 2. HANDLE EDGE CASES
 *    - Empty strings, null inputs
 *    - Distinguish between prefix and complete word
 *
 * 3. MEMORY CONSIDERATIONS
 *    - Each node can have up to 26 children (for English letters)
 *    - Consider using HashMap instead of array for sparse data
 *    - For production: compressed tries, radix trees
 *
 * 4. DELETION IS TRICKY
 *    - Must carefully remove nodes without breaking other words
 *    - Use recursive approach for clean implementation
 *
 * 5. COMMON OPTIMIZATION: Early termination
 *    - In search operations, return false as soon as path doesn't exist
 *    - In DFS problems (like Word Search II), prune aggressively
 *
 * 6. TIME COMPLEXITY ANALYSIS
 *    - Operations are O(m) where m is word length
 *    - Space can be O(ALPHABET_SIZE * N * M) in worst case
 *    - Much better than naive string matching for prefix operations
 *
 * PRACTICE PROBLEMS:
 * - LeetCode 208: Implement Trie
 * - LeetCode 211: Design Add and Search Words Data Structure
 * - LeetCode 212: Word Search II
 * - LeetCode 14: Longest Common Prefix
 * - LeetCode 676: Implement Magic Dictionary
 */