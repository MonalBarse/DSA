package com.monal.Graphs.Topological;

import java.util.*;
/*
 * TOpological Sort and Graph Algorithms
 */


public class P001 {

  // ================================================================= //
  // ---------- PROBLEM 1: COURSE SCHEDULE (LEETCODE 207) ------------ //
  // ================================================================= //

  /*
   * Problem: There are 'numCourses' courses labeled 0 to numCourses-1.
   * Given prerequisites array where prerequisites[i] = [ai, bi] means
   * you must take course bi before course ai.
   *
   * Return true if you can finish all courses.
   *
   * Example: numCourses = 2, prerequisites = [[1,0]]
   * Output: true (take course 0, then course 1)
   *
   * Example: numCourses = 2, prerequisites = [[1,0],[0,1]]
   * Output: false (circular dependency!)
   */

  public static boolean canFinish(int numCourses, int[][] prerequisites) {
    // Build adjacency list
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) {
      adj.add(new ArrayList<>());
    }

    // Calculate indegrees
    int[] indegree = new int[numCourses];
    for (int[] prereq : prerequisites) {
      int course = prereq[0];
      int prerequisite = prereq[1];
      adj.get(prerequisite).add(course);
      indegree[course]++;
    }

    // Kahn's Algorithm
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (indegree[i] == 0) {
        queue.offer(i);
      }
    }

    int completedCourses = 0;
    while (!queue.isEmpty()) {
      int course = queue.poll();
      completedCourses++;

      for (int nextCourse : adj.get(course)) {
        indegree[nextCourse]--;
        if (indegree[nextCourse] == 0) {
          queue.offer(nextCourse);
        }
      }
    }

    return completedCourses == numCourses;
  }

  // =================================================================
  // PROBLEM 2: COURSE SCHEDULE II (LEETCODE 210) - ACTUAL ORDER
  // =================================================================
  /*
   * Same as above but return the actual course order.
   * If impossible, return empty array.
   */

  public static int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) {
      adj.add(new ArrayList<>());
    }

    int[] indegree = new int[numCourses];
    for (int[] prereq : prerequisites) {
      adj.get(prereq[1]).add(prereq[0]);
      indegree[prereq[0]]++;
    }

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (indegree[i] == 0) {
        queue.offer(i);
      }
    }

    int[] result = new int[numCourses];
    int index = 0;

    while (!queue.isEmpty()) {
      int course = queue.poll();
      result[index++] = course;

      for (int nextCourse : adj.get(course)) {
        indegree[nextCourse]--;
        if (indegree[nextCourse] == 0) {
          queue.offer(nextCourse);
        }
      }
    }

    return index == numCourses ? result : new int[0];
  }

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

  // =================================================================
  // PROBLEM 4: IS GRAPH BIPARTITE? (LEETCODE 785)
  // =================================================================
  /*
   * Given undirected graph, determine if it can be colored with 2 colors
   * such that no adjacent nodes have same color.
   *
   * Example: graph = [[1,3],[0,2],[1,3],[0,2]]
   * This forms a square: 0-1-2-3-0
   * Output: true (can color alternately)
   */

  public static boolean isBipartite(int[][] graph) {
    int n = graph.length;
    int[] color = new int[n];
    Arrays.fill(color, -1);

    for (int i = 0; i < n; i++) {
      if (color[i] == -1) {
        if (!bfsColor(graph, i, color)) {
          return false;
        }
      }
    }

    return true;
  }

  private static boolean bfsColor(int[][] graph, int start, int[] color) {
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    color[start] = 0;

    while (!queue.isEmpty()) {
      int node = queue.poll();

      for (int neighbor : graph[node]) {
        if (color[neighbor] == -1) {
          color[neighbor] = 1 - color[node];
          queue.offer(neighbor);
        } else if (color[neighbor] == color[node]) {
          return false;
        }
      }
    }

    return true;
  }

  // =================================================================
  // PROBLEM 5: POSSIBLE BIPARTITION (LEETCODE 886)
  // =================================================================
  /*
   * We want to split people into two groups. Given "dislikes" array where
   * dislikes[i] = [ai, bi] means person ai dislikes person bi.
   *
   * Return true if possible to split everyone into two groups such that
   * no one dislikes anyone in their group.
   */

  public static boolean possibleBipartition(int n, int[][] dislikes) {
    // Build adjacency list
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
      graph.add(new ArrayList<>());
    }

    for (int[] dislike : dislikes) {
      graph.get(dislike[0]).add(dislike[1]);
      graph.get(dislike[1]).add(dislike[0]);
    }

    int[] group = new int[n + 1];
    Arrays.fill(group, -1);

    for (int i = 1; i <= n; i++) {
      if (group[i] == -1) {
        if (!dfsAssignGroup(graph, i, 0, group)) {
          return false;
        }
      }
    }

    return true;
  }

  private static boolean dfsAssignGroup(List<List<Integer>> graph, int person,
      int currentGroup, int[] group) {
    group[person] = currentGroup;

    for (int dislikedPerson : graph.get(person)) {
      if (group[dislikedPerson] == -1) {
        if (!dfsAssignGroup(graph, dislikedPerson, 1 - currentGroup, group)) {
          return false;
        }
      } else if (group[dislikedPerson] == currentGroup) {
        return false; // Same group = conflict!
      }
    }

    return true;
  }

  // =================================================================
  // PROBLEM 6: MINIMUM HEIGHT TREES (LEETCODE 310) - TOPO SORT VARIANT
  // =================================================================
  /*
   * For undirected tree, find all nodes that minimize tree height when used as
   * root.
   *
   * Key insight: Remove leaf nodes layer by layer until 1-2 nodes remain.
   * Those are the answer!
   */

  public static List<Integer> findMinHeightTrees(int n, int[][] edges) {
    if (n == 1)
      return Arrays.asList(0);

    // Build adjacency list
    List<Set<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      adj.add(new HashSet<>());
    }

    for (int[] edge : edges) {
      adj.get(edge[0]).add(edge[1]);
      adj.get(edge[1]).add(edge[0]);
    }

    // Find initial leaves (degree = 1)
    Queue<Integer> leaves = new LinkedList<>();
    for (int i = 0; i < n; i++) {
      if (adj.get(i).size() == 1) {
        leaves.offer(i);
      }
    }

    int remaining = n;

    // Remove leaves layer by layer
    while (remaining > 2) {
      int leavesToRemove = leaves.size();
      remaining -= leavesToRemove;

      for (int i = 0; i < leavesToRemove; i++) {
        int leaf = leaves.poll();

        // Remove leaf from its only neighbor
        int neighbor = adj.get(leaf).iterator().next();
        adj.get(neighbor).remove(leaf);

        // If neighbor becomes leaf, add to queue
        if (adj.get(neighbor).size() == 1) {
          leaves.offer(neighbor);
        }
      }
    }

    return new ArrayList<>(leaves);
  }

  // =================================================================
  // TEST ALL SOLUTIONS
  // =================================================================
  public static void main(String[] args) {
    System.out.println("=== TESTING GRAPH ALGORITHMS ===\n");

    // Test 1: Course Schedule
    System.out.println("1. Course Schedule:");
    System.out.println("Can finish [[1,0]]: " +
        canFinish(2, new int[][] { { 1, 0 } })); // true
    System.out.println("Can finish [[1,0],[0,1]]: " +
        canFinish(2, new int[][] { { 1, 0 }, { 0, 1 } })); // false

    // Test 2: Course Order
    System.out.println("\n2. Course Order:");
    System.out.println("Order for [[1,0],[2,0],[3,1],[3,2]]: " +
        Arrays.toString(findOrder(4, new int[][] { { 1, 0 }, { 2, 0 }, { 3, 1 }, { 3, 2 } })));

    // Test 3: Alien Dictionary
    System.out.println("\n3. Alien Dictionary:");
    System.out.println("Order for [\"wrt\",\"wrf\",\"er\",\"ett\",\"rftt\"]: " +
        alienOrder(new String[] { "wrt", "wrf", "er", "ett", "rftt" }));

    // Test 4: Bipartite
    System.out.println("\n4. Is Bipartite:");
    int[][] graph1 = { { 1, 3 }, { 0, 2 }, { 1, 3 }, { 0, 2 } }; // Square
    System.out.println("Square graph: " + isBipartite(graph1)); // true

    // Test 5: Possible Bipartition
    System.out.println("\n5. Possible Bipartition:");
    System.out.println("4 people, dislikes [[1,2],[1,3],[2,4]]: " +
        possibleBipartition(4, new int[][] { { 1, 2 }, { 1, 3 }, { 2, 4 } })); // true

    // Test 6: Minimum Height Trees
    System.out.println("\n6. Minimum Height Trees:");
    System.out.println("Edges [[1,0],[1,2],[1,3]]: " +
        findMinHeightTrees(4, new int[][] { { 1, 0 }, { 1, 2 }, { 1, 3 } })); // [1]
  }
}
