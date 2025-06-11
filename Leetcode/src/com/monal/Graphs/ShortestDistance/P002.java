package com.monal.Graphs.ShortestDistance;

import java.util.*;

/*
Print Shortest Path from source to destination in a graph using Dijkstra's algorithm.
Leetcode: https://leetcode.com/problems/shortest-path-in-a-weighted-undirected-graph/
*/
public class P002 {
  public class Solution {
    public List<List<Integer>> allShortestPaths(int n, int[][] edges, int src, int dest) {
      // Build adjacency list
      List<List<int[]>> adj = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        adj.add(new ArrayList<>());
      }
      for (int[] edge : edges) {
        int u = edge[0], v = edge[1], weight = edge[2];
        adj.get(u).add(new int[] { v, weight });
        adj.get(v).add(new int[] { u, weight }); // undirected
      }

      int[] dist = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[src] = 0;

      // KEY: Store ALL parents that lead to shortest distance
      List<List<Integer>> parents = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        parents.add(new ArrayList<>());
      }

      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
      pq.offer(new int[] { src, 0 });

      while (!pq.isEmpty()) {
        int[] curr = pq.poll();
        int currNode = curr[0], currDist = curr[1];

        // ⛔ Early Exit - If we're processing nodes beyond dest's distance
        if (dist[dest] != Integer.MAX_VALUE && currDist > dist[dest])
          break;

        if (currDist > dist[currNode])
          continue;

        for (int[] edge : adj.get(currNode)) {
          int nextNode = edge[0];
          int newDist = currDist + edge[1];

          // If we found strictly shorter distance
          if (newDist < dist[nextNode]) {
            dist[nextNode] = newDist;
            // We need to clear the previous parents and add the current node
            parents.get(nextNode).clear();
            parents.get(nextNode).add(currNode);
            pq.offer(new int[] { nextNode, newDist });
          }
          // If we found another path with the same distance
          else if (newDist == dist[nextNode]) {
            parents.get(nextNode).add(currNode);
          }
        }
      }

      // Check if destination is reachable
      if (dist[dest] == Integer.MAX_VALUE) {
        return new ArrayList<>();
      }

      // Phase 2: DFS to collect all shortest paths
      List<List<Integer>> result = new ArrayList<>();
      List<Integer> currentPath = new ArrayList<>();
      dfsAllPaths(dest, src, parents, currentPath, result);

      return result;
    }

    private void dfsAllPaths(int current, int src, List<List<Integer>> parents,
        List<Integer> currentPath, List<List<Integer>> result) {

      currentPath.add(current);
      if (current == src) {
        // Found a complete path, add it (reversed)
        List<Integer> path = new ArrayList<>(currentPath);
        Collections.reverse(path);
        result.add(path);
      } else {
        // Explore all parents
        for (int parent : parents.get(current)) {
          dfsAllPaths(parent, src, parents, currentPath, result);
        }
      }
      currentPath.remove(currentPath.size() - 1);
    }
  }
}
