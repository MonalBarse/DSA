package com.monal.Graphs.BFS_DFS;

import java.util.*;

/*
You are given an integer n. There is an undirected graph with n vertices, numbered from 0 to n - 1. You are given a 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected edge connecting vertices ai and bi.

Return the number of complete connected components of the graph.

A connected component is a subgraph of a graph in which there exists a path between any two vertices,
and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.

A connected component is said to be complete if there exists an edge between every pair of its vertices.

Example 1:
  Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4]]
  Output: 3
  Explanation: From the picture above, one can see that all of the components of this graph are complete.

Example 2:
  Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]
  Output: 1
  Explanation: The component containing vertices 0, 1, and 2 is complete since there is an edge between every pair of two vertices. On the other hand, the component containing vertices 3, 4, and 5 is not complete since there is no edge between vertices 4 and 5. Thus, the number of complete components in this graph is 1.
 */
public class P002 {
  public class CompleteComponentsCounter {

    public int countCompleteComponents(int n, int[][] edges) {
      // Build adjacency list for efficient traversal
      List<List<Integer>> adj = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        adj.add(new ArrayList<>());
      }

      // Add edges to adjacency list
      for (int[] edge : edges) {
        adj.get(edge[0]).add(edge[1]);
        adj.get(edge[1]).add(edge[0]);
      }

      boolean[] visited = new boolean[n];
      int completeComponents = 0;

      for (int i = 0; i < n; i++) {
        if (!visited[i]) {
          if (isCompleteComponent(i, adj, visited)) {
            completeComponents++;
          }
        }
      }
      return completeComponents;
    }

    private boolean isCompleteComponent(int start, List<List<Integer>> adj, boolean[] visited) {
      Queue<Integer> queue = new LinkedList<>();
      List<Integer> component = new ArrayList<>(); // Track nodes in this component

      queue.offer(start);
      visited[start] = true;

      while (!queue.isEmpty()) {
        int node = queue.poll();
        component.add(node);

        for (int neighbor : adj.get(node)) {
          if (!visited[neighbor]) {
            visited[neighbor] = true;
            queue.offer(neighbor);
          }
        }
      }

      int nodeCount = component.size();
      int expectedEdges = nodeCount * (nodeCount - 1) / 2;

      // Count actual edges in this component
      int actualEdges = 0;
      for (int node : component) {
        for (int neighbor : adj.get(node)) {
          // Only count each edge once by ensuring node < neighbor
          if (node < neighbor && isInComponent(neighbor, component)) {
            actualEdges++;
          }
        }
      }

      return actualEdges == expectedEdges;
    }

    // Helper method to check if a node is in the current component
    private boolean isInComponent(int node, List<Integer> component) {
      return component.contains(node);
    }

    // Alternative more efficient approach using Set for component tracking
    public int countCompleteComponentsOptimized(int n, int[][] edges) {
      List<List<Integer>> adj = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        adj.add(new ArrayList<>());
      }

      for (int[] edge : edges) {
        adj.get(edge[0]).add(edge[1]);
        adj.get(edge[1]).add(edge[0]);
      }

      boolean[] visited = new boolean[n];
      int completeComponents = 0;

      for (int i = 0; i < n; i++)
        if (!visited[i])
          if (isCompleteComponentOptimized(i, adj, visited))
            completeComponents++;

      return completeComponents;
    }

    private boolean isCompleteComponentOptimized(int start, List<List<Integer>> adj, boolean[] visited) {
      Queue<Integer> queue = new LinkedList<>();
      Set<Integer> component = new HashSet<>();

      queue.offer(start);
      visited[start] = true;

      // BFS to find all nodes in component
      while (!queue.isEmpty()) {
        int node = queue.poll();
        component.add(node);

        for (int neighbor : adj.get(node)) {
          if (!visited[neighbor]) {
            visited[neighbor] = true;
            queue.offer(neighbor);
          }
        }
      }

      int nodeCount = component.size();
      int expectedEdges = nodeCount * (nodeCount - 1) / 2; // for a dense network of n vertices

      // Count actual edges
      int actualEdges = 0;
      for (int node : component) {
        for (int neighbor : adj.get(node)) {
          // Only count edge once and ensure both endpoints are in component
          if (node < neighbor && component.contains(neighbor))
            actualEdges++;

        }
      }

      return actualEdges == expectedEdges;
    }

    // Test method
    public static void main(String[] args) {
      CompleteComponentsCounter counter = new P002().new CompleteComponentsCounter();
      // Test case 1: Complete component with 3 nodes (triangle)
      int[][] edges1 = { { 0, 1 }, { 1, 2 }, { 2, 0 } };
      System.out.println("Test 1 - Complete triangle: " +
          counter.countCompleteComponents(3, edges1)); // Expected: 1

      // Test case 2: Two separate complete components
      int[][] edges2 = { { 0, 1 }, { 2, 3 }, { 3, 4 }, { 4, 2 } };
      System.out.println("Test 2 - Two components: " +
          counter.countCompleteComponents(5, edges2)); // Expected: 1 (only triangle 2-3-4 is complete)

      // Test case 3: No edges (each node is complete by itself)
      int[][] edges3 = {};
      System.out.println("Test 3 - Isolated nodes: " +
          counter.countCompleteComponents(4, edges3)); // Expected: 4
    }
  }
}
