package com.monal.Graphs.BFS_DFS;

import java.util.*;

/*

Topics
premium lock icon
Companies
You have a data structure of employee information, including the employee's unique ID, importance value, and direct subordinates' IDs.

You are given an array of employees employees where:

employees[i].id is the ID of the ith employee.
employees[i].importance is the importance value of the ith employee.
employees[i].subordinates is a list of the IDs of the direct subordinates of the ith employee.
Given an integer id that represents an employee's ID, return the total importance value of this employee and all their direct and indirect subordinates.
 */

public class P015 {
  class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;

    Employee(int id, int importance, List<Integer> subordinates) {
      this.id = id;
      this.importance = importance;
      this.subordinates = subordinates;
    }
  }

  class Solution {
    class Pair {
      int importance;
      List<Integer> subordinates;

      Pair(int importance, List<Integer> subordinates) {
        this.importance = importance;
        this.subordinates = subordinates;
      }
    }

    public int getImportance(List<Employee> employees, int id) {
      // Create a map - Integer(id) to Employee (importance, List of subordinates)
      Map<Integer, Pair> employeeMap = new HashMap<>();
      for (Employee employee : employees)
        employeeMap.put(employee.id, new Pair(employee.importance, employee.subordinates));

      // Use BFS to traverse the graph
      Queue<Integer> queue = new LinkedList<>();
      queue.offer(id);
      int totalImportance = 0;

      while (!queue.isEmpty()) {
        int currentId = queue.poll();
        Pair currentEmployee = employeeMap.get(currentId);
        totalImportance += currentEmployee.importance;

        // Add all subordinates to the queue
        for (int subordinateId : currentEmployee.subordinates) {
          queue.offer(subordinateId);
        }
      }
      return totalImportance;
    }
  }
}
