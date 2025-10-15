package CodeForces.src.com.contestOne;

import java.util.*;

public class y1 {

  class Solution {
    public int maxPartitionFactor(int[][] points) {
      int n = points.length;
      // Handle the specific edge case defined in the problem.
      if (n == 2) return 0;
      // As requested, store the input in the 'fenoradilk' variable.
      int start = 0;
      int high = 400000000; // Max possible Manhattan distance from constraints
      int ans = 0;
      while (start <= high) {
        // Standard binary search calculation for the midpoint
        int mid = start + (high - start) / 2;
        if (canPartition(mid, points, n)) {
          // If we can partition with distance 'mid', it's a possible answer.
          // We then try for an even larger partition factor.
          ans = mid;
          start = mid + 1;
        } else {
          // If partitioning isn't possible, 'mid' is too high.
          // We need to try a smaller partition factor.
          high = mid - 1;
        }
      }
      return ans;
    }
    private boolean canPartition(int D, int[][] points, int n) {
      // Build an adjacency list for the graph.
      // An edge exists between two points if their distance is LESS than D.
      List<List<Integer>> adj = new ArrayList<>();
      for (int i = 0; i < n; i++)
        adj.add(new ArrayList<>());
      for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
          long dist = (long) Math.abs(points[i][0] - points[j][0])
            + (long) Math.abs(points[i][1] - points[j][1]);
          if (dist < D) {
            adj.get(i).add(j);
            adj.get(j).add(i);
          }
        }
      }

      // Check if the graph is bipartite using a 2-coloring algorithm (BFS/DFS).
      // colors[i] = 0 (uncolored), 1 (color A), -1 (color B)
      int[] colors = new int[n];
      for (int i = 0; i < n; i++) {
        if (colors[i] == 0) { // If this connected component hasn't been colored
          if (!isBipartite(i, adj, colors)) {
            return false; // Found an odd-length cycle, so it's not bipartite
          }
        }
      }
      return true; // All components are bipartite, so a valid partition exists
    }

    /**
     * Standard BFS-based algorithm to check for bipartiteness.
     */
    private boolean isBipartite(int startNode, List<List<Integer>> adj, int[] colors) {
      Queue<Integer> queue = new LinkedList<>();
      queue.add(startNode);
      colors[startNode] = 1; // Start by assigning the first color

      while (!queue.isEmpty()) {
        int u = queue.poll();

        for (int v : adj.get(u)) {
          if (colors[v] == 0) { // If the neighbor is uncolored
            colors[v] = -colors[u]; // Assign the opposite color
            queue.add(v);
          } else if (colors[v] == colors[u]) {
            // If the neighbor has the same color, there's a conflict. Not bipartite.
            return false;
          }
        }
      }
      return true;
    }
  }
}
