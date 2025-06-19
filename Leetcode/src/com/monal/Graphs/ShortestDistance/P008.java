package com.monal.Graphs.ShortestDistance;

import java.util.*;

/*
LeetCode 1334: Find the City With the Smallest Number of Neighbors at a Threshold Distance

There are n cities numbered from 0 to n-1. Given the array edges where edges[i] = [fromi, toi, weighti] represents a bidirectional and weighted edge between cities fromi and toi, and given the integer distanceThreshold.
Return the city with the smallest number of cities that are reachable through some path and whose distance is at most distanceThreshold, If there are multiple such cities, return the city with the greatest number.
Notice that the distance of a path connecting cities i and j is equal to the sum of the edges' weights along that path.

Example 1:
  Input: n = 4, edges = [[0,1,3],[1,2,1],[1,3,4],[2,3,1]], distanceThreshold = 4
  Output: 3
  Explanation: The figure above describes the graph.
  The neighboring cities at a distanceThreshold = 4 for each city are:
  City 0 -> [City 1, City 2]
  City 1 -> [City 0, City 2, City 3]
  City 2 -> [City 0, City 1, City 3]
  City 3 -> [City 1, City 2]
  Cities 0 and 3 have 2 neighboring cities at a distanceThreshold = 4, but we have to return city 3 since it has the greatest number.

Example 2:
  Input: n = 5, edges = [[0,1,2],[0,4,8],[1,2,3],[1,4,2],[2,3,1],[3,4,1]], distanceThreshold = 2
  Output: 0
  Explanation: The figure above describes the graph.
  The neighboring cities at a distanceThreshold = 2 for each city are:
  City 0 -> [City 1]
  City 1 -> [City 0, City 4]
  City 2 -> [City 3, City 4]
  City 3 -> [City 2, City 4]
  City 4 -> [City 1, City 2, City 3]
  The city 0 has 1 neighboring city at a distanceThreshold = 2.
*/

public class P008 {
    /**
     * OPTIMIZED SOLUTION using Floyd-Warshall Algorithm
     * Time Complexity: O(V³) where V is number of cities
     * Space Complexity: O(V²) for distance matrix
     *
     * Why Floyd-Warshall over Dijkstra from each vertex?
     * 1. Simpler implementation (no priority queue, visited array)
     * 2. Better for dense graphs (when E approaches V²)
     * 3. More cache-friendly (better memory access pattern)
     * 4. Same time complexity but cleaner code
     */
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // Step 1: Initialize distance matrix
        final int INF = 10001; // Max possible distance (constraint: weight ≤ 10^4, n ≤ 100)
        int[][] dist = new int[n][n];

