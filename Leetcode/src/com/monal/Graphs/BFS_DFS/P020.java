package com.monal.Graphs.BFS_DFS;

import java.util.*;

public class P020 {
    class Solution {
        public class Pair {
            int node;
            boolean isRed;

            Pair(int edge, boolean isRed) {
                this.node = edge;
                this.isRed = isRed;
            }
        }

        public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
            List<List<Pair>> graph = new ArrayList<>();

            for (int i = 0; i < n; i++)
                graph.add(new ArrayList<>());
            for (int[] edge : redEdges)
                graph.get(edge[0]).add(new Pair(edge[1], true));
            for (int[] edge : blueEdges)
                graph.get(edge[0]).add(new Pair(edge[1], false));
            int[] answer = new int[n];
            answer[0] = 0;

            // BFS to find the shortest alternating paths
            Queue<Pair> q = new ArrayDeque<>();
            boolean[][] visited = new boolean[n][2]; // visited[node][0] for red, visited[node][1] for blue
            q.offer(new Pair(0, true)); // Start with red edge
            q.offer(new Pair(0, false)); // Start with blue edge
            /* Let’s say node 0 has only red outgoing edges.
             * (0, true) — will look for blue edges next → finds none → dead end.
             * (0, false) — will look for red edges next → valid! → proceeds with BFS.
             */
            visited[0][0] = true;
            visited[0][1] = true;

            int steps = 0;
            while (!q.isEmpty()) {
                int size = q.size();
                // Process all nodes at the current step
                for (int i = 0; i < size; i++) {
                    Pair current = q.poll();
                    int node = current.node;
                    boolean isRed = current.isRed;

                    // If we haven't visited this node with the current edge color
                    if (answer[node] == 0 && node != 0)
                        answer[node] = steps;
                    // Explore neighbors
                    for (Pair neighbor : graph.get(node)) {
                        if (neighbor.isRed != isRed && !visited[neighbor.node][neighbor.isRed ? 0 : 1]) {
                            visited[neighbor.node][neighbor.isRed ? 0 : 1] = true;
                            q.offer(new Pair(neighbor.node, neighbor.isRed));
                        }
                    }
                }
                steps++;
            }

            // Fill in -1 for unreachable nodes
            for (int i = 0; i < n; i++)
                if (answer[i] == 0 && i != 0)
                    answer[i] = -1;
            return answer;
        }
    }
}
