package com.monal.Stacks_Queues;

import java.util.*;
/*
Alexa has two stacks of non-negative integers, stack  and stack
where index  denotes the top of the stack. Alexa challenges Nick to play the following game:

In each move, Nick can remove one integer from the top of either stack  or stack .
Nick keeps a running sum of the integers he removes from the two stacks.
Nick is disqualified from the game if, at any point, his running sum becomes greater than some integer  given at the beginning of the game.
Nick's final score is the total number of integers he has removed from the two stacks.
Given , , and  for  games, find the maximum possible score Nick can achieve.

Example
The maximum number of values Nick can remove is . There are two sets of choices with this result.
Remove  from  with a sum of .
Remove  from  and  from  with a sum of .
Function Description
Complete the twoStacks function in the editor below.
twoStacks has the following parameters: - int maxSum: the maximum allowed sum
- int a[n]: the first stack
- int b[m]: the second stack

Returns
- int: the maximum number of selections Nick can make

Input Format
  The first line contains an integer,  (the number of games). The  subsequent lines describe each game in the following format:
  The first line contains three space-separated integers describing the respective values of  (the number of integers in stack ),  (the number of integers in stack ), and  (the number that the sum of the integers removed from the two stacks cannot exceed).
  The second line contains  space-separated integers, the respective values of .
  The third line contains  space-separated integers, the respective values of .


Sample Input
  1
  5 4 10
  4 2 4 6 1
  2 1 8 5
Sample Output
  4

*/

public class P002 {
  class Result {

    /*
     * Complete the 'twoStacks' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     * 1. INTEGER maxSum
     * 2. INTEGER_ARRAY a
     * 3. INTEGER_ARRAY b
     */

    public static int twoStacks(int maxSum, List<Integer> a, List<Integer> b) {
      // Lets solve using recursion
      int n = a.size(), m = b.size();
      int[][] dp = new int[n + 1][m + 1]; // dp[i][j] = max sum with i elements from a and j elements from b

      // FIll the DP table
      for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= m; j++) {
          if (i == 0 && j == 0) {
            dp[i][j] = 0;
          } else if (i == 0) {
            // If we are taking elements from b only
            dp[i][j] = dp[i][j - 1] + b.get(j - 1);
          } else if (j == 0) {
            // If we are taking elements from a only
            dp[i][j] = dp[i - 1][j] + a.get(i - 1);
          } else {
            // If we are taking elements from both a and b
            dp[i][j] = Math.max(dp[i - 1][j] + a.get(i - 1), dp[i][j - 1] + b.get(j - 1));
          }
        }
      }

      // Now we need to find the maximum number of elements we can take from both
      // stacks
      int maxElements = 0;
      for (int i = 0; i <= n; i++) {
        for (int j = 0; j <= m; j++) {
          if (dp[i][j] <= maxSum) {
            maxElements = Math.max(maxElements, i + j);
          }
        }
      }
      return maxElements;
    }

  }
}
