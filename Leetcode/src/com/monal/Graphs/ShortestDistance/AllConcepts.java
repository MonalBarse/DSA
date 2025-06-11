package com.monal.Graphs.ShortestDistance;

import java.util.*;

public class AllConcepts {

  /**
   * PREREQUISITES SECTION
   * Understanding these concepts is crucial before diving into shortest path
   * algorithms
   */

  static class Prerequisites {

    /**
     * EDGE REPRESENTATION - Different ways to represent weighted edges
     */

    static class Edge {
      int to, weight;

      Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
      }
    }

    /**
     * PAIR CLASS - Used extensively in shortest path algorithms
     * First element typically represents distance/weight
     * Second element represents the node/vertex
     */
    static class Pair {
      int distance, node;

      Pair(int distance, int node) {
        this.distance = distance;
        this.node = node;
      }
    }

    /**
     * PRIORITY QUEUE UNDERSTANDING
     * - Min-heap by default in Java
     * - We often need custom comparator for our Pair class
     * - Essential for Dijkstra's algorithm
     */
    public static void priorityQueueDemo() {
      // Min-heap based on distance
      PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> a.distance - b.distance);

      pq.offer(new Pair(10, 1));
      pq.offer(new Pair(5, 2));
      pq.offer(new Pair(15, 3));

