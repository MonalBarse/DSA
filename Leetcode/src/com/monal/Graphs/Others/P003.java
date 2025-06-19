package com.monal.Graphs.Others;

import java.util.*;

/*
Given a list of edges of a graph `edges` where edge is from edges[i][0] to edges[j][1], where i and j varies from 0 to n-1,
You need to find all the articulation points in the graph.
Articulation points are nodes in a graph whose removal increases the number of connected components in the graph.

Example 1:
  Input: n = 10, edges =[[0,1] [1,2] [2,0] [2,3] [3,6] [6,4] [3,4] [6,7] [4,7] [7,8] [4,5] [5,8] [9,1]]
  Output: [3, 4, 6, 7]

Example 2:
  Input: n = 3, edges = [[0, 1], [1, 2], [2, 0]]
  Output: []
*/
public class P003 {
  private int time = 0;

  List<Integer> articuationPoints(int n, List<List<Integer>> edges) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      graph.add(new ArrayList<>());
    }

    for (List<Integer> edge : edges) {
      graph.get(edge.get(0)).add(edge.get(1));
      graph.get(edge.get(1)).add(edge.get(0)); // Undirected graph
    }

    boolean[] visited = new boolean[n];
    int[] inTime = new int[n];
    int[] low = new int[n];
    // THE defination of Low has changed from bridges problem -
    /*
     * low[node] in previous prob was minimum inTime of all adj nodes apart from
     * parent node.
     * Here, low[node] is the minimum inTime of all adj nodes apart from parent and
     * visited nodes.
     * This is because we are looking for articulation points, not bridges.
     */
    boolean[] isArticulation = new boolean[n];

    // Start DFS from each unvisited node
    // we need not to do this in case of bridges problem, as we are sure that the
    // graph is connected, but here the graph may not be connected.
    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        dfs(i, -1, visited, inTime, low, graph, isArticulation);
      }
    }

    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (isArticulation[i]) {
        result.add(i);
      }
    }

    return result;
  }

  private void dfs(int node, int parent, boolean[] visited, int[] inTime, int[] low,
      List<List<Integer>> graph, boolean[] isArticulation) {
    visited[node] = true;
    inTime[node] = low[node] = time;
    time++;
    for (int neighbor : graph.get(node)) {
      if (!visited[neighbor]) {
        dfs(neighbor, node, visited, inTime, low, graph, isArticulation);
        low[node] = Math.min(low[node], low[neighbor]);
        if (low[neighbor] >= inTime[node] && parent != -1) {
          isArticulation[node] = true;
        }
      } else if (neighbor != parent) {
        low[node] = Math.min(low[node], inTime[neighbor]);
      }
    }
  }

  public static void main(String[] args) {
    P003 solution = new P003();
    List<List<Integer>> edges = Arrays.asList(
        Arrays.asList(0, 1), Arrays.asList(1, 2), Arrays.asList(2, 0),
        Arrays.asList(2, 3), Arrays.asList(3, 6), Arrays.asList(6, 4),
        Arrays.asList(3, 4), Arrays.asList(6, 7), Arrays.asList(4, 7),
        Arrays.asList(7, 8), Arrays.asList(4, 5), Arrays.asList(5, 8),
        Arrays.asList(9, 1));
    System.out.println(solution.articuationPoints(10, edges)); // Output: [3, 4, 6, 7]
  }

}
