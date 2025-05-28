package com.monal.Basics;

import java.util.*;

/*
 * All of these will be explained in detail in the code comments below.
 * 1. GRAPH = NODES + EDGES
 * - Nodes are like cities, edges are like roads
 *
 * 2. TWO REPRESENTATIONS:
 * - Adjacency List: List of neighbors for each node (more memory efficient)
 * - Adjacency Matrix: 2D array (easier for some operations)
 *
 * 3. TWO TRAVERSAL METHODS:
 * - BFS: Use Queue, visit level by level (good for shortest path)
 * - DFS: Use recursion/stack, go deep first (good for detecting cycles)
 *
 * 4. COMMON PATTERNS:
 * - Always mark nodes as visited to avoid infinite loops
 * - BFS uses Queue, DFS uses Stack/Recursion
 * - Most graph problems involve traversing and checking conditions
 *
 * 5. TYPICAL EASY PROBLEMS:
 * - Is there a path between A and B?
 * - How many separate components are there?
 * - What's the shortest path? (BFS for unweighted graphs)
 *
 */
public class Basics {
    // ======================================================== //
    // ---------- Two Main Representations of Graphs ---------- //
    // ======================================================== //

    // ========== 1. ADJACENCY LIST DEMONSTRATION ============ //
    public static void demonstrateAdjacencyList() {
        System.out.println("3a. ADJACENCY LIST EXAMPLE:");

        // Create adjacency list using ArrayList of ArrayLists
        // Given graph
        /*
         * 0
         * / \
         * 4 -- 1 2
         * \ /
         * 3
         */
        // This represents: 0->1,2 1->3 2->(empty) 3->4 4->1
        ArrayList<ArrayList<Integer>> adjList = new ArrayList<>();

        // Initialize lists for each node (0 to 4)
        for (int i = 0; i < 5; i++) {
            adjList.add(new ArrayList<>());
        }

        // Add edges: 0->1, 0->2, 1->3, 3->4, 4->1
        adjList.get(0).add(1); // 0 is connected to 1
        adjList.get(0).add(2); // 0 is connected to 2
        adjList.get(1).add(3); // 1 is connected to 3
        adjList.get(3).add(4); // 3 is connected to 4
        adjList.get(4).add(1); // 4 is connected to 1

        // Print the adjacency list
        System.out.println("Adjacency List representation:");
        for (int i = 0; i < adjList.size(); i++) {
            System.out.println("Node " + i + " -> " + adjList.get(i));
        }
        System.out.println();
    }

