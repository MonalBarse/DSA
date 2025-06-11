package com.monal.Graphs.ShortestDistance;

import java.util.*;

/*
Cheapest Flights Within K Stop
There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.
You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.
Example 1:
  Input: n = 4, flights = [[0,1,100],[1,2,100],[2,0,100],[1,3,600],[2,3,200]], src = 0, dst = 3, k = 1
  Output: 700
  The optimal path with at most 1 stop from city 0 to 3 has cost 100 + 600 = 700.
  Note that the path through cities [0,1,2,3] is cheaper but is invalid because it uses 2 stops.
Example 2:
  Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 1
  Output: 200
  The optimal path with at most 1 stop from city 0 to 2 is has cost 100 + 100 = 200.
Example 3:
  Input: n = 3, flights = [[0,1,100],[1,2,100],[0,2,500]], src = 0, dst = 2, k = 0
  Output: 500
  The optimal path with no stops from city 0 to 2 has cost 500.

*/
public class P005 {
  class Solution {

    class Flight {
      int to;
      int price;

      public Flight(int to, int price) {
        this.to = to;
        this.price = price;
      }
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
      // adjacency list
      List<List<Flight>> adj = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        adj.add(new ArrayList<>());
      }

      for (int[] flight : flights) {
        int from = flight[0], to = flight[1], price = flight[2];
        adj.get(from).add(new Flight(to, price));
      }
      // minimum cost to reach each node in up to k stops
      int[] minCost = new int[n];
      Arrays.fill(minCost, Integer.MAX_VALUE);
      minCost[src] = 0;

      Queue<int[]> queue = new ArrayDeque<>();
      queue.offer(new int[] { src, 0, 0 }); // [node, costs, stops]
      while (!queue.isEmpty()) {
        int[] curr = queue.poll();
        int currentNode = curr[0], costSoFar = curr[1], stopsSoFar = curr[2];

        if (stopsSoFar > k)
          continue;

        for (Flight neighbor : adj.get(currentNode)) {
          int nextNode = neighbor.to;
          int totalCost = costSoFar + neighbor.price;

          // Important: Only push if this path is better for now
          if (totalCost < minCost[nextNode]) {
            minCost[nextNode] = totalCost;
            queue.offer(new int[] { nextNode, totalCost, stopsSoFar + 1 });
          }
        }
      }

      return minCost[dst] == Integer.MAX_VALUE ? -1 : minCost[dst];
    }
  }

  public static void main(String[] args) {
    Solution solution = new P005().new Solution();
    int n = 4;
    int[][] flights = { { 0, 1, 100 }, { 1, 2, 100 }, { 2, 0, 100 }, { 1, 3, 600 }, { 2, 3, 200 } };
    int src = 0;
    int dst = 3;
    int k = 1;
    System.out.println(solution.findCheapestPrice(n, flights, src, dst, k)); // Output: 700

    n = 3;
    flights = new int[][] { { 0, 1, 100 }, { 1, 2, 100 }, { 0, 2, 500 } };
    src = 0;
    dst = 2;
    k = 1;
    System.out.println(solution.findCheapestPrice(n, flights, src, dst, k)); // Output: 200
  }
}
