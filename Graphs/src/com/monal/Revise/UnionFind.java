package com.monal.Revise;

import java.util.*;

public class UnionFind {

// PROBLEM 1: Dynamic Connectivity with Edge Weights

// You have n nodes. Process q queries:
// 1. Add edge (u,v) with weight w
// 2. Query if nodes u,v are connected AND their path has max edge weight <= k
// Constraints: n <= 10^5, q <= 10^5
/*
 * In this problem we are given a class Weighted Union-Find we need to implement
 * that supports the following operations:
 * 1. `union(x, y, weight)` - Connect nodes x and y with weight.
 * 2. `find(x)` - Find the root of node x.
 * 3. `canReach(x, y, maxW)` - Check if x and y are connected and the maximum weight on the path from x to y is <= maxW.
 * This is a dynamic connectivity problem with edge weights.
 * We will use a union-find data structure with path compression and union by rank.
 */


class Solution1 {
  static class WeightedUnionFind {
    int[] parent, rank;
    int[] maxWeight; // Maximum weight on path to root

    WeightedUnionFind(int n) {
      parent = new int[n];
      rank = new int[n];
      maxWeight = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        rank[i] = 0;
        maxWeight[i] = 0;
      }
    }

    int find(int x) {
      if (parent[x] != x) {
        int root = find(parent[x]);
        maxWeight[x] = Math.max(maxWeight[x], maxWeight[parent[x]]);
        parent[x] = root;
      }
      return parent[x];
    }

    void union(int x, int y, int weight) {
      int px = find(x), py = find(y);
      if (px == py)
        return;

      if (rank[px] < rank[py]) {
        parent[px] = py;
        maxWeight[px] = weight;
      } else if (rank[px] > rank[py]) {
        parent[py] = px;
        maxWeight[py] = weight;
      } else {
        parent[py] = px;
        maxWeight[py] = weight;
        rank[px]++;
      }
    }

    boolean canReach(int x, int y, int maxW) {
      if (find(x) != find(y))
        return false;

      // Check if path from x to root has max weight <= maxW
      int curr = x;
      int pathMax = 0;
      while (parent[curr] != curr) {
        pathMax = Math.max(pathMax, maxWeight[curr]);
        curr = parent[curr];
      }

      // Check if path from y to root has max weight <= maxW
      curr = y;
      while (parent[curr] != curr) {
        pathMax = Math.max(pathMax, maxWeight[curr]);
        curr = parent[curr];
      }

      return pathMax <= maxW;
    }
  }
}

// PROBLEM 2: Minimum Spanning Tree with Forbidden Edges
// Given n nodes, m edges, and k forbidden edges that cannot be used together.
// Find MST weight, or -1 if impossible.
// Advanced: Use Union-Find with rollback operations

class Solution2 {
  static class RollbackUnionFind {
    int[] parent, rank;
    Stack<int[]> operations; // Store operations for rollback

    RollbackUnionFind(int n) {
      parent = new int[n];
      rank = new int[n];
      operations = new Stack<>();
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        rank[i] = 0;
      }
    }

    int find(int x) {
      if (parent[x] != x) {
        parent[x] = find(parent[x]);
      }
      return parent[x];
    }

    boolean union(int x, int y) {
      int px = find(x), py = find(y);
      if (px == py) {
        operations.push(new int[] { -1, -1, -1, -1 }); // No-op
        return false;
      }

      if (rank[px] < rank[py]) {
        int temp = px;
        px = py;
        py = temp;
      }

      operations.push(new int[] { py, parent[py], px, rank[px] });
      parent[py] = px;
      if (rank[px] == rank[py])
        rank[px]++;
      return true;
    }

    void rollback() {
      if (operations.isEmpty())
        return;
      int[] op = operations.pop();
      if (op[0] == -1)
        return; // No-op

      parent[op[0]] = op[1];
      rank[op[2]] = op[3];
    }

    int getComponents() {
      Set<Integer> roots = new HashSet<>();
      for (int i = 0; i < parent.length; i++) {
        roots.add(find(i));
      }
      return roots.size();
    }
  }

  static class Edge {
    int u, v, w;

    Edge(int u, int v, int w) {
      this.u = u;
      this.v = v;
      this.w = w;
    }
  }

  static long solveMST(int n, Edge[] edges, int[][] forbidden) {
    Arrays.sort(edges, (a, b) -> a.w - b.w);

    // Try all possible combinations of forbidden edge sets
    int k = forbidden.length;
    long minCost = Long.MAX_VALUE;

    for (int mask = 0; mask < (1 << k); mask++) {
      Set<Integer> forbiddenSet = new HashSet<>();
      for (int i = 0; i < k; i++) {
        if ((mask & (1 << i)) != 0) {
          forbiddenSet.add(forbidden[i][0]);
          forbiddenSet.add(forbidden[i][1]);
        }
      }

      RollbackUnionFind uf = new RollbackUnionFind(n);
      long cost = 0;
      int edgesUsed = 0;

      for (int i = 0; i < edges.length; i++) {
        if (forbiddenSet.contains(i))
          continue;

        if (uf.union(edges[i].u, edges[i].v)) {
          cost += edges[i].w;
          edgesUsed++;
          if (edgesUsed == n - 1)
            break;
        }
      }

      if (edgesUsed == n - 1) {
        minCost = Math.min(minCost, cost);
      }
    }

    return minCost == Long.MAX_VALUE ? -1 : minCost;
  }
}

