package com.monal.Graphs.MST;

import java.util.*;

/*
1202. Smallest String With Swaps
You are given a string s, and an array of pairs of indices in the string pairs where pairs[i] = [a, b] indicates 2 indices(0-indexed) of the string.
You can swap the characters at any pair of indices in the given pairs any number of times.
Return the lexicographically smallest string that s can be changed to after using the swaps.
Example
  Input: s = "dcab", pairs = [[0,3],[1,2]]
  Output: "bacd"
  Explaination:
  Swap s[0] and s[3], s = "bcad"
  Swap s[1] and s[2], s = "bacd"
*/
public class P007 {
  class Solution {
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
      DisjointSet djs = new DisjointSet(s.length());
      for (List<Integer> pair : pairs)
        djs.union(pair.get(0), pair.get(1));
      // Add indices to their respective sets
      Map<Integer, PriorityQueue<Character>> map = new HashMap<>();
      for (int i = 0; i < s.length(); i++) {
        int root = djs.find(i);
        map.computeIfAbsent(root, k -> new PriorityQueue<>()).offer(s.charAt(i));
      }

      StringBuilder result = new StringBuilder();
      for (int i = 0; i < s.length(); i++) {
        int root = djs.find(i);
        result.append(map.get(root).poll());
      }

      return new String(result);
    }
  }

  class DisjointSet {
    int[] parent, rank;

    DisjointSet(int n) {
      parent = new int[n];
      rank = new int[n];
      for (int i = 0; i < n; i++)
        parent[i] = i;
    }

    int find(int x) {
      if (parent[x] != x)
        parent[x] = find(parent[x]);
      return parent[x];
    }

    void union(int x, int y) {
      int rootX = find(x), rootY = find(y);
      if (rootX == rootY)
        return;

      if (rank[rootX] < rank[rootY])
        parent[rootX] = rootY;
      else if (rank[rootX] > rank[rootY])
        parent[rootY] = rootX;
      else {
        parent[rootY] = rootX;
        rank[rootX]++;
      }
    }
  }
}
