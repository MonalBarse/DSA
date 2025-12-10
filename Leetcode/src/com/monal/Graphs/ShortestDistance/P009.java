package com.monal.Graphs.ShortestDistance;

import java.io.*;
import java.util.*;

/*
Problem: Loudspeakers Network Propagation
You are given A cities, numbered 1 to A, connected by bidirectional roads described in a 2D array B,
where each element B[i] = [u, v] indicates a road between city u and city v.

There are certain cities that have loudspeakers, represented by an array C.
All loudspeakers have equal intensity, denoted by an integer x.

A loudspeaker’s voice can be heard by all cities that are at most distance x away (in terms of number of roads).

When a loudspeaker hears another loudspeaker’s voice, it automatically starts broadcasting as well.

Initially, only the loudspeaker at city D is manually turned on.
You must determine the minimum intensity x such that the voice from the source loudspeaker at city D eventually reaches the destination city E (after possible chain activations of other loudspeakers).

If it’s impossible to reach E, return -1.

Input Format:
  - The first argument is an integer A, the number of cities.
  - The second argument is a 2D integer array B, where each element B[i] = [u, v] indicates a road between city u and city v.
  - The third argument is an integer array C, representing the cities with loudspeakers.
  - The fourth argument is an integer D, the source city with the initially turned-on loudspeaker.
  - The fifth argument is an integer E, the destination city.

Output Format:
  - Return a single integer representing the minimum intensity x required for the voice to reach city E,
    or -1 if it’s impossible.

Example:

Input:
  A = 6
  B = [[1,2], [2,3], [3,4], [4,5], [5,6]]
  C = [1, 3, 5]
  D = 1
  E = 6

Output:
2

Explaination
   - First D (city 1) broadcasts with intensity 2, reaching city 3 (via city 2).
   - City 3's loudspeaker activates and broadcasts, reaching city 5 (via city 4).
   - City 5's loudspeaker activates and broadcasts, reaching city 6.
   - Thus, the minimum intensity required is 2.

*/

public class P009 {

  private int minimumIntensity(int A, int[][] B, int[] C, int D, int E) {
    // Build the graph
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i <= A; i++) graph.add(new ArrayList<>());
    for (int[] road : B) {
      graph.get(road[0]).add(road[1]);
      graph.get(road[1]).add(road[0]);
    }
    Set<Integer> speakers = new HashSet<>();
    for (int c : C) speakers.add(c);

    // Binary search for minimum intensity
    int left = 0, right = A; // Max possible intensity is A (worst case)
    int answer = -1;
    while (left <= right) {
      int mid = left + (right - left) / 2;
      if (canReachDestination(graph, speakers, D, E, mid)) {
        answer = mid;
        right = mid - 1; // Try for a smaller intensity
      } else {
        left = mid + 1; // Increase intensity
      }
    }
    return answer;
  }

  private boolean canReachDestination(
      List<List<Integer>> graph, Set<Integer> speakers, int D, int E, int intensity) {

    Queue<int[]> q = new ArrayDeque<>(); // {currentCity, distanceFromLastSpeaker}
    boolean[] visited = new boolean[graph.size()];
    q.offer(new int[] {D, 0}); // Start from city D, distance 0
    visited[D] = true;

    // BFS
    while (!q.isEmpty()) {
      int[] curr = q.poll();
      int city = curr[0], dist = curr[1];

      if (city == E) return true; // Reached destination

      int newDist = dist;
      if (speakers.contains(city)) newDist = 0; // This speaker activates and starts broadcasting

      for (int neigh : graph.get(city)) {
        if (!visited[neigh] && newDist + 1 <= intensity) {
          visited[neigh] = true;
          q.offer(new int[] {neigh, newDist + 1});
        }
      }
    }
    return false;
  }

  public static void main(String[] args) {
    P009 p009 = new P009();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      int A = Integer.parseInt(br.readLine().trim());
      int M = Integer.parseInt(br.readLine().trim());
      int[][] B = new int[M][2];
      for (int i = 0; i < M; i++) {
        String[] parts = br.readLine().trim().split(" ");
        B[i][0] = Integer.parseInt(parts[0]);
        B[i][1] = Integer.parseInt(parts[1]);
      }
      String[] cParts = br.readLine().trim().split(" ");
      int[] C = new int[cParts.length];

      for (int i = 0; i < cParts.length; i++) C[i] = Integer.parseInt(cParts[i]);

      int D = Integer.parseInt(br.readLine().trim());
      int E = Integer.parseInt(br.readLine().trim());

      int result = p009.minimumIntensity(A, B, C, D, E);
      System.out.println(result);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
