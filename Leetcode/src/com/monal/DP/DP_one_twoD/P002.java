package com.monal.DP.DP_one_twoD;

import java.util.Arrays;

/*
Problem statement
Ninja is planing this ‘N’ days-long training schedule. Each day, he can perform any one of these three activities. (Running, Fighting Practice or Learning New Moves). Each activity has some merit points on each day. As Ninja has to improve all his skills, he can’t do the same activity in two consecutive days. Can you help Ninja find out the maximum merit points Ninja can earn?

You are given a 2D array of size N*3 ‘POINTS’ with the points corresponding to each day and activity. Your task is to calculate the maximum number of merit points that Ninja can earn.

For Example
If the given ‘POINTS’ array is [[1,2,5], [3 ,1 ,1] ,[3,3,3] ],the answer will be 11 as 5 + 3 + 3.

*/
public class P002 {
  public class Solution {
    public static int ninjaTraining(int n, int[][] points) {
      int[][] memo = new int[n][4]; // 4 options for last activity (0, 1, 2, or 3)
      for (int i = 0; i < n; i++) {
        Arrays.fill(memo[i], -1);
      }
      // return ninjaMemo(n, points, 0, 3, memo); // Start with no last activity
      return ninjaMemo1(n, points, n - 1, 3, memo);
    }

    @SuppressWarnings("unused")
    private static int ninjaMemo(int n, int[][] points, int day, int lastActivity, int[][] memo) {
      if (day == n)
        return 0;
      if (memo[day][lastActivity] != -1)
        return memo[day][lastActivity];

      int maxPoints = 0;
      for (int activity = 0; activity < 3; activity++) {
        if (activity != lastActivity) {
          int pointsTillToday = points[day][activity]/* points today */
              + ninjaMemo(n, points, day + 1, activity, memo); /* all points from next day */

          maxPoints = Math.max(maxPoints, pointsTillToday);
        }
      }
      memo[day][lastActivity] = maxPoints;
      return maxPoints;
    }

    private static int ninjaMemo1(int n, int[][] points, int day, int lastActivity, int[][] memo) {
      // base case
      if (day == 0) {
        int maxPoints = 0;
        for (int i = 0; i < 3; i++) {
          if (i != lastActivity) {
            maxPoints = Math.max(maxPoints, points[day][i]);
          }
        }
        return maxPoints;
      }

      // check if already calculated
      if (memo[day][lastActivity] != -1) {
        return memo[day][lastActivity];
      }

      int maxPoints = 0;
      for (int activity = 0; activity < 3; activity++) {
        if (activity != lastActivity) {
          int p = points[day][activity] + ninjaMemo1(n, points, day - 1, activity, memo);
          maxPoints = Math.max(maxPoints, p);
        }
      }
      memo[day][lastActivity] = maxPoints;
      return maxPoints;

    }

    public static int ninjaTrainingTab(int n, int[][] points) {
      int[][] dp = new int[n][4];

      // Base case for day 0
      for (int last = 0; last <= 3; last++) {
        dp[0][last] = 0;
        for (int task = 0; task < 3; task++) {
          if (task != last) {
            dp[0][last] = Math.max(dp[0][last], points[0][task]);
          }
        }
      }

      // Build dp table
      for (int day = 1; day < n; day++) {
        for (int last = 0; last <= 3; last++) {
          dp[day][last] = 0;
          for (int task = 0; task < 3; task++) {
            if (task != last) {
              int point = points[day][task] + dp[day - 1][task];
              dp[day][last] = Math.max(dp[day][last], point);
            }
          }
        }
      }
      return dp[n - 1][3];
    }

    @SuppressWarnings("unused")
    private static int ninjaTraingSpaceOptimized(int[][] points) {
      int days = points.length;
      int[] prev = new int[4];

      // Base case for day 0
      for (int last = 0; last <= 3; last++) {
        prev[last] = 0;
        for (int task = 0; task < 3; task++) {
          if (task != last) {
            prev[last] = Math.max(prev[last], points[0][task]);
          }
        }
      }

      // Build dp table
      for (int day = 1; day < days; day++) {
        int curr[] = new int[4];
        for (int last = 0; last <= 3; last++) {
          curr[last] = 0;
          for (int task = 0; task < 3; task++) {
            if (task != last) {
              int point = points[day][task] + prev[task];
              curr[last] = Math.max(curr[last], point);
            }
          }
        }

        // Update prev to curr for the next iteration
        prev = curr; // This copies the reference, not the content

      }
      return prev[3];
    }

  }
}
