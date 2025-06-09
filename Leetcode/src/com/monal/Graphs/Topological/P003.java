package com.monal.Graphs.Topological;

import java.util.*;

public class P003 {

  // =================================================================
  // PROBLEM 2: COURSE SCHEDULE II (LEETCODE 210) - ACTUAL ORDER
  // =================================================================
  /*
   * Same as above but return the actual course order.
   * If impossible, return empty array.
   */

  public static int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) {
      adj.add(new ArrayList<>());
    }

    int[] indegree = new int[numCourses];
    for (int[] prereq : prerequisites) {
      adj.get(prereq[1]).add(prereq[0]);
      indegree[prereq[0]]++;
    }

    Queue<Integer> queue = new LinkedList<>();
    for (int i = 0; i < numCourses; i++) {
      if (indegree[i] == 0) {
        queue.offer(i);
      }
    }

    int[] result = new int[numCourses];
    int index = 0;

    while (!queue.isEmpty()) {
      int course = queue.poll();
      result[index++] = course;

      for (int nextCourse : adj.get(course)) {
        indegree[nextCourse]--;
        if (indegree[nextCourse] == 0) {
          queue.offer(nextCourse);
        }
      }
    }

    return index == numCourses ? result : new int[0];
  }

  public static void main(String[] args) {
    int numCourses = 4;
    int[][] prerequisites = {
        { 1, 0 },
        { 2, 1 },
        { 3, 2 }
    };

    int[] order = findOrder(numCourses, prerequisites);
    if (order.length == 0) {
      System.out.println("Impossible to complete all courses.");
    } else {
      System.out.println("Course order: " + Arrays.toString(order));
    }

  }
}
