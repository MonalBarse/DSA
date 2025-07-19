package com.monal.Graphs.BFS_DFS.Others;

import java.util.*;

/*
List all the SCC's and their count in a provided `graph`. A graph is a list of edges
represented as a list of pairs, where each pair (u, v) indicates a directed edge from node u to node v.
The output should be a list of lists, where each inner list contains the nodes in one strongly
connected component (SCC), and the outer list contains all SCCs. The count of SCCs should also be returned at the start
Example 1:
Input: graph = [[0,1], [1,2], [2,0],[1,3],[3,4],[4,5],[5,6],[6,4],[4,7]]
Output: [4, [0, 1, 2], [3], [4, 5, 6], [7]]  // First element is count, then SCCs
*/
public class P001 {

  public List<Object> findSCCsWithCount(int[][] graph) {
    List<List<Integer>> sccs = findSCCs(graph);
    List<Object> result = new ArrayList<>();
    result.add(sccs.size()); // Add count first
    result.addAll(sccs); // Add all SCCs
    return result;
  }

  public List<List<Integer>> findSCCs(int[][] graph) {
    int maxNode = 0;
    for (int[] edge : graph) {
      maxNode = Math.max(maxNode, Math.max(edge[0], edge[1]));
    }
    int n = maxNode + 1; // Number of nodes is maxNode + 1 (assuming 0-indexed)

    boolean[] visited = new boolean[n];
    List<List<Integer>> orgGraph = new ArrayList<>();
    List<List<Integer>> transGraph = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      orgGraph.add(new ArrayList<>());
      transGraph.add(new ArrayList<>());
    }

    for (int[] edge : graph) {
      int u = edge[0];
      int v = edge[1];
      orgGraph.get(u).add(v);
      transGraph.get(v).add(u);
    }

    // Let's do a DFS to find the finishing times
    Stack<Integer> order = new Stack<>();
    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        // Do dfs on the original graph
        dfsOrg(orgGraph, visited, order, i);
      }
    }

    // Now that we have the finishing order in the stack
    List<List<Integer>> sccs = new ArrayList<>(); // List to hold the strongly connected components
    Arrays.fill(visited, false); // Reset visited for the transposed graph

    while (!order.isEmpty()) {
      int node = order.pop();
      if (!visited[node]) {
        List<Integer> scc = new ArrayList<>();
        // Do dfs on the transposed graph
        dfsTransGraph(transGraph, visited, scc, node);
        sccs.add(scc); // Add the found SCC to the list
      }
    }
    return sccs; // Return the list of SCCs
  }

  public void dfsTransGraph(List<List<Integer>> transGraph, boolean[] visited, List<Integer> scc, int node) {
    visited[node] = true;
    scc.add(node); // Add the node to the current SCC
    for (int neighbor : transGraph.get(node)) {
      if (!visited[neighbor]) {
        dfsTransGraph(transGraph, visited, scc, neighbor);
      }
    }
  }

  public void dfsOrg(List<List<Integer>> graph, boolean[] visited, Stack<Integer> stack, int node) {
    visited[node] = true;
    for (int neighbor : graph.get(node)) {
      if (!visited[neighbor]) {
        dfsOrg(graph, visited, stack, neighbor);
      }
    }
    stack.push(node); // Push the node onto the stack after visiting all its neighbors
  }

  // Test method
  public static void main(String[] args) {
    P001 solution = new P001();
    int[][] graph = { { 0, 1 }, { 1, 2 }, { 2, 0 }, { 1, 3 }, { 3, 4 }, { 4, 5 }, { 5, 6 }, { 6, 4 }, { 4, 7 } };

    System.out.println("SCCs only: " + solution.findSCCs(graph));
    System.out.println("Count + SCCs: " + solution.findSCCsWithCount(graph));
  }
}