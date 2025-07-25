package com.monal.Graphs.MST;
import java.util.*;
/*
You are given a network of n nodes represented as an n x n adjacency matrix graph, where the ith node is directly connected to the jth node if graph[i][j] == 1.

Some nodes initial are initially infected by malware. Whenever two nodes are directly connected, and at least one of those two nodes is infected by malware, both nodes will be infected by malware. This spread of malware will continue until no more nodes can be infected in this manner.

Suppose M(initial) is the final number of nodes infected with malware in the entire network after the spread of malware stops. We will remove exactly one node from initial.

Return the node that, if removed, would minimize M(initial). If multiple nodes could be removed to minimize M(initial), return such a node with the smallest index.

Note that if a node was removed from the initial list of infected nodes, it might still be infected later due to the malware spread.

Example 1:

Input: graph = [[1,1,0],[1,1,0],[0,0,1]], initial = [0,1]
Output: 0
Example 2:

Input: graph = [[1,0,0],[0,1,0],[0,0,1]], initial = [0,2]
Output: 0

*/
public class P010 {
  class Solution {
    public int minMalwareSpread(int[][] adj, int[] initial) {
      int n = adj.length;
      List<List<Integer>> graph = new ArrayList<>();
      // build graph
      for (int i = 0; i < n; i++)
        graph.add(new ArrayList<>());
      for (int i = 0; i < n; i++)
        for (int j = 0; j < adj[0].length; j++)
          if (adj[i][j] == 1)
            graph.get(i).add(j);
      // let's group the malware
      DisjointSet djs = new DisjointSet(n);
      for (int i = 0; i < n; i++)
        for (int j = i + 1; j < n; j++)
          if (adj[i][j] == 1)
            djs.union(i, j);

      // Count size of each component & infected Compoents
      int[] size = new int[n];
      for (int i = 0; i < n; i++)
        size[djs.find(i)]++;

      int[] infectedComponent = new int[n];
      for (int node : initial)
        infectedComponent[djs.find(node)]++;

      Arrays.sort(initial);

      int answer = initial[0], maxSaved = -1;
      for (int node : initial) {
        int root = djs.find(node);
        if (infectedComponent[root] == 1) {
          // Only one infection in the component
          if (size[root] > maxSaved) {
            maxSaved = size[root];
            answer = node;
          }
        }
      }

      return answer;
    }

    class DisjointSet {
      int rank[];
      int parent[];

      DisjointSet(int n) {
        rank = new int[n];
        parent = new int[n];
        for (int i = 0; i < n; i++)
          parent[i] = i;
      }

      int find(int node) {
        if (node == parent[node])
          return node;
        return parent[node] = find(parent[node]);
      }

      boolean union(int x, int y) {
        int rx = find(x), ry = find(y);
        if (rx == ry)
          return false;
        if (rank[rx] > rank[ry])
          parent[ry] = rx;
        else if (rank[rx] < rank[ry])
          parent[rx] = ry;
        else {
          parent[rx] = ry;
          rank[ry]++;
        }
        return true;
      }
    }
  }
}
