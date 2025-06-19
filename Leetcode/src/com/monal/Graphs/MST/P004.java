package com.monal.Graphs.MST;
import java.util.*;

/*
You are given a n,m which means the row and column of the 2D matrix and an array of  size k denoting the number of operations. Matrix elements is 0 if there is water or 1 if there is land. Originally, the 2D matrix is all 0 which means there is no land in the matrix. The array has k operator(s) and each operator has two integer A[i][0], A[i][1] means that you can change the cell matrix[A[i][0]][A[i][1]] from sea to island. Return how many island are there in the matrix after each operation.You need to return an array of size k.
Note : An island means group of 1s such that they share a common side.



Example 1:

Input: n = 4
m = 5
k = 4
A = {{1,1},{0,1},{3,3},{3,4}}

Output: 1 1 2 2
*/
public class P004 {
  public List<Integer> numOfIslands(int rows, int cols, int[][] operators) {
    int n = rows, m = cols;
    DisjointSet ds = new DisjointSet(n * m);

    List<Integer> result = new ArrayList<>();
    boolean[][] visited = new boolean[n][m];
    int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    int islandCount = 0;

    for (int[] operation : operators) {
      int x = operation[0], y = operation[1];
      int currentIndex = x * m + y; // Convert 2D coordinates to 1D index

      // Check if already visited
      if (visited[x][y]) {
        result.add(islandCount);
        continue;
      }

      // If not visited, mark as visited
      visited[x][y] = true;
      islandCount++; // New land added, increment island count

      // Check all 4 directions
      for (int[] dir : directions) {
        int newX = x + dir[0], newY = y + dir[1];

        // Check if newX and newY are valid AND the neighbor is already land
        if (newX >= 0 && newY >= 0 && newX < n && newY < m && visited[newX][newY]) {
          int neighborIndex = newX * m + newY;
          if (ds.union(currentIndex, neighborIndex)) {
            islandCount--; // Two islands merged into one
          }
        }
      }
      result.add(islandCount);
    }
    return result;
  }
}

class DisjointSet {
  int[] rank;
  int[] parent;

  public DisjointSet(int n) {
    parent = new int[n];
    rank = new int[n];

    // Initialize
    for (int i = 0; i < n; i++) {
      parent[i] = i;
      rank[i] = 0;
    }
  }

  public int find(int node) {
    if (parent[node] == node) {
      return node;
    }
    return parent[node] = find(parent[node]); // Path compression
  }

  public boolean union(int n1, int n2) {
    int rootX = find(n1), rootY = find(n2);
    if (rootX == rootY)
      return false; // Already in same component

    // Union by rank
    if (rank[rootX] < rank[rootY]) {
      parent[rootX] = rootY;
    } else if (rank[rootX] > rank[rootY]) {
      parent[rootY] = rootX;
    } else {
      parent[rootY] = rootX;
      rank[rootX]++;
    }
    return true; // Successfully merged two different components
  }
}