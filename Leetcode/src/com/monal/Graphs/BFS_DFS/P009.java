package com.monal.Graphs.BFS_DFS;

import java.util.*;
/*
LEETCODE BIPARTITE GRAPH PROBLEM
https://leetcode.com/problems/is-graph-bipartite/
*/

/*
  A bipartite graph is a graph whose nodes can be divided into two independent sets A and B such that every edge connects a node in set A to a node in set B.
  Given an undirected graph represented by an array of edges, return true if and only if it is bipartite.

  Example 1:
    Input: edges = [[1,2,3],[0,2],[0,1,3],[0,2]]
    Output: true
    Explanation: The graph can be colored with two colors.
  Example 2:
    Input: edges = [[1,3],[0,2],[1,3],[0,2]]
    Output: false
    Explanation: The graph cannot be colored with two colors.
*/
public class P009 {

  class Solution {
    public boolean isBipartite(int[][] connections, boolean useBFS) {
      // convert the int[][] to adj List
      int n = connections.length;
      List<List<Integer>> graph = new ArrayList<>();

      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
        for (int j = 0; j < connections[i].length; j++) {
          if (connections[i][j] == 1) {
            graph.get(i).add(j);
          }
        }
      }

      // Color array - keep two segments ( 0, 1 )
      int color[] = new int[n];
      Arrays.fill(color, -1); // uncolored

      if (useBFS) {

        for (int i = 0; i < n; i++) {
          if (color[i] == -1) { // uncolored
            if (!bfs(graph, color, i)) {
              return false; // not bipartite
            }
          }
        }
      } else {

        for (int i = 0; i < n; i++) {
          if (color[i] == -1) { // uncolored
            if (!dfs(graph, color, i, 0)) {
              return false; // not bipartite
            }
          }
        }
      }

      // DFS for each component

      return true; // bipartite
    }

    private boolean bfs(List<List<Integer>> graph, int[] color, int start) {
      Queue<Integer> q = new ArrayDeque<>();
      q.offer(start);
      color[start] = 1; // start coloring with 1

      while (!q.isEmpty()) {
        int node = q.poll();
        for (int neighbor : graph.get(node)) {
          if (color[neighbor] == -1) { // uncolored
            color[neighbor] = 1 - color[node]; // alternate color
            q.offer(neighbor);
          } else if (color[neighbor] == color[node]) { // same color as current node
            return false; // not bipartite
          }
        }
      }

      return true;
    }

    @SuppressWarnings("unused")
    private boolean dfs(List<List<Integer>> graph, int[] color, int node, int colorValue) {
      if (color[node] != -1) {
        return color[node] == colorValue; // already colored, check if it matches
      }

      color[node] = colorValue; // color the node

      for (int neighbor : graph.get(node)) {
        if (!dfs(graph, color, neighbor, 1 - colorValue)) { // alternate color for neighbor
          return false; // not bipartite
        }
      }

      return true; // all neighbors colored correctly
    }
  }

  public static void main(String[] args) {
    P009 p009 = new P009();
    Solution solution = p009.new Solution();

    // 5x5 Bipartite Graph
    int[][] adjMatrix_1 = {
        { 0, 0, 0, 1, 1 }, // vertex 0 connects to 3,4
        { 0, 0, 0, 1, 0 }, // vertex 1 connects to 3
        { 0, 0, 0, 0, 1 }, // vertex 2 connects to 4
        { 1, 1, 0, 0, 0 }, // vertex 3 connects to 0,1
        { 1, 0, 1, 0, 0 } // vertex 4 connects to 0,2
    };

    // 6x6 Regular Graph (not bipartite)
    int[][] adjMatrix_2 = {
        { 0, 1, 1, 1, 0, 0 }, // vertex 0 connects to 1,2,3 (forms triangle 0-1-2)
        { 1, 0, 1, 0, 1, 0 }, // vertex 1 connects to 0,2,4
        { 1, 1, 0, 0, 0, 1 }, // vertex 2 connects to 0,1,5
        { 1, 0, 0, 0, 1, 1 }, // vertex 3 connects to 0,4,5 (forms triangle 3-4-5)
        { 0, 1, 0, 1, 0, 1 }, // vertex 4 connects to 1,3,5
        { 0, 0, 1, 1, 1, 0 } // vertex 5 connects to 2,3,4
    };

    // 8x8 Bipartite Graph
    int[][] adjMatrix_3 = {
        { 0, 0, 0, 0, 1, 1, 0, 1 }, // vertex 0 connects to 4,5,7
        { 0, 0, 0, 0, 0, 1, 1, 0 }, // vertex 1 connects to 5,6
        { 0, 0, 0, 0, 1, 0, 1, 1 }, // vertex 2 connects to 4,6,7
        { 0, 0, 0, 0, 0, 1, 0, 1 }, // vertex 3 connects to 5,7
        { 1, 0, 1, 0, 0, 0, 0, 0 }, // vertex 4 connects to 0,2
        { 1, 1, 0, 1, 0, 0, 0, 0 }, // vertex 5 connects to 0,1,3
        { 0, 1, 1, 0, 0, 0, 0, 0 }, // vertex 6 connects to 1,2
        { 1, 0, 1, 1, 0, 0, 0, 0 } // vertex 7 connects to 0,2,3
    };

    // 8x8 Regular Graph (not bipartite)
    int[][] adjMatrix_4 = {
        { 0, 1, 1, 0, 1, 0, 0, 1 }, // vertex 0 connects to 1,2,4,7
        { 1, 0, 1, 1, 0, 1, 0, 0 }, // vertex 1 connects to 0,2,3,5
        { 1, 1, 0, 1, 0, 0, 1, 0 }, // vertex 2 connects to 0,1,3,6
        { 0, 1, 1, 0, 0, 0, 1, 1 }, // vertex 3 connects to 1,2,6,7
        { 1, 0, 0, 0, 0, 1, 1, 1 }, // vertex 4 connects to 0,5,6,7
        { 0, 1, 0, 0, 1, 0, 1, 1 }, // vertex 5 connects to 1,4,6,7
        { 0, 0, 1, 1, 1, 1, 0, 1 }, // vertex 6 connects to 2,3,4,5,7
        { 1, 0, 0, 1, 1, 1, 1, 0 } // vertex 7 connects to 0,3,4,5,6
    };

    System.out.println("Adjacency Matrix 1 is Bipartite (DFS): " + solution.isBipartite(adjMatrix_1, false));
    System.out.println("Adjacency Matrix 2 is Bipartite (DFS): " + solution.isBipartite(adjMatrix_2, false));
    System.out.println("Adjacency Matrix 3 is Bipartite (DFS): " + solution.isBipartite(adjMatrix_3, false));
    System.out.println("Adjacency Matrix 4 is Bipartite (DFS): " + solution.isBipartite(adjMatrix_4, false));
    System.out.println();
    System.out.println("Adjacency Matrix 1 is Bipartite (BFS): " + solution.isBipartite(adjMatrix_1, true));
    System.out.println("Adjacency Matrix 2 is Bipartite (BFS): " + solution.isBipartite(adjMatrix_2, true));
    System.out.println("Adjacency Matrix 3 is Bipartite (BFS): " + solution.isBipartite(adjMatrix_3, true));
    System.out.println("Adjacency Matrix 4 is Bipartite (BFS): " + solution.isBipartite(adjMatrix_4, true));

  }

}
