package com.monal.Graphs.Topological;

import java.util.*;
/*
 * TOpological Sort and Graph Algorithms
 */

public class P001 {

  // ================================================================= //
  // ---------- PROBLEM 1: COURSE SCHEDULE (LEETCODE 207) ------------ //
  // ================================================================= //

  /*
   * Problem: There are 'numCourses' courses labeled 0 to numCourses-1.
   * Given prerequisites array where prerequisites[i] = [ai, bi] means
   * you must take course bi before course ai.
   *
   * Return true if you can finish all courses.
   *
   * Example: numCourses = 2, prerequisites = [[1,0]]
   * Output: true (take course 0, then course 1)
   *
   * Example: numCourses = 2, prerequisites = [[1,0],[0,1]]
   * Output: false (circular dependency!)
   */
   public static boolean canFinish(int numCourses, int[][] prerequisites) {
    // Build adjacency list
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) {
      adj.add(new ArrayList<>());
    }

    // Calculate indegrees
    int[] indegree = new int[numCourses];
    for (int[] prereq : prerequisites) {
      int course = prereq[0];
      int prerequisite = prereq[1];
      adj.get(prerequisite).add(course);
      indegree[course]++;
    }

    // Kahn's Algorithm
    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (indegree[i] == 0) {
        queue.offer(i);
      }
    }

    int completedCourses = 0;
    while (!queue.isEmpty()) {
      int course = queue.poll();
      completedCourses++;

      for (int nextCourse : adj.get(course)) {
        indegree[nextCourse]--;
        if (indegree[nextCourse] == 0) {
          queue.offer(nextCourse);
        }
      }
    }

    return completedCourses == numCourses;
  }

  // ============================================================= //
  public static void main(String[] args) {

    // Test 1: Course Schedule
    System.out.println("1. Course Schedule:");
    System.out.println("Can finish [[1,0]]: " +
        canFinish(2, new int[][] { { 1, 0 } })); // true
    System.out.println("Can finish [[1,0],[0,1]]: " +

        canFinish(2, new int[][] { { 1, 0 }, { 0, 1 } })); // false
  }

}
