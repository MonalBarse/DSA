package com.monal.Graphs.BFS_DFS;

import java.util.*;

/*
A certain bug's home is on the x-axis at position x. Help them get there from position 0.
The bug jumps according to the following rules:

It can jump exactly a positions forward (to the right).
It can jump exactly b positions backward (to the left).
It cannot jump backward twice in a row.
It cannot jump to any forbidden positions.
The bug may jump forward beyond its home, but it cannot jump to positions numbered with negative integers.

Given an array of integers forbidden, where forbidden[i] means that the bug cannot jump to the position forbidden[i], and integers a, b, and x, return the minimum number of jumps needed for the bug to reach its home. If there is no possible sequence of jumps that lands the bug on position x, return -1.
Example 1:
  Input: forbidden = [14,4,18,1,15], a = 3, b = 15, x = 9 | Output: 3
  Explanation: 3 jumps forward (0 -> 3 -> 6 -> 9) will get the bug home.
Example 2:
  Input: forbidden = [8,3,16,6,12,20], a = 15, b = 13, x = 11 | Output: -1
Example 3:
  Input: forbidden = [1,6,2,14,5,17,4], a = 16, b = 9, x = 7 | Output: 2
  Explanation: One jump forward (0 -> 16) then one jump backward (16 -> 7) will get the bug home.

Constraints:
  1 <= forbidden.length <= 1000
  1 <= a, b, forbidden[i] <= 2000
  0 <= x <= 2000
  All the elements in forbidden are distinct.
  Position x is not forbidden.
 */
public class P021 {
  public int minimumJumps(int[] forbidden, int a, int b, int x) {
    Set<Integer> forbiddenSet = new HashSet<>();
    for (int f : forbidden)
      forbiddenSet.add(f);

    Queue<int[]> q = new ArrayDeque<>();
    q.offer(new int[] { 0, 0 }); // [position, backUsed]

    // Visited[position][backUsed]
    boolean[][] visited = new boolean[6001][2]; // 2000(forbidden[i]) + x (2000) + a/b (2000) = 6000
    visited[0][0] = true;

    int steps = 0;
    while (!q.isEmpty()) {
      int size = q.size();
      for (int i = 0; i < size; i++) {
        int[] state = q.poll();
        int pos = state[0], backUsed = state[1];
        if (pos == x)
          return steps;
        // 1. forward jump
        int nextPos = pos + a;
        if (nextPos < 6000 && !forbiddenSet.contains(nextPos) && !visited[nextPos][0]) {
          visited[nextPos][0] = true;
          q.offer(new int[] { nextPos, 0 });
        }

        // 2. backward jump (if alst was not backmove)
        int prevPos = pos - b;
        if (backUsed == 0 && prevPos >= 0 && !forbiddenSet.contains(prevPos) && !visited[prevPos][1]) {
          visited[prevPos][1] = true;
          q.offer(new int[] { prevPos, 1 });
        }
      }
      steps++;
    }
    return -1;
  }
}
