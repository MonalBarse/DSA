package com.monal.Revise;

import java.util.*;

public class Revise01 {

    /*
     * =============================================================================
     * GRAPH ALGORITHMS REVISION - PART 1
     * =============================================================================
     *
     * This file focuses on the CORE FOUNDATION patterns you need to recognize:
     * 1. BFS/DFS Traversals and their variations
     * 2. Topological Sort and Cycle Detection
     * 3. Basic Graph Representations
     *
     * LEARNING APPROACH FOR YOUR PROFILE:
     * - Each algorithm has a PATTERN RECOGNITION section
     * - Comments explain WHY we do something, not just HOW
     * - Boilerplate templates at the end for quick recall
     * - Focus on RELATIONSHIPS between concepts
     *
     * =============================================================================
     */

    // =============================
    // 1. GRAPH REPRESENTATIONS
    // =============================

    /*
     * PATTERN RECOGNITION: How do I know which representation to use?
     *
     * ADJACENCY LIST → When edges are sparse, need to iterate neighbors
     * - Most interview problems use this
     * - Pattern: "Find all neighbors of a node"
     *
     * ADJACENCY MATRIX → When need O(1) edge lookup, dense graphs
     * - Pattern: "Check if edge exists between two specific nodes"
     *
     * EDGE LIST → When processing edges themselves
     * - Pattern: "Sort edges by weight", "Union-Find operations"
     */

    // Standard adjacency list representation
    public static List<List<Integer>> buildAdjacencyList(int n, int[][] edges) {
        List<List<Integer>> adj = new ArrayList<>();
        // Step 1: Initialize adjacency list with empty lists
        for (int i = 0; i < n; i++)
            adj.add(new ArrayList<>());

        // Step 2: Populate adjacency list with edges
        for (int[] edge : edges)
            adj.get(edge[0]).add(edge[1]); // For undirected: adj.get(edge[1]).add(edge[0]);

        return adj;
    }

    // =============================
    // 2. BFS TRAVERSAL PATTERNS
    // =============================

    /*
     * BFS PATTERN RECOGNITION:
     *
     * The BFS pattern has these CORE COMPONENTS:
     * 1. Queue for level-order processing
     * 2. Visited array to avoid cycles
     * 3. Distance/level tracking (if needed)
     * 4. Early termination (if searching for target)
     *
     * When to use BFS?
     * - "Shortest path in UNWEIGHTED graph" → BFS
     * - "Level by level processing" → BFS
     * - "Find minimum steps/moves" → BFS
     * - "Check if reachable" → BFS or DFS (BFS often easier)
     */

    // BASIC BFS Template - memorize this structure
    public static void bfsBasic(List<List<Integer>> adj, int start) {
        int n = adj.size();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        // Initialize
        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " "); // Process current node

            // Explore neighbors
            for (int neighbor : adj.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }

    // find the shortest path from start to all using BFS
    public int[] bfsShortestPathAll(List<List<Integer>> adj, int start) {
        int n = adj.size();
        boolean[] visited = new boolean[n];
        int[] distance = new int[n];
        Arrays.fill(distance, -1); // -1 means unreachable
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);
        visited[start] = true;
        distance[start] = 0; // Distance to self is 0

