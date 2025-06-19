package com.monal.Graphs.Others;

import java.util.*;

public class Basics {

  /**
   * 1. Kosaraju's Algorithm - Find Strongly Connected Components in directed
   * graphs
   * 2. Bridges in Graph - Find critical edges whose removal increases connected
   * components
   * 3. Articulation Points - Find critical vertices whose removal increases
   * connected components
   */

  public class GraphAlgorithmsDemo {

    // ====================== KOSARAJU'S ALGORITHM ======================

    /**
     * KOSARAJU'S ALGORITHM:
     * Purpose: Find all Strongly Connected Components (SCCs) in a directed graph
     *
     * What is an SCC?
     * - A maximal set of vertices where every vertex can reach every other vertex
     * - In other words: there's a path from any vertex to any other vertex in the
     * same SCC
     *
     * Algorithm Steps:
     * 1. Perform DFS on original graph and store vertices in stack by finish time
     * 2. Create transpose graph (reverse all edges)
     * 3. Pop vertices from stack and perform DFS on transpose graph
     * 4. Each DFS call gives one SCC
     *
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    static class KosarajuSCC {
      private List<List<Integer>> orgGraph; // Original graph
      private List<List<Integer>> transposeGraph; // Graph with reversed edges
      private boolean[] visited;
      private Stack<Integer> finishOrder; // Stack to store finish order
      private int vertices;

      public KosarajuSCC(int vertices) {
        this.vertices = vertices;
        this.visited = new boolean[vertices];
        this.orgGraph = new ArrayList<>();
        this.transposeGraph = new ArrayList<>();
        this.finishOrder = new Stack<>();

        // Initialize adjacency lists
        for (int i = 0; i < vertices; i++) {
          orgGraph.add(new ArrayList<>());
          transposeGraph.add(new ArrayList<>());
        }
      }

      // Add directed edge from u to v
      public void addEdge(int u, int v) {
        orgGraph.get(u).add(v); // Original edge u -> v
        transposeGraph.get(v).add(u); // Reversed edge v -> u in transpose
      }

      public List<List<Integer>> findSCCs() {
        List<List<Integer>> allSCCs = new ArrayList<>(); // To store all SCCs

        // fill the stack with vertices in order of their finish times
        System.out.println("Step 1: Performing DFS on original graph to get finish order");
        for (int i = 0; i < vertices; i++) {
          if (!visited[i]) {
            dfsOriginal(i);
          }
        }
        System.out.println("Finish order (top to bottom): " + finishOrder);

        // Step 2: Reset visited array for second DFS
        Arrays.fill(visited, false);

        // Step 3: Process vertices in reverse finish order on transpose graph
        System.out.println("\nStep 2: Performing DFS on transpose graph");
        int sccCount = 1;
        // finishOrder has vertices in decreasing order of finish time
        while (!finishOrder.isEmpty()) {
          int vertex = finishOrder.pop();
          if (!visited[vertex]) {
            System.out.println("Finding SCC #" + sccCount + " starting from vertex " + vertex);
            List<Integer> currentSCC = new ArrayList<>();
            dfsTranspose(vertex, currentSCC);
            allSCCs.add(currentSCC);
            System.out.println("SCC #" + sccCount + ": " + currentSCC);
            sccCount++;
          }
        }

        return allSCCs;
      }

      // DFS on original graph to fill finish order stack
      private void dfsOriginal(int vertex) {
        visited[vertex] = true;

        for (int neighbor : orgGraph.get(vertex)) {
          if (!visited[neighbor]) {
            dfsOriginal(neighbor);
          }
        }

        // Push to stack when DFS for this vertex finishes
        finishOrder.push(vertex);
      }

      // DFS on transpose graph to find SCC
      private void dfsTranspose(int vertex, List<Integer> currentSCC) {
        visited[vertex] = true;
        currentSCC.add(vertex);

        for (int neighbor : transposeGraph.get(vertex)) {
          if (!visited[neighbor]) {
            dfsTranspose(neighbor, currentSCC);
          }
        }
      }

      public void printGraph() {
        System.out.println("Original Graph:");
        for (int i = 0; i < vertices; i++) {
          System.out.println("Vertex " + i + " -> " + orgGraph.get(i));
        }
        System.out.println("\nTranspose Graph:");
        for (int i = 0; i < vertices; i++) {
          System.out.println("Vertex " + i + " -> " + transposeGraph.get(i));
        }
      }
    }

    static void demoKosarajuAlgorithm() {
      System.out.println("1. KOSARAJU'S ALGORITHM - Strongly Connected Components");
      System.out.println("=====================================================");

      // Create a directed graph with multiple SCCs
      // Graph: 0 -> 1 -> 2 -> 0 (SCC 1: {0,1,2})
      // 2 -> 3 -> 4 (SCC 2: {3,4} if there's back edge 4->3)
      // 4 -> 5 (SCC 3: {5})

      KosarajuSCC graph = new KosarajuSCC(8);

      // Adding edges to create SCCs
      graph.addEdge(0, 1); // 0 -> 1
      graph.addEdge(1, 2); // 1 -> 2
      graph.addEdge(2, 0); // 2 -> 0 (completes cycle: 0-1-2-0)
      graph.addEdge(1, 3); // 1 -> 3 (bridge to next component)
      graph.addEdge(3, 4); // 3 -> 4
      graph.addEdge(4, 5); // 4 -> 5 (to isolated vertex)
      graph.addEdge(5, 6); // 5 -> 6 (to isolated vertex)
      graph.addEdge(6, 4); // 6 -> 4 (creates cycle: 4-5-6-4)
      graph.addEdge(4, 7); // 4 -> 7 (to isolated vertex)

      System.out.println("Example Graph Structure:");
      System.out.println("SCC 1: 0 -> 1 -> 2 (cycle)");
      System.out.println("SCC 2: 3 -> 4 (isolated vertex)");
      System.out.println("SCC 3: 4-> 5 -> 6 -> 4 (cycle)");
      System.out.println("SCC 4: 4 -> 7 (isolated vertex)");
      System.out.println();

      graph.printGraph();
      System.out.println();

      List<List<Integer>> sccs = graph.findSCCs();

      System.out.println("\n=== RESULTS ===");
      System.out.println("Total Strongly Connected Components found: " + sccs.size());
      for (int i = 0; i < sccs.size(); i++) {
        System.out.println("SCC " + (i + 1) + ": " + sccs.get(i));
      }

      System.out.println("\nWhy this works:");
      System.out.println("- First DFS finds finish times (when we're done exploring from each vertex)");
      System.out.println("- Transpose graph reverses all edges");
      System.out.println("- Second DFS in reverse finish order groups vertices that can reach each other");
      System.out.println("=".repeat(80) + "\n");
    }

    // ====================== BRIDGES ALGORITHM ======================

    /**
     * BRIDGES IN GRAPH (Tarjan's Algorithm):
     * Purpose: Find all bridges (critical edges) in an undirected graph
     *
     * What is a Bridge?
     * - An edge whose removal increases the number of connected components
     * - Removing a bridge disconnects the graph
     *
     * Algorithm Logic:
     * - Use DFS with discovery time and low value
     * - Discovery time: when vertex is first visited
     * - Low value: lowest discovery time reachable from vertex
     * - Edge (u,v) is bridge if: low[v] > discovery[u]
     *
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    static class BridgesFinder {
      private List<List<Integer>> adjList;
      private boolean[] visited;
      private int[] discoveryTime; // When vertex was discovered
      private int[] lowValue; // Lowest discovery time reachable
      private int[] parent; // Parent in DFS tree
      private List<int[]> bridges; // Store all bridges
      private int time; // Global time counter

      public BridgesFinder(int vertices) {
        this.adjList = new ArrayList<>();
        this.visited = new boolean[vertices];
        this.discoveryTime = new int[vertices];
        this.lowValue = new int[vertices];
        this.parent = new int[vertices];
        this.bridges = new ArrayList<>();
        this.time = 0;

        for (int i = 0; i < vertices; i++) {
          adjList.add(new ArrayList<>());
          parent[i] = -1;
        }
      }

      public void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u); // Undirected graph
      }

      public List<int[]> findBridges() {
        System.out.println("Finding bridges using Tarjan's algorithm...");

        // Start DFS from all unvisited vertices (handles disconnected components)
        for (int i = 0; i < adjList.size(); i++) {
          if (!visited[i]) {
            bridgeDFS(i);
          }
        }

        return bridges;
      }

      private void bridgeDFS(int u) {
        // Mark current vertex as visited
        visited[u] = true;

        // Initialize discovery time and low value
        discoveryTime[u] = lowValue[u] = time++;

        System.out.println("Visiting vertex " + u + " at time " + discoveryTime[u]);

        // Explore all neighbors
        for (int v : adjList.get(u)) {
          if (!visited[v]) {
            // v is not visited, so it's a tree edge
            parent[v] = u;
            bridgeDFS(v);

            // Update low value of u based on v's low value
            lowValue[u] = Math.min(lowValue[u], lowValue[v]);

            // Check if edge u-v is a bridge
            // Bridge condition: low[v] > discovery[u]
            // This means v cannot reach any vertex discovered before u
            if (lowValue[v] > discoveryTime[u]) {
              bridges.add(new int[] { u, v });
              System.out.println("BRIDGE FOUND: (" + u + ", " + v + ")");
              System.out.println("  Reason: low[" + v + "] = " + lowValue[v] +
                  " > discovery[" + u + "] = " + discoveryTime[u]);
            }
          } else if (v != parent[u]) {
            // v is visited and not parent of u, so it's a back edge
            // Update low value of u based on discovery time of v
            lowValue[u] = Math.min(lowValue[u], discoveryTime[v]);
            System.out.println("Back edge found: " + u + " -> " + v);
          }
        }

        System.out.println("Finished processing vertex " + u +
            ", low[" + u + "] = " + lowValue[u]);
      }

      public void printGraph() {
        System.out.println("Graph structure:");
        for (int i = 0; i < adjList.size(); i++) {
          System.out.println("Vertex " + i + " -> " + adjList.get(i));
        }
      }
    }

    static void demoBridgesAlgorithm() {
      System.out.println("2. BRIDGES IN GRAPH - Critical Edges");
      System.out.println("=====================================");

      // Create a graph with some bridges
      BridgesFinder graph = new BridgesFinder(7);

      // Create connected components with bridges between them
      // Component 1: 0-1-2 (triangle)
      graph.addEdge(0, 1);
      graph.addEdge(1, 2);
      graph.addEdge(2, 0);

      // Bridge: 2-3 (critical edge)
      graph.addEdge(2, 3);

      // Component 2: 3-4-5-6 with internal bridge
      graph.addEdge(3, 4); // Bridge
      graph.addEdge(4, 5);
      graph.addEdge(5, 6);
      graph.addEdge(6, 4); // Creates cycle 4-5-6-4

      System.out.println("Example Graph:");
      System.out.println("Triangle: 0-1-2-0");
      System.out.println("Bridge: 2-3");
      System.out.println("Component: 3-4-5-6 with 6-4 closing cycle");
      System.out.println();

      graph.printGraph();
      System.out.println();

      List<int[]> bridges = graph.findBridges();

      System.out.println("\n=== RESULTS ===");
      System.out.println("Bridges found: " + bridges.size());
      for (int[] bridge : bridges) {
        System.out.println("Bridge: (" + bridge[0] + ", " + bridge[1] + ")");
      }

      System.out.println("\nWhy these are bridges:");
      System.out.println("- Removing bridge (2,3) would separate the triangle from the rest");
      System.out.println("- Removing bridge (3,4) would isolate vertex 3");
      System.out.println("- Edges in cycles are NOT bridges (removing them doesn't disconnect)");
      System.out.println("=".repeat(80) + "\n");
    }

    // ====================== ARTICULATION POINTS ======================

    /**
     * ARTICULATION POINTS (Cut Vertices):
     * Purpose: Find all articulation points in an undirected graph
     *
     * What is an Articulation Point?
     * - A vertex whose removal increases the number of connected components
     * - Critical vertices that hold the graph together
     *
     * Algorithm Logic:
     * - Similar to bridges but focuses on vertices instead of edges
     * - Vertex u is articulation point if:
     * 1. u is root of DFS tree and has more than one child, OR
     * 2. u is not root and has a child v where low[v] >= discovery[u]
     *
     * Time Complexity: O(V + E)
     * Space Complexity: O(V)
     */
    static class ArticulationPointsFinder {
      private List<List<Integer>> adjList;
      private boolean[] visited;
      private int[] discoveryTime;
      private int[] lowValue;
      private int[] parent;
      private boolean[] isArticulationPoint;
      private int time;

