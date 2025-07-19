package com.monal.Graphs.BFS_DFS;

import java.util.*;

// https://leetcode.com/problems/number-of-provinces/description/
/*
There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c.

A province is a group of directly or indirectly connected cities and no other cities outside of the group.

You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.

Return the total number of provinces.


 */
public class P001 {
    class Solution {
        // we are given a adj matrix
        public int findCircleNum(int[][] isConnected) {
            boolean visited[] = new boolean[isConnected.length];

            // to track connected province
            int Tprovience = 0;
            // check for every city
            for (int i = 0; i < isConnected.length; i++) {
                if (!visited[i]) {
                    // dfs(isConnected, i, visited);
                    bfs(isConnected, i, visited);
                    Tprovience++;
                }
            }
            return Tprovience;
        }

        public void dfs(int[][] graph, int i, boolean[] visited) {
            // mark the curent node visited
            visited[i] = true;

            for (int j = 0; j < graph.length; j++) {
                // If city j is connected to current city i and not visited
                if (graph[i][j] == 1 && !visited[j]) {
                    dfs(graph, j, visited);
                }
            }
        }

        public void bfs(int[][] graph, int start, boolean[] visited) {
            Queue<Integer> q = new LinkedList<>();
            q.offer(start);
            visited[start] = true;

            while (!q.isEmpty()) {
                int city = q.poll();
                for (int neighbor = 0; neighbor < graph.length; neighbor++) {
                    if (graph[city][neighbor] == 1 && !visited[neighbor]) {
                        // if not visited and current city and neighbor are connected
                        visited[neighbor] = true;
                        q.offer(neighbor);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Solution solution = new P001().new Solution();
        int[][] isConnected = {
                { 1, 1, 0 },
                { 1, 1, 0 },
                { 0, 0, 1 }
        };
        int result = solution.findCircleNum(isConnected);
        System.out.println("Total number of provinces: " + result); // Output: 2

        int[][] isConnected2 = {
                { 1, 0, 0, 1 },
                { 0, 1, 0, 0 },
                { 0, 0, 1, 0 },
                { 1, 0, 0, 1 }
        };

        int result2 = solution.findCircleNum(isConnected2);
        System.out.println("Total number of provinces: " + result2); // Output: 3

    }
}
