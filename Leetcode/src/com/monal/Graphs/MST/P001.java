package com.monal.Graphs.MST;

/*
There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c.
A province is a group of directly or indirectly connected cities and no other cities outside of the group.
You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.
Return the total number of provinces.

Example 1:
  Input: isConnected = [[1,1,0],[1,1,0],[0,0,1]]
  Output: 2
Example 2:
  Input: isConnected = [[1,0,0],[0,1,0],[0,0,1]]
  Output: 3
*/
public class P001 {
  class Solution {
    public int findCircleNum(int[][] isConnected) {
      int n = isConnected.length;
      DisjointSet ds = new DisjointSet(n);

      for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
          if (isConnected[i][j] == 1) {
            ds.unionBySize(i, j);
          }
        }
      }

      int provinces = 0;
      for (int i = 0; i < n; i++) {
        if (ds.findParent(i) == i) {
          provinces++;
        }
      }
      return provinces;
    }
  }

  class DisjointSet {
    private int[] parent, size, rank;

    public DisjointSet(int n) {
      parent = new int[n];
      size = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        size[i] = 1;
        rank[i] = 0;
      }
    }

    // Find with path compression
    public int findParent(int x) {
      if (parent[x] != x) {
        parent[x] = findParent(parent[x]);
      }
      return parent[x];
    }

    // Union by size - returns true if union happened
    public boolean unionBySize(int x, int y) {
      int rootX = findParent(x);
      int rootY = findParent(y);

      if (rootX == rootY)
        return false;

      if (size[rootX] < size[rootY]) {
        parent[rootX] = rootY;
        size[rootY] += size[rootX];
      } else {
        parent[rootY] = rootX;
        size[rootX] += size[rootY];
      }
      return true;
    }

    // Union by rank - returns true if union happened
    public boolean unionByRank(int x, int y) {
      int rootX = findParent(x);
      int rootY = findParent(y);

      if (rootX == rootY)
        return false;

      if (rank[rootX] < rank[rootY]) {
        parent[rootX] = rootY;
      } else if (rank[rootX] > rank[rootY]) {
        parent[rootY] = rootX;
      } else {
        parent[rootY] = rootX;
        rank[rootX]++;
      }
      return true;
    }

    // Check if x and y are connected
    public boolean connected(int x, int y) {
      return findParent(x) == findParent(y);
    }
  }

  // Usage Example:
  // DisjointSet ds = new DisjointSet(n);
  // ds.unionBySize(a, b); // Connect a and b using size optimization
  // ds.unionByRank(a, b); // Connect a and b using rank optimization
  // ds.connected(a, b); // Check if a and b are connected
  // ds.find(x);
}
