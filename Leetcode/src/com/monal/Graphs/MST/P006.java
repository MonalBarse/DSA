package com.monal.Graphs.MST;

/*
On a 2D plane, we place n stones at some integer coordinate points. Each coordinate point may have at most one stone.
A stone can be removed if it shares either the same row or the same column as another stone that has not been removed.
Given an array stones of length n where stones[i] = [xi, yi] represents the location of the ith stone, return the largest possible number of stones that can be removed.

Example 1:

  Input: stones = [[0,0],[0,1],[1,0],[1,2],[2,1],[2,2]]
  Output: 5
  Explanation: One way to remove 5 stones is as follows:
  1. Remove stone [2,2] because it shares the same row as [2,1].
  2. Remove stone [2,1] because it shares the same column as [0,1].
  3. Remove stone [1,2] because it shares the same row as [1,0].
  4. Remove stone [1,0] because it shares the same column as [0,0].
  5. Remove stone [0,1] because it shares the same row as [0,0].
  Stone [0,0] cannot be removed since it does not share a row/column with another stone still on the plane.
Example 2:
  Input: stones = [[0,0],[0,2],[1,1],[2,0],[2,2]]
  Output: 3
  Explanation: One way to make 3 moves is as follows:
  1. Remove stone [2,2] because it shares the same row as [2,0].
  2. Remove stone [2,0] because it shares the same column as [0,0].
  3. Remove stone [0,2] because it shares the same row as [0,0].
  Stones [0,0] and [1,1] cannot be removed since they do not share a row/column with another stone still on the plane.
Example 3:
Input: stones = [[0,0]]
Output: 0
Explanation: [0,0] is the only stone on the plane, so you cannot remove it.
*/
public class P006 {
  class DisjointSet {
    final int[] parent;
    final int[] size;

    DisjointSet(int n) {
      parent = new int[n];
      size = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        size[i] = 1;
      }
    }

    int findParent(int node) {
      if (parent[node] == node) {
        return node;
      }
      return parent[node] = findParent(parent[node]);
    }

    boolean union(int n1, int n2) {
      int rootX = findParent(n1), rootY = findParent(n2);
      if (rootX == rootY) {
        return false; // Already in the same set
      }

      if (size[rootX] < size[rootY]) {
        parent[rootX] = rootY;
        size[rootY] += size[rootX];

      } else {
        parent[rootY] = rootX;
        size[rootX] += size[rootY];
      }
      return true; // Successfully merged two different components
    }
  }

  class Solution {
    public int removeStones(int[][] stones) {
      int n = stones.length;
      DisjointSet ds = new DisjointSet(n);
      // we have to detect the connected components in the stones matrix
      // here we define connected as two stones being in the same row and column
      // no matter how far apart they are

      for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
          // if two stones are in the same row or column, union them
          if (stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]) {
            ds.union(i, j);
          }
        }
      }

      int components = 0;
      for (int i = 0; i < n; i++) {
        if (ds.findParent(i) == i) {
          components++;
        }
      }

      // our answer is simple
      // it's total number of stones minus the number of components
      return n - components;
    }
  }
}
