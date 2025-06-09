package com.monal.Graphs.MST_DisjointSet;

import java.util.*;

public class MST_DisjointSet {
    /**
     * PREREQUISITES: What students need to know before MST and Disjoint Set
     * 1. Graph representation (Adjacency List, Edge List)
     * 2. Basic graph traversal (DFS/BFS)
     * 3. Sorting algorithms
     * 4. Basic understanding of trees and cycles
     */
    class Prerequisites {

        // Edge class - fundamental building block for MST algorithms
        static class Edge implements Comparable<Edge> {
            int src, dest, weight;

            Edge(int src, int dest, int weight) {
                this.src = src;
                this.dest = dest;
                this.weight = weight;
            }

            // For sorting edges by weight (used in Kruskal's)
            @Override
            public int compareTo(Edge other) {
                return Integer.compare(this.weight, other.weight);
            }

            @Override
            public String toString() {
                return "(" + src + "->" + dest + ", w=" + weight + ")";
            }
        }

        // Graph representation for MST problems
        static class Graph {
            int vertices;
            List<Edge> edges;
            List<List<Edge>> adjList;

            Graph(int v) {
                this.vertices = v;
                this.edges = new ArrayList<>();
                this.adjList = new ArrayList<>();
                for (int i = 0; i < v; i++) {
                    adjList.add(new ArrayList<>());
                }
            }

            void addEdge(int src, int dest, int weight) {
                Edge edge = new Edge(src, dest, weight);
                edges.add(edge);
                // For undirected graph, add both directions in adjacency list
                adjList.get(src).add(new Edge(src, dest, weight));
                adjList.get(dest).add(new Edge(dest, src, weight));
            }

            void printGraph() {
                System.out.println("Graph Edges:");
                for (Edge e : edges) {
                    System.out.println(e);
                }
            }
        }
    }

    /**
     * CORE CONCEPTS: The theoretical foundation
     * Students must understand these concepts before solving problems
     */
    class Concepts {

        /**
         * DISJOINT SET (Union-Find) Data Structure
         *
         * Purpose: Efficiently determine if two elements belong to same set
         * and merge two sets. Essential for cycle detection in undirected graphs.
         *
         * Time Complexity: Nearly O(1) with path compression and union by rank
         * Space Complexity: O(n)
         *
         * Key Operations:
         * 1. Find: Get the representative/parent of a set
         * 2. Union: Merge two sets
         *
         * Optimizations:
         * 1. Path Compression: Make all nodes point directly to root
         * 2. Union by Rank: Always attach smaller tree under root of larger tree
         */
        static class DisjointSetUnionByRank {
            int[] parent, rank;

            DisjointSetUnionByRank(int n) {
                parent = new int[n];
                rank = new int[n];
                // Initially, each element is its own parent (separate sets)
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    rank[i] = 0; // Initial rank is 0
                }
            }

            // Find with path compression
            int findParent(int node) {
                if (parent[node] != node) {
                    // Path compression: make parent[node] point directly to root
                    parent[node] = findParent(parent[node]);
                }
                return parent[node];
            }

            // Union by rank
            void union(int u, int v) {
                int rootU = findParent(u);
                int rootV = findParent(v);

                if (rootU == rootV)
                    return; // Already in same set

                // Attach smaller rank tree under root of higher rank tree
                if (rank[rootU] < rank[rootV]) {
                    parent[rootU] = rootV;
                } else if (rank[rootU] > rank[rootV]) {
                    parent[rootV] = rootU;
                } else {
                    // If ranks are same, make one root and increment its rank
                    parent[rootV] = rootU;
                    rank[rootU]++;
                }
            }

            // Check if two nodes are in same component
            boolean isConnected(int u, int v) {
                return findParent(u) == findParent(v);
            }
        }

        /**
         * Alternative: Disjoint Set with Union by Size
         * Sometimes size-based union gives better practical performance
         */
        static class DisjointSetUnionBySize {
            int[] parent, size;

