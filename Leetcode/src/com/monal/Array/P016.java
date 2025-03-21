package com.monal.Array;

import java.util.*;

// https://leetcode.com/problems/merge-intervals/
/*
  Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals,
  and return an array of the non-overlapping intervals that cover all the intervals in the input.

  Example 1:
    Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
    Output: [[1,6],[8,10],[15,18]]
  Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].

  Example 2:
    Input: intervals = [[1,4],[4,5]]
    Output: [[1,5]]
  Explanation: Intervals [1,4] and [4,5] are considered overlapping.

*/
public class P016 {
  // FIRST THOUGHT

  // class Solution {
  // public int[][] merge(int[][] intervals) {
  // if (intervals == null || intervals.length == 0) return new int[0][];
  // Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

  // List<List<Integer>> result = new ArrayList<>();

  // int start = intervals[0][0];
  // int end = intervals[0][1];

  // for (int i = 1; i < intervals.length; i++) {
  // int nextStart = intervals[i][0];
  // int nextEnd = intervals[i][1];

  // if (end >= nextStart) {
  // end = Math.max(end, nextEnd);
  // } else {
  // // No overlap: store merged interval and reset
  // result.add(Arrays.asList(start, end));
  // start = nextStart;
  // end = nextEnd;
  // }
  // }

  // // Add the last merged interval
  // result.add(Arrays.asList(start, end));

  // // Convert List<List<Integer>> to int[][]
  // return result.stream().map(l ->
  // l.stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new);
  // }
  // }

  class Solution {
    public int[][] merge(int[][] intervals) {
      TreeMap<Integer, Integer> map = new TreeMap<>();

      // Step 1: Store max end for each start point
      for (int[] interval : intervals) {
        map.put(interval[0], Math.max(map.getOrDefault(interval[0], 0), interval[1]));
      }

      List<int[]> result = new ArrayList<>();
      int start = -1, end = -1;

      // Step 2: Merge intervals using TreeMap
      for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        int currStart = entry.getKey();
        int currEnd = entry.getValue();

        if (start == -1) {
          start = currStart;
          end = currEnd;
        } else if (currStart <= end) {
          end = Math.max(end, currEnd);
        } else {
          result.add(new int[] { start, end });
          start = currStart;
          end = currEnd;
        }
      }

      // Add last merged interval
      result.add(new int[] { start, end });

      // Convert List to int[][]
      return result.toArray(new int[result.size()][]);
    }
  }

  public static void main(String[] args) {

    P016 p016 = new P016();
    Solution solution = p016.new Solution();

    int[][] intervals1 = { { 1, 3 }, { 2, 6 }, { 8, 10 }, { 15, 18 } };
    int[][] intervals2 = { { 1, 4 }, { 2, 5 }, { 3, 9 }, { 2, 5 }, { 8, 21 }, { 21, 22 }, { 23, 54 } };

    System.out.println(
        "Result 1: " + Arrays.deepToString(solution.merge(intervals1)) + "  Expected: [[1, 6], [8, 10], [15, 18]]");
    System.out
        .println("Result 2: " + Arrays.deepToString(solution.merge(intervals2)) + " Expected: [[1, 22], [23, 54]]");
  }
}
