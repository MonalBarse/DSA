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
      boolean[] isBeingVisited = new boolean[n]; // to track the current path
      for (int i = 0; i < n; i++) {
        if (!visited[i]) { // unvisited
          if (dfsDetect(adjList, visited, isBeingVisited, i)) {
            return true; // cycle detected
          }
        }
      }

    }

    return false; // no cycle detected

  }

  public static boolean bfsDetect(List<List<Integer>> adjList, boolean[] visited, int start) {
    boolean[] isBeingVisited = new boolean[adjList.size()];
    Queue<Integer> queue = new ArrayDeque<>();
    queue.offer(start);

    isBeingVisited[start] = true;
    visited[start] = true;

    while (!queue.isEmpty()) {
      int node = queue.poll();
      for (int neighbor : adjList.get(node)) {
        if (!visited[neighbor]) {
          visited[neighbor] = true;
          isBeingVisited[neighbor] = true;
          queue.offer(neighbor);
        } else if (isBeingVisited[neighbor]) {
          return true; // cycle detected
        }
      }
      isBeingVisited[node] = false; // backtrack
    }
    return false; // no cycle detected
  }

  public static boolean dfsDetect(List<List<Integer>> adjList, boolean[] visited, boolean[] isBeingVisited, int node) {
    visited[node] = true;
    isBeingVisited[node] = true; // mark the node as part of the current path

    for (int neighbor : adjList.get(node)) {
      if (!visited[neighbor]) {
        if (dfsDetect(adjList, visited, isBeingVisited, neighbor)) {
          return true; // cycle detected in the recursive call
        }
      } else if (isBeingVisited[neighbor]) {
        return true; // cycle detected in the current path
      }
    }
    isBeingVisited[node] = false; // backtrack, unmark the node from the current path
    return false; // no cycle detected
  }

}