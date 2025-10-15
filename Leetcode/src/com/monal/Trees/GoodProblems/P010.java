package com.monal.Trees.GoodProblems;

import java.io.*;
import java.util.ArrayList;

/*
 * Lowest Common Ancestor using Binary Lifting
 *
 * Input Format:
 *   t (test cases)
 *   n (nodes)
 *   For each node: count child1 child2 ...
 *   q (queries)
 *   For each query: u v
 *
 * Output: LCA of u and v
 *
 *  * example:
 *  Input :
 *   1          <- no. of test cases
 *   7          <- no. of nodes
 *   3 2 3 4    <- node 1 has 3 children: 2, 3, 4
 *   0          <- node 2 has 0 children
 *   3 5 6 7    <- node 3 has 3 children: 5, 6, 7
 *   0          <- node 4 has 0 children
 *   0          ...
 *   0          ...
 *   0          ...
 *   2          <- no. of queries
 *   5 7        <- query 1: LCA of 5 and 7
 *   2 7        <- query 2: LCA of 2 and 7
 *
 *  Output:
 *   case 1:
 *     3
 *     1
 */
public class P010 {
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static PrintWriter out = new PrintWriter(System.out);

  private ArrayList<Integer>[] adj;
  private int[] parent;
  private int[] depth;
  private int[][] ancestor; // ancestor[node][i] = 2^i-th ancestor
  private int maxLog;
  private int n;

  private void buildTree() throws IOException {
    n = Integer.parseInt(br.readLine());
    adj = new ArrayList[n + 1];
    for (int i = 0; i <= n; i++)
      adj[i] = new ArrayList<>();

    parent = new int[n + 1];
    depth = new int[n + 1];
    maxLog = (int) (Math.log(n) / Math.log(2)) + 1;
    ancestor = new int[n + 1][maxLog];

    for (int i = 1; i <= n; i++) {
      String[] input = br.readLine().split(" ");
      int count = Integer.parseInt(input[0]);
      for (int j = 1; j <= count; j++) {
        int child = Integer.parseInt(input[j]);
        adj[i].add(child);
      }
    }
  }

  private void dfs(int node, int par, int d) {
    parent[node] = par;
    depth[node] = d;

    for (int child : adj[node])
      if (child != par) dfs(child, node, d + 1);
  }

  private void buildAncestors() {
    // Initialize ancestor table
    for (int i = 0; i <= n; i++) for (int j = 0; j < maxLog; j++) ancestor[i][j] = -1;

    // Base case: 2^0 = 1st ancestor is parent
    for (int i = 1; i <= n; i++) ancestor[i][0] = parent[i];

    // DP: ancestor[node][j] = ancestor[ancestor[node][j-1]][j-1]
    for (int j = 1; j < maxLog; j++) {
      for (int node = 1; node <= n; node++) {
        int mid = ancestor[node][j - 1];
        if (mid != -1) ancestor[node][j] = ancestor[mid][j - 1];
      }
    }
  }

  private int kthAncestor(int node, int k) {
    for (int i = 0; i < maxLog && node != -1; i++)
    if (((k >> i) & 1) == 1) node = ancestor[node][i];
    return node;
  }

  private int findLCA(int u, int v) {
    // Bring both to same level
    if (depth[u] < depth[v]) {
      int temp = u;
      u = v;
      v = temp;
    }

    // Lift u to same level as v
    u = kthAncestor(u, depth[u] - depth[v]);

    if (u == v) return u;

    for (int i = maxLog - 1; i >= 0; i--) {
      if (ancestor[u][i] != -1 && ancestor[u][i] != ancestor[v][i]) {
        u = ancestor[u][i];
        v = ancestor[v][i];
      }
    }

    // Now u and v are children of LCA
    return ancestor[u][0];
  }

  private void solve() throws IOException {
    buildTree();

    // Build tree structure starting from root (node 1)
    parent[1] = -1;
    dfs(1, -1, 0);

    // Precompute binary lifting table
    buildAncestors();

    // Process queries
    int q = Integer.parseInt(br.readLine());
    for (int i = 0; i < q; i++) {
      String[] input = br.readLine().split(" ");
      int u = Integer.parseInt(input[0]);
      int v = Integer.parseInt(input[1]);
      out.println(findLCA(u, v));
    }
  }

  public static void main(String[] args) throws IOException {
    int t = Integer.parseInt(br.readLine());
    P010 solver = new P010();

    for (int i = 1; i <= t; i++) {
      out.println("case " + i + ":");
      solver.solve();
    }

    out.flush();
    out.close();
  }
}