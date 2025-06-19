package com.monal.Graphs.ShortestDistance;

import java.util.*;

/* You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between some intersections. The inputs are generated such that you can reach any intersection from any other intersection and that there is at most one road between any two intersections.
You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] means that there is a road between intersections ui and vi that takes timei minutes to travel. You want to know in how many ways you can travel from intersection 0 to intersection n - 1 in the shortest amount of time.
Return the number of ways you can arrive at your destination in the shortest amount of time. Since the answer may be large, return it modulo 109 + 7.

Example 1
  Input: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
  Output: 4
  Explanation: The shortest amount of time it takes to go from intersection 0 to intersection 6 is 7 minutes.
  The four ways to get there in 7 minutes are:
  - 0 ➝ 6
  - 0 ➝ 4 ➝ 6
  - 0 ➝ 1 ➝ 2 ➝ 5 ➝ 6
  - 0 ➝ 1 ➝ 3 ➝ 5 ➝ 6
Example 2:
  Input: n = 2, roads = [[1,0,10]]
  Output: 1
  Explanation: There is only one way to go from intersection 0 to intersection 1, and it takes 10 minutes.
 */
public class P006 {
  class Solution {
    public int countPaths(int n, int[][] roads) {
      final int MOD = 1_000_000_007;

      // create adj list
      ArrayList<ArrayList<int[]>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }

      for (int[] road : roads) {
        int u = road[0];
        int v = road[1];
        int dist = road[2];
        graph.get(u).add(new int[] { dist, v });
        graph.get(v).add(new int[] { dist, u }); // since roads are bi-directional
      }

      // We maintain a distance array and a number of ways array
      int[] numWays = new int[n];
      long[] dist = new long[n]; // Use long to prevent overflow
      Arrays.fill(dist, Long.MAX_VALUE);
      Arrays.fill(numWays, 0);
      dist[0] = 0; // distance from source to source is 0
      numWays[0] = 1; // number of ways to reach source is 1

      // Define a priority queue to process nodes
      PriorityQueue<long[]> q = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
      q.offer(new long[] { 0, 0 }); // {distance, current node}

      while (!q.isEmpty()) {
        long[] curr = q.poll();
        long currDist = curr[0];
        int currNode = (int) curr[1];

        // Skip if this is an outdated entry
        if (currDist > dist[currNode]) {
          continue;
        }

        for (int[] neighbors : graph.get(currNode)) {
          int neigh = neighbors[1];
          long distToNeigh = neighbors[0];
          long newDist = currDist + distToNeigh;

          // if we find a shorter path to the neighbor
          if (newDist < dist[neigh]) {
            dist[neigh] = newDist;
            numWays[neigh] = numWays[currNode];
            q.offer(new long[] { dist[neigh], neigh });
          } else if (newDist == dist[neigh]) {
            // we found another path with shortest distance
            numWays[neigh] = (numWays[neigh] + numWays[currNode]) % MOD;
          }
        }
      }
      return numWays[n - 1];
    }
  }

  public static void main(String[] args) {
    P006 p = new P006();
    Solution sol = p.new Solution();
    int n = 7;
    int[][] roads = {
        { 0, 6, 7 }, { 0, 1, 2 }, { 1, 2, 3 }, { 1, 3, 3 },
        { 6, 3, 3 }, { 3, 5, 1 }, { 6, 5, 1 }, { 2, 5, 1 },
        { 0, 4, 5 }, { 4, 6, 2 }
    };
    int[][] roads2 = {
        { 0, 1, 865326231 },
        { 1, 4, 865326231 },
        { 0, 2, 865326231 },
        { 2, 4, 865326231 },
        { 0, 3, 865326231 },
        { 3, 4, 865326231 },
        { 4, 5, 647618270 },
        { 5, 9, 647618270 },
        { 4, 6, 647618270 },
        { 6, 9, 647618270 },
        { 4, 7, 647618270 },
        { 7, 9, 647618270 },
        { 4, 8, 647618270 },
        { 8, 9, 647618270 },
        { 9, 10, 153310768 },
        { 10, 15, 153310768 },
        { 9, 11, 153310768 },
        { 11, 15, 153310768 },
        { 9, 12, 153310768 },
        { 12, 15, 153310768 },
        { 9, 13, 153310768 },
        { 13, 15, 153310768 },
        { 9, 14, 153310768 },
        { 14, 15, 153310768 },
        { 15, 16, 446216658 },
        { 16, 21, 446216658 },
        { 15, 17, 446216658 },
        { 17, 21, 446216658 },
        { 15, 18, 446216658 },
        { 18, 21, 446216658 },
        { 15, 19, 446216658 },
        { 19, 21, 446216658 },
        { 15, 20, 446216658 },
        { 20, 21, 446216658 },
        { 21, 22, 482432125 },
        { 22, 27, 482432125 },
        { 21, 23, 482432125 },
        { 23, 27, 482432125 },
        { 21, 24, 482432125 },
        { 24, 27, 482432125 },
        { 21, 25, 482432125 },
        { 25, 27, 482432125 },
        { 21, 26, 482432125 },
        { 26, 27, 482432125 },
        { 27, 28, 546917635 },
        { 28, 32, 546917635 },
        { 27, 29, 546917635 },
        { 29, 32, 546917635 },
        { 27, 30, 546917635 },
        { 30, 32, 546917635 },
        { 27, 31, 546917635 },
        { 31, 32, 546917635 },
        { 32, 33, 905837683 },
        { 33, 37, 905837683 },
        { 32, 34, 905837683 },
        { 34, 37, 905837683 },
        { 32, 35, 905837683 },
        { 35, 37, 905837683 },
        { 32, 36, 905837683 },
        { 36, 37, 905837683 },
        { 37, 38, 941383964 },
        { 38, 41, 941383964 },
        { 37, 39, 941383964 },
        { 39, 41, 941383964 },
        { 37, 40, 941383964 },
        { 40, 41, 941383964 },
        { 41, 42, 482278242 },
        { 42, 44, 482278242 },
        { 41, 43, 482278242 },
        { 43, 44, 482278242 },
        { 44, 45, 209029963 },
        { 45, 49, 209029963 },
        { 44, 46, 209029963 },
        { 46, 49, 209029963 },
        { 44, 47, 209029963 },
        { 47, 49, 209029963 },
        { 44, 48, 209029963 },
        { 48, 49, 209029963 },
        { 49, 50, 180362920 },
        { 50, 53, 180362920 },
        { 51, 53, 180362920 },
    };
    System.out.println(sol.countPaths(n, roads2));
  }
}
