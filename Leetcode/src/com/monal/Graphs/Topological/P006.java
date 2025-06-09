package com.monal.Graphs.Topological;

import java.util.*;

public class P006 {

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
}
