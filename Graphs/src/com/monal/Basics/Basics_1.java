package com.monal.Basics;

import java.util.*;

/*
 FUNDAMENTALS OF GRAPHS
 These are the core graph problems and concepts
 --------------------------------------
 * 1. Find Path Existence (DFS/BFS)
 * 2. Count Connected Components
 * 3. Detect Cycle in Graph
 * 4. Find Shortest Path in Unweighted Graph
 * 5. Topological Sort (DFS/BFS)
 * 6. Check Bipartiteness
 * 7. Find All Paths Between Two Nodes
 * 8. Clone Graph (Deep Copy)
 * 9. Minimum Spanning Tree (Kruskal's Algorithm)
 * 10. Union-Find for Connectivity
 */

/*
 *
 * 1. ALWAYS ASK CLARIFYING QUESTIONS:
 * - Directed or undirected?
 * - Weighted or unweighted?
 * - Can there be self-loops?
 * - Are nodes 0-indexed or 1-indexed?
 *
 * 2. CONSIDER EDGE CASES:
 * - Empty graph
 * - Single node
 * - Disconnected components
 * - Source = destination
 *
 * 3. OPTIMIZATION OPPORTUNITIES:
 * - Early termination in path finding
 * - Bidirectional BFS for shortest path
 * - Union-Find for connectivity queries
 *
 * 4. COMMON PITFALLS:
 * - Not handling disconnected nodes
 * - Infinite loops due to cycles (always use visited set)
 * - Confusing DFS and BFS use cases
 * - Off-by-one errors in distance calculation
 *
 * 5. SPACE-TIME TRADEOFFS:
 * - DFS: O(V) extra space, risk of stack overflow
 * - BFS: O(V) extra space, no stack overflow risk
 * - Adjacency List vs Matrix based on graph density
 * - Time complexity: O(V + E) for most traversal algorithms
 * - Space complexity: O(V) for visited array + O(V) for recursion stack in DFS
 *
 */
public class Basics_1 {

  // =================== PROBLEM 1: PATH EXISTENCE ===================

  /*
   * PROBLEM: Given a graph and two nodes, determine if a path exists between them
   *
   * APPROACH 1: DFS (Depth-First Search)
   * - Time: O(V + E), Space: O(V) for visited + O(V) for recursion stack
   * - Good for: When you want to explore as far as possible
   * - Interview Tip: Easier to implement recursively, but watch stack overflow
   */
  public static boolean hasPathDFS(Map<Integer, List<Integer>> graph, int start, int end) {
    if (start == end)
      return true; // Edge case: same node

    Set<Integer> visited = new HashSet<>();
    return pathDFSHelper(graph, start, end, visited);
  }

  /*
   * If we reach target node return true;
   * If not first add current node to visited set to avoid cycles
   * Explore all neighbors:
   * - If neighbor is not visited, recursively call DFS on it
   * - If any recursive call returns true (path found), return true
   * If no path is found through any neighbor, return false
   */
  private static boolean pathDFSHelper(Map<Integer, List<Integer>> graph, int current, int target,
      Set<Integer> visited) {
    if (current == target)
      return true;

    visited.add(current); // Mark as visited to avoid cycles

    // Explore all neighbors
    for (int neighbor : graph.get(current)) {
      if (!visited.contains(neighbor)) {
        if (pathDFSHelper(graph, neighbor, target, visited)) {
          return true; // Found path through this neighbor
        }
      }
    }

    return false; // No path found through any neighbor
  }

  /*
   * APPROACH 2: BFS (Breadth-First Search)
   * - Time: O(V + E), Space: O(V) for visited + O(V) for queue
   * - Good for: Finding shortest path, level-order exploration
   * - Interview Tip: Use Queue, not recursion - no stack overflow risk
   */
  public static boolean hasPathBFS(Map<Integer, List<Integer>> graph, int start, int end) {
    if (start == end)
      return true;

    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();

    queue.offer(start);
    visited.add(start);

    while (!queue.isEmpty()) {
      int current = queue.poll();

      // Check all neighbors
      for (int neighbor : graph.get(current)) {
        if (neighbor == end)
          return true; // Found target

        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          queue.offer(neighbor);
        }
      }
    }

