package com.monal.Graphs.Topological;

import java.util.*;
/* There is a directed graph of n nodes with each node labeled from 0 to n - 1. The graph is represented by a 0-indexed 2D integer array graph where graph[i] is an integer array of nodes adjacent to node i, meaning there is an edge from node i to each node in graph[i].

A node is a terminal node if there are no outgoing edges. A node is a safe node if every possible path starting from that node leads to a terminal node (or another safe node).

Return an array containing all the safe nodes of the graph. The answer should be sorted in ascending order. */

public class P002 {

  /*
   * 🔥 SAFE NODES PROBLEM - DFS vs BFS Analysis
   *
   * PROBLEM: Find all nodes where every path leads to a terminal node (no
   * outgoing edges)
   *
   * KEY INSIGHT: A node is UNSAFE if it's part of a cycle OR leads to a cycle
   */

  public class SafeNodesAnalysis {

    // =============================================================== //
    // ====================== DFS TOPO SORT ========================== //
    // =============================================================== //

    public static List<Integer> eventualSafeNodesDFS(int[][] graph) {
      int n = graph.length;
      int[] state = new int[n]; // 0 = unvisited, 1 = visiting, 2 = safe
      List<Integer> result = new ArrayList<>();

      for (int i = 0; i < n; i++) {
        if (dfs(i, graph, state)) {
          result.add(i);
        }
      }

      return result;
    }

    private static boolean dfs(int node, int[][] graph, int[] state) {
      if (state[node] != 0) {
        return state[node] == 2; // return true if already marked safe
      }

      state[node] = 1; // mark as visiting (in current path)

      for (int neighbor : graph[node]) {
        if (state[neighbor] == 2)
          continue; // already safe, skip
        if (state[neighbor] == 1 || !dfs(neighbor, graph, state)) {
          return false; // cycle detected OR leads to unsafe node
        }
      }

      state[node] = 2; // mark as safe
      return true;
    }

    // ================================================================= //
    /*
     * DFS naturally explores "paths" - perfect for cycle detection in current path
     * BFS explores "layers" - harder to track if we're in a cycle
     *
     * DFS: "Am I in a cycle right now?" ✓ Easy with recursion stack
     * BFS: "Am I in a cycle?" ❌ Need to reverse the graph!
     *
     * SOLUTION: Reverse the problem!
     * Instead of "find safe nodes" → "eliminate unsafe nodes"
     */

    // ================================================================= //
    // BFS SOLUTION - REVERSE GRAPH APPROACH
    // ================================================================= //

    public static List<Integer> eventualSafeNodesBFS(int[][] graph) {
      int n = graph.length;

      // Step 1: Build REVERSE graph
      List<List<Integer>> reverseGraph = new ArrayList<>();
      int[] outDegree = new int[n]; // Count outgoing edges in original graph

      for (int i = 0; i < n; i++) {
        reverseGraph.add(new ArrayList<>());
      }

      for (int i = 0; i < n; i++) {
        for (int neighbor : graph[i]) {
          reverseGraph.get(neighbor).add(i); // Reverse the edge!
          outDegree[i]++; // Count outgoing edges
        }
      }

      // Step 2: Start BFS from terminal nodes (outDegree = 0)
      Queue<Integer> queue = new LinkedList<>();
      for (int i = 0; i < n; i++) {
        if (outDegree[i] == 0) {
          queue.offer(i); // Terminal nodes are definitely safe
        }
      }

      boolean[] safe = new boolean[n];

      // Step 3: BFS - propagate safety backwards
      while (!queue.isEmpty()) {
        int safeNode = queue.poll();
        safe[safeNode] = true;

        // Check all nodes that point TO this safe node
        for (int predecessor : reverseGraph.get(safeNode)) {
          outDegree[predecessor]--; // Remove edge to safe node

          if (outDegree[predecessor] == 0) {
            queue.offer(predecessor); // Now this node is also safe!
          }
        }
      }

      // Step 4: Collect all safe nodes
      List<Integer> result = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        if (safe[i]) {
          result.add(i);
        }
      }

      return result;
    }

    // =================================================================
    // VISUAL EXPLANATION WITH EXAMPLE
    // =================================================================

