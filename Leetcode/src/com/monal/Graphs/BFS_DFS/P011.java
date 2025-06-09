package com.monal.Graphs.BFS_DFS;

import java.util.*;

/*
Eventual Safe States in a Directed Graph
Leetcode: https://leetcode.com/problems/find-eventual-safe-states/
*/
public class P011 {

  class Solution1 {
    public List<Integer> eventualSafeNodes(int[][] graph) {
      boolean visited[] = new boolean[graph.length];
      boolean inPath[] = new boolean[graph.length];
      boolean[] isNodeSafe = new boolean[graph.length];
      for (int V = 0; V < graph.length; V++) {
        if (!visited[V]) {
          dfs(graph, visited, inPath, isNodeSafe, V);
        }
      }

      List<Integer> res = new ArrayList<>();
      for (int i = 0; i < graph.length; i++) {
        if (isNodeSafe[i]) {
          res.add(i);
        }
      }

      return res;

    }

    private boolean dfs(int[][] graph, boolean[] visited, boolean[] inPath, boolean[] isSafe, int V) {
      visited[V] = true;
      inPath[V] = true;

      for (int neighbor : graph[V]) {
        if (!visited[neighbor]) {
          if (dfs(graph, visited, inPath, isSafe, neighbor)) {
            return true; // cycle detected
          }
        } else if (inPath[neighbor]) {
          return true; // cycle detected
        }
      }

      inPath[V] = false; // backtrack
      isSafe[V] = true; // mark as safe
      return false; // no cycle detected
    }

  }

  public static void main(String[] args) {
    P011 p = new P011();
    Solution1 sol = p.new Solution1();

    int[][] graph = {
        { 1, 2 },
        { 2, 3 },
        { 5 },
        { 0 },
        { 5 },
        {},
        {}
    };

    List<Integer> result = sol.eventualSafeNodes(graph);
    System.out.println(result); // Output: [2, 4, 5, 6]
  }
}
