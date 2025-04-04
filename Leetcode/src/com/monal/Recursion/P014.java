package com.monal.Recursion;
/*
Problem : Josephous Circle
Description : The Josephus problem is a theoretical problem related to a certain elimination game.
Statement: In this problem you are given `n` people who stand in a circle and are numbered from `1` to `n`.
The elimination game proceeds as follows:
    - The first person in the circle is eliminated.
    - The next `k'th person is eliminated.
    - This process continues until only one person remains.
The task is to find the position of the last remaining person.

Example 1:
  Input: n = 40, k = 7
  Output: 24

Example 2:
  Input: n = 7, k = 3
  Output: 4
*/

public class P014 {
  class Solution {
    public int findTheWinner(int n, int k) {
      if (n == 1)
        return 1; // Base case: only one person left
      // Recursive case: find the winner in the smaller circle
      int winner = findTheWinner(n - 1, k);
      // Adjust the position based on the current circle size and step size
      // The formula (winner + k - 1) % n gives the new position in the current circle
      return (winner + k - 1) % n + 1;
    }
  }

  public static void main(String[] args) {
    P014 p = new P014();
    Solution sol = p.new Solution();
    int[] n = { 40, 5, 3, 6, 7, 34, 66, 100 };
    int[] k = { 7, 2, 1, 3, 4, 5, 6, 7 };

    for (int i = 0; i < n.length; i++) {
      System.out.println("n: " + n[i] + ", k: " + k[i] + " => Winner: " + sol.findTheWinner(n[i], k[i]));
    }
  }

}