        // Initialize all distances to infinity
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0; // Distance to self is 0
        }

        // Add direct edges (bidirectional)
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], weight = edge[2];
            dist[u][v] = weight;
            dist[v][u] = weight;
        }

        // Step 2: Floyd-Warshall Algorithm
        // For each intermediate vertex k, check if path through k is shorter
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // If path i->k->j is shorter than direct path i->j, update it
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        // Step 3: Find city with minimum reachable cities within threshold
        int minReachableCount = Integer.MAX_VALUE;
        int resultCity = -1;

        for (int i = 0; i < n; i++) {
            int reachableCount = 0;

            // Count cities reachable within threshold (excluding self)
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] <= distanceThreshold) {
                    reachableCount++;
                }
            }

            // Update result if this city has fewer reachable cities,
            // or same count but higher index (tie-breaking rule)
            if (reachableCount < minReachableCount ||
                (reachableCount == minReachableCount && i > resultCity)) {
                minReachableCount = reachableCount;
                resultCity = i;
            }
        }

        return resultCity;
    }

    /**
     * ALTERNATIVE: Your Original Approach (Fixed)
     * Using Dijkstra from each vertex - corrected version
     */
    public int findTheCityDijkstra(int n, int[][] edges, int distanceThreshold) {
        // Create adjacency list (more space-efficient than matrix for sparse graphs)
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        // Build bidirectional graph
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], weight = edge[2];
            graph.get(u).add(new int[]{v, weight});
            graph.get(v).add(new int[]{u, weight});
        }

        int minReachableCount = Integer.MAX_VALUE;
        int resultCity = -1;

        // Run Dijkstra from each city
        for (int source = 0; source < n; source++) {
            int[] distances = dijkstra(graph, source, n);

            // Count reachable cities within threshold
            int reachableCount = 0;
            for (int j = 0; j < n; j++) {
                if (source != j && distances[j] <= distanceThreshold) {
                    reachableCount++;
                }
            }

            // Update result with tie-breaking
            if (reachableCount < minReachableCount ||
                (reachableCount == minReachableCount && source > resultCity)) {
                minReachableCount = reachableCount;
                resultCity = source;
            }
        }

        return resultCity;
    }

    /**
     * Single-source Dijkstra implementation
     */
    private int[] dijkstra(List<List<int[]>> graph, int source, int n) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int currentDist = current[1];

            // Skip if we've already found a shorter path
            if (currentDist > dist[node]) continue;

            // Explore neighbors
            for (int[] neighbor : graph.get(node)) {
                int nextNode = neighbor[0];
                int edgeWeight = neighbor[1];
                int newDist = currentDist + edgeWeight;

                if (newDist < dist[nextNode]) {
                    dist[nextNode] = newDist;
                    pq.offer(new int[]{nextNode, newDist});
                }
            }
        }

        return dist;
    }

    /**
     * Test method with comprehensive examples
     */
    public static void main(String[] args) {

        P008 solution = new P008();

        // Test Case 1: Basic example
        System.out.println("=== Test Case 1 ===");
        int n1 = 4;
        int[][] edges1 = {{0,1,3},{1,2,1},{1,3,4},{2,3,1}};
        int threshold1 = 4;

        System.out.println("Cities: " + n1);
        System.out.println("Edges: " + Arrays.deepToString(edges1));
        System.out.println("Threshold: " + threshold1);

        int result1 = solution.findTheCity(n1, edges1, threshold1);
        int result1Alt = solution.findTheCityDijkstra(n1, edges1, threshold1);

        System.out.println("Result (Floyd-Warshall): " + result1);
        System.out.println("Result (Dijkstra): " + result1Alt);
        System.out.println("Expected: 3\n");

        // Test Case 2: Tie-breaking example
        System.out.println("=== Test Case 2 ===");
        int n2 = 5;
        int[][] edges2 = {{0,1,2},{0,4,8},{1,2,3},{1,4,2},{2,3,1},{3,4,1}};
        int threshold2 = 2;

        System.out.println("Cities: " + n2);
        System.out.println("Edges: " + Arrays.deepToString(edges2));
        System.out.println("Threshold: " + threshold2);

        int result2 = solution.findTheCity(n2, edges2, threshold2);
        int result2Alt = solution.findTheCityDijkstra(n2, edges2, threshold2);

        System.out.println("Result (Floyd-Warshall): " + result2);
        System.out.println("Result (Dijkstra): " + result2Alt);
        System.out.println("Expected: 0\n");

        // Performance comparison
        System.out.println("=== Algorithm Comparison ===");
        System.out.println("Floyd-Warshall:");
        System.out.println("  ✓ Time: O(V³), Space: O(V²)");
        System.out.println("  ✓ Simpler implementation");
        System.out.println("  ✓ Better for dense graphs");
        System.out.println("  ✓ More cache-friendly");

        System.out.println("\nDijkstra from each vertex:");
        System.out.println("  ✓ Time: O(V² log V), Space: O(V + E)");
        System.out.println("  ✓ Better for sparse graphs");
        System.out.println("  ✓ Can terminate early if needed");
        System.out.println("  ✓ More complex implementation");

        System.out.println("\nFor this problem:");
        System.out.println("  • Floyd-Warshall is preferred (simpler, same complexity)");
        System.out.println("  • Both solutions are correct and efficient");
    }
}