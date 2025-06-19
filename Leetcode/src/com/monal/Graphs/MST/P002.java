package com.monal.Graphs.MST;

import com.monal.Graphs.MST.P001.DisjointSet;
import java.util.ArrayList;

/*
There are n computers numbered from 0 to n - 1 connected by ethernet cables connections forming a network where connections[i] = [ai, bi] represents a connection between computers ai and bi. Any computer can reach any other computer directly or indirectly through the network.
You are given an initial computer network connections. You can extract certain cables between two directly connected computers, and place them between any pair of disconnected computers to make them directly connected.
Return the minimum number of times you need to do this in order to make all the computers connected. If it is not possible, return -1.

Example 1:
  Input: n = 4, connections = [[0,1],[0,2],[1,2]]
  Output: 1
  Explanation: Remove cable between computer 1 and 2 and place between computers 1 and 3.
Example 2:
  Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2],[1,3]]
  Output: 2
Example 3:
  Input: n = 6, connections = [[0,1],[0,2],[0,3],[1,2]]
  Output: -1
  Explanation: There are not enough cables.

*/
public class P002 {
  class Solution {

    private int makeConnected(int n, int[][] connections) {
      // If there are not enough connections to connect all computers
      if (connections.length < n - 1) {
        return -1;
      }

      DisjointSet ds = new P001().new DisjointSet(n);
      int countExtraEdges = 0;

      // Union the connected computers
      for (int[] connection : connections) {
        int u = connection[0];
        int v = connection[1];
        if (ds.findParent(u) != ds.findParent(v)) {
          ds.unionBySize(u, v);
        } else {
          countExtraEdges++; // Count extra edges that can be used to connect components
        }
      }

      // count the number of components
      int connectedComponents = 0;
      for (int i = 0; i < n; i++) {
        if (ds.findParent(i) == i) {
          connectedComponents++;
        }
      }

      // To connect all components, we need at least (components - 1) edges
      int ans = connectedComponents - 1;
      // If we have enough extra edges, we can connect all components
      if (countExtraEdges >= ans) {
        return ans;
      } else {
        return -1; // Not enough extra edges to connect all components
      }

    }

    public int numberOfProvinces(int n, int[][] connections) {
      boolean visited[] = new boolean[n];
      // make an adj list
      ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }
      // fill the adj list
      for (int[] connection : connections) {
        int u = connection[0];
        int v = connection[1];
        graph.get(u).add(v);
        graph.get(v).add(u);
      }

      // to track connected province
      int TotalProviences = 0;
      // check for every city
      for (int i = 0; i < n; i++) {
        if (!visited[i]) {
          dfs(graph, i, visited);
          TotalProviences++;
        }
      }
      return TotalProviences;
    }

    public void dfs(ArrayList<ArrayList<Integer>> graph, int i, boolean[] visited) {
      // mark the curent node visited
      visited[i] = true;
      // visit all the neighbours
      for (int neighbour : graph.get(i)) {
        if (!visited[neighbour]) {
          dfs(graph, neighbour, visited);
        }
      }
    }

  }
}
