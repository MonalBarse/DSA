package com.monal.Graphs.Topological;

import java.util.*;

public class P004 {

  // =================================================================
  // PROBLEM 3: ALIEN DICTIONARY (HARD) - TOPOLOGICAL SORT
  // =================================================================
  /*
   * Given words in alien language in sorted order, find the order
   * of characters in alien alphabet.
   *
   * Example: words = ["wrt","wrf","er","ett","rftt"]
   * Output: "wertf"
   *
   * Logic: Compare adjacent words to find character ordering
   */

  public static String alienOrder(String[] words) {
    // Step 1: Initialize graph
    Map<Character, Set<Character>> graph = new HashMap<>();
    Map<Character, Integer> indegree = new HashMap<>();

    // Initialize all characters
    for (String word : words) {
      for (char c : word.toCharArray()) {
        graph.putIfAbsent(c, new HashSet<>());
        indegree.putIfAbsent(c, 0);
      }
    }

    // Step 2: Build graph by comparing adjacent words
    for (int i = 0; i < words.length - 1; i++) {
      String word1 = words[i];
      String word2 = words[i + 1];

      // Check for invalid case: "abc" before "ab"
      if (word1.length() > word2.length() && word1.startsWith(word2)) {
        return "";
      }

      // Find first different character
      for (int j = 0; j < Math.min(word1.length(), word2.length()); j++) {
        char c1 = word1.charAt(j);
        char c2 = word2.charAt(j);

        if (c1 != c2) {
          if (!graph.get(c1).contains(c2)) {
            graph.get(c1).add(c2);
            indegree.put(c2, indegree.get(c2) + 1);
          }
          break; // Only first difference matters
        }
      }
    }

    // Step 3: Topological Sort
    Queue<Character> queue = new LinkedList<>();
    for (char c : indegree.keySet()) {
      if (indegree.get(c) == 0) {
        queue.offer(c);
      }
    }

    StringBuilder result = new StringBuilder();
    while (!queue.isEmpty()) {
      char c = queue.poll();
      result.append(c);

      for (char next : graph.get(c)) {
        indegree.put(next, indegree.get(next) - 1);
        if (indegree.get(next) == 0) {
          queue.offer(next);
        }
      }
    }

    return result.length() == indegree.size() ? result.toString() : "";
  }

  public static void main(String[] args) {
    System.out.println("\n3. Alien Dictionary:");
    System.out.println("Order for [\"wrt\",\"wrf\",\"er\",\"ett\",\"rftt\"]: " +
        alienOrder(new String[] { "wrt", "wrf", "er", "ett", "rftt" }));
  }
}
