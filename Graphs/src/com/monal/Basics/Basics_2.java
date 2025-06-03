package com.monal.Basics;

import java.util.*;

/*
 * BASICS OF GRAPHS TECHNIQUES
 *
 * BEFORE CODING:
 * 1. Identify the graph type (directed/undirected, weighted/unweighted)
 * 2. Choose representation (adjacency list usually better)
 * 3. Identify the core algorithm needed
 * 4. Consider edge cases (empty graph, disconnected components)
 *
 * COMMON OPTIMIZATIONS:
 * 1. Early termination in BFS/DFS when target found
 * 2. Bidirectional search for shortest path
 * 3. Memoization in recursive solutions
 * 4. Using sets for O(1) lookups instead of lists
 *
 * TIME COMPLEXITIES TO REMEMBER:
 * - DFS/BFS: O(V + E)
 * - Dijkstra: O(E log V) with priority queue
 * - Union-Find: O(α(n)) ≈ O(1) with optimizations
 * - Topological Sort: O(V + E)
 *
 * SPACE COMPLEXITIES:
 * - Adjacency List: O(V + E)
 * - Adjacency Matrix: O(V²)
 * - Visited array: O(V)
 * - Recursion stack: O(V) in worst case
 *
 * KEY INSIGHTS:
 * 1. Many problems can be reduced to standard graph algorithms
 * 2. Grid problems are just implicit graphs
 * 3. State-space search can be modeled as graphs
 * 4. Dynamic programming often combines with graph algorithms
 * 5. Always consider if Union-Find can simplify the solution
 */
public class Basics_2 {

    public static void patternsCovered() {
        System.out.println("GRAPH PATTERNS COVERED:");
        System.out.println();

        System.out.println("1. SHORTEST PATH:");
        System.out.println("   - Unweighted: BFS");
        System.out.println("   - Weighted (positive): Dijkstra");
        System.out.println("   - With negative edges: Bellman-Ford");
        System.out.println();

        System.out.println("2. CONNECTIVITY:");
        System.out.println("   - Connected components: DFS/BFS or Union-Find");
        System.out.println("   - Bridge finding: Tarjan's algorithm");
        System.out.println("   - Articulation points: Tarjan's algorithm");
        System.out.println();

        System.out.println("3. ORDERING:");
        System.out.println("   - Topological sort: DFS or Kahn's algorithm");
        System.out.println("   - Course scheduling: Topological sort + cycle detection");
        System.out.println();

        System.out.println("4. CYCLE DETECTION:");
        System.out.println("   - Undirected: DFS with parent or Union-Find");
        System.out.println("   - Directed: 3-color DFS");
        System.out.println();

        System.out.println("5. SPECIAL GRAPHS:");
        System.out.println("   - Bipartite: 2-coloring with BFS/DFS");
        System.out.println("   - Trees: N nodes, N-1 edges, connected");
        System.out.println();

        System.out.println("6. GRID PROBLEMS:");
        System.out.println("   - Islands: DFS/BFS on 2D array");
        System.out.println("   - Shortest path in grid: BFS");
        System.out.println("   - Multi-source: Add all sources to queue initially");
        System.out.println();

        System.out.println("7. ADVANCED:");
        System.out.println("   - Minimum Spanning Tree: Kruskal's (Union-Find) or Prim's");
        System.out.println("   - Strongly Connected Components: Kosaraju's or Tarjan's");
        System.out.println("   - Maximum Flow: Ford-Fulkerson, Edmonds-Karp");
    }

    // ========== DIJKSTRA'S ALGORITHM FOR SHORTEST PATH ==========
    public static void demonstrateDijkstra() {
        System.out.println("1. DIJKSTRA'S ALGORITHM - SHORTEST PATH IN WEIGHTED GRAPHS");
        System.out.println("Use when: Finding shortest path with positive weights");
        System.out.println("Time: O(V²) or O(E log V) with priority queue\n");

        // Create weighted graph: 0->(1,4), 0->(2,2), 1->(3,1), 2->(3,5)
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            graph.add(new ArrayList<>());
        }

        graph.get(0).add(new Edge(1, 4));
        graph.get(0).add(new Edge(2, 2));
        graph.get(1).add(new Edge(3, 1));
        graph.get(2).add(new Edge(3, 5));

