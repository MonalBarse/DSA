package com.monal.Graphs.BFS_DFS;

import java.util.*;

/*
You are given an array of variable pairs equations and an array of real numbers values,
where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i].
Each Ai or Bi is a string that represents a single variable.
You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query
where you must find the answer for Cj / Dj = ?.
Return the answers to all queries. If a single answer cannot be determined, return -1.0.
Note: The input is always valid. You may assume that evaluating the queries will not result in division by zero and that there is no contradiction.
Note: The variables that do not occur in the list of equations are undefined, so the answer cannot be determined for them.

Example 1:
  Input: equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]

  Output: [6.00000,0.50000,-1.00000,1.00000,-1.00000]

  Explanation:
    Given: a / b = 2.0, b / c = 3.0
    queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ?
    return: [6.0, 0.5, -1.0, 1.0, -1.0 ]
    note: x is undefined => -1.0
*/

/*
 * Note that how cleverly this is a graph problem, we can represent the equations as a graph
 * a / b = 2.0, b / c = 3.0
 * a -> b -> c
 * a -> c = 2.0 * 3.0 = 6.0
 * b -> a = 1 / 2.0 = 0.5
 * a -> a = 1.0
 * x -> x = -1.0
 */
public class P012 {

  public class Pair {
    String node;
    double weight;

    public Pair(String node, double weight) {
      this.node = node;
      this.weight = weight;
    }
  }

  class Solution {

    Map<String, List<Pair>> graph;

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
      // 1.
      buildGraph(equations, values);

      // 2.
      double[] results = new double[queries.size()];
      for (int i = 0; i < queries.size(); i++)
        // 3.
        results[i] = dfs(queries.get(i).get(0), queries.get(i).get(1), new HashSet<>());

      return results;
    }

    private void buildGraph(List<List<String>> equations, double[] values) {
      graph = new HashMap<>();
      for (int i = 0; i < equations.size(); i++) {
        String a = equations.get(i).get(0), b = equations.get(i).get(1);
        graph.computeIfAbsent(a, k -> new ArrayList<>()).add(new Pair(b, values[i]));
        graph.computeIfAbsent(b, k -> new ArrayList<>()).add(new Pair(a, 1.0 / values[i]));
      }
    }

    private double dfs(String start, String end, Set<String> visited) {
      if (!graph.containsKey(start) || !graph.containsKey(end))
        return -1.0;
      if (start.equals(end))
        return 1.0;

      visited.add(start);
      for (Pair neighbor : graph.get(start)) {
        if (!visited.contains(neighbor.node)) {
          double result = dfs(neighbor.node, end, visited);
          if (result != -1.0)
            return result * neighbor.weight;
        }
      }
      visited.remove(start);
      return -1.0;
    }
  }

  public class Solution2 {
    Map<String, String> parent = new HashMap<>();
    Map<String, Double> weight = new HashMap<>();

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
      for (int i = 0; i < equations.size(); i++) {
        String a = equations.get(i).get(0);
        String b = equations.get(i).get(1);
        double val = values[i];
        union(a, b, val);
      }

      double[] results = new double[queries.size()];
      for (int i = 0; i < queries.size(); i++) {
        String a = queries.get(i).get(0);
        String b = queries.get(i).get(1);

        if (!parent.containsKey(a) || !parent.containsKey(b)) {
          results[i] = -1.0;
          continue;
        }

        Pair rootA = find(a);
        Pair rootB = find(b);

        if (!rootA.node.equals(rootB.node)) {
          results[i] = -1.0;
        } else {
          results[i] = rootA.weight / rootB.weight;
        }
      }
      return results;
    }

    private void union(String a, String b, double value) {
      parent.putIfAbsent(a, a);
      weight.putIfAbsent(a, 1.0);
      parent.putIfAbsent(b, b);
      weight.putIfAbsent(b, 1.0);

      Pair rootA = find(a), rootB = find(b);

      if (!rootA.node.equals(rootB.node)) {
        parent.put(rootA.node, rootB.node);
        // Adjust weight:
        // a / rootA * value = b / rootB => rootA = (b / rootB) / value
        weight.put(rootA.node, value * rootB.weight / rootA.weight);
      }
    }

    private Pair find(String x) {
      if (!x.equals(parent.get(x))) {
        String originalParent = parent.get(x);
        Pair root = find(originalParent);

        parent.put(x, root.node); // Path compression
        weight.put(x, weight.get(x) * root.weight); // Weight update
      }

      return new Pair(parent.get(x), weight.get(x));
    }
  }

}
