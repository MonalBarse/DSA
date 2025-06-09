package com.monal.Graphs.BFS_DFS;

import java.util.*;

/*
Detectcyclein a Directed Graph using BFS or DFS
*/
public class P010 {

  public static boolean isDirectedGraphCyclic(int[][] graph, boolean usingBFS) {
    // convert the int[][] to adj List
    int n = graph.length;
    List<List<Integer>> adjList = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      adjList.add(new ArrayList<>());
      for (int j = 0; j < graph[i].length; j++) {
        if (graph[i][j] == 1) {
          adjList.get(i).add(j);
        }
      }
    }

    boolean[] visited = new boolean[n];
    if (usingBFS) {
      for (int i = 0; i < n; i++) {
        if (!visited[i]) { // unvisited
          if (bfsDetect(adjList, visited, i)) {
            return true; // cycle detected
          }
        }
      }
    } else {
      boolean[] pathVisited = new boolean[n]; // to track the current path
      for (int i = 0; i < n; i++) {
        if (!visited[i]) { // unvisited
          if (dfsDetect(adjList, visited, pathVisited, i)) {
            return true; // cycle detected
          }
        }
      }

    }

    return false; // no cycle detected

  }

  public static boolean bfsDetect(List<List<Integer>> adjList, boolean[] visited, int start) {
    boolean[] pathVisited = new boolean[adjList.size()];
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(start);

    pathVisited[start] = true;
    visited[start] = true;

    while (!queue.isEmpty()) {
      int node = queue.poll();
      for (int neighbor : adjList.get(node)) {
        if (!visited[neighbor]) {
          visited[neighbor] = true;
          pathVisited[neighbor] = true;
          queue.offer(neighbor);
        } else if (pathVisited[neighbor]) {
          return true; // cycle detected
        }
      }
      pathVisited[node] = false; // backtrack
    }
    return false; // no cycle detected
  }

  public static boolean dfsDetect(List<List<Integer>> adjList, boolean[] visited, boolean[] pathVisited, int node) {
    visited[node] = true;
    pathVisited[node] = true; // mark the node as part of the current path

    for (int neighbor : adjList.get(node)) {
      if (!visited[neighbor]) {
        if (dfsDetect(adjList, visited, pathVisited, neighbor)) {
          return true; // cycle detected in the recursive call
        }
      } else if (pathVisited[neighbor]) {
        return true; // cycle detected in the current path
      }
    }
    pathVisited[node] = false; // backtrack, unmark the node from the current path
    return false; // no cycle detected
  }

}