        int[] distances = dijkstra(graph, 0);
        System.out.println("Shortest distances from node 0:");
        for (int i = 0; i < distances.length; i++) {
            System.out.println("To node " + i + ": " + distances[i]);
        }
        System.out.println();
    }

    // Edge class for weighted graphs
    public static class Edge {
        int to, weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    // Dijkstra implementation
    public static int[] dijkstra(List<List<Edge>> graph, int start) {
        int n = graph.size();
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // Priority queue: [distance, node]
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[] { 0, start });

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int currDist = curr[0], currNode = curr[1];

            if (currDist > dist[currNode])
                continue;

            for (Edge edge : graph.get(currNode)) {
                int newDist = currDist + edge.weight;
                if (newDist < dist[edge.to]) {
                    dist[edge.to] = newDist;
                    pq.offer(new int[] { newDist, edge.to });
                }
            }
        }
        return dist;
    }

    // ========== TOPOLOGICAL SORT ==========
    public static void demonstrateTopologicalSort() {
        System.out.println("2. TOPOLOGICAL SORT - ORDERING FOR DAG (Directed Acyclic Graph)");
        System.out.println("Use when: Course scheduling, build dependencies, task ordering");
        System.out.println("Methods: DFS-based, Kahn's algorithm (BFS-based)\n");

        // Create DAG: 0->1, 0->2, 1->3, 2->3
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            graph.add(new ArrayList<>());
        }

        graph.get(0).add(1);
        graph.get(0).add(2);
        graph.get(1).add(3);
        graph.get(2).add(3);

        System.out.println("Graph: 0->1,2  1->3  2->3");

        // Method 1: DFS-based
        List<Integer> topoOrderDFS = topologicalSortDFS(graph);
        System.out.println("Topological order (DFS): " + topoOrderDFS);

        // Method 2: Kahn's algorithm
        List<Integer> topoOrderKahn = topologicalSortKahn(graph);
        System.out.println("Topological order (Kahn): " + topoOrderKahn);
        System.out.println();
    }

    // DFS-based topological sort
    public static List<Integer> topologicalSortDFS(List<List<Integer>> graph) {
        int n = graph.size();
        boolean[] visited = new boolean[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                topoDFS(graph, i, visited, stack);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private static void topoDFS(List<List<Integer>> graph, int node, boolean[] visited, Stack<Integer> stack) {
        visited[node] = true;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                topoDFS(graph, neighbor, visited, stack);
            }
        }

        stack.push(node); // Add to stack after visiting all descendants
    }

    // Kahn's algorithm (BFS-based)
    public static List<Integer> topologicalSortKahn(List<List<Integer>> graph) {
        int n = graph.size();
        int[] indegree = new int[n];

        // Calculate indegrees
        for (int i = 0; i < n; i++) {
            for (int neighbor : graph.get(i)) {
                indegree[neighbor]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);

            for (int neighbor : graph.get(node)) {
                indegree[neighbor]--;
                if (indegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return result;
    }

    // ========== UNION FIND (DISJOINT SET) ==========
    public static void demonstrateUnionFind() {
        System.out.println("3. UNION FIND (DISJOINT SET) - TRACK CONNECTED COMPONENTS");
        System.out.println("Use when: Dynamic connectivity, MST algorithms, detecting cycles");
        System.out.println("Time: Nearly O(1) with path compression and union by rank\n");

        UnionFind uf = new UnionFind(5);

        System.out.println("Initial: 5 separate components");
        System.out.println("Components: " + uf.getComponents());

        uf.union(0, 1);
        System.out.println("After union(0,1): " + uf.getComponents() + " components");

        uf.union(2, 3);
        System.out.println("After union(2,3): " + uf.getComponents() + " components");

        uf.union(0, 2);
        System.out.println("After union(0,2): " + uf.getComponents() + " components");

        System.out.println("Are 1 and 3 connected? " + uf.connected(1, 3));
        System.out.println("Are 1 and 4 connected? " + uf.connected(1, 4));
        System.out.println();
    }

    // Union Find implementation with path compression and union by rank
    static class UnionFind {
        private final int[] parent;
        private final int[] rank;
        private int components;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            components = n;
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); // Path compression
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX != rootY) {
                // Union by rank
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                components--;
            }
        }

        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }

        public int getComponents() {
            return components;
        }
    }

    // ========== CYCLE DETECTION ==========
    public static void demonstrateCycleDetection() {
        System.out.println("4. CYCLE DETECTION");
        System.out.println("Undirected: Use DFS with parent tracking or Union-Find");
        System.out.println("Directed: Use DFS with recursion stack (3-color DFS)\n");

        // Undirected graph with cycle: 0-1-2-0
        List<List<Integer>> undirected = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            undirected.add(new ArrayList<>());
        }
        undirected.get(0).add(1);
        undirected.get(1).add(0);
        undirected.get(1).add(2);
        undirected.get(2).add(1);
        undirected.get(2).add(0);
        undirected.get(0).add(2);

        System.out.println("Undirected graph 0-1-2-0 has cycle: " + hasCycleUndirected(undirected));

        // Directed graph with cycle: 0->1->2->0
        List<List<Integer>> directed = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            directed.add(new ArrayList<>());
        }
        directed.get(0).add(1);
        directed.get(1).add(2);
        directed.get(2).add(0);

        System.out.println("Directed graph 0->1->2->0 has cycle: " + hasCycleDirected(directed));
        System.out.println();
    }

    // Cycle detection in undirected graph
    public static boolean hasCycleUndirected(List<List<Integer>> graph) {
        boolean[] visited = new boolean[graph.size()];

        for (int i = 0; i < graph.size(); i++) {
            if (!visited[i]) {
                if (dfsUndirected(graph, i, -1, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfsUndirected(List<List<Integer>> graph, int node, int parent, boolean[] visited) {
        visited[node] = true;

        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                if (dfsUndirected(graph, neighbor, node, visited)) {
                    return true;
                }
            } else if (neighbor != parent) {
                return true; // Back edge found
            }
        }
        return false;
    }

    // Cycle detection in directed graph (3-color DFS)
    public static boolean hasCycleDirected(List<List<Integer>> graph) {
        int n = graph.size();
        int[] color = new int[n]; // 0: white, 1: gray, 2: black

        for (int i = 0; i < n; i++) {
            if (color[i] == 0) {
                if (dfsDirected(graph, i, color)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean dfsDirected(List<List<Integer>> graph, int node, int[] color) {
        color[node] = 1; // Gray (currently processing)

        for (int neighbor : graph.get(node)) {
            if (color[neighbor] == 1) { // Back edge to gray node
                return true;
            }
            if (color[neighbor] == 0 && dfsDirected(graph, neighbor, color)) {
                return true;
            }
        }

        color[node] = 2; // Black (completely processed)
        return false;
    }

    // ========== BIPARTITE GRAPH CHECKING ==========
    public static void demonstrateBipartiteCheck() {
        System.out.println("5. BIPARTITE GRAPH CHECKING");
        System.out.println("Use when: 2-coloring problems, matching problems");
        System.out.println("Method: BFS/DFS with 2-coloring\n");

        // Bipartite graph: 0-1, 0-3, 1-2, 2-3
        List<List<Integer>> bipartite = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            bipartite.add(new ArrayList<>());
        }
        bipartite.get(0).add(1);
        bipartite.get(0).add(3);
        bipartite.get(1).add(0);
        bipartite.get(1).add(2);
        bipartite.get(2).add(1);
        bipartite.get(2).add(3);
        bipartite.get(3).add(0);
        bipartite.get(3).add(2);

        System.out.println("Graph 0-1, 0-3, 1-2, 2-3 is bipartite: " + isBipartite(bipartite));

        // Non-bipartite (triangle): 0-1-2-0
        List<List<Integer>> triangle = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            triangle.add(new ArrayList<>());
        }
        triangle.get(0).add(1);
        triangle.get(0).add(2);
        triangle.get(1).add(0);
        triangle.get(1).add(2);
        triangle.get(2).add(0);
        triangle.get(2).add(1);

        System.out.println("Triangle 0-1-2-0 is bipartite: " + isBipartite(triangle));
        System.out.println();
    }

    public static boolean isBipartite(List<List<Integer>> graph) {
        int n = graph.size();
        int[] color = new int[n]; // 0: uncolored, 1: red, -1: blue

        for (int i = 0; i < n; i++) {
            if (color[i] == 0) {
                Queue<Integer> queue = new LinkedList<>();
                queue.offer(i);
                color[i] = 1;

                while (!queue.isEmpty()) {
                    int node = queue.poll();

                    for (int neighbor : graph.get(node)) {
                        if (color[neighbor] == 0) {
                            color[neighbor] = -color[node];
                            queue.offer(neighbor);
                        } else if (color[neighbor] == color[node]) {
                            return false; // Same color = not bipartite
                        }
                    }
                }
            }
        }
        return true;
    }

    // ========== GRID-BASED GRAPH PROBLEMS ==========
    public static void demonstrateGridProblems() {
        System.out.println("6. GRID-BASED GRAPH PROBLEMS");
        System.out.println("Common in: Island problems, path finding in matrix, flood fill");
        System.out.println("Key: Treat grid as implicit graph, use directions array\n");

        int[][] grid = {
                { 1, 1, 0, 0, 0 },
                { 1, 1, 0, 0, 0 },
                { 0, 0, 1, 0, 0 },
                { 0, 0, 0, 1, 1 }
        };

        System.out.println("Grid (1=land, 0=water):");
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }

        int islands = countIslands(grid);
        System.out.println("Number of islands: " + islands);
        System.out.println();
    }

    // Count islands using DFS
    private static final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static int countIslands(int[][] grid) {
        if (grid == null || grid.length == 0)
            return 0;

        int rows = grid.length, cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    dfsIsland(grid, i, j, visited);
                    count++;
                }
            }
        }
        return count;
    }

    private static void dfsIsland(int[][] grid, int row, int col, boolean[][] visited) {
        int rows = grid.length, cols = grid[0].length;

        if (row < 0 || row >= rows || col < 0 || col >= cols ||
                visited[row][col] || grid[row][col] == 0) {
            return;
        }

        visited[row][col] = true;

        for (int[] dir : DIRECTIONS) {
            dfsIsland(grid, row + dir[0], col + dir[1], visited);
        }
    }

    // ========== MULTI-SOURCE BFS ==========
    public static void demonstrateMultiSourceBFS() {
        System.out.println("7. MULTI-SOURCE BFS");
        System.out.println("Use when: Multiple starting points, shortest distance from any source");
        System.out.println("Examples: Rotten oranges, walls and gates\n");

        int[][] grid = {
                { 0, -1, 0, 0 },
                { 0, 0, 0, -1 },
                { 0, -1, 0, 0 },
                { 0, 0, 0, 0 }
        };

        System.out.println("Grid (0=empty, -1=wall):");
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }

        wallsAndGates(grid);

        System.out.println("After filling distances to nearest gate:");
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println();
    }

    // Multi-source BFS example: Walls and Gates
    public static void wallsAndGates(int[][] rooms) {
        if (rooms == null || rooms.length == 0)
            return;

        int rows = rooms.length, cols = rooms[0].length;
        Queue<int[]> queue = new LinkedList<>();

        // Add all gates (value 0) to queue
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(new int[] { i, j });
                }
            }
        }

        // BFS from all gates simultaneously
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int row = curr[0], col = curr[1];

            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols &&
                        rooms[newRow][newCol] > rooms[row][col] + 1) {

                    rooms[newRow][newCol] = rooms[row][col] + 1;
                    queue.offer(new int[] { newRow, newCol });
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== ADVANCED GRAPH TECHNIQUES FOR MEDIUM PROBLEMS ===\n");

        // ========== WEIGHTED GRAPHS & DIJKSTRA ==========
        demonstrateDijkstra();

        // ========== TOPOLOGICAL SORT ==========
        demonstrateTopologicalSort();

        // ========== UNION FIND (DISJOINT SET) ==========
        demonstrateUnionFind();

        // ========== CYCLE DETECTION ==========
        demonstrateCycleDetection();

        // ========== BIPARTITE GRAPH CHECKING ==========
        demonstrateBipartiteCheck();

        // ========== GRID-BASED GRAPH PROBLEMS ==========
        demonstrateGridProblems();

        // ========== MULTI-SOURCE BFS ==========
        demonstrateMultiSourceBFS();

        System.out.println("\n=== LEETCODE PATTERNS SUMMARY ===");
        patternsCovered();
    }
}