      public ArticulationPointsFinder(int vertices) {
        this.adjList = new ArrayList<>();
        this.visited = new boolean[vertices];
        this.discoveryTime = new int[vertices];
        this.lowValue = new int[vertices];
        this.parent = new int[vertices];
        this.isArticulationPoint = new boolean[vertices];
        this.time = 0;

        for (int i = 0; i < vertices; i++) {
          adjList.add(new ArrayList<>());
          parent[i] = -1;
        }
      }

      public void addEdge(int u, int v) {
        adjList.get(u).add(v);
        adjList.get(v).add(u);
      }

      public List<Integer> findArticulationPoints() {
        System.out.println("Finding articulation points using Tarjan's algorithm...");

        for (int i = 0; i < adjList.size(); i++) {
          if (!visited[i]) {
            articulationDFS(i);
          }
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < isArticulationPoint.length; i++) {
          if (isArticulationPoint[i]) {
            result.add(i);
          }
        }

        return result;
      }

      private void articulationDFS(int u) {
        int children = 0; // Count of children in DFS tree

        visited[u] = true;
        discoveryTime[u] = lowValue[u] = time++;

        System.out.println("Visiting vertex " + u + " at time " + discoveryTime[u]);

        for (int v : adjList.get(u)) {
          if (!visited[v]) {
            children++;
            parent[v] = u;
            articulationDFS(v);

            // Update low value
            lowValue[u] = Math.min(lowValue[u], lowValue[v]);

            // Check articulation point conditions

            // Condition 1: u is root and has more than one child
            if (parent[u] == -1 && children > 1) {
              isArticulationPoint[u] = true;
              System.out.println("ARTICULATION POINT: " + u + " (root with " + children + " children)");
            }

            // Condition 2: u is not root and low[v] >= discovery[u]
            if (parent[u] != -1 && lowValue[v] >= discoveryTime[u]) {
              isArticulationPoint[u] = true;
              System.out.println("ARTICULATION POINT: " + u +
                  " (low[" + v + "] = " + lowValue[v] +
                  " >= discovery[" + u + "] = " + discoveryTime[u] + ")");
            }

          } else if (v != parent[u]) {
            // Back edge
            lowValue[u] = Math.min(lowValue[u], discoveryTime[v]);
            System.out.println("Back edge: " + u + " -> " + v);
          }
        }

        System.out.println("Finished vertex " + u + ", low[" + u + "] = " + lowValue[u]);
      }

