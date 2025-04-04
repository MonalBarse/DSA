package com.monal.Recursion;
/*
 * Problem : Tower of Hanoi
 * Description : The Tower of Hanoi is a classic problem in recursion.
 * It consists of three rods and n disks of different sizes which can slide onto any rod.
 * The puzzle starts with the disks stacked in ascending order of size on one rod,
 * smallest on top, so that the smallest disk is on the top and the largest disk is at the bottom.
 * The objective of the puzzle is to move the entire stack to another rod,
 * obeying the following simple rules:
 *    1. Only one disk can be moved at a time.
 *    2. Each move consists of taking the upper disk from one of the stacks and placing it on top of another stack or on an empty rod.
 *    3. No larger disk may be placed on top of a smaller disk.
 *
 *
 */

public class P012 {
  public class Solution {
    /**
     * Note: Despite accepting source, destination, and auxiliary peg parameters,
     * this implementation doesn't actually use them for the calculation.
     *
     * @param n    The number of disks
     * @param from The source peg
     * @param to   The destination peg
     * @param aux  The auxiliary peg
     * @return The minimum number of moves required
     */
    public int towerOfHanoi(int n, int from, int to, int aux) {
      if (n == 0)
        return 0; // Edge case: no disks means no moves

      int[] cache = new int[n];
      // Build the answer table using dynamic programming
      return steps(n, cache);
    }

    /**
     * Helper method that uses dynamic programming to calculate the number of steps
     * required for the Tower of Hanoi problem with i disks.
     * The recurrence relation is: steps(i) = 2 * steps(i-1) + 1
     *
     * @param i  The number of disks
     * @param dp The memoization array to store computed results
     * @return The number of steps required to move i disks
     */
    public int steps(int i, int[] dp) {
      if (i == 1) {
        return (dp[i - 1] = 1); // Base case: 1 disk requires 1 move
      }

      // Check if we've already calculated this value
      if (dp[i - 1] != 0) {
        return dp[i - 1]; // Return cached result
      }

      // Calculate using recurrence relation: steps(i) = 2 * steps(i-1) + 1
      dp[i - 1] = 2 * steps(i - 1, dp) + 1;

      return dp[i - 1];
    }

    /**
     * This method calculates the number of steps required to solve the Tower of
     * Hanoi problem using pure recursion.
     * 
     * @param n    The number of disks
     * @param from The source peg
     * @param to   The destination peg
     * @param aux  The auxiliary peg
     *
     * @return The number of steps required to move n disks from the source peg to
     *         the destination peg
     */
    public int towerOfHanoi2(int n, int from, int to, int aux) {
      if (n <= 1)
        return n;
      int steps = 0;

      steps += towerOfHanoi2(n - 1, from, aux, to);
      // Induction step: Move the nth disk
      steps += 1;
      steps += towerOfHanoi2(n - 1, aux, to, from);

      return steps;
    }
  }
}