            DisjointSetUnionBySize(int n) {
                parent = new int[n];
                size = new int[n];
                for (int i = 0; i < n; i++) {
                    parent[i] = i;
                    size[i] = 1; // Each set initially has size 1
                }
            }

            int findParent(int node) {
                if (parent[node] != node) {
                    parent[node] = findParent(parent[node]);
                }
                return parent[node];
            }

            void union(int u, int v) {
                int rootU = findParent(u);
                int rootV = findParent(v);

                if (rootU == rootV)
                    return;

                // Attach smaller size tree under root of larger size tree
                if (size[rootU] < size[rootV]) {
                    parent[rootU] = rootV;
                    size[rootV] += size[rootU];
                } else {
                    parent[rootV] = rootU;
                    size[rootU] += size[rootV];
                }
            }
        }

        /**
         * MINIMUM SPANNING TREE (MST)
         *
         * Definition: A spanning tree of a graph with minimum total edge weight
         * Properties:
         * 1. Connects all vertices
         * 2. Has exactly (V-1) edges
         * 3. No cycles
         * 4. Minimum total weight among all possible spanning trees
         *
         * Applications:
         * 1. Network design (minimize cable cost)
         * 2. Clustering algorithms
         * 3. Circuit design
         */

        /**
         * KRUSKAL'S ALGORITHM
         *
         * Strategy: Greedy approach - sort edges by weight, add edge if it doesn't
         * create cycle
         *
         * Algorithm:
         * 1. Sort all edges by weight (ascending)
         * 2. Initialize disjoint set
         * 3. For each edge in sorted order:
         * - If adding edge doesn't create cycle (endpoints in different sets)
         * - Add edge to MST and union the sets
         * 4. Stop when we have (V-1) edges
         *
         * Time Complexity: O(E log E) - dominated by sorting
         * Space Complexity: O(V) for disjoint set
         */
        static class KruskalsAlgorithm {

            List<Prerequisites.Edge> findMST(Prerequisites.Graph graph) {
                List<Prerequisites.Edge> mst = new ArrayList<>();

                // Step 1: Sort edges by weight
                Collections.sort(graph.edges);

                // Step 2: Initialize disjoint set
                DisjointSetUnionByRank ds = new DisjointSetUnionByRank(graph.vertices);

                // Step 3: Process edges in sorted order
                for (Prerequisites.Edge edge : graph.edges) {
                    int srcParent = ds.findParent(edge.src);
                    int destParent = ds.findParent(edge.dest);

                    // If adding this edge doesn't create cycle
                    if (srcParent != destParent) {
                        mst.add(edge);
                        ds.union(edge.src, edge.dest);

                        // MST complete when we have (V-1) edges
                        if (mst.size() == graph.vertices - 1) {
                            break;
                        }
                    }
                }

                return mst;
            }
        }

        /**
         * PRIM'S ALGORITHM
         *
         * Strategy: Greedy approach - start from any vertex, always add minimum weight
         * edge
         * that connects a vertex in MST to a vertex outside MST
         *
         * Algorithm:
         * 1. Start with any vertex in MST
         * 2. Use priority queue to get minimum weight edge from MST to non-MST vertex
         * 3. Add the minimum edge and the new vertex to MST
         * 4. Repeat until all vertices are in MST
         *
         * Time Complexity: O(E log V) with priority queue
         * Space Complexity: O(V)
         */
        static class PrimsAlgorithm {