// PROBLEM 3: Dynamic Graph Connectivity with Time Travel
// Process queries on a graph:
// 1. Add edge (u,v) at time t
// 2. Remove edge (u,v) at time t
// 3. Query if u,v were connected at time t
// Use persistent Union-Find with coordinate compression

class Solution3 {
  static class PersistentUnionFind {
    Map<Integer, Map<Integer, Integer>> timeToParent;
    Map<Integer, Map<Integer, Integer>> timeToRank;
    TreeSet<Integer> timePoints;

    PersistentUnionFind() {
      timeToParent = new HashMap<>();
      timeToRank = new HashMap<>();
      timePoints = new TreeSet<>();
    }

    void addTimePoint(int time, int n) {
      timePoints.add(time);
      timeToParent.putIfAbsent(time, new HashMap<>());
      timeToRank.putIfAbsent(time, new HashMap<>());

      // Copy from previous time point
      Integer prevTime = timePoints.lower(time);
      if (prevTime != null) {
        timeToParent.get(time).putAll(timeToParent.get(prevTime));
        timeToRank.get(time).putAll(timeToRank.get(prevTime));
      } else {
        // Initialize
        for (int i = 0; i < n; i++) {
          timeToParent.get(time).put(i, i);
          timeToRank.get(time).put(i, 0);
        }
      }
    }

    int find(int x, int time) {
      Integer floorTime = timePoints.floor(time);
      if (floorTime == null)
        return x;

      Map<Integer, Integer> parent = timeToParent.get(floorTime);
      if (parent.get(x) != x) {
        parent.put(x, find(parent.get(x), time));
      }
      return parent.get(x);
    }

    void union(int x, int y, int time, int n) {
      if (!timePoints.contains(time)) {
        addTimePoint(time, n);
      }

      int px = find(x, time);
      int py = find(y, time);
      if (px == py)
        return;

      Map<Integer, Integer> parent = timeToParent.get(time);
      Map<Integer, Integer> rank = timeToRank.get(time);

      if (rank.get(px) < rank.get(py)) {
        parent.put(px, py);
      } else if (rank.get(px) > rank.get(py)) {
        parent.put(py, px);
      } else {
        parent.put(py, px);
        rank.put(px, rank.get(px) + 1);
      }
    }

    boolean isConnected(int x, int y, int time) {
      return find(x, time) == find(y, time);
    }
  }
}

// PROBLEM 4: Bipartite Graph Detection with Union-Find
// Check if a graph remains bipartite after each edge addition
// Use Union-Find with parity/color tracking

class Solution4 {
  static class BipartiteUnionFind {
    int[] parent, rank, parity;

    BipartiteUnionFind(int n) {
      parent = new int[2 * n]; // Create 2n nodes for bipartite representation
      rank = new int[2 * n];
      parity = new int[n];

      for (int i = 0; i < 2 * n; i++) {
        parent[i] = i;
        rank[i] = 0;
      }
      for (int i = 0; i < n; i++) {
        parity[i] = 0;
      }
    }

    int find(int x) {
      if (parent[x] != x) {
        parent[x] = find(parent[x]);
      }
      return parent[x];
    }

    void union(int x, int y) {
      int px = find(x), py = find(y);
      if (px == py)
        return;

      if (rank[px] < rank[py]) {
        parent[px] = py;
      } else if (rank[px] > rank[py]) {
        parent[py] = px;
      } else {
        parent[py] = px;
        rank[px]++;
      }
    }

    boolean addEdge(int u, int v) {
      // Check if u and v are already in same partition
      if (find(u) == find(v))
        return false; // Not bipartite

      // Check if u and v are in opposite partitions
      if (find(u + parent.length / 2) == find(v + parent.length / 2))
        return false;

      // Add edge: connect u with v's opposite, v with u's opposite
      union(u, v + parent.length / 2);
      union(v, u + parent.length / 2);

      return true;
    }

    boolean isBipartite() {
      int n = parent.length / 2;
      for (int i = 0; i < n; i++) {
        if (find(i) == find(i + n))
          return false;
      }
      return true;
    }
  }
}

// USAGE EXAMPLES AND TEST CASES
class TestCases {
  public static void main(String[] args) {
    // Test Problem 1: Weighted connectivity
    System.out.println("=== Testing Weighted Union-Find ===");
    Solution1.WeightedUnionFind wuf = new Solution1.WeightedUnionFind(5);
    wuf.union(0, 1, 10);
    wuf.union(1, 2, 5);
    wuf.union(3, 4, 15);
    System.out.println("Can reach 0->2 with max weight 10: " + wuf.canReach(0, 2, 10));
    System.out.println("Can reach 0->2 with max weight 4: " + wuf.canReach(0, 2, 4));

    // Test Problem 4: Bipartite detection
    System.out.println("\n=== Testing Bipartite Union-Find ===");
    Solution4.BipartiteUnionFind buf = new Solution4.BipartiteUnionFind(4);
    System.out.println("Add edge 0-1: " + buf.addEdge(0, 1));
    System.out.println("Add edge 1-2: " + buf.addEdge(1, 2));
    System.out.println("Add edge 2-3: " + buf.addEdge(2, 3));
    System.out.println("Is bipartite: " + buf.isBipartite());
    System.out.println("Add edge 0-2: " + buf.addEdge(0, 2));
    System.out.println("Is bipartite: " + buf.isBipartite());
  }
}}
