package com.monal.Graphs.ShortestDistance;

import java.util.*;

public class P001 {

  /*
   * Shortest Distance in a Directed Acyclic Graph (DAG) with weighted edges.
   * In weighted graphs (if non cyclic), we can use Topological Sort to find the
   * shortest distance from a source node to all other nodes.
   */
  public class ShortestDistWeighted {

    // Shortest Distance in a DAG using TOPO
    private int[] shortestDist(int n, int[][] edges, int src) {
      // Build the adj list
      ArrayList<ArrayList<Pair>> adj = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        adj.add(new ArrayList<>());
      }

      for (int[] edge : edges) {
        int u = edge[0];
        int v = edge[1];
        int wt = edge[2];
        adj.get(u).add(new Pair(v, wt));
      }

      // Topo sort (Bfs - Kahn's Algorithm)
      int[] indegree = new int[n];
      for (int i = 0; i < n; i++) {
        for (Pair p : adj.get(i)) {
          indegree[p.node]++;
        }
      }

      // Detect the starting point
      Queue<Integer> queue = new LinkedList<>();

      for (int i = 0; i < n; i++) {
        if (indegree[i] == 0) {
          queue.offer(i);
        }
      }

      List<Integer> order = new ArrayList<>(); // TO store the topological order

      // loop is basically checking from queue that whatever was is the queue,
      // we explore it first add it to order then explore what is connected to it
      // and reduce the indegree of that node, if it becomes 0 (that means it is next
      // in the topological order) then we add it to the queue.
      while (!queue.isEmpty()) {
        int node = queue.poll();
        order.add(node);

        for (Pair p : adj.get(node)) {
          indegree[p.node]--;
          if (indegree[p.node] == 0) {
            queue.offer(p.node);
          }
        }
      }

      // Initialize distances
      int[] dist = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[src] = 0;

      // Relax edges in topological order
      // relaxing means that we are checking if the distance to the node
      // can be minimized by going through the current node.

      // Start with first node in topological order (FIFO - we used queue)
      for (int node : order) {
        // If the node is unreachable, skip it
        if (dist[node] == Integer.MAX_VALUE)
          continue;
        for (Pair p : adj.get(node)) {
          int nextNode = p.node, weight = p.wt;
          // the distance to next node is the minimum of the current distance
          // and the distance to the current node plus the wt(dist) of the edge
          dist[nextNode] = Math.min(dist[nextNode], dist[node] + weight);
        }
      }
      // Replace unreachable nodes with -1
      for (int i = 0; i < n; i++) {
        if (dist[i] == Integer.MAX_VALUE) {
          dist[i] = -1;
        }
      }

      return dist;

    }

    private int[] shortestDistDFS(int n, int m, int[][] edges, int src) {
      // build graph adjlist
      ArrayList<ArrayList<Pair>> graph = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }

      for (int[] edge : edges) {
        int u = edge[0];
        int v = edge[1];
        int wt = edge[2];
        graph.get(u).add(new Pair(v, wt));
      }

      // Start TOPO SORT
      boolean[] visited = new boolean[n];
      Stack<Integer> st = new Stack<>();

      for (int i = 0; i < n; i++) {
        if (!visited[i]) {
          topoSort(i, graph, st, visited);
        }
      }

      // count the distance
      int dist[] = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[src] = 0;

      while (!st.isEmpty()) {
        int curr = st.pop();
        for (Pair neighbor : graph.get(curr)) {
          int node = neighbor.node;
          int currDist = neighbor.wt;
          dist[node] = Math.min(dist[node], dist[curr] + currDist);
        }
      }
      for (int i = 0; i < n; i++) {
        if (dist[i] == Integer.MAX_VALUE) {
          dist[i] = -1;
        }
      }
      return dist;
    }

    private void topoSort(int node, ArrayList<ArrayList<Pair>> graph, Stack<Integer> st, boolean[] visited) {
      visited[node] = true;
      for (Pair neigh : graph.get(node)) {
        int curr = neigh.node;
        if (!visited[curr]) {
          topoSort(curr, graph, st, visited);
        }
      }
      st.add(node);
    }

    class Pair {
      int node;
      int wt;

      Pair(int node, int wt) {
        this.node = node;
        this.wt = wt;
      }
    }
  }

  public class ShortestDisUnweighted {
    /*
     * Whiles solving for unweighted graphs we can use normal BFS to find the
     * shortest distance from a source node to all other nodes.
     */
    private int[] shortestDistance(int[][] edges, int n, int m, int src) {
      // Create a AdjList
      ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

      for (int i = 0; i < n; i++) {
        graph.add(new ArrayList<>());
      }
      for (int[] edge : edges) {
        int u = edge[0];
        int v = edge[1];
        graph.get(u).add(v);
        graph.get(v).add(u); // since it is undirected
      }

      // start bfs from src
      boolean visited[] = new boolean[n];
      Queue<Integer> q = new ArrayDeque<>();
      q.offer(src);

      int[] dist = new int[n];
      Arrays.fill(dist, Integer.MAX_VALUE);
      dist[src] = 0;

      while (!q.isEmpty()) {
        int curr = q.poll();
        for (int neigh : graph.get(curr)) {
          if (!visited[neigh]) {
            dist[neigh] = dist[curr] + 1;
            visited[neigh] = true;
            q.offer(neigh);
          }
        }
      }

      for (int i = 0; i < n; i++) {
        if (dist[i] == Integer.MAX_VALUE) {
          dist[i] = -1;
        }
      }

      return dist;
    }
  }

  public static void main(String[] args) {
    P001 p = new P001();
    // Example usage of ShortestDistWeighted
    ShortestDistWeighted sdw = p.new ShortestDistWeighted();
    int[][] edges = { { 0, 1, 5 }, { 0, 2, 3 }, { 1, 3, 6 }, { 1, 2, 2 }, { 2, 3, 7 }, { 3, 4, 2 }, { 4, 5, 1 } };
    int n = 6; // number of nodes
    int src = 0; // source node
    int[] result = sdw.shortestDist(n, edges, src);
    System.out.println(Arrays.toString(result));

    // Example usage of ShortestDistDFS
    ShortestDisUnweighted sdu = p.new ShortestDisUnweighted();
    int[][] unweightedEdges = { { 0, 1 }, { 0, 2 }, { 1, 3 }, { 2, 3 }, { 3, 4 }, { 4, 5 } };
    int nUnweighted = 6; // number of nodes
    int mUnweighted = unweightedEdges.length; // number of edges
    int srcUnweighted = 0; // source node
    int[] unweightedResult = sdu.shortestDistance(unweightedEdges, nUnweighted, mUnweighted, srcUnweighted);
    System.out.println(Arrays.toString(unweightedResult));
  }

}