            List<Prerequisites.Edge> findMST(Prerequisites.Graph graph) {
                List<Prerequisites.Edge> mst = new ArrayList<>();
                boolean[] inMST = new boolean[graph.vertices];

                // Priority queue to get minimum weight edge
                // [weight, vertex, parent]
                PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

                // Start from vertex 0
                pq.offer(new int[] { 0, 0, -1 });

                while (!pq.isEmpty()) {
                    int[] current = pq.poll();
                    int weight = current[0];
                    int vertex = current[1];
                    int parent = current[2];

                    // Skip if already in MST
                    if (inMST[vertex])
                        continue;

                    // Add vertex to MST
                    inMST[vertex] = true;

                    // Add edge to MST (except for starting vertex)
                    if (parent != -1) {
                        mst.add(new Prerequisites.Edge(parent, vertex, weight));
                    }

                    // Add all adjacent edges to priority queue
                    for (Prerequisites.Edge edge : graph.adjList.get(vertex)) {
                        if (!inMST[edge.dest]) {
                            pq.offer(new int[] { edge.weight, edge.dest, vertex });
                        }
                    }
                }

                return mst;
            }
        }

        /**
         * When to use Kruskal's vs Prim's:
         *
         * Kruskal's:
         * - Better for sparse graphs (fewer edges)
         * - When you need to work with edge list
         * - When edges are already sorted
         *
         * Prim's:
         * - Better for dense graphs (more edges)
         * - When you have adjacency list representation
         * - When you want to build MST incrementally from a specific vertex
         */
    }

    /**
     * HARD PROBLEMS: Real interview questions that test deep understanding
     * These problems combine MST/Disjoint Set with other concepts
     */
    class HardProblems {

        /**
         * PROBLEM 1: Number of Operations to Make Network Connected
         *
         * You have n computers numbered 0 to n-1. You're given connections where
         * connections[i] = [a, b] represents a connection between computers a and b.
         *
         * You can remove any connection and reconnect it between any two computers.
         * Return minimum number of operations to make all computers connected.
         *
         * Key Insight: You need at least (n-1) edges to connect n nodes.
         * If you have more edges, the extra edges can be redistributed.
         *
         * Approach:
         * 1. Count total edges and connected components using Disjoint Set
         * 2. Check if we have enough edges: edges >= (n-1)
         * 3. Return (components - 1) operations needed
         */
        int makeConnected(int n, int[][] connections) {
            // Need at least (n-1) edges to connect n computers
            if (connections.length < n - 1) {
                return -1;
            }

            Concepts.DisjointSetUnionByRank ds = new Concepts.DisjointSetUnionByRank(n);

            // Process all connections
            for (int[] connection : connections) {
                ds.union(connection[0], connection[1]);
            }

            // Count number of connected components
            Set<Integer> components = new HashSet<>();
            for (int i = 0; i < n; i++) {
                components.add(ds.findParent(i));
            }

            // Need (components - 1) operations to connect all components
            return components.size() - 1;
        }

        /**
         * PROBLEM 2: Most Stones Removed with Same Row or Column
         *
         * On a 2D plane, we place stones at some integer coordinate points.
         * A stone can be removed if it shares either the same row or column with
         * another stone.
         * Return the maximum number of stones that can be removed.
         *
         * Key Insight: Stones in same row/column form connected components.
         * In each component of size k, we can remove (k-1) stones.
         *
         * Approach:
         * 1. Use Disjoint Set to group stones by row/column
         * 2. Stones at (r1,c1) and (r2,c2) are connected if r1==r2 or c1==c2
         * 3. For each component of size k, we can remove (k-1) stones
         */
        int removeStones(int[][] stones) {
            int n = stones.length;
            Concepts.DisjointSetUnionByRank ds = new Concepts.DisjointSetUnionByRank(n);

            // Connect stones that share row or column
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    // If stones share same row or column, union them
                    if (stones[i][0] == stones[j][0] || stones[i][1] == stones[j][1]) {
                        ds.union(i, j);
                    }
                }
            }

            // Count components and their sizes
            Map<Integer, Integer> componentSize = new HashMap<>();
            for (int i = 0; i < n; i++) {
                int parent = ds.findParent(i);
                componentSize.put(parent, componentSize.getOrDefault(parent, 0) + 1);
            }

            // In each component of size k, we can remove (k-1) stones
            int removable = 0;
            for (int size : componentSize.values()) {
                removable += (size - 1);
            }

            return removable;
        }

        /**
         * PROBLEM 3: Accounts Merge
         *
         * Given accounts where accounts[i] is a list with the first element being
         * the name and rest being emails owned by this account.
         *
         * Two accounts belong to the same person if they have common email.
         * Merge accounts that belong to the same person.
         *
         * Key Insight: Accounts with common emails should be merged using Union-Find.
         *
         * Approach:
         * 1. Map each email to its first occurrence (account index)
         * 2. Union accounts that share emails
         * 3. Group emails by their root account
         */
        List<List<String>> accountsMerge(List<List<String>> accounts) {
            int n = accounts.size();
            Concepts.DisjointSetUnionByRank ds = new Concepts.DisjointSetUnionByRank(n);

            // Map email to its first account index
            Map<String, Integer> emailToAccount = new HashMap<>();

            for (int i = 0; i < n; i++) {
                for (int j = 1; j < accounts.get(i).size(); j++) {
                    String email = accounts.get(i).get(j);
                    if (emailToAccount.containsKey(email)) {
                        // Merge this account with the account that has this email
                        ds.union(i, emailToAccount.get(email));
                    } else {
                        emailToAccount.put(email, i);
                    }
                }
            }

            // Group emails by root account
            Map<Integer, Set<String>> mergedAccounts = new HashMap<>();
            for (int i = 0; i < n; i++) {
                int root = ds.findParent(i);
                mergedAccounts.putIfAbsent(root, new TreeSet<>()); // TreeSet for sorting

                for (int j = 1; j < accounts.get(i).size(); j++) {
                    mergedAccounts.get(root).add(accounts.get(i).get(j));
                }
            }

            // Build result
            List<List<String>> result = new ArrayList<>();
            for (int root : mergedAccounts.keySet()) {
                List<String> account = new ArrayList<>();
                account.add(accounts.get(root).get(0)); // Name
                account.addAll(mergedAccounts.get(root)); // Sorted emails
                result.add(account);
            }

            return result;
        }

        /**
         * PROBLEM 4: Making a Large Island
         *
         * You have a binary grid where 1 represents land and 0 represents water.
         * You can change at most one 0 to 1. Return the size of the largest island.
         *
         * Key Insight:
         * 1. First, find all existing islands and their sizes
         * 2. For each 0, check adjacent islands and calculate potential size
         *
         * Advanced Approach using Disjoint Set:
         * 1. Use Disjoint Set to group connected land cells
         * 2. For each water cell, check adjacent land components
         * 3. Calculate max possible island size by flipping this water to land
         */
        int largestIsland(int[][] grid) {
            int n = grid.length;
            Concepts.DisjointSetUnionBySize ds = new Concepts.DisjointSetUnionBySize(n * n);

            int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

            // Step 1: Union all connected land cells
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        for (int[] dir : directions) {
                            int ni = i + dir[0];
                            int nj = j + dir[1];
                            if (ni >= 0 && ni < n && nj >= 0 && nj < n && grid[ni][nj] == 1) {
                                ds.union(i * n + j, ni * n + nj);
                            }
                        }
                    }
                }
            }

            // Step 2: Calculate component sizes
            Map<Integer, Integer> componentSize = new HashMap<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        int root = ds.findParent(i * n + j);
                        componentSize.put(root, componentSize.getOrDefault(root, 0) + 1);
                    }
                }
            }

            int maxIslandSize = componentSize.isEmpty() ? 0 : Collections.max(componentSize.values());

            // Step 3: Try flipping each water cell to land
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 0) {
                        Set<Integer> adjacentComponents = new HashSet<>();
                        for (int[] dir : directions) {
                            int ni = i + dir[0];
                            int nj = j + dir[1];
                            if (ni >= 0 && ni < n && nj >= 0 && nj < n && grid[ni][nj] == 1) {
                                adjacentComponents.add(ds.findParent(ni * n + nj));
                            }
                        }

                        int newIslandSize = 1; // The flipped cell itself
                        for (int component : adjacentComponents) {
                            newIslandSize += componentSize.get(component);
                        }

                        maxIslandSize = Math.max(maxIslandSize, newIslandSize);
                    }
                }
            }

            return maxIslandSize;
        }

        /**
         * PROBLEM 5: Swim in Rising Water
         *
         * You are given an n x n integer matrix grid where each value grid[i][j]
         * represents the elevation at that point (i, j).
         *
         * Rain starts to fall. Water can flow from a cell to another one if the
         * current cell's elevation >= neighbor's elevation.
         *
         * Find the minimum time for water to flow from top-left to bottom-right.
         *
         * Key Insight: This is essentially finding minimum bottleneck path.
         * We can use binary search + Union-Find or Kruskal's-like approach.
         *
         * Approach using Union-Find:
         * 1. Sort all cells by elevation
         * 2. Process cells in order, connecting adjacent cells
         * 3. Stop when top-left and bottom-right are connected
         */
        int swimInWater(int[][] grid) {
            int n = grid.length;
            Concepts.DisjointSetUnionByRank ds = new Concepts.DisjointSetUnionByRank(n * n);

            // Create list of cells sorted by elevation
            List<int[]> cells = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    cells.add(new int[] { grid[i][j], i, j });
                }
            }
            cells.sort((a, b) -> a[0] - b[0]);

            int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
            boolean[][] visited = new boolean[n][n];

            // Process cells in order of elevation
            for (int[] cell : cells) {
                int elevation = cell[0];
                int i = cell[1];
                int j = cell[2];

                visited[i][j] = true;

                // Connect with adjacent visited cells
                for (int[] dir : directions) {
                    int ni = i + dir[0];
                    int nj = j + dir[1];
                    if (ni >= 0 && ni < n && nj >= 0 && nj < n && visited[ni][nj]) {
                        ds.union(i * n + j, ni * n + nj);
                    }
                }

                // Check if top-left and bottom-right are connected
                if (ds.isConnected(0, n * n - 1)) {
                    return elevation;
                }
            }

            return -1; // Should never reach here
        }
    }

    /**
     * ILLUSTRATIONS: Step-by-step examples to solidify understanding
     */
    class Illustrations {

        void demonstrateDisjointSet() {
            System.out.println("=== DISJOINT SET DEMONSTRATION ===");

            Concepts.DisjointSetUnionByRank ds = new Concepts.DisjointSetUnionByRank(6);

            System.out.println("Initial state: Each element is its own parent");
            for (int i = 0; i < 6; i++) {
                System.out.println("Parent of " + i + " = " + ds.findParent(i));
            }

            System.out.println("\nPerforming unions:");
            System.out.println("Union(0, 1)");
            ds.union(0, 1);
            System.out.println("Union(2, 3)");
            ds.union(2, 3);
            System.out.println("Union(4, 5)");
            ds.union(4, 5);

            System.out.println("\nAfter unions:");
            for (int i = 0; i < 6; i++) {
                System.out.println("Parent of " + i + " = " + ds.findParent(i));
            }

            System.out.println("\nConnectivity checks:");
            System.out.println("Are 0 and 1 connected? " + ds.isConnected(0, 1));
            System.out.println("Are 0 and 2 connected? " + ds.isConnected(0, 2));

            System.out.println("Union(1, 2) - connecting two components");
            ds.union(1, 2);
            System.out.println("Are 0 and 3 connected now? " + ds.isConnected(0, 3));
        }

        void demonstrateKruskalsAlgorithm() {
            System.out.println("\n\n=== KRUSKAL'S ALGORITHM DEMONSTRATION ===");

            // Create a sample graph
            Prerequisites.Graph graph = new Prerequisites.Graph(5);
            graph.addEdge(0, 1, 2);
            graph.addEdge(0, 3, 6);
            graph.addEdge(1, 2, 3);
            graph.addEdge(1, 3, 8);
            graph.addEdge(1, 4, 5);
            graph.addEdge(2, 4, 7);
            graph.addEdge(3, 4, 9);

            System.out.println("Original Graph:");
            graph.printGraph();

            Concepts.KruskalsAlgorithm kruskal = new Concepts.KruskalsAlgorithm();
            List<Prerequisites.Edge> mst = kruskal.findMST(graph);

            System.out.println("\nMinimum Spanning Tree (Kruskal's):");
            int totalWeight = 0;
            for (Prerequisites.Edge edge : mst) {
                System.out.println(edge);
                totalWeight += edge.weight;
            }
            System.out.println("Total MST Weight: " + totalWeight);
        }

        void demonstratePrimsAlgorithm() {
            System.out.println("\n\n=== PRIM'S ALGORITHM DEMONSTRATION ===");

            Prerequisites.Graph graph = new Prerequisites.Graph(5);
            graph.addEdge(0, 1, 2);
            graph.addEdge(0, 3, 6);
            graph.addEdge(1, 2, 3);
            graph.addEdge(1, 3, 8);
            graph.addEdge(1, 4, 5);
            graph.addEdge(2, 4, 7);
            graph.addEdge(3, 4, 9);

            Concepts.PrimsAlgorithm prims = new Concepts.PrimsAlgorithm();
            List<Prerequisites.Edge> mst = prims.findMST(graph);

            System.out.println("Minimum Spanning Tree (Prim's):");
            int totalWeight = 0;
            for (Prerequisites.Edge edge : mst) {
                System.out.println(edge);
                totalWeight += edge.weight;
            }
            System.out.println("Total MST Weight: " + totalWeight);
        }
    }

    public static void main(String[] args) {
        MST_DisjointSet tutorial = new MST_DisjointSet();

        // Run illustrations to show how algorithms work
        tutorial.new Illustrations().demonstrateDisjointSet();
        tutorial.new Illustrations().demonstrateKruskalsAlgorithm();
        tutorial.new Illustrations().demonstratePrimsAlgorithm();

        // Test hard problems
        System.out.println("\n\n=== TESTING HARD PROBLEMS ===");

        HardProblems problems = tutorial.new HardProblems();

        // Test 1: Network Connection
        int[][] connections1 = { { 0, 1 }, { 0, 2 }, { 1, 2 } };
        System.out.println("Network connection operations needed: " +
                problems.makeConnected(4, connections1));

        // Test 2: Stone Removal
        int[][] stones = { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 2 }, { 2, 1 }, { 2, 2 } };
        System.out.println("Maximum stones removable: " +
                problems.removeStones(stones));

        // Test 3: Swim in Water
        int[][] water = { { 0, 2 }, { 1, 3 } };
        System.out.println("Minimum time to swim: " +
                problems.swimInWater(water));

        System.out.println("\n=== CONCEPTS COVERED ===");
        System.out.println("✓ Disjoint Set (Union by Rank & Size)");
        System.out.println("✓ Path Compression Optimization");
        System.out.println("✓ Kruskal's Algorithm for MST");
        System.out.println("✓ Prim's Algorithm for MST");
        System.out.println("✓ Real-world problem applications");
        System.out.println("✓ Advanced problem-solving patterns");

        System.out.println("\n=== NEXT STEPS FOR STUDENTS ===");
        System.out.println("1. Practice more Union-Find problems on LeetCode");
        System.out.println("2. Implement MST algorithms from scratch");
        System.out.println("3. Solve advanced graph problems combining multiple concepts");
        System.out.println("4. Study time/space complexity analysis deeply");
        System.out.println("5. Practice explaining these algorithms in interviews");
    }
}

