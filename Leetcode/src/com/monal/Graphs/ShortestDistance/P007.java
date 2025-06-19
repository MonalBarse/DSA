package com.monal.Graphs.ShortestDistance;

import java.util.*;

/*
Bellman - Ford Algorithm
This Algorithm is used to find the shortest path from
a source node to all other nodes in a weighted graph. (Even negative weights)
It is different from Dijkstra's algorithm in that it can handle negative weights.

Working - It works similarly to Dijkstra's algorithm, but it relaxes all edges repeatedly
until no more updates can be made. It iterates through all edges and updates the distance
to each node if a shorter path is found. This process is repeated for (n-1) times,
where n is the number of nodes in the graph.

SO The time complexity is O(V * E) [V - nodes, E - edges]
for Dijkstra's algorithm, the time complexity is O(E log V) using a priority queue.
So considering positive weights, Dijkstra's algorithm is more efficient.

import java.util.*;
import java.io.*;

/**
 * Dijkstra's Algo
 * - Finds shortest paths from a single source to all vertices
 * - Cannot handle negative weights
 * - Time Complexity: O(E log V) where E = edges, V = vertices
 * - Space Complexity: O(V)
 *
 * BELLMAN-FORD Algorithm:
 * - Finds shortest paths from a single source to all vertices
 * - Can detect negative weight cycles
 * - Time Complexity: O(V*E) where V = vertices, E = edges
 * - Space Complexity: O(V)
 *
 * FLOYD-WARSHALL Algorithm:
 * - Finds shortest paths between ALL pairs of vertices
 * - Can detect negative weight cycles
 * - Time Complexity: O(V³)
 * - Space Complexity: O(V²)
 * - Different form BFord : - finds shortest path from every vertex to every other vertex
 *
 */
public class P007 {

    /**
     * BELLMAN-FORD ALGORITHM IMPLEMENTATION
     *
     * Algorithm Steps:
     * 1. Initialize distances to all vertices as infinite except source (0)
     * 2. Relax all edges V-1 times (where V is number of vertices)
     * 3. Check for negative weight cycles by doing one more iteration
     *
     * Why V-1 iterations?
     * - In worst case, shortest path can have at most V-1 edges
     * - Each iteration guarantees finding shortest path with one more edge
     */
    public static int[] bellmanFord(Graph graph, int source) {
        int N = graph.vertices; // Number of nodes in the graph

        int[] distance = new int[N]; // Distance array to store shortest path from source
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        System.out.println("=== BELLMAN-FORD ALGORITHM EXECUTION ===");
        System.out.println("Initial distances: " + Arrays.toString(distance));

        // Relaxation step: repeat V-1 times
        for (int i = 1; i <= N - 1; i++) {
            System.out.println("\n--- Iteration " + i + " ---");
            boolean updated = false;

            for (Edge edge : graph.edgeList) {
                int u = edge.source;
                int v = edge.destination;
                int edgeDist = edge.weight;

                // Relaxation step: if we found a shorter path, update it
                if (distance[u] != Integer.MAX_VALUE &&
                        distance[u] + edgeDist < distance[v]) {

                    System.out.println("Relaxing edge " + edge +
                            ": distance[" + v + "] = " + distance[v] +
                            " -> " + (distance[u] + edgeDist));

                    distance[v] = distance[u] + edgeDist;
                    updated = true;
                }
            }

            System.out.println("Distances after iteration " + i + ": " +
                    Arrays.toString(distance));

            // Early termination if no updates in this iteration
            if (!updated) {
                System.out.println("No updates in this iteration - early termination");
                break;
            }
        }

        // Step 3: Check for negative weight cycles
        System.out.println("\n--- Checking for negative cycles ---");
        for (Edge edge : graph.edgeList) {
            int u = edge.source;
            int v = edge.destination;
            int weight = edge.weight;

            // If we can still relax an edge beyond V-1 iterations,
            // it means there's a negative weight cycle
            if (distance[u] != Integer.MAX_VALUE &&
                    distance[u] + weight < distance[v]) {
                System.out.println("NEGATIVE CYCLE DETECTED!");
                return null; // Negative cycle exists
            }
        }

        System.out.println("No negative cycles found.");
        return distance;
    }