      System.out.println("Priority Queue Demo (Min-Heap by distance):");
      while (!pq.isEmpty()) {
        Pair p = pq.poll();
        System.out.println("Distance: " + p.distance + ", Node: " + p.node);
      }
    }

    /**
     * ADJACENCY LIST FOR WEIGHTED GRAPHS
     * This is the most common representation for shortest path problems
     */
    public static List<List<Prerequisites.Edge>> createWeightedGraph(int n) {
      List<List<Prerequisites.Edge>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }
      return graph;
    }
  }

  /**
   * CORE CONCEPTS SECTION
   * Implementation of all major shortest path algorithms
   * 1. Shortest Path in Unweighted Graph (BFS)
   * 2. Shortest Path in DAG (Topological Sort + Relaxation)
   * 3. Dijkstra's Algorithm
   * 4. Shortest Path in Binary Maze
   * 5. Path with Minimum Effort (Leetcode 1631)
   * 6. Cheapest Flights Within K Stops (Leetcode 787)
   * 7. Network Delay Time (Leetcode 743)
   * 8. Number of Ways to Arrive at Destination (Leetcode 1976)
   * 9. Minimum Steps with Multiplication and Mod Operations
   * 10. Bellman-Ford Algorithm
   * 11. Floyd-Warshall Algorithm
   * 12. Find City with Smallest Number of Neighbors (Leetcode 1334)
   * 13. Swim in Rising Water (Leetcode 778)
   */

  static class Concepts {
    /**
     * 1. SHORTEST PATH IN UNWEIGHTED GRAPH (BFS)
     * Time: O(V + E), Space: O(V)
     *
     * Key Insight: In unweighted graphs, BFS naturally finds shortest path
     * because it explores nodes level by level (distance-wise)
     */
    public static int[] shortestPathUnweighted(List<List<Integer>> graph, int src) {
      int n = graph.size();
      int[] dist = new int[n];
      Arrays.fill(dist, -1); // -1 means unreachable

      Queue<Integer> queue = new LinkedList<>();
      queue.offer(src);
      dist[src] = 0;

      while (!queue.isEmpty()) {
        int node = queue.poll();

        for (int neighbor : graph.get(node)) {
          if (dist[neighbor] == -1) { // Not visited
            dist[neighbor] = dist[node] + 1;
            queue.offer(neighbor);
          }
        }
      }

      return dist;
    }

    /**
     * 2. SHORTEST PATH IN DAG (Topological Sort + Relaxation)
     * Time: O(V + E), Space: O(V)
     *
     * Key Insight: Process nodes in topological order, then relax edges
     * Works because there are no cycles, so once we process a node,
     * we've found its shortest distance
     */
    public static int[] shortestPathDAG(List<List<Prerequisites.Edge>> graph, int src) {
      int n = graph.size();

      // Step 1: Find topological ordering using DFS
      boolean[] visited = new boolean[n];
      Stack<Integer> topoStack = new Stack<>();

      for (int i = 0; i < n; i++) {
        if (!visited[i]) {
          topologicalSort(graph, i, visited, topoStack);
        }
      }

      // Step 2: Initialize distances
      int[] dist = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[src] = 0;

      // Step 3: Process nodes in topological order and relax edges
      while (!topoStack.isEmpty()) {
        int node = topoStack.pop();

        if (dist[node] != Integer.MAX_VALUE) {
          for (Prerequisites.Edge edge : graph.get(node)) {
            if (dist[node] + edge.weight < dist[edge.to]) {
              dist[edge.to] = dist[node] + edge.weight;
            }
          }
        }
      }

      return dist;
    }

    private static void topologicalSort(List<List<Prerequisites.Edge>> graph, int node, boolean[] visited,
        Stack<Integer> topoStack) {
      visited[node] = true;

      for (Prerequisites.Edge edge : graph.get(node)) {
        if (!visited[edge.to]) {
          topologicalSort(graph, edge.to, visited, topoStack);
        }
      }

      topoStack.push(node);
    }

    /**
     * 3. DIJKSTRA'S ALGORITHM - Most Important for Interviews!
     * Time: O((V + E) log V), Space: O(V)
     *
     *
     * Key Insight: Always pick the unvisited node with minimum distance
     * Greedy approach works because edge weights are non-negative
     *
     * INTERVIEW PATTERN: This exact template is used in 70%+ shortest path problems
     */
    // Using PriorityQueue for Dijkstra's algorithm
    public static int[] dijkstra(List<List<Prerequisites.Edge>> graph, int src) {
      int n = graph.size();
      int[] dist = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[src] = 0;

      // Min-heap: (distance, node)
      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
      pq.offer(new int[] { 0, src });

      while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int currentDist = current[0];
        int node = current[1];

        // Skip if we've already found a better path
        if (currentDist > dist[node])
          continue;

        // Relax all adjacent edges
        for (Prerequisites.Edge edge : graph.get(node)) {
          int newDist = dist[node] + edge.weight;
          if (newDist < dist[edge.to]) {
            dist[edge.to] = newDist;
            pq.offer(new int[] { newDist, edge.to });
          }
        }

      }

      // Replace unreachable nodes with -1
      for (int i = 0; i < n; i++) {
        if (dist[i] == Integer.MAX_VALUE) {
          dist[i] = -1;
        }
      }

      return dist;
    }

    public static int[] dijkstraOptimizedSet(List<List<Prerequisites.Edge>> graph, int src) {
      int n = graph.size();
      int[] dist = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[src] = 0;

      // TreeSet to maintain nodes sorted by distance
      // Format: [distance, node] - TreeSet sorts by distance first
      TreeSet<int[]> unvisited = new TreeSet<>((a, b) -> {
        if (a[0] != b[0])
          return a[0] - b[0]; // Compare by distance first
        return a[1] - b[1]; // If distances equal, compare by node ID
      });

      // Add all nodes to the set
      for (int i = 0; i < n; i++) {
        unvisited.add(new int[] { dist[i], i });
      }

      while (!unvisited.isEmpty()) {
        // Get node with minimum distance - O(log V)
        int[] current = unvisited.pollFirst();
        int currentNode = current[1];
        int currentDist = current[0];

        // Skip if we've found a better path (shouldn't happen with TreeSet)
        if (currentDist > dist[currentNode])
          continue;

        // Relax all adjacent edges
        for (Prerequisites.Edge edge : graph.get(currentNode)) {
          int newDist = dist[currentNode] + edge.weight;
          if (newDist < dist[edge.to]) {
            // Remove old entry from TreeSet
            unvisited.remove(new int[] { dist[edge.to], edge.to });

            // Update distance
            dist[edge.to] = newDist;

            // Add updated entry to TreeSet
            unvisited.add(new int[] { newDist, edge.to });
          }
        }
      }

      // Replace unreachable nodes with -1
      for (int i = 0; i < n; i++) {
        if (dist[i] == Integer.MAX_VALUE) {
          dist[i] = -1;
        }
      }

      return dist;
    }

    /**
     * 4. SHORTEST PATH IN BINARY MAZE
     * Given an n x n binary matrix grid, return the length of the shortest clear
     * path in the matrix. If there is no clear path, return -1.
     *
     * A clear path in a binary matrix is a path from the top-left cell (i.e., (0,
     * 0)) to the bottom-right cell (i.e., (n - 1, n - 1)) such that:
     *
     * All the visited cells of the path are 0.
     * All the adjacent cells of the path are 8-directionally connected (i.e., they
     * are different and they share an edge or a corner).
     * The length of a clear path is the number of visited cells of this path.
     */
    public static int shortestPathBinaryMatrix(int[][] grid) {
      int n = grid.length;
      if (grid[0][0] == 1 || grid[n - 1][n - 1] == 1) {
        return -1; // No path possible
      }
      // BFS queue
      Queue<int[]> queue = new ArrayDeque<>();
      queue.offer(new int[] { 0, 0, 1 }); // {row, col, distance }
      boolean[][] visited = new boolean[n][n];
      visited[0][0] = true;

      int[][] directions = {
          { -1, -1 }, { -1, 0 }, { -1, 1 },
          { 0, -1 }, { 0, 1 },
          { 1, -1 }, { 1, 0 }, { 1, 1 }
      };

      // Loop
      while (!queue.isEmpty()) {
        int[] current = queue.poll();
        int row = current[0], col = current[1], dist = current[2];

        // If we reached the bottom-right corner
        if (row == n - 1 && col == n - 1) {
          return dist;
        }

        // Explore all 8 directions
        for (int[] dir : directions) {
          int newRow = row + dir[0];
          int newCol = col + dir[1];

          // Check bounds and if cell is clear
          if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n &&
              grid[newRow][newCol] == 0 && !visited[newRow][newCol]) {
            visited[newRow][newCol] = true;
            queue.offer(new int[] { newRow, newCol, dist + 1 });
          }
        }
      }

      return -1; // No path found
    }

    /**
     * 5. PATH WITH MINIMUM EFFORT (Leetcode 1631)
     * Time: O(MN log(MN)), Space: O(MN)
     *
     * Key Insight: Use Dijkstra where "distance" is the maximum effort needed
     * Effort = absolute difference in heights
     */
    public static int minimumEffortPath(int[][] heights) {
      int m = heights.length, n = heights[0].length;
      int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

      // dist[i][j] = minimum effort to reach cell (i, j)
      int[][] dist = new int[m][n];
      for (int[] row : dist) {
        Arrays.fill(row, Integer.MAX_VALUE);
      }
      dist[0][0] = 0;

      // PriorityQueue: (effort, row, col)
      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
      pq.offer(new int[] { 0, 0, 0 });

      while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int effort = current[0], row = current[1], col = current[2];

        if (row == m - 1 && col == n - 1) {
          return effort; // Reached destination
        }

        if (effort > dist[row][col])
          continue;

        for (int[] dir : directions) {
          int newRow = row + dir[0];
          int newCol = col + dir[1];

          if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n) {
            int newEffort = Math.max(effort, Math.abs(heights[newRow][newCol] - heights[row][col]));
            if (newEffort < dist[newRow][newCol]) {
              dist[newRow][newCol] = newEffort;
              pq.offer(new int[] { newEffort, newRow, newCol });
            }
          }
        }
      }

      return 0;
    }

    /**
     * 6. CHEAPEST FLIGHTS WITHIN K STOPS (Leetcode 787)
     * Time: O(K * E), Space: O(V)
     *
     * Key Insight: Modified Dijkstra with constraint on number of stops
     * We need to track both cost and number of stops
     */
    public static int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
      // Build adjacency list
      List<List<int[]>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }
      for (int[] flight : flights) {
        graph.get(flight[0]).add(new int[] { flight[1], flight[2] }); // to, price
      }

      // PriorityQueue: (cost, node, stops)
      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
      pq.offer(new int[] { 0, src, 0 });

      // dist[node] = minimum cost to reach node within allowed stops
      int[] dist = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);

      while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int cost = current[0], node = current[1], stops = current[2];

        if (node == dst) {
          return cost;
        }

        if (stops > k)
          continue;

        for (int[] neighbor : graph.get(node)) {
          int nextNode = neighbor[0];
          int price = neighbor[1];
          int newCost = cost + price;

          // Key insight: We don't use standard dist array check here
          // because we might reach a node with higher cost but fewer stops
          if (newCost < dist[nextNode] || stops < k) {
            dist[nextNode] = newCost;
            pq.offer(new int[] { newCost, nextNode, stops + 1 });
          }
        }
      }

      return -1;
    }

    /**
     * 7. NETWORK DELAY TIME (Leetcode 743)
     * Time: O((V + E) log V), Space: O(V + E)
     *
     * Key Insight: Find shortest path to all nodes, return maximum distance
     * Classic Dijkstra application
     */
    public static int networkDelayTime(int[][] times, int n, int k) {
      // Build adjacency list
      List<List<int[]>> graph = new ArrayList<>();
      for (int i = 0; i <= n; i++) {
        graph.add(new ArrayList<>());
      }
      for (int[] time : times) {
        graph.get(time[0]).add(new int[] { time[1], time[2] }); // to, weight
      }

      // Dijkstra's algorithm
      int[] dist = new int[n + 1];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[k] = 0;

      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
      pq.offer(new int[] { 0, k });

      while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int d = current[0], node = current[1];

        if (d > dist[node])
          continue;

        for (int[] neighbor : graph.get(node)) {
          int nextNode = neighbor[0];
          int weight = neighbor[1];
          int newDist = dist[node] + weight;

          if (newDist < dist[nextNode]) {
            dist[nextNode] = newDist;
            pq.offer(new int[] { newDist, nextNode });
          }
        }
      }

      // Find maximum distance (excluding node 0 as we use 1-indexed)
      int maxDist = 0;
      for (int i = 1; i <= n; i++) {
        if (dist[i] == Integer.MAX_VALUE) {
          return -1; // Some node is unreachable
        }
        maxDist = Math.max(maxDist, dist[i]);
      }

      return maxDist;
    }

    /**
     * 8. NUMBER OF WAYS TO ARRIVE AT DESTINATION (Leetcode 1976)
     * Time: O((V + E) log V), Space: O(V)
     *
     * Key Insight: Modified Dijkstra that also counts paths
     * When we find a path with same shortest distance, add to count
     */
    public static int countPaths(int n, int[][] roads) {
      final int MOD = 1000000007;

      // Build adjacency list
      List<List<long[]>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }
      for (int[] road : roads) {
        graph.get(road[0]).add(new long[] { road[1], road[2] });
        graph.get(road[1]).add(new long[] { road[0], road[2] });
      }

      long[] dist = new long[n];
      Arrays.fill(dist, Long.MAX_VALUE);
      long[] ways = new long[n];

      dist[0] = 0;
      ways[0] = 1;

      PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
      pq.offer(new long[] { 0, 0 }); // distance, node

      while (!pq.isEmpty()) {
        long[] current = pq.poll();
        long d = current[0];
        int node = (int) current[1];

        if (d > dist[node])
          continue;

        for (long[] neighbor : graph.get(node)) {
          int nextNode = (int) neighbor[0];
          long weight = neighbor[1];
          long newDist = dist[node] + weight;

          if (newDist < dist[nextNode]) {
            // Found shorter path
            dist[nextNode] = newDist;
            ways[nextNode] = ways[node];
            pq.offer(new long[] { newDist, nextNode });
          } else if (newDist == dist[nextNode]) {
            // Found another path with same shortest distance
            ways[nextNode] = (ways[nextNode] + ways[node]) % MOD;
          }
        }
      }

      return (int) ways[n - 1];
    }

    /**
     * 9. MINIMUM STEPS WITH MULTIPLICATION AND MOD OPERATIONS
     * Time: O(K * log K), where K is the constraint value
     *
     * Key Insight: Use BFS/Dijkstra with states as (current_value, steps)
     * Constraint: value should be within [0, 1000] to avoid infinite loops
     */
    public static int minimumOperations(int[] arr, int start, int target) {
      if (start == target)
        return 0;

      Set<Integer> visited = new HashSet<>();
      Queue<int[]> queue = new LinkedList<>();
      queue.offer(new int[] { start, 0 }); // value, steps
      visited.add(start);

      while (!queue.isEmpty()) {
        int[] current = queue.poll();
        int value = current[0];
        int steps = current[1];

        for (int num : arr) {
          // Try all three operations: +, -, *
          int[] nextValues = {
              value + num,
              value - num,
              value * num
          };

          for (int nextValue : nextValues) {
            if (nextValue == target) {
              return steps + 1;
            }

            // Constraint to avoid infinite exploration
            if (nextValue >= 0 && nextValue <= 1000 && !visited.contains(nextValue)) {
              visited.add(nextValue);
              queue.offer(new int[] { nextValue, steps + 1 });
            }
          }
        }
      }

      return -1;
    }

    /**
     * 10. BELLMAN-FORD ALGORITHM
     * Time: O(VE), Space: O(V)
     *
     * Key Insight: Can detect negative cycles and handle negative weights
     * Relax all edges V-1 times, then check for negative cycles
     */
    public static int[] bellmanFord(int n, int[][] edges, int src) {
      int[] dist = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[src] = 0;

      // Relax all edges V-1 times
      for (int i = 0; i < n - 1; i++) {
        for (int[] edge : edges) {
          int u = edge[0], v = edge[1], w = edge[2];
          if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
            dist[v] = dist[u] + w;
          }
        }
      }

      // Check for negative cycles
      for (int[] edge : edges) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (dist[u] != Integer.MAX_VALUE && dist[u] + w < dist[v]) {
          return null; // Negative cycle detected
        }
      }

      return dist;
    }

    /**
     * 11. FLOYD-WARSHALL ALGORITHM
     * Time: O(V³), Space: O(V²)
     *
     * Key Insight: All-pairs shortest path using dynamic programming
     * dist[i][j] = shortest path from i to j using vertices 0 to k-1 as
     * intermediates
     */
    public static int[][] floydWarshall(int[][] graph) {
      int n = graph.length;
      int[][] dist = new int[n][n];

      // Initialize distance matrix
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (i == j) {
            dist[i][j] = 0;
          } else if (graph[i][j] != 0) {
            dist[i][j] = graph[i][j];
          } else {
            dist[i][j] = Integer.MAX_VALUE;
          }
        }
      }

      // Try all intermediate vertices
      for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
          for (int j = 0; j < n; j++) {
            if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
              dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
            }
          }
        }
      }

      return dist;
    }

    /**
     * 12. FIND CITY WITH SMALLEST NUMBER OF NEIGHBORS (Leetcode 1334)
     * Time: O(V³), Space: O(V²)
     *
     * Key Insight: Use Floyd-Warshall to find all-pairs shortest paths,
     * then count reachable cities within threshold for each city
     */
    public static int findTheCity(int n, int[][] edges, int distanceThreshold) {
      // Initialize distance matrix
      int[][] dist = new int[n][n];
      for (int i = 0; i < n; i++) {
        Arrays.fill(dist[i], Integer.MAX_VALUE);
        dist[i][i] = 0;
      }

      // Fill in direct edges
      for (int[] edge : edges) {
        dist[edge[0]][edge[1]] = edge[2];
        dist[edge[1]][edge[0]] = edge[2];
      }

      // Floyd-Warshall
      for (int k = 0; k < n; k++) {
        for (int i = 0; i < n; i++) {
          for (int j = 0; j < n; j++) {
            if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
              dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
            }
          }
        }
      }

      // Find city with minimum reachable neighbors
      int minReachable = n;
      int resultCity = 0;

      for (int i = 0; i < n; i++) {
        int reachable = 0;
        for (int j = 0; j < n; j++) {
          if (i != j && dist[i][j] <= distanceThreshold) {
            reachable++;
          }
        }

        if (reachable <= minReachable) {
          minReachable = reachable;
          resultCity = i;
        }
      }

      return resultCity;
    }

    /**
     * 13. SWIM IN RISING WATER (Leetcode 778)
     * Time: O(N² log N), Space: O(N²)
     *
     * Key Insight: Use Dijkstra where "distance" is the maximum elevation
     * encountered
     * We want to minimize the maximum elevation on the path
     */
    public static int swimInWater(int[][] grid) {
      int n = grid.length;
      int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

      // PriorityQueue: (max_elevation_so_far, row, col)
      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
      boolean[][] visited = new boolean[n][n];

      pq.offer(new int[] { grid[0][0], 0, 0 });

      while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int maxElevation = current[0];
        int row = current[1];
        int col = current[2];

        if (visited[row][col])
          continue;
        visited[row][col] = true;

        if (row == n - 1 && col == n - 1) {
          return maxElevation;
        }

        for (int[] dir : directions) {
          int newRow = row + dir[0];
          int newCol = col + dir[1];

          if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n && !visited[newRow][newCol]) {
            int newMaxElevation = Math.max(maxElevation, grid[newRow][newCol]);
            pq.offer(new int[] { newMaxElevation, newRow, newCol });
          }
        }
      }

      return -1;
    }
  }

  /**
   * ILLUSTRATIONS SECTION
   * Visual examples and step-by-step walkthroughs
   */
  static class Illustrations {

    public static void demonstrateAllAlgorithms() {
      System.out.println("=== SHORTEST PATH ALGORITHMS DEMONSTRATION ===\n");

      // 1. Unweighted Graph BFS
      System.out.println("1. SHORTEST PATH IN UNWEIGHTED GRAPH:");
      List<List<Integer>> unweightedGraph = Arrays.asList(
          Arrays.asList(1, 2), // 0 -> [1, 2]
          Arrays.asList(0, 3), // 1 -> [0, 3]
          Arrays.asList(0, 3), // 2 -> [0, 3]
          Arrays.asList(1, 2) // 3 -> [1, 2]
      );
      int[] distances = Concepts.shortestPathUnweighted(unweightedGraph, 0);
      System.out.println("Distances from node 0: " + Arrays.toString(distances));
      System.out.println("Explanation: BFS explores level by level, guaranteeing shortest path in unweighted graphs\n");

      // 2. Dijkstra's Algorithm
      System.out.println("2. DIJKSTRA'S ALGORITHM:");
      List<List<Prerequisites.Edge>> weightedGraph = Prerequisites.createWeightedGraph(4);
      weightedGraph.get(0).add(new Prerequisites.Edge(1, 1));
      weightedGraph.get(0).add(new Prerequisites.Edge(2, 4));
      weightedGraph.get(1).add(new Prerequisites.Edge(2, 2));
      weightedGraph.get(1).add(new Prerequisites.Edge(3, 6));
      weightedGraph.get(2).add(new Prerequisites.Edge(3, 3));

      int[] dijkstraDistances = Concepts.dijkstra(weightedGraph, 0);
      System.out.println("Distances from node 0: " + Arrays.toString(dijkstraDistances));
      System.out.println("Explanation: Dijkstra greedily picks the closest unvisited node, ensuring optimal paths\n");

      // 3. Binary Maze Example
      System.out.println("3. SHORTEST PATH IN BINARY MAZE:");
      int[][] maze = {
          { 0, 0, 1, 0, 0 },
          { 0, 0, 0, 0, 0 },
          { 0, 0, 0, 1, 0 },
          { 1, 1, 0, 1, 1 },
          { 0, 0, 0, 0, 0 }
      };
      int shortestMazePath = Concepts.shortestPathBinaryMatrix(maze);
      System.out.println("Shortest path from (0,0) to (4,4): " + shortestMazePath + " steps");
      System.out.println("Explanation: Treat maze as graph, use BFS since all moves have equal cost\n");

      // 4. Network Delay Time Example
      System.out.println("4. NETWORK DELAY TIME:");
      int[][] times = { { 2, 1, 1 }, { 2, 3, 1 }, { 3, 4, 1 } };
      int delayTime = Concepts.networkDelayTime(times, 4, 2);
      System.out.println("Network delay time from node 2 to reach all nodes: " + delayTime);
      System.out.println("Explanation: Find shortest path to all nodes, return the maximum distance\n");
    }

    /**
     * STEP-BY-STEP DIJKSTRA WALKTHROUGH
     * This helps students understand the algorithm internals
     */
    public static void dijkstraStepByStep() {
      System.out.println("=== DIJKSTRA'S ALGORITHM STEP-BY-STEP ===");
      System.out.println("Graph: 0 --(1)--> 1 --(2)--> 2");
      System.out.println("       |          |         |");
      System.out.println("      (4)        (6)       (3)");
      System.out.println("       v          v         v");
      System.out.println("       3 <---------- 4 <--->");

      // Manual step-by-step demonstration
      System.out.println("\nStep-by-step execution:");
      System.out.println("Initial: dist = [0, ∞, ∞, ∞], visited = [false, false, false, false]");
      System.out.println("Step 1: Pick node 0 (dist=0), relax edges to nodes 1 and 3");
      System.out.println("        dist = [0, 1, ∞, 4], pq = [(1,1), (4,3)]");
      System.out.println("Step 2: Pick node 1 (dist=1), relax edges to nodes 2 and 4");
      System.out.println("        dist = [0, 1, 3, 4], pq = [(3,2), (4,3), (7,4)]");
      System.out.println("Step 3: Pick node 2 (dist=3), relax edge to node 4");
      System.out.println("        dist = [0, 1, 3, 4], pq = [(4,3), (6,4), (7,4)]");
      System.out.println("Final distances: [0, 1, 3, 4]\n");
    }
  }

  /**
   * HARD PROBLEMS SECTION
   * Advanced applications combining multiple concepts
   * These are the types of problems asked in top-tier tech interviews
   */
  static class HardProblems {

    /**
     * PROBLEM 1: Shortest Path with Exactly K Edges
     * Time: O(K * E), Space: O(K * V)
     *
     * Key Insight: DP where dp[k][v] = shortest path to v using exactly k edges
     * Can't use standard Dijkstra because we have exact constraint
     */
    public static int shortestPathKEdges(int n, int[][] edges, int src, int dst, int k) {
      // dp[i][j] = shortest distance to node j using exactly i edges
      int[][] dp = new int[k + 1][n];
      for (int[] row : dp) {
        Arrays.fill(row, Integer.MAX_VALUE);
      }
      dp[0][src] = 0;

      for (int i = 1; i <= k; i++) {
        for (int[] edge : edges) {
          int u = edge[0], v = edge[1], w = edge[2];
          if (dp[i - 1][u] != Integer.MAX_VALUE) {
            dp[i][v] = Math.min(dp[i][v], dp[i - 1][u] + w);
          }
        }
      }

      return dp[k][dst] == Integer.MAX_VALUE ? -1 : dp[k][dst];
    }

    /**
     * PROBLEM 2: Shortest Path with Forbidden Paths
     * Time: O((V + E) log V), Space: O(V)
     *
     * Key Insight: Modified Dijkstra that avoids forbidden nodes/edges
     * Check forbidden set before relaxing edges
     */
    public static int shortestPathAvoidingForbidden(List<List<Prerequisites.Edge>> graph,
        int src, int dst, Set<Integer> forbidden) {
      int n = graph.size();
      int[] dist = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[src] = 0;

      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
      pq.offer(new int[] { 0, src });

      while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int d = current[0], node = current[1];

        if (node == dst)
          return d;
        if (d > dist[node])
          continue;
        if (forbidden.contains(node) && node != src)
          continue; // Skip forbidden nodes

        for (Prerequisites.Edge edge : graph.get(node)) {
          if (!forbidden.contains(edge.to)) { // Avoid forbidden destinations
            int newDist = dist[node] + edge.weight;
            if (newDist < dist[edge.to]) {
              dist[edge.to] = newDist;
              pq.offer(new int[] { newDist, edge.to });
            }
          }
        }
      }

      return dist[dst] == Integer.MAX_VALUE ? -1 : dist[dst];
    }

    /**
     * PROBLEM 3: Shortest Path with Color Constraints
     * Time: O((V + E) log V), Space: O(V)
     *
     * Key Insight: State = (node, last_color_used)
     * Can't use same color consecutively
     */
    public static int shortestPathColorConstraint(int n, int[][][] coloredEdges, int src, int dst) {
      // coloredEdges[i] = [from, to, weight, color]
      List<List<int[]>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }

      for (int[][] edge : coloredEdges) {
        graph.get(edge[0][0]).add(new int[] { edge[0][1], edge[0][2], edge[0][3] }); // to, weight, color
      }

      // State: (distance, node, lastColor)
      // Use -1 for initial state (no last color)
      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
      Map<String, Integer> visited = new HashMap<>();

      pq.offer(new int[] { 0, src, -1 });

      while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int dist = current[0], node = current[1], lastColor = current[2];

        if (node == dst)
          return dist;

        String state = node + "," + lastColor;
        if (visited.containsKey(state))
          continue;
        visited.put(state, dist);

        for (int[] edge : graph.get(node)) {
          int nextNode = edge[0], weight = edge[1], color = edge[2];

          if (color != lastColor) { // Can use this edge
            pq.offer(new int[] { dist + weight, nextNode, color });
          }
        }
      }

      return -1;
    }

    /**
     * PROBLEM 4: Shortest Path in Grid with Obstacles and Keys
     * Time: O(MN * 2^K), Space: O(MN * 2^K) where K is number of keys
     *
     * Key Insight: State = (row, col, keys_bitmask)
     * Use BFS with state compression for keys
     */
    public static int shortestPathWithKeys(String[] grid) {
      int m = grid.length, n = grid[0].length();
      int startRow = 0, startCol = 0, totalKeys = 0;

      // Find start position and count total keys
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          char c = grid[i].charAt(j);
          if (c == '@') {
            startRow = i;
            startCol = j;
          } else if (c >= 'a' && c <= 'f') {
            totalKeys = Math.max(totalKeys, c - 'a' + 1);
          }
        }
      }

      int finalMask = (1 << totalKeys) - 1; // All keys collected
      int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

      // BFS with state: (row, col, keyMask, steps)
      Queue<int[]> queue = new LinkedList<>();
      Set<String> visited = new HashSet<>();

      queue.offer(new int[] { startRow, startCol, 0, 0 });
      visited.add(startRow + "," + startCol + "," + 0);

      while (!queue.isEmpty()) {
        int[] current = queue.poll();
        int row = current[0], col = current[1], keyMask = current[2], steps = current[3];

        if (keyMask == finalMask) {
          return steps; // Collected all keys
        }

        for (int[] dir : directions) {
          int newRow = row + dir[0];
          int newCol = col + dir[1];
          int newKeyMask = keyMask;

          if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n) {
            char c = grid[newRow].charAt(newCol);

            if (c == '#')
              continue; // Wall

            // Check if we can pass through locks
            if (c >= 'A' && c <= 'F') {
              int requiredKey = c - 'A';
              if ((keyMask & (1 << requiredKey)) == 0) {
                continue; // Don't have required key
              }
            }

            // Collect key if present
            if (c >= 'a' && c <= 'f') {
              int keyIndex = c - 'a';
              newKeyMask |= (1 << keyIndex);
            }

            String newState = newRow + "," + newCol + "," + newKeyMask;
            if (!visited.contains(newState)) {
              visited.add(newState);
              queue.offer(new int[] { newRow, newCol, newKeyMask, steps + 1 });
            }
          }
        }
      }

      return -1;
    }

    /**
     * PROBLEM 5: Shortest Path with Time-Based Constraints
     * Time: O((V + E) log V * T), Space: O(V * T)
     *
     * Key Insight: Edges have different weights based on time of day
     * State = (node, time_mod_period)
     */
    public static int shortestPathTimeDependent(int n, int[][][] timeEdges, int src, int dst, int period) {
      // timeEdges[i] = [from, to, [weight_at_time_0, weight_at_time_1, ...]]
      List<List<int[][]>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }

      for (int[][] edge : timeEdges) {
        int from = edge[0][0], to = edge[0][1];
        int[] weights = new int[edge.length - 1];
        for (int i = 1; i < edge.length; i++) {
          weights[i - 1] = edge[i][0];
        }
        graph.get(from).add(new int[][] { new int[] { to }, weights });
      }

      // Dijkstra with time state
      int[][] dist = new int[n][period];
      for (int[] row : dist) {
        Arrays.fill(row, Integer.MAX_VALUE);
      }
      dist[src][0] = 0;

      PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
      pq.offer(new int[] { 0, src, 0 }); // distance, node, time

      while (!pq.isEmpty()) {
        int[] current = pq.poll();
        int d = current[0], node = current[1], time = current[2];

        if (d > dist[node][time])
          continue;

        for (int[][] edge : graph.get(node)) {
          int nextNode = edge[0][0];
          int weight = edge[1][time]; // Weight depends on current time
          int nextTime = (time + 1) % period;
          int newDist = dist[node][time] + weight;

          if (newDist < dist[nextNode][nextTime]) {
            dist[nextNode][nextTime] = newDist;
            pq.offer(new int[] { newDist, nextNode, nextTime });
          }
        }
      }

      // Return minimum distance to destination at any time
      int result = Integer.MAX_VALUE;
      for (int t = 0; t < period; t++) {
        result = Math.min(result, dist[dst][t]);
      }

      return result == Integer.MAX_VALUE ? -1 : result;
    }
  }

  /**
   * MAIN METHOD - COMPREHENSIVE TESTING AND DEMONSTRATION
   */
  public static void main(String[] args) {
    System.out.println("🚀 COMPLETE SHORTEST PATH ALGORITHMS GUIDE 🚀");
    System.out.println("=".repeat(60));

    // 1. Prerequisites Demo
    System.out.println("\n📚 PREREQUISITES DEMONSTRATION:");
    Prerequisites.priorityQueueDemo();

    // 2. Core Algorithm Illustrations
    System.out.println("\n🔬 ALGORITHM DEMONSTRATIONS:");
    Illustrations.demonstrateAllAlgorithms();

    // 3. Dijkstra Step-by-Step
    System.out.println("\n🎯 DIJKSTRA DETAILED WALKTHROUGH:");
    Illustrations.dijkstraStepByStep();

    // 4. Advanced Problem Testing
    System.out.println("\n🔥 ADVANCED PROBLEM SOLUTIONS:");

    // Test minimum effort path
    int[][] heights = { { 1, 2, 2 }, { 3, 8, 2 }, { 5, 3, 5 } };
    int effort = Concepts.minimumEffortPath(heights);
    System.out.println("Minimum effort path: " + effort);

    // Test cheapest flights
    int[][] flights = { { 0, 1, 100 }, { 1, 2, 100 }, { 0, 2, 500 } };
    int cheapest = Concepts.findCheapestPrice(3, flights, 0, 2, 1);
    System.out.println("Cheapest flight price: " + cheapest);

    // Test swim in water
    int[][] water = { { 0, 2 }, { 1, 3 } };
    int swimTime = Concepts.swimInWater(water);
    System.out.println("Minimum time to swim: " + swimTime);

    // Test hard problems
    System.out.println("\n💎 ULTRA-HARD PROBLEM EXAMPLES:");

    // Shortest path with exactly K edges
    int[][] edgesK = { { 0, 1, 3 }, { 1, 2, 1 }, { 0, 2, 5 } };
    int pathK = HardProblems.shortestPathKEdges(3, edgesK, 0, 2, 2);
    System.out.println("Shortest path with exactly 2 edges: " + pathK);

    System.out.println("\n✅ ALL TESTS COMPLETED! You're ready for any shortest path interview question!");

    // Interview Tips
    System.out.println("\n" + "=".repeat(60));
    System.out.println("🎯 INTERVIEW SUCCESS TIPS:");
    System.out.println("1. Always ask about edge weights (negative? zero?)");
    System.out.println("2. Clarify if it's directed or undirected graph");
    System.out.println("3. Check for constraints (memory, time limits)");
    System.out.println("4. Consider if you need all-pairs or single-source shortest paths");
    System.out.println("5. Think about state representation for complex problems");
    System.out.println("6. Master Dijkstra's template - it's used in 70% of problems!");
    System.out.println("7. BFS for unweighted, Dijkstra for weighted, Bellman-Ford for negative weights");
    System.out.println("8. Floyd-Warshall for all-pairs shortest paths");
    System.out.println("=".repeat(60));

    // Common Patterns Summary
    System.out.println("\n🔍 COMMON INTERVIEW PATTERNS:");
    System.out.println("Pattern 1: Grid + Shortest Path → BFS or Dijkstra");
    System.out.println("Pattern 2: Constraints on path → Modified Dijkstra with state");
    System.out.println("Pattern 3: k-stops/k-edges → BFS with level tracking or DP");
    System.out.println("Pattern 4: Multiple sources → Multi-source BFS");
    System.out.println("Pattern 5: Path reconstruction → Keep parent array");
    System.out.println("Pattern 6: Count paths → Modified Dijkstra with counting");
    System.out.println("Pattern 7: Min-max problems → Dijkstra with custom distance function");

    // Time Complexity Cheat Sheet
    System.out.println("\n⏰ TIME COMPLEXITY CHEAT SHEET:");
    System.out.println("BFS (Unweighted): O(V + E)");
    System.out.println("Dijkstra: O((V + E) log V)");
    System.out.println("Bellman-Ford: O(VE)");
    System.out.println("Floyd-Warshall: O(V³)");
    System.out.println("DFS/BFS in Grid: O(MN)");
    System.out.println("Dijkstra in Grid: O(MN log(MN))");

    System.out.println("\n🎉 Happy Coding! You've got this! 💪");
  }
}