    // ========== 2. ADJACENCY MATRIX DEMONSTRATION ==========
    public static void demonstrateAdjacencyMatrix() {
        System.out.println("3b. ADJACENCY MATRIX EXAMPLE:");

        // Create 5x5 matrix for nodes 0,1,2,3,4
        int[][] adjMatrix = new int[5][5];

        // Given graph
        /*
         * 0
         * / \
         * 4 -- 1 2
         * \ /
         * 3
         */
        // Add same edges: 0->1, 0->2, 1->3, 3->4, 4->1
        adjMatrix[0][1] = 1; // Edge from 0 to 1
        adjMatrix[0][2] = 1; // Edge from 0 to 2
        adjMatrix[1][3] = 1; // Edge from 1 to 3
        adjMatrix[3][4] = 1; // Edge from 3 to 4
        adjMatrix[4][1] = 1; // Edge from 4 to 1

        // Print the matrix
        System.out.println("Adjacency Matrix representation:");
        System.out.println("   0 1 2 3 4");
        for (int i = 0; i < 5; i++) {
            System.out.print(i + ": ");
            for (int j = 0; j < 5; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("1 means edge exists, 0 means no edge");
        System.out.println();
    }

    // ======================================================== //
    // ------- Graph Traversal Algorithms (BFS & DFS) --------- //
    // ======================================================== //
    /*
     * 4. GRAPH TRAVERSAL ALGORITHMS
     * - BFS (Breadth-First Search): Uses Queue, visits nodes level by level
     * - DFS (Depth-First Search): Uses Stack/Recursion, goes as deep as possible
     * first
     */
    public static void demonstrateTraversals() {
        // Create a simple graph:
        /*
         * 0
         * / \
         * 1 3
         * /
         * 2
         * /
         * 4
         */
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            graph.add(new ArrayList<>());
        }

        // Add edges (undirected graph)
        addEdge(graph, 0, 1);
        addEdge(graph, 0, 3);
        addEdge(graph, 1, 2);
        addEdge(graph, 2, 4);

        System.out.println("5. BFS (Breadth-First Search) - Level by Level:");
        System.out.println("Graph: 0-1-2");
        System.out.println("       |   |");
        System.out.println("       3   4");

        bfs(graph, 0);
        System.out.println();

        System.out.println("6. DFS (Depth-First Search) - Go Deep First:");
        dfs(graph, 0, new boolean[5]);
        System.out.println();
    }

    // Helper method to add undirected edge
    /**
     * Adds an undirected edge between nodes u and v in the graph.
     * This means both u->v and v->u will be added to the adjacency list.
     *
     * @param graph
     * @param u     - the first node (from which v is connected)
     * @param v     - the second node (to which u is connected)
     */
    public static void addEdge(ArrayList<ArrayList<Integer>> graph, int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    // =================== BFS IMPLEMENTATION ==================== //
    public static void bfs(ArrayList<ArrayList<Integer>> graph, int start) {
        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        // Start BFS from given node
        queue.add(start);
        visited[start] = true;
        System.out.print("BFS Order: ");

        while (!queue.isEmpty()) {
            int node = queue.poll(); // Remove from front of queue
            System.out.print(node + " ");

            // Visit all neighbors of current node
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor); // Add to back of queue
                }
            }
        }
        System.out.println();
    }

    // =================== DFS IMPLEMENTATION ==================== //
    public static void dfs(ArrayList<ArrayList<Integer>> graph, int node, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");

        // Visit all unvisited neighbors
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(graph, neighbor, visited); // Recursive call
            }
        }

        if (node == 0)
            System.out.println("(DFS Order)");
    }

    // ========================================================== //
    // ---------- Finding Path Between Two Nodes (BFS) ---------- //
    // ========================================================== //

    public static void demonstratePathFinding() {

        System.out.println("8. FINDING PATH BETWEEN TWO NODES:");

        // Create same graph as before
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            graph.add(new ArrayList<>());
        }

        addEdge(graph, 0, 1);
        addEdge(graph, 1, 2);
        addEdge(graph, 0, 3);
        addEdge(graph, 2, 4);

        // Check if path exists from 0 to 4
        boolean pathExists = hasPath(graph, 0, 4);
        System.out.println("Path from 0 to 4 exists: " + pathExists);

        // Check if path exists from 3 to 4
        pathExists = hasPath(graph, 3, 4);
        System.out.println("Path from 3 to 4 exists: " + pathExists);
    }

    /**
     * Checks if there is a path between start and end nodes in the graph
     * using BFS. This method returns true if a path exists from start to end
     *
     * @param graph - The adjacency list representation of the graph
     * @param start - The starting node from which to search for a path
     * @param end   - The target node to which we want to find a path
     * @return - TRUE (path exists) or FALSE (no path found)
     */
    public static boolean hasPath(ArrayList<ArrayList<Integer>> graph, int start, int end) {
        if (start == end)
            return true;

        boolean[] visited = new boolean[graph.size()];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(start);
        visited[start] = true;

        while (!queue.isEmpty()) {
            int node = queue.poll();

            // Check all neighbors
            for (int neighbor : graph.get(node)) {
                if (neighbor == end) {
                    return true; // Found the target!
                }

                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }

        return false; // No path found
    }

    public static void main(String[] args) {
        System.out.println("=== GRAPH BASICS - PREREQUISITES FOR PROBLEM SOLVING ===\n");

        // =============== WHAT IS A GRAPH? =============== //
        System.out.println("1. WHAT IS A GRAPH?");
        System.out.println("A graph is a collection of NODES (vertices) connected by EDGES");
        System.out.println("Think of it like a network - cities connected by roads, people connected by friendships");
        System.out.println();

        // ========== HOW TO REPRESENT A GRAPH? ========== //
        System.out.println("2. HOW TO REPRESENT A GRAPH IN CODE?");
        System.out.println("There are 2 main ways:");
        System.out.println("a) Adjacency List - Each node stores list of its neighbors");
        System.out.println("b) Adjacency Matrix - 2D array where matrix[i][j] = 1 means edge from i to j");
        System.out.println();

        demonstrateAdjacencyList();
        demonstrateAdjacencyMatrix();

        // ============ HOW TO TRAVERSE GRAPH? ============ //
        System.out.println("\n4. GRAPH TRAVERSAL - HOW TO VISIT ALL NODES?");
        System.out.println("Two main algorithms:");
        System.out.println("a) BFS (Breadth-First Search) - Visit level by level (like ripples in water)");
        System.out.println("b) DFS (Depth-First Search) - Go as deep as possible, then backtrack");
        System.out.println();
        demonstrateTraversals();

        // ============= COMMON GRAPH PATTERNS ============== //
        System.out.println("\n7. COMMON EASY GRAPH PROBLEMS:");
        System.out.println("- Find if path exists between two nodes");
        System.out.println("- Count number of connected components");
        System.out.println("- Detect cycle in graph");
        System.out.println("- Find shortest path (unweighted)");
        System.out.println();
        demonstratePathFinding();

        System.out.println("\n=== END OF BASICS ===");
        System.out.println("Now you're ready to solve easy graph problems!");
    }
}