        while (!queue.isEmpty()) {
            int current = queue.poll();

            for (int neighbor : adj.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distance[neighbor] = distance[current] + 1; // Increment distance
                    queue.offer(neighbor);
                }
            }
        }
        return distance; // Return distances from start to all nodes
    }

    // BFS with LEVEL tracking - common interview pattern
    public static int bfsShortestPath(List<List<Integer>> adj, int start, int target) {
        int n = adj.size();
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);
        visited[start] = true;
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size(); // KEY: Process entire level at once

            // Process all nodes at current level
            for (int i = 0; i < size; i++) {
                int current = queue.poll();

                if (current == target)
                    return level; // Found target at this level

                for (int neighbor : adj.get(current)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
            level++; // Move to next level
        }
        return -1; // Target not reachable
    }

    // =============================
    // 3. DFS TRAVERSAL PATTERNS
    // =============================

    /*
     * DFS PATTERN RECOGNITION:
     *
     *
     * DFS has TWO main implementations:
     * 1. Recursive (easier to write, but stack overflow risk)
     * 2. Iterative with stack (more control, no stack overflow)
     *
     * When to use DFS?
     * - "Explore all paths" → DFS
     * - "Check connectivity" → DFS
     * - "Detect cycles" → DFS
     * - "Topological sort" → DFS
     * - "Find strongly connected components" → DFS
     */

    // RECURSIVE DFS Template - most common in interviews
    public static void dfsRecursive(List<List<Integer>> adj, int current, boolean[] visited) {
        visited[current] = true;
        System.out.print(current + " "); // Process current node

        for (int neighbor : adj.get(current)) {
            if (!visited[neighbor]) {
                dfsRecursive(adj, neighbor, visited);
            }
        }
    }

    // ITERATIVE DFS Template - useful for complex state tracking
    public static void dfsIterative(List<List<Integer>> adj, int start) {
        int n = adj.size();
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (!visited[current]) {
                visited[current] = true;
                System.out.print(current + " "); // Process current node

                // Add neighbors to stack (reverse order for left-to-right traversal)
                for (int i = adj.get(current).size() - 1; i >= 0; i--) {
                    int neighbor = adj.get(current).get(i);
                    if (!visited[neighbor])
                        stack.push(neighbor);
                }
            }
        }
    }

    // =============================
    // 4. CYCLE DETECTION PATTERNS
    // =============================

    /*
     * CYCLE DETECTION PATTERN RECOGNITION:
     *
     * DIRECTED GRAPH → Use DFS with 3-color approach
     * - White (0): Unvisited
     * - Gray (1): Currently being processed (in recursion stack)
     * - Black (2): Completely processed
     *
     * UNDIRECTED GRAPH → Use DFS with parent tracking
     * - If we reach a visited node that's not our parent → cycle
     *
     * WHY 3-color for directed graphs?
     * - We need to distinguish between "visited in current path" vs
     * "visited in previous path"
     * - Gray nodes represent current path (recursion stack)
     * - If we reach a gray node → back edge → cycle
     */

    // Cycle detection in DIRECTED graph
    public static boolean hasCycleDirected(List<List<Integer>> adj) {
        int n = adj.size();
        int[] color = new int[n]; // 0=white, 1=gray, 2=black

        for (int i = 0; i < n; i++) {
            if (color[i] == 0) { // Unvisited
                if (dfsHasCycle(adj, i, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfsHasCycle(List<List<Integer>> adj, int node, int[] color) {
        color[node] = 1; // Mark as gray (currently processing)

        for (int neighbor : adj.get(node)) {
            if (color[neighbor] == 1)
                return true; // Gray node found → back edge → return true;

            // If neighhbor is unvisites and recursive call returns true means has cycle
            if (color[neighbor] == 0 && dfsHasCycle(adj, neighbor, color))
                return true;
        }

        color[node] = 2; // Mark as black (completely processed) meaning no cycle found
        return false;
    }

    // ============== Cycle detection in UNDIRECTED graph ============== //
    public static boolean hasCycleUndirected(List<List<Integer>> adj) {
        if (adj == null || adj.isEmpty())
            return false; // Empty or null graph

        int n = adj.size();
        boolean[] visited = new boolean[n];

        // Iterate over all nodes
        for (int i = 0; i < n; i++)
            if (!visited[i])
                if (dfsHasCycleUndirected(adj, i, visited, -1))
                    return true; // Cycle found
        return false; // No cycle found
    }

    private static boolean dfsHasCycleUndirected(List<List<Integer>> adj, int node, boolean[] visited, int parent) {
        visited[node] = true;

        // Explore neighbors
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                // Recursively explore neighbor
                if (dfsHasCycleUndirected(adj, neighbor, visited, node))
                    return true;
            } else if (neighbor != parent)
                return true; // If visited neighbor is not the parent, cycle detected
        }

        return false; // No cycle found
    }

    // =============================
    // 5. TOPOLOGICAL SORT PATTERNS
    // =============================

    /*
     * TOPOLOGICAL SORT PATTERN RECOGNITION:
     * What is topological sort?
     * - It's a linear ordering of vertices in a directed acyclic graph (DAG)
     * - For every directed edge u → v, u comes before v in the ordering
     * - eg. If you have task with dependencies, topological sort gives you a valid
     * order to complete them
     *
     * TWO APPROACHES:
     * 1. Kahn's Algorithm (BFS-based)
     * 2. DFS-based (post-order traversal)
     *
     * KAHN'S ALGORITHM INTUITION:
     * - Find nodes with no incoming edges (indegree = 0)
     * - Remove them one by one, updating indegrees
     * - If we can't remove all nodes → cycle exists
     *
     * When do I need topological sort?
     * - "Course prerequisites" → Topo sort
     * - "Task dependency" → Topo sort
     * - "Build order" → Topo sort
     * - Any "ordering with dependencies" → Topo sort
     *
     * KAHN'S APPROACH - "Forward Thinking"
     * Logic: "I have no dependencies left, so I can go now"
     * - Calculate indegree for each node (how many edges point TO this node)
     * - Start with nodes that have indegree 0 (no dependencies)
     * - Process them and reduce indegree of their neighbors
     * - Add neighbors to queue when their indegree becomes 0
     * - Builds result from left to right in correct topological order
     */
    public static List<Integer> topologicalSortKahn(List<List<Integer>> adj) {
        int n = adj.size();
        int[] indegree = new int[n];
        // Calculate indegree for each node
        for (int i = 0; i < n; i++)
            for (int neighbor : adj.get(i))
                indegree[neighbor]++;
        // Find all nodes with indegree 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++)
            if (indegree[i] == 0)
                queue.offer(i);
        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);
            // Remove current node and update indegrees
            for (int neighbor : adj.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0)
                    queue.offer(neighbor);
            }
        }
        if (result.size() != n)
            return new ArrayList<>(); // Cycle detected
        return result;
    }

    // ======= DFS-based Topological Sort ========== //
    /*
     * DFS APPROACH - "Backward Thinking"
     * Logic: "All my dependents are done, so I can go now"
     *
     * KEY DIFFERENCES FROM NORMAL DFS:
     * - Normal DFS: Process nodes when you VISIT them (pre-order)
     * - Topological DFS: Process nodes when you FINISH with them (post-order)
     *
     * NORMAL DFS:
     * void normalDFS(int node) {
     * visited[node] = true;
     * process(node); // ← Process IMMEDIATELY when visited
     * for (neighbor : adj.get(node)) if (!visited[neighbor]) normalDFS(neighbor);
     *
     * TOPOLOGICAL DFS:
     * void topologicalDFS(int node) {
     * visited[node] = true;
     * for (neighbor : adj.get(node)) if (!visited[neighbor])
     * topologicalDFS(neighbor);
     * stack.push(node); // ← Process AFTER visiting all neighbors
     * }
     *
     * WHY THE STACK?
     * - We visit nodes in any order (doesn't matter)
     * - We finish nodes in REVERSE topological order
     * - Stack reverses this to give correct topological order
     * - You might think about shirt, pants, shoes in any order
     * - But you can only FINISH putting on shoes after socks are done
     * - Stack ensures socks come before shoes in final order
     */
    public static List<Integer> topologicalSortDFS(List<List<Integer>> adj) {
        int n = adj.size();
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++)
            if (!visited[i])
                dfsTopological(adj, i, visited, stack);
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty())
            result.add(stack.pop());
        return result;
    }

    private static void dfsTopological(List<List<Integer>> adj, int node, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;
        for (int neighbor : adj.get(node))
            if (!visited[neighbor])
                dfsTopological(adj, neighbor, visited, stack);
        stack.push(node); // Push to stack AFTER processing all neighbors (post-order)
    }

    /*
     * SUMMARY:
     * - Kahn's: "I'm ready to start" (processes nodes when dependencies are met)
     * - DFS: "I'm completely done" (processes nodes when all dependents are
     * finished)
     * - Both guarantee: for every edge u → v, u comes before v in final ordering
     * - Kahn's builds result forward, DFS builds result backward then reverses
     */

    // =============================
    // 6. COMMON INTERVIEW PROBLEMS
    // =============================

    /*
     * PROBLEM PATTERN: "Number of Islands" type
     *
     * RECOGNITION SIGNALS:
     * - 2D grid/matrix
     * - "Connected components"
     * - "Count groups/clusters"
     *
     * APPROACH: DFS/BFS to mark connected components
     */

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;

        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfsMarkIsland(grid, i, j, rows, cols);
                }
            }
        }

        return count;
    }

    private static void dfsMarkIsland(char[][] grid, int i, int j, int rows, int cols) {
        if (i < 0 || i >= rows || j < 0 || j >= cols || grid[i][j] == '0') {
            return;
        }

        grid[i][j] = '0'; // Mark as visited

        // Explore 4 directions
        dfsMarkIsland(grid, i + 1, j, rows, cols);
        dfsMarkIsland(grid, i - 1, j, rows, cols);
        dfsMarkIsland(grid, i, j + 1, rows, cols);
        dfsMarkIsland(grid, i, j - 1, rows, cols);
    }

    /*
     * PROBLEM PATTERN: "Course Schedule" type
     *
     * RECOGNITION SIGNALS:
     * - Prerequisites/dependencies
     * - "Can all be completed"
     * - "Order of completion"
     *
     * APPROACH: Topological sort + cycle detection
     */

    public static boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++)
            adj.add(new ArrayList<>());

        // Build adjacency list
        for (int[] pre : prerequisites)
            adj.get(pre[1]).add(pre[0]); // pre[1] → pre[0]

        // Check if topological sort is possible (no cycle)
        return topologicalSortKahn(adj).size() == numCourses;
    }

    // =============================
    // 7. BOILERPLATE TEMPLATES
    // =============================

    /*
     * =============================================================================
     * BOILERPLATE TEMPLATES FOR QUICK RECALL
     * =============================================================================
     * These are the patterns you should memorize and recognize instantly:
     */

    // Template 1: Basic BFS
    public static void bfsTemplate(List<List<Integer>> adj, int start) {
        boolean[] visited = new boolean[adj.size()];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            // Process current

            for (int neighbor : adj.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
    }

    // Template 2: BFS with levels
    public static void bfsLevelsTemplate(List<List<Integer>> adj, int start) {
        boolean[] visited = new boolean[adj.size()];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(start);
        visited[start] = true;
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int current = queue.poll();
                // Process current at this level

                for (int neighbor : adj.get(current)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        queue.offer(neighbor);
                    }
                }
            }
            level++;
        }
    }

    // Template 3: DFS Recursive
    public static void dfsTemplate(List<List<Integer>> adj, int current, boolean[] visited) {
        visited[current] = true;
        // Process current

        for (int neighbor : adj.get(current)) {
            if (!visited[neighbor]) {
                dfsTemplate(adj, neighbor, visited);
            }
        }
    }

    // Template 4: Cycle Detection (Directed)
    public static boolean cycleDetectionTemplate(List<List<Integer>> adj) {
        int n = adj.size();
        int[] color = new int[n]; // 0=white, 1=gray, 2=black

        for (int i = 0; i < n; i++) {
            if (color[i] == 0 && hasCycleDFS(adj, i, color)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasCycleDFS(List<List<Integer>> adj, int node, int[] color) {
        color[node] = 1; // Gray

        for (int neighbor : adj.get(node)) {
            if (color[neighbor] == 1 ||
                    (color[neighbor] == 0 && hasCycleDFS(adj, neighbor, color))) {
                return true;
            }
        }

        color[node] = 2; // Black
        return false;
    }

    // Template 5: Topological Sort (Kahn's)
    public static List<Integer> topoSortTemplate(List<List<Integer>> adj) {
        int n = adj.size();
        int[] indegree = new int[n];

        // Calculate indegrees
        for (int i = 0; i < n; i++) {
            for (int neighbor : adj.get(i)) {
                indegree[neighbor]++;
            }
        }

        // Find nodes with indegree 0
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);

            for (int neighbor : adj.get(current)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return result.size() == n ? result : new ArrayList<>();
    }

    /*
     * =============================================================================
     * PATTERN RECOGNITION CHEAT SHEET
     * =============================================================================
     *
     * WHEN YOU SEE → THINK
     *
     * "Shortest path" + unweighted → BFS
     * "All paths" → DFS
     * "Prerequisites" → Topological Sort
     * "Can complete all" → Cycle Detection
     * "Connected components" → DFS/BFS
     * "Level by level" → BFS with levels
     * "Islands/clusters" → DFS on 2D grid
     * "Dependencies" → Topological Sort
     * "Reachability" → DFS/BFS
     *
     * =============================================================================
     */

    // Example usage and testing
    public static void main(String[] args) {
        // Test with a simple graph
        int[][] edges = { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 3, 1 } };
        List<List<Integer>> adj = buildAdjacencyList(4, edges);

        System.out.println("Graph created successfully!");
        System.out.println("Has cycle: " + hasCycleDirected(adj));

        // Test BFS
        System.out.print("BFS from 0: ");
        bfsBasic(adj, 0);
        System.out.println();

        // Test DFS
        System.out.print("DFS from 0: ");
        boolean[] visited = new boolean[4];
        dfsRecursive(adj, 0, visited);
        System.out.println();
    }
}