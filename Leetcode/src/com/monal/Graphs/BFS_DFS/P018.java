package com.monal.Graphs.BFS_DFS;
/*
There is an infrastructure of n cities with some number of roads connecting these cities. Each roads[i] = [ai, bi] indicates that there is a bidirectional road between cities ai and bi.
The network rank of two different cities is defined as the total number of directly connected roads to either city. If a road is directly connected to both cities, it is only counted once.
The maximal network rank of the infrastructure is the maximum network rank of all pairs of different cities.
Given the integer n and the array roads, return the maximal network rank of the entire infrastructure.

*/
/*
 * This is a classic problem of finding the maximum network rank in a graph.
 * Approach:
 * 1. Build the graph using an adjacency list.
 * 2. For each pair of cities, calculate the network rank.
 * 3. Use a nested loop to iterate through all pairs of cities.
 * 4. For each pair, count the number of roads connected to either city.
 * 5. Keep track of the maximum network rank found during the iteration.
 * 6. Return the maximum network rank after checking all pairs.
 */
public class P018 {
    public int maximalNetworkRank(int n, int[][] roads) {
       boolean[][] graph = new boolean[n][n];
      int[] indegree = new int[n];
      for (int[] road : roads) {
        int u = road[0], v = road[1];
        graph[u][v] = true;
        graph[v][u] = true; // Undirected graph
        indegree[u]++;
        indegree[v]++;
      }

      int maxRank = 0;
      for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
          int rank = indegree[i] + indegree[j];
          if (graph[i][j]) rank--; // If there's a direct road between i and j, count it only once
          maxRank = Math.max(maxRank, rank);
        }
      }

      return maxRank;
    }
}
