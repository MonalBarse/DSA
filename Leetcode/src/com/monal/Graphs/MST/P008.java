package com.monal.Graphs.MST;

/*
Alice and Bob have an undirected graph of n nodes and three types of edges:

Type 1: Can be traversed by Alice only.
Type 2: Can be traversed by Bob only.
Type 3: Can be traversed by both Alice and Bob.
Given an array edges where edges[i] = [typei, ui, vi] represents a bidirectional edge of type typei between nodes ui and vi, find the maximum number of edges you can remove so that after removing the edges, the graph can still be fully traversed by both Alice and Bob. The graph is fully traversed by Alice and Bob if starting from any node, they can reach all other nodes.

Return the maximum number of edges you can remove, or return -1 if Alice and Bob cannot fully traverse the graph.

Synonymous Sentences

Number of Islands II

The Earliest Moment When Everyone Become Friends


 */
public class P008 {
  class Solution {
    public int maxNumEdgesToRemove(int n, int[][] edges) {
      DisjointSet alice = new DisjointSet(n);
      DisjointSet bob = new DisjointSet(n);
      int edgesUsed = 0;

      // Process type 3 edges first (both Alice and Bob can use)
      for (int[] edge : edges) {
        if (edge[0] == 3) {
          boolean aliceConnected = alice.union(edge[1] - 1, edge[2] - 1);
          boolean bobConnected = bob.union(edge[1] - 1, edge[2] - 1);
          if (aliceConnected || bobConnected)
            edgesUsed++;
        }
      }

      // Process type 1 edges (Alice only)
      for (int[] edge : edges)
        if (edge[0] == 1)
          if (alice.union(edge[1] - 1, edge[2] - 1))
            edgesUsed++;
      // Process type 2 edges (Bob only)
      for (int[] edge : edges)
        if (edge[0] == 2)
          if (bob.union(edge[1] - 1, edge[2] - 1))
            edgesUsed++;

      // Check if both graphs are fully connected
      if (alice.getComponentCount() == 1 && bob.getComponentCount() == 1)
        return edges.length - edgesUsed;
      return -1;
    }

    class DisjointSet {
      private int[] parent;
      private int[] rank;
      private int componentCount;

      public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        componentCount = n;
        for (int i = 0; i < n; i++) {
          parent[i] = i;
        }
      }

      public int find(int node) {
        if (parent[node] != node) {
          parent[node] = find(parent[node]); // Path compression
        }
        return parent[node];
      }

      public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY)
          return false; // Already connected

        // Union by rank
        if (rank[rootX] < rank[rootY])
          parent[rootX] = rootY;
        else if (rank[rootX] > rank[rootY])
          parent[rootY] = rootX;
        else {
          parent[rootY] = rootX;
          rank[rootX]++;
        }

        componentCount--;
        return true;
      }

      public int getComponentCount() {
        return componentCount;
      }
    }
  }

}