      public void printGraph() {
        System.out.println("Graph structure:");
        for (int i = 0; i < adjList.size(); i++) {
          System.out.println("Vertex " + i + " -> " + adjList.get(i));
        }
      }
    }

    static void demoArticulationPoints() {
      System.out.println("3. ARTICULATION POINTS - Critical Vertices");
      System.out.println("==========================================");

      // Create a graph with articulation points
      ArticulationPointsFinder graph = new ArticulationPointsFinder(7);

      // Create structure with clear articulation points
      // Triangle: 0-1-2-0
      graph.addEdge(0, 1);
      graph.addEdge(1, 2);
      graph.addEdge(2, 0);

      // Articulation point: vertex 2 connects to rest
      graph.addEdge(2, 3);

      // Linear chain: 3-4-5 (both 3 and 4 are articulation points)
      graph.addEdge(3, 4);
      graph.addEdge(4, 5);

      // Another branch from articulation point 4
      graph.addEdge(4, 6);

      System.out.println("Example Graph:");
      System.out.println("Triangle: 0-1-2-0");
      System.out.println("Linear: 2-3-4-5");
      System.out.println("Branch: 4-6");
      System.out.println();

      graph.printGraph();
      System.out.println();

      List<Integer> articulationPoints = graph.findArticulationPoints();

      System.out.println("\n=== RESULTS ===");
      System.out.println("Articulation points found: " + articulationPoints.size());
      for (int point : articulationPoints) {
        System.out.println("Articulation Point: " + point);
      }

      System.out.println("\nWhy these are articulation points:");
      System.out.println("- Removing vertex 2: separates triangle {0,1} from rest {3,4,5,6}");
      System.out.println("- Removing vertex 3: isolates vertex 2 from {4,5,6}");
      System.out.println("- Removing vertex 4: separates {2,3} from {5} and {6}");
      System.out.println("- Vertices in triangles (0,1) are NOT articulation points");
      System.out.println("=".repeat(80) + "\n");
    }

  }

  public static void main(String[] args) {
    System.out.println("=== GRAPH ALGORITHMS DEMONSTRATION ===\n");
    GraphAlgorithmsDemo.demoKosarajuAlgorithm();
    GraphAlgorithmsDemo.demoBridgesAlgorithm();
    GraphAlgorithmsDemo.demoArticulationPoints();
    System.out.println("=== END OF DEMONSTRATION ===");
  }
}
