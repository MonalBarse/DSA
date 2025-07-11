package com.monal.Revise;

import java.util.*;

public class Revise02 {

  /*
   * =============================================================================
   * GRAPH ALGORITHMS REVISION - PART 2
   * =============================================================================
   *
   * INDEX:
   * 1. Shortest Path Algorithms (Dijkstra, Bellman-Ford)
   * 2. Minimum Spanning Tree (Kruskal, Prim)
   * 3. Advanced Graph Algorithms (Bridges, Articulation Points, Kosaraju)
   * 4. Complex Interview Problems and Patterns
   * =============================================================================
   */

  // =============================
  // 1. SHORTEST PATH ALGORITHMS
  // =============================

  /*
   * SHORTEST PATH PATTERN RECOGNITION:
   *
   * DIJKSTRA'S ALGORITHM:
   * - "Shortest path" + "weighted graph" + "non-negative weights" → Dijkstra
   * - Uses greedy approach: always pick closest unvisited node
   * - Think: "Expanding circle from source"
   *
   * BELLMAN-FORD ALGORITHM:
   * - "Shortest path" + "negative weights allowed" → Bellman-Ford
   * - "Detect negative cycles" → Bellman-Ford
   * - Think: "Relax all edges V-1 times"
   *
   * FLOYD-WARSHALL:
   * - "All pairs shortest path" → Floyd-Warshall
   * - Think: "Try every node as intermediate"
   *
   * KEY INSIGHT: Dijkstra = BFS + Priority Queue (for weighted graphs)
   */

  // DIJKSTRA'S ALGORITHM - Single Source Shortest Path
  public static int[] dijkstra(List<List<Edge>> adj, int source) {
    int n = adj.size();
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[source] = 0;

    // Priority queue: min-heap based on distance
    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.offer(new Node(source, 0));

    while (!pq.isEmpty()) {
      Node current = pq.poll();
      int u = current.vertex;
      int d = current.distance;
      // Skip if we've already found a better path
      if (d > dist[u])
        continue;

      // Explore neighbors
      for (Edge edge : adj.get(u)) {
        int v = edge.to;
        int weight = edge.weight;

        // Relaxation: can we improve the distance to v?
        if (dist[u] + weight < dist[v]) {
          dist[v] = dist[u] + weight;
          pq.offer(new Node(v, dist[v]));
        }
      }
    }

    return dist;
  }

  // BELLMAN-FORD ALGORITHM - Handles negative weights
  public static int[] bellmanFord(List<List<Edge>> adj, int source) {
    int n = adj.size();
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[source] = 0;

    // Relax all edges V-1 times
    // (relax means updating the distance if we find a shorter path)
    for (int i = 0; i < n - 1; i++) {
      // Iterate through all edges
      for (int u = 0; u < n; u++) {
        if (dist[u] == Integer.MAX_VALUE)
          continue;

        for (Edge edge : adj.get(u)) {
          int v = edge.to;
          int weight = edge.weight;

          if (dist[u] + weight < dist[v])
            dist[v] = dist[u] + weight;

        }
      }
    }
    // now after that iteration we have the shortest path from source to all other
    // vertices
    // If we can still relax an edge, it means there's a negative cycle
    for (int u = 0; u < n; u++) {
      if (dist[u] == Integer.MAX_VALUE)
        continue;

      for (Edge edge : adj.get(u)) {
        int v = edge.to;
        int weight = edge.weight;

        if (dist[u] + weight < dist[v])
          return null; // can further relax -> negative cycle
      }
    }

    return dist;
  }

