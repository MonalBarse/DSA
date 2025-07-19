package com.monal.Graphs.MST;

import java.util.*;

/*
 * MINIMUM SPANNING TREE & DISJOINT SET - COMPLETE TUTORIAL
 * ========================================================
 *
 * 🎯 WHAT IS MST?
 * MST (Minimum Spanning Tree) is a special kind of tree that connects
 * all vertices in a graph with the minimum total edge weight.
 * It ensures:
 *  1. All vertices are connected (spanning)
 *  2. No cycles (tree property)
 *  3. Exactly V-1 edges (tree property)
 *  4. Minimum total weight (optimality)
 *
 * Think of it like finding the cheapest way to connect all cities with roads.
 * You want to connect ALL cities but use minimum total cost of roads.
 *
 * 💡 NOTE: When you see "connect all nodes with minimum cost",
 *    think MST immediately!
 *
 * To solve MST problems, we typically use two main algorithms:
 *
 * A. Prim's Algorithm - "Grow the Tree One Vertex at a Time"
 *    Strategy - Start from any vertex and keep adding the cheapest edge that connects to a new vertex.
 * B. Kruskal's Algorithm - "Sorts all edges, adds cheapest edges that don't create cycles (uses Disjoint Set)
 *    Strategy - Sort all edges by weight and add them one by one, ensuring no cycles using Disjoint Set.
 */

public class Basics {

    /*
     * PRIM'S ALGORITHM
     * ================
     *
     * 🎯 STRATEGY: "Greedy by Vertex Growth"
     * Think like building a spider web:
     * 1. Start from any vertex (like center of web)
     * 2. Always pick the cheapest edge that connects to a new vertex
     * 3. Grow the tree one vertex at a time
     *
     * 💡 INTERVIEW INSIGHT: "I'll use Prim's because the graph is dense"
     * or "I need to start from a specific vertex"
     *
     * Time: O(E log V) with priority queue
     * Space: O(V) - for visited array and priority queue
     *
     * 🚀 WHEN TO USE: Dense graphs or when you need to start from specific vertex
     */

    static class PrimMST {
        private int vertices;
        private List<List<Edge>> adj;

        // EDGE has - a source vertex, destination vertex, and weight
        // EDGE - (src, dest, weight)
        PrimMST(int vertices) {
            this.vertices = vertices;
            this.adj = new ArrayList<>();
            for (int i = 0; i < vertices; i++) {
                adj.add(new ArrayList<>());
            }
        }

        void addEdge(int e1, int e2, int weight) {
            adj.get(e1).add(new Edge(e1, e2, weight));
            adj.get(e2).add(new Edge(e2, e1, weight)); // Undirected graph
        }

        List<Edge> findMST() {
            List<Edge> mst = new ArrayList<>();
            // Need a visited array
            boolean[] visited = new boolean[vertices];

            // Use PriorityQueue to always pick cheapest edge!
            // PriorityQueue<Edge> pq = new PriorityQueue<>();
            // This uses the Default comparator inside Edge class (compareTo method)

            // PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.weight - b.weight);
            // Caveat: a.weight - b.weight could overflow
            // If weights can be very large/negative, this subtraction could overflow.
            // Use Integer.compare instead to avoid overflow issues.
            PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.weight, b.weight));

            // Start from vertex 0 (arbitrary choice)
            int startVertex = 0; // may be parameterized int startVertex = start;
            visited[startVertex] = true;

            // Add all edges from start vertex to priority queue
            for (Edge edge : adj.get(startVertex)) {
                pq.offer(edge);
            }

            System.out.println("🌱 Starting Prim's MST from vertex " + startVertex);
            System.out.println("🏗️  Building MST:");

            while (!pq.isEmpty() && mst.size() < vertices - 1) {
                Edge minEdge = pq.poll();

                // If destination vertex is already visited, skip this edge
                if (!visited[minEdge.dest]) {
                    // Add this edge to MST
                    mst.add(minEdge);
                    visited[minEdge.dest] = true;
                    System.out.println("✅ Added edge: " + minEdge);

                    // Add all edges from newly added vertex
                    for (Edge edge : adj.get(minEdge.dest)) {
                        if (!visited[edge.dest]) {
                            pq.offer(edge);
                        }
                    }
                }
            }

            int totalWeight = mst.stream().mapToInt(e -> e.weight).sum();
            System.out.println("🌉 Total weight of MST: " + totalWeight);

