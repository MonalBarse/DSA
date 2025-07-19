package com.monal.Graphs.BFS_DFS;

import java.util.*;
/*
You are given an undirected graph (the "original graph") with n nodes labeled
from 0 to n - 1. You decide to subdivide each edge in the graph into a chain of
nodes, with the number of new nodes varying between each edge.

The graph is given as a 2D array of edges where edges[i] = [ui, vi, cnti] indicates
that there is an edge between nodes ui and vi in the original graph, and cnti is
the total number of new nodes that you will subdivide the edge into.
Note that cnti == 0 means you will not subdivide the edge.

To subdivide the edge [ui, vi], replace it with (cnti + 1) new edges and cnti new nodes.
The new nodes are x1, x2, ..., xcnti, and the new edges are
[ui, x1], [x1, x2], [x2, x3], ..., [xcnti-1, xcnti], [xcnti, vi].

In this new graph, you want to know how many nodes are reachable from the node 0,
where a node is reachable if the distance is maxMoves or less.

Given the original graph and maxMoves, return the number of nodes
that are reachable from node 0 in the new graph.
*/

/*
 * Approach:
 * The problem seems to be a graph traversal since we need to find the number of nodes reachable from node 0
 * so i am thinking BFS/DFS. But the main challenge is that we need to consider the subdivisions of edges.
 * We start with an undirected graph, what i am thinking now is to create a new graph and traverse it.
 * To create a new graph, we can use a map to store edges and their subdivision. Map<Integer, List<Pair>> graph
 * Map of (Node, Node's subdivisions and their number of subdivisions)
 *
 * Use Dijkstra's algorithm to compute shortest distances from node 0.
 * For each original node, if distance[node] <= maxMoves, it’s reachable.;
 * For each edge (u, v, cnt):
   - Check how many subdivided nodes on this edge can be reached from both u and v.
   - If maxMoves - dist[u] is positive, we can go min(cnt, maxMoves - dist[u]) steps into the subdivisions from u.
   - Same from v.
 * Total subdivisions counted on this edge = min(cnt, moves_from_u + moves_from_v)
 */

/*
  Example walkthrough:
  edges = [[0,1,10],[0,2,1],[1,2,2]], maxMoves = 6, n = 3

  Original graph: 0-1 (with 10 subdivisions), 0-2 (with 1 subdivision), 1-2 (with 2 subdivisions)

  After Dijkstra from node 0:
  - dist[0] = 0
  - dist[1] = 3 (0 -> 2 -> 1, cost = 2 + 3 = 5, but direct is 11, so via 2 is better)
  - dist[2] = 2 (0 -> 2, cost = 2)

  Count original nodes: dist[0]=0≤6, dist[1]=3≤6, dist[2]=2≤6 → 3 nodes

  Count subdivisions:
  Edge [0,1,10]: movesFromU=6-0=6, movesFromV=6-3=3, min(10, 6+3)=9
  Edge [0,2,1]: movesFromU=6-0=6, movesFromV=6-2=4, min(1, 6+4)=1
  Edge [1,2,2]: movesFromU=6-3=3, movesFromV=6-2=4, min(2, 3+4)=2

  Total: 3 + 9 + 1 + 2 = 15
*/
public class P013 {
  class Pair {
    int node;
    int weight;

    public Pair(int node, int weight) {
      this.node = node;
      this.weight = weight;
    }
  }

  public int reachableNodes(int[][] edges, int maxMoves, int n) {
    int start = 0;

    // Build Graph
    Map<Integer, List<Pair>> graph = new HashMap<>();
    for (int[] edge : edges) {
      int u = edge[0], v = edge[1], cnt = edge[2];
      graph.computeIfAbsent(u, k -> new ArrayList<>()).add(new Pair(v, cnt + 1));
      graph.computeIfAbsent(v, k -> new ArrayList<>()).add(new Pair(u, cnt + 1));
    }

    // Find shortes dist from `0`
    int[] dist = findShortestDistances(graph, n, maxMoves, start);

    int result = 0; // if maxMoves = 0

    // Nodes reachable
    for (int i = 0; i < n; i++)
      if (dist[i] <= maxMoves)
        result++;

    for (int[] edge : edges) {
      int u = edge[0], v = edge[1], cnt = edge[2];
      // How many subdivisions can we reach from u & v?
      int movesFromU = 0, movesFromV = 0;
      if (dist[u] <= maxMoves) movesFromU = Math.max(0, maxMoves - dist[u]);
      if (dist[v] <= maxMoves) movesFromV = Math.max(0, maxMoves - dist[v]);

      // Total subdivisions we can reach on this edge
      // We can't count more than the total subdivisions available
      int reachableSubdivisions = Math.min(cnt, movesFromU + movesFromV);
      result += reachableSubdivisions;
    }
    return result;
  }

  private int[] findShortestDistances(Map<Integer, List<Pair>> graph, int n, int maxMoves, int start) {
    // Dijkstra's algorithm
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[start] = 0;

    PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingInt(p -> p.weight));
    pq.offer(new Pair(start, 0));
    while (!pq.isEmpty()) {
      Pair current = pq.poll();
      int node = current.node;
      int weight = current.weight;

      if (weight > dist[node])
        continue; // Skip if we already found a better path

      for (Pair neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        int newDist = weight + neighbor.weight;
        if (newDist < dist[neighbor.node]) {
          dist[neighbor.node] = newDist;
          pq.offer(new Pair(neighbor.node, newDist));
        }
      }
    }
    return dist;
  }

}