  // FLOYD-WARSHALL - All pairs shortest path
  public static int[][] floydWarshall(int[][] graph) {
    int n = graph.length;
    int[][] dist = new int[n][n];

    // Initialize distances
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        dist[i][j] = graph[i][j];
      }
    }

    // Try each vertex as intermediate
    for (int k = 0; k < n; k++) {
      for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
          if (dist[i][k] != Integer.MAX_VALUE &&
              dist[k][j] != Integer.MAX_VALUE &&
              dist[i][k] + dist[k][j] < dist[i][j]) {
            dist[i][j] = dist[i][k] + dist[k][j];
          }
        }
      }
    }

    return dist;
  }

  // =============================
  // 2. MINIMUM SPANNING TREE
  // =============================

  /*
   * MST PATTERN RECOGNITION:
   *
   * WHEN TO USE MST:
   * - "Connect all nodes with minimum cost" → MST
   * - "Minimum cost to connect cities" → MST
   * - "Network design problems" → MST
   *
   * KRUSKAL'S ALGORITHM:
   * - Think: "Sort edges, add if no cycle"
   * - Uses Union-Find (Disjoint Set) data structure
   * - Good when edges are given as list
   *
   * PRIM'S ALGORITHM:
   * - Think: "Grow tree from a vertex"
   * - Uses priority queue like Dijkstra
   * - Good when adjacency list is given
   */

  // Edge class for MST
  static class MSTEdge implements Comparable<MSTEdge> {
    int from, to, weight;

    MSTEdge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }

    @Override
    public int compareTo(MSTEdge other) {
      return Integer.compare(this.weight, other.weight);
    }
  }

  // UNION-FIND DATA STRUCTURE
  static class UnionFind {
    int[] parent, rank, size;

    UnionFind(int n) {
      parent = new int[n];
      rank = new int[n];
      size = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        rank[i] = 0;
        size[i] = 1; // Size of each set is initially 1
      }
    }

    int find(int x) {
      if (parent[x] != x) {
        parent[x] = find(parent[x]); // Path compression
      }
      return parent[x];
    }

    boolean unionByRank(int x, int y) {
      int px = find(x), py = find(y);
      if (px == py)
        return false; // Already in same set

      // Union by rank
      if (rank[px] < rank[py]) {
        parent[px] = py;
      } else if (rank[px] > rank[py]) {
        parent[py] = px;
      } else {
        parent[py] = px;
        rank[px]++;
      }
      return true;
    }

    boolean unionBySize(int x, int y) {
      int px = find(x), py = find(y);
      if (px == py)
        return false;
      if (size[px] < size[py]) {
        parent[px] = py;
        size[py] += size[px]; // Update size of new root
      } else if (size[px] > size[py]) {
        parent[py] = px;
        size[px] += size[py]; // Update size of new root
      } else {
        parent[py] = px;
        size[px] += size[py]; // Update size of new root
      }
      return true;
    }

    boolean isConnected(int x, int y) {
      return find(x) == find(y);
    }

    int getSize(int x) {
      return size[find(x)]; // Return size of the set containing x
    }

    int getRank(int x) {
      return rank[find(x)]; // Return rank of the set containing x
    }
  }

  // ======= PRIM'S ALGORITHM ========== //
  /*
   * PRIM'S APPROACH - "Vertex-Centric"
   * Philosophy:
   * "Grow the MST one vertex at a time, always picking the cheapest expansion"
   *
   * STRATEGY:
   * - Start from any vertex (usually 0)
   * - Maintain a "cut" between MST vertices and non-MST vertices
   * - Always add the minimum weight edge that crosses this cut
   * - Use priority queue to efficiently find minimum crossing edge
   *
   * MENTAL MODEL: "Blob Growing"
   * - Start with one vertex as your "blob"
   * - Always expand the blob by adding the closest neighboring vertex
   * - The "closest" means minimum weight edge from blob to outside
   * - Keep growing until blob includes all vertices
   *
   * WHEN TO USE:
   * - Dense graphs (more edges)
   * - When graph is given as adjacency list
   * - When you want to build MST incrementally from a starting point
   *
   * TIME: O(E log V) - each edge can be added/updated in priority queue
   * SPACE: O(V + E) - adjacency list + priority queue + tracking arrays
   */

  public static List<MSTEdge> primMST(List<List<Edge>> adj) {
    int n = adj.size();
    boolean[] inMST = new boolean[n]; // Track which vertices are in MST
    int[] key = new int[n]; // Minimum weight to reach each vertex
    int[] parent = new int[n]; // Parent in MST
    Arrays.fill(key, Integer.MAX_VALUE);
    Arrays.fill(parent, -1);

    key[0] = 0; // Start from vertex 0
    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.offer(new Node(0, 0));
    List<MSTEdge> mst = new ArrayList<>();

    while (!pq.isEmpty()) {
      Node current = pq.poll();
      int u = current.vertex;

      if (inMST[u])
        continue; // Already processed
      inMST[u] = true; // Add to MST

      // Add edge to MST (except for the first vertex)
      if (parent[u] != -1)
        mst.add(new MSTEdge(parent[u], u, key[u]));

      // Update keys of adjacent vertices (expand the "cut")
      for (Edge edge : adj.get(u)) {
        int v = edge.to;
        int weight = edge.weight;
        if (!inMST[v] && weight < key[v]) {
          key[v] = weight;
          parent[v] = u;
          pq.offer(new Node(v, key[v]));
        }
      }
    }
    return mst;
  }

  // ======= KRUSKAL'S ALGORITHM ========== //
  /*
   * KRUSKAL'S APPROACH - "Edge-Centric"
   * Philosophy: "Pick the globally cheapest edge that doesn't create a cycle"
   *
   * STRATEGY:
   * - Sort ALL edges by weight (global view)
   * - Use Union-Find to detect cycles
   * - Greedily add edges to MST until we have n-1 edges
   * - Works on disconnected components simultaneously
   *
   * MENTAL MODEL: "Forest Growing"
   * - Start with n individual trees (each vertex is its own tree)
   * - Keep connecting trees with cheapest possible edges
   * - Union-Find tracks which vertices belong to which tree
   * - Stop when all trees merge into one
   *
   * WHEN TO USE:
   * - Sparse graphs (fewer edges)
   * - When edges are given as a list
   * - When you need to process edges in weight order
   *
   * TIME: O(E log E) - dominated by sorting edges
   * SPACE: O(E) - for storing edges + O(V) for Union-Find
   */
  public static List<MSTEdge> kruskalMST(int n, List<MSTEdge> edges) {
    // Sort edges by weight - this is the key step
    Collections.sort(edges);
    UnionFind uf = new UnionFind(n);
    List<MSTEdge> mst = new ArrayList<>();

    for (MSTEdge edge : edges) {
      // Try to add this edge - only succeeds if it doesn't create cycle
      if (uf.unionByRank(edge.from, edge.to)) {
        mst.add(edge);
        if (mst.size() == n - 1)
          break; // MST complete
      }
    }
    return mst;
  }

  /*
   * ======= KEY DIFFERENCES SUMMARY =======
   *
   * APPROACH:
   * - Kruskal: "Edge-first" - considers all edges globally, picks cheapest
   * - Prim: "Vertex-first" - grows MST locally, always expands from current MST
   *
   * DATA STRUCTURE:
   * - Kruskal: Union-Find (to detect cycles)
   * - Prim: Priority Queue (to find minimum crossing edge)
   *
   * PROCESSING:
   * - Kruskal: Processes edges in weight order
   * - Prim: Processes vertices in order of minimum distance from MST
   *
   * GRAPH REPRESENTATION:
   * - Kruskal: Works well with edge list
   * - Prim: Works well with adjacency list
   *
   * PERFORMANCE:
   * - Kruskal: O(E log E) - better for sparse graphs
   * - Prim: O(E log V) - better for dense graphs
   *
   * PARALLELIZATION:
   * - Kruskal: Harder to parallelize (global sorting)
   * - Prim: Easier to parallelize (local decisions)
   *
   * MEMORY:
   * - Kruskal: O(E) for edges + O(V) for Union-Find
   * - Prim: O(V + E) for adjacency list + O(V) for priority queue
   *
   * INTUITION:
   * - Kruskal: "Connect forests with cheapest bridges"
   * - Prim: "Grow a tree by always adding the closest vertex"
   */

  // =============================
  // 3. ADVANCED GRAPH ALGORITHMS
  // =============================

  /*
   * ADVANCED ALGORITHMS PATTERN RECOGNITION:
   *
   * BRIDGES:
   * - "Critical connections" → Bridges
   * - "Removing which edge disconnects graph" → Bridges
   * - Uses DFS + Low-Link values
   *
   * ARTICULATION POINTS:
   * - "Critical vertices" → Articulation Points
   * - "Removing which vertex disconnects graph" → Articulation Points
   * - Uses DFS + Low-Link values
   *
   * STRONGLY CONNECTED COMPONENTS (SCC):
   * - "Groups where every vertex can reach every other" → SCC
   * - "Kosaraju's Algorithm" → Two DFS passes
   *
   * KEY INSIGHT: All use DFS with additional bookkeeping
   */

  // TARJAN'S ALGORITHM for finding bridges
  public static List<int[]> findBridges(List<List<Integer>> adj) {
    int n = adj.size();
    boolean[] visited = new boolean[n];
    int[] disc = new int[n]; // Discovery time
    int[] low = new int[n]; // Low-link value
    int[] parent = new int[n];
    List<int[]> bridges = new ArrayList<>();

    Arrays.fill(parent, -1);
    int[] time = { 0 }; // Using array to pass by reference

    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        bridgesDFS(adj, i, visited, disc, low, parent, bridges, time);
      }
    }

    return bridges;
  }

  private static void bridgesDFS(List<List<Integer>> adj, int u, boolean[] visited,
      int[] disc, int[] low, int[] parent,
      List<int[]> bridges, int[] time) {
    visited[u] = true;
    disc[u] = low[u] = time[0]++;

    for (int v : adj.get(u)) {
      if (!visited[v]) {
        parent[v] = u;
        bridgesDFS(adj, v, visited, disc, low, parent, bridges, time);

        // Update low-link value
        low[u] = Math.min(low[u], low[v]);

        // Check if edge (u, v) is a bridge
        if (low[v] > disc[u]) {
          bridges.add(new int[] { u, v });
        }
      } else if (v != parent[u]) {
        // Back edge
        low[u] = Math.min(low[u], disc[v]);
      }
    }
  }

  // TARJAN'S ALGORITHM for finding articulation points
  public static List<Integer> findArticulationPoints(List<List<Integer>> adj) {
    int n = adj.size();
    boolean[] visited = new boolean[n];
    int[] disc = new int[n];
    int[] low = new int[n];
    int[] parent = new int[n];
    boolean[] isAP = new boolean[n];

    Arrays.fill(parent, -1);
    int[] time = { 0 };

    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        articulationDFS(adj, i, visited, disc, low, parent, isAP, time);
      }
    }

    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (isAP[i])
        result.add(i);
    }

    return result;
  }

  private static void articulationDFS(List<List<Integer>> adj, int u, boolean[] visited,
      int[] disc, int[] low, int[] parent,
      boolean[] isAP, int[] time) {
    int children = 0;
    visited[u] = true;
    disc[u] = low[u] = time[0]++;

    for (int v : adj.get(u)) {
      if (!visited[v]) {
        children++;
        parent[v] = u;
        articulationDFS(adj, v, visited, disc, low, parent, isAP, time);

        low[u] = Math.min(low[u], low[v]);

        // Check articulation point conditions
        if (parent[u] == -1 && children > 1) {
          isAP[u] = true; // Root with multiple children
        }
        if (parent[u] != -1 && low[v] >= disc[u]) {
          isAP[u] = true; // Non-root vertex
        }
      } else if (v != parent[u]) {
        low[u] = Math.min(low[u], disc[v]);
      }
    }
  }

  // KOSARAJU'S ALGORITHM for Strongly Connected Components
  public static List<List<Integer>> findSCCs(List<List<Integer>> adj) {
    int n = adj.size();

    // Step 1: Get finishing order using DFS
    Stack<Integer> stack = new Stack<>();
    boolean[] visited = new boolean[n];

    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        dfsFinishingOrder(adj, i, visited, stack);
      }
    }

    // Step 2: Create transpose graph
    List<List<Integer>> transpose = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      transpose.add(new ArrayList<>());
    }

    for (int u = 0; u < n; u++) {
      for (int v : adj.get(u)) {
        transpose.get(v).add(u);
      }
    }

    // Step 3: DFS on transpose in reverse finishing order
    Arrays.fill(visited, false);
    List<List<Integer>> sccs = new ArrayList<>();

    while (!stack.isEmpty()) {
      int v = stack.pop();
      if (!visited[v]) {
        List<Integer> scc = new ArrayList<>();
        dfsCollectSCC(transpose, v, visited, scc);
        sccs.add(scc);
      }
    }

    return sccs;
  }

  private static void dfsFinishingOrder(List<List<Integer>> adj, int u,
      boolean[] visited, Stack<Integer> stack) {
    visited[u] = true;

    for (int v : adj.get(u)) {
      if (!visited[v]) {
        dfsFinishingOrder(adj, v, visited, stack);
      }
    }

    stack.push(u); // Push after processing all neighbors
  }

  private static void dfsCollectSCC(List<List<Integer>> adj, int u,
      boolean[] visited, List<Integer> scc) {
    visited[u] = true;
    scc.add(u);

    for (int v : adj.get(u)) {
      if (!visited[v]) {
        dfsCollectSCC(adj, v, visited, scc);
      }
    }
  }

  // =============================
  // 4. COMPLEX INTERVIEW PROBLEMS
  // =============================

  /*
   * COMPLEX PROBLEM PATTERNS:
   *
   * "Network Delay Time" → Dijkstra's shortest path
   * "Cheapest Flights Within K Stops" → Modified Dijkstra/Bellman-Ford
   * "Critical Connections" → Tarjan's bridges algorithm
   * "Minimum Cost to Connect All Points" → MST (Prim's/Kruskal's)
   * "Accounts Merge" → Union-Find
   * "Alien Dictionary" → Topological Sort
   */

  // PROBLEM: Network Delay Time (Dijkstra application)
  /*
   * Given a list `times` where times[i] = [u, v, w] means a signal takes
   * `w` ms to travel from node `u` to node `v`.
   * Return the distance of the longest time it takes for a signal to reach out of
   * all nodes starting from node `k`.
   * If it is impossible for all nodes to be reached, return -1.
   */
  public static int networkDelayTime(int[][] times, int n, int k) {
    // Build adjacency list
    List<List<Edge>> adj = new ArrayList<>();
    for (int i = 0; i <= n; i++)
      adj.add(new ArrayList<>());
    for (int[] time : times)
      adj.get(time[0]).add(new Edge(time[1], time[2]));

    // Run Dijkstra
    int[] dist = dijkstra(adj, k);
    // Find maximum distance
    int maxDist = 0;
    for (int i = 1; i <= n; i++) {
      if (dist[i] == Integer.MAX_VALUE)
        return -1; // Some node unreachable
      maxDist = Math.max(maxDist, dist[i]);
    }
    return maxDist;
  }

  // PROBLEM: Cheapest Flights Within K Stops (Modified Dijkstra)
  /*
   * Given a list of flights where flights[i] = [u, v, w] means there is a flight
   * from city `u` to city `v` with cost `w`.
   * Return the cheapest price to travel from city `src` to city `dst` with
   * at most `k` stops. If there is no such route, return -1.
   * Note: A flight with 0 stops is a direct flight.
   */

  public class Flight {
    int to;
    int price;

    public Flight(int to, int price) {
      this.to = to;
      this.price = price;
    }
  }

  public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
    // adjacency list
    List<List<Flight>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] flight : flights) {
      int from = flight[0], to = flight[1], price = flight[2];
      adj.get(from).add(new Flight(to, price));
    }
    // minimum cost to reach each node in up to k stops
    int[] minCost = new int[n];
    Arrays.fill(minCost, Integer.MAX_VALUE);
    minCost[src] = 0;

    Queue<int[]> queue = new ArrayDeque<>();
    queue.offer(new int[] { src, 0, 0 }); // [node, costs, stops]
    while (!queue.isEmpty()) {
      int[] curr = queue.poll();
      int currentNode = curr[0], costSoFar = curr[1], stopsSoFar = curr[2];

      if (stopsSoFar > k)
        continue;

      for (Flight neighbor : adj.get(currentNode)) {
        int nextNode = neighbor.to;
        int totalCost = costSoFar + neighbor.price;

        // Important: Only push if this path is better for now
        if (totalCost < minCost[nextNode]) {
          minCost[nextNode] = totalCost;
          queue.offer(new int[] { nextNode, totalCost, stopsSoFar + 1 });
        }
      }
    }

    return minCost[dst] == Integer.MAX_VALUE ? -1 : minCost[dst];
  }

  // =============================
  // 5. BOILERPLATE TEMPLATES
  // =============================

  /*
   * =============================================================================
   * ADVANCED BOILERPLATE TEMPLATES
   * =============================================================================
   */

  // Template 1: Dijkstra's Algorithm
  public static int[] dijkstraTemplate(List<List<Edge>> adj, int source) {
    int n = adj.size();
    int[] dist = new int[n];
    Arrays.fill(dist, Integer.MAX_VALUE);
    dist[source] = 0;

    PriorityQueue<Node> pq = new PriorityQueue<>();
    pq.offer(new Node(source, 0));

    while (!pq.isEmpty()) {
      Node current = pq.poll();
      int u = current.vertex;

      if (current.distance > dist[u])
        continue;

      for (Edge edge : adj.get(u)) {
        int v = edge.to;
        if (dist[u] + edge.weight < dist[v]) {
          dist[v] = dist[u] + edge.weight;
          pq.offer(new Node(v, dist[v]));
        }
      }
    }

    return dist;
  }

  // Template 2: Union-Find
  public static class UnionFindTemplate {
    int[] parent, rank;

    public UnionFindTemplate(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++)
        parent[i] = i;
    }

    public int find(int node) {
      if(parent[node] == node) return node;
      // Path compression
      parent[node] = find(parent[node]);
      return parent[node];
    }

    public boolean union(int x, int y) {
      int px = find(x), py = find(y);
      if (px == py)
        return false;

      if (rank[px] < rank[py])
        parent[px] = py;
      else if (rank[px] > rank[py])
        parent[py] = px;
      else {
        parent[py] = px;
        rank[px]++;
      }

      return true;
    }
  }

  // Template 3: Kruskal's MST
  public static List<MSTEdge> kruskalTemplate(int n, List<MSTEdge> edges) {
    Collections.sort(edges);
    UnionFindTemplate uf = new UnionFindTemplate(n);
    List<MSTEdge> mst = new ArrayList<>();

    for (MSTEdge edge : edges) {
      if (uf.union(edge.from, edge.to)) {
        mst.add(edge);
        if (mst.size() == n - 1)
          break;
      }
    }

    return mst;
  }

  /*
   * =============================================================================
   * ADVANCED PATTERN RECOGNITION CHEAT SHEET
   * =============================================================================
   *
   * WHEN YOU SEE → THINK
   *
   * "Shortest path" + "weighted" + "non-negative" → Dijkstra
   * "Shortest path" + "negative weights" → Bellman-Ford
   * "All pairs shortest path" → Floyd-Warshall
   * "Minimum cost to connect all" → MST (Kruskal/Prim)
   * "Critical connections" → Bridges (Tarjan)
   * "Critical vertices" → Articulation Points (Tarjan)
   * "Strongly connected components" → Kosaraju
   * "Network delay" → Dijkstra
   * "Cheapest flights with stops" → Modified Dijkstra
   * "Union two groups" → Union-Find
   *
   * COMPLEXITY QUICK REFERENCE:
   * - Dijkstra: O(E log V)
   * - Bellman-Ford: O(VE)
   * - Floyd-Warshall: O(V³)
   * - Kruskal: O(E log E)
   * - Prim: O(E log V)
   * - Tarjan (Bridges/APs): O(V + E)
   * - Kosaraju: O(V + E)
   *
   * =============================================================================
   */

  // Example usage
  public static void main(String[] args) {
    // Test Dijkstra
    List<List<Edge>> adj = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      adj.add(new ArrayList<>());
    }

    // Add some edges
    adj.get(0).add(new Edge(1, 4));
    adj.get(0).add(new Edge(2, 1));
    adj.get(1).add(new Edge(3, 1));
    adj.get(2).add(new Edge(1, 2));
    adj.get(2).add(new Edge(3, 5));
    adj.get(3).add(new Edge(4, 3));

    int[] distances = dijkstra(adj, 0);
    System.out.println("Shortest distances from 0: " + Arrays.toString(distances));

    // Test Union-Find
    UnionFindTemplate uf = new UnionFindTemplate(5);
    uf.union(0, 1);
    uf.union(2, 3);

    System.out.println("0 and 1 connected: " + (uf.find(0) == uf.find(1)));
    System.out.println("0 and 2 connected: " + (uf.find(0) == uf.find(2)));
  }

  // Edge class for weighted graphs
  static class Edge {
    int to, weight;

    Edge(int to, int weight) {
      this.to = to;
      this.weight = weight;
    }
  }

  // Node class for Dijkstra's algorithm
  static class Node implements Comparable<Node> {
    int vertex, distance;

    Node(int vertex, int distance) {
      this.vertex = vertex;
      this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
      return Integer.compare(this.distance, other.distance);
    }
  }
}