            if (mst.size() != vertices - 1) {
                System.out.println("⚠️ Graph is not connected. MST not possible.");
            }
            return mst;
        }
    }

    /*
     * DISJOINT SET (UNION-FIND) DATA STRUCTURE
     * =========================================
     *
     * 🤔 WHY DO WE NEED THIS?
     * Imagine you're at a party and want to know if two people belong to the same
     * friend group.
     * Disjoint Set helps us:
     * 1. Check if two elements are in the same set (Find operation)
     * 2. Merge two sets (Union operation)
     * SO :
     * // Union is merging two sets (sets - groups of connected nodes)
     * // Find is checking which set a node belongs to (find root of the set)
     *
     * 💡 INTERVIEW INSIGHT: This is used in Kruskal's to detect cycles!
     * If two nodes are already connected (same set), adding edge creates cycle.
     *
     * Two main optimizations:
     * - Union by Rank: Attach smaller tree under root of deeper tree
     * - Union by Size: Attach smaller tree under root of larger tree
     * - Path Compression: Make all nodes point directly to root during find
     */

    /*
     * DISJOINT SET WITH UNION BY RANK
     * Union operation (merging two sets) can be done by rank or size.
     * Rank is the height of the tree representing the set.
     */
    static class DisjointSetByRank {
        int[] parent, rank;

        DisjointSetByRank(int n) {
            parent = new int[n];
            rank = new int[n];

            // Initially every element is considered separate set
            for (int i = 0; i < n; i++) {
                parent[i] = i; // Each element is its own parent
                rank[i] = 0; // Height of tree rooted at i
            }
        }

        // Find operation - find the root of the set containing x
        int find(int node) {
            if (parent[node] == node) {
                return node; // Node is its own parent
            }
            // Path compression - make all nodes point directly to root
            return parent[node] = find(parent[node]);
        }

        // Union by Rank
        // 💡 EUREKA MOMENT: Always attach shorter tree under taller tree!
        // This keeps the overall tree height minimal
        boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY)
                return false; // Already in same set

            // Union by rank: attach smaller rank tree under root of higher rank tree
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                // else - both have same rank, attach one under the other
                parent[rootY] = rootX;
                rank[rootX]++; // Only increment when ranks are equal
            }
            return true;
        }
    }

    // DISJOINT SET WITH UNION BY SIZE
    @SuppressWarnings("unused")
    static class DisjointSetBySize {
        int[] parent, size;

        DisjointSetBySize(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1; // Each set initially has size 1
            }
        }

        int find(int node) {
            if (parent[node] == node) {
                return node; // Node is its own parent
            }
            // Path compression - make all nodes point directly to root
            return parent[node] = find(parent[node]);
        }

        // Union by Size
        // 💡 INTUITION: Attach smaller group to larger group
        // This minimizes the depth increase
        boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY)
                return false;

            // Union by size: attach smaller size tree under root of larger size tree
            if (size[rootX] < size[rootY]) {
                parent[rootX] = rootY;
                size[rootY] += size[rootX];
            } else {
                parent[rootY] = rootX;
                size[rootX] += size[rootY];
            }
            return true;
        }
    }

    /*
     * KRUSKAL'S ALGORITHM
     * ===================
     * 🎯 STRATEGY: "Greedy by Edge Weight"
     * Think like a cheapskate contractor:
     * 1. Sort all roads by cost (cheapest first)
     * 2. Pick the cheapest road that doesn't create a cycle
     * 3. Repeat until all cities are connected
     *
     * 💡 INTERVIEW TIP: "I'll use Kruskal's because I can sort edges easily"
     *
     * Time: O(E log E) - dominated by sorting
     * Space: O(V) - for disjoint set
     *
     * 🚀 WHEN TO USE: When edges are given as a list and you can sort them easily
     */

    static class KruskalMST {
        private List<Edge> edges;
        private int vertices;

        KruskalMST(int vertices) {
            this.vertices = vertices;
            this.edges = new ArrayList<>();
        }

        void addEdge(int src, int dest, int weight) {
            edges.add(new Edge(src, dest, weight));
        }

        List<Edge> findMST() {
            List<Edge> mst = new ArrayList<>();

            // Step 1: Sort edges by weight (greedy choice)
            // The sort function uses Edge's compareTo method to sort edges by weight;
            Collections.sort(edges);
            // If we wanted to use a lambda, it would look like this:
            // Collections.sort(edges, (a, b) -> Integer.compare(a.weight, b.weight));
            System.out.println("🔍 Sorted edges: " + edges);

            // Step 2: Use Disjoint Set to detect cycles
            DisjointSetByRank ds = new DisjointSetByRank(vertices);
            System.out.println("\n🏗️  Building MST using Kruskal's:");

            // Step 3: Process edges in order of weight
            for (Edge edge : edges) {
                // if union is successful, it means no cycle is created
                // i.e src and dest were not already connected
                if (ds.union(edge.src, edge.dest)) {
                    mst.add(edge);
                    System.out.println("✅ Added edge: " + edge);
                    // It also ensures that we check minimum edge weight as we
                    // already sorted edges by weight

                    // If we have enough edges, we can stop this ensures we are creating
                    // a `minimum` spanning tree
                    if (mst.size() == vertices - 1)
                        break;
                } else {
                    System.out.println("❌ Rejected edge: " + edge + " (creates cycle)");
                }
            }

            return mst;
        }
    }

    /*
     * 🎯 INTERVIEW STRATEGY GUIDE
     * ===========================
     *
     * When you see MST problems, ask yourself:
     *
     * 1. "How is the graph given?"
     * - Edge list → Kruskal's is natural
     * - Adjacency matrix/list → Prim's is natural
     *
     * 2. "What's the graph density?"
     * - Sparse (E ≈ V) → Kruskal's (O(E log E))
     * - Dense (E ≈ V²) → Prim's (O(E log V))
     *
     * 3. "Do I need to start from specific vertex?"
     * - Yes → Prim's
     * - No → Either works
     *
     * 4. "Do I need to detect cycles separately?"
     * - If yes, Disjoint Set is your friend!
     *
     * 💡 COMMON INTERVIEW VARIANTS:
     * - "Connect all computers with minimum cable cost" → MST
     * - "Find if adding edge creates cycle" → Disjoint Set
     * - "Minimum cost to make graph connected" → MST
     * - "Critical edges in MST" → Advanced MST
     */

    // Utility method to calculate total weight of MST
    static int calculateTotalWeight(List<Edge> mst) {
        return mst.stream().mapToInt(e -> e.weight).sum();
    }

    // Demo method to show both algorithms in action
    public static void main(String[] args) {
        System.out.println("🎓 MST AND DISJOINT SET TUTORIAL");
        System.out.println("=".repeat(50));

        // Create sample graph
        // 0
        // / \\
        // 4 2
        // / \\
        // 1---------3
        // 5

        System.out.println("\n📊 Sample Graph:");
        System.out.println("Vertices: 0, 1, 2, 3");
        System.out.println("Edges: (0-1:4), (0-2:2), (1-3:5), (2-3:3)");

        // Test Kruskal's Algorithm
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🔍 KRUSKAL'S ALGORITHM DEMO");
        System.out.println("=".repeat(50));

        KruskalMST kruskal = new KruskalMST(4);
        kruskal.addEdge(0, 1, 4);
        kruskal.addEdge(0, 2, 2);
        kruskal.addEdge(1, 3, 5);
        kruskal.addEdge(2, 3, 3);

        List<Edge> kruskalMST = kruskal.findMST();
        System.out.println("\n🏆 Kruskal's MST: " + kruskalMST);
        System.out.println("💰 Total weight: " + calculateTotalWeight(kruskalMST));

        // Test Prim's Algorithm
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🌿 PRIM'S ALGORITHM DEMO");
        System.out.println("=".repeat(50));

        PrimMST prim = new PrimMST(4);
        prim.addEdge(0, 1, 4);
        prim.addEdge(0, 2, 2);
        prim.addEdge(1, 3, 5);
        prim.addEdge(2, 3, 3);

        List<Edge> primMST = prim.findMST();
        System.out.println("\n🏆 Prim's MST: " + primMST);
        System.out.println("💰 Total weight: " + calculateTotalWeight(primMST));

        // Test Disjoint Set
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🔗 DISJOINT SET DEMO");
        System.out.println("=".repeat(50));

        DisjointSetByRank ds = new DisjointSetByRank(4);
        System.out.println("Initial state: Each element in its own set");

        System.out.println("Union(0, 1): " + ds.union(0, 1));
        System.out.println("Union(2, 3): " + ds.union(2, 3));
        System.out.println("Are 0 and 1 connected? " + (ds.find(0) == ds.find(1)));
        System.out.println("Are 0 and 2 connected? " + (ds.find(0) == ds.find(2)));
        System.out.println("Union(1, 2): " + ds.union(1, 2));
        System.out.println("Now are 0 and 3 connected? " + (ds.find(0) == ds.find(3)));

        System.out.println("\n🎯 KEY TAKEAWAYS:");
        System.out.println("1. Both algorithms produce the same MST (different order possible)");
        System.out.println("2. Kruskal's processes edges globally (sort all edges)");
        System.out.println("3. Prim's grows locally (always expand current tree)");
        System.out.println("4. Disjoint Set is crucial for cycle detection in Kruskal's");
        System.out.println("5. Both have similar time complexity for most practical purposes");

        System.out.println("\n🚀 INTERVIEW SUCCESS FORMULA:");
        System.out.println("MST Problem → Ask about graph representation → Choose algorithm → Code with confidence!");
    }

    // Edge class - represents a connection between two nodes with a weight
    static class Edge implements Comparable<Edge> {

        int src, dest, weight;

        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        // Comparable<Edge> interface is used for sorting objects of Edge class
        // it makes the user of Edge class able to sort things
        // here we are overriding compareTo method which is used by functions to
        // sort the objects of this class... here we are sorting edges by weight

        // For sorting edges by weight (needed in Kruskal's)
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(this.weight, other.weight);
        }

        @Override
        public String toString() {
            return String.format("(%d->%d: %d)", src, dest, weight);
        }
    }

    class DJS {
        class DisjointSet {
            int[] parent, rank;

            DisjointSet(int n) {
                parent = new int[n];
                rank = new int[n];
            }

            int find(int node) {
                if (parent[node] == node)
                    return node;
                return parent[node] = find(parent[node]);
            }

            boolean union(int x, int y) {
                int rootX = find(x), rootY = find(y);
                if (rootX == rootY)
                    return false; // already in same set
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
                return true;
            }

            int countComponents() {
                int count = 0;
                for (int i = 0; i < parent.length; i++)
                    if (parent[i] == i)
                        count++;
                return count;
            }
        }

        // Example 1: Number of Islands (2D grid)
        class Solution1 {
            public int numIslands(char[][] grid) {
                int m = grid.length, n = grid[0].length;
                DisjointSet djs = new DisjointSet(m * n); // Each cell can be separate

                // Convert 2D coordinates to 1D index
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        if (grid[i][j] == '1') {
                            int current = i * n + j; // 2D to 1D conversion
                            // Check adjacent cells and union if both are '1'
                        }
                    }
                }
                return djs.countComponents();
            }
        }

        // Example 2: Friend Circles (people numbered 0 to n-1)
        class Solution2 {
            public int findCircleNum(int[][] isConnected) {
                int n = isConnected.length;
                DisjointSet djs = new DisjointSet(n); // n people, indices 0 to n-1

                for (int i = 0; i < n; i++)
                    for (int j = i + 1; j < n; j++)
                        if (isConnected[i][j] == 1)
                            djs.union(i, j); // Direct mapping: person i → index i
                return djs.countComponents();
            }
        }

        // Example 3: Accounts Merge (emails as strings)
        class Solution3 {
            public List<List<String>> buildResult(DisjointSet djs, Map<String, Integer> emailToIndex) {
                List<List<String>> result = new ArrayList<>();
                Map<Integer, List<String>> indexToEmails = new HashMap<>();
                for (Map.Entry<String, Integer> entry : emailToIndex.entrySet()) {
                    int index = djs.find(entry.getValue());
                    indexToEmails.computeIfAbsent(index, k -> new ArrayList<>()).add(entry.getKey());
                }
                for (List<String> emails : indexToEmails.values()) {
                    Collections.sort(emails);
                    result.add(emails);
                }
                return result;
            }

            public List<List<String>> accountsMerge(List<List<String>> accounts) {
                Map<String, Integer> emailToIndex = new HashMap<>();
                int emailCount = 0;

                // First pass: assign index to each unique email
                for (List<String> account : accounts) {
                    for (int i = 1; i < account.size(); i++) {
                        String email = account.get(i);
                        if (!emailToIndex.containsKey(email)) {
                            emailToIndex.put(email, emailCount++);
                        }
                    }
                }

                DisjointSet djs = new DisjointSet(emailCount); // Size = number of unique emails

                // Second pass: union emails belonging to same account
                for (List<String> account : accounts) {
                    int firstEmailIndex = emailToIndex.get(account.get(1));
                    for (int i = 2; i < account.size(); i++) {
                        int emailIndex = emailToIndex.get(account.get(i));
                        djs.union(firstEmailIndex, emailIndex);
                    }
                }

                return buildResult(djs, emailToIndex);
            }

        }

        // Example 4: Variable equations (your current problem)
        class Solution4 {
            public boolean equationsPossible(String[] equations) {
                // Variables are single lowercase letters: a, b, c, ..., z
                DisjointSet djs = new DisjointSet(26); // Exactly 26 possible variables

                // Process equations...
                for (String eqn : equations) {
                    if (eqn.charAt(1) == '=') {
                        char u = eqn.charAt(0), v = eqn.charAt(3);
                        djs.union(u - 'a', v - 'a'); // Map a→0, b→1, ..., z→25
                    }
                }

                return true;
            }
        }

        // Example 5: When you don't know the range in advance
        class Solution5 {
            public boolean solve(int[] elements) {
                // If elements can be any integer, use HashMap approach
                Map<Integer, Integer> valueToIndex = new HashMap<>();
                int indexCounter = 0;

                // Assign indices to unique values
                for (int element : elements) {
                    if (!valueToIndex.containsKey(element)) {
                        valueToIndex.put(element, indexCounter++);
                    }
                }

                DisjointSet djs = new DisjointSet(indexCounter); // Size = number of unique values

                // Use valueToIndex.get(element) to get the index for Union-Find operations
                return true;
            }
        }
    }
}