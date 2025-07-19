package com.monal.Graphs.BFS_DFS.Others;

import java.util.*;

/*
Given a List of Edges of a graph `edges` where edge is from edges[i][0] to edges[j][1], where i and j varies from 0 to n-1,
Return a List of List of all the edges that acts as a `bridge` in the graph.
'A bridge is an edge in a graph whose removal increases the number of connected components in the graph.'

Example 1:
Input: n = 10, edges =[[0,1] [1,2] [2,0] [2,3] [3,6] [6,4] [3,4] [6,7] [4,7] [7,8] [4,5] [5,8] [9,1]]
Output: [[2,3], [1,9]]

*/
public class P002 {
  class Solution {
    private int time = 0;

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> edges) {
      List<List<Integer>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }

      for (List<Integer> edge : edges) {
        graph.get(edge.get(0)).add(edge.get(1));
        graph.get(edge.get(1)).add(edge.get(0)); // Undirected graph
      }

      boolean visited[] = new boolean[n];
      int inTime[] = new int[n];
      int low[] = new int[n];

      List<List<Integer>> bridges = new ArrayList<>();

      dfs(0, -1, visited, inTime, low, graph, bridges);
      return bridges;

    }

    private void dfs(int node, int parent, boolean[] visited, int inTime[], int low[], List<List<Integer>> graph, List<List<Integer>> bridges) {
      visited[node] = true;
      inTime[node] = low[node] = time;
      time++;
      for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
          dfs(neighbor, node, visited, inTime, low, graph, bridges);
          low[node] = Math.min(low[node], low[neighbor]);
          if (low[neighbor] > inTime[node]) {
            bridges.add(Arrays.asList(node, neighbor));
          }
        } else if (neighbor != parent) {
          low[node] = Math.min(low[node], inTime[neighbor]);
        }
      }
    }
  }

  public static void main(String[] args) {
    Solution solution = new P002().new Solution();

    List<List<Integer>> edges = Arrays.asList(
        Arrays.asList(0, 1), Arrays.asList(1, 2), Arrays.asList(2, 0), Arrays.asList(2, 3),
        Arrays.asList(3, 6), Arrays.asList(6, 4), Arrays.asList(3, 4), Arrays.asList(6, 7),
        Arrays.asList(4, 7), Arrays.asList(7, 8), Arrays.asList(4, 5), Arrays.asList(5, 8),
        Arrays.asList(9, 1));
    int n = 10; // Number of nodes
    List<List<Integer>> bridges = solution.criticalConnections(n, edges);
    System.out.println("Bridges in the graph: " + bridges);

    List<List<Integer>> edges2 = Arrays.asList(
        Arrays.asList(0, 1), Arrays.asList(1, 2), Arrays.asList(2, 3), Arrays.asList(3, 4),
        Arrays.asList(4, 5), Arrays.asList(5, 6), Arrays.asList(6, 7), Arrays.asList(7, 8),
        Arrays.asList(8, 9), Arrays.asList(9, 0), Arrays.asList(2, 9), Arrays.asList(3, 8), Arrays.asList(4, 7),
        Arrays.asList(4, 12), Arrays.asList(11, 12), Arrays.asList(10, 11), Arrays.asList(10, 2), Arrays.asList(13, 3),
        Arrays.asList(13, 14), Arrays.asList(12, 15), Arrays.asList(15, 16),
        Arrays.asList(16, 17),
        Arrays.asList(17, 15));
    int n2 = 18; // Number of nodes
    List<List<Integer>> bridges2 = solution.criticalConnections(n2, edges2);
    System.out.println("Bridges in the second graph: " + bridges2);
  }
}
