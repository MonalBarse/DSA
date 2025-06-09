package com.monal.Graphs.Topological;

import java.util.*;

public class P005 {

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

  public static void main(String[] args) {
    // Example for isBipartite
    int[][] graph1 = { { 1, 3 }, { 0, 2 }, { 1, 3 }, { 0, 2 } };
    System.out.println("Is Bipartite: " + isBipartite(graph1)); // Output: true

    // Example for possibleBipartition
    int n = 4;
    int[][] dislikes = { { 1, 2 }, { 1, 3 }, { 2, 4 } };
    System.out.println("Possible Bipartition: " + possibleBipartition(n, dislikes)); // Output: true
  }
}
