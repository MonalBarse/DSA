package com.monal.Graphs.BFS_DFS;

import java.util.*;
/*
  A tree is an undirected graph in which any two vertices are connected by
  exactly one path. In other words, any connected graph without simple cycles is a tree.

  Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges
  where edges[i] = [ai, bi] indicates that there is an undirected edge between the two
  nodes ai and bi in the tree, you can choose any node of the tree as the root.
  When you select a node x as the root, the result tree has height h. Among all possible rooted trees,
  those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).
  Return a list of all MHTs' root labels. You can return the answer in any order.
  The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
*/

// The Maximum Number of Minimum Height Trees (MHTs) in a graph with n nodes and n-1 edges is at most 2.
// Approach:
/*
 * 1. Build the graph using an adjacency list.
 * 2. Use a queue to perform a BFS traversal.
 * 3. Start from the leaves (nodes with only one connection).
 * 4. Remove the leaves and their edges from the graph.
 * 5. Repeat until only one or two nodes are left.
 * 6. Return the remaining nodes as the roots of the MHTs.
 */

public class P016 {
  class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
      List<Integer> result = new ArrayList<>();
      if (n == 1) {
        result.add(0);
        return result;
      }

      // Build graph adgj
      List<Set<Integer>> adj = new ArrayList<>();
      for (int i = 0; i < n; i++)
        adj.add(new HashSet<>());
      for (int[] edge : edges) {
        adj.get(edge[0]).add(edge[1]);
        adj.get(edge[1]).add(edge[0]);
      }

      // leaves have (degree = 1)
      Queue<Integer> leaves = new LinkedList<>();
      for (int i = 0; i < n; i++)
        if (adj.get(i).size() == 1)
          leaves.offer(i);
      int remainingNodes = n;

      // Remove leaves layer by layer until 1-2 nodes remain
      while (remainingNodes > 2) {
        int leafCount = leaves.size();
        remainingNodes -= leafCount;

        // Process current layer of leaves
        for (int i = 0; i < leafCount; i++) {
          int leaf = leaves.poll();
          // Remove this leaf from all its neighbors
          for (int neighbor : adj.get(leaf)) {
            adj.get(neighbor).remove(leaf); // IMP (forgot)
            if (adj.get(neighbor).size() == 1) // if turned to leaf add to q
              leaves.offer(neighbor);
          }
        }
      }

      // Collect remaining nodes (these are the centers)
      while (!leaves.isEmpty())
        result.add(leaves.poll());
      return result;
    }
  }

  // Test method to verify the solution
  public static void main(String[] args) {
    P016 problem = new P016();
    Solution solution = problem.new Solution();

    // Test case 1: n=4, edges=[[1,0],[1,2],[1,3]]
    // Tree looks like: 0-1-2, with 3 also connected to 1
    // Expected: [1] (node 1 is the center)
    int[][] edges1 = { { 1, 0 }, { 1, 2 }, { 1, 3 } };
    System.out.println("Test 1: " + solution.findMinHeightTrees(4, edges1)); // Expected: [1]

    // Test case 2: n=6, edges=[[3,0],[3,1],[3,2],[3,4],[5,4]]
    // Tree looks like: 0,1,2 connected to 3, 3-4-5
    // Expected: [3,4] (both 3 and 4 are centers)
    int[][] edges2 = { { 3, 0 }, { 3, 1 }, { 3, 2 }, { 3, 4 }, { 5, 4 } };
    System.out.println("Test 2: " + solution.findMinHeightTrees(6, edges2)); // Expected: [3,4]

    // Test case 3: n=1 (single node)
    // Expected: [0]
    int[][] edges3 = {};
    System.out.println("Test 3: " + solution.findMinHeightTrees(1, edges3)); // Expected: [0]

    // Test case 4: n=2, edges=[[0,1]]
    // Expected: [0,1] (both nodes are centers)
    int[][] edges4 = { { 0, 1 } };
    System.out.println("Test 4: " + solution.findMinHeightTrees(2, edges4)); // Expected: [0,1]
  }
}