    return false; // Exhausted all reachable nodes
  }

  // =================== PROBLEM 2: COUNT CONNECTED COMPONENTS ===================

  /*
   * PROBLEM: Count the number of connected components in an undirected graph
   *
   * KEY INSIGHT: Each DFS/BFS traversal covers exactly one connected component
   *
   * ALGORITHM:
   * 1. Iterate through all nodes
   * 2. If unvisited, start new DFS/BFS (new component found)
   * 3. Mark all reachable nodes as visited
   * 4. Increment component count
   *
   * COMMON MISTAKE: Forgetting to handle disconnected nodes (nodes with no edges)
   */
  public static int countConnectedComponents(Map<Integer, List<Integer>> graph) {
    Set<Integer> visited = new HashSet<>();
    int components = 0;

    // Check all nodes in the graph
    for (int node : graph.keySet()) {
      if (!visited.contains(node)) {
        // Found new component - explore it completely
        dfsMarkComponent(graph, node, visited);
        components++;
      }
    }

    return components;
  }

  // Helper to mark all nodes in current component as visited
  private static void dfsMarkComponent(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited) {
    visited.add(node);

    for (int neighbor : graph.get(node)) {
      if (!visited.contains(neighbor)) {
        dfsMarkComponent(graph, neighbor, visited);
      }
    }
  }

  /*
   * UNION-FIND ALTERNATIVE (Advanced):
   * - More efficient for dynamic connectivity queries
   * - Time: O(α(n)) per operation where α is inverse Ackermann function
   * - Space: O(V)
   * - Interview Tip: Mention this as optimization, implement if asked
   */

  // =================== PROBLEM 3: CYCLE DETECTION ===================

  /*
   * PROBLEM: Detect if there's a cycle in the graph
   *
   * UNDIRECTED GRAPH: Use DFS with parent tracking
   * - If we visit a node that's already visited AND it's not our parent, cycle
   * found
   *
   * DIRECTED GRAPH: Use DFS with 3 states (white/gray/black)
   * - White: unvisited, Gray: currently processing, Black: completely processed
   * - Cycle exists if we encounter a gray node during DFS
   */

  // UNDIRECTED GRAPH CYCLE DETECTION
  public static boolean hasCycleUndirected(Map<Integer, List<Integer>> graph) {
    Set<Integer> visited = new HashSet<>();

    // Check each component separately
    for (int node : graph.keySet()) {
      if (!visited.contains(node)) {
        if (dfsCheckCycleUndirected(graph, node, -1, visited)) {
          return true;
        }
      }
    }

    return false;
  }

  private static boolean dfsCheckCycleUndirected(Map<Integer, List<Integer>> graph, int current, int parent,
      Set<Integer> visited) {
    visited.add(current);

    for (int neighbor : graph.get(current)) {
      if (neighbor == parent)
        continue; // Skip parent to avoid false positive

      if (visited.contains(neighbor)) {
        return true; // Back edge found - cycle detected
      }

      if (dfsCheckCycleUndirected(graph, neighbor, current, visited)) {
        return true;
      }
    }

    return false;
  }

  // DIRECTED GRAPH CYCLE DETECTION
  public static boolean hasCycleDirected(Map<Integer, List<Integer>> graph) {
    Set<Integer> visited = new HashSet<>(); // Completely processed (black)
    Set<Integer> recursionStack = new HashSet<>(); // Currently processing (gray)

    for (int node : graph.keySet()) {
      if (!visited.contains(node)) {
        if (dfsCheckCycleDirected(graph, node, visited, recursionStack)) {
          return true;
        }
      }
    }

    return false;
  }

  private static boolean dfsCheckCycleDirected(Map<Integer, List<Integer>> graph, int current,
      Set<Integer> visited, Set<Integer> recursionStack) {
    recursionStack.add(current); // Mark as currently processing

    for (int neighbor : graph.get(current)) {
      if (recursionStack.contains(neighbor)) {
        return true; // Back edge in directed graph - cycle found
      }

      if (!visited.contains(neighbor) &&
          dfsCheckCycleDirected(graph, neighbor, visited, recursionStack)) {
        return true;
      }
    }

    recursionStack.remove(current); // Remove from recursion stack
    visited.add(current); // Mark as completely processed
    return false;
  }

  // =================== PROBLEM 4: SHORTEST PATH (UNWEIGHTED) ===================

  /*
   * PROBLEM: Find shortest path in unweighted graph
   *
   * KEY INSIGHT: BFS naturally finds shortest path in unweighted graphs
   * - Each level in BFS represents distance from source
   * - First time we reach target = shortest distance
   *
   * VARIATIONS:
   * 1. Return distance only
   * 2. Return actual path
   * 3. All shortest paths from source (single-source shortest path)
   */

  // Return shortest distance (-1 if no path exists)
  public static int shortestDistance(Map<Integer, List<Integer>> graph, int start, int end) {
    if (start == end)
      return 0;

    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();

    queue.offer(start);
    visited.add(start);
    int distance = 0;

    while (!queue.isEmpty()) {
      int size = queue.size();
      distance++; // Increment distance for next level

      // Process all nodes at current level
      for (int i = 0; i < size; i++) {
        int current = queue.poll();

        for (int neighbor : graph.get(current)) {
          if (neighbor == end)
            return distance; // Found target

          if (!visited.contains(neighbor)) {
            visited.add(neighbor);
            queue.offer(neighbor);
          }
        }
      }
    }

    return -1; // No path exists
  }

  // Return actual shortest path
  public static List<Integer> shortestPath(Map<Integer, List<Integer>> graph, int start, int end) {
    if (start == end)
      return Arrays.asList(start);

    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();
    Map<Integer, Integer> parent = new HashMap<>();

    queue.offer(start);
    visited.add(start);
    parent.put(start, -1); // Mark start's parent as -1

    while (!queue.isEmpty()) {
      int current = queue.poll();

      for (int neighbor : graph.get(current)) {
        if (neighbor == end) {
          // Reconstruct path
          parent.put(neighbor, current);
          return reconstructPath(parent, start, end);
        }

        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          parent.put(neighbor, current);
          queue.offer(neighbor);
        }
      }
    }

    return new ArrayList<>(); // No path exists
  }

  @SuppressWarnings("unused")
  private static List<Integer> reconstructPath(Map<Integer, Integer> parent, int start, int end) {
    List<Integer> path = new ArrayList<>();
    int current = end;

    // Trace back from end to start
    while (current != -1) {
      path.add(current);
      current = parent.get(current);
    }

    Collections.reverse(path); // Reverse to get start -> end path
    return path;
  }

  // All shortest distances from source (single-source shortest path)
  public static Map<Integer, Integer> allShortestDistances(Map<Integer, List<Integer>> graph, int start) {
    Map<Integer, Integer> distances = new HashMap<>();
    Set<Integer> visited = new HashSet<>();
    Queue<Integer> queue = new LinkedList<>();

    queue.offer(start);
    visited.add(start);
    distances.put(start, 0);

    while (!queue.isEmpty()) {
      int current = queue.poll();
      int currentDistance = distances.get(current);

      for (int neighbor : graph.get(current)) {
        if (!visited.contains(neighbor)) {
          visited.add(neighbor);
          distances.put(neighbor, currentDistance + 1);
          queue.offer(neighbor);
        }
      }
    }

    return distances;
  }

  // =================== GRAPH REPRESENTATION ===================

  /*
   * CRITICAL DECISION: How to represent the graph?
   *
   * Adjacency List vs Adjacency Matrix:
   * - Adjacency List: Better for sparse graphs, O(V + E) space
   * - Adjacency Matrix: Better for dense graphs, O(V²) space, O(1) edge lookup
   *
   * For interviews, adjacency list is more common due to real-world sparsity
   */

  // Method to build adjacency list from edge list (most common interview format)
  public static Map<Integer, List<Integer>> buildGraph(int[][] edges, int n) {
    Map<Integer, List<Integer>> graph = new HashMap<>();

    // Initialize all nodes (handle disconnected nodes)
    for (int i = 0; i < n; i++) {
      graph.put(i, new ArrayList<>());
    }

    // Add edges (assuming undirected graph)
    for (int[] edge : edges) {
      graph.get(edge[0]).add(edge[1]);
      graph.get(edge[1]).add(edge[0]); // Remove this line for directed graphs
    }

    return graph;
  }

  // ==============================================================================
  // //
  // ================ TOPOLOGICAL SORT (Directed Acyclic Graph)
  // =================== //
  // ==============================================================================
  // //

  // DFS-based topological sort
  public static List<Integer> topologicalSortDFS(Map<Integer, List<Integer>> graph) {
    Set<Integer> visited = new HashSet<>();
    Stack<Integer> stack = new Stack<>();

    for (int node : graph.keySet()) {
      if (!visited.contains(node)) {
        topoDFS(graph, node, visited, stack);
      }
    }

    List<Integer> result = new ArrayList<>();
    while (!stack.isEmpty()) {
      result.add(stack.pop());
    }
    return result;
  }

  private static void topoDFS(Map<Integer, List<Integer>> graph, int node, Set<Integer> visited, Stack<Integer> stack) {
    visited.add(node);
    for (int neighbor : graph.get(node)) {
      if (!visited.contains(neighbor)) {
        topoDFS(graph, neighbor, visited, stack);
      }
    }
    stack.push(node); // Add to stack after processing all neighbors
  }

  // Kahn's Algorithm (BFS-based)
  public static List<Integer> topologicalSortKahn(Map<Integer, List<Integer>> graph, int[] indegree) {
    Queue<Integer> queue = new LinkedList<>();
    List<Integer> result = new ArrayList<>();

    // Add all nodes with 0 indegree
    for (int i = 0; i < indegree.length; i++) {
      if (indegree[i] == 0) {
        queue.offer(i);
      }
    }

    while (!queue.isEmpty()) {
      int current = queue.poll();
      result.add(current);

      for (int neighbor : graph.get(current)) {
        indegree[neighbor]--;
        if (indegree[neighbor] == 0) {
          queue.offer(neighbor);
        }
      }
    }

    return result.size() == graph.size() ? result : new ArrayList<>(); // Empty if cycle exists
  }

  // ====================================================================== //
  // ====================== BIPARTITE GRAPH CHECK ======================== //
  // ====================================================================== //

  public static boolean isBipartite(Map<Integer, List<Integer>> graph) {
    Map<Integer, Integer> color = new HashMap<>();

    for (int node : graph.keySet()) {
      if (!color.containsKey(node)) {
        if (!dfsColorBipartite(graph, node, 0, color)) {
          return false;
        }
      }
    }
    return true;
  }

  private static boolean dfsColorBipartite(Map<Integer, List<Integer>> graph, int node, int c,
      Map<Integer, Integer> color) {
    color.put(node, c);

    for (int neighbor : graph.get(node)) {
      if (color.containsKey(neighbor)) {
        if (color.get(neighbor) == c)
          return false; // Same color as current
      } else {
        if (!dfsColorBipartite(graph, neighbor, 1 - c, color)) {
          return false;
        }
      }
    }
    return true;
  }

  // ====================================================================== //
  // --------------- Find all paths between two nodes (DFS) --------------- //
  // ====================================================================== //
  public static List<List<Integer>> findAllPaths(Map<Integer, List<Integer>> graph, int start, int end) {
    List<List<Integer>> allPaths = new ArrayList<>();
    List<Integer> currentPath = new ArrayList<>();
    Set<Integer> visited = new HashSet<>();

    currentPath.add(start);
    dfsAllPaths(graph, start, end, currentPath, allPaths, visited);
    return allPaths;
  }

  private static void dfsAllPaths(Map<Integer, List<Integer>> graph, int current, int target,
      List<Integer> path, List<List<Integer>> allPaths, Set<Integer> visited) {
    if (current == target) {
      allPaths.add(new ArrayList<>(path));
      return;
    }

    visited.add(current);
    for (int neighbor : graph.get(current)) {
      if (!visited.contains(neighbor)) {
        path.add(neighbor);
        dfsAllPaths(graph, neighbor, target, path, allPaths, visited);
        path.remove(path.size() - 1); // Backtrack
      }
    }
    visited.remove(current); // Backtrack visited for other paths
  }

  // ====================================================================== //
  // --------------------- Clone Graph (Deep Copy) ------------------------ //
  // ====================================================================== //

  public static class GraphNode {
    int val;
    List<GraphNode> neighbors;

    GraphNode(int val) {
      this.val = val;
      this.neighbors = new ArrayList<>();
    }
  }

  public static GraphNode cloneGraph(GraphNode node) {
    if (node == null)
      return null;

    Map<GraphNode, GraphNode> visited = new HashMap<>();
    return dfsClone(node, visited);
  }

  private static GraphNode dfsClone(GraphNode node, Map<GraphNode, GraphNode> visited) {
    if (visited.containsKey(node)) {
      return visited.get(node);
    }

    GraphNode clone = new GraphNode(node.val);
    visited.put(node, clone);

    for (GraphNode neighbor : node.neighbors) {
      clone.neighbors.add(dfsClone(neighbor, visited));
    }

    return clone;
  }

  // ====================================================================== //
  // --------------------- Minimum Spanning Tree -------------------------- //
  // ====================================================================== //
  public static class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
      this.src = src;
      this.dest = dest;
      this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
      return Integer.compare(this.weight, other.weight);
    }
  }

  // Union-Find helper class
  static class UnionFind {
    int[] parent, rank;

    UnionFind(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
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
      if (px == py)
        return false;

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
  }

  public static List<Edge> kruskalMST(List<Edge> edges, int n) {
    Collections.sort(edges);
    UnionFind uf = new UnionFind(n);
    List<Edge> mst = new ArrayList<>();

    for (Edge edge : edges) {
      if (uf.union(edge.src, edge.dest)) {
        mst.add(edge);
        if (mst.size() == n - 1)
          break;
      }
    }

    return mst;
  }

  // =================== TESTING AND EXAMPLES ===================

  public static void main(String[] args) {
    // Test Case 1: Simple connected graph
    int[][] edges1 = { { 0, 1 }, { 1, 2 }, { 2, 3 } };
    Map<Integer, List<Integer>> graph1 = buildGraph(edges1, 4);

    System.out.println("=== TEST CASE 1: Linear Graph (0-1-2-3) ===");
    System.out.println("Has path 0->3 (DFS): " + hasPathDFS(graph1, 0, 3));
    System.out.println("Has path 0->3 (BFS): " + hasPathBFS(graph1, 0, 3));
    System.out.println("Connected components: " + countConnectedComponents(graph1));
    System.out.println("Has cycle: " + hasCycleUndirected(graph1));
    System.out.println("Shortest distance 0->3: " + shortestDistance(graph1, 0, 3));
    System.out.println("Shortest path 0->3: " + shortestPath(graph1, 0, 3));

    // Test Case 2: Graph with cycle
    int[][] edges2 = { { 0, 1 }, { 1, 2 }, { 2, 0 }, { 2, 3 } };
    Map<Integer, List<Integer>> graph2 = buildGraph(edges2, 4);

    System.out.println("\n=== TEST CASE 2: Graph with Cycle ===");
    System.out.println("Connected components: " + countConnectedComponents(graph2));
    System.out.println("Has cycle: " + hasCycleUndirected(graph2));
    System.out.println("Shortest distance 0->3: " + shortestDistance(graph2, 0, 3));

    // Test Case 3: Disconnected graph
    int[][] edges3 = { { 0, 1 }, { 2, 3 } };
    Map<Integer, List<Integer>> graph3 = buildGraph(edges3, 5); // Node 4 is isolated

    System.out.println("\n=== TEST CASE 3: Disconnected Graph ===");
    System.out.println("Has path 0->2: " + hasPathDFS(graph3, 0, 2));
    System.out.println("Connected components: " + countConnectedComponents(graph3));
    System.out.println("All distances from 0: " + allShortestDistances(graph3, 0));

    // Test topological sort
    // Example directed graph for topological sort: 5 -> 2, 5 -> 0, 4 -> 0, 4 -> 1,
    // 2 -> 3, 3 -> 1
    int[][] directedEdges = { { 5, 2 }, { 5, 0 }, { 4, 0 }, { 4, 1 }, { 2, 3 }, { 3, 1 } };
    Map<Integer, List<Integer>> directedGraph = new HashMap<>();
    for (int i = 0; i < 6; i++)
      directedGraph.put(i, new ArrayList<>());
    for (int[] edge : directedEdges)
      directedGraph.get(edge[0]).add(edge[1]);
    System.out.println("Topological sort: " + topologicalSortDFS(directedGraph));

    // Test bipartite
    System.out.println("Is bipartite: " + isBipartite(graph1));

    // Test all paths
    System.out.println("All paths 0->3: " + findAllPaths(graph1, 0, 3));

    // Test MST
    List<Edge> edges = Arrays.asList(new Edge(0, 1, 4), new Edge(0, 2, 3), new Edge(1, 2, 1), new Edge(1, 3, 2));
    System.out.println("MST edges: " + kruskalMST(edges, 4));

  }
}