    /**
     * FLOYD-WARSHALL ALGORITHM IMPLEMENTATION
     *
     * Algorithm Steps:
     * 1. Initialize distance matrix with direct edge weights
     * 2. For each intermediate vertex k, update shortest paths
     * 3. Check if path through k is shorter than direct path
     *
     * Key Insight:
     * - For each pair (i,j), consider all possible intermediate vertices
     * - If going through vertex k gives shorter path, update it
     * - dist[i][j] = min(dist[i][j], dist[i][k] + dist[k][j])
     */
    public static int[][] floydWarshall(int[][] graph) {
        int V = graph.length;
        int[][] distance = new int[V][V];

        // Step 1: Initialize distance matrix
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                distance[i][j] = graph[i][j];
            }
        }

        System.out.println("\n=== FLOYD-WARSHALL ALGORITHM EXECUTION ===");
        System.out.println("Initial distance matrix:");
        printMatrix(distance);

        // Step 2: Update distances using all vertices as intermediate points
        for (int k = 0; k < V; k++) {
            System.out.println("\n--- Using vertex " + k + " as intermediate ---");

            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    // If vertex k provides a shorter path from i to j, update it
                    if (distance[i][k] != Integer.MAX_VALUE &&
                            distance[k][j] != Integer.MAX_VALUE &&
                            distance[i][k] + distance[k][j] < distance[i][j]) {

                        System.out.println("Updating path " + i + " -> " + j +
                                " through " + k + ": " + distance[i][j] +
                                " -> " + (distance[i][k] + distance[k][j]));

                        distance[i][j] = distance[i][k] + distance[k][j];
                    }
                }
            }

            System.out.println("Distance matrix after considering vertex " + k + ":");
            printMatrix(distance);
        }

        // Check for negative cycles
        System.out.println("\n--- Checking for negative cycles ---");
        for (int i = 0; i < V; i++) {
            if (distance[i][i] < 0) {
                System.out.println("NEGATIVE CYCLE DETECTED involving vertex " + i);
                return null;
            }
        }
        System.out.println("No negative cycles found.");

        return distance;
    }

    // Utility method to print distance matrix
    public static void printMatrix(int[][] matrix) {
        int V = matrix.length;
        System.out.print("     ");
        for (int i = 0; i < V; i++) {
            System.out.printf("%8d", i);
        }
        System.out.println();

        for (int i = 0; i < V; i++) {
            System.out.printf("%3d: ", i);
            for (int j = 0; j < V; j++) {
                if (matrix[i][j] == Integer.MAX_VALUE) {
                    System.out.printf("%8s", "INF");
                } else {
                    System.out.printf("%8d", matrix[i][j]);
                }
            }
            System.out.println();
        }
    }

    /**
     * CHALLENGING PROBLEM 1: BELLMAN-FORD
     *
     * "Currency Arbitrage Detection"
     *
     * Problem: Given exchange rates between currencies, detect if there's
     * an arbitrage opportunity (negative cycle in logarithmic space).
     *
     * Mathematical Insight:
     * - Convert multiplication to addition using logarithms
     * - log(a * b * c) = log(a) + log(b) + log(c)
     * - Use negative logarithms to find maximum product paths
     * - If negative cycle exists, arbitrage is possible
     */
    public static void solveCurrencyArbitrage() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("CHALLENGING PROBLEM 1: CURRENCY ARBITRAGE DETECTION");
        System.out.println("=".repeat(60));

        // Currency exchange rates matrix
        // Currencies: 0=USD, 1=EUR, 2=GBP, 3=JPY, 4=CAD
        double[][] rates = {
                { 1.0, 0.85, 0.75, 110.0, 1.25 }, // USD to others
                { 1.18, 1.0, 0.88, 129.5, 1.47 }, // EUR to others
                { 1.33, 1.14, 1.0, 147.2, 1.67 }, // GBP to others
                { 0.009, 0.0077, 0.0068, 1.0, 0.011 }, // JPY to others
                { 0.80, 0.68, 0.60, 88.0, 1.0 } // CAD to others
        };

        String[] currencies = { "USD", "EUR", "GBP", "JPY", "CAD" };
        int V = rates.length;

        System.out.println("Exchange Rate Matrix:");
        System.out.print("     ");
        for (String curr : currencies) {
            System.out.printf("%10s", curr);
        }
        System.out.println();

        for (int i = 0; i < V; i++) {
            System.out.printf("%s: ", currencies[i]);
            for (int j = 0; j < V; j++) {
                System.out.printf("%10.4f", rates[i][j]);
            }
            System.out.println();
        }

        // Convert to graph with negative logarithms
        Graph arbitrageGraph = new Graph(V, V * V);

        System.out.println("\nConverting to logarithmic weights (negative log for max product):");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i != j && rates[i][j] > 0) {
                    // Use negative logarithm to convert max product to min sum
                    int weight = (int) Math.round(-Math.log(rates[i][j]) * 1000000);
                    arbitrageGraph.addEdge(i, j, weight);
                    System.out.println(currencies[i] + " -> " + currencies[j] +
                            ": rate=" + rates[i][j] + ", weight=" + weight);
                }
            }
        }

        // Run Bellman-Ford from each currency as source
        System.out.println("\nChecking for arbitrage opportunities...");
        for (int source = 0; source < V; source++) {
            System.out.println("\n--- Checking from " + currencies[source] + " ---");
            int[] distances = bellmanFord(arbitrageGraph, source);

            if (distances == null) {
                System.out.println("ARBITRAGE OPPORTUNITY FOUND starting from " +
                        currencies[source] + "!");
                return;
            }
        }

        System.out.println("\nNo arbitrage opportunities found with these exchange rates.");
    }

    /**
     * CHALLENGING PROBLEM 2: FLOYD-WARSHALL
     * "Network Latency Optimization with Server Failures"
     *
     * Problem: In a distributed network, find shortest paths between all
     * server pairs considering potential server failures and routing costs.
     * Handle dynamic server failures and find alternative paths.
     */
    public static void solveNetworkLatencyOptimization() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("CHALLENGING PROBLEM 2: NETWORK LATENCY OPTIMIZATION");
        System.out.println("=".repeat(60));

        // Network topology: 8 servers with latency costs
        int V = 8;
        int[][] network = new int[V][V];

        // Initialize with infinite distances
        for (int i = 0; i < V; i++) {
            Arrays.fill(network[i], Integer.MAX_VALUE);
            network[i][i] = 0; // Distance to self is 0
        }

        // Define network connections with latencies (milliseconds)
        int[][] connections = {
                { 0, 1, 4 }, { 0, 2, 3 }, { 0, 3, 7 },
                { 1, 2, 2 }, { 1, 4, 5 }, { 1, 5, 6 },
                { 2, 3, 2 }, { 2, 4, 4 }, { 2, 6, 8 },
                { 3, 6, 3 }, { 3, 7, 5 },
                { 4, 5, 1 }, { 4, 6, 7 },
                { 5, 6, 2 }, { 5, 7, 9 },
                { 6, 7, 1 }
        };

        System.out.println("Network Topology (Server connections with latencies):");

        // Build bidirectional network
        for (int[] conn : connections) {
            int from = conn[0], to = conn[1], latency = conn[2];
            network[from][to] = latency;
            network[to][from] = latency; // Bidirectional
            System.out.println("Server " + from + " <-> Server " + to +
                    ": " + latency + "ms");
        }

        System.out.println("\nInitial Network Latency Matrix:");
        printMatrix(network);

        // Apply Floyd-Warshall to find all shortest paths
        int[][] allPairsDistance = floydWarshall(network);

        if (allPairsDistance != null) {
            System.out.println("\nFINAL: All-pairs shortest distances:");
            printMatrix(allPairsDistance);

            // Find critical analysis
            System.out.println("\n=== NETWORK ANALYSIS ===");

            // 1. Find maximum latency between any two servers
            int maxLatency = 0;
            int[] worstPair = new int[2];
            for (int i = 0; i < V; i++) {
                for (int j = i + 1; j < V; j++) {
                    if (allPairsDistance[i][j] > maxLatency &&
                            allPairsDistance[i][j] != Integer.MAX_VALUE) {
                        maxLatency = allPairsDistance[i][j];
                        worstPair[0] = i;
                        worstPair[1] = j;
                    }
                }
            }

            System.out.println("Worst case latency: " + maxLatency +
                    "ms between Server " + worstPair[0] + " and Server " + worstPair[1]);

            // 2. Find average latency
            long totalLatency = 0;
            int pairs = 0;
            for (int i = 0; i < V; i++) {
                for (int j = i + 1; j < V; j++) {
                    if (allPairsDistance[i][j] != Integer.MAX_VALUE) {
                        totalLatency += allPairsDistance[i][j];
                        pairs++;
                    }
                }
            }

            System.out.println("Average network latency: " +
                    (double) totalLatency / pairs + "ms");

            // 3. Simulate server failure and recalculate
            simulateServerFailure(network, 2); // Simulate Server 2 failure
        }
    }

    /**
     * Simulate server failure and recalculate network paths
     */
    public static void simulateServerFailure(int[][] originalNetwork, int failedServer) {
        System.out.println("\n=== SIMULATING SERVER " + failedServer + " FAILURE ===");

        int V = originalNetwork.length;
        int[][] networkWithFailure = new int[V][V];

        // Copy original network
        for (int i = 0; i < V; i++) {
            System.arraycopy(originalNetwork[i], 0, networkWithFailure[i], 0, V);
        }

        // Remove failed server connections
        for (int i = 0; i < V; i++) {
            networkWithFailure[failedServer][i] = Integer.MAX_VALUE;
            networkWithFailure[i][failedServer] = Integer.MAX_VALUE;
        }
        networkWithFailure[failedServer][failedServer] = 0;

        System.out.println("Network after Server " + failedServer + " failure:");
        printMatrix(networkWithFailure);

        // Recalculate shortest paths
        int[][] newDistances = floydWarshall(networkWithFailure);

        if (newDistances != null) {
            System.out.println("\nShortest paths after server failure:");
            printMatrix(newDistances);

            // Check connectivity
            System.out.println("\n=== CONNECTIVITY ANALYSIS ===");
            boolean fullyConnected = true;
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (i != j && i != failedServer && j != failedServer &&
                            newDistances[i][j] == Integer.MAX_VALUE) {
                        System.out.println("Server " + i + " and Server " + j +
                                " are now disconnected!");
                        fullyConnected = false;
                    }
                }
            }

            if (fullyConnected) {
                System.out.println("Network remains fully connected despite server failure.");
            } else {
                System.out.println("Network is partitioned due to server failure!");
            }
        }
    }

    /**
     * Main method to demonstrate both algorithms with challenging problems
     */
    public static void main(String[] args) {
        System.out.println("SHORTEST PATH ALGORITHMS: BELLMAN-FORD & FLOYD-WARSHALL");
        System.out.println("=".repeat(70));

        // Solve challenging problems
        solveCurrencyArbitrage();
        solveNetworkLatencyOptimization();

        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALGORITHM COMPARISON SUMMARY:");
        System.out.println("=".repeat(70));
        System.out.println("BELLMAN-FORD:");
        System.out.println("  ✓ Single-source shortest paths");
        System.out.println("  ✓ Handles negative weights");
        System.out.println("  ✓ Detects negative cycles");
        System.out.println("  ✓ Time: O(V*E), Space: O(V)");
        System.out.println("  ✓ Best for: Sparse graphs, negative edge detection");

        System.out.println("\nFLOYD-WARSHALL:");
        System.out.println("  ✓ All-pairs shortest paths");
        System.out.println("  ✓ Handles negative weights");
        System.out.println("  ✓ Detects negative cycles");
        System.out.println("  ✓ Time: O(V³), Space: O(V²)");
        System.out.println("  ✓ Best for: Dense graphs, all-pairs queries");

        System.out.println("\nWhen to use which:");
        System.out.println("  • Use Bellman-Ford when you need shortest paths from ONE source");
        System.out.println("  • Use Floyd-Warshall when you need shortest paths between ALL pairs");
        System.out.println("  • Both can detect negative cycles and handle negative weights");
        System.out.println("  • Dijkstra is faster but can't handle negative weights");
    }

    // Edge class for representing graph edges
    static class Edge {
        int source, destination, weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "(" + source + " -> " + destination + ", weight: " + weight + ")";
        }
    }

    // Graph class for Bellman-Ford algorithm
    static class Graph {
        int vertices, edges;
        List<Edge> edgeList;

        Graph(int vertices, int edges) {
            this.vertices = vertices;
            this.edges = edges;
            this.edgeList = new ArrayList<>();
        }

        void addEdge(int source, int destination, int weight) {
            edgeList.add(new Edge(source, destination, weight));
        }
    }

}