    public static void explainWithExample() {
      // Example graph: [[1,2],[2,3],[5],[0],[5],[],[]]
      int[][] graph = { { 1, 2 }, { 2, 3 }, { 5 }, { 0 }, { 5 }, {}, {} };

      System.out.println("=== ORIGINAL GRAPH ===");
      System.out.println("0 → [1,2]");
      System.out.println("1 → [2,3]");
      System.out.println("2 → [5]");
      System.out.println("3 → [0]     ← Creates cycle: 0→1→3→0");
      System.out.println("4 → [5]");
      System.out.println("5 → []      ← Terminal node");
      System.out.println("6 → []      ← Terminal node");

      System.out.println("\n=== WHY EACH NODE IS SAFE/UNSAFE ===");
      System.out.println("Node 5: Terminal → SAFE ✓");
      System.out.println("Node 6: Terminal → SAFE ✓");
      System.out.println("Node 4: Leads to 5 (safe) → SAFE ✓");
      System.out.println("Node 2: Leads to 5 (safe) → SAFE ✓");
      System.out.println("Node 0: Part of cycle 0→1→3→0 → UNSAFE ✗");
      System.out.println("Node 1: Part of cycle 0→1→3→0 → UNSAFE ✗");
      System.out.println("Node 3: Part of cycle 0→1→3→0 → UNSAFE ✗");

      System.out.println("\n=== BFS STEP-BY-STEP ===");

      // Step 1: Reverse graph
      System.out.println("1. Build reverse graph:");
      System.out.println("   5 ← [2,4]  (nodes 2,4 point to 5)");
      System.out.println("   2 ← [0,1]  (nodes 0,1 point to 2)");
      System.out.println("   3 ← [1]    (node 1 points to 3)");
      System.out.println("   0 ← [3]    (node 3 points to 0)");

      // Step 2: Find terminal nodes
      System.out.println("\n2. Start with terminal nodes (outDegree = 0):");
      System.out.println("   Queue: [5, 6]");

      // Step 3: BFS propagation
      System.out.println("\n3. BFS Propagation:");
      System.out.println("   Process 5: Mark safe, reduce outDegree of [2,4]");
      System.out.println("   Process 6: Mark safe (no predecessors)");
      System.out.println("   Process 2: outDegree became 0, mark safe, reduce outDegree of [0,1]");
      System.out.println("   Process 4: outDegree became 0, mark safe");
      System.out.println("   Nodes 0,1,3 still have outDegree > 0 → stuck in cycle → UNSAFE");

      System.out.println("\n=== RESULTS ===");
      System.out.println("DFS result: " + eventualSafeNodesDFS(graph));
      System.out.println("BFS result: " + eventualSafeNodesBFS(graph));
    }

    // =================================================================
    // TEST BOTH SOLUTIONS
    // =================================================================
    public static void main(String[] args) {
      explainWithExample();

      System.out.println("\n=== ADDITIONAL TESTS ===");

      // Test case 1: Simple terminal nodes
      int[][] graph1 = { {}, { 0, 2, 4 }, { 3 }, { 4 }, {} };
      System.out.println("Graph1 DFS: " + eventualSafeNodesDFS(graph1));
      System.out.println("Graph1 BFS: " + eventualSafeNodesBFS(graph1));

      // Test case 2: All nodes in cycle
      int[][] graph2 = { { 1 }, { 2 }, { 0 } };
      System.out.println("Graph2 DFS: " + eventualSafeNodesDFS(graph2));
      System.out.println("Graph2 BFS: " + eventualSafeNodesBFS(graph2));

      // Test case 3: All safe nodes
      int[][] graph3 = { { 1 }, { 2 }, { 3 }, {} };
      System.out.println("Graph3 DFS: " + eventualSafeNodesDFS(graph3));
      System.out.println("Graph3 BFS: " + eventualSafeNodesBFS(graph3));
    }
  }
}

// =================================================================
// MEMORY TECHNIQUE: WHY BFS NEEDS REVERSE GRAPH
// =================================================================
/*
 * 🧠 MEMORY AID: "The River Flow Analogy"
 *
 * DFS: Like following a river downstream
 * - Easy to detect if you're going in circles (recursion stack)
 * - Natural for path-based problems
 *
 * BFS: Like spreading water from multiple sources
 * - Hard to detect cycles directly
 * - BUT: If we reverse the flow (reverse graph), we can start from
 * "sinks" (terminal nodes) and spread backwards!
 *
 * 🔑 KEY INSIGHT:
 * "If all your outgoing water eventually reaches a safe drain,
 * then you're safe too!"
 *
 * Original: A → B → Terminal
 * Reversed: A ← B ← Terminal (start from terminal, work backwards)
 */

// =================================================================
// COMPARISON SUMMARY
// =================================================================
/*
 * | Aspect | DFS | BFS |
 * |--------|-----|-----|
 * | **Natural for** | Path problems | Layer problems |
 * | **Cycle Detection** | Easy (recursion stack) | Hard (need tricks) |
 * | **This Problem** | Direct approach | Reverse graph needed |
 * | **Time Complexity** | O(V + E) | O(V + E) |
 * | **Space Complexity** | O(V) recursion | O(V + E) for reverse graph |
 * | **Intuition** | "Follow path to end" | "Eliminate unsafe from safe end" |
 */
