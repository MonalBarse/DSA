package com.monal.Graphs.Topological;

import java.util.*;

public class Basics {

  // ========================================================================== //
  // ============ TOPOLOGICAL SORT (Directed Acyclic Graph) =================== //
  // ========================================================================== //

  // DFS-based topological sort
  /*
   * Graph: A → B → C
   * A → D → C
   *
   * DFS Journey:
   * 1. Start A → go to B → go to C (C has no children)
   * 2. C finishes first → stack.push(C)
   * 3. Back to B (B done) → stack.push(B)
   * 4. Back to A → go to D → go to C (already visited)
   * 5. D finishes → stack.push(D)
   * 6. A finishes → stack.push(A)
   *
   * Stack (bottom to top): [C, B, D, A]
   * Pop order: A, D, B, C ✓ (Valid topological order!)
   */
  public static List<Integer> topologicalSortDFS(int V, List<List<Integer>> adj) {
    boolean[] visited = new boolean[V];
    boolean[] recStack = new boolean[V]; // To detect cycles
    Stack<Integer> stack = new Stack<>();

    // Try DFS from each unvisited node
    for (int i = 0; i < V; i++) {
      if (!visited[i]) {
        if (helper_hasCycle(i, adj, visited, recStack, stack)) {
          return new ArrayList<>(); // Cycle detected!
        }
      }
    }

    // Pop from stack to get topological order
    List<Integer> result = new ArrayList<>();
    while (!stack.isEmpty()) {
      result.add(stack.pop());
    }

    return result;
  }

  private static boolean helper_hasCycle(int node, List<List<Integer>> adj,
      boolean[] visited, boolean[] recStack,
      Stack<Integer> stack) {
    visited[node] = true;
    recStack[node] = true; // Currently in recursion path

    for (int neighbor : adj.get(node)) {
      if (!visited[neighbor]) {
        if (helper_hasCycle(neighbor, adj, visited, recStack, stack)) {
          return true;
        }
      } else if (recStack[neighbor]) {
        return true; // Back edge = cycle!
      }
    }

    recStack[node] = false; // Done with this path
    stack.push(node); // Add to result (reverse order)
    return false;
  }

  // Kahn's Algorithm (BFS-based)
  public static List<Integer> topologicalSortKahn(Map<Integer, List<Integer>> graph, int[] indegree) {
    // A map of graph represents - key : [neighbors]
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

  public static List<Integer> topologicalSortBFS(int V, List<List<Integer>> adj) {
    // Step 1: Count prerequisites (indegree)
    int[] indegree = new int[V];
    for (int i = 0; i < V; i++) {
      for (int neighbor : adj.get(i)) {
        indegree[neighbor]++;
      }
    }

    // Step 2: Find courses with NO prerequisites
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < V; i++) {
      if (indegree[i] == 0) {
        queue.offer(i); // Can take these courses first!
      }
    }

    List<Integer> result = new ArrayList<>();

    // Step 3: BFS - Take courses and unlock next ones
    while (!queue.isEmpty()) {
      int course = queue.poll();
      result.add(course);

      // This course is done! Reduce prerequisites for next courses
      for (int nextCourse : adj.get(course)) {
        indegree[nextCourse]--;
        if (indegree[nextCourse] == 0) {
          queue.offer(nextCourse); // Now this course can be taken!
        }
      }
    }

    return result.size() == V ? result : new ArrayList<>(); // Empty if cycle exists
  }
}
