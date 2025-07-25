package com.monal.Graphs.BFS_DFS;

/*
An n x n grid is composed of 1 x 1 squares where each 1 x 1 square consists of a '/', '\', or blank space ' '. These characters divide the square into contiguous regions.
Given the grid grid represented as a string array, return the number of regions.
Note that backslash characters are escaped, so a '\' is represented as '\\'.
Example 1:
  Input: grid = [" /","/ "]
  Output: 2
Example 2:
  Input: grid = [" /","  "]
  Output: 1
Example 3:
  Input: grid = ["/\\","\\/"]
  Output: 5
  Explanation: Recall that because \ characters are escaped, "\\/" refers to \/, and "/\\" refers to /\.
*/
/*
VISUALIZATION OF THE 4-TRIANGLE APPROACH:

Each cell is divided into 4 triangular regions:
     0
   ┌─┴─┐
 3 │ X │ 1
   └─┬─┘
     2

Index mapping for cell (i,j):
- Triangle 0: Top
- Triangle 1: Right
- Triangle 2: Bottom
- Triangle 3: Left

CHARACTER EFFECTS:

1. SPACE ' ': All triangles connected
   ┌───┐
   │   │  → All 4 triangles = 1 region
   └───┘

2. FORWARD SLASH '/': Divides diagonally
   ┌─╱─┐
   │╱  │  → Top+Left vs Right+Bottom
   └───┘    (triangles 0,3) vs (1,2)

3. BACKSLASH '\': Divides diagonally
   ┌─╲─┐
   │  ╲│  → Top+Right vs Left+Bottom
   └───┘    (triangles 0,1) vs (2,3)

ADJACENT CELL CONNECTIONS:
- Current cell's TOP (0) connects with above cell's BOTTOM (2)
- Current cell's LEFT (3) connects with left cell's RIGHT (1)

WHY UNION-FIND WORKS:
1. **Problem Transformation**: Convert visual regions to graph connectivity
2. **Efficient Merging**: Union-Find excels at merging connected components
3. **Dynamic Counting**: Automatically tracks number of separate regions
4. **Optimal Complexity**: O(N²α(N²)) ≈ O(N²) for practical purposes

ALGORITHM STEPS:
1. Create 4 triangles per grid cell (4×N² total triangles)
2. For each cell, union appropriate triangles based on character
3. Union triangles across cell boundaries
4. Return number of connected components

TIME: O(N² × α(N²)) where α is inverse Ackermann (nearly constant)
SPACE: O(N²) for Union-Find structure

This elegant transformation shows Union-Find's power beyond typical "connected components" problems!
*/
public class P022 {
  class Solution {
    public int regionsBySlashes(String[] grid) {
      int n = grid.length;
      UnionFind uf = new UnionFind(4 * n * n); // 4 triangles per cell

      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          int index = 4 * (i * n + j); // Base index for current cell
          char c = grid[i].charAt(j);

          // Union triangles within the same cell based on character
          if (c == ' ') {
            // Empty space: all 4 triangles are connected
            uf.union(index, index + 1); // top-right
            uf.union(index + 1, index + 2); // right-bottom
            uf.union(index + 2, index + 3); // bottom-left
          } else if (c == '/') {
            // '/' divides: top-left with left, right with bottom
            uf.union(index, index + 3); // top with left
            uf.union(index + 1, index + 2); // right with bottom
          } else if (c == '\\') {
            // '\' divides: top-left with right, left with bottom
            uf.union(index, index + 1); // top with right
            uf.union(index + 2, index + 3); // bottom with left
          }

          // Union with adjacent cells
          // Connect with cell above (if exists)
          if (i > 0) {
            int aboveIndex = 4 * ((i - 1) * n + j);
            uf.union(index, aboveIndex + 2); // current top with above bottom
          }

          // Connect with cell to the left (if exists)
          if (j > 0) {
            int leftIndex = 4 * (i * n + (j - 1));
            uf.union(index + 3, leftIndex + 1); // current left with left right
          }
        }
      }

      return uf.getComponents();
    }
  }

  class UnionFind {
    private int[] parent;
    private int[] rank;
    private int components;

    public UnionFind(int n) {
      parent = new int[n];
      rank = new int[n];
      components = n;
      for (int i = 0; i < n; i++) {
        parent[i] = i;
      }
    }

    public int find(int x) {
      if (parent[x] != x) {
        parent[x] = find(parent[x]); // Path compression
      }
      return parent[x];
    }

    public void union(int x, int y) {
      int rootX = find(x);
      int rootY = find(y);

      if (rootX != rootY) {
        // Union by rank
        if (rank[rootX] < rank[rootY]) {
          parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
          parent[rootY] = rootX;
        } else {
          parent[rootY] = rootX;
          rank[rootX]++;
        }
        components--; // Decrease component count when merging
      }
    }

    public int getComponents() {
      return components;
    }
  }

}
