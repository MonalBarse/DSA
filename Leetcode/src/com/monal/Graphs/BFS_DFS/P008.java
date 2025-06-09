package com.monal.Graphs.BFS_DFS;
/* WORLD LADDER I and II
Leetcode: https://leetcode.com/problems/word-ladder/
Leetcode: https://leetcode.com/problems/word-ladder-ii/ */

import java.util.*;

/*
  A transformation sequence from word beginWord to word endWord using a dictionary wordList
  is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
  Every adjacent pair of words differs by a single letter.
  Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
  sk == endWord
  Given two words, beginWord and endWord, and a dictionary wordList, return the number
  of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.

  Example 1:
    Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
    Output: 5
    Explanation: One shortest transformation sequence is "hit" -> "hot" -> "dot" -> "dog" -> cog", which is 5 words long.
  Example 2:
    Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
    Output: 0
    Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
*/

/*
  A transformation sequence from word beginWord to word endWord using a dictionary
  wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
  Every adjacent pair of words differs by a single letter.
  Every si for 1 <= i <= k is in wordList. Note that beginWord does
  not need to be in wordList sk == endWord
  Given two words, beginWord and endWord, and a dictionary wordList, return all
  the shortest transformation sequences from beginWord to endWord, or an empty list if no
  such sequence exists. Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].

  Example 1:
    Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
    Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
    Explanation: There are 2 shortest transformation sequences:
    "hit" -> "hot" -> "dot" -> "dog" -> "cog"
    "hit" -> "hot" -> "lot" -> "log" -> "cog"
  Example 2:
    Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
    Output: []
    Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
*/

public class P008 {
  class Solution1 {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
      Set<String> wordSet = new HashSet<>(wordList);
      if (!wordSet.contains(endWord))
        return 0;

      Queue<String> queue = new ArrayDeque<>();
      Set<String> visited = new HashSet<>();

      queue.offer(beginWord);
      visited.add(beginWord);

      int level = 1; // Level 1 means beginWord itself

      while (!queue.isEmpty()) {
        int size = queue.size();

        // Traverse current level
        for (int i = 0; i < size; i++) {
          String word = queue.poll();

          // Try changing each character to 'a' to 'z'
          char[] chars = word.toCharArray();
          for (int j = 0; j < chars.length; j++) {
            char original = chars[j];
            for (char c = 'a'; c <= 'z'; c++) {
              if (c == original)
                continue;
              chars[j] = c;
              String nextWord = new String(chars);

              if (nextWord.equals(endWord)) {
                return level + 1; // Found shortest path
              }

              if (wordSet.contains(nextWord) && !visited.contains(nextWord)) {
                queue.offer(nextWord);
                visited.add(nextWord);
              }
            }
            chars[j] = original; // Restore
          }
        }

        level++; // Move to next level
      }

      return 0; // No path found
    }
  }

  /**
   * Word Ladder II - Find All Shortest Transformation Sequences
   *
   * Problem: Given a begin word, end word, and dictionary of words, find all
   * shortest
   * transformation sequences from begin word to end word such that:
   * 1. Only one letter can be changed at a time
   * 2. Each transformed word must exist in the word list
   * 3. All intermediate words must be valid dictionary words
   *
   * Algorithm: Two-phase approach
   * Phase 1: BFS to find shortest path distances to all reachable words
   * Phase 2: DFS backtracking to reconstruct all shortest paths
   */
  class Solution2 {

    /**
     * Find all shortest transformation sequences from beginWord to endWord
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
      Set<String> wordSet = new HashSet<>(wordList);
      if (!wordSet.contains(endWord))
        return new ArrayList<>();

      // Add beginWord to wordSet for neighbor generation
      wordSet.add(beginWord);

      // Step 1: BFS to build shortest distance map
      Map<String, Integer> distanceMap = buildDistanceMap(beginWord, wordSet);
      // If endWord is not reachable, return empty list
      if (!distanceMap.containsKey(endWord))
        return new ArrayList<>();

      // Step 2: Backtrack to collect all shortest paths
      List<List<String>> result = new ArrayList<>();
      List<String> currentPath = new ArrayList<>();
      currentPath.add(endWord);
      backtrack(endWord, beginWord, distanceMap, wordSet, currentPath, result);
      return result;
    }

    private Map<String, Integer> buildDistanceMap(String beginWord, Set<String> wordSet) {
      Queue<Pair> queue = new LinkedList<>();
      Map<String, Integer> distanceMap = new HashMap<>();
      Set<String> visited = new HashSet<>(); // Use separate visited set

      queue.offer(new Pair(beginWord, 1));
      distanceMap.put(beginWord, 1);
      visited.add(beginWord); // Mark as visited without removing from wordSet

      while (!queue.isEmpty()) {
        Pair current = queue.poll();
        String word = current.word;
        int level = current.distance;

        for (String neighbor : getNeighbors(word, wordSet)) {
          if (!visited.contains(neighbor)) {
            distanceMap.put(neighbor, level + 1);
             queue.offer(new Pair(neighbor, level + 1));
            visited.add(neighbor); // Add to visited set
          }
        }
      }
      return distanceMap;
    }

    private void backtrack(String currentWord, String beginWord, Map<String, Integer> distanceMap,
        Set<String> wordSet, List<String> path, List<List<String>> result) {
      if (currentWord.equals(beginWord)) {
        List<String> validPath = new ArrayList<>(path);
        Collections.reverse(validPath);
        result.add(validPath);
        return;
      }

      int currentDistance = distanceMap.get(currentWord);
      for (String neighbor : getNeighbors(currentWord, wordSet)) {
        if (distanceMap.containsKey(neighbor) &&
            distanceMap.get(neighbor) == currentDistance - 1) {
          path.add(neighbor);
          backtrack(neighbor, beginWord, distanceMap, wordSet, path, result);
          path.remove(path.size() - 1);
        }
      }
    }

    private List<String> getNeighbors(String word, Set<String> wordSet) {
      List<String> neighbors = new ArrayList<>();
      char[] chars = word.toCharArray();

      for (int i = 0; i < chars.length; i++) {
        char original = chars[i];
        for (char c = 'a'; c <= 'z'; c++) {
          if (c == original)
            continue;
          chars[i] = c;
          String newWord = new String(chars);
          if (wordSet.contains(newWord)) {
            neighbors.add(newWord);
          }
        }
        chars[i] = original; // Restore
      }
      return neighbors;
    }

    private static class Pair {
      String word;
      int distance;

      Pair(String word, int distance) {
        this.word = word;
        this.distance = distance;
      }
    }
  }

  /*
   * Time Complexity: O(N * M * 26) for BFS + O(N * M * 26) for DFS = O(N * M)
   * where N = number of words in dictionary, M = length of each word
   *
   * Space Complexity: O(N * M) for storing the distance map and result paths
   *
   * Key Insights:
   * 1. BFS finds shortest distances because it explores level by level
   * 2. DFS backtracking ensures we only follow paths that maintain shortest
   * distance
   * 3. Working backwards from endWord to beginWord is easier for path
   * reconstruction
   * 4. Distance map acts as a "guide" to ensure we only follow shortest paths
   * during DFS
   */

  public static void main(String[] args) {
    P008 p008 = new P008();
    Solution1 solution1 = p008.new Solution1();

    String beginWord = "hit";
    String endWord = "cog";
    List<String> wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");
    int result = solution1.ladderLength(beginWord, endWord, wordList);
    System.out.println("Shortest transformation sequence length: " + result); // Output: 5

    beginWord = "hit";
    endWord = "fog";
    wordList = Arrays.asList("hot", "dot", "dog", "lot", "log", "fog");
    result = solution1.ladderLength(beginWord, endWord, wordList);
    System.out.println("Shortest transformation sequence length: " + result);

  }
}