/*
 *
 * 1. COMMON PATTERNS TO RECOGNIZE:
 * - Cycle detection in undirected graphs → Use Union-Find
 * - Minimum cost to connect all nodes → MST (Kruskal's/Prim's)
 * - Grouping elements with shared properties → Union-Find
 * - Finding connected components → Union-Find
 * - Network connectivity problems → Union-Find + Graph theory
 *
 * 2. OPTIMIZATION TECHNIQUES:
 * - Always use path compression in Union-Find
 * - Choose union by rank vs size based on problem requirements
 * - For MST: Kruskal's for sparse graphs, Prim's for dense graphs
 *
 * 3. EDGE CASES TO CONSIDER:
 * - Empty graph or single node
 * - Disconnected components (check if MST is possible)
 * - Self-loops and multiple edges between same vertices
 * - All edges having same weight
 * - Negative weights (MST algorithms work, but be careful with problem context)
 *
 * 4. TIME COMPLEXITY ANALYSIS:
 * - Union-Find with optimizations: O(α(n)) ≈ O(1) amortized
 * - Kruskal's Algorithm: O(E log E) dominated by sorting
 * - Prim's Algorithm: O(E log V) with priority queue
 * - Always analyze based on graph density (sparse vs dense)
 *
 * 5. SPACE COMPLEXITY:
 * - Union-Find: O(V) for parent and rank/size arrays
 * - Kruskal's: O(V) for disjoint set + O(E) for edge storage
 * - Prim's: O(V) for visited array + O(E) for priority queue
 *
 * 6. WHEN TO USE WHICH ALGORITHM:
 * - Need to check connectivity → Union-Find
 * - Need actual MST edges with weights → Kruskal's or Prim's
 * - Working with edge list → Kruskal's
 * - Working with adjacency list → Prim's
 * - Memory constraints → Consider space complexity differences
 *
 * 7. ADVANCED VARIATIONS TO PRACTICE:
 * - Minimum Spanning Forest (for disconnected graphs)
 * - Maximum Spanning Tree (negate weights)
 * - Second Minimum Spanning Tree
 * - Dynamic MST (edges added/removed online)
 * - Bottleneck Spanning Tree
 *
 * 8. CODING INTERVIEW TIPS:
 * - Always clarify if graph is directed or undirected
 * - Ask about constraints (number of nodes, edges, weight ranges)
 * - Discuss approach before coding
 * - Write clean, modular code with helper functions
 * - Test with edge cases
 * - Explain time/space complexity
 * - Be ready to optimize if asked
 *
 * 9. RELATED TOPICS TO STUDY NEXT:
 * - Shortest Path Algorithms (Dijkstra, Bellman-Ford, Floyd-Warshall)
 * - Network Flow (Max Flow, Min Cut)
 * - Strongly Connected Components
 * - Articulation Points and Bridges
 * - Tree algorithms and LCA (Lowest Common Ancestor)
 *
 * 10. PRACTICE PROBLEMS BY DIFFICULTY:
 *
 * EASY:
 * - Number of Connected Components in Undirected Graph
 * - Friend Circles / Number of Provinces
 * - Redundant Connection
 *
 * MEDIUM:
 * - Accounts Merge
 * - Most Stones Removed with Same Row or Column
 * - Number of Operations to Make Network Connected
 * - Satisfiability of Equality Equations
 * - Regions Cut by Slashes
 *
 * HARD:
 * - Making a Large Island
 * - Swim in Rising Water
 * - Minimize Malware Spread
 * - Number of Islands II (Online Algorithm)
 * - Connecting Cities with Minimum Cost
 *
 * Remember: The key to mastering these concepts is understanding the underlying
 * principles, not just memorizing code. Focus on when and why to use each
 * technique, and practice explaining your approach clearly during